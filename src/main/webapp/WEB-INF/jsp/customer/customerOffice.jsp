<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="custom-tag/footer" prefix="ftr" %>
<%@ taglib uri="custom-tag/operation-message" prefix="op-msg"%>
<%@ taglib uri="custom-tag/blocking-message" prefix="block-msg"%>

<%@ include file="../part/locale.jsp"%>

<!DOCTYPE html>

<html>
    <head>

        <meta name="viewport" content="width=device-width, initial-scale=0.8">
        <title><fmt:message key="page.title"/></title>

        <script src="${pageContext.request.contextPath}/js/container_selector.js"></script>

        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/tab.css">
    </head>

    <body onload="autoClick(); smoothPanelBorder('tab-link', '6px')">

        <c:import url="../part/header.jsp"/>

        <section>

            <div class="tab-container">

                <div class="warn-message">
                    <op-msg:operationMessage messageCode="3" textMessage="Change this booking is impossible"/>
                    <op-msg:operationMessage messageCode="4" textMessage="Booking was successfully changed"/>
                </div>

                <c:if test="${sessionScope.blocking ne 0}">
                    <div class="block-message">
                        <block-msg:userBlock reasonCode="1" textOfReason="you had damage hotel property"/>
                        <block-msg:userBlock reasonCode="2" textOfReason="you have violated the rules of accommodation in the hotel"/>
                        <block-msg:userBlock reasonCode="3" textOfReason="you have problems with payment"/>
                    </div>
                </c:if>

                <div class="link-panel">

                    <button id="defaultOpen" class="tab-link" onclick="openTable('info', this)" >
                        Person
                    </button>
                    <button class="tab-link" onclick="openTable('bookings', this)">
                        Bookings
                    </button>
                    <button class="tab-link" onclick="openTable('invoices', this)">
                        Invoices
                    </button>

                </div>

                <div id="info" class="tab-content">
                    <table border="1px">
                        <tr>
                            <td class="label-cell">Email</td>
                            <td class="content-cell">${sessionScope.email}</td>
                        </tr>
                        <tr>
                            <td class="label-cell">First name</td>
                            <td class="content-cell">${sessionScope.firstName}</td>
                        </tr>
                        <tr>
                            <td class="label-cell">Last name</td>
                            <td class="content-cell">${sessionScope.lastName}</td>
                        </tr>
                        <tr>
                            <td class="label-cell">Birthday</td>
                            <td class="content-cell">${sessionScope.birthday}</td>
                        </tr>
                        <tr>
                            <td class="label-cell">Tel. number</td>
                            <td class="content-cell">${sessionScope.telephoneNumber}</td>
                        </tr>
                        <tr>
                            <td class="label-cell">Gender</td>
                            <td class="content-cell">
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
                                                      method="post">
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

            </div>

        </section>

    <ftr:footer/>

    </body>

</html>
