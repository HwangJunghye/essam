<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctxPath" value="<%= request.getContextPath() %>"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>마이페이지</title>
<link rel="stylesheet" type="text/css" href="${ctxPath}/resources/css/basic.css">
<style type="text/css">
	.center{
		margin: auto;
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
</style>
</head>
<body>

<%@ include file="../common/header.jsp" %>
<%@ include file="../common/nav.jsp" %>
<section>
<div id="contents">
	<div id="aside">
		<div id="aside_area">
			<%@ include file="../common/aside.jsp"%>
		</div>
	</div>
	<div id="contents_area">
<!--------- 본문 시작 -------------->

	<form action="memberupdate" method="post">  
	<table class="center">
		<caption>회원가입</caption>
		<div class="modal-table">
		<tr>
			<th>회원타입</th>
			<td>
				${myInfo.mbType == 1?"학생":"강사"}
			</td>
		</tr>
		<tr>
			<th>아이디</th>
			<td>
				${myInfo.mbId}
			</td>
		</tr>
		<tr>
			<th>기존 비밀번호</th>
			<td><input type="text" id="mbPwd" name="mbPwd" /></td>
		</tr>
		<tr>
			<th>새로운 비밀번호</th>
			<td><input type="text" id="newMbPwd" name="newMbPwd" /></td>
		</tr>
		<tr>
			<th>비밀번호 확인</th>
			<td>
				<input type="text" id="mbPwdcheck" name="mbPwdcheck" />
				<input type="button" id="pwChange" value="비밀번호 변경">
			</td>
		</tr>
		<tr>
			<th>성명</th>
			<td>${myInfo.mbName}</td>
		</tr>
		<tr>
			<th>닉네임</th>
			<td><input	type="text" name="mbNickName" value="${myInfo.mbNickName}" required /></td>
		</tr>
		<tr>
			<th>성별</th>
			<td>
				<input type="radio" name="mbGender" value="m" ${myInfo.mbGender=='m'?'checked':''} /> 남 
				<input type="radio" name="mbGender" value="w" ${myInfo.mbGender=='w'?'checked':''} /> 여
			</td>
		</tr>
		<tr>
			<th>생년월일</th>
			<td>
				<input type="date" name="mbBirth" value="${myInfo.mbBirth}" required /> 
			</td>
		</tr>
		<tr>
			<th>연락처</th>
			<td>
				<input type="text" name="mbTel" value="${myInfo.mbTel}" required />
			</td>
		</tr>
		<tr>
			<th>주소</th>
			<td>
				<input type="text" name="mbAddr" value="${myInfo.mbAddr}" required />
				<input type="button" value="주소검색"> 
			</td>
		</tr>
		<tr>
			<th colspan="2">관심카테고리 1</th>
		</tr>
		<tr>
			<td colspan="2">
				<input type="checkbox" name="cate1No" value="1" ${cate1Chk[1]?'checked':''}/>유아
				<input type="checkbox" name="cate1No" value="2" ${cate1Chk[2]?'checked':''}/>초등
				<input type="checkbox" name="cate1No" value="3" ${cate1Chk[3]?'checked':''}/>중등
				<input type="checkbox" name="cate1No" value="4" ${cate1Chk[4]?'checked':''}/>고등
				<input type="checkbox" name="cate1No" value="5" ${cate1Chk[5]?'checked':''}/>특목
				<input type="checkbox" name="cate1No" value="6" ${cate1Chk[6]?'checked':''}/>기타
			</td>
		</tr>
		<tr>
			<th colspan="2">관심카테고리 2</th>
		</tr>
		<tr>
			<td colspan="2">
				<input type="checkbox" name="cate2No" value="1" ${cate2Chk[1]?'checked':''}/>국어
				<input type="checkbox" name="cate2No" value="2" ${cate2Chk[2]?'checked':''}/>영어
				<input type="checkbox" name="cate2No" value="3" ${cate2Chk[3]?'checked':''}/>수학
				<input type="checkbox" name="cate2No" value="4" ${cate2Chk[4]?'checked':''}/>과학
				<input type="checkbox" name="cate2No" value="5" ${cate2Chk[5]?'checked':''}/>논술
				<input type="checkbox" name="cate2No" value="6" ${cate2Chk[6]?'checked':''}/>독서
				<input type="checkbox" name="cate2No" value="7" ${cate2Chk[7]?'checked':''}/>입시컨설팅
				<input type="checkbox" name="cate2No" value="8" ${cate2Chk[8]?'checked':''}/>기타	
			</td>
		</tr>
		<tr>
			<th colspan="2">
				<input type="submit" value="정보수정" />
				<input type="reset" value="취소" />
			</th>
		</tr>
	</table> 		
</form>
<!--------- 본문 끝 -------------->
	</div>
</div>
</section>
<%@ include file="../common/footer.jsp" %>

<script type="text/javascript">
	$(function(){
		$('#pwChange').on('click',function(){
			let mbPwd = $('#mbPwd').val();
			let newMbPwd = $('#newMbPwd').val();
			let mbPwdcheck = $('#mbPwdcheck').val();
			
			if(mbPwd==''){
				alert('기존 비밀번호을 입력하세요');
				return false;
			}
			if (newMbPwd==''){
				alert('새로운 비밀번호를 입력하세요');
				return false;
			}
			if (newMbPwd!=mbPwdcheck){
				alert('비밀번호가 일치하지 않습니다');
				return false;
			}
			
			// 비밀번호 변경 ajax 요청
			$.ajax({
				url:'${ctxPath}/changepassword',
				data:{mbPwd:mbPwd,newMbPwd:newMbPwd},
				method: 'post',
				dataType:'json'
			}).done(function(data){
				alert(data.msg);
				return true;
			}).fail(function(err){
				alert('서버와 연결 할 수 없습니다.');
				return false;
			}); // ajax End
		}); // on End
	}); // ready End
</script>
</body>
</html>