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
    <script src="${pageContext.request.contextPath}/js/password.js"></script>

    <script src="${pageContext.request.contextPath}/js/paginator.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/pagination.css">
</head>
<body>

<c:import url="../part/header.jsp"/>

<section>
    <div class="container">
        <form action="${pageContext.request.contextPath}/admin/newRoomAddingPerform.do" method="post">

            <label>Room Number: <input type="text" name="roomNumber" placeholder="" /></label><br/>

            <label>Berth Count: <input type="text" name="berthCount" placeholder=""/></label><br/>

            <label>Comfort Level<input type="text" name="comfortLevel" placeholder=""/></label><br/>

            <label>Price per Night<input type="text" name="pricePerNight" placeholder=""/></label><br/>

            <label>Picture Link<input type="text" name="pictureLink" placeholder=""/></label><br/>


            <div>
                <input type="submit" value="Add new room"/>
            </div>

        </form>
    </div>

</section>

<ftr:footer/>

</body>
</html>
