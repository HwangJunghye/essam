<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctxPath" value="<%=request.getContextPath()%>" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>마이 클래스</title>
<link rel="stylesheet" type="text/css" href="${ctxPath}/resources/css/basic.css">
<link rel="icon" href="${ctxPath}/resources/images/favicon_essam.ico" type="image/x-icon">
<link rel="shortcut icon" href="${ctxPath}/resources/images/favicon_essam.ico" type="image/x-icon">
</head>
<style>
.clsList{
margin:30px;
width: 420px;
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
text-align:left;
margin:15px;
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
#msgbox{
width: 530px;
height: 120px;
margin:200px;
padding:auto;
float:center;
border: outset 3px #3566A2;
}

#msg{
width:500px;
height:100px;
font-style: bold;
font-weight: normal;
font-size: 24px;
margin:20px;
padding:auto;
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
	<!-- clsInfo가 있으면 -->
	<c:if test="${!empty clsInfo}">
		<table>
		<tr>
		<c:forEach items="${clsInfo}" varStatus="state" var="cInfo">
			<td>	
			<div class="clsList" onclick="location.href='${ctxPath}/class/curriculum?clsNo=${cInfo.clsNo}&pageNum=1'" style="cursor:pointer;">
				<p class="clsCate">${cInfo.cate1Name}> ${cInfo.cate2Name}</p>
				<span class="clsTitle">${cInfo.clsName}</span> <span class="clsLimit">(${cInfo.clsRegiCnt}/${cInfo.clsLimit})</span>
				<p class="clsIntro">${cInfo.clsIntro}</p>
			</div>		
			</td>
			<c:if test="${state.index%2==1}">
				</tr>
			</c:if>
		</c:forEach>
		</table>
	</c:if>
	
	<!-- clsInfo가 없으면 -->
	<c:if test="${empty clsInfo}">
		<div id="msgbox"><div id="msg">마이 클래스가 없습니다.</div></div>
	</c:if>
	<br><br><br><br>
<!--------- 본문 끝 -------------->
	</div>
</div>
	</section>
	<%@ include file="../common/footer.jsp"%>
</body>
</html>