<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
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
userList
<br>
<form action="${pageContext.request.contextPath}/listBookingsShow.do" method="post">
    <input type="submit" value="Go to bookingsList">
</form>

<c:set var="currentPage" value="${requestScope.page}" scope="page"/>
<c:set var="pagesCount" value="${requestScope.usersForDisplay.pagesCount}" scope="page"/>
<br>
<div>
    <c:if test="${requestScope.userChangeBlockMessage eq 4}">
        User was not blocked.
    </c:if>

    <c:if test="${not empty requestScope.blockedUserId}">
        User with id ${requestScope.blockedUserId}
        <c:choose>
            <c:when test="${requestScope.blockDown eq 'true'}">
                 was blocked.
            </c:when>
            <c:otherwise>
                was unblocked.
            </c:otherwise>
        </c:choose>
    </c:if>
</div>
<div>
    <table border="1" >
        <thead>
            <tr>
                <td>UserId</td>
                <td>Email</td>
                <td>Name</td>
                <td>Telephone number</td>
                <td>BirthDay</td>
                <td>Discount</td>
                <td>Gender</td>
                <td>Blocking</td>
                <td>Role</td>
            </tr>
        </thead>

        <tbody>
            <c:forEach var="user" items="${requestScope.usersForDisplay.entityList}">
                <tr>
                    <td>${user.userId}</td>
                    <td>${user.email}</td>
                    <td>${user.surname} ${user.name}</td>
                    <td>${user.telNumber}</td>
                    <td>${user.birthday}</td>
                    <td>${user.discount}</td>

                    <td>
                        <c:choose>
                            <c:when test="${user.genderMale eq 'true'}">
                                Male
                            </c:when>
                            <c:otherwise>
                                Female
                            </c:otherwise>
                        </c:choose>
                    </td>

                    <td>

                        <c:choose>
                            <c:when test="${user.blocked eq 'true'}">
                                Blocked
                                <c:if test="${user.role ne 'MODER'}">
                                    <form id="unblockUser" action="${pageContext.request.contextPath}/userBlockingControl.do" method="post">
                                        <input type="hidden" name="user_id" value="${user.userId}"/>
                                        <input type="hidden" name="blockDown" value="false"/>
                                        <input type="submit" value="Unblock"/>
                                    </form>
                                </c:if>

                            </c:when>
                            <c:otherwise>
                                Not blocked
                                <c:if test="${user.role ne 'MODER'}">
                                    <form id="blockUser" action="${pageContext.request.contextPath}/userBlockingControl.do" method="post">
                                        <input type="hidden" name="user_id" value="${user.userId}"/>
                                        <input type="hidden" name="blockDown" value="true"/>
                                        <input type="submit" value="Block"/>
                                    </form>
                                </c:if>
                            </c:otherwise>
                        </c:choose>
                    </td>

                    <td>${user.role}</td>
                </tr>
            </c:forEach>
        </tbody>

    </table>
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
        <form action="${pageContext.request.contextPath}/listUsersShow.do" method="post" name="form1" id="changePage">
            <input type="hidden" name="page"/>
        </form>
    </nav>
</div>



</body>
</html>
