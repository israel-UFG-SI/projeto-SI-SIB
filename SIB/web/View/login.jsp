<%@page contentType="text/html" pageEncoding="UTF-8"%>
    <html>

    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <script type="text/javascript" src="http://localhost/SIB/lib/js/jquery.min.js"></script>
        <script type="text/javascript" src="http://localhost/SIB/lib/js/bootstrap.min.js"></script>
        <link href="http://localhost/SIB/lib/css/font-awesome.min.css" rel="stylesheet" type="text/css">
        <link href="http://localhost/SIB/lib/css/bootstrap.css" rel="stylesheet" type="text/css">
        <link href="http://localhost/SIB/lib/css/padrao.css" rel="stylesheet" type="text/css">
    </head>
    <title>Página de Autenticação</title>

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
                        <h3 class="tt_menu">&gt;&gt; AUTENTICAÇÃO &lt;&lt;</h3>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-12">
                        <form class="form-horizontal" role="form" action="http://localhost/SIB/View/util/autenticacao.jsp" method="post">
                            <div class="form-group">
                                <div class="col-sm-2">
                                    <label for="inputIdFuncionario" class="control-label">Informar o Usuário:</label>
                                </div>
                                <div class="col-sm-10">
                                    <input type="number" min="0" name="idFuncionario" class="form-control" id="inputIdFuncionario" placeholder="Informe o Número de Usuário" title="Digite o código único númerico" required="">
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-sm-2">
                                    <label for="inputSenha" class="control-label">Informar a Senha:</label>
                                </div>
                                <div class="col-sm-10">
                                    <input type="password" name="senha" class="form-control" id="inputSenha" placeholder="Senha" required>
                                </div>
                            </div>
                    </div>
                    <div class="col-md-12 text-center">
                        <button type="submit" class="btn btn-danger">Entrar</button>
                    </div>
                    </form>
                </div>
            </div>
        </div>
        </div>
        <p style="text-align:center; color: red">${mensagem}</p>


    </body>

    </html>