<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:set var="URL" scope="page" value="${pageContext.request.requestURL}"/>

<div class="navbar" id="topnavbar">

    <fmt:message key="page.title" var="siteTitle"/>
    <fmt:message key="header.link.home" var="home"/>
    <a href="${pageContext.request.contextPath}/home.do" class="active" title="${home}">
        ${siteTitle}
    </a>

    <fmt:message key="header.link.searchRoom" var="searchRoom"/>
    <a href="${pageContext.request.contextPath}/searchFreeRooms.do">
        ${searchRoom}
    </a>

    <c:if test="${sessionScope.role eq 'CUSTOMER'}">
        <fmt:message key="page.btn.cart" var="cartOpen"/>
        <a href="${pageContext.request.contextPath}/openCart.do">
                ${cartOpen}
        </a>
    </c:if>

    <div class="navbar-right">
        <c:choose>
            <c:when test="${sessionScope.role eq 'GUEST'}">

                <fmt:message key="page.btn.reg" var="register"/>
                <a href="${pageContext.request.contextPath}/register.do">
                    ${register}
                </a>

                <fmt:message key="header.link.login" var="logIn"/>
                <a href="${pageContext.request.contextPath}/login.do">
                    ${logIn}
                </a>

            </c:when>
            <c:otherwise>
                <fmt:message key="header.link.account" var="myOffice"/>
                <a href="${pageContext.request.contextPath}/login.do">
                    ${myOffice}
                </a>
            </c:otherwise>
        </c:choose>
        <label for="drdwn"><fmt:message key="header.label.language"/>:</label>
        <div class="dropdown" id="drdwn">
            <form action="${pageContext.request.contextPath}/changeLang.do" method="post">
                <input type="hidden" name="currentPage" value="${URL}"/>
                <input type="hidden" name="setLang" value="ru"/>
                <input type="submit" value="RU"/>
            </form>

            <form action="${pageContext.request.contextPath}/changeLang.do" method="post">
                <input type="hidden" name="currentPage" value="${URL}"/>
                <input type="hidden" name="setLang" value="by"/>
                <input type="submit" value="BY"/>
            </form>

            <form action="${pageContext.request.contextPath}/changeLang.do" method="post">
                <input type="hidden" name="currentPage" value="${URL}"/>
                <input type="hidden" name="setLang" value="en"/>
                <input type="submit" value="EN"/>
            </form>

            <form action="${pageContext.request.contextPath}/changeLang.do" method="post">
                <input type="hidden" name="currentPage" value="${URL}"/>
                <input type="hidden" name="setLang" value="de"/>
                <input type="submit" value="DE"/>
            </form>
        </div>
    </div>



</div>
