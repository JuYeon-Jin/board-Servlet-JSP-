<%@ page import="com.study.connection.ConnectionTest" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>
</head>
<body>
<h1><%= "Hello World!" %>
</h1>
<br/>
<a href="hello-servlet">Hello Servlet</a>


<%

    ConnectionTest t = new ConnectionTest();
    out.println(t.getConnection());

%>

<br/>
<!-- 게시물 목록 페이지로 이동하는 버튼 -->
<form action="/board1/posts" method="get" style="margin-top: 20px;">
    <button type="submit">게시물 목록 보기</button>
</form>


</body>
</html>
