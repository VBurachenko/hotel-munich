<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="custom-tag/footer" prefix="ctg" %>
<%@ include file="part/locale.jsp" %>

<!DOCTYPE html>

<html>

    <head>
        <meta name="viewport" content="width=device-width, initial-scale=0.8">
        <title><fmt:message key="page.title"/></title>
        <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
        <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
        <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
        <script src="../../js/locale/datepicker_de.js"></script>
        <script src="../../js/locale/datepicker_ru.js"></script>
        <script src="../../js/locale/datepicker_by.js"></script>
        <script src="../../js/locale/datepicker_en.js"></script>

        <script src="${pageContext.request.contextPath}/js/dates_picker.js"></script>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
    </head>

    <body>

    <c:import url="part/header.jsp"/>

    <section>
        <%@ include file="part/search.jsp" %>
    </section>

    <ctg:footer/>

    </body>

</html>
