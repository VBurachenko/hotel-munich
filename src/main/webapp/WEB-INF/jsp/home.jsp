<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="part/locale.jsp"%>

<html>

<head>
    <meta name="viewport" content="width=device-width, initial-scale=0.8">
    <title><fmt:message key="page.title"/></title>
    <script src="../../js/password.js"></script>
    <script src="../../js/navigation_bar.js"></script>
    <link rel="stylesheet" href="../../css/navigation_bar.css">

</head>

<body>
<br/>
<header>
    <%@ include file="part/header.jsp"%>
</header>
<br/>
<section>
    <div class="content">
        <fmt:message key="page.home.greeting"/>
    </div>
</section>

</body>
</html>