<%-- 
    Document   : chat
    Created on : 12-may-2021, 0:09:01
    Author     : migue
--%>

<%@page import="eventoswebapp.entity.Conversacion"%>
<%@page import="eventoswebapp.entity.Usuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    
    <%  
        Usuario usuario = (Usuario)session.getAttribute("usuario");
         int idc ;
        if (usuario == null) {
            response.sendRedirect("login.jsp");  
            return;
        }
        if (usuario.getRol()==5){ // es teleoperador
            String idConversacion = request.getParameter("id");
            idc = Integer.parseInt(idConversacion);
        }else{
            Conversacion c = (Conversacion)request.getAttribute("conversacion");
            idc= c.getConversacionId();
        }
        
    %> 
    <body onload="getMessages();">
        <h1>CHAT</h1>
        <form>
            <table>
                <tr>
                    <td><input type="hidden" id="idc"  value="<%= Integer.toString(idc) %>"  name="idc"/></td>
                </tr>
                <tr>
                    <td>Usuario:<%=usuario.getEmail()%></td>
                    <td><input type="hidden" id="name"  value="<%=usuario.getEmail()%>"  name="name"/></td>
                </tr>
                <tr>
                    <td>Mensaje:</td>
                    <td><input type="text" id="message" name="message" /></td>
                </tr>
                <tr>
                    <td><input type="button" onclick="postMessage();" value="ENVIAR" /></td>
                </tr>
            </table>
        </form>
        <%  
        if (usuario.getRol()==5){ // es teleoperador
        %>
           <a href="ServletListarCoversaciones?id=<%=Integer.toString(idc)%>">Abandonar el chat</a> 
        <%
        }else{
        %>
            <a href="menu.jsp">Abandonar el chat</a>
        <%
        }  
        %>  
        <h2> Conversacion </h2>
        <div id="content">
            <% if (application.getAttribute("messages") != null) {%>
            <%= application.getAttribute("messages")%>
            <% }%>
        </div>
        <script>
            function postMessage() {
                var xmlhttp = new XMLHttpRequest();
                //xmlhttp.open("POST", "shoutServlet?t="+new Date(), false);
                xmlhttp.open("POST", "shoutServlet", false);
                xmlhttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
                var idCon = escape(document.getElementById("idc").value);
                var nameText = escape(document.getElementById("name").value);
                var messageText = escape(document.getElementById("message").value);
                document.getElementById("message").value = "";
                xmlhttp.send("name="+nameText+"&message="+messageText+"&idc="+idCon);
            }
            var messagesWaiting = false;
            function getMessages(){
                if(!messagesWaiting){
                    messagesWaiting = true;
                    var xmlhttp = new XMLHttpRequest();
                    xmlhttp.onreadystatechange=function(){
                        if (xmlhttp.readyState==4 && xmlhttp.status==200) {
                            messagesWaiting = false;
                            var contentElement = document.getElementById("content");
                            contentElement.innerHTML = xmlhttp.responseText + contentElement.innerHTML;
                        }
                    }
                    //xmlhttp.open("GET", "shoutServlet?t="+new Date(), true);
                    xmlhttp.open("GET", "shoutServlet", true);
                    xmlhttp.send();
                }
            }
            setInterval(getMessages, 1000);
        </script>
    </body>
</html>
