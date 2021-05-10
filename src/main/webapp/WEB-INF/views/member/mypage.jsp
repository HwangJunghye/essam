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
<link rel="stylesheet" type="text/css" href="${ctxPath}/resources/css/mypage.css">
<script src="${ctxPath}/resources/js/mypage.js"></script>
<style type="text/css">
#center{
	height: 600px;
	margin-left: auto;
    margin-right: auto;
}
.object{
	text-align: left;
}
#mTable{
	height: 100px;
}
input[type="text"], input[type="date"] {
  width:200px;
  height:25px;
  font-size:15px;
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
	<table id="center">
		<tr>
			<th class="object">회원타입</th>
			<td class="object_con">&emsp;${myInfo.mbType == 1?"학생":"강사"}</td>
		</tr>
		<tr>
			<th class="object">아이디</th>
			<td class="object_con">&emsp;${myInfo.mbId}</td>
		</tr>
		<tr>
			<th class="object"><label for="mbPwd">기존 비밀번호</label></th>
			<td class="object_con">&emsp;<input type="text" id="mbPwd" name="mbPwd" /></td>
		</tr>
		<tr>
			<th class="object"><label for="newMbPwd">새로운 비밀번호</label></th>
			<td class="object_con">&emsp;<input type="text" id="newMbPwd" name="newMbPwd" /></td>
		</tr>
		<tr>
			<th class="object"><label for="mbPwdcheck">비밀번호 확인</label></th>
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
			<th class="object"><label for="mbNickName">닉네임</label></th>
			<td class="object_con">&emsp;<input	type="text" name="mbNickName" id="mbNickName" value="${myInfo.mbNickName}" required /></td>
		</tr>
		<tr>
			<th class="object">성별</th>
			<td class="object_con">
				&emsp;<label><input type="radio" name="mbGender" value="m" ${myInfo.mbGender=='m'?'checked':''} /> 남</label> 
				&nbsp;<label><input type="radio" name="mbGender" value="w" ${myInfo.mbGender=='w'?'checked':''} /> 여</label>
			</td>
		</tr>
		<tr>
			<th class="object"><label for="mbBirth">생년월일</label></th>
			<td class="object_con">
				&emsp;<input type="date" name="mbBirth" id="mbBirth" value="${myInfo.mbBirth}" required /> 
			</td>
		</tr>
		<tr>
			<th class="object"><label for="mbTel">연락처</label></th>
			<td class="object_con">
				&emsp;<input type="text" name="mbTel" id="mbTel" value="${myInfo.mbTel}" required />
			</td>
		</tr>
		<tr>
			<th class="object"><label for="mbAddr">주소</label></th>
			<td class="object_con">
				&emsp;<input type="text" name="mbAddr" id="mbAddr" value="${myInfo.mbAddr}" required />
			</td>
		</tr>
		<tr>
			<th colspan="2" class="object_con">관심카테고리 1</th>
		</tr>
		<tr>
			<td colspan="2" class="object_con">
				&emsp;<label><input type="checkbox" name="cate1No" value="1" ${cate1Chk[1]?'checked':''}/>유아</label>
				&nbsp;<label><input type="checkbox" name="cate1No" value="2" ${cate1Chk[2]?'checked':''}/>초등</label>
				&nbsp;<label><input type="checkbox" name="cate1No" value="3" ${cate1Chk[3]?'checked':''}/>중등</label>
				&nbsp;<label><input type="checkbox" name="cate1No" value="4" ${cate1Chk[4]?'checked':''}/>고등</label>
				&nbsp;<label><input type="checkbox" name="cate1No" value="5" ${cate1Chk[5]?'checked':''}/>특목</label>
				&nbsp;<label><input type="checkbox" name="cate1No" value="6" ${cate1Chk[6]?'checked':''}/>기타</label>
			</td>
		</tr>
		<tr>
			<th colspan="2" class="object_con">관심카테고리 2</th>
		</tr>
		<tr>
			<td colspan="2" class="object_con">
				&emsp;<label><input type="checkbox" name="cate2No" value="1" ${cate2Chk[1]?'checked':''}/>국어</label>
				&nbsp;<label><input type="checkbox" name="cate2No" value="2" ${cate2Chk[2]?'checked':''}/>영어</label>
				&nbsp;<label><input type="checkbox" name="cate2No" value="3" ${cate2Chk[3]?'checked':''}/>수학</label>
				&nbsp;<label><input type="checkbox" name="cate2No" value="4" ${cate2Chk[4]?'checked':''}/>과학</label>
				&nbsp;<label><input type="checkbox" name="cate2No" value="5" ${cate2Chk[5]?'checked':''}/>논술</label>
				&nbsp;<label><input type="checkbox" name="cate2No" value="6" ${cate2Chk[6]?'checked':''}/>독서</label>
				&nbsp;<label><input type="checkbox" name="cate2No" value="7" ${cate2Chk[7]?'checked':''}/>입시컨설팅</label>
				&nbsp;<label><input type="checkbox" name="cate2No" value="8" ${cate2Chk[8]?'checked':''}/>기타</label>
			</td>
		</tr>
		<br>
		<tr>
			<th colspan="2" id="mTable">
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