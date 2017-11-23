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
                        <h3 class="tt_menu">&gt;&gt; LIVROS - ALTERAR UM LIVRO &lt;&lt;</h3>
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
                                    <input type="number" name="idLivro" class="form-control" id="IdLivro" min="0" placeholder="Informe o código" title="Digite o código único númerico" required="">
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-sm-2">
                                    <label for="inputTitulo" class="control-label">Informar o Titulo:</label>
                                </div>
                                <div class="col-sm-10">
                                    <input type="text" name="titulo" class="form-control" id="inputTitulo" placeholder="Titulo" required>
                                </div>
                            </div>

                            <div class="form-group">
                                <div class="col-sm-2">
                                    <label for="inputISBN" class="control-label">Informar o ISBN:</label>
                                </div>
                                <div class="col-sm-10">
                                    <input type="text" name="ISBN" class="form-control" id="inputISBN" placeholder="ISBN" required>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-sm-2">
                                    <label for="inputAutor" class="control-label">Informar o Autor:</label>
                                </div>
                                <div class="col-sm-10">
                                    <input type="text" name="autor" class="form-control" id="inputAutor" placeholder="Autor" required>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-sm-2">
                                    <label for="inputEditora" class="control-label">Informar a Editora:</label>
                                </div>
                                <div class="col-sm-10">
                                    <input type="text" name="editora" class="form-control" id="inputEditora" placeholder="Editora" required>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-sm-2">
                                    <label for="inputEdicao" class="control-label">Informar a Edição:</label>
                                </div>
                                <div class="col-sm-10">
                                    <input type="text" name="edicao" class="form-control" id="inputEdicao" placeholder="Edição" required>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-sm-2">
                                    <label for="inputAno" class="control-label">Informar o Ano:</label>
                                </div>
                                <div class="col-sm-10">
                                    <input type="number" name="ano" class="form-control" id="inputAno" min="0" placeholder="Ano" required>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-sm-2">
                                    <label for="inputSecao" class="control-label">Informar a Seção:</label>
                                </div>
                                <div class="col-sm-10">
                                    <input type="text" name="secao" class="form-control" id="inputSecao" placeholder="Seção" required>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-sm-2">
                                    <label for="inputQtd" class="control-label">Informar a Quatidade:</label>
                                </div>
                                <div class="col-sm-10">
                                    <input type="number" name="qtd" class="form-control" min="0" id="inputQtd" placeholder="Quantidade de Exemplares" required>
                                </div>
                            </div>
                            <input type="hidden" name="idFormulario" value="2" />
                            <input type="hidden" name="tipoFormulario" value="24" />
                            <div class="col-md-12 text-center">
                                <button type="submit" class="btn btn-danger">Alterar</button>
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