/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eventoswebapp.servlet;

import eventoswebapp.dao.ConversacionFacade;
import eventoswebapp.dao.UsuarioFacade;
import eventoswebapp.entity.Conversacion;
import eventoswebapp.entity.Usuario;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
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
@WebServlet(name = "ServletCrearConversacion", urlPatterns = {"/ServletCrearConversacion"})
public class ServletCrearConversacion extends HttpServlet {

    @EJB
    private ConversacionFacade conversacionFacade;

    @EJB
    private UsuarioFacade usuarioFacade;
    

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
        HttpSession session = request.getSession();
        Usuario usuario;
        Conversacion c, cNueva = new Conversacion();
    
        usuario = (Usuario)session.getAttribute("usuario");
        
        if (usuario == null) { 
            response.sendRedirect("login.jsp");
        } else {
            String idTele = request.getParameter("teleop");
            Usuario teleoperador = this.usuarioFacade.find(new Integer(idTele));
            c = this.conversacionFacade.findByUserAndTele(usuario.getUsuarioId(), teleoperador.getUsuarioId());
            if(c==null){
            cNueva.setConversacionId(0);
            cNueva.setUsuarioId(usuario);
            cNueva.setTeleoperadorId(teleoperador);
            this.conversacionFacade.create(cNueva); 
            c = this.conversacionFacade.findByUserAndTele(usuario.getUsuarioId(), teleoperador.getUsuarioId());
            }      
            request.setAttribute("conversacion", c);
            RequestDispatcher rd = request.getRequestDispatcher("chat.jsp");
            rd.forward(request, response);
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
