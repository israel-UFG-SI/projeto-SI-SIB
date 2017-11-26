<%@page contentType="text/html" pageEncoding="UTF-8"%>
    <jsp:include page="../../../util/topo.jsp" />
    <html>

    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <script type="text/javascript" src="../../../../lib/js/jquery.min.js"></script>
        <script type="text/javascript" src="../../../../lib/js/bootstrap.min.js"></script>
        <link href="../../../../lib/css/font-awesome.min.css" rel="stylesheet" type="text/css">
        <link href="../../../../lib/css/bootstrap.css" rel="stylesheet" type="text/css">
        <link href="../../../../lib/css/padrao.css" rel="stylesheet" type="text/css">
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
                        <h3 class="tt_menu">&gt;&gt; MULTAS - CONSULTAR MULTAS &lt;&lt;</h3>
                        <div class="col-md-12  btn-group btn-group-lg btn-group-vertical">
                            <a href="consultaEmp.jsp" class="btn btn-default">Consultar uma Multa Pelo Código da Multa (Empréstimo)</a>
                            <a href="consultaCli.jsp" class="btn btn-default">Consultar uma Multa Pelo Código do Cliente</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="section">
            <div class="container">
                <div class="row">
                    <div class="col-md-12 text-center corrigir">
                        <a class="btn btn-default" href="../index.jsp">Voltar</a>
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