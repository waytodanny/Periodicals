<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<c:set var="language"
       value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}"
       scope="session"/>

<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="resources" var="rb"/>
<html>
<head>
    <title>Error</title>

    <link href="<c:url value='/components/bootstrap/css/bootstrap.min.css'/>" rel="stylesheet" type="text/css">
    <link href="<c:url value='/components/css/main.css'/>" rel="stylesheet" type="text/css"/>
</head>
<body class="no-header">
<div id="error" class="centered-flex-column">
    <img class="error-image" src="<c:url value='/components/img/error-icon.png'/>">
    <h1 class="error-title">
        <fmt:message key="page.error.main_message" bundle="${rb}"/>
    </h1>
    <span>
         <fmt:message key="page.error.explanation" bundle="${rb}"/>
    </span>
</div>
</body>
</html>
