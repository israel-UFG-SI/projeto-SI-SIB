<%@page contentType="text/html" pageEncoding="UTF-8"%>
    <jsp:include page="../util/topo.jsp" />
    <html>

    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <script type="text/javascript" src="../../lib/js/jquery.min.js"></script>
        <script type="text/javascript" src="../../lib/js/bootstrap.min.js"></script>
        <script type="text/javascript" src="../../lib/js/jquery.mask.js"></script>
        <script type="text/javascript" src="../../lib/js/jquery.mask.min.js"></script>
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
                        <h3 class="tt_menu">&gt;&gt; CLIENTES - CADASTRAR UM NOVO CLIENTE &lt;&lt;</h3>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-12">
                        <form class="form-horizontal" role="form" action="http://localhost/SIB/Controlador" method="post">
                            <div class="form-group">
                                <div class="col-sm-2">
                                    <label for="inputIdUsuario" class="control-label">Informar o Código:</label>
                                </div>
                                <div class="col-sm-10">
                                    <input type="number" name="idUsuario" class="form-control" id="inputIdUsuario" placeholder="Informe o código" title="Digite o código único númerico" required="">
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-sm-2">
                                    <label for="inputNome" class="control-label">Informar o Nome:</label>
                                </div>
                                <div class="col-sm-10">
                                    <input type="text" name="nome" class="form-control" id="inputNome" placeholder="Nome" required>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-sm-2">
                                    <label for="inputCPF" class="control-label">Informar o CPF:</label>
                                </div>
                                <div class="col-sm-10">
                                    <input type="text" name="cpf" id="inputCPF" title="O formato precisa ser XXX.XXX.XXX-XX" class="form-control" maxlength="14" placeholder="CPF">
                                </div>
                                <script>
                                    $("#inputCPF").mask("000.000.000-09");
                                </script>
                            </div>
                            <div class="form-group">
                                <div class="col-sm-2">
                                    <label for="inputEndereco" class="control-label">Informar o Endereço:</label>
                                </div>
                                <div class="col-sm-10">
                                    <input type="text" name="endereco" class="form-control" id="inputEndereco" placeholder="Endereço" required>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-sm-2">
                                    <label for="inputTelefone" class="control-label">Informar o Telefone:</label>
                                </div>
                                <div class="col-sm-10">
                                    <input type="text" name="telefone" class="form-control" id="inputTelefone" placeholder="Telefone" required maxlength="13" title="O  número de telefone precisa atender o formato (XX) X XXXXX-XXXX">
                                </div>
                                <script>
                                    $("#inputTelefone").mask("(00) 0 0000-0009");
                                </script>
                            </div>
                            <input type="hidden" name="idFormulario" value="1" />
                            <input type="hidden" name="tipoFormulario" value="13" />
                            <div class="col-md-12 text-center">
                                <button type="submit" class="btn btn-danger">Cadastrar</button>
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