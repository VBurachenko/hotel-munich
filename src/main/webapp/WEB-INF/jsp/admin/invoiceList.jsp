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
<form action="${pageContext.request.contextPath}/listUsersView.do" method="post">
    <input type="submit" value="Go to users List">
</form>
<form action="${pageContext.request.contextPath}/listRoomsView.do" method="post">
    <input type="submit" value="Go to rooms List">
</form>
<form action="${pageContext.request.contextPath}/listBookingsView.do" method="post">
    <input type="submit" value="Go to bookings List">
</form>


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
                    <td>${invoice.userId}</td>
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
                <form action="${pageContext.request.contextPath}/listBookingsShow.do" method="post" name="form1"
                      id="changePage">
                    <input type="hidden" name="page"/>
                </form>
            </nav>
        </c:if>
    </div>
</c:if>

</body>

</html>
