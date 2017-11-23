<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@page contentType="text/html" pageEncoding="UTF-8"%>
        <jsp:include page="../../util/topo.jsp" />
        <html>

        <head>
            <meta charset="utf-8">
            <meta name="viewport" content="width=device-width, initial-scale=1">
            <script type="text/javascript" src="../../../lib/js/jquery.min.js"></script>
            <script type="text/javascript" src="../../../lib/js/bootstrap.min.js"></script>
            <script type="text/javascript" src="../../../lib/js/jquery.mask.js"></script>
            <script type="text/javascript" src="../../../lib/js/jquery.mask.min.js"></script>
            <link href="../../../lib/css/font-awesome.min.css" rel="stylesheet" type="text/css">
            <link href="../../../lib/css/bootstrap.css" rel="stylesheet" type="text/css">
            <link href="../../../lib/css/padrao.css" rel="stylesheet" type="text/css">
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
                            <h3 class="tt_menu">&gt;&gt; EXEMPLARES - CONSULTAR TODOS OS EXEMPLARES &lt;&lt;</h3>
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
                                                <th scope="col">CÓDIGO EXEMPLAR</th>
                                                <th scope="col">DISPONÍVEL</th>
                                                <th scope="col">CÓDIGO LIVRO</th>
                                                <th scope="col">TÍTULO</th>
                                                <th scope="col">ISBN</th>
                                                <th scope="col">AUTOR</th>
                                                <th scope="col">EDITORA</th>
                                                <th scope="col">EDIÇÃO</th>
                                                <th scope="col">ANO</th>
                                                <th scope="col">SEÇÃO</th>
                                                <th scope="col">QUANTIDADE</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="row" items="${exemplares}">
                                                <tr>
                                                    <td>
                                                        <c:out value="${row.idexemplar}" />
                                                    </td>
                                                    <td>
                                                        <c:out value="${row.disponivel}" />
                                                    </td>
                                                    <td>
                                                        <c:out value="${row.livro.idlivro}" />
                                                    </td>
                                                    <td>
                                                        <c:out value="${row.livro.titulo}" />
                                                    </td>
                                                    <td>
                                                        <c:out value="${row.livro.isbn}" />
                                                    </td>
                                                    <td>
                                                        <c:out value="${row.livro.autor}" />
                                                    </td>
                                                    <td>
                                                        <c:out value="${row.livro.editora}" />
                                                    </td>
                                                    <td>
                                                        <c:out value="${row.livro.edição}" />
                                                    </td>
                                                    <td>
                                                        <c:out value="${row.livro.ano}" />
                                                    </td>
                                                    <td>
                                                        <c:out value="${row.livro.secao}" />
                                                    </td>
                                                    <td>
                                                        <c:out value="${row.livro.quantidade}" />
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
                                    <a class="btn btn-default" href="index.jsp">Retornar ao Menu Livros</a>
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