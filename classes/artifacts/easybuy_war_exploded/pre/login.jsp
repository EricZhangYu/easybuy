<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <%@include file="/common/pre/header.jsp"%>

    <title>易买网登陆页面</title>
    <script type="text/javascript" src="${ctx}/statics/js/login/login.js"></script>
    <script type="text/javascript" src="${ctx}/statics/js/common/jquery-1.11.1.min_044d0927.js"></script>
    <script type="text/javascript" src="${ctx}/statics/js/common/jquery.bxslider_e88acd1b.js"></script>

    <script type="text/javascript" src="${ctx}/statics/js/common/jquery-1.8.2.min.js"></script>
    <%--<script type="text/javascript" src="${ctx}/statics/js/common/jquery-1.12.4.js"></script>--%>
    <script type="text/javascript" src="${ctx}/statics/js/common/jquery-1.4.2.min.js"></script>
    <script type="text/javascript" src="${ctx}/statics/js/common/menu.js"></script>
    <script type="text/javascript" src="${ctx}/statics/js/common/shade.js"></script>
    <script type="text/javascript" src="${ctx}/statics/js/common/select.js"></script>
    <script type="text/javascript" src="${ctx}/statics/js/common/lrscroll.js"></script>
    <script type="text/javascript" src="${ctx}/statics/js/common/iban.js"></script>
    <script type="text/javascript" src="${ctx}/statics/js/common/fban.js"></script>
    <script type="text/javascript" src="${ctx}/statics/js/common/f_ban.js"></script>
    <script type="text/javascript" src="${ctx}/statics/js/common/mban.js"></script>
    <script type="text/javascript" src="${ctx}/statics/js/common/bban.js"></script>
    <script type="text/javascript" src="${ctx}/statics/js/common/hban.js"></script>
    <script type="text/javascript" src="${ctx}/statics/js/common/tban.js"></script>
    <script type="text/javascript" src="${ctx}/statics/js/common/lrscroll_1.js"></script>
    <script type="text/javascript">
        //$(function() {
        ////    $("#button").click(function () {
        //        $("#submit").onclick()
        //        {
        //            var loginName = $("#loginName").val();
        //            var password = $("#password").val();
        //            $.ajax({
        //                url: "/easybuy/Login",
        //                method: "post",
        //                data: {loginName: loginName, password: password, action: "login"},
        //                dataType: "json",
        //                success: callBack
        //            })
        //            $.get("/easybuy/Login", "loginName=" + loginName + "password=" + password + "action=login", callBack);
        //
        //            function callBack(jsonStr) {
        //                if (jsonStr.status == 1) {
        //                    window.location.href = "/easybuy/Home?action=index";
        //                } else {
        //                    showMessage(jsonStr.message);
        //                }
        //            }
        //        }
        ////    })
        //})
    </script>
</head>
<body>
<!--Begin Login Begin-->
<div class="log_bg">
    <div class="top">
        <div class="logo"><a href="${ctx}/pre/Index.jsp"><img src="${ctx}/statics/images/logo.png" /></a></div>
    </div>
    <div class="login">
        <div class="log_img"><img src="${ctx}/statics/images/l_img.png" width="611" height="425" /></div>
        <div class="log_c">
            <form>
                <table border="0" style="width:370px; font-size:14px; margin-top:30px;" cellspacing="0" cellpadding="0">
                    <tr height="50" valign="top">
                        <td width="55">&nbsp;</td>
                        <td>
                            <span class="fl" style="font-size:24px;">登录</span>
                            <span class="fr">还没有商城账号，<a href="${ctx}/Register?action=toRegister" style="color:#ff4e00;">立即注册</a></span>
                        </td>
                    </tr>
                    <tr height="70">
                        <td>用户名</td>
                        <td><input type="text" id="loginName" value="" class="l_user" /></td>
                    </tr>
                    <tr height="70">
                        <td>密&nbsp; &nbsp; 码</td>
                        <td><input type="password" id="password" value="" class="l_pwd" /></td>
                    </tr>
                    <tr>
                        <td>&nbsp;</td>
                        <td style="font-size:12px; padding-top:20px;">
                	<span style="font-family:'宋体';" class="fl">
                    	<label class="r_rad"><input type="checkbox" /></label><label class="r_txt">请保存我这次的登录信息</label>
                    </span>
                            <span class="fr"><a href="#" style="color:#ff4e00;">忘记密码</a></span>
                        </td>
                    </tr>
                    <tr height="60">
                        <td>&nbsp;</td>
                        <%--<td><input type="button" value="登录" class="log_btn" id ="button"  /></td>--%>
                        <td><input  type="submit" value="登录" class="log_btn" onclick="login();"/></td>
                    </tr>
                </table>
            </form>
        </div>
    </div>
</div>
<!--End Login End-->
<!--Begin Footer Begin-->
<div class="btmbg">
    <div class="btm">
        备案/许可证编号：蜀ICP备12009302号-1-www.dingguagua.com   Copyright © 2015-2018 尤洪商城网 All Rights Reserved. 复制必究 , Technical Support: Dgg Group <br />
        <img src="${ctx}/statics/images/b_1.gif" width="98" height="33" /><img src="${ctx}/statics/images/b_2.gif" width="98" height="33" /><img src="${ctx}/statics/images/b_3.gif" width="98" height="33" /><img src="${ctx}/statics/images/b_4.gif" width="98" height="33" /><img src="${ctx}/statics/images/b_5.gif" width="98" height="33" /><img src="${ctx}/statics/images/b_6.gif" width="98" height="33" />
    </div>
</div>
</body>
</html>
