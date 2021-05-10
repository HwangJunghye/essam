<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctxPath" value="<%= request.getContextPath() %>"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>관리자 페이지</title>
<script src="https://YOUR-SERVER/javascripts/api/tableau-2.min.js"></script>
<link rel="stylesheet" type="text/css" href="${ctxPath}/resources/css/basic.css">
<link rel="icon" href="${ctxPath}/resources/images/favicon_essam.ico" type="image/x-icon">
<link rel="shortcut icon" href="${ctxPath}/resources/images/favicon_essam.ico" type="image/x-icon">
</head>
<style>

#chooseCondition{
width: 480px;
height: 200px;
margin-left:150px;
margin-top:40px;
}
#btnArea{
width: 480px;
height: 70px;
margin-left:150px;
text-align:center;
}

input[type="button"]{
width: 70px;
height: 36px;
background: #3566A2;
box-shadow: 3px 3px 4px 1px rgba(0, 0, 0, 0.1);
border-radius: 10px;
color: #FFFFFF;
text-align: center;
border: none;
cursor: pointer;
}

input[type="reset"]{
width: 70px;
height: 36px;
background: #3566A2;
box-shadow: 3px 3px 4px 1px rgba(0, 0, 0, 0.1);
border-radius: 10px;
color: #FFFFFF;
text-align: center;
border: none;
cursor: pointer;
}
#chartArea{
width:760px;
height:415px;
margin-left:150px;
border:solid 3px black;
}
.condition{
text-align:left;
}
</style>
<body>

<%@ include file="../common/header.jsp" %>
<%@ include file="../common/nav.jsp" %>
<section>
<div id="contents">
	<div id="aside">
		<div id="aside_area"><%@ include file="../common/aside.jsp"%></div>
	</div>
	<div id="contents_area">
<!--------- 본문 시작 -------------->

<form action="getstatistic" method="post">

<table id="chooseCondition">
<tr>
<td>조회기간:</td>&emsp;
<td><input type="date" id="startDate"> ~ <input type="date" id="endDate"></td>
</tr>
<tr>
<td>조회대상:</td>
<td class="condition">&emsp;
<input type="radio" name="searchTarget" value="class" checked required/>클래스&emsp;&emsp;
<input type="radio" name="searchTarget" value="teacher" />강사&emsp;&emsp;&emsp;
<input type="radio" name="searchTarget" value="student" />수강생
</td>
</tr>
<tr>
<td>필&ensp;터:</td>
<td class="condition">&emsp;
<input type="radio" name="filter" value="total" checked required/>전체&emsp;&emsp;&emsp;
<input type="radio" name="filter" value="new" />신규
</td>
</tr>
</table>
<br/>
<div id="btnArea">
<input type="reset" class="btn" id="reset" value="초기화">&emsp;
<input type="button" class="btn" id="showChart" value="조회">
</div>
<br/>

<div id="chartArea">
차트 표시 영역
</div>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>

</form>

<!--------- 본문 끝 -------------->
	</div>
</div>
</section>
<%@ include file="../common/footer.jsp" %>

<script type="text/javascript">
$(function(){	
	$("#showChart").on("click", function(){
		
		let startDate = $("#startDate").val();
		let endDate = $("#endDate").val();
		let searchTarget = $(":input:radio[name=searchTarget]:checked").val();
		let filter = $(":input:radio[name=filter]:checked").val();
		
		
		//차트 조회 ajax요청
		$.ajax({
				url:'${ctxPath}/getstatistic',
				data:{startDate:startDate,endDate:endDate,searchTarget:searchTarget,filter:filter},
				method: 'post',
				dataType:'json'
			}).done(function(data){
				return true;
			}).fail(function(err){
				alert('자료를 조회할 수 없습니다.');
				return false;
			}); // ajax End
		
	})//showChart onclick End
	
})//ready function End

</script>
</body>
</html>