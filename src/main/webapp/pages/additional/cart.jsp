<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:set var="language"
       value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}"
       scope="session"/>

<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="resources" var="rb"/>
<fmt:setBundle basename="resources" var="rb"/>
<div id="cart">
    <h2>
        <fmt:message key="page.cart.title" bundle="${rb}"/>
    </h2>
    <form method="post" action="remove_from_cart">
        <ul class="cart-items list-unstyled">
            <c:forEach var="periodical" items="${sessionScope.cart.items}">
                <li>
                        ${periodical.name}
                    <div class="cart-price">
                        $${periodical.subscriptionCost}
                    </div>
                    <button name="item_id" type="submit"
                            class="fa fa-remove cart-item-remove"
                            value="${periodical.id}"></button>
                </li>
            </c:forEach>
        </ul>
    </form>

    <div class="cart-total">
        <p>
            <fmt:message key="page.cart.total" bundle="${rb}"/>:
            <span>$
                <c:if test="${sessionScope.cart.totalValue gt 0}">
                    <fmt:formatNumber value="${sessionScope.cart.totalValue}" minFractionDigits="0"/>
                </c:if>
            </span>
        </p>
    </div>

    <c:if test="${fn:length(sessionScope.cart.items) gt 0}">
        <form method="post" action="subscribe">
            <input type="submit"
                   class="btn btn-success btn-lg btn-block cart-checkout"
                   value="<fmt:message key="page.cart.checkout" bundle="${rb}"/>">
        </form>
    </c:if>
</div>