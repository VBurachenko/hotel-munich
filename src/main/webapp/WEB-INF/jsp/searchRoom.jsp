<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ include file="part/locale.jsp" %>
<c:set var="locale" value="${sessionScope.localLang}"/>
<html>
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=0.8">
        <title><fmt:message key="page.title"/></title>
        <script src="../../js/password.js"></script>
        <script src="../../js/navigation_bar.js"></script>
        <link rel="stylesheet" href="../../css/navigation_bar.css">
        <%--<link rel="stylesheet" href="/resources/demos/style.css">--%>
        <%--<link rel="stylesheet" href="../../css/jquery-ui.css">--%>
        <%--<script src="../../js/jquery.js"></script>--%>
        <%--<script src="../../js/jquery-ui.js"></script>--%>
        <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
        <%--<link rel="stylesheet" href="/resources/demos/style.css">--%>
        <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
        <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
        <script src="../../js/locale/datepicker_de.js"></script>
        <script src="../../js/locale/datepicker_ru.js"></script>
        <script src="../../js/locale/datepicker_by.js"></script>
        <script src="../../js/locale/datepicker_en.js"></script>

        <script src="${pageContext.request.contextPath}/js/dates_picker.js"></script>
    </head>
    <body>
        <br/>
        <header>
            <%@include file="part/header.jsp" %>
        </header>
        <br/>
        <section>
            <%@ include file="part/search.jsp" %>
        </section>
    </body>
</html>
