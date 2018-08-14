<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="custom-tag/footer" prefix="ftr" %>
<%@ taglib uri="custom-tag/paginator" prefix="pgr" %>

<%@ include file="../part/locale.jsp" %>


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

Rooms list
<br>


<section>
    <form action="${pageContext.request.contextPath}/provideRoomForView.do" method="get">
        <label>Search room by number:
            <input type="text" name="roomNumber"/>
        </label>
        <input type="submit" value="search"/>
    </form>

    <form action="${pageContext.request.contextPath}/addNewRoom.do" method="get">
        <input type="submit" value="Add new room"/>
    </form>

    <c:set var="currentPage" value="${requestScope.page}" scope="page"/>
    <c:set var="pagesCount" value="${requestScope.roomsForView.pagesCount}" scope="page"/>

    <div>
        <c:if test="${requestScope.roomOperationMessage eq 6}">
            Room was not disabled.
        </c:if>
        <c:if test="${requestScope.roomOperationMessage eq 8}">
            No such room existing.
        </c:if>
        <c:if test="${requestScope.roomOperationMessage eq 9}">
            New room wasn't added.
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

                        <c:choose>
                            <c:when test="${room.availableStatus eq 'true'}">
                                <td>Is available
                                    <form id="disable" action="${pageContext.request.contextPath}/roomBlockingControl.do"
                                          method="post">
                                        <input type="hidden" name="roomNumber" value="${room.roomNumber}"/>
                                        <input type="hidden" name="blockDown" value="false"/>
                                        <input type="submit" value="Disable"/>
                                    </form>
                                </td>
                            </c:when>
                            <c:otherwise>
                                <td>NOT available
                                    <form id="makeAble" action="${pageContext.request.contextPath}/roomBlockingControl.do"
                                          method="post">
                                        <input type="hidden" name="roomNumber" value="${room.roomNumber}"/>
                                        <input type="hidden" name="blockDown" value="true"/>
                                        <input type="submit" value="Make Able"/>
                                    </form>
                                </td>
                            </c:otherwise>
                        </c:choose>
                        <td>
                            <form id="changeRoom" action="${pageContext.request.contextPath}/openRoomChangeForm.do" method="post">
                                <input type="hidden" name="roomNumber" value="${room.roomNumber}"/>
                                <input type="submit" value="change"/>
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
                                      urlPattern="/listRoomsView.do"
                                      previous="&laquo;"
                                      next="&raquo;"/>
                    </div>
                </div>
            </c:if>
        </div>
    </c:if>
</section>

<ftr:footer/>
</body>
</html>
