<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!-- 게시글을 작성하는 페이지  -->

<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"
            integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN"
            crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"
            integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q"
            crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"
            integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl"
            crossorigin="anonymous"></script>

    <link href="https://fonts.googleapis.com/css?family=Jua" rel="stylesheet">
    <style>
        body { font-family: 굴림체; }
        table.table { width: 500px; }
        table td:nth-child(1) { background-color: #eee; }
        h1 {
            margin-top: 30px;
            margin-bottom: 10px;
        }
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
                    ${member.getName()}
                </td>
            </tr>
            <tr>
                <td>닉네임</td>
                <td>
                    <input type="text" name="nickname" value="${member.getNickName()}" class="form-control" readonly>
                </td>
            </tr>
            <tr>
                <td>사용자번호</td>
                <td>
                    <input type="text" name="memberId" value="${member.getMemberId()}" class="form-control" readonly>
                </td>
            </tr>
            <tr>
                <td>작성시간</td>
                <td>
                    <input type="text" name="time" value="${time}" class="form-control" readonly >
                </td>
            </tr>
        </table>

        <button type="submit" class="btn btn-primary">등록</button>
        <a href="javascript:window.history.back()" class="btn btn-info">돌아가기</a>
    </form>

</div>
</body>
</html>
