<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctxPath" value="<%= request.getContextPath() %>"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>관리자 페이지</title>
<link rel="stylesheet" type="text/css" href="${ctxPath}/resources/css/basic.css">
</head>
<style>
.btn{
width: 64px;
height: 32px;
background: #3566A2;
box-shadow: 3px 3px 4px 1px rgba(0, 0, 0, 0.1);
border-radius: 10px;
color: #FFFFFF;
text-align: center;
border: none;
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

<form action="getstatistic" method="post">
조회기간: <input type="date" id="startDate"> ~ <input type="date" id="endDate"><br/>
조회대상:
<input type="radio" name="searchTarget" value="class" checked required/>클래스
<input type="radio" name="searchTarget" value="teacher" />강사
<input type="radio" name="searchTarget" value="student" />수강생
<br/>
필터1: 
<input type="radio" name="filter1" value="all" checked required/>전체
<input type="radio" name="filter1" value="category" />카테고리
<br/>
필터2:
<input type="radio" name="filter2" value="total" checked required/>총
<input type="radio" name="filter2" value="new" />신규
<br/>
<br/>
<input type="reset" class="btn" id="reset" value="초기화">
<input type="button" class="btn" id="showChart" value="조회">
<br/>
<br/>

<div id="warning">텍스트 표시 영역</div>

<div id="chartArea">
차트 표시 영역
</div>

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
		let filter1 = $(":input:radio[name=filter1]:checked").val();
		let filter2 = $(":input:radio[name=filter2]:checked").val();
		
		
		//차트 조회 ajax요청
		$.ajax({
				url:'${ctxPath}/getstatistic',
				data:{startDate:startDate,endDate:endDate,searchTarget:searchTarget,filter1:filter1,filter2:filter2},
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