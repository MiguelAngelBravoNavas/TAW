<%-- 
    Document   : listadoConversaciones
    Created on : 10-may-2021, 16:18:03
    Author     : migue
--%>

<%@page import="eventoswebapp.entity.Usuario"%>
<%@page import="eventoswebapp.entity.Conversacion"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Conversaciones</title>
    </head>
    <%
         Usuario usuario = (Usuario)session.getAttribute("usuario");
       
        if (usuario == null) {
            response.sendRedirect("login.jsp");  
            return;
        }
        
        
        List<Conversacion> listado = (List)request.getAttribute("listado");
        List<Usuario> teleop =(List)request.getAttribute("teleop");
       
    %>    
    <body>
        <h1>Conversaciones</h1>
            
            <form action="ServletListarCoversaciones" method="POST">
                BUSCAR POR TELEOPERADOR: 
                <select  name="teleop">
                    <option selected="selected" value="todos">todos</option>
                    <%
                        for(Usuario t : teleop){
            %>
                    <option value="<%=t.getUsuarioId() %>"><%=t.getEmail() %></option>
                    <%
                        }
            %>
                </select>
               <input type="submit" value="BUSCAR" name="BUSCAR" />
            </form>
           
    <%
        if (listado == null || listado.isEmpty()) {
    %>          
            
            <h2>No hay ningúna conversacion actualmente</h2>
     <a href="Salir">Salir del sistema</a> <br/> 
    <%
        } else {
    %>
     <a href="Salir">Salir del sistema</a> <br/> 
    <table border="1">
        <tr>
            
            <th>USUARIO</th>
            <th>TELEOPERADOR</th>
            <th>CHATEAR</th>
            <th>BORRAR</th>
            <th>VERMENSAJES</th>         
        </tr>
    <%    
            for (Conversacion c:listado) {
    %>
        <tr>
           
            <td><%=c.getUsuarioId().getEmail()  %></td>
            <td><%=c.getTeleoperadorId().getEmail() %></td>
            <%
            if(c.getTeleoperadorId().getUsuarioId()== usuario.getUsuarioId()){
     %>
            <td><a href="chat.jsp?id=<%= c.getConversacionId() %>">Chatear</a> </td>
            <%
             }else{   
     %>
            <td>Chatear</td>
     <%
            } 
     %>
            <td><a href="ServletBorrarConversacion?id=<%= c.getConversacionId() %>">Borrar</a> </td>
            <td><a href="ServletListarCoversaciones?id=<%=c.getConversacionId()%>&codigo=listarmensajes">Ver Mensajes</a> </td>
        </tr>    
    <%
            } // for
    
     %>
    </table>
    <%
            } // else
     %>
    
   
   </body>
</html>
