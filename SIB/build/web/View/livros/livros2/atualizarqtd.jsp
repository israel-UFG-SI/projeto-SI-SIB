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
                        <h3 class="tt_menu">&gt;&gt; LIVROS - ATUALIZAR A QUANTIDADE DE EXEMPLARES&lt;&lt;</h3>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-12">
                        <form class="form-horizontal" role="form" action="http://localhost/SIB/Controlador" method="post">
                            <div class="form-group">
                                <div class="col-sm-2">
                                    <label for="inputIdLivro" class="control-label">Informar o Código:</label>
                                </div>
                                <div class="col-sm-10">
                                    <input type="number" name="idLivro" min="0" class="form-control" id="IdLivro" placeholder="Informe o código" title="Digite o código único númerico" required="">
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-sm-2">
                                    <label for="inputQtd" class="control-label">Informar a Quatidade:</label>
                                </div>
                                <div class="col-sm-10">
                                    <input type="number" name="qtd" min="0" class="form-control" id="inputQtd" placeholder="Quantidade de Exemplares" required>
                                </div>
                            </div>
                            <input type="hidden" name="idFormulario" value="2" />
                            <input type="hidden" name="tipoFormulario" value="25" />
                            <div class="col-md-12 text-center">
                                <button type="submit" class="btn btn-danger">Atualizar</button>
                            </div>
                    </div>
                </div>
                </form>
            </div>
        </div>
        </div>
        </div>
        <div class="section">
            <div class="container">
                <div class="row">
                    <div class="col-md-12 text-center corrigir">
                        <a class="btn btn-default" href="javascript:window.history.go(-1)">Voltar</a>
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