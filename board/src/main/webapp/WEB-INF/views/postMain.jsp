<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!--

게시글 검색을 하면 Main 페이지

1. 게시글 검색을 하고 title 링크를 누르면 해당 게시글 내용 페이지인 PostView.jsp 로 이동

2. "글 쓰기" 버튼을 누르면 WritePost.jsp로 이동


-->

<!DOCTYPE html>
<html lang="ko">
<head>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Insert title here</title>
</head>
<body>


<div class="container">

    <form method="post" class="form-inline">
        <div class="form-group">
            <label><a href="http://localhost:8081/post/list">검색</a></label>
            <input type="text" size=20 class="form-control" name="srchText"  placeholder="제목을 입력하세요" >
        </div>
        <button type="submit" class="btn btn-primary">조회</button>
    </form>

    <table class="table table-hover table table-striped">


        <tr>
            <th>번호</th>
            <th>작성자</th>
            <th>제목</th>
            <th>조회수</th>
            <th>날짜</th>
        </tr>

        <c:forEach items="${posts}" var="post">
            <tr>
                <th>${post.getPostId()}</th>
                <th>${post.getMemberId()}</th>
                <th><a href="http://localhost:8081/post/View?id=${post.getPostId()}">${post.getTitle()}</a></th>
                <th>${post.getCount()}</th>
                <th>${post.getCreateDateTime()}</th>
            </tr>
        </c:forEach>

    </table>

    <a href="http://localhost:8081/post/write" class="btn btn-default pull-right">글쓰기</a>

    <%--<ul class="pagination">
       <c:forEach var="i" begin="1" end="${totalPage}" step="1">
           <li class=<c:if test='${i} == ${page} ? "active" : ""'/>>
               <a href='/post/list?page=${i}&pageSize=${pageSize}'>${i}</a>
           </li>
       </c:forEach>
    </ul>--%>
</div>
</body>
</html>
