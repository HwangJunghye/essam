<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctxPath" value="<%= request.getContextPath() %>"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>클래스 등록</title>
<link rel="stylesheet" type="text/css" href="${ctxPath}/resources/css/basic.css">
<link rel="icon" href="${ctxPath}/resources/images/favicon_essam.ico" type="image/x-icon">
<link rel="shortcut icon" href="${ctxPath}/resources/images/favicon_essam.ico" type="image/x-icon">
</head>
<style>
.line{
width: 960px;
height: 0px;
border: 2px solid #9E3C7E;
}

input[type="button"]{
width: 70px;
height: 36px;
background: #9E3C7E;
box-shadow: 3px 3px 4px 1px rgba(0, 0, 0, 0.1);
border-radius: 10px;
color: #FFFFFF;
text-align: center;
border: none;
cursor: pointer;
}

input[type="submit"]{
width: 70px;
height: 36px;
background: #9E3C7E;
box-shadow: 3px 3px 4px 1px rgba(0, 0, 0, 0.1);
border-radius: 10px;
color: #FFFFFF;
text-align: center;
border: none;
cursor: pointer;
}
#inputTable{
width:680px;
height:640px;
margin:auto;
margin-top:50px;
}
.object_name{
text-align:right;
}
.object_con{
text-align:left;
}
input[type="text"]{
width:350px;
}
input[type="number"]{
width:350px;
}
</style>
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
<form action="update" method="post" enctype="multipart/form-data">

<!-- 클래스 정보 있으면 클래스이름 출력 -->
<c:if test="${!empty clsInfo}">

		<table class="container">
		<tr>
			<td align="left" style="padding:20px 0;">
				<h6><i class="fab fa-edge-legacy"></i> 클래스 <i class="fas fa-angle-right"></i> <span style="font-weight: bold;background-color:#f4edd8;">${clsInfo.clsName}</span></h6>
				<hr class="hr_${mbColor}"></td>
		</tr>
		</table>
</c:if>
<!-- 클래스 정보 있으면 클래스 수정 -->
<c:if test="${!empty clsInfo}">
<input type="hidden" name="clsNo" value="${param.clsNo}">
<table id="inputTable">
<tr>
<td class="object_name">카테고리1:</td>
<td class="object_con">&emsp;
	<input type="radio" name="cate1No" value="1" ${clsInfo.cate1No==1?'checked':''} required/>유아&nbsp;
	<input type="radio" name="cate1No" value="2" ${clsInfo.cate1No==2?'checked':''}/>초등&nbsp;
	<input type="radio" name="cate1No" value="3" ${clsInfo.cate1No==3?'checked':''}/>중등&nbsp;
	<input type="radio" name="cate1No" value="4" ${clsInfo.cate1No==4?'checked':''}/>고등&nbsp;
	<input type="radio" name="cate1No" value="5" ${clsInfo.cate1No==5?'checked':''}/>특목&nbsp;
	<input type="radio" name="cate1No" value="6" ${clsInfo.cate1No==6?'checked':''}/>기타
</td>
</tr>
<tr>
<td class="object_name">카테고리2:</td>
<td class="object_con">&emsp;
	<input type="radio" name="cate2No" value="1" ${clsInfo.cate1No==1?'checked':''} required/>국어&nbsp;
	<input type="radio" name="cate2No" value="2" ${clsInfo.cate1No==2?'checked':''}/>영어&nbsp;
	<input type="radio" name="cate2No" value="3" ${clsInfo.cate1No==3?'checked':''}/>수학&nbsp;
	<input type="radio" name="cate2No" value="4" ${clsInfo.cate1No==4?'checked':''}/>과학&nbsp;
	<input type="radio" name="cate2No" value="5" ${clsInfo.cate1No==5?'checked':''}/>논술&nbsp;
	<input type="radio" name="cate2No" value="6" ${clsInfo.cate1No==6?'checked':''}/>독서&nbsp;
	<input type="radio" name="cate2No" value="7" ${clsInfo.cate1No==7?'checked':''}/>입시컨설팅&nbsp;
	<input type="radio" name="cate2No" value="8" ${clsInfo.cate1No==8?'checked':''}/>기타
</td>
</tr>

<tr>
<td class="object_name">클래스명:</td>
<td class="object_con">&emsp;<input type="text" name="clsName" value="${clsInfo.clsName}" required/>
</td>
</tr>

<tr>
<td class="object_name">클래스 정원:</td>
<td class="object_con">&emsp;<input type="number" name="clsLimit" value="${clsInfo.clsLimit}" required/> (명)</td>
</tr>

