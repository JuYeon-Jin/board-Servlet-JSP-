<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" type="text/css" href="/css/postList.css">
</head>
<body>

<div class="container">

    <form class="search-container">
        <div class="search-date">
            등록일
            <input type="date" class="date-box-left"> ~
            <input type="date" class="date-box-right">
        </div>
        <div class="search-category">
            <select class="select-box">
                <option class="option-box">1번</option>
                <option class="option-box">2번</option>
                <option class="option-box">3번</option>
            </select>
        </div>
        <div class="search-keywards">
            <input type="text" placeholder="검색어를 입력해 주세요. (제목+작성자+내용)" class="keywards-box">
        </div>
        <div class="botton-box">
            <button class="search-button">검색</button>
        </div>
    </form>

    <div class="posts-container">
        <hr class="tableLine2">
        <div class="posts-title">
            <div class="post-category">카테고리</div>
            <div class="post-files"></div>
            <div class="post-title">제목</div>
            <div class="post-writer">작성자</div>
            <div class="post-views">조회수</div>
            <div class="post-created">등록 일시</div>
            <div class="post-updated">수정 일시</div>
        </div>
        <hr class="tableLine1">
        <c:forEach var="post" items="${posts}">
        <div class="posts-item">
            <div class="post-category"><c:out value="${post.categoryName}" /></div>
            <div class="post-files"></div>
            <div class="post-title" id="<c:out value='${post.id}' />">
                <c:out value="${post.title}" />
            </div>
            <div class="post-writer"><c:out value="${post.writer}" /></div>
            <div class="post-views"><c:out value="${post.views}" /></div>
            <div class="post-created"><c:out value="${post.createdAt}" /></div>
            <div class="post-updated">
                <c:choose>
                    <c:when test="${post.updatedAt != null}">
                        <c:out value="${post.updatedAt}" />
                    </c:when>
                    <c:otherwise>
                        -
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
            <hr class="tableLine2">
        </c:forEach>

        <form class="pages">
            <button>◀</button>
            1 2 3 4 5 6 7 ..
            <button>▶</button>
        </form>

        <form class="new-post">
            <button class="post-submit-button">등록</button>
        </form>

    </div>

</div>

</body>
</html>
