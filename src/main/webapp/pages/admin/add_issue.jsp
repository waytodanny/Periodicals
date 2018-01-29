<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Admin\Add  issue</title>

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
    <link href="<c:url value='/components/bootstrap/css/bootstrap.min.css'/>" rel="stylesheet" type="text/css">
    <link href="<c:url value='/components/css/main.css'/>" rel="stylesheet" type="text/css"/>
</head>
<body>

<jsp:include page="../additional/admin_header.jsp"/>

<div id="add_issue" class="container">
    <div class="row">
        <div class="offset-md-2 col-md-6">
            <h2>New issue</h2>
            <br/>
        </div>
        <br/>
    </div>
    <form method="post" action="add_issue" class="ajax-form"
          success-message="Successfully added issue"
          fail-message="Failed to add issue">
        <div class="form-group row">
            <label for="name" class="col-md-2 offset-md-2 col-form-label">Name</label>
            <div class="col-md-6">
                <input id="name" name="name" type="text" required="required" class="form-control here">
            </div>
        </div>
        <div class="form-group row">
            <label for="issue_no" class="col-md-2 offset-md-2 col-form-label">Issue No.</label>
            <div class="col-md-6">
                <input id="issue_no" name="issue_no" type="number" min="0"
                       required="required" class="form-control here">
            </div>
        </div>
        <div class="form-group row">
            <div class="offset-md-4 col-md-6">
                <input type="hidden" name="periodical" value="${periodical_id}">
                <button name="submit" type="submit" class="btn btn-primary">Add issue</button>
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