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
Rooms list
<br>
<form action="${pageContext.request.contextPath}/listUsersShow.do" method="post">
    <input type="submit" value="Go to users List">
</form>
<form action="${pageContext.request.contextPath}/listBookingsShow.do" method="post">
    <input type="submit" value="Go to bookings List">
</form>

<form action="${pageContext.request.contextPath}/provideRoomForView.do" method="get">
    <label>Insert room number:
        <input type="text" name="roomNumber"/>
    </label>
    <input type="submit" value="search"/>
</form>

<form action="${pageContext.request.contextPath}/addNewRoom.do" method="get">
    <input type="submit" value="Add new room"/>
</form>

<c:set var="currentPage" value="${requestScope.page}" scope="page"/>
<c:set var="pagesCount" value="${requestScope.roomsForView.pagesCount}" scope="page"/>
<br>
<div>
    <c:if test="${requestScope.roomOperationMessage eq 6}">
        Room was not disabled.
    </c:if>
    <c:if test="${requestScope.roomOperationMessage eq 8}">
        No such room existing.
    </c:if>

    <c:if test="${not empty requestScope.disabledRoomNumber}">
        Room with number ${requestScope.disabledRoomNumber}
        <c:choose>
            <c:when test="${requestScope.blockDown eq 'true'}">
                became able.
            </c:when>
            <c:otherwise>
                disabled.
            </c:otherwise>
        </c:choose>
    </c:if>
</div>
<c:if test="${not empty requestScope.roomsForView}">
    <div>
        <table border="1">
            <thead>
            <tr>
                <td>Room Number</td>
                <td>Berth Count</td>
                <td>Comfort Level</td>
                <td>Price per Night</td>
                <td>Picture Link</td>
                <td>Available Status</td>
                <td>Perform Action</td>

            </tr>
            </thead>

            <tbody>
            <c:forEach var="room" items="${requestScope.roomsForView.entityList}">
                <tr>
                    <td>${room.roomNumber}</td>
                    <td>${room.berthCount}</td>
                    <td>${room.comfortLevel}</td>
                    <td>${room.pricePerNight}</td>
                    <td>${room.pictureLink}</td>

                    <td>
                        <c:choose>
                            <c:when test="${room.availableStatus eq 'true'}">
                                Is available
                                <form id="disable" action="${pageContext.request.contextPath}/roomBlockingControl.do"
                                      method="post">
                                    <input type="hidden" name="roomNumber" value="${room.roomNumber}"/>
                                    <input type="hidden" name="blockDown" value="false"/>
                                    <input type="submit" value="Disable"/>
                                </form>
                            </c:when>
                            <c:otherwise>
                                NOT available
                                <form id="makeAble" action="${pageContext.request.contextPath}/roomBlockingControl.do"
                                      method="post">
                                    <input type="hidden" name="roomNumber" value="${room.roomNumber}"/>
                                    <input type="hidden" name="blockDown" value="true"/>
                                    <input type="submit" value="Make Able"/>
                                </form>
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td>
                        <form id="changeRoom">

                        </form>
                        <form id="deleteRoom">

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
                <form action="${pageContext.request.contextPath}/listRoomsShow.do" method="post" name="form1"
                      id="changePage">
                    <input type="hidden" name="page"/>
                </form>
            </nav>
        </c:if>
    </div>
</c:if>


</body>
</html>
