<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="ctg" uri="custom-tag/footer" %>
<%@ include file="part/locale.jsp"%>

<html>

<head>
    <meta name="viewport" content="width=device-width, initial-scale=0.8">
    <title><fmt:message key="page.title"/></title>
    <script src="${pageContext.request.contextPath}/js/navigation_bar.js"></script>
    <%--<link rel="stylesheet" href="${pageContext.request.contextPath}/css/navigation_bar.css">--%>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
</head>
<body style="background: url('${pageContext.request.contextPath}/pic/Munich-München.jpg');
        background-attachment: scroll;
        background-position: center;
        background-repeat: no-repeat;
        background-size: cover;">

<header>
    <%@ include file="part/header.jsp"%>
</header>


<br/>

<section>
    <div class="content">
        <fmt:message key="page.home.greeting"/>
    </div>
</section>

<ctg:footer/>

</body>
</html>