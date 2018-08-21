<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="custom-tag/footer" prefix="ctg" %>
<%@ taglib uri="custom-tag/operation-message" prefix="op-msg"%>

<%@ include file="part/locale.jsp" %>

<!DOCTYPE html>

<html>

    <head>
        <meta name="viewport" content="width=device-width, initial-scale=0.8">
        <title><fmt:message key="page.title"/></title>

        <script src="${pageContext.request.contextPath}/js/password.js"></script>
        <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
        <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
        <script src="../../js/locale/datepicker_de.js"></script>
        <script src="../../js/locale/datepicker_ru.js"></script>
        <script src="../../js/locale/datepicker_by.js"></script>
        <script src="../../js/locale/datepicker_en.js"></script>

        <script>
            $( function() {
                var options = $.extend({},
                    $.datepicker.regional["${sessionScope.localLang}"],{
                        dateFormat: "yy-mm-dd",
                        maxDate: "-18y"
                    });
                $("#birthday").datepicker(options);
                var dateFormat = "yy-mm-dd",
                    bDay = $("#birthday")
                        .datepicker({
                            defaultDate: "maxDate"
                        })
                        .on("change", function () {
                            bDay.datepicker("option", "maxDate", getDate(this));
                        });

                function getDate(element) {
                    var date;
                    try {
                        date = $.datepicker.parseDate(dateFormat, element.value);
                    } catch (error) {
                        date = null;
                    }
                    return date;
                }
            });
        </script>

        <script src="${pageContext.request.contextPath}/js/userValidator.js"></script>

        <link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
        <link rel="stylesheet" href="/resources/demos/style.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/container.css">
    </head>

    <body>

        <c:import url="part/header.jsp"/>

        <section>

            <div class="data-container">
                <h2><fmt:message key="form.registration"/></h2>
                <form action="${pageContext.request.contextPath}/performRegister.do" method="post" onsubmit="return validateRegistration(this)">

                    <div class="data-line">
                        <label><fmt:message key="page.email"/>:
                            <input type="text" name="email" placeholder="mail@mail.com"/>
                        </label>
                    </div>
                    <label class="warn-label" id="wrongEmail">Wrong email</label>

                    <div class="data-line">
                        <label><fmt:message key="page.pass"/>:
                            <input type="password" name="passwordFirst" id="pwd1"/>
                        </label>
                    </div>
                    <label class="warn-label" id="wrongPassword">
                        Password should consist: 6-20 symbols, one and more capital character, one and more digit
                    </label>

                    <div class="data-line">
                        <label><fmt:message key="page.pass.rep"/>:
                            <input type="password" name="passwordSecond" id="pwd2"/>
                        </label>
                    </div>
                    <label class="warn-label" id="passwordsNotEquals">
                        Passwords not equals
                    </label>

                    <div class="data-line">
                        <fmt:message key="input.placeholder.nameLatin" var="nameSample"/>
                        <label><fmt:message key="page.firstname"/>:
                            <input type="text" name="firstName" placeholder="${nameSample}"/>
                        </label>
                    </div>
                    <label class="warn-label" id="name-not-correspond">
                        Name inserted with mistake. It should be started from the capital character and 2-30 characters length.
                    </label>

                    <div class="data-line">
                        <fmt:message key="input.placeholder.surnameLatin" var="surnameSample"/>
                        <label><fmt:message key="page.lastname"/>:
                            <input type="text" name="lastName" placeholder="${surnameSample}"/>
                        </label>
                    </div>
                    <label class="warn-label" id="surname-not-correspond">
                        Surname inserted with mistake. It should be started from the capital character and 2-30 characters length.
                    </label>

                    <div class="data-line">

                        <label><fmt:message key="page.phoneNum"/>:
                            <input type="text" name="telephoneNumber" placeholder="+375 12 123 12 12"/>
                        </label>
                    </div>
                    <label class="warn-label" id="wrong-number-format">
                        Wrong telephone format. Should be the same as: +375 12 123 12 12..
                    </label>

                    <div class="data-line">
                        <fmt:message key="input.placeholder.dateFormat" var="dateFormat"/>
                        <label><fmt:message key="page.birthday"/>:
                            <input type="text" name="birthday" id="birthday" placeholder="${dateFormat}"/>
                        </label>
                    </div>
                    <label class="warn-label" id="wrong-date-format">
                        Wrong date format. Should be: YYYY-mm-DD
                    </label>

                    <div class="select-data">
                        <label><fmt:message key="page.gender"/>:
                            <select name="gender_male" size="1" contenteditable="false">
                                <option value="true" selected="selected"><fmt:message key="page.gender.male"/></option>
                                <option value="false"><fmt:message key="page.gender.female"/></option>
                            </select>
                        </label>
                    </div>

                    <div class="check-label">
                        <label>
                            <input type="checkbox" onclick="showInsertedPasswords()"/>
                            <fmt:message key="page.label.showpass"/>
                        </label>
                    </div>

                    <input type="submit" value="<fmt:message key="page.btn.reg"/>"/>

                    <div class="warn-message">
                        <fmt:message key="error.allreadyRegistered" var="allreadyReg"/>
                        <op-msg:operationMessage messageCode="2" textMessage="${allreadyReg}"/>
                    </div>
                </form>
            </div>

        </section>

        <ctg:footer/>

    </body>

</html>
