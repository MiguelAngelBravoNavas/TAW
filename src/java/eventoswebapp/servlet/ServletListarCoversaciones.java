/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eventoswebapp.servlet;

import eventoswebapp.dao.ConversacionFacade;
import eventoswebapp.dao.MensajeFacade;
import eventoswebapp.dao.UsuarioFacade;
import eventoswebapp.entity.Conversacion;
import eventoswebapp.entity.Mensaje;
import eventoswebapp.entity.Usuario;
import java.io.IOException;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;

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
    private MensajeFacade mensajeFacade;

    @EJB
    private UsuarioFacade usuarioFacade;
    
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
        String codigo, id, goTo,idteleop,filtro;
        Conversacion conver;
        List<Mensaje> lista;
        
        usuario = (Usuario)session.getAttribute("usuario");
        
        if (usuario == null) { 
            response.sendRedirect("login.jsp");
        } else { 
            codigo=request.getParameter("codigo");
            if(codigo != null){                                         //listar losmensajes
                id = request.getParameter("id");
                filtro= request.getParameter("filtro");
                conver =this.conversacionFacade.find(new Integer(id));
                if (filtro !=null){
                 lista = this.mensajeFacade.findOrdenInverso(Integer.parseInt(id));
                 conver.setMensajeList(lista);
                }
                
                request.setAttribute("conver", conver);
                goTo = "listadoMensajes.jsp";
            }else{                                                      //listar conversaciones
                idteleop =request.getParameter("teleop");
                List<Conversacion> listadoConversaciones;
                if(idteleop == null || idteleop.equals("todos")){
                listadoConversaciones = this.conversacionFacade.findAll();
                }else{
                listadoConversaciones = this.conversacionFacade.findByTele(Integer.parseInt(idteleop));
                }
                request.setAttribute("listado", listadoConversaciones);
                List<Usuario> teleop = this.usuarioFacade.findByRol(5);
                request.setAttribute("teleop", teleop);
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
