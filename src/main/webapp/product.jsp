<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<body>
    <h2>PRODUCT PAGE</h2>
    <p>
        <br/> Product id: ${product.id}
        <br/> Product name: ${product.name}
        <br/> <a href="/index.jsp">main page</a>
        <br/> <a href="./productsInBucket.do?id=${product.id}">Add this product to a bucket</a>
    </p>
    <h3>PRODUCTS BUCKET</h3>
    <ul>
        <c:forEach var="productInBucket" items="${productsInBucket}">
            <li>
                <a href="./product.do?id=${productInBucket.key.id}">${productInBucket.key.name}</a> =
                ${productInBucket.value} (<a href="./productsRemoveFromBucket.do?id=${productInBucket.key.id}">X</a>)
            </li>
        </c:forEach>
    </ul>
    <img src="">
</body>
</html>
