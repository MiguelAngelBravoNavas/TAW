<%-- 
    Document   : menuUser
    Created on : 11-may-2021, 22:08:22
    Author     : migue
--%>

<%@page import="java.util.List"%>
<%@page import="eventoswebapp.entity.Usuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
  
        
        <%
              List<Usuario> teleop =(List)request.getAttribute("teleop");


            %>
            <h1>Teleoperadores disponibles</h1>
            <form action="ServletCrearConversacion" method="POST">
                CHATEAR CON: 
                <select  name="teleop">
                    <%
                        for(Usuario t : teleop){
            %>
                    <option value="<%=t.getUsuarioId() %>"><%=t.getEmail() %></option>
                    <%
                        }
            %>
                </select>
               <input type="submit" value="Chatear" name="Chatear" />
            </form>
    </body>
</html>
