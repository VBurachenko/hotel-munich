<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="custom-tag/footer" prefix="ftr" %>

<%@ include file="../part/locale.jsp"%>

<!DOCTYPE html>

<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=0.8">
    <title><fmt:message key="page.title"/></title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
</head>
<body>

    <c:import url="../part/header.jsp"/>

    <section>
        Payment page <br>
        ${sessionScope.invoiceForPayment}

        <form action="${pageContext.request.contextPath}/customer/paymentPerform.do" method="post">
            <label>Insert total sum payment:
                <input type="text" name="totalAmount"/>
            </label>
            <input type="hidden" name="invoiceIdForPayment" value="${sessionScope.invoiceForPayment.invoiceId}"/>

            <input type="submit" value="pay"/>
        </form>
    </section>


    <ftr:footer/>

</body>

</html>
