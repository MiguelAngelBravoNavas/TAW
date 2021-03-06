<%-- 
    Document   : listadoMensajes
    Created on : 10-may-2021, 21:24:15
    Author     : migue
--%>

<%@page import="java.text.SimpleDateFormat"%>
<%@page import="eventoswebapp.entity.Mensaje"%>
<%@page import="eventoswebapp.entity.Conversacion"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
    <%
        Conversacion con = (Conversacion)request.getAttribute("conver");
        
    %>    
    <body>
        <h1>Mensajes</h1>
           
    <%
        if (con == null || con.getMensajeList().isEmpty()) {
    %>          
            
            <h2>No hay mensajes</h2>
    </br><a href="ServletListarCoversaciones?id=<%=con.getConversacionId()%>">Atras</a>
    <%
        } else {
    %>
    
    </br><a href="ServletListarCoversaciones?id=<%=con.getConversacionId()%>">Atras</a>
    <form action="ServletListarCoversaciones?id=<%=con.getConversacionId()%>&codigo=listarmensajes&filtro=porfecha" name="reciente" method="POST">
        <input type="submit" value="Recientes" name="Recientes" />
    </form>
    <table border="1">
        <tr>
            <th>USUARIO</th>
            <th>MENSAJE</th>
            <th>HORA</th>
            <th>FECHA</th>
            <th>BORRAR</th>         
        </tr>
    <%      SimpleDateFormat ff = new SimpleDateFormat("dd-MM-yyyy");
            SimpleDateFormat fh = new SimpleDateFormat("HH:mm");
            for (Mensaje m :con.getMensajeList()) {
    %>
        <tr>
            <td><%=m.getRemitenteId().getEmail() %></td>
            <td><%=m.getTexto()%></td>
            <td><%=fh.format(m.getEnviado()) %></td>
            <td><%=ff.format(m.getEnviado()) %></td>
            <td><a href="ServletBorrarConversacion?id=<%= m.getMensajeId() %>&codigo=borrarmensaje">Borrar</a> </td>
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