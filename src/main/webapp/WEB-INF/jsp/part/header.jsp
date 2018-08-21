<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ include file="locale.jsp" %>

<c:set var="URL" value="${pageContext.request.requestURL}" scope="page"/>
<c:set var="languageLabel" value="${sessionScope.localLang}" scope="page"/>

<header>

    <a href="${pageContext.request.contextPath}/home.do" title="<fmt:message key="header.link.home"/>">
        <fmt:message key="page.title"/>
    </a>

    <a href="${pageContext.request.contextPath}/searchFreeRooms.do">
        <fmt:message key="header.link.searchRoom"/>
    </a>

    <c:if test="${sessionScope.role eq 'CUSTOMER'}">
        <a href="${pageContext.request.contextPath}/openCart.do">
            <fmt:message key="page.btn.cart"/>
        </a>
    </c:if>

    <c:if test="${sessionScope.role eq 'ADMIN' or sessionScope.role eq 'MODER'}">
        <a href="${pageContext.request.contextPath}/listUsersView.do">Users</a>
        <a href="${pageContext.request.contextPath}/listBookingsView.do">Bookings</a>
        <a href="${pageContext.request.contextPath}/listInvoicesView.do">Invoices</a>
        <a href="${pageContext.request.contextPath}/listRoomsView.do">Rooms</a>
    </c:if>

    <div class="header-right">
        <c:choose>
            <c:when test="${sessionScope.role eq 'GUEST'}">
                <a href="${pageContext.request.contextPath}/register.do">
                    <fmt:message key="page.btn.reg"/>
                </a>

                <a href="${pageContext.request.contextPath}/login.do">
                    <fmt:message key="header.link.login"/>
                </a>
            </c:when>
            <c:otherwise>
                <a href="${pageContext.request.contextPath}/logout.do">
                    <fmt:message key="page.btn.logout"/>
                </a>

                <a href="${pageContext.request.contextPath}/login.do">
                    <fmt:message key="header.link.account"/>
                </a>
            </c:otherwise>
        </c:choose>

        <div class="lang-panel">

            <form action="${pageContext.request.contextPath}/changeLang.do" method="post">

                <input type="hidden" name="currentPage" value="${URL}" hidden/>

                <fmt:message key="header.label.language" var="chooseLang"/>

                <select contenteditable="false" name="setLang" onchange="submit();" title="${chooseLang}">

                    <option value="${sessionScope.localLang}">${languageLabel.toUpperCase()}</option>

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
            </form>
        </div>

    </div>

</header>