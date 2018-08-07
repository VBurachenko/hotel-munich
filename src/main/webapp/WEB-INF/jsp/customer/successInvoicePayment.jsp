<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../part/locale.jsp"%>

<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=0.8">
    <title><fmt:message key="page.title"/></title>
    <script src="../../js/password.js"></script>
    <script src="../../js/navigation_bar.js"></script>
    <link rel="stylesheet" href="../../css/navigation_bar.css">
    <script src="../../../js/container_selector.js"></script>
    <link rel="stylesheet" href="../../../css/tables-content.css">
    <link rel="stylesheet" href="../../../css/tabs.css">
</head>
<body>
<br/>
<header>
    <%@include file="../part/header.jsp"%>
</header>
    Your booking is absolutely completed. Check for details in your
    <a href="${pageContext.request.contextPath}/openUserOffice.do">account</a>. In case of discrepancies, please contact with support.
</body>
</html>
