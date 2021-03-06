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
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author migue
 */
@WebServlet(name = "ServletBorrarConversacion", urlPatterns = {"/ServletBorrarConversacion"})
public class ServletBorrarConversacion extends HttpServlet {

    @EJB
    private MensajeFacade mensajeFacade;

    @EJB
    private ConversacionFacade conversacionFacade;
    
    

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
         
        String id, codigo, goTo="menu.jsp";
        Conversacion c;
        Mensaje me;
        
        HttpSession session = request.getSession();
        Usuario usuario;
        usuario = (Usuario)session.getAttribute("usuario");
        
        if (usuario == null) { 
            response.sendRedirect("login.jsp");           
        } else {        
            id = request.getParameter("id");
            if (id == null) {
                response.sendRedirect("menu.jsp");            
            } else {
                codigo=request.getParameter("codigo");
                if(codigo != null){ //mensajes
                 me= this.mensajeFacade.find(new Integer(id));
                 me.setTexto("--Mensaje--eliminado--");
                 this.mensajeFacade.edit(me);
                 response.sendRedirect("ServletListarCoversaciones?id="+me.getConversacionId().getConversacionId() +"&codigo=listarmensajes");
                }else{ //conversaciones
                
                c= this.conversacionFacade.find(new Integer(id));               
                if (c == null) { 
                    response.sendRedirect("menu.jsp");       
                } else {          
                    for(Mensaje m : c.getMensajeList()){
                        this.mensajeFacade.remove(m);
                    }
                    this.conversacionFacade.remove(c);
                    response.sendRedirect("ServletListarCoversaciones");   
                }
              }
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
