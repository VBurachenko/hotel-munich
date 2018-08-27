<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="custom-tag/footer" prefix="ftr" %>

<%@ include file="../part/locale.jsp"%>

<!DOCTYPE html>

<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=0.8">
    <title><fmt:message key="page.title"/></title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
</head>
<body>

<c:import url="../part/header.jsp"/>

<section>
    Your booking: ${sessionScope.bookingInProcess}<br>
    Your invoice: ${sessionScope.invoiceForBooking}

    <form action="${pageContext.request.contextPath}/decidePaymentIssue.do" method="post">
        <label>
            <input type="radio" name="payment" value="instant_pay" checked/> Pay now
        </label><br>
        <label>
            <input type="radio" name="payment" value="pay_in_hotel"/> Pay in hotel
        </label><br>
        <input type="hidden" name="invoiceIdForPayment" value="${sessionScope.invoiceForBooking.invoiceId}"/>
        <input type="submit" value="Finalize booking"/>
    </form>
</section>

<ftr:footer/>

</body>
</html>
