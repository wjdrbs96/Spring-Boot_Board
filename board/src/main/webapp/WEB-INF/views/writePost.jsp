<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!-- 게시글을 작성하는 페이지  -->

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

    <h1>게시글 등록</h1>

    <form method="post">
        <table class="table table-bordered table-condensed">

            <tr>
                <td>글 제목</td>
                <td>
                    <textarea class="form-control" name="title" rows="1"></textarea>
                </td>
            </tr>
            <tr>
                <td>글 내용</td>
                <td>
                    <textarea class="form-control" name="content" rows="10"></textarea>
                </td>
            </tr>

            <tr>
                <td>작성자</td>
                <td>
                    ${post.getName()}
                </td>
            </tr>
            <tr>
                <td>작성자 번호</td>
                <td>
                    ${post.getMemberId()}
                </td>
            </tr>
            <tr>
                <td>작성시간</td>
                <td>
                    <input type="text" name="time" value="" class="form-control" >
                </td>
            </tr>
        </table>

        <button type="submit" class="btn btn-primary">등록</button>
        <a href="javascript:window.history.back()" class="btn btn-info">돌아가기</a>
    </form>

</div>
</body>
</html>
