<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Admin\Add periodical</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
    <link href="<c:url value='/components/bootstrap/css/bootstrap.min.css'/>" rel="stylesheet" type="text/css">
    <link href="<c:url value='/components/css/main.css'/>" rel="stylesheet" type="text/css"/>
</head>
<body>

<jsp:include page="../additional/admin_header.jsp"/>

<div id="add_periodical" class="container">
    <div class="row">
        <div class="offset-md-2 col-md-6">
            <h2>New periodical</h2>
            <br/>
        </div>
        <br/>
    </div>
    <form method="post" action="add_periodical">
        <div class="form-group row">
            <label for="name" class="col-md-2 offset-md-2 col-form-label">Name</label>
            <div class="col-md-6">
                <input id="name" name="name" type="text" required="required" class="form-control here">
            </div>
        </div>
        <div class="form-group row">
            <label for="description" class="col-md-2 offset-md-2 col-form-label">Description</label>
            <div class="col-md-6">
                <textarea id="description" name="description" maxlength="500" rows="6" required="required"
                          class="form-control v-resize"></textarea>
            </div>
        </div>
        <div class="form-group row">
            <label for="issues_per_year" class="col-md-2 offset-md-2 col-form-label">Issues per year</label>
            <div class="col-md-6">
                <input id="issues_per_year" name="issues_per_year" type="number" min="0"
                       required="required" class="form-control here">
            </div>
        </div>
        <div class="form-group row">
            <label for="subscription_cost" class="col-md-2 offset-md-2 col-form-label">Subscription cost</label>
            <div class="col-md-6">
                <input id="subscription_cost" name="subscription_cost" type="number" step="0.01" min="0"
                       required="required" class="form-control here">
            </div>
        </div>
        <div class="form-group row">
            <label class="col-md-2 offset-md-2">Is limited</label>
            <div class="col-md-6">
                <div class="form-check form-check-inline">
                    <label class="form-check-label">
                        <input name="is_limited" type="hidden" value="false"><input class="form-check-input"
                            type="checkbox" onchange="this.previousSibling.value = this.previousSibling.value == 'false'">
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
                        <option value="${genre.id}">${genre.name}</option>
                    </c:forEach>
                </select>
            </div>
        </div>
        <div class="form-group row">
            <label for="publisher_id" class="col-md-2 offset-md-2 col-form-label">Publisher</label>
            <div class="col-md-6">
                <select id="publisher_id" name="publisher_id" required="required" class="custom-select">
                    <c:forEach items="${publishers}" var="publisher">
                        <option value="${publisher.id}">${publisher.name}</option>
                    </c:forEach>
                </select>
            </div>
        </div>
        <div class="form-group row">
            <div class="offset-md-4 col-md-6">
                <button name="submit" type="submit" class="btn btn-primary">Add periodical</button>
            </div>
        </div>
        <c:if test="${not empty resultMessage}">
            <div class="row">
                <div class="offset-md-4 col-md-6">
                    <div class="alert alert-warning alert-dismissable">
                        <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                            ${resultMessage}
                    </div>
                </div>
            </div>
        </c:if>
    </form>
</div>

<script src="<c:url value='/components/jquery/jquery.min.js'/>"></script>

</body>
</html>
