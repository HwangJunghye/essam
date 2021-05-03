<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctxPath" value="<%=request.getContextPath()%>" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="${ctxPath}/resources/css/basic.css">
<link rel="icon" href="${ctxPath}/resources/images/favicon_essam.ico" type="image/x-icon">
<link rel="shortcut icon" href="${ctxPath}/resources/images/favicon_essam.ico" type="image/x-icon">
</head>
<style>
table {
	float: center;
	margin: auto;
	items-align: center;
	width: 80%;
	border: none;
	text-align: center;
	line-height: 28px;
}
.line {
	width: 960px;
	height: 0px;
	border: 2px solid #9E3C7E;
}
.listArea{
height: 20px;
}

.attendDay{
text-align:right;
}
.totalDay{
text-align:left;

}

</style>
<body>
	<%@ include file="../common/header.jsp"%>
	<%@ include file="../common/nav.jsp"%>
<section>

<div id="contents">
	<div id="aside">
		<div id="aside_area">
			<%@ include file="../common/aside.jsp"%>
		</div>
	</div>
	<div id="contents_area">
<!--------- 본문 시작 -------------->

		<!-- sList가 있으면 -->
		<c:if test="${!empty sList}">
	
			<table class="container">
				<tr>
					<td align="left" style="padding:20px 0;">
						<h6><i class="fab fa-edge-legacy"></i> 클래스 <i class="fas fa-angle-right"></i> <span style="font-weight: bold;background-color:#f4edd8;">${clsInfo.clsName}</span></h6>
						<hr class="hr_${mbColor}"></td>
				</tr>
			</table>
			
			<table>
				<tr>
					<th>닉네임</th>
					<th>이메일</th>
					<th>등록일</th>
					<th colspan="3">출결현황(출석일/총수업일)</th>
				</tr>
			
				<c:forEach var="sInfo" items="${sList}">
					<tr class="listArea" onclick="location.href='${ctxPath}/class/studentinfo?mbId=${sInfo.mbId}&clsNo=${sInfo.clsNo}'">
						<td>${sInfo.mbNickName}</td>
						<td>${sInfo.mbId}</td>
						<td>${sInfo.regiStartDate}</td>
						<td class="attendDay">${sInfo.attendDay}</td>
						<td>/</td>
						<td class="totalDay">${sInfo.totalDay}</td>
					</tr>
				</c:forEach>
			</table>
		</c:if>

		<!-- sList가 없으면 -->
		<c:if test="${empty sList}">
			<h3>수강생이 없습니다.</h3>
		</c:if>
		
<!--------- 본문 끝 -------------->
	</div>
</div>
	</section>


	<%@ include file="../common/footer.jsp"%>
</body>
</html>