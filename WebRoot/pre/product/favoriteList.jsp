<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%@page  isELIgnored="false" %>
<script type="text/javascript">
    var contextPath = "${ctx}";
</script>
<c:if test="${favoriteList!=null}">
    <div class="l_history">
    <div class="fav_t">我的收藏</div>
    <c:forEach items="${favoriteList}" var="temp">
        <ul>
            <li>
                <div class="img">
                    <a href="${ctx}/Product?action=queryProductDeatil&id=${temp.id}">
                        <img src="${ctx}/files/${temp.fileName}" width="185" height="162"/>
                    </a>
                </div>
                <div class="name">
                    <a href="${ctx}/Product?action=queryProductDeatil&id=${temp.id}">${temp.name}</a>
                </div>
                <div class="price">
                    <font>￥<span>${temp.price}</span></font> &nbsp;
                </div>
            </li>
        </ul>
    </c:forEach>
</c:if>
</div>
