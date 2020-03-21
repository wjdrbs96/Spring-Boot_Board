<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- 회원 가입을 하는 페이지 -->


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
        input.form-control, select.form-control { width: 200px; }
    </style>
</head>
<body>

<div class="container">
    <h1>회원가입</h1>
    <hr />

    <form action="http://localhost:8081/register" method="post">
        <div class="form-group">
            <label>사용자 아이디</label>
            <input type="text" class="form-control" name="loginId" value="${loginId}">
        </div>
        <div class="form-group">
            <label>비밀번호1</label>
            <input type="password" class="form-control" name="password1" value="${password1}">
        </div>
        <div class="form-group">
            <label>비밀번호2</label>
            <input type="password" class="form-control" name="password2" value="${password2}" />
        </div>
        <div class="form-group">
            <label>이름</label>
            <input type="text" class="form-control" name="name" value="${name}" />
        </div>
        <div class="form-group">
            <label>닉네임</label>
            <input type="text" class="form-control" name="nickname" value="${nickname}" />
        </div>
        <div class="form-group">
            <label>이메일</label>
            <input type="email" class="form-control" name="email" value="${email}" />
        </div>

        <button type="submit" class="btn btn-primary">
            <i class="glyphicon glyphicon-ok"></i> 회원가입
        </button>
        <a href="http://localhost:8081/login" class="btn btn-default">처음으로</a>
    </form>

    <hr />

    <c:if test="${error != null}">
        <div class="alert alert-danger">
            회원가입 실패: ${error}
        </div>
    </c:if>


</div>
</body>
</html>