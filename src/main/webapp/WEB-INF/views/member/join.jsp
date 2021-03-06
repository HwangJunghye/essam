<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctxPath" value="<%= request.getContextPath() %>"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입</title>
<link rel="stylesheet" type="text/css" href="${ctxPath}/resources/css/basic.css">
<link rel="icon" href="${ctxPath}/resources/images/favicon_essam.ico" type="image/x-icon">
<link rel="shortcut icon" href="${ctxPath}/resources/images/favicon_essam.ico" type="image/x-icon">
<style type="text/css">
.center{
	height: 500px;
	margin-left: auto;
    margin-right: auto;
}
input[type="text"],input[type="password"],input[type="date"] {
  width:200px;
  height:25px;
  font-size:15px;
}
td{
	padding : 5px;
}
.modal-table{
	display:table;
	position:relative;
	width:100%;
	height:100px;
}
input[type="submit"], input[type="button"], input[type="reset"]{
	width: 70px;
	height: 36px;
	box-shadow: 3px 3px 4px 1px rgba(0, 0, 0, 0.1);
	border-radius: 10px;
	color: #FFFFFF;
	text-align: center;
	border: none;
	cursor: pointer;
}
#joinBtn{
	height: 70px;
}
#mbAddr{
	width:400px;
	height:25px;
	font-size:15px;
}
</style>
</head>
<body>
<%@ include file="../common/header.jsp" %>
<%@ include file="../common/nav.jsp" %>
<section>
<!--  get : 크기제한 있음. url?param1=aaa&param2=bbb -->
<!-- post : 크기제한 없음. url에 정보 표시 X, 
            form이나 ajax만으로 전송 가능 -->  
<form action="memberjoin" method="post" onsubmit="return joinFormCheck()">
<br/><br/>
<table class="center">
	<tr>
		<th>회원타입</th>
		<td>
			<label><input type="radio" name="mbType" value="1" checked /> 학생</label>
			<label><input type="radio" name="mbType" value="2" /> 강사</label>
		</td>
	</tr>
	<tr>
		<th><label for="mbIdInput">아이디</label></th>
		<td>
			<input type="text" name="mbId" id="mbIdInput" placeholder="이메일형식으로 입력" required />
			<div id="idCheckResult"></div>
		</td>
	</tr>
	<tr>
		<th><label for="mbPwd">비밀번호</label></th>
		<td><input type="password" name="mbPwd" id="mbPwd" required />
			<div id="pwdCheckResult"></div>
		</td>
	</tr>
	<tr>
		<th><label for="mbPwdcheck" >비밀번호 확인</label></th>
		<td><input type="password" name="mbPwdcheck" id="mbPwdcheck" required />
			<div id="pwdMatchResult"></div>
		</td>
	</tr>
	<tr>
		<th><label for="mbName" >성명</label></th>
		<td><input type="text" name="mbName" id="mbName" required /></td>
	</tr>
	<tr>
		<th><label for="mbNickName">닉네임</label></th>
		<td><input	type="text" name="mbNickName" id="mbNickName" required /></td>
	</tr>
	<tr>
		<th>성별</th>
		<td>
			<label><input type="radio" name="mbGender" value="m" checked /> 남</label> 
			<label><input type="radio" name="mbGender" value="w" /> 여</label> 
		</td>
	</tr>
	<tr>
		<th><label for="mbBirth">생년월일</label></th>
		<td>
			<input type="date" name="mbBirth" id="mbBirth" min="1900-01-01" max="2100-12-31" required /> 
		</td>
	</tr>
	<tr>
		<th><label for="mbTel">연락처</label></th>
		<td>
			<input type="text" name="mbTel" id="mbTel" required />
		</td>
	</tr>
	<tr>
		<th><label for="mbAddr">주소</label></th>
		<td>
			<input type="text" name="mbAddr" id="mbAddr" required />
		</td>
	</tr>
	<tr>
		<th colspan="2">관심카테고리 1</th>
	</tr>
	<tr>
		<td colspan="2">
			<label><input type="checkbox" name="cate1No" value="1"/>유아</label>
			<label><input type="checkbox" name="cate1No" value="2"/>초등</label>
			<label><input type="checkbox" name="cate1No" value="3"/>중등</label>
			<label><input type="checkbox" name="cate1No" value="4"/>고등</label>
			<label><input type="checkbox" name="cate1No" value="5"/>특목</label>
			<label><input type="checkbox" name="cate1No" value="6"/>기타</label>
		</td>
	</tr>
	<tr>
		<th colspan="2">관심카테고리 2</th>
	</tr>
	<tr>
		<td colspan="2">
			<label><input type="checkbox" name="cate2No" value="1"/>국어</label>
			<label><input type="checkbox" name="cate2No" value="2"/>영어</label>
			<label><input type="checkbox" name="cate2No" value="3"/>수학</label>
			<label><input type="checkbox" name="cate2No" value="4"/>과학</label>
			<label><input type="checkbox" name="cate2No" value="5"/>논술</label>
			<label><input type="checkbox" name="cate2No" value="6"/>독서</label>
			<label><input type="checkbox" name="cate2No" value="7"/>입시컨설팅</label>
			<label><input type="checkbox" name="cate2No" value="8"/>기타</label>
		</td>
	</tr>
	<tr>
		<th colspan="2" id="joinBtn">
			<input type="submit" class="${mbColor}" value="회원가입" />
			<input type="reset" class="${mbColor}" value="취소" />
		</th>
	</tr>
</table> 
</form>
</section>
	<%@ include file="../common/footer.jsp" %>

<script src="${ctxPath}/resources/js/join.js"></script>
<script>
	$(function(){
		joinReady({ctxPath:'${ctxPath}'});
	}); // ready End
</script>
</body>
</html>