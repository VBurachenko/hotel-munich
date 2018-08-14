<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="custom-tag/footer" prefix="ctg" %>
<%@ include file="part/locale.jsp"%>

<!DOCTYPE html>

<html>

<head>
    <meta name="viewport" content="width=device-width, initial-scale=0.8">
    <title><fmt:message key="page.title"/></title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
</head>

<body>

<c:import url="part/header.jsp"/>

<section>
    Booking impossible <a href="${pageContext.request.contextPath}/searchFreeRooms.do">Go back to search</a>
</section>

<ctg:footer/>

</body>

</html>
