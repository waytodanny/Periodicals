<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Admin\Periodical issues</title>

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
    <link href="<c:url value='/components/bootstrap/css/bootstrap.min.css'/>" rel="stylesheet" type="text/css">
    <link href="<c:url value='/components/css/main.css'/>" rel="stylesheet" type="text/css"/>
</head>
<body>
<jsp:include page="../additional/admin_header.jsp"/>

<div id="issues" class="container">
    <div class="row">
        <h2>${periodical.name}</h2>

        <br/>
    </div>
    <table class="table table-striped table-hover">
        <thead>
        <tr class="row">
            <th class="col-sm-4">Name</th>
            <th class="col-sm-2">Issue No.</th>
            <th class="col-sm-2">Publish date</th>
            <th class="col-sm-4"></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="issue" items="${displayedObjects}">
            <tr class="row">
                <td class="col-sm-4">${issue.name}</td>
                <td class="col-sm-2">${issue.issueNo}</td>
                <td class="col-sm-2">${issue.publishDate}</td>
                <td class="col-sm-2 table-success text-center table-default-link">
                    <a href="edit_issue?id=${issue.id}">
                        <i class="fa fa-pencil" aria-hidden="true"></i> Edit</a></td>
                <td class="col-sm-2 table-danger text-center table-default-link">
                    <a href="delete_issue?id=${issue.id}">
                        <i class="fa fa-trash" aria-hidden="true"></i> Delete</a></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <div class="row">
        <jsp:include page="/pages/additional/pagination.jsp"/>
    </div>
</div>
</body>
</html>