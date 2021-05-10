<%-- 
    Document   : listadoConversaciones
    Created on : 10-may-2021, 16:18:03
    Author     : migue
--%>

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
        List<Conversacion> listado = (List)request.getAttribute("listado");
       
    %>    
    <body>
        <h1>Listado de Conversacioness</h1>
           
    <%
        if (listado == null || listado.isEmpty()) {
    %>          
            
            <h2>No hay ningúna conversacion actualmente</h2>
    
    <%
        } else {
    %>
    <table border="1">
        <tr>
            <th>CONVERSACIONID</th>
            <th>USUARIOEMAIL</th>
            <th>TELEOPERADOREMAIL</th>
            <th>CHATEAR</th>
            <th>BORRAR</th>
            <th>VERMENSAJES</th>         
        </tr>
    <%    
            for (Conversacion c:listado) {
    %>
        <tr>
            <td><%=c.getConversacionId() %></td>
            <td><%=c.getUsuarioId().getEmail()  %></td>
            <td><%=c.getTeleoperadorId().getEmail() %></td>
            <td><a href="werwe">Chatear</a> </td>
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
    
    </br><a href="menu.jsp">Volver al menu</a>
   </body>
</html>
