<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@page contentType="text/html" pageEncoding="UTF-8"%>
        <jsp:include page="../util/topo.jsp" />
        <html>

        <head>
            <meta charset="utf-8">
            <meta name="viewport" content="width=device-width, initial-scale=1">
            <script type="text/javascript" src="../../lib/js/jquery.min.js"></script>
            <script type="text/javascript" src="../../lib/js/bootstrap.min.js"></script>
            <link href="../../lib/css/font-awesome.min.css" rel="stylesheet" type="text/css">
            <link href="../../lib/css/bootstrap.css" rel="stylesheet" type="text/css">
            <link href="../../lib/css/padrao.css" rel="stylesheet" type="text/css">
        </head>

        <body>
            <div class="section section-danger text-justify">
                <div class="container">
                    <div class="row text-center">
                        <div class="col-md-12 text-center">
                            <h1 class="text-center">Sistema Integrado de Biblioteca</h1>
                        </div>
                    </div>
                </div>
            </div>
            <div class="section">
                <div class="container">
                    <div class="row">
                        <div class="col-md-12 text-center">
                            <h3 class="tt_menu">&gt;&gt; CLIENTES - CONSULTAR TODOS OS CLIENTES &lt;&lt;</h3>
                        </div>
                    </div>

                    <div class="section section-danger text-justify">
                        <div class="container">
                            <div class="row text-center">
                                <div class="col-md-12 text-center">
                                    <h2 class="text-center">${mensagem}</h2>
                                    <table class="table table-bordered">
                                        <thead>
                                            <tr>
                                                <th scope="col">CÓDIGO</th>
                                                <th scope="col">NOME</th>
                                                <th scope="col">CPF</th>
                                                <th scope="col">ENDEREÇO</th>
                                                <th scope="col">TELEFONE</th>
                                                <th scope="col">PENDENCIA</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="row" items="${clientes}">
                                                <tr>
                                                    <td>
                                                        <c:out value="${row.idusuario}" />
                                                    </td>
                                                    <td>
                                                        <c:out value="${row.nome}" />
                                                    </td>
                                                    <td>
                                                        <c:out value="${row.cpf}" />
                                                    </td>
                                                    <td>
                                                        <c:out value="${row.endereco}" />
                                                    </td>
                                                    <td>
                                                        <c:out value="${row.telefone}" />
                                                    </td>
                                                    <td>
                                                        <c:out value="${row.pendencia}" />
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="section">
                        <div class="container">
                            <div class="row">
                                <div class="col-md-12 text-center">
                                    <a class="btn btn-default" href="index.jsp">Retornar ao Menu Cliente</a>
                                </div>
                            </div>
                        </div>
                    </div>
                    <footer>
                        <div class="navbar navbar-fixed-bottom bgred">
                            <div class="container">
                                <div class="row">
                                    <div class="col-sm-12 text-center" style="top:13px;color:#fff;">SIB</div>
                                </div>
                            </div>
                        </div>
                    </footer>


        </body>

        </html>