<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Admin/User payments</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
    <link href="<c:url value='/components/bootstrap/css/bootstrap.min.css'/>" rel="stylesheet" type="text/css">
    <link href="<c:url value='/components/css/main.css'/>" rel="stylesheet" type="text/css"/>
</head>
<body>
<jsp:include page="../additional/admin_header.jsp"/>

<div id="users" class="container">
    <div class="row">
        <h2>
            <c:out value="${user.login}"/> payments
        </h2>
        <br/>
    </div>
    <table class="table table-striped table-hover">
        <thead>
        <tr class="row">
            <th class="col-sm-6">Time</th>
            <th class="col-sm-6">Sum</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="payment" items="${displayedObjects}">
            <tr class="row">
                <td class="col-sm-6">${payment.paymentTime}</td>
                <td class="col-sm-6">${payment.paymentSum}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <div class="row">
        <jsp:include page="/pages/additional/pagination.jsp"/>
    </div>
</div>

<script src="<c:url value='/components/jquery/jquery.min.js'/>"></script>

</body>
</html>