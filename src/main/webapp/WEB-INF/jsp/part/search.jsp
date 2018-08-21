<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="locale.jsp" %>

<div class="data-container">
    <form action="${pageContext.request.contextPath}/prepareForSearch.do" method="post">

        <fmt:message key="search.checkin" var="checkin"/>
        <label>
            ${checkin}:<input type="text" name="check_in" id="check_in" />
        </label>

        <fmt:message key="search.checkout" var="checkout"/>
        <label>
            ${checkout}:<input type="text" name="check_out" id="check_out" />
        </label><br/>

        <fmt:message key="search.comfortlevel" var="comfLevel"/>
        <label>
            ${comfLevel}:<input type="number" value="3" min="3" max="5" name="comfLevel"/>
        </label><br/>

        <fmt:message key="search.count.adult" var="adults"/>
        <label>
            ${adults}:<input type="number" value="1" min="1" max="8" name="adultCount"/>
        </label>

        <fmt:message key="search.count.child" var="children"/>
        <label>
            ${children}:<input type="number" value="0" min="0" max="4" name="childCount"/>
        </label>

        <fmt:message key="page.btn.search" var="search"/>
        <input type="submit" value="${search}"/>
    </form>
</div>