<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="language"
       value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}"
       scope="session"/>

<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="resources" var="rb"/>
<html>
<head>
    <title>Issues</title>

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
    <link href="<c:url value='/components/bootstrap/css/bootstrap.min.css'/>" rel="stylesheet" type="text/css">
    <link href="<c:url value='/components/css/main.css'/>" rel="stylesheet" type="text/css"/>
</head>
<body>

<jsp:include page="/pages/additional/header.jsp"/>
<jsp:include page="/pages/additional/cart.jsp"/>

<div id="issues" class="container">
    <h2>${periodical.name}</h2>
    <br/>
    <table class="table table-striped table-hover">
        <thead>
        <tr>
            <th><fmt:message key="page.issues.issue_name" bundle="${rb}"/></th>
            <th><fmt:message key="page.issues.issue_no" bundle="${rb}"/></th>
            <th><fmt:message key="page.issues.issue_date" bundle="${rb}"/></th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="issue" items="${displayedObjects}">
            <tr>
                <td>${issue.name}</td>
                <td>${issue.issueNo}</td>
                <td>${issue.publishDate}</td>
                <td><a class="btn btn-info" href="">
                    <fmt:message key="page.issues.more" bundle="${rb}"/>
                </a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <jsp:include page="/pages/additional/pagination.jsp"/>

</div>

<script src="<c:url value='/components/jquery/jquery.min.js'/>"></script>
<script src="<c:url value='/components/js/popper.min.js'/>"></script>
<script src="<c:url value='/components/bootstrap/js/bootstrap.min.js'/>"></script>
<script src="<c:url value='/components/js/sidebar.js'/>"></script>

</body>
</html>
