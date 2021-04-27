<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctxPath" value="<%= request.getContextPath() %>"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>클래스 관리> 마이 클래스</title>
<link rel="stylesheet" type="text/css" href="${ctxPath}/resources/css/basic.css">
</head>
<style>
.clsList{
width: 530px;
height: 120px;
background: #FFFFFF;
box-shadow: 3px 3px 4px 3px rgba(0, 0, 0, 0.25);
}

.clsCate{
font-style: normal;
font-weight: normal;
font-size: 16px;
line-height: 19px;
color: #828282;
}
.clsTitle{
font-style: normal;
font-weight: bold;
font-size: 24px;
line-height: 28px;
color: #000000;
}
.clsLimit{
font-align: right;
font-style: normal;
font-weight: normal;
font-size: 16px;
line-height: 28px;
color: #828282;
}
.clsIntro{
font-style: normal;
font-weight: normal;
font-size: 16px;
line-height: 19px;
color: #828282;
}

</style>
<body>

<%@ include file="../common/header.jsp" %>
<%@ include file="../common/nav.jsp" %>
<section>
	
	<!-- clsInfo가 있으면 -->
	<c:if test="${!empty clsInfo}">
		<c:forEach var="cInfo" items="${clsInfo}">
			<div class="clsList" onclick="location.href='${ctxPath}/class/classinfo?clsNo=${sInfo.clsNo}'">
				<p class="clsCate">${cInfo.cate1Name}> ${cInfo.cate2Name}</p>
				<span class="clsTitle">${cInfo.clsName}</span> <span class="clsLimit">(${cInfo.clsRegiCnt}/${cInfo.clsLimit})</span>
				<p class="clsIntro">${cInfo.clsIntro}</p>
			</div>
		</c:forEach>
	</c:if>
	
	
	<!-- clsInfo가 없으면 -->
	<c:if test="${empty clsInfo}">
	 <p>마이 클래스가 없습니다.</p>
	</c:if>

	
</section>
<%@ include file="../common/footer.jsp" %>
</body>
</html>