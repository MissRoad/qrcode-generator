<%@ page contentType="text/html;charset=UTF-8" language="java"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html ng-app="actionApp">
<head>
    <meta charset="UTF-8"/>
    <meta http-equiv="X-UA-COMPATIBLE" content="IE=edge"/>
    <meta name="viewport" content="width=device-width,initial-scale=1"/>
    <title>二维码生成</title>
    <link href="../assets/css/bootstrap.min.css" rel="stylesheet">
    <link href="../assets/css/jquery-ui.min.css" rel="stylesheet">
    <style type="text/css">
        .content {
            width: 200px;
            margin: 30px auto;
        }
    </style>

    <script src="../assets/js/html5shiv.min.js"></script>
    <script src="../assets/js/respond.min.js"></script>
</head>
<body>
<!-- 使用Bootstrap定义导航栏,并配合AngularJS的路由,通过路由名称#/xx切换名称 -->
<nav class="navbar navbar-inverse navbar-collapse">
    <div class="container">
        <div id="navbar" class="collapse navbar-collapse">
            <ul class="nav navbar-nav">
                <li><a href="#/hello">Hello,World</a></li>
                <li><a href="#/qrcode">生成二维码</a></li>
            </ul>
        </div>
    </div>
</nav>
<!-- 通过<ng-view>标签展示载入的页面 -->
<div class="content">
    <ng-view></ng-view>
</div>
<script src="../assets/js/jquery.min.js"></script>
<script src="../assets/js/jquery-ui.min.js"></script>
<script src="../assets/js/angular.min.js"></script>
<script src="../assets/js/angular-route.min.js"></script>
<script src="../assets/js/bootstrap.min.js"></script>
<script src="../assets/app/app.js"></script>
<script src="../assets/app/qrcodeController.js"></script>
<script src="../assets/app/commonsController.js"></script>
</body>
</html>