<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false"%>
<html>
<head>
    <title>海文 在线短信平台</title>
    <link type="text/css" rel="stylesheet" href="css/sms.css"/>
    <script type="text/javascript" src="scripts/jquery.js"></script>
    <script>
        var $name, $pwd, $email, $sub, $info, $affirm;

        $(function () {
            $name = $("#uName");
            $pwd = $("#pwd");
            $affirm = $("#affirm")
            $email = $("#email");
            $sub = $("#sub");
            //绑定失去焦点事件；鼠标移出文本框就判断用户名是否重复
            $("#uName").blur(function () {
                //获取用户名框中的值
                get();
            });

            //绑定单击事件
            $sub.click(function () {
                //如果输入正确就判断注册是否成功
                if (jiance()) {
                    post();
                }else {//不正确打印错误
                    $("#error").html($info);
                }

            })
        });

        //验证用户输入的是否正确
        function jiance() {
            var em = /^\w{3,}(\.\w+)*@[A-z0-9]+(\.[A-z]{2,5}){1,2}$/;
            if ($name.val() == "") {
                $info = "用户名不能为空！";
                return false;
            } else if ($pwd.val() == "") {
                $info = "密码不能为空！";
                return false;
            } else if ($affirm.val()!=$pwd.val()){
                $info = "两次密码不一致！";
                return false;
            }else if ($email.val() == "") {
                $info = "邮箱不能为空！";
                return false;
            }else if (em.test($email.val())==false) {
                $info = "邮箱格式不正确！";
                return false;
            }
            return true;
        }

        //注册单击事件
        function post() {
            $.post("${pageContext.request.contextPath}/s2", {
                "param": "zhuCe",
                "username": $name.val(),
                "pwd": $pwd.val(),
                "email": $email.val(),
                "affirm":$affirm.val()
            }, function (data) {
                //判断注册是否成功
                if (data.success) {
                    window.location = "${pageContext.request.contextPath}/index.jsp";
                } else {
                    $("#error").html(data.info);
                }
            }, "json")
        }

        //检测用户名是否被使用
        function get() {
            $.post("${pageContext.request.contextPath}/s2", {
                "param": "queryName",
                "username": $name.val()
            }, function (data) {
                //判断用户名是否被使用
                if (data.success) {
                    $("#error").html(data.info);
                } else {
                    $("#error").html(data.info);
                }
            }, "json")
        }
    </script>
</head>

<body>
<div id="regTitle" class="png"></div>
<div id="regForm" class="userForm png">

    <form action="s2" method="post">
        <dl>
            <div id="error"></div>
            <dt>用 户 名：</dt>
            <dd>
                <input type="text" id="uName"/>
            </dd>
            <dt>密 码：</dt>
            <dd>
                <input type="password" id="pwd"/>
            </dd>
            <dt>确认密码：</dt>
            <dd>
                <input type="password" id="affirm"/>
            </dd>
            <dt>邮 箱：</dt>
            <dd>
                <input type="text" id="email"/>
            </dd>
        </dl>
        <div class="buttons">
            <input class="btn-reg png" type="button" id="sub" value=" "/><input
                class="btn-reset png" type="reset" name="reset" value=" "/>
        </div>
        <div class="goback">
            <a href="index.jsp" class="png">返回登录页</a>
        </div>
    </form>
</div>
</body>
</html>
