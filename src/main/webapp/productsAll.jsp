<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<body>
    <h2>PRODUCTS PAGE</h2>
    <ul>
        <c:forEach var="item" items="${allProducts}">
            <li>
                <a href="./product.do?id=${item.getId()}">${item.getName()}</a>
            </li>
        </c:forEach>
    </ul>
</body>
</html>
