<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:set var="language"
       value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}"
       scope="session"/>

<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="resources" var="rb"/>
<div id="overlay"></div>

<header id="header">
    <div id="hamburger-menu">
        <i class="fa fa-navicon"></i>
    </div>
    <c:if test="${not empty sessionScope.user}">
        <div id="cart-button">
            <i class="fa fa-shopping-cart"></i>
        </div>
    </c:if>
    <nav id="main-nav">
        <ul class="list-inline list-unstyled">
            <li class="nav-item list-inline-item dropdown">
                <a class="btn dropdown-toggle" href="#" id="lcz" data-toggle="dropdown">
                    <i class="fa fa-globe" aria-hidden="true"></i>
                    <fmt:message key="header.language" bundle="${rb}"/>
                </a>
                <div class="dropdown-menu">
                    <a class="dropdown-item" href="change_language?language=ru">
                        <fmt:message key="header.language.rus" bundle="${rb}"/>
                    </a>
                    <a class="dropdown-item" href="change_language?language=en">
                        <fmt:message key="header.language.eng" bundle="${rb}"/>
                    </a>
                </div>
            </li>

            <li class="list-inline-item">
                <a class="btn btn-success navbar-button" href="catalog">
                    <i class="fa fa-folder-open" aria-hidden="true"></i>
                    <fmt:message key="header.catalog" bundle="${rb}"/>
                </a>
            </li>
            <c:choose>
                <c:when test="${empty user}">
                    <li class="list-inline-item">
                        <a class="btn navbar-button" href="registration">
                            <i class="fa fa-user-circle-o" aria-hidden="true"></i>
                            <fmt:message key="header.registration" bundle="${rb}"/>
                        </a>
                    </li>
                    <li class="list-inline-item">
                        <a class="btn navbar-button" href="login">
                            <i class="fa fa-sign-in" aria-hidden="true"></i>
                            <fmt:message key="header.login" bundle="${rb}"/>
                        </a>
                    </li>
                    <li class="list-inline-item">
                        <a class="btn navbar-button disabled">
                            <i class="fa fa-user-secret" aria-hidden="true"></i>
                            <fmt:message key="header.user.guest" bundle="${rb}"/>
                        </a>
                    </li>
                </c:when>
                <c:otherwise>
                    <li class="list-inline-item">
                        <a class="btn navbar-button" href="subscriptions">
                            <i class="fa fa-book" aria-hidden="true"></i>
                            <fmt:message key="header.subscriptions" bundle="${rb}"/>
                        </a>
                    </li>
                    <li class="list-inline-item">
                        <a class="btn navbar-button" href="logout">
                            <i class="fa fa-sign-out" aria-hidden="true"></i>
                            <fmt:message key="header.logout" bundle="${rb}"/>
                        </a>
                    </li>
                    <li class="list-inline-item">
                        <a class="btn navbar-button disabled">
                            <i class="fa fa-id-card-o" aria-hidden="true"></i>
                            <c:out value="${sessionScope.user.login}"/>
                        </a>
                    </li>
                </c:otherwise>
            </c:choose>
        </ul>
    </nav>
</header>

<nav id="sidebar" role="navigation">
    <h2>
        <fmt:message key="page.header.sidebar.genres" bundle="${rb}"/>
    </h2>
    <ul class="nav sidebar-nav">
        <c:forEach var="genre" items="${applicationScope.genres}">
            <li><a href="catalog?genre=${genre.id}">${genre.name}</a></li>
        </c:forEach>
    </ul>
</nav>