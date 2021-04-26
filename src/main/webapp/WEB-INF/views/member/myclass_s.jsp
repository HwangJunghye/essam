<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctxPath" value="<%=request.getContextPath()%>" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>마이 클래스</title>
<link rel="stylesheet" type="text/css"
	href="${ctxPath}/resources/css/basic.css">
</head>
<body>
	<%@ include file="../common/header.jsp"%>
	<%@ include file="../common/nav.jsp"%>

	<section>
		
		<!-- clsInfo가 있으면 -->
		<c:if test="${!empty clsInfo}">
			<c:forEach var="cInfo" items="${clsInfo}">
				<div>
					<p>${cInfo.cate1Name}>${cInfo.cate2Name}</p>
					<span>${cInfo.clsName}</span> <span>${cInfo.clsRegiCnt}/${cInfo.clsLimit}</span>
					<p>${cInfo.clsIntro}</p>
				</div>
			</c:forEach>
		</c:if>


		<!-- clsInfo가 없으면 -->
		<c:if test="${empty clsInfo}">
			<h3>마이 클래스가 없습니다.</h3>
		</c:if>

	</section>
	<%@ include file="../common/footer.jsp"%>
</body>
</html>