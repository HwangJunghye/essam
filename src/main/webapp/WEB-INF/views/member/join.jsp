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
	height: 600px;
	margin-left: auto;
    margin-right: auto;
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
	height: 100px;
}
</style>
<script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
</head>
<body>
<%@ include file="../common/header.jsp" %>
<%@ include file="../common/nav.jsp" %>
<section>
<!--  get : 크기제한 있음. url?param1=aaa&param2=bbb -->
<!-- post : 크기제한 없음. url에 정보 표시 X, 
            form이나 ajax만으로 전송 가능 -->  
<form action="memberjoin" method="post">
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
		<th><label for="mbId">아이디</label></th>
		<td>
			<input type="text" name="mbId" id="mbId" required />
			<div id="result"></div>
		</td>
	</tr>
	<tr>
		<th><label for="mbPwd">비밀번호</label></th>
		<td><input type="text" name="mbPwd" id="mbPwd" required /></td>
	</tr>
	<tr>
		<th><label for="mbPwdcheck" >비밀번호 확인</label></th>
		<td><input type="text" name="mbPwdcheck" id="mbPwdcheck" required /></td>
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


<script>
//이메일 정규식

/* function checkemail(){
	let mbId = $('#mbId').val();
	
	//영문자로 시작,그다음 영숫자 또는 .이 하나이상온다.그다음 @,그다음에 영문자 또는 .이 하나이상 온다.
	//.은 정규식에서 임의의 문자라는 뜻을 가지므로, \. escape시켜서 단순 문자로 인식한다.
	let patt = /^[A-Za-z][A-Za-z0-9\.]+@[A-Za-z\.]+$/;
	if(mbId.length==0)
		return printErrorMessage($("#mbId"), "필수 입력입니다.");
	if(patt.test(mbId)==false)
		return printErrorMessage($("#mbId"), "이메일 형식에서 어긋납니다.");
	$("#mbId").text("");
	return true;	
}
	$(function() {
		$('#mbId').on('focusout', function() {
			let mbIdval = $('#mbId').val(); // 입력값 가져오기
			if (mbIdval == '') {
				$('#result').text('이메일을 입력하세요');
			} else {
				$.ajax({
					url : 'checkemail',
					data : {
						mbId : mbIdval
					}, // {이름 : 값}
					dataType : 'json',
					method : 'get' // get일 경우 생략 가능
				}).done(function(data) {
					$('#result').text(data.msg);
				}).fail(function(err) {
					$('#result').text('서버 통신 실패');
				});
			}
		}); // on End */
	//비밀번호 정규식
	/* 1. 암호:

	조건1. 6~20 영문 대소문자

	조건2. 최소 1개의 숫자 혹은 특수 문자를 포함해야 함

	/^(?=.*[a-zA-Z])((?=.*\d)|(?=.*\W)).{6,20}$/

	}); // ready End */
	
</script>
</section>
	<%@ include file="../common/footer.jsp" %>
</body>
</html>