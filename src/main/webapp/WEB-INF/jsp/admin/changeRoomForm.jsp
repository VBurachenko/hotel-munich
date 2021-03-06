<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="custom-tag/footer" prefix="ftr"%>

<%@ include file="../part/locale.jsp" %>

<c:set var="locale" value="${sessionScope.localLang}"/>

<!DOCTYPE HTML>

<html>

<head>
    <meta name="viewport" content="width=device-width, initial-scale=0.8">
    <title><fmt:message key="page.title"/></title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
</head>

<body>

<c:import url="../part/header.jsp"/>

<section>
    <c:set var="room" value="${sessionScope.roomForChange}" scope="page"/>
    <div class="container">
        <form action="${pageContext.request.contextPath}/admin/changeRoomDescription.do" method="post">

            <label>Room Number: <input type="text" name="roomNumber" value="${room.roomNumber}" readonly/></label><br/>

            <label>Berth Count: <input type="text" name="berthCount" value="${room.berthCount}"/></label><br/>

            <label>Comfort Level<input type="text" name="comfortLevel" value="${room.comfortLevel}"/></label><br/>

            <label>Price per Night<input type="text" name="pricePerNight" value="${room.pricePerNight}"/></label><br/>

            <label>Picture Link<input type="text" name="pictureLink" value="${room.pictureLink}"/></label><br/>

            <label>Available Status: <br>
                <c:choose>
                    <c:when test="${room.availableStatus eq 'true'}">
                        <label>Available
                            <input type="radio" name="availableStatus" value="true" checked readonly/>
                        </label><br>
                    </c:when>
                    <c:otherwise>
                        <label>NOT available
                            <input type="radio" name="availableStatus" value="false" checked readonly/>
                        </label>
                    </c:otherwise>
                </c:choose>
            </label>


            <div>
                <input type="submit" value="Change"/>
            </div>

        </form>
    </div>

</section>

<ftr:footer/>

</body>
</html>