<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ include file="part/locale.jsp" %>
<c:set var="locale" value="${sessionScope.localLang}"/>

<!DOCTYPE HTML>
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
    <link rel="stylesheet" href="/resources/demos/style.css">
    <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
    <script src="../../js/locale/datepicker_de.js"></script>
    <script src="../../js/locale/datepicker_ru.js"></script>
    <script src="../../js/locale/datepicker_by.js"></script>
    <script src="../../js/locale/datepicker_en.js"></script>
    <script>
        $( function() {
            var options = $.extend({},
                $.datepicker.regional["${locale}"],{
                    dateFormat: "yy-mm-dd",
                    maxDate: "-18y"
                });
            $("#birthday").datepicker(options);
            var bDay = $("#birthday").datepicker({
                defaultDate: "maxDate"
            })
                .on("change", function () {
                    bDay.datepicker("option", "maxDate", getDate(this));
                });

            function getDate(element) {
                var date;
                try {
                    date = $.datepicker.parseDate(element.value);
                } catch (error) {
                    date = null;
                }
                return date;
            }
        });
    </script>
</head>
<body>

<br/>
<header>
    <%@include file="part/header.jsp"%>
</header>
<br/>
<section>
    <div class="container">
        <form action="${pageContext.request.contextPath}/performRegister.do" method="post">
            <fmt:message key="page.email" var="email"/>
            <label>${email}<input type="text" name="email" placeholder="" /></label><br/>

            <fmt:message key="page.pass" var="pass1"/>
            <label>${pass1}<input type="password" name="password1" id="pwd1" placeholder=""/></label><br/>

            <fmt:message key="page.pass.rep" var="pass2"/>
            <label>${pass2}<input type="password" name="password2" id="pwd2" placeholder=""/></label><br/>

            <fmt:message key="page.firstname" var="f_name"/>
            <label>${f_name}<input type="text" name="firstName" placeholder=""/></label><br/>

            <fmt:message key="page.lastname" var="l_name"/>
            <label>${l_name}<input type="text" name="lastName" placeholder=""/></label><br/>

            <fmt:message key="page.phoneNum" var="phone"/>
            <label>${phone}<input type="text" name="telephoneNumber" placeholder="+375 12 123 12 12"/></label><br/>

            <label for="birthday"><fmt:message key="page.label.birthday"/></label>
            <input id="birthday" type="text" name="birthday"/>

            <fmt:message key="page.gender" var="gend"/>
            <label for="gend-list">${gend}</label>
            <select id="gend-list" name="gender_male" size="1" contenteditable="false">
                <option value="true" selected="selected"><fmt:message key="page.gender.male"/></option>
                <option value="false"><fmt:message key="page.gender.female"/></option>
            </select><br/>

            <div>
                <label>
                    <input type="checkbox" onclick="showInsertedPasswords()"/>
                    <fmt:message key="page.label.showpass"/>
                </label>
                <c:if test="${sessionScope.registerError eq 2}">
                    <fmt:message key="error.allreadyRegistered" var="errorMessage"/>
                </c:if>
            </div>

            <div>
                ${errorMessage}
            </div>

            <div>
                <fmt:message key="page.btn.reg" var="register" />
                <input type="submit" value="${register}"/>
            </div>

        </form>
    </div>

</section>

</body>
</html>
