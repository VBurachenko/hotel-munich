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

    Your booking: ${sessionScope.bookingInProcess}<br>
    Your invoice: ${sessionScope.invoiceForBooking}

    <form action="${pageContext.request.contextPath}/decidePaymentIssue.do" method="post">
        <label>
            <input type="radio" name="payment" value="instant_pay" checked/> Pay now
        </label><br>
        <label>
            <input type="radio" name="payment" value="pay_in_hotel"/> Pay in hotel
        </label><br>
        <%--<c:set var="invoiceIdForPayment" value="${sessionScope.invoiceForBooking.invoiceId}" scope="session"/>--%>
        <input type="hidden" name="invoiceIdForPayment" value="${sessionScope.invoiceForBooking.invoiceId}"/>
        <input type="submit" value="Finalize booking"/>
    </form>
</body>
</html>
