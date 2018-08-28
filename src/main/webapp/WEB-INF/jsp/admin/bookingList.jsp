<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="custom-tag/footer" prefix="ftr" %>
<%@ taglib uri="custom-tag/paginator" prefix="pgr"%>
<%@ taglib uri="custom-tag/operation-message" prefix="op-msg"%>
<%@ include file="../part/locale.jsp" %>

<!DOCTYPE html>

<html>

<head>
    <meta name="viewport" content="width=device-width, initial-scale=0.8">
    <title><fmt:message key="page.title"/></title>

    <script src="${pageContext.request.contextPath}/js/paginator.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/pagination.css">
</head>

<body>

<c:import url="../part/header.jsp"/>

bookingList
<br>

<form action="${pageContext.request.contextPath}/provideBookingForView.do" method="get">
    <label>Search booking by booking id:
        <input type="text" name="bookingId"/>
    </label>
    <input type="submit" value="search"/>
</form>

<c:set var="currentPage" value="${requestScope.page}" scope="page"/>
<c:set var="pagesCount" value="${requestScope.bookingsForView.pagesCount}" scope="page"/>
<br>
<div>

    <div>
        <op-msg:operationMessage messageCode="12" textMessage="Booking status was not changed."/>
        <op-msg:operationMessage messageCode="10" textMessage="No such booking existing."/>
        <op-msg:operationMessage messageCode="11" textMessage="Booking was not processed."/>
    </div>

</div>
<c:if test="${not empty requestScope.bookingsForView}">
    <div>
        <table border="1">
            <thead>
            <tr>
                <td>BookingId</td>
                <td>Check-In-Date</td>
                <td>Check-Out-Date</td>
                <td>AdultCount</td>
                <td>ChildCount</td>
                <td>InvoiceId</td>
                <td>UserId</td>
                <td>BookingStatus</td>
                <td>RoomsSet</td>
                <td>Perform action</td>
            </tr>
            </thead>

            <tbody>
            <c:forEach var="booking" items="${requestScope.bookingsForView.entityList}">
                <tr>
                    <td>${booking.bookingId}</td>
                    <td>${booking.checkInDate}</td>
                    <td>${booking.checkOutDate}</td>
                    <td>${booking.adultCount}</td>
                    <td>${booking.childCount}</td>
                    <td>
                        <a href="${pageContext.request.contextPath}/provideInvoiceForView.do?invoiceId=${booking.invoiceId}">
                                ${booking.invoiceId}
                        </a>
                    </td>
                    <td>
                        <a href="${pageContext.request.contextPath}/provideUserForView.do?searchUserArtifact=${booking.userId}">
                                ${booking.userId}
                        </a>
                    </td>
                    <td>${booking.bookingStatus}</td>
                    <td>
                        <c:forEach var="room" items="${booking.roomsSet}">
                            <p>${room}</p>
                        </c:forEach>
                    </td>
                    <td>
                        <form id="processBookingAndInvoice" action="${pageContext.request.contextPath}/prepareForBookingProcess.do" method="post">
                            <input type="hidden" name="bookingId" value="${booking.bookingId}">
                            <input type="submit" value="process"/>
                        </form>
                        <form action="${pageContext.request.contextPath}/cancelBooking.do" method="post">
                            <input type="hidden" name="cancelBookingId" value="${booking.bookingId}"/>
                            <input type="hidden" name="cancelInvoiceId" value="${booking.invoiceId}"/>
                            <input type="submit" value="cancel"/>
                        </form>
                    </td>
                </tr>
            </c:forEach>
            </tbody>

        </table>
        <c:if test="${pagesCount ne '1'}">
            <div class="pagination-container">
                <div class="pagination">
                    <pgr:navPages currentPage="${currentPage}"
                                  pagesCount="${pagesCount}"
                                  urlPattern="/listBookingsView.do"
                                  previous="&laquo;"
                                  next="&raquo;"/>
                </div>
            </div>
        </c:if>
    </div>
</c:if>

<ftr:footer/>

</body>

</html>
