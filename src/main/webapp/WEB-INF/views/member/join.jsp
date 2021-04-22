<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctxPath" value="<%= request.getContextPath() %>"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="${ctxPath}/resources/css/basic.css">
<script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
</head>
<body>
<%@ include file="../common/header.jsp" %>
<%@ include file="../common/nav.jsp" %>
	<section>
<h1>회원가입 페이지 ${fMsg}</h1>
<!--  get : 크기제한 있음. url?param1=aaa&param2=bbb -->
<!-- post : 크기제한 없음. url에 정보 표시 X, 
            form이나 ajax만으로 전송 가능 -->           
<form action="memberjoin" method="post">
	회원타입 <input type="radio" name="mbType" value="1" checked /> 학생 
			<input type="radio" name="mbType" value="2" /> 강사 <br> 
	아이디 <input type="text" name="mbId" id="mbId" required/>
	<div id="result"><!-- 중복체크 결과 출력 --></div>
	<br>
		비밀번호 <input type="text" name="mbPwd" required /><br>
		비밀번호 확인 <input type="text" name="mbPwdcheck" required /><br> 
		성명 <input type="text" name="mbName" required /><br> 
		닉네임 <input	type="text" name="mbNickName" required /><br> 
		성별 <input type="radio" name="mbGender" value="m" checked /> 남 
			<input type="radio" name="mbGender" value="w" /> 여 <br> 
		생년월일 <input type="date" name="mbBirth" required /><br> 
		연락처 <input type="text" name="mbTel" required /><br> 
		주소 <input type="text" name="mbAddr" required /><br> 
		관심카테고리 1
		<input type="checkbox" name="cate1No" value="1"/>유아
		<input type="checkbox" name="cate1No" value="2"/>초등
		<input type="checkbox" name="cate1No" value="3"/>중등
		<input type="checkbox" name="cate1No" value="4"/>고등
		<input type="checkbox" name="cate1No" value="5"/>특목
		<input type="checkbox" name="cate1No" value="6"/>기타<br>
		관심카테고리 2
		<input type="checkbox" name="cate2No" value="1"/>국어
		<input type="checkbox" name="cate2No" value="2"/>영어
		<input type="checkbox" name="cate2No" value="3"/>수학
		<input type="checkbox" name="cate2No" value="4"/>과학
		<input type="checkbox" name="cate2No" value="5"/>논술
		<input type="checkbox" name="cate2No" value="6"/>독서
		<input type="checkbox" name="cate2No" value="7"/>입시컨설팅
		<input type="checkbox" name="cate2No" value="8"/>기타<br>
		<input type="submit" value="회원가입">
</form>


<script>
function checkemail(){
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
		}); // on End
		
	}); // ready End
</script>
</section>
	<%@ include file="../common/footer.jsp" %>
</body>
</html>