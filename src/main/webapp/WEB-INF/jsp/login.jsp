<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="custom-tag/footer" prefix="ctg" %>

<%@ include file="part/locale.jsp" %>

<!DOCTYPE html>

<html>

    <head>
        <meta name="viewport" content="width=device-width, initial-scale=0.8">
        <title><fmt:message key="page.title"/></title>
        <script src="${pageContext.request.contextPath}/js/password.js"></script>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
    </head>

    <body>

    <c:import url="part/header.jsp"/>

    <div>
        <h2>Login form</h2>
        <form action="${pageContext.request.contextPath}/performLogin.do" method="post">
            <div>
                <fmt:message key="page.email" var="email"/>
                <label for="email">${email}</label>
                <div>
                    <fmt:message key="input.placeholder.enterEmail" var="emailEnter"/>
                    <input type="text" id="email" placeholder="${emailEnter}" name="email">
                </div>
            </div>
            <div>
                <fmt:message key="page.pass" var="pass"/>
                <label for="pwd">${pass}</label>
                <div>
                    <fmt:message key="input.placeholder.enterPassword" var="passEnter"/>
                    <input type="password" class="form-control" id="pwd" placeholder="${passEnter}" name="password">
                </div>
            </div>
            <div>
                <label>
                    <input type="checkbox" onclick="showPassword()"/>
                    <fmt:message key="page.label.showpass"/>
                </label>


                <c:if test="${sessionScope.loginError eq 1}">
                    <fmt:message key="error.wrongEmailOrPass" var="errorMessage"/>
                </c:if>
                <c:if test="${sessionScope.loginError eq 5}">
                    <c:set var="errorMessage" value="Your account was blocked"/>
                </c:if>
                <div>
                    ${errorMessage}
                </div>
                <div>
                    <div>
                        <button type="submit">
                            <fmt:message key="page.btn.login"/>
                        </button>
                    </div>
                </div>
        </form>
    </div>

    <ctg:footer/>

    </body>

</html>
