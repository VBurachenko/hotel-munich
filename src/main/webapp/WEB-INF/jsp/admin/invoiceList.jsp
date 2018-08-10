<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="pgr" uri="custom-tag/paginator" %>
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
Invoice List
<br>

<form action="${pageContext.request.contextPath}/provideInvoiceForView.do" method="get">
    <label>Search invoice by id:
        <input type="text" name="invoiceId"/>
    </label>
    <input type="submit" value="search"/>
</form>

<c:set var="currentPage" value="${requestScope.page}" scope="page"/>
<c:set var="pagesCount" value="${requestScope.invoicesForView.pagesCount}" scope="page"/>
<br>
<div>
    <c:if test="${requestScope.invoiceOperationMessage eq 13}">
        No such invoice existing.
    </c:if>

    <%--<c:if test="${not empty requestScope.changedInvoceId}">--%>
        <%--Booking with id ${requestScope.changedInvoiceId}--%>
        <%--<c:choose>--%>
            <%--<c:when test="${requestScope.changedInvoiceStatus eq 'true'}">--%>
                <%--was blocked.--%>
            <%--</c:when>--%>
            <%--<c:otherwise>--%>
                <%--was unblocked.--%>
            <%--</c:otherwise>--%>
        <%--</c:choose>--%>
    <%--</c:if>--%>
</div>
<c:if test="${not empty requestScope.invoicesForView}">
    <div>
        <table border="1">
            <thead>
            <tr>
                <td>Invoice Id</td>
                <td>User Id</td>
                <td>Invoice Date</td>
                <td>Nights Count</td>
                <td>Total Payment</td>
                <td>Payment Type</td>
                <td>Payed</td>
                <td>Perform Action</td>
            </tr>
            </thead>

            <tbody>
            <c:forEach var="invoice" items="${requestScope.invoicesForView.entityList}">
                <tr>
                    <td>${invoice.invoiceId}</td>
                    <td>
                        <a href="${pageContext.request.contextPath}/provideUserForView.do?searchUserArtifact=${invoice.userId}">
                                ${invoice.userId}
                        </a>
                    </td>
                    <td>${invoice.invoiceDate}</td>
                    <td>${invoice.nightsCount}</td>
                    <td>${invoice.totalPayment}</td>
                    <td>${invoice.invoiceStatus}</td>
                    <td>
                        <c:choose>
                            <c:when test="${invoice.isPayed eq 'true'}">
                                Payed
                            </c:when>
                            <c:otherwise>
                                NOT payed
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td>
                        <form id="processBookingAndInvoice" action="${pageContext.request.contextPath}/prepareForBookingProcess.do" method="post">
                            <input type="hidden" name="invoiceId" value="${invoice.invoiceId}">
                            <input type="submit" value="process"/>
                        </form>
                    </td>
                </tr>
            </c:forEach>
            </tbody>

        </table>
        <c:if test="${pagesCount ne '1'}">
            <nav>
                <ul class="pagination">
                    <pgr:navPages currentPage="${currentPage}" pagesCount="${pagesCount}" urlPattern="/listInvoicesView.do"/>
                </ul>
            </nav>
        </c:if>
    </div>
</c:if>

</body>

</html>
