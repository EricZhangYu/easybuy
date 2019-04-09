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
      <div class="mem_tit">分类列表</div>
      <p align="right">
        <a href="javascript:void(0);" onclick="toAddProductCategory();" class="add_b">添加分类</a>
        <br>
      </p>
      <br>
      <table border="0" class="order_tab" style="width:930px; text-align:center; margin-bottom:30px;" cellspacing="0" cellpadding="0">
        <tbody>
        <tr>
          <td width="20%">选择</td>
          <td width="25%">分类名称</td>
          <td width="15%">分类级别</td>
          <td width="25%">父级分类</td>
          <td width="15%" >操作</td>
        </tr>
        <c:forEach items="${productCategoryList}" var="temp">
          <tr>
            <td width="5%"><input type="radio" value="${temp.id}" name="select" onclick="toUpdateProductCategoryList(this);"/></td>
            <td>${temp.name}</td>
            <td>
            <c:choose>
               <c:when test="${temp.type==1}">一级分类</c:when>
               <c:when test="${temp.type==2}">二级分类</c:when>
               <c:when test="${temp.type==3}">三级分类</c:when>
            </c:choose>
            </td>
            <td>
            <c:if test="${empty temp.parentName}">
            	无
            </c:if>
            <c:if test="${not empty temp.parentName}">
            	${temp.parentName}
            </c:if>
            </td>
            <td><a href="#" onclick="deleteProductCategory('${temp.id}');">删除</a></td>
          </tr>
        </c:forEach>
        </tbody>
      </table>
      <%@ include file="/common/pre/pagerBar.jsp" %>
      <div id="addProductCategory">

      </div>
    </div>
  </div>
  <%@ include file="/common/pre/footer.jsp" %>
</div>
</body>
</html>


