<%@page isELIgnored="false" pageEncoding="UTF-8" contentType="text/html; UTF-8" %>
<!doctype html>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/boot/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/jqgrid/extend/css/trirand/ui.jqgrid-bootstrap.css">
    <script src="${pageContext.request.contextPath}/boot/js/jquery-3.3.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/boot/js/bootstrap.3.3.7.min.js"></script>
    <script src="${pageContext.request.contextPath}/jqgrid/extend/js/trirand/jquery.jqGrid.min.js"></script>
    <script src="${pageContext.request.contextPath}/jqgrid/js/i18n/grid.locale-cn.js"></script>
    <script src="${pageContext.request.contextPath}/boot/js/ajaxfileupload.js"></script>
    <style type="text/css">
        .table thead th{
            text-align:center;
        }
        .table tbody tr td{
            vertical-align: middle;
            text-align:center;
        }
        .table tbody tr td input{
            margin: 0 auto;
        }
    </style>
    <title>持明法洲后台管理系统</title>
</head>
<body>
    <div class="container-fluid">
        <%--标题导航栏--%>
        <jsp:include page="head.jsp"/>
        <%--页面主体--%>
        <div class="row">
            <%--左侧导航栏--%>
            <div class="col-md-2">
                <jsp:include page="left.jsp"/>
            </div>
            <%--右侧内容栏--%>
            <div class="col-md-10" id="rightJsp">
                <div class="well" style="height: 25%">
                    <h1>欢迎进入持明法洲后台管理系统</h1>
                </div>
                <div id="carousel-example-generic" class="carousel slide" data-ride="carousel">
                    <!-- Indicators -->
                    <ol class="carousel-indicators">
                        <li data-target="#carousel-example-generic" data-slide-to="0" class="active"></li>
                        <li data-target="#carousel-example-generic" data-slide-to="1"></li>
                        <li data-target="#carousel-example-generic" data-slide-to="2"></li>
                        <li data-target="#carousel-example-generic" data-slide-to="3"></li>
                    </ol>

                    <!-- Wrapper for slides -->
                    <div class="carousel-inner" role="listbox" style="height: 410px">
                        <div class="item active">
                            <img src="${pageContext.request.contextPath}/img/1.png" alt="..." style="margin: 0 auto">
                        </div>
                        <div class="item">
                            <img src="${pageContext.request.contextPath}/img/2.png" alt="..." style="margin: 0 auto">
                        </div>
                        <div class="item">
                            <img src="${pageContext.request.contextPath}/img/3.png" alt="..." style="margin: 0 auto">
                        </div>
                        <div class="item">
                            <img src="${pageContext.request.contextPath}/img/4.png" alt="..." style="margin: 0 auto">
                        </div>
                    </div>

                    <!-- Controls -->
                    <a class="left carousel-control" href="#carousel-example-generic" role="button" data-slide="prev">
                        <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
                        <span class="sr-only">Previous</span>
                    </a>
                    <a class="right carousel-control" href="#carousel-example-generic" role="button" data-slide="next">
                        <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
                        <span class="sr-only">Next</span>
                    </a>
                </div>
            </div>
        </div>
        <%--页尾--%>
        <nav class="navbar navbar-default navbar-fixed-bottom">
            <div class="container text-center">
                @百知教育 baizhi@zparkhr.com.cn
            </div>
        </nav>
    </div>
</body>
</html>