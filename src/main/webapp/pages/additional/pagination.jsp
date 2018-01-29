<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="language"
       value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}"
       scope="session"/>

<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="resources" var="rb"/>
<c:if test="${pagesCount > 0}">
    <ul class="pagination">
        <c:choose>
            <c:when test="${currentPage != 1}">
                <li class="page-item">
                    <a class="page-link" href="${pageHrefTemplate}page=${currentPage - 1}">
                        <fmt:message key="pagination.btn.prev" bundle="${rb}"/>
                    </a>
                </li>
            </c:when>
            <c:otherwise>
                <li class="page-item disabled">
                <span class="page-link">
                   <fmt:message key="pagination.btn.prev" bundle="${rb}"/>
                </span>
                </li>
            </c:otherwise>
        </c:choose>

        <c:forEach begin="1" end="${pagesCount}" var="i">
            <c:choose>
                <c:when test="${currentPage eq i}">
                    <li class="page-item active">
                    <span class="page-link">
                            ${i}
                        <span class="sr-only">(current)</span>
                    </span>
                    </li>
                </c:when>
                <c:otherwise>
                    <li class="page-item">
                        <a class="page-link" href="${pageHrefTemplate}page=${i}">${i}</a>
                    </li>
                </c:otherwise>
            </c:choose>
        </c:forEach>

        <c:choose>
            <c:when test="${currentPage lt pagesCount}">
                <li class="page-item">
                    <a class="page-link" href="${pageHrefTemplate}page=${currentPage + 1}">
                        <fmt:message key="pagination.btn.next" bundle="${rb}"/>
                    </a>
                </li>
            </c:when>
            <c:otherwise>
                <li class="page-item disabled">
                <span class="page-link">
                  <fmt:message key="pagination.btn.next" bundle="${rb}"/>
                </span>
                </li>
            </c:otherwise>
        </c:choose>
    </ul>
</c:if>