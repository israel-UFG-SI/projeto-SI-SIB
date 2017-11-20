<%
    if (session.getAttribute("login")!= "true"){
        session.setAttribute("mensagem", "Acesso Negado! Favor indentifique-se.");        

        %>
    <jsp:forward page="../login.jsp"></jsp:forward>
    <%
    }
%>