<tr>
<td class="object_name">커버이미지:</td>
<td class="object_con">&emsp;<input type="file" name="file" accept="image/*"></td>
</tr>

<tr>
<td class="object_name">한줄 소개:</td>
<td class="object_con">&emsp;<input type="text" name="clsIntro" value="${clsInfo.clsIntro}" required/>
</td>
</tr>

<tr>
<td class="object_name">상세 소개:</td>
<td class="object_con">&emsp;<textarea cols="45" rows="8" name="clsDesc" required>${clsInfo.clsDesc}</textarea>
</td>
</tr>

<tr>
<td class="object_name">가격:</td>
<td class="object_con">&emsp;<input type="number" name="clsPrice" value="${clsInfo.clsPrice}"> (/월)</td>
</tr>

<tr>
<td class="object_name">키워드:</td>
<td class="object_con">&emsp;<input type="text" name="clsKeyword" value="${clsInfo.clsKeyword}"></td>
</tr>
<tr>
<td class="object_name">zoom링크:</td>
<td class="object_con">&emsp;<input type="text" name="zoomLink" value="${clsInfo.zoomLink}"></td>
</tr>
<tr>
<td class="object_name">zoom비밀번호:</td>
<td class="object_con">&emsp;<input type="text" name="zoomPwd" value="${clsInfo.zoomPwd}">
	</td>
</tr>
</table>
</c:if>
<!-- 클래스 정보 없으면 클래스 등록 -->
<c:if test="${empty clsInfo}">
<table id="inputTable">
<tr>
<td class="object_name">카테고리1:</td>
<td class="object_con">&emsp;
	<input type="radio" name="cate1No" value="1" required/>유아&nbsp;
	<input type="radio" name="cate1No" value="2"/>초등&nbsp;
	<input type="radio" name="cate1No" value="3"/>중등&nbsp;
	<input type="radio" name="cate1No" value="4"/>고등&nbsp;
	<input type="radio" name="cate1No" value="5"/>특목&nbsp;
	<input type="radio" name="cate1No" value="6"/>기타
</td>
</tr>
<tr>
<td class="object_name">카테고리2:</td>
<td class="object_con">&emsp;
	<input type="radio" name="cate2No" value="1" required/>국어&nbsp;
	<input type="radio" name="cate2No" value="2"/>영어&nbsp;
	<input type="radio" name="cate2No" value="3"/>수학&nbsp;
	<input type="radio" name="cate2No" value="4"/>과학&nbsp;
	<input type="radio" name="cate2No" value="5"/>논술&nbsp;
	<input type="radio" name="cate2No" value="6"/>독서&nbsp;
	<input type="radio" name="cate2No" value="7"/>입시컨설팅&nbsp;
	<input type="radio" name="cate2No" value="8"/>기타
</td>
</tr>

<tr>
<td class="object_name">클래스명:</td>
<td class="object_con">&emsp;<input type="text" name="clsName" required/>
</td>
</tr>

<tr>
<td class="object_name">클래스 정원:</td>
<td class="object_con">&emsp;<input type="number" name="clsLimit" required/> (명)</td>
</tr>

<tr>
<td class="object_name">커버이미지:</td>
<td class="object_con">&emsp;<input type="file" name="file" accept="image/*"></td>
</tr>

<tr>
<td class="object_name">한줄 소개:</td>
<td class="object_con">&emsp;<input type="text" name="clsIntro" required/>
</td>
</tr>

<tr>
<td class="object_name">상세 소개:</td>
<td class="object_con">&emsp;<textarea cols="45" rows="8" name="clsDesc" required></textarea>
</td>
</tr>

<tr>
<td class="object_name">가격:</td>
<td class="object_con">&emsp;<input type="number" name="clsPrice" > (/월)</td>
</tr>

<tr>
<td class="object_name">키워드:</td>
<td class="object_con">&emsp;<input type="text" name="clsKeyword"></td>
</tr>
<tr>
<td class="object_name">zoom링크:</td>
<td class="object_con">&emsp;<input type="text" name="zoomLink"></td>
</tr>
<tr>
<td class="object_name">zoom비밀번호:</td>
<td class="object_con">&emsp;<input type="text" name="zoomPwd">
	</td>
</tr>
</table>
</c:if>



	<br/>
	<br/>
	<input type="button" class="btn" value="취소" onclick="history.back(-1);">
	<input type="submit" class="btn" value="등록">
	<br/>
	<br/>
	<br/>
	<br/>
	<br/>
	<br/>
	<br/>
	
	</form>
<!--------- 본문 끝 -------------->
	</div>
</div>
</section>
<%@ include file="../common/footer.jsp" %>

</body>
</html>