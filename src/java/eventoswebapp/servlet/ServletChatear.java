/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eventoswebapp.servlet;

import eventoswebapp.dao.ConversacionFacade;
import eventoswebapp.dao.MensajeFacade;
import eventoswebapp.entity.Conversacion;
import eventoswebapp.entity.Mensaje;
import eventoswebapp.entity.Usuario;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.servlet.AsyncContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author https://www.oracle.com/webfolder/technetwork/tutorials/obe/java/async-servlet/async-servlets.html
 */
@WebServlet(urlPatterns = {"/shoutServlet"}, asyncSupported=true)
public class ServletChatear extends HttpServlet {

    @EJB
    private ConversacionFacade conversacionFacade;

    @EJB
    private MensajeFacade mensajeFacade;
    
    
    private List<AsyncContext> contexts = new LinkedList<>();
    private static final Logger LOG = Logger.getLogger(ServletChatear.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        final AsyncContext asyncContext = request.startAsync(request, response);
        asyncContext.setTimeout(10 * 60 * 1000);
        contexts.add(asyncContext);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Usuario usuario;
        Mensaje m = new Mensaje();
        Conversacion c;
        usuario = (Usuario)session.getAttribute("usuario");
       
        
        if (usuario == null) { 
            response.sendRedirect("login.jsp");
        } 
        
        List<AsyncContext> asyncContexts = new ArrayList<>(this.contexts);
        this.contexts.clear();
        
        String idc = request.getParameter("idc");
        String name = request.getParameter("name");
        String message = request.getParameter("message");
        c = this.conversacionFacade.find(new Integer(idc));
       
        m.setConversacionId(c);
        m.setMensajeId(0);
        m.setRemitenteId(usuario);
        m.setTexto(message);
        m.setEnviado(new Date());
        this.mensajeFacade.create(m);
        c.getMensajeList().add(m);
        this.conversacionFacade.edit(c);
           
       
        String htmlMessage = "<p><b>" + name + "</b><br/>" + message + "</p>";
        
        ServletContext application = request.getServletContext();
        
        
        if (application.getAttribute("messages") == null) {
            application.setAttribute("messages", htmlMessage);
        } else {
            String currentMessages = (String) application.getAttribute("messages");
            application.setAttribute("messages", htmlMessage + currentMessages);
        }
        
        for (AsyncContext asyncContext : asyncContexts) {
            try (PrintWriter writer = asyncContext.getResponse().getWriter()) {
                writer.println(htmlMessage);
                writer.flush();
                asyncContext.complete();
            } catch (Exception ex) {
                LOG.severe("Se ha producido la siguiente excepcion: " + ex.getMessage());
            }
        }

    }
}
