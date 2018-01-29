<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Admin\Catalog</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
    <link href="<c:url value='/components/bootstrap/css/bootstrap.min.css'/>" rel="stylesheet" type="text/css">
    <link href="<c:url value='/components/css/main.css'/>" rel="stylesheet" type="text/css"/>
</head>
<body>
<jsp:include page="../additional/admin_header.jsp"/>

<div id="issues" class="container">
    <div class="row">
        <h2>Periodicals</h2>
        <br/>
    </div>
    <table class="table table-striped table-hover">
        <thead>
        <tr class="row">
            <th class="col-sm-12">Name</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="periodical" items="${displayedObjects}">
            <tr class="row">
                <td class="col-sm-4">${periodical.name}</td>
                <td class="col-sm-2 table-success text-center table-default-link">
                    <a href="edit_periodical?id=${periodical.id}">
                    <i class="fa fa-pencil" aria-hidden="true"></i> Edit</a></td>
                <td class="col-sm-2 table-danger text-center table-default-link">
                    <a href="delete_periodical?id=${periodical.id}">
                    <i class="fa fa-trash" aria-hidden="true"></i> Delete</a></td>
                <td class="col-sm-2 table-info text-center table-default-link">
                    <a href="see_issues?periodical=${periodical.id}">
                    <i class="fa fa-book" aria-hidden="true"></i> See issues</a></td>
                <td class="col-sm-2 table-warning text-center table-default-link">
                    <a href="add_issue?periodical=${periodical.id}">
                    <i class="fa fa-plus" aria-hidden="true"></i> Add issue</a></td>
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
