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

        <script src="${pageContext.request.contextPath}/js/container_selector.js"></script>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/tables-content.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/tabs.css">

        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
    </head>
    <body>

        <c:import url="../part/header.jsp"/>

        <section>
            <h2>Customer office</h2>

            <p>Hello ${sessionScope.firstName}</p>

            <c:if test="${sessionScope.changeBookingMessage eq 3}">
                <p>Change this booking is impossible</p>
            </c:if>
            <c:if test="${sessionScope.changeBookingMessage eq 4}">
                <p>Booking was successfully changed</p>
            </c:if>

            <div>
                <button class="button tablink" onclick="openTab(event, 'info')" id="defaultOpen">
                    My personal Data
                </button>
                <button class="button tablink" onclick="openTab(event, 'bookings')">
                    My bookings
                </button>
                <button class="button tablink" onclick="openTab(event, 'invoices')">
                    My invoices
                </button>
            </div>

            <div id="info" class="tab-content">
                <table>
                    <tr>
                        <td>Email</td>
                        <td>${sessionScope.email}</td>
                    </tr>
                    <tr>
                        <td>First name</td>
                        <td>${sessionScope.firstName}</td>
                    </tr>
                    <tr>
                        <td>Last name</td>
                        <td>${sessionScope.lastName}</td>
                    </tr>
                    <tr>
                        <td>Birthday</td>
                        <td>${sessionScope.birthday}</td>
                    </tr>
                    <tr>
                        <td>Tel. number</td>
                        <td>${sessionScope.telephoneNumber}</td>
                    </tr>
                    <tr>
                        <td>Gender</td>
                        <td>
                            <c:choose>
                                <c:when test="${sessionScope.genderMale}">
                                    Male
                                </c:when>
                                <c:otherwise>
                                    Female
                                </c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                </table>
            </div>

            <div id="bookings" class="tab-content">
                <table border="1">
                    <thead>
                    <c:if test="${not empty requestScope.usersBookingSet}">
                        <tr>
                            <td>bookingId</td>
                            <td>checkIn</td>
                            <td>checkOut</td>
                            <td>adultCount</td>
                            <td>childCount</td>
                            <td>invoiceId</td>
                            <td>bookingStatus</td>
                            <td>rooms</td>
                        </tr>
                    </c:if>
                    </thead>

                    <tbody>
                    <c:choose>
                        <c:when test="${not empty requestScope.usersBookingSet}">
                            <c:forEach var="currentBooking" items="${requestScope.usersBookingSet}">
                                <tr>
                                    <td>${currentBooking.bookingId}</td>
                                    <td>${currentBooking.checkInDate}</td>
                                    <td>${currentBooking.checkOutDate}</td>
                                    <td>${currentBooking.adultCount}</td>
                                    <td>${currentBooking.childCount}</td>
                                    <td>${currentBooking.invoiceId}</td>
                                    <td>${currentBooking.bookingStatus}
                                        <c:if test="${currentBooking.bookingStatus eq 'REGISTERED'}">
                                            <form action="${pageContext.request.contextPath}/openChangeForm.do"
                                                  method="get">
                                                <input type="hidden" name="bookingId"
                                                       value="${currentBooking.bookingId}"/>
                                                <input type="submit" value="change booking"/>
                                            </form>
                                            <form action="${pageContext.request.contextPath}/cancelBooking.do"
                                                  method="post">
                                                <input type="hidden" name="cancelBookingId"
                                                       value="${currentBooking.bookingId}"/>
                                                <input type="hidden" name="cancelInvoiceId"
                                                       value="${currentBooking.invoiceId}"/>
                                                <input type="submit" value="cancel"/>
                                            </form>
                                        </c:if>
                                    </td>
                                    <td>
                                        <c:forEach var="room" items="${currentBooking.roomsSet}">
                                            ${room}
                                        </c:forEach>
                                    </td>
                                </tr>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            You have not bookings
                        </c:otherwise>
                    </c:choose>
                    </tbody>
                </table>
            </div>
            <div id="invoices" class="tab-content">
                <table border="1">
                    <thead>
                    <c:if test="${not empty requestScope.usersInvoiceSet}">
                        <tr>
                            <td>invoiceId</td>
                            <td>invoiceDate</td>
                            <td>nightsCount</td>
                            <td>totalPayment</td>
                            <td>invoiceStatus</td>
                            <td>payment</td>
                        </tr>
                    </c:if>
                    </thead>

                    <tbody>
                    <c:choose>
                        <c:when test="${not empty requestScope.usersInvoiceSet}">
                            <c:forEach var="currentInvoice" items="${requestScope.usersInvoiceSet}">
                                <tr>
                                    <td>${currentInvoice.invoiceId}</td>
                                    <td>${currentInvoice.invoiceDate}</td>
                                    <td>${currentInvoice.nightsCount}</td>
                                    <td>${currentInvoice.totalPayment}</td>
                                    <td>${currentInvoice.invoiceStatus}
                                        <c:if test="${currentInvoice.invoiceStatus eq 'PAY_IN_HOTEL' and currentInvoice.isPayed eq 'false'}">
                                            <form action="${pageContext.request.contextPath}/payInvoice.do"
                                                  method="post">
                                                <input type="hidden" value="${currentInvoice.invoiceId}"
                                                       name="invoiceIdForPayment"/>
                                                <input type="submit" value="pay now"/>
                                            </form>
                                        </c:if>
                                    </td>
                                    <td>
                                        <c:if test="${currentInvoice.isPayed eq 'true'}">
                                            Payed
                                        </c:if>
                                        <c:if test="${currentInvoice.isPayed eq 'false'}">
                                            Not payed
                                        </c:if>
                                    </td>

                                </tr>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            You have not invoices
                        </c:otherwise>
                    </c:choose>

                    </tbody>
                </table>
            </div>
            <form action="${pageContext.request.contextPath}/logout.do" method="post" id="logout">
                <fmt:message key="page.btn.logout" var="exit"/>
                <input type="submit" value="${exit}"/>
            </form>
            <form action="${pageContext.request.contextPath}/openCart.do" method="post" id="cart">
                <fmt:message key="page.btn.cart" var="cartOpen"/>
                <input type="submit" value="${cartOpen}"/>
            </form>
        </section>

    <ftr:footer/>

    </body>

</html>
