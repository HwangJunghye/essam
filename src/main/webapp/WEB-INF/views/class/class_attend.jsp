<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctxPath" value="<%= request.getContextPath() %>"/>
<!DOCTYPE html>
<html>
<head>
<style type="text/css">

.clsTitle{
width:600px;
height:50px;
font-weight: bold;
font-size: 22px;
line-height: 28px;
margin-left:120px;
margin-top:40px;
text-align:left;
}

.line{
width: 960px;
height: 0px;
border: 2px solid #3566A2;
margin-left:20px;
}

#attendResult{
width: 490px;
height: 110px;
display: inline-block;
border: outset 3px #3566A2;
text-align: center;
font-size: 18px;
line-height: 21px;
}

#a_table{
width:400px;
height:300px;
float: center;
margin:auto;
font-size: 18px;
line-height: 21px;
text-align: center;
}

.a_object{
width:100px;
text-align: right;
}

</style>


<meta charset="UTF-8">
<title>출석현황</title>
<link rel="stylesheet" type="text/css" href="${ctxPath}/resources/css/basic.css">
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

<div class="clsTitle">
${attendInfo.clsName}
</div>

<hr class="line">
<br/>



<table id="a_table">
<tr>
<td class="a_object">전체수업:</td>
<td>${attendInfo.totalDay}</td>
<td>회</td>
</tr>
<tr>
<td class="a_object">출&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;석:</td>
<td>${attendInfo.attendDay}</td>
<td>회</td>
</tr>
<tr>
<td class="a_object">결&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;석:</td>
<td>${attendInfo.absenceDay}</td>
<td>회</td>
</tr>
</table>


<br/>
<br/>
<br/>

<div id="attendResult">
<p> ${attendMsg} </p>
<p>${attendInfo.mbNickName}님의 출석률은 ${attendPercent}%입니다.</p>
<p>앞으로도 꾸준히 출석해주세요^^</p>
</div>

<!--------- 본문 끝 -------------->
	</div>
</div>
</section>
<%@ include file="../common/footer.jsp" %>
</body>
</html>