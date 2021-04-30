<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctxPath" value="<%= request.getContextPath() %>"/>
<% //@Author 고연미 %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>e-쌤</title>
<link rel="stylesheet" type="text/css" href="${ctxPath}/resources/css/basic.css">
<link rel="stylesheet" type="text/css" href="${ctxPath}/resources/css/class.css">
</head>
<style>
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

		<table class="container">
		<tr>
			<td align="left" style="padding:20px 0;">
				<h6><i class="fab fa-edge-legacy"></i> 클래스 <i class="fas fa-angle-right"></i> <span style="font-weight: bold;background-color:#f4edd8;">${classInfo.clsName}</span></h6>
				<hr class="hr_${mbColor}"></td>
		</tr></table>
		<table class="container">
		<tr>
			<td align="left">
			<img src="${ctxPath}/getimage?fileNo=${classInfo.fileNo}">
			<p>
			<i class="far fa-caret-square-right"></i> 카테고리 : ${classInfo.cate1Name} > ${classInfo.cate2Name}<br/>
			<i class="far fa-caret-square-right"></i> 클래스명 : ${classInfo.clsName}<br/>
			<i class="far fa-caret-square-right"></i> 정원 : <fmt:formatNumber value="${classInfo.clsLimit}" type="number"/> 명<br/>
			<i class="far fa-caret-square-right"></i> 한줄 소개 : ${classInfo.clsIntro}<br/><br>
			<span style="white-space:pre-wrap;word-break:break-all;">${classInfo.clsDesc}</span><br/></p>
			</td>
		</tr>
		</table>
		<input type="button" class="btn" value="수정" onclick="location.href='${ctxPath}/class/classinfo/write?clsNo=${param.clsNo}'">
		
		
		
		<br><br><br><br><br>

<!--------- 본문 끝 -------------->
	</div>
</div>
</section>
<%@ include file="../common/footer.jsp" %>
</body>
</html>