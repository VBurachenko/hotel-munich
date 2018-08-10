<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<c:set var="URL" value="${pageContext.request.requestURL}" scope="page" />
<c:set var="languageLabel" value="${sessionScope.localLang}" scope="page"/>

<div class="header-left">
    <ul>
        <fmt:message key="page.title" var="siteTitle"/>

        <fmt:message key="header.link.home" var="home"/>
        <li>
            <a href="${pageContext.request.contextPath}/home.do" title="${home}">
                ${siteTitle}
            </a>
        </li>


        <fmt:message key="header.link.searchRoom" var="searchRoom"/>
        <li>
            <a href="${pageContext.request.contextPath}/searchFreeRooms.do">
                ${searchRoom}
            </a>
        </li>


        <c:if test="${sessionScope.role eq 'CUSTOMER'}">
            <fmt:message key="page.btn.cart" var="cartOpen"/>
            <li>
                <a href="${pageContext.request.contextPath}/openCart.do">
                        ${cartOpen}
                </a>
            </li>

        </c:if>

        <c:if test="${sessionScope.role eq 'ADMIN' or sessionScope.role eq 'MODER'}">
            <li>
                <a href="${pageContext.request.contextPath}/listUsersView.do">Users</a>
            </li>
            <li>
                <a href="${pageContext.request.contextPath}/listBookingsView.do">Bookings</a>
            </li>
            <li>
                <a href="${pageContext.request.contextPath}/listInvoicesView.do">Invoices</a>
            </li>
            <li>
                <a href="${pageContext.request.contextPath}/listRoomsView.do">Rooms</a>
            </li>
        </c:if>
    </ul>

</div>

<div class="header-right">
    <c:choose>
        <c:when test="${sessionScope.role eq 'GUEST'}">

            <fmt:message key="page.btn.reg" var="register"/>
            <li>
                <a href="${pageContext.request.contextPath}/register.do">
                        ${register}
                </a>
            </li>

            <fmt:message key="header.link.login" var="logIn"/>
            <li>
                <a href="${pageContext.request.contextPath}/login.do">
                        ${logIn}
                </a>
            </li>

        </c:when>
        <c:otherwise>
            <li>
                <a href="${pageContext.request.contextPath}/logout.do">
                    Logout
                </a>
            </li>

            <fmt:message key="header.link.account" var="myOffice"/>
            <li>
                <a href="${pageContext.request.contextPath}/login.do">
                        ${myOffice}
                </a>
            </li>

        </c:otherwise>
    </c:choose>

    <li>
        <form action="${pageContext.request.contextPath}/changeLang.do" method="post">

            <input type="hidden" name="currentPage" value="${URL}"/>

            <label><fmt:message key="header.label.language"/>:

                <select contenteditable="false" name="setLang" onchange="submit();">
                    <option name="setLang"
                            value="${sessionScope.localLang}">${languageLabel.toUpperCase()}</option>
                    <c:if test="${sessionScope.localLang ne 'en'}">
                        <option value="en">EN</option>
                    </c:if>
                    <c:if test="${sessionScope.localLang ne 'de'}">
                        <option value="de">DE</option>
                    </c:if>
                    <c:if test="${sessionScope.localLang ne 'by'}">
                        <option value="by">BY</option>
                    </c:if>
                    <c:if test="${sessionScope.localLang ne 'ru'}">
                        <option value="ru">RU</option>
                    </c:if>
                </select>
            </label>
        </form>
    </li>

</div>