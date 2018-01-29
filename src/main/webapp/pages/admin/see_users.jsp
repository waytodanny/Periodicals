<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Admin\Users</title>

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
    <link href="<c:url value='/components/bootstrap/css/bootstrap.min.css'/>" rel="stylesheet" type="text/css">
    <link href="<c:url value='/components/css/main.css'/>" rel="stylesheet" type="text/css"/>
</head>
<body>
<jsp:include page="../additional/admin_header.jsp"/>

<div id="users" class="container">
    <div class="row">
        <h2>Users</h2>
        <br/>
    </div>
    <table class="table table-striped table-hover">
        <thead>
        <tr class="row">
            <th class="col-sm-12">Login</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="user" items="${displayedObjects}">
            <tr class="row">
                <td class="col-sm-8">${user.login}</td>
                <td class="col-sm-2 table-success text-center table-default-link">
                    <a href="edit_user?id=${user.id}">
                        <i class="fa fa-pencil" aria-hidden="true"></i> Edit</a></td>
                <td class="col-sm-2 table-info text-center table-default-link">
                    <a href="user_info?id=${user.id}">
                        <i class="fa fa-credit-card" aria-hidden="true"></i></i>Payments</a></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <div class="row">
        <jsp:include page="/pages/additional/pagination.jsp"/>
    </div>
</div>

<%--<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>--%>
<%--<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.3/js/bootstrap.min.js"></script>--%>

</body>
</html>