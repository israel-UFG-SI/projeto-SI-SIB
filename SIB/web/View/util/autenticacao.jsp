<%-- 
    Document   : autenticacao
    Created on : 15/11/2017, 23:43:31
    Author     : Matheus
--%>
    <meta charset="utf-8">
    <jsp:directive.page import="java.sql.*" />
    <%@page contentType="text/html" pageEncoding="UTF-8"%>
        <jsp:declaration>
            static String url = "jdbc:mysql://localhost:3306/sib?zeroDateTimeBehavior=convertToNull"; 
            static String usuario = "root"; 
            static String senha = "root"; 
            static Connection conexao; 
            public void init() throws ServletException { 
                try { 
                    Class.forName("com.mysql.jdbc.Driver");
                    conexao = DriverManager.getConnection(url, usuario, senha); 
                    conexao.setAutoCommit(false); 
                } catch (SQLException | ClassNotFoundException e) { 
                    e.printStackTrace(); 
                    } 
                }
        </jsp:declaration>

        <jsp:scriptlet>
            String cpfmascara = request.getParameter("cpf"); 
            cpfmascara = cpfmascara.replaceAll("[.-]", ""); 
            long cpf = Long.parseLong(cpfmascara); 
            String senhauser = request.getParameter("senha"); 
            String consulta = "SELECT * FROM Login where cpf ='"+cpf+"' and senha='"+senhauser+"'";
            Statement statetment; 
            try { 
                statetment = conexao.createStatement(); 
                ResultSet rs = statetment.executeQuery(consulta); 
                if(rs.next()){ 
                    session.setAttribute("mensagem", "Usuário Autenticado!"); 
                    session.setAttribute("login", "true"); 
                    response.sendRedirect("../index.jsp");
            }else{ 
                    session.setAttribute("mensagem", "Usuário Não Autenticado! Tente Novamente."); 
                    session.setAttribute("login", "false"); response.sendRedirect("../login.jsp"); 
                } 
            } catch (SQLException e) { 
                e.printStackTrace(); 
            }
        </jsp:scriptlet>