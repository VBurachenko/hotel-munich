<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="custom-tag/footer" prefix="ftr" %>

<%@ include file="../part/locale.jsp"%>

<c:set var="locale" value="${sessionScope.localLang}"/>

<!DOCTYPE html>

<html>

<head>

    <meta name="viewport" content="width=device-width, initial-scale=0.8">
    <title><fmt:message key="page.title"/></title>

    <script src="${pageContext.request.contextPath}/js/paginator.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/pagination.css">

    <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
    <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
    <script src="../../../js/locale/datepicker_de.js"></script>
    <script src="../../../js/locale/datepicker_ru.js"></script>
    <script src="../../../js/locale/datepicker_by.js"></script>
    <script src="../../../js/locale/datepicker_en.js"></script>

</head>

<body>

<c:import url="../part/header.jsp"/>

<c:set var="userForBlock" value="${requestScope.userForBlock}" scope="page"/>

<section>
    <form action="${pageContext.request.contextPath}/admin/performUserBlockChange.do" method="post">
        <label>User-id:
            <input type="text" name="user_id" value="${userForBlock.userId}" readonly/>
        </label>
        <br>

        <label>Email:
            <input type="text" name="check_in" id="check_in" value="${userForBlock.email}" readonly/>
        </label>
        <br>

        <label>Name:
            <input type="text" name="check_out" id="check_out" value="${userForBlock.name}" readonly/>
        </label>
        <br>

        <label>Surname:
            <input type="text" name="adultCount" value="${userForBlock.surname}" readonly/>
        </label>
        <br>

        <label>Telephone number:
            <input type="text" name="childCount" value="${userForBlock.telNumber}" readonly/>
        </label>
        <br>

        <input type="hidden" name="genderMale" value="${userForBlock.genderMale}"/>
        <input type="hidden" name="role" value="${userForBlock.role}">

        <label> Discount:
            <input type="text" name="role" value="${userForBlock.discount}"/>
        </label>
        <br>

        <label> Birthday
            <input type="text" name="birthday" value="${userForBlock.birthday}"/>
        </label>
        <br>

        <label>Select block option:
            <select name="blocking" size="1" contenteditable="false">

                <option value="${userForBlock.blocking}">
                    <c:if test="${userForBlock.blocking eq 0}">
                        Not blocked
                    </c:if>
                    <c:if test="${userForBlock.blocking eq 1}">
                        Damager
                    </c:if>
                    <c:if test="${userForBlock.blocking eq 2}">
                        Violator
                    </c:if>
                    <c:if test="${userForBlock.blocking eq 3}">
                        Defaulter
                    </c:if>
                </option>

                <c:if test="${userForBlock.blocking ne 0}">
                    <option value="0">Unblocked</option>
                </c:if>
                <c:if test="${userForBlock.blocking ne 1}">
                    <option value="1">Damager</option>
                </c:if>
                <c:if test="${userForBlock.blocking ne 2}">
                    <option value="2">Violator</option>
                </c:if>
                <c:if test="${userForBlock.blocking ne 3}">
                    <option value="3">Defaulter</option>
                </c:if>
            </select>
        </label>

        <input type="submit" value="Change block"/>

    </form>
</section>

<ftr:footer/>

</body>

</html>
