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
            <div class="section section-danger text-justify">
                <div class="container">
                    <div class="row text-center">
                        <div class="col-md-12 text-center">
                            <h2 class="text-center">${mensagem}</h2>
                            <c:if test="${exemplar != null}">
                                <h4 class="text-center"> CÓDIGO EXEMPLAR: ${exemplar.idexemplar}</h4>
                                <h4 class="text-center"> CÓDIGO LIVRO: ${exemplar.livro.idlivro}</h4>
                                <h4 class="text-center"> TÍRTULO: ${exemplar.livro.titulo}</h4>
                                <h4 class="text-center"> ISBN: ${exemplar.livro.isbn}</h4>
                                <h4 class="text-center"> AUTOR: ${exemplar.livro.autor}</h4>
                                <h4 class="text-center"> EDITORA ${exemplar.livro.editora}</h4>
                                <h4 class="text-center"> EDIÇÃO: ${exemplar.livro.edição}</h4>
                                <h4 class="text-center"> ANO: ${exemplar.livro.ano}</h4>
                                <h4 class="text-center"> SEÇÃO: ${exemplar.livro.secao}</h4>
                                <h4 class="text-center"> QUANTIDADE: ${exemplar.livro.quantidade}</h4>
                            </c:if>
                        </div>
                    </div>
                </div>
            </div>

            <div class="section">
                <div class="container">
                    <div class="row">
                        <div class="col-md-12 text-center">
                            <a class="btn btn-default" href="index.jsp">Retornar ao Menu Exemplar</a>
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