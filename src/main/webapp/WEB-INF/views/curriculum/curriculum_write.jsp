<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctxPath" value="<%= request.getContextPath() %>"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>커리큘럼 등록</title>
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
	<table class="container">
		<tr>
			<td align="left" style="padding:20px 0;">
				<h6><i class="fab fa-edge-legacy"></i> 클래스 <i class="fas fa-angle-right"></i> <span style="font-weight: bold;background-color:#f4edd8;">${clsName}</span></h6>
				<hr class="hr_${mbColor}"></td>
		</tr>
	</table>
	
	<div id="wrap_dong_div" style="width:100%; height:100%; backbround:lightgray; position:relative;">
		<input type="radio" name="curTypeNo" id="dong" value="1" checked>동영상
		<input type="radio" name="curTypeNo" id="sil" value="2">실시간
		
		<form action="/class/curriculum/write" method="post" enctype="multipart/form-data">
			<input type="hidden" name="curTypeNo" value="1">
			<input type="hidden" name="clsNo" value="${clsNo}">
			<div class="dong_div" id="dong_div" style="width:900px; height:600px; margin:0 auto;">
				<table width=600 height=400 border=2 style="margin-left:auto; margin-right:auto;">
					<tr>
						<th>제목</th>
						<td><input type="text" name="curTitle" required /></td>
					</tr>
					<tr>
						<th>동영상파일</th>
						<td><input type="file" name="file" accept="video/mp4" required /></td>
					</tr>
					<tr>
						<th>재생시작일</th>
						<td>
							<input type="date" name="curStartDate" min="1900-01-01" max="2100-12-31" required /> 
						</td>
					</tr>
					<tr>
						<th>재생종료일</th>
						<td>
							<input type="date" name="curEndDate" min="1900-01-01" max="2100-12-31" required /> 
						</td>
					</tr>
					<tr>
						<th>설명</th>
						<td><textarea name="curDisc"></textarea></td>
					</tr>
					<tr>
						<td colspan=2><input type="submit" class="btn_normal_t" value="등록" /></td>
					</tr>
				</table>
			</div>
		</form>
		
		<form action="/class/curriculum/write" method="post" enctype="multipart/form-data">
			<input type="hidden" name="curTypeNo" value="2">
			<input type="hidden" name="clsNo" value="${clsNo}">
			<div class="sil_div" id="sil_div" style="width:900px; height:300px; margin:0 auto;">
				<table width=600 height=400 border=2 style="margin-left:auto; margin-right:auto;">
					<tr>
						<th>제목</th>
						<td><input type="text" name="curTitle" required /></td>
					</tr>
					<tr>
						<th>시작일시</th>
						<td>
							<input type="datetime-local" name="curStartDate" min="1900-01-01" max="2100-12-31" required /> 
						</td>
					</tr>
					<tr>
						<th>종료일시</th>
						<td>
							<input type="datetime-local" name="curEndDate" min="1900-01-01" max="2100-12-31" required /> 
						</td>
					</tr>
					<tr>
						<th>설명</th>
						<td><textarea name="curDisc"></textarea></td>
					</tr>
					<tr>
						<th>줌링크</th>
						<td><textarea name="zoomLink"></textarea></td>
					</tr>
					<tr>
						<th>줌비밀번호</th>
						<td><textarea name="zoomPwd"></textarea></td>
					</tr>
					<tr>
						<td colspan=2><input type="submit" class="btn_normal_t" value="등록" /></td>
					</tr>
				</table>
			</div>
		</form>
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