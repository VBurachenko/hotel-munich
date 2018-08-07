<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../part/locale.jsp"%>

<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=0.8">
    <title><fmt:message key="page.title"/></title>
    <script src="${pageContext.request.contextPath}/js/password.js"></script>
    <script src="${pageContext.request.contextPath}/js/navigation_bar.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/navigation_bar.css">
    <script src="${pageContext.request.contextPath}/js/container_selector.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/tables-content.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/tabs.css">
</head>
<body>
<br/>
<header>
    <%@include file="../part/header.jsp"%>
</header>
    Payment page <br>
        ${sessionScope.invoiceForPayment}

    <form action="${pageContext.request.contextPath}/paymentPerform.do" method="post">
        <label>Insert total sum payment:
            <input type="text" name="totalAmount"/>
        </label>
        <input type="hidden" name="invoiceIdForPayment" value="${sessionScope.invoiceForPayment.invoiceId}"/>

        <input type="submit" value="pay"/>
    </form>
</body>
</html>
