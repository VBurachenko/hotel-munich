<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

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

Invoice List
<br>

<form action="${pageContext.request.contextPath}/admin/provideInvoiceForView.do" method="get">
    <label>Search invoice by id:
        <input type="text" name="invoiceId"/>
    </label>
    <input type="submit" value="search"/>
</form>

<c:set var="currentPage" value="${requestScope.page}" scope="page"/>
<c:set var="pagesCount" value="${requestScope.invoicesForView.pagesCount}" scope="page"/>

<section>
    <div>
        <div>
            <op-msg:operationMessage messageCode="13" textMessage="No such invoice existing."/>
        </div>
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
                            <a href="${pageContext.request.contextPath}/admin/provideUserForView.do?searchUserArtifact=${invoice.userId}">
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
                            <form id="processBookingAndInvoice" action="${pageContext.request.contextPath}/admin/prepareForBookingProcess.do" method="post">
                                <input type="hidden" name="invoiceId" value="${invoice.invoiceId}">
                                <input type="submit" value="process"/>
                            </form>
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
                                      urlPattern="/admin/listInvoicesView.do"
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
