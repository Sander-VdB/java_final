<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
{ headings: ["Username"],
  users: [
  <c:forEach var="user" items="${users}" varStatus="status">
    ["${user.username}"]
    <c:if test="${!status.last}">,</c:if>
  </c:forEach>
  ]
}
