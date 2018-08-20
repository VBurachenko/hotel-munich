<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="custom-tag/footer" prefix="ftr" %>

<%@ include file="part/locale.jsp" %>

<!DOCTYPE html>

<html>

    <head>
        <meta name="viewport" content="width=device-width, initial-scale=0.8">
        <title><fmt:message key="page.title"/></title>
        <link rel="stylesheet" href="../../css/main.css">
    </head>

    <body style="

            background-image: url('${pageContext.request.contextPath}/pic/Munich-München.jpg');
            background-size: cover;
            background-attachment: scroll;">

    <c:import url="part/header.jsp"/>

    <section>
        <div class="content">
            <fmt:message key="page.home.greeting"/>
        </div>
    </section>

    <ftr:footer/>

    </body>

</html>