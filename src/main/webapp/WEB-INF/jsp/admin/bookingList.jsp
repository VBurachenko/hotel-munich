<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ include file="../part/locale.jsp" %>

<html>

<head>
    <meta name="viewport" content="width=device-width, initial-scale=0.8">
    <title><fmt:message key="page.title"/></title>
    <script src="${pageContext.request.contextPath}/js/navigation_bar.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/navigation_bar.css">
    <script src="${pageContext.request.contextPath}/js/paginator.js"></script>
    <script src="${pageContext.request.contextPath}/js/container_selector.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/tables-content.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/tabs.css">
</head>

<body>

<br/>
<header>
    <%@include file="../part/header.jsp" %>
</header>
<br/>
bookingList
<br>
<form action="${pageContext.request.contextPath}/listUsersShow.do" method="post">
    <input type="submit" value="Go to users List">
</form>
<form action="${pageContext.request.contextPath}/listRoomsShow.do" method="post">
    <input type="submit" value="Go to rooms List">
</form>

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
        User with id ${requestScope.changedBookingId}
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
                    <td>${booking.invoiceId}</td>
                    <td>${booking.userId}</td>
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
            <nav>
                <ul class="pagination">
                    <c:if test="${currentPage ne 1}">
                        <li>
                            <a href="JavaScript:changePage(${currentPage - 1})">Previous</a>
                        </li>
                    </c:if>
                    <c:forEach begin="1" end="${pagesCount}" var="i">
                        <c:choose>
                            <c:when test="${currentPage eq i}">
                                <li>
                                    <a><span>${i}</span></a>
                                </li>
                            </c:when>
                            <c:otherwise>
                                <li>
                                    <a href="JavaScript:changePage(${i})">${i}</a>
                                </li>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                    <c:if test="${currentPage lt pagesCount}">
                        <li>
                            <a href="JavaScript:changePage(${currentPage + 1})">Next</a>
                        </li>
                    </c:if>
                </ul>
                <form action="${pageContext.request.contextPath}/listBookingsShow.do" method="post" name="form1"
                      id="changePage">
                    <input type="hidden" name="page"/>
                </form>
            </nav>
        </c:if>
    </div>
</c:if>

</body>

</html>
