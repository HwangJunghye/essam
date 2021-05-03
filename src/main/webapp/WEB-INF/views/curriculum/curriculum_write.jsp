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
<link rel="icon" href="${ctxPath}/resources/images/favicon_essam.ico" type="image/x-icon">
<link rel="shortcut icon" href="${ctxPath}/resources/images/favicon_essam.ico" type="image/x-icon">

<style>
      .dong_div {
        margin: 0 auto;
      }
      .sil_div {
      	margin: 0 auto;
      }
</style>

<script src="//code.jquery.com/jquery-3.3.1.min.js"></script>
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
<h1>curriculum_write.jsp</h1>

${msg}<br/><br/><br/>

	<input type="radio" name="chk" id="dong" checked>동영상
	<input type="radio" name="chk" id="sil">실시간
	
	<div class="dong_div" id="dong_div">
		<table width=200 height=100 border=2>
			<tr>
				<td> 동영상 영역 </td>
			</tr>
		</table>
	</div>
	
	<div class="sil_div" id="sil_div">
		<table width=200 height=100 border=2>
			<tr>
				<td> 실시간 영역 </td>
			</tr>
		</table>
	</div>

<!--------- 본문 끝 -------------->
	</div>
</div>
</section>
<%@ include file="../common/footer.jsp" %>

<script>
$(function() {
   $('#sil_div').hide();
   $('#dong').on("click",function() {
      $('#dong_div').show();
      $('#sil_div').hide();      
   });
   $('#sil').on("click",function() {
      $('#dong_div').hide();
      $('#sil_div').show();      
   });
})
</script>
</body>
</html>