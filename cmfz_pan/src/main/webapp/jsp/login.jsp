<%@page isELIgnored="false" pageEncoding="UTF-8" contentType="text/html; UTF-8" %>
<!doctype html>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="../boot/css/bootstrap.min.css">
    <script src="../boot/js/jquery-3.3.1.min.js"></script>
    <script src="../boot/js/bootstrap.3.3.7.min.js"></script>
    <title>持明法洲后台管理系统</title>

    <script type="text/javascript">
        function sub() {
            $.ajax({
                url:"${pageContext.request.contextPath}/admin/login",
                type:"post",
                data:$("#formFo").serialize(),
                datatype:"json",
                success:function (data) {
                    if(data.code == "102"){
                        location.href = "${pageContext.request.contextPath}/jsp/main.jsp";
                    }else {
                        $("#message").text(data.message);
                    }
                }
            });
        }
    </script>
</head>
<body>
    <div class="container-fluid">
        <div class="row" style="margin: 0 auto">
            <div class="well text-center">
                <h1>管理员登录</h1>
            </div>
            <div class="container" style="width: 20%">
                <form id="formFo" method="post">
                    <div class="form-group">
                        <label for="exampleInputEmail1">用户名</label>
                        <input type="text" class="form-control" name="username" id="exampleInputEmail1">
                    </div>
                    <div class="form-group">
                        <label for="exampleInputPassword1">密码</label>
                        <input type="password" name="password" class="form-control" id="exampleInputPassword1">
                    </div>
                    <div class="form-group text-center">
                        <h5 style="color: red" id="message"><br></h5>
                    </div>
                    <div class="form-group text-center">
                        <button type="button" class="btn btn-primary" onclick="sub()">提交</button>
                        <button type="button" class="btn btn-default" style="margin-left: 20px">关闭</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</body>
</html>