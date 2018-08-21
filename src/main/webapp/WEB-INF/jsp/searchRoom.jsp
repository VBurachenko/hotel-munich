<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="custom-tag/footer" prefix="ctg" %>

<%@ include file="part/locale.jsp" %>

<!DOCTYPE html>

<html>

    <head>
        <meta name="viewport" content="width=device-width, initial-scale=0.8">
        <title><fmt:message key="page.title"/></title>

        <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
        <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
        <script src="../../js/locale/datepicker_de.js"></script>
        <script src="../../js/locale/datepicker_ru.js"></script>
        <script src="../../js/locale/datepicker_by.js"></script>
        <script src="../../js/locale/datepicker_en.js"></script>

        <script>
            $(function () {
                var options = $.extend({},
                    $.datepicker.regional["${sessionScope.localLang}"],{
                        dateFormat: "yy-mm-dd",
                        minDate: 0
                    });
                $("#check_in").datepicker(options);
                $("#check_out").datepicker(options);
                var dateFormat = "yy-mm-dd",
                    from = $("#check_in")
                        .datepicker({
                            minDate: 0,
                            numberOfMonths: 1
                        })
                        .on("change", function () {
                            to.datepicker("option", "minDate", getDate(this));
                        }),
                    to = $("#check_out")
                        .datepicker({
                            defaultDate: "minDate",
                            numberOfMonths: 1
                        })
                        .on("change", function () {
                            from.datepicker("option", "maxDate", getDate(this));
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
        <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/container.css">

    </head>

    <body>

        <c:import url="part/header.jsp"/>

        <section>
            <c:import url="part/search.jsp"/>
        </section>

        <ctg:footer/>

    </body>

</html>
