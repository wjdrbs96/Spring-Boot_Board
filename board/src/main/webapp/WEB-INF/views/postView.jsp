<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <style>
        body { font-family: 굴림체; }
        table.table { width: 500px; }
        table td:nth-child(1) { background-color: #eee; }
    </style>
</head>
<body>

<div class="container">

    <h1>게시글 수정</h1>

    <form action="http://localhost:8081/update?postId=${posts.getPostId()}" method="POST">
        <table class="table table-bordered table-condensed">
            <tr>
                <td>게시글 번호</td>
                <td>${posts.getPostId()}</td>
            </tr>
            <tr>
                <td>글 제목</td>
                <td>
                    <textarea class="form-control" name="title" rows="1">${posts.getTitle()}</textarea>
                </td>
            </tr>
            <tr>
                <td>글 내용</td>
                <td>
                    <textarea class="form-control" name="content" rows="10">${posts.getContent()}</textarea>
                </td>
            </tr>

            <tr>
                <td>작성자</td>
                <td>${posts.getName()}</td>
            </tr>
            <tr>
                <td>작성시간</td>
                <td></td>
            </tr>
        </table>

        <button type="submit" class="btn btn-primary">저장</button>
        <a href="http://localhost:8081/comment/list?postId=${posts.getPostId()}" class="btn btn-warning">댓글</a>
        <a href="http://localhost:8081/post/delete?postId=${posts.getPostId()}" class="btn btn-warning" onclick="confirm('삭제하시겠습니까?')">삭제</a>
        <a href="javascript:window.history.back()" class="btn btn-info">돌아가기</a>
    </form>


    <h1>댓글 등록</h1>
    <form action="http://localhost:8081/comment/view?postId=${posts.getPostId()}" method="get">
        <table class="table table-bordered table-condensed">
            <tr>
                <td class="mid" width="100">댓글</td>
                <td width="700">
                    <textarea class="form-control" name="comment" rows="2"></textarea>
                </td>
                <td><input type="text" name="postId" value="${posts.getPostId()}"></td>
            </tr>
        </table>

        <button type="submit" class="btn btn-primary">등록</button>

    </form>

</div>
</body>
</html>