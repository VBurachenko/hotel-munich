<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../part/locale.jsp"%>
<c:set var="locale" value="${sessionScope.localLang}"/>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=0.8">
    <title><fmt:message key="page.title"/></title>
    <script src="../../../js/navigation_bar.js"></script>
    <link rel="stylesheet" href="../../../css/navigation_bar.css">
    <script src="../../../js/container_selector.js"></script>
    <link rel="stylesheet" href="../../../css/tables-content.css">
    <link rel="stylesheet" href="../../../css/tabs.css">
    <%--<link rel="stylesheet" href="/resources/demos/style.css">--%>
    <%--<link rel="stylesheet" href="../../css/jquery-ui.css">--%>
    <%--<script src="../../js/jquery.js"></script>--%>
    <%--<script src="../../js/jquery-ui.js"></script>--%>
    <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
    <%--<link rel="stylesheet" href="/resources/demos/style.css">--%>
    <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
    <script src="../../../js/locale/datepicker_de.js"></script>
    <script src="../../../js/locale/datepicker_ru.js"></script>
    <script src="../../../js/locale/datepicker_by.js"></script>
    <script src="../../../js/locale/datepicker_en.js"></script>
    <script src="${pageContext.request.contextPath}/js/dates_picker.js"></script>
</head>
<body>
<br/>
<header>
    <%@include file="../part/header.jsp"%>
</header>
    <c:set var="changedBooking" value="${requestScope.bookingForChange}" scope="page"/>
    <form action="${pageContext.request.contextPath}/performBookingChange.do" method="post">
        <label>Booking id:
            <input type="text" name="bookingId" value="${requestScope.bookingForChange.bookingId}" readonly/>
        </label>
        <br>

        <label>Check-In-Date:
            <input type="text" name="check_in" id="check_in" value="${requestScope.bookingForChange.checkInDate}"/>
        </label>
        <br>

        <label>Check-Out-Date:
            <input type="text" name="check_out" id="check_out" value="${requestScope.bookingForChange.checkOutDate}"/>
        </label>
        <br>

        <label>Adults count:
            <input type="text" name="adultCount" value="${requestScope.bookingForChange.adultCount}"/>
        </label>
        <br>

        <label>Child count:
            <input type="text" name="childCount" value="${requestScope.bookingForChange.childCount}"/>
        </label>
        <br>

        <label>Invoice id:
            <input type="text" name="invoiceId" value="${requestScope.bookingForChange.invoiceId}" readonly/>
        </label>
        <br>

        <input type="hidden" name="bookingStatus" value="${requestScope.bookingForChange.bookingStatus}" readonly/>
        <br>

       <input type="hidden" name="userId" value="${requestScope.bookingForChange.userId}"/>
        <br>

        <c:forEach var="roomInBooking" items="${requestScope.bookingForChange.roomsSet}">
            <div class="container">
                <label>Keep in booking
                    <input type="checkbox" checked name="selectedRooms" value="${roomInBooking.roomNumber}"/>
                </label>
                <br>
                <img src="${roomInBooking.pictureLink}" alt="Photo of room">
                <p><span>Room number: ${roomInBooking.roomNumber}</span> Count of berth : ${roomInBooking.berthCount} Comfort level : ${roomInBooking.comfortLevel}</p>
                <p>Price per night: ${roomInBooking.pricePerNight}</p>
            </div>
        </c:forEach>

        <input type="submit" value="Change"/>
    </form>
</body>
</html>
