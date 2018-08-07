<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ include file="../part/locale.jsp" %>

<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=0.8">
    <title><fmt:message key="page.title"/></title>
    <script src="../../js/password.js"></script>
    <script src="../../js/navigation_bar.js"></script>
    <link rel="stylesheet" href="../../css/navigation_bar.css">
    <link rel="stylesheet" href="../../css/rooms_list.css">
</head>
<body>
<br/>
    <header>
        <%@include file="../part/header.jsp" %>
    </header>
    <br/>
    <c:choose>
        <c:when test="${sessionScope.orderInCart.roomsSet eq null}">
            <p>Basket is empty</p>
        </c:when>
        <c:otherwise>
            <form action="${pageContext.request.contextPath}/displayFreeRooms.do" method="post">
                <input type="hidden" name="page" value="${sessionScope.Page}"/>
                <input type="submit" value="Go back to search"/>
            </form>
            <form action="${pageContext.request.contextPath}/bookingSelectedRooms.do" method="post" id="bookingSelectedRooms">

                <input type="submit" value="Order selected rooms">

                <c:forEach var="roomInCart" items="${sessionScope.orderInCart.roomsSet}">
                    <div class="container">
                        <input type="checkbox" checked name="selectedRooms" value="${roomInCart.roomNumber}"/>
                        <img src="${roomInCart.pictureLink}" alt="Photo of room">
                        <p><span>Room number: ${roomInCart.roomNumber}</span> Count of berth : ${roomInCart.berthCount} Comfort level : ${roomInCart.comfortLevel}</p>
                        <p>Price per night: ${roomInCart.pricePerNight}</p>
                    </div>
                </c:forEach>
            </form>
            <button type="submit" form="bookingSelectedRooms" >Order selected rooms</button>
        </c:otherwise>
    </c:choose>


</body>
</html>
