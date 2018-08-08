<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ include file="../part/locale.jsp" %>
<!DOCTYPE HTML>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=0.8">
    <title><fmt:message key="page.title"/></title>
    <script src="${pageContext.request.contextPath}/js/password.js"></script>
    <script src="${pageContext.request.contextPath}/js/navigation_bar.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/navigation_bar.css">
    <title><fmt:message key="page.title"/></title>
</head>
<body>
<br/>
<header>
    <%@include file="../part/header.jsp" %>
</header>
<br/>
<c:set var="booking" value="${sessionScope.bookingInProcess}" scope="page"/>
<c:set var="invoice" value="${sessionScope.invoiceForBooking}" scope="page"/>
<div>
    Booking in process : ${booking}<br>
    <p>${booking.roomsSet}</p>
    Invoice For this booking : ${invoice}<br>
</div> <br>
<div>
    <form action="${pageContext.request.contextPath}/performBookingProcessing.do" method="post">
        <hr>
        <div> Select status for booking:<br>
            <c:if test="${booking.bookingStatus eq 'REGISTERED'}">
                <label>CONFIRM
                    <input type="radio" name="bookingStatus" value="CONFIRMED" checked/>
                </label><br>
                <label>PERFORM
                    <input type="radio" name="bookingStatus" value="PERFORMING"/>
                </label><br>
            </c:if>

            <c:if test="${booking.bookingStatus eq 'CONFIRMED'}">
                <label>PERFORMING
                    <input type="radio" name="bookingStatus" value="PERFORMING" checked/>
                </label>
                <label>COMPLETE
                    <input type="radio" name="bookingStatus" value="COMPLETED"/>
                </label>
            </c:if>

            <c:if test="${booking.bookingStatus eq 'PERFORMING'}">
                <label>COMPLETE
                    <input type="radio" name="bookingStatus" value="COMPLETED"/>
                </label>
            </c:if>

            <c:if test="${booking.bookingStatus ne 'COMPLETED'}">
                <label>REJECT
                    <input type="radio" name="bookingStatus" value="REJECTED"/>
                </label>
            </c:if>

            <c:if test="${booking.bookingStatus eq 'REJECTED'}">
                <label>CONFIRM BACK
                    <input type="radio" name="bookingStatus" value="CONFIRMED"/>
                </label>
            </c:if>

        </div>

        <br>
        <hr>

        <div>
            <c:choose>
                <c:when test="${invoice.isPayed eq 'false'}">
                    <label>Register invoice payment
                        <input type="checkbox" name="isPayed" value="true"/>
                    </label>
                </c:when>
                <c:otherwise>
                    <label>
                        Invoice payed
                    </label>
                </c:otherwise>
            </c:choose>
        </div>

        <input type="submit" value="perform">
    </form>
</div>


</body>
</html>

