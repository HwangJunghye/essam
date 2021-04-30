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
<!-- 클래스 정보 수정 페이지 -->
<c:if test="${!empty clsInfo}">
		<table class="container">
		<tr>
			<td align="left" style="padding:20px 0;">
				<h6><i class="fab fa-edge-legacy"></i> 클래스 <i class="fas fa-angle-right"></i> <span style="font-weight: bold;background-color:#f4edd8;">${clsInfo.clsName}</span></h6>
				<hr class="hr_${mbColor}"></td>
		</tr>
		</table>
<br/>
카테고리1:
		<input type="radio" name="cate1No" value="1" ${clsInfo.cate1No==1?'checked':''} required/>유아
		<input type="radio" name="cate1No" value="2" ${clsInfo.cate1No==2?'checked':''}/>초등
		<input type="radio" name="cate1No" value="3" ${clsInfo.cate1No==3?'checked':''}/>중등
		<input type="radio" name="cate1No" value="4" ${clsInfo.cate1No==4?'checked':''}/>고등
		<input type="radio" name="cate1No" value="5" ${clsInfo.cate1No==5?'checked':''}/>특목
		<input type="radio" name="cate1No" value="6" ${clsInfo.cate1No==6?'checked':''}/>기타
	<br/>
카테고리2:
		<input type="radio" name="cate2No" value="1" ${clsInfo.cate1No==1?'checked':''} required/>국어
		<input type="radio" name="cate2No" value="2" ${clsInfo.cate1No==2?'checked':''}/>영어
		<input type="radio" name="cate2No" value="3" ${clsInfo.cate1No==3?'checked':''}/>수학
		<input type="radio" name="cate2No" value="4" ${clsInfo.cate1No==4?'checked':''}/>과학
		<input type="radio" name="cate2No" value="5" ${clsInfo.cate1No==5?'checked':''}/>논술
		<input type="radio" name="cate2No" value="6" ${clsInfo.cate1No==6?'checked':''}/>독서
		<input type="radio" name="cate2No" value="7" ${clsInfo.cate1No==7?'checked':''}/>입시컨설팅
		<input type="radio" name="cate2No" value="8" ${clsInfo.cate1No==8?'checked':''}/>기타
	<br/>
	클래스명: <input type="text" name="clsName" value="${clsInfo.clsName}" required/>
	<br/>
	클래스 정원: <input type="number" name="clsLimit" value="${clsInfo.clsLimit}" required/> (명)
	<br/>
	커버이미지: <input type="file" name="file" value="${clsInfo.fileNo}" >
	<br/>
	한줄 소개:<input type="text" name="clsIntro" value="${clsInfo.clsIntro}" required/>
	<br/>
	상세 소개:<textarea cols="50" rows="10" name="clsDesc" value="${clsInfo.clsDesc}" required/></textarea>
	<br/>
	가격:<input type="number" name="clsPrice" value="${clsInfo.clsPrice}"> (/월)
	<br/>
	키워드:<input type="text" name="clsKeyword" value="${clsInfo.clsKeyword}">
	<br/>
	zoom링크:<input type="text" name="zoomLink" value="${clsInfo.zoomLink}">
	<br/>
	zoom비밀번호:<input type="text" name="zoomPwd" value="${clsInfo.zoomPwd}">
	<br/>
	<br/>
</c:if>



<!-- 클래스 신규 등록 페이지 -->
<c:if test="${empty clsInfo}">
	카테고리1:
		<input type="radio" name="cate1No" value="1" required/>유아
		<input type="radio" name="cate1No" value="2"/>초등
		<input type="radio" name="cate1No" value="3"/>중등
		<input type="radio" name="cate1No" value="4"/>고등
		<input type="radio" name="cate1No" value="5"/>특목
		<input type="radio" name="cate1No" value="6"/>기타
	<br/>
	카테고리2:
		<input type="radio" name="cate2No" value="1" required/>국어
		<input type="radio" name="cate2No" value="2"/>영어
		<input type="radio" name="cate2No" value="3"/>수학
		<input type="radio" name="cate2No" value="4"/>과학
		<input type="radio" name="cate2No" value="5"/>논술
		<input type="radio" name="cate2No" value="6"/>독서
		<input type="radio" name="cate2No" value="7"/>입시컨설팅
		<input type="radio" name="cate2No" value="8"/>기타
	<br/>
	클래스명: <input type="text" name="clsName" required/>
	<br/>
	클래스 정원: <input type="number" name="clsLimit" required/> (명)
	<br/>
	커버이미지: <input type="file" name="file">	
	<br/>
	한줄 소개:<input type="text" name="clsIntro" required/>
	<br/>
	상세 소개:<textarea cols="50" rows="10" name="clsDesc" required/></textarea>
	<br/>
	가격:<input type="number" name="clsPrice"> (/월)
	<br/>
	키워드:<input type="text" name="clsKeyword">
	<br/>
	zoom링크:<input type="text" name="zoomLink">
	<br/>
	zoom비밀번호:<input type="text" name="zoomPwd">
	<br/>
	<br/>
	</c:if>
	
	
	<input type="button" class="btn" value="취소" onclick="history.back(-1);">
	<input type="submit" class="btn" value="등록">
	
	</form>

	<!--------- 본문 끝 -------------->
	</div>
</div>
</section>
<%@ include file="../common/footer.jsp" %>

</body>
</html>