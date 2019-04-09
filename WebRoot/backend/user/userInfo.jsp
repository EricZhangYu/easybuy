<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <%@ include file="/common/pre/header.jsp" %>
    <script src="${ctx}/statics/js/backend/backend.js"></script>
</head>
<body>
<%@ include file="/common/backend/searchBar.jsp" %>
<!--End Header End-->
<div class="i_bg bg_color">
    <!--Begin 用户中心 Begin -->
    <div class="m_content">
        <%@ include file="/common/backend/leftBar.jsp"%>
        <div class="m_right" id="content">
            <div class="m_des">
                <table border="0" style="width:870px; line-height:22px;" cellspacing="0" cellpadding="0">
                    <tr valign="top">
                        <td width="115"><img src="${ctx}/statics/images/user.jpg" width="90" height="90" /></td>
                        <td>
                            <div class="m_user">${sessionScope.loginUser.userName}</div><br />
                            <p>
                                性别:
                                <c:choose>
                                    <c:when test="${sessionScope.loginUser.sex==1}">男</c:when>
                                    <c:otherwise>女</c:otherwise>
                                </c:choose>
                                <br /><br />
                                邮箱:${sessionScope.loginUser.email}<br /><br />
                                电话:${sessionScope.loginUser.mobile}<br /><br />
                            </p>
                        </td>
                    </tr>
                </table>
            </div>
        </div>
    </div>
    <%@ include file="/common/pre/footer.jsp" %>
</div>
</body>
</html>

















