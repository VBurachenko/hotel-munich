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
<h2>You activated as Administrator</h2>
<p>Hello ${sessionScope.firstName}</p>

<c:if test="${sessionScope.changeBookingMessage eq 3}">
    <p>Change this booking is impossible</p>
</c:if>
<c:if test="${sessionScope.changeBookingMessage eq 4}">
    <p>Booking was successfully changed</p>
</c:if>

<div>
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

</body>
</html>
