/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eventoswebapp.servlet;

import eventoswebapp.dao.ConversacionFacade;
import eventoswebapp.entity.Conversacion;
import eventoswebapp.entity.Usuario;
import java.io.IOException;

import java.util.List;

import javax.ejb.EJB;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
@WebServlet(name = "ServletListarCoversaciones", urlPatterns = {"/ServletListarCoversaciones"})
public class ServletListarCoversaciones extends HttpServlet {

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
        
        HttpSession session = request.getSession();
        Usuario usuario;
        String codigo, id, goTo;
        Conversacion conver;
        
        usuario = (Usuario)session.getAttribute("usuario");
        
        if (usuario == null) { 
            response.sendRedirect("login.jsp");
        } else { 
            codigo=request.getParameter("codigo");
            if(codigo != null){                                         //listar losmensajes
                id = request.getParameter("id");
                conver =this.conversacionFacade.find(new Integer(id));
                request.setAttribute("conver", conver);
                goTo = "listadoMensajes.jsp";
            }else{                                                      //listar conversaciones
                List<Conversacion> listadoConversaciones;
                listadoConversaciones = this.conversacionFacade.findAll();
                request.setAttribute("listado", listadoConversaciones);
                goTo = "listadoConversaciones.jsp";
            
            }
            RequestDispatcher rd = request.getRequestDispatcher(goTo);
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
