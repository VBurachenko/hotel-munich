<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="custom-tag/footer" prefix="ctg" %>
<%@ taglib uri="custom-tag/operation-message" prefix="op-msg" %>

<%@ include file="part/locale.jsp" %>

<!DOCTYPE html>

<html>

    <head>
        <meta name="viewport" content="width=device-width, initial-scale=0.8">
        <title><fmt:message key="page.title"/></title>
        <script src="${pageContext.request.contextPath}/js/password.js"></script>
        <script src="${pageContext.request.contextPath}/js/userValidator.js"></script>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/container.css">
    </head>

    <body>

    <c:import url="part/header.jsp"/>

    <section>
        <div class="data-container">

            <h2><fmt:message key="form.login"/></h2>

            <form action="${pageContext.request.contextPath}/performLogin.do" method="post" onsubmit="return validateSignIn(this)">

                <div class="data-line">
                    <label for="email"><fmt:message key="page.email"/>:</label>
                    <fmt:message key="input.placeholder.enterEmail" var="emailEnter"/>
                    <input type="text" id="email" placeholder="${emailEnter}" name="email" required>
                </div>

                <label class="warn-label" id="wrongEmail">Wrong email</label>

                <div class="data-line">
                    <label for="pwd"><fmt:message key="page.pass"/>:</label>
                    <fmt:message key="input.placeholder.enterPassword" var="passEnter"/>
                    <input type="password" id="pwd" placeholder="${passEnter}" name="passwordFirst" required>
                </div>

                <label class="warn-label" id="wrongPassword">Password should consist: 6-20 symbols, one and more capital character, one and more digit</label>

                <div class="check-label">
                    <label>
                        <input type="checkbox" onclick="showPassword()"/>
                        <fmt:message key="page.label.showpass"/>
                    </label>
                </div>

                <input type="submit" value="<fmt:message key="page.btn.login"/>"/>

                <div class="warn-message">
                    <fmt:message key="error.wrongEmailOrPass" var="wrongEmailOrPassword"/>
                    <op-msg:operationMessage messageCode="1" textMessage="${wrongEmailOrPassword}"/>

                    <fmt:message key="opmessage.accountBlocked" var="accountBlocked"/>
                    <op-msg:operationMessage messageCode="5" textMessage="${accountBlocked}"/>
                </div>

            </form>
        </div>
    </section>

    <ctg:footer/>

    </body>

</html>
