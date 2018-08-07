<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ include file="part/locale.jsp" %>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=0.8">
    <title><fmt:message key="page.title"/></title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/rooms_list.css">
    <script src="../../js/navigation_bar.js"></script>
    <script src="${pageContext.request.contextPath}/js/paginator.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/navigation_bar.css">
</head>
<body>
<br/>
<header>
    <%@include file="part/header.jsp" %>
</header>
<br/>

<div>
    <fmt:message key="search.criteria"/>:
    <p><fmt:message key="search.checkin"/>: ${sessionScope.searchUnit.from}</p>
    <p><fmt:message key="search.checkout"/>: ${sessionScope.searchUnit.to}</p>
    <p><fmt:message key="search.count.adult"/>: ${sessionScope.searchUnit.adultCount}</p>
    <p><fmt:message key="search.count.child"/>: ${sessionScope.searchUnit.childCount}</p>
    <p><fmt:message key="search.comfortlevel"/>: ${sessionScope.searchUnit.comfortLevel}</p>
    <form action="${pageContext.request.contextPath}/searchFreeRooms.do">
        <fmt:message key="search.change" var="change"/>
        <input type="submit" value="${change}">
    </form>
</div>

<c:set var="currentPage" value="${sessionScope.page}" scope="page"/>
<c:set var="pagesCount" value="${sessionScope.roomsForDisplay.pagesCount}" scope="page"/>

<c:forEach var="room" items="${sessionScope.roomsForDisplay.entityList}">
    <div class="container">
        <img src="${room.pictureLink}" alt="Photo of room">
        <p><span>Room number: ${room.roomNumber}</span> Count of berth : ${room.berthCount}</p>
        <p>Price per night: ${room.pricePerNight}</p>
        <div>
            <fmt:message key="page.label.forOrder" var="forOrder"/>
            <c:choose>
                <c:when test="${empty sessionScope.searchUnit.from or empty sessionScope.searchUnit.to}">
                    <form action="${pageContext.request.contextPath}/searchFreeRooms.do">
                        <fmt:message key="page.btn.fillSearchForm" var="fillSearchForm"/>
                        <label>
                                ${forOrder} <input type="submit" value="${fillSearchForm}"/>
                        </label>
                    </form>
                </c:when>
                <c:otherwise>
                    <c:choose>
                        <c:when test="${sessionScope.role ne 'GUEST'}">
                            <form action="${pageContext.request.contextPath}/putRoomInCart.do" method="post">
                                <input type="hidden" name="roomNumber" value="${room.roomNumber}"/>
                                <input type="submit" value="Add to cart"/>
                            </form>
                            <form action="${pageContext.request.contextPath}/singleRoomBooking.do" method="post">
                                <input type="hidden" name="roomNumber" value="${room.roomNumber}"/>
                                <input type="submit" value="Booking room"/>
                            </form>
                        </c:when>
                        <c:otherwise>
                            <form action="${pageContext.request.contextPath}/login.do" method="post">
                                <fmt:message key="page.btn.loginForOrder" var="loginForOrder"/>
                                <label>
                                        ${forOrder} <input type="submit" value="${loginForOrder}"/>
                                </label>
                            </form>
                        </c:otherwise>
                    </c:choose>
                </c:otherwise>
            </c:choose>

        </div>

    </div>
</c:forEach>

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
    <form action="${pageContext.request.contextPath}/prepareForSearch.do" method="post" name="form1" id="changePage">
        <input type="hidden" name="page"/>
        <input type="hidden" name="check_in" value="${sessionScope.searchUnit.from}"/>
        <input type="hidden" name="check_out" value="${sessionScope.searchUnit.to}"/>
        <input type="hidden" name="comfLevel" value="${sessionScope.searchUnit.comfortLevel}"/>
        <input type="hidden" name="adultCount" value="${sessionScope.searchUnit.adultCount}"/>
        <input type="hidden" name="childCount" value="${sessionScope.searchUnit.childCount}"/>
    </form>
</nav>
</body>
</html>
