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
<link rel="icon" href="${ctxPath}/resources/images/favicon_essam.ico" type="image/x-icon">
<link rel="shortcut icon" href="${ctxPath}/resources/images/favicon_essam.ico" type="image/x-icon">
<style type="text/css">
	.center{
	
		margin: auto;
		width: 600px;
		height:650px;
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
	.object{
	width:150px;
	text-align:right;
	}
	.object_con{
	text-align:left;
	}
	
	input[type="submit"]{
	width: 70px;
	height: 36px;
	box-shadow: 3px 3px 4px 1px rgba(0, 0, 0, 0.1);
	border-radius: 10px;
	color: #FFFFFF;
	text-align: center;
	border: none;
	cursor: pointer;
	}
	input[type="button"]{
	width: 70px;
	height: 36px;
	box-shadow: 3px 3px 4px 1px rgba(0, 0, 0, 0.1);
	border-radius: 10px;
	color: #FFFFFF;
	text-align: center;
	border: none;
	cursor: pointer;
	}
	input[type="reset"]{
	width: 70px;
	height: 36px;
	box-shadow: 3px 3px 4px 1px rgba(0, 0, 0, 0.1);
	border-radius: 10px;
	color: #FFFFFF;
	text-align: center;
	border: none;
	cursor: pointer;
	}
</style>
<script src="${ctxPath}/resources/js/mypage.js"></script>

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
		
		<tr>
			<th class="object">회원타입</th>
			<td class="object_con">&emsp;${myInfo.mbType == 1?"학생":"강사"}</td>
		</tr>
		<tr>
			<th class="object">아이디</th>
			<td class="object_con">&emsp;${myInfo.mbId}</td>
		</tr>
		<tr>
			<th class="object">기존 비밀번호</th>
			<td class="object_con">&emsp;<input type="text" id="mbPwd" name="mbPwd" /></td>
		</tr>
		<tr>
			<th class="object">새로운 비밀번호</th>
			<td class="object_con">&emsp;<input type="text" id="newMbPwd" name="newMbPwd" /></td>
		</tr>
		<tr>
			<th class="object">비밀번호 확인</th>
			<td class="object_con">
				&emsp;<input type="text" id="mbPwdcheck" name="mbPwdcheck" />
				&nbsp;<input type="button" class="${mbColor}" id="pwChange" value="비밀번호 변경">
			</td>
		</tr>
		<tr>
			<th class="object">성명</th>
			<td class="object_con">&emsp;${myInfo.mbName}</td>
		</tr>
		<tr>
			<th class="object">닉네임</th>
			<td class="object_con">&emsp;<input	type="text" name="mbNickName" value="${myInfo.mbNickName}" required /></td>
		</tr>
		<tr>
			<th class="object">성별</th>
			<td class="object_con">
				&emsp;<input type="radio" name="mbGender" value="m" ${myInfo.mbGender=='m'?'checked':''} /> 남 
				&nbsp;<input type="radio" name="mbGender" value="w" ${myInfo.mbGender=='w'?'checked':''} /> 여
			</td>
		</tr>
		<tr>
			<th class="object">생년월일</th>
			<td class="object_con">
				&emsp;<input type="date" name="mbBirth" value="${myInfo.mbBirth}" required /> 
			</td>
		</tr>
		<tr>
			<th class="object">연락처</th>
			<td class="object_con">
				&emsp;<input type="text" name="mbTel" value="${myInfo.mbTel}" required />
			</td>
		</tr>
		<tr>
			<th class="object">주소</th>
			<td class="object_con">
				&emsp;<input type="text" name="mbAddr" value="${myInfo.mbAddr}" required />
				&nbsp;<input type="button" class="${mbColor}" value="주소검색"> 
			</td>
		</tr>
		<tr>
			<th colspan="2" class="object_con">관심카테고리 1</th>
		</tr>
		<tr>
			<td colspan="2" class="object_con">
				&emsp;<input type="checkbox" name="cate1No" value="1" ${cate1Chk[1]?'checked':''}/>유아
				&nbsp;<input type="checkbox" name="cate1No" value="2" ${cate1Chk[2]?'checked':''}/>초등
				&nbsp;<input type="checkbox" name="cate1No" value="3" ${cate1Chk[3]?'checked':''}/>중등
				&nbsp;<input type="checkbox" name="cate1No" value="4" ${cate1Chk[4]?'checked':''}/>고등
				&nbsp;<input type="checkbox" name="cate1No" value="5" ${cate1Chk[5]?'checked':''}/>특목
				&nbsp;<input type="checkbox" name="cate1No" value="6" ${cate1Chk[6]?'checked':''}/>기타
			</td>
		</tr>
		<tr>
			<th colspan="2" class="object_con">관심카테고리 2</th>
		</tr>
		<tr>
			<td colspan="2" class="object_con">
				&emsp;<input type="checkbox" name="cate2No" value="1" ${cate2Chk[1]?'checked':''}/>국어
				&nbsp;<input type="checkbox" name="cate2No" value="2" ${cate2Chk[2]?'checked':''}/>영어
				&nbsp;<input type="checkbox" name="cate2No" value="3" ${cate2Chk[3]?'checked':''}/>수학
				&nbsp;<input type="checkbox" name="cate2No" value="4" ${cate2Chk[4]?'checked':''}/>과학
				&nbsp;<input type="checkbox" name="cate2No" value="5" ${cate2Chk[5]?'checked':''}/>논술
				&nbsp;<input type="checkbox" name="cate2No" value="6" ${cate2Chk[6]?'checked':''}/>독서
				&nbsp;<input type="checkbox" name="cate2No" value="7" ${cate2Chk[7]?'checked':''}/>입시컨설팅
				&nbsp;<input type="checkbox" name="cate2No" value="8" ${cate2Chk[8]?'checked':''}/>기타	
			</td>
		</tr>
		<tr>
			<th colspan="2">
				<input type="submit" class="${mbColor}" value="정보수정" />
				<input type="reset" class="${mbColor}" value="취소" />
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
		mypageReady({ctxPath:'${ctxPath}'});
	}); // ready End
</script>
</body>
</html>