<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctxPath" value="<%= request.getContextPath() %>"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>e-쌤</title>
<link rel="stylesheet" type="text/css" href="${ctxPath}/resources/css/basic.css">
<link rel="icon" href="${ctxPath}/resources/images/favicon_essam.ico" type="image/x-icon">
<link rel="shortcut icon" href="${ctxPath}/resources/images/favicon_essam.ico" type="image/x-icon">
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
	<table class="container">
		<tr>
			<td align="left" style="padding:20px 0;">
				<h6><i class="fab fa-edge-legacy"></i> 클래스 <i class="fas fa-angle-right"></i> <span style="font-weight: bold;background-color:#f4edd8;">${clsName}</span></h6>
				<hr class="hr_${mbColor}"></td>
		</tr>
	</table>
	
	<div>
		<h3>${msg}</h3>
		${curriInfo.curStartDate}
		${curriInfo.curEndDate}<br/>
		<video width=900" height="500" controls="controls">
			<source src="${ctxPath}/getvideo?fileNo=${curriInfo.fileNo}" type="video/mp4">
		</video>
	</div><br/><br/><br/><br/><br/>
	
		<button type="button" class="btn_normal_t" onclick="location.href='${ctxPath}/class/curriculum?clsNo=${clsNo}&pageNum=${pageNum}'">목록</button>
	<c:if test="${sessionScope.loginData.mbType==2}">
		<button type="button" class="btn_normal_t" onclick="location.href='${ctxPath}/class/curriculum/update?clsNo=${curriInfo.clsNo}&curNo=${curriInfo.curNo}&pageNum=${pageNum}'">수정</button>
		<button type="button" class="btn_normal_t" onclick="location.href='${ctxPath}/class/curriculum/delete?clsNo=${curriInfo.clsNo}&curNo=${curriInfo.curNo}&pageNum=${pageNum}'">삭제</button>
	</c:if>

<!--------- 본문 끝 -------------->
	</div>
</div>
</section>
<%@ include file="../common/footer.jsp" %>

</body>
</html>