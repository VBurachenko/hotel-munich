<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="custom-tag/footer" prefix="ftr" %>
<%@ taglib uri="custom-tag/paginator" prefix="pgr"%>
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
    <c:if test="${requestScope.bookingOperationMessage eq 6}">
        Booking status was not changed.
    </c:if>
    <c:if test="${requestScope.bookingOperationMessage eq 10}">
        No such booking existing.
    </c:if>
    <c:if test="${requestScope.bookingOperationMessage eq 12}">
        Booking was not processed.
    </c:if>

    <c:if test="${not empty requestScope.changedBookingId}">
        Booking with id ${requestScope.changedBookingId}
        <c:choose>
            <c:when test="${requestScope.changedBookingStatus eq 'true'}">
                was blocked.
            </c:when>
            <c:otherwise>
                was unblocked.
            </c:otherwise>
        </c:choose>
    </c:if>
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
