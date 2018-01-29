<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Admin\Edit periodical</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
    <link href="<c:url value='/components/bootstrap/css/bootstrap.min.css'/>" rel="stylesheet" type="text/css">
    <link href="<c:url value='/components/css/main.css'/>" rel="stylesheet" type="text/css"/>
</head>
<body>

<jsp:include page="../additional/admin_header.jsp"/>

<div id="edit_periodical" class="container">
    <div class="row">
        <div class="offset-md-2 col-md-6">
            <h2>Modify periodical</h2>
            <br/>
        </div>
        <br/>
    </div>
    <form method="post" action="edit_periodical" class="ajax-form"
          success-message="Successfully updated periodical"
          fail-message="Failed to update periodical">
        <div class="form-group row">
            <label for="name" class="col-md-2 offset-md-2 col-form-label">Name</label>
            <div class="col-md-6">
                <input id="name" name="name" type="text" required="required"
                       class="form-control here" value="${periodical.name}">
            </div>
        </div>
        <div class="form-group row">
            <label for="description" class="col-md-2 offset-md-2 col-form-label">Description</label>
            <div class="col-md-6">
                <textarea id="description" name="description" maxlength="500" rows="6" required="required"
                          class="form-control v-resize">${periodical.description}</textarea>
            </div>
        </div>
        <div class="form-group row">
            <label for="issues_per_year" class="col-md-2 offset-md-2 col-form-label">Issues per year</label>
            <div class="col-md-6">
                <input id="issues_per_year" name="issues_per_year" type="number" min="0"
                       required="required" class="form-control here" value="${periodical.issuesPerYear}">
            </div>
        </div>
        <div class="form-group row">
            <label for="subscription_cost" class="col-md-2 offset-md-2 col-form-label">Subscription cost</label>
            <div class="col-md-6">
                <input id="subscription_cost" name="subscription_cost" type="number" step="0.01" min="0"
                       required="required" class="form-control here" value="${periodical.subscriptionCost}">
            </div>
        </div>
        <div class="form-group row">
            <label class="col-md-2 offset-md-2">Is limited</label>
            <div class="col-md-6">
                <div class="form-check form-check-inline">
                    <label class="form-check-label">
                        <input name="is_limited" type="hidden" value="${periodical.isLimited}"><input class="form-check-input"
                            type="checkbox" onchange="this.previousSibling.value = this.previousSibling.value == 'false'"
                                <c:if test="${periodical.isLimited == true}">checked</c:if>>
                        Yes
                    </label>
                </div>
            </div>
        </div>
        <div class="form-group row">
            <label for="genre_id" class="col-md-2 offset-md-2 col-form-label">Genre</label>
            <div class="col-md-6">
                <select id="genre_id" name="genre_id" required="required" class="custom-select">
                    <c:forEach items="${genres}" var="genre">
                        <c:choose>
                            <c:when test="${genre.id eq periodical.genre.id}">
                                <option value="${genre.id}" selected="selected">${genre.name}</option>
                            </c:when>
                            <c:otherwise>
                                <option value="${genre.id}">${genre.name}</option>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </select>
            </div>
        </div>
        <div class="form-group row">
            <label for="publisher_id" class="col-md-2 offset-md-2 col-form-label">Publisher</label>
            <div class="col-md-6">
                <select id="publisher_id" name="publisher_id" required="required" class="custom-select">
                    <c:forEach items="${publishers}" var="publisher">
                        <c:choose>
                            <c:when test="${publisher.id eq periodical.publisher.id}">
                                <option value="${publisher.id}" selected="selected">${publisher.name}</option>
                            </c:when>
                            <c:otherwise>
                                <option value="${publisher.id}">${publisher.name}</option>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </select>
            </div>
        </div>
        <div class="form-group row">
            <div class="offset-md-4 col-md-6">
                <input type="hidden" name="id" value="${periodical.id}">
                <button type="submit" class="btn btn-primary">Update periodical</button>
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