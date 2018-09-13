<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"  %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="custom-tag/paginator" prefix="pgr"%>
<%@ taglib uri="custom-tag/footer" prefix="ftr" %>
<%@ taglib uri="custom-tag/operation-message" prefix="op-msg"%>

<%@ include file="../part/locale.jsp" %>

<!DOCTYPE html>

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

<section>
    User List
    <br>

    <form action="${pageContext.request.contextPath}/admin/provideUserForView.do" method="get">
        <label>Search user by user-id, email or telephone number:
            <input type="text" name="searchUserArtifact"/>
        </label>
        <input type="submit" value="search"/>
    </form>

    <c:set var="currentPage" value="${requestScope.page}" scope="page"/>
    <c:set var="pagesCount" value="${requestScope.usersForView.pagesCount}" scope="page"/>
    <br>
    <div>
        <op-msg:operationMessage messageCode="4" textMessage="User was not blocked."/>
        <op-msg:operationMessage messageCode="7" textMessage="No such user registered."/>
        <op-msg:operationMessage messageCode="14" textMessage="Admin was not registered."/>
        <op-msg:operationMessage messageCode="16" textMessage="User's block was changed successfully"/>

        <c:if test="${not empty requestScope.blockedUserId}">
            User with id ${sessionScope.blockedUserId}
            <c:choose>
                <c:when test="${requestScope.blockDown eq 'true'}">
                    was blocking.
                </c:when>
                <c:otherwise>
                    was unblocked.
                </c:otherwise>
            </c:choose>
        </c:if>
    </div>
    <c:if test="${not empty requestScope.usersForView}">
        <div>
            <table border="1">
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
                <c:forEach var="user" items="${requestScope.usersForView.entityList}">
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
                                <c:when test="${user.blocking ne 0}">
                                    Blocked,
                                    <c:if test="${user.blocking eq 1}">
                                        damager
                                    </c:if>

                                    <c:if test="${user.blocking eq 2}">
                                        violator
                                    </c:if>

                                    <c:if test="${user.blocking eq 3}">
                                        defaulter
                                    </c:if>
                                </c:when>
                                <c:otherwise>
                                    Not blocked
                                </c:otherwise>
                            </c:choose>
                            <c:if test="${(user.role ne 'MODER') and (user.role ne 'ADMIN')}">
                                <form id="changeBlock"
                                      action="${pageContext.request.contextPath}/admin/userBlockingControl.do" method="post">
                                    <input type="hidden" name="user_id" value="${user.userId}"/>
                                    <input type="submit" value="Change block"/>
                                </form>
                            </c:if>
                        </td>

                        <td>${user.role}
                            <c:if test="${(user.role eq 'CUSTOMER') and (sessionScope.role eq 'MODER')}">
                                <form action="${pageContext.request.contextPath}/moder/registerNewAdmin.do" method="post">
                                    <input type="hidden" name="email" value="${user.email}"/>
                                    <input type="submit" value="make admin"/>
                                </form>
                            </c:if>

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
                                      urlPattern="/admin/listUsersView.do"
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