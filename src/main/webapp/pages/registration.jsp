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
    <title>Registration</title>

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
    <link href="<c:url value='/components/bootstrap/css/bootstrap.min.css'/>" rel="stylesheet" type="text/css">
    <link href="<c:url value='/components/css/main.css'/>" rel="stylesheet" type="text/css"/>
</head>
<body class="no-header">

<jsp:include page="additional/header.jsp"/>

<div class="login-wrapper centered-flex-column">
    <form class="form-signin" method="post" action="registration">
        <h2 class="form-signin-heading">
            <fmt:message key="page.registration.title" bundle="${rb}"/>
        </h2>
        <input type="text" class="form-control" name="login" required="" maxlength="50" autofocus=""
               placeholder="<fmt:message key="page.registration.placeholder.login" bundle="${rb}"/>"/>
        <input type="email" class="form-control" name="email" required="" maxlength="254" autofocus=""
               placeholder="<fmt:message key="page.registration.placeholder.email" bundle="${rb}"/>"/>
        <input type="password" class="form-control" name="password" required="" maxlength="50"
               placeholder="<fmt:message key="page.registration.placeholder.pass" bundle="${rb}"/>"/>
        <button class="btn btn-lg btn-primary btn-block" type="submit">
            <span>
                <fmt:message key="page.registration.button.enter" bundle="${rb}"/>
            </span>
        </button>
        <c:if test="${not empty registrationErrorMessage}">
        <span class="help-block">
            <fmt:message key="page.registration.error.exists" bundle="${rb}"/>,
            <a href="login">
                <fmt:message key="page.registration.link.login" bundle="${rb}"/>?
            </a>
        </span>
        </c:if>
    </form>
</div>

<script src="<c:url value='/components/jquery/jquery.min.js'/>"></script>
<script src="<c:url value='/components/js/popper.min.js'/>"></script>
<script src="<c:url value='/components/bootstrap/js/bootstrap.min.js'/>"></script>
<script src="<c:url value='/components/js/sidebar.js'/>"></script>

</body>
</html>
