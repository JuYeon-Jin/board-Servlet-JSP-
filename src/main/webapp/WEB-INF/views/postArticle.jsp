<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<html>
<head>
    <title>상세내용</title>
    <link rel="stylesheet" type="text/css" href="/css/postArticle.css">
</head>
<body>

<div class="container">

    <div class="post-info-container">
        <div class="title-box">
            [<c:out value="${post.categoryName}" />] <c:out value="${post.title}" />
        </div>
        <div class="etc-info-container">
            <div class="date-box">
                <div>등록일시 <c:out value="${post.createdAt}" /></div>
                <div>수정일시 <c:out value="${post.updatedAt}" /></div>
            </div>
            <div class="etc-box">
                <div>작성자 <c:out value="${post.writer}" /></div>
                <div>조회수 <c:out value="${post.views}" /></div>
            </div>
        </div>
    </div>
    <hr class="tableLine1">

    <div class="content-container">
        <c:out value="${post.content}" escapeXml="false" />
    </div>

    <div class="files-container">
        <c:forEach var="file" items="${files}">
            <div class="file-box">
                <img class="download-img" src="/img/download.png">
                <a href="/file/download?seq=${file.id}"><c:out value="${file.fileName}"/></a>
            </div>
        </c:forEach>

    </div>

    <div class="comment-container">
        <div class="comment-item">
            <div class="command-date">2022.03.08 17:20</div>
            <div class="command-content">댓글입니다.</div>
        </div>
        <hr class="tableLine2">
        <div class="comment-item">
            <div class="command-date">2022.03.08 17:20</div>
            <div class="command-content">댓글입니다.2</div>
        </div>
        <hr class="tableLine2">
        <form class="new-comment-box">
            <textarea class="new-comment">댓글을 입력해주세요</textarea>
            <button class="comment-submit-button">등록</button>
        </form>
    </div>
    <hr class="tableLine1">
    <div class="button-container">
        <button class="button-item" type="button" onclick="backToList()">목록</button>
        <button class="button-item" type="button" onclick="redirectTo('/board/update-form?id=<c:out value='${post.id}' />')">수정</button>
        <button class="button-item" type="button" onclick="redirectTo('/board/delete-form?id=<c:out value='${post.id}' />')">삭제</button>
    </div>
</div>

<script>

    function backToList() {
        location.href = "/board/posts";
    }

    function redirectTo(path) {
        location.href = path;
    }

</script>

</body>
</html>
