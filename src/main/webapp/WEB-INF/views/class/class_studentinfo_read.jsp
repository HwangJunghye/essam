<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctxPath" value="<%= request.getContextPath() %>"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="${ctxPath}/resources/css/basic.css">
</head>
<style>
.line{
width: 960px;
height: 0px;
border: 2px solid #9E3C7E;
}
.btn{
width: 64px;
height: 32px;
background: #9E3C7E;
box-shadow: 3px 3px 4px 1px rgba(0, 0, 0, 0.1);
border-radius: 10px;
color: #FFFFFF;
text-align: center;
border: none;
cursor: pointer;
}
.contents1{
width:300px;
height:280px;
margin:auto;
}
.contents2{
width:300px;
height:200px;
margin:auto;
}
th{
font-style: bold;
font-weight: normal;
font-size: 20px;
border: outset 3px #9E3C7E;
}
.item{
text-align: right;
}
.item_contents{
text-align:left;
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
				<h6><i class="fab fa-edge-legacy"></i> 클래스 <i class="fas fa-angle-right"></i> <span style="font-weight: bold;background-color:#f4edd8;">${sInfo.clsName}</span></h6>
				<hr class="hr_${mbColor}"></td>
		</tr>
		</table>

<table class="contents1">
<th colspan="2" class="tableTitle">기본정보</th>
<tr>
<td class="item">이름:</td>
<td class="item_contents">&emsp;${mInfo.mbName}</td>
</tr>
<tr>
<td class="item">닉네임:</td>
<td class="item_contents">&emsp;${sInfo.mbNickName}</td>
</tr>
<tr>
<td class="item">이메일:</td>
<td class="item_contents">&emsp;${sInfo.mbId}</td>
</tr>
<tr>
<td class="item">등록일:</td>
<td class="item_contents">&emsp;${sInfo.regiStartDate}</td>
</tr>
<tr>
<td class="item">주소:</td>
<td class="item_contents">&emsp;${mInfo.mbAddr}</td>
</tr>
</table>
<br/>
<br/>
<br/>
<table class="contents2">
<th colspan="2" class="tableTitle">출석현황</th>
<tr>
<td colspan="2">출석일수 / 총수업일수</td>
</tr>
<tr>
<td colspan="2">${sInfo.attendDay}/${sInfo.totalDay}</td>
</tr>
<tr>
<td>출석률:</td>
<td class="item_contents">${attendPercent}%</td>
</tr>
</table>
<br/>
<br/>
<br/>
<input type="button" class="btn" value="학생목록" onclick="history.back(-1);">

<!--------- 본문 끝 -------------->
	</div>
</div>
</section>
<%@ include file="../common/footer.jsp" %>
</body>
</html>