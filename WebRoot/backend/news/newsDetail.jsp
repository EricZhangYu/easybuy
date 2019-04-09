<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <%@ include file="/common/pre/header.jsp" %>
    <link type="text/css" rel="stylesheet" href="${ctx}/statics/css/style.css" />
</head>
<body>
<%@ include file="/common/backend/searchBar.jsp" %>
<div class="i_bg bg_color">
    <!--Begin 用户中心 Begin -->
	<div class="m_content">
        <%@ include file="/common/backend/leftBar.jsp"%>
		<div class="m_right">
            <p></p>
            <div class="mem_tit">${news.title}</div>
            <p style="padding:0 40px; margin:0 auto 20px auto;">
                ${news.content}
            </p>
        </div>
    </div>
	<!--End 用户中心 End--> 
    <!--Begin Footer Begin -->
    <%@ include file="/common/pre/footer.jsp" %>
    <!--End Footer End -->    
</div>
</body>
</html>
