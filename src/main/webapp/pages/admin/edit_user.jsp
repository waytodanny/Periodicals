<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Admin\Edit user</title>

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
    <link href="<c:url value='/components/bootstrap/css/bootstrap.min.css'/>" rel="stylesheet" type="text/css">
    <link href="<c:url value='/components/css/main.css'/>" rel="stylesheet" type="text/css"/>
</head>
<body>

<jsp:include page="../additional/admin_header.jsp"/>

<div id="edit_user" class="container">
    <div class="row">
        <div class="offset-md-2 col-md-6">
            <h2>Modify user</h2>
            <br/>
        </div>
        <br/>
    </div>
    <form method="post" action="edit_user" class="ajax-form"
          success-message="Successfully updated user"
          fail-message="Failed to update user">
        <div class="form-group row">
            <label for="login" class="col-md-2 offset-md-2 col-form-label">Login</label>
            <div class="col-md-6">
                <input id="login" name="login" type="text" required="required"
                       class="form-control here" value="${user.login}" readonly>
            </div>
        </div>
        <div class="form-group row">
            <label for="role_id" class="col-md-2 offset-md-2 col-form-label">Role</label>
            <div class="col-md-6">
                <select id="role_id" name="role_id" required="required" class="custom-select">
                    <c:forEach var="role" items="${applicationScope.roles}">
                        <c:choose>
                            <c:when test="${role.id eq user.role.id}">
                                <option value="${role.id}" selected="selected">${role.name}</option>
                            </c:when>
                            <c:otherwise>
                                <option value="${role.id}">${role.name}</option>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </select>
            </div>
        </div>
        <div class="form-group row">
            <div class="offset-md-4 col-md-6">
                <input type="hidden" name="id" value="${user.id}">
                <button type="submit" class="btn btn-primary">Update user</button>
            </div>
        </div>
        <div class="row">
            <div id="message_box" class="offset-md-4 col-md-6">

            </div>
        </div>
    </form>
</div>

<script src="<c:url value='/components/jquery/jquery.min.js'/>"></script>
<script src="<c:url value='/components/js/formUtils.js'/>"></script>

</body>
</html>