<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctxPath" value="<%= request.getContextPath() %>"/>
<% //Author : 고연미 %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Board</title>
<link rel="stylesheet" type="text/css" href="${ctxPath}/resources/css/basic.css">
</head>
<body>
<%@ include file="../common/header.jsp" %>
<%@ include file="../common/nav.jsp"%>
<section>
<div id="contents">
	<div id="aside">
		<div id="aside_area">
			<%@ include file="../common/aside.jsp"%>
		</div>
	</div>
	<div id="contents_area">
	<!---------- 본문 시작 ---------->

		<table class="container">
		<tr>
			<td align="left" style="padding:20px 0;"><h6><i class="fab fa-edge-legacy"></i> 클래스 <i class="fas fa-angle-right"></i> <span style="font-weight: bold;background-color:#f4edd8;">${clsName}</span></h6>
				<hr style="height:10px;border:0px;box-shadow:0px 10px 10px -10px #bbb inset;"></td>
		</tr></table>

		<div class="container">
			<form action="${ctxPath}/class/boardwrite" id="frm" method="post" enctype="multipart/form-data">
			<input type="hidden" name="clsBrdNo" value="${boardData.clsBrdNo}">
			<input type="hidden" name="clsBrdType" value="${clsBrdType}">
			<input type="hidden" name="clsNo" value="${clsNo}">
			<input type="hidden" name="pageNum" value="${pageNum}">
			<table>
			<tr>
				<th width="150">제목</th>
				<td><input type="text" name="clsBrdTitle" class="clsBrdTitle" size="75" value="${boardData.clsBrdTitle}" autofocus required/></td>
			</tr>
			<tr>
				<th>내용</th>
				<td><textarea name="clsBrdContent" class="clsBrdContent" rows="20" cols="70" required>${boardData.clsBrdContent}</textarea></td>
			</tr>
			<tr>
				<th valign="top">첨부파일</th>
				<td align="left">
					<c:set var="files" value="${boardData.filesInfo}" />
					<c:if test="${!empty files}">
						<c:forEach var="file" items="${files}">
							<c:if test="${file.fileTypeNo == 1}"><i class="far fa-file-image"></i></c:if>
							<c:if test="${file.fileTypeNo == 2}"><i class="far fa-file-video"></i></c:if>
							<c:if test="${file.fileTypeNo == 3}"><i class="far fa-file-alt"></i></c:if>
							<a href="${ctxPath}/download?fileNo=${file.fileNo}">${file.origFileName}</a> 
							<a href="javascript:void(0);" onclick="fncDelFile(${file.fileNo})" id="delFile"><i class="fas fa-backspace"></i></a><br>
						</c:forEach>
					</c:if>
					<p><br><input type="file" name="files" id="files" multiple/></p></td>
			</tr>
			</table><br>
			<p><button>등록</button> <input type="button" value="이전으로" onClick="history.back();"></p>
			</form><br><br>
		</div>
		
		<script>
		//file 삭제 --->ajax처리
		//$('#delFile').on('click',function(){
		function fncDelFile(fn) {
			//js객체
			const param = {
					_method:"patch", //method(type):post
					delFileNo: fn
				};
			$.ajax({
				url: "class/delbrdfile",
				method: "post", //type:'post',
				data:param
			}).done(()=> {
				toastr.success("파일을 삭제했습니다", '서버 메시지');
			}).fail((xhr)=> printError(xhr, "파일 삭제에 실패했습니다"));
			
		}
		</script>

	<!---------- 본문 끝 ---------->
	</div>
</div>
</section>
<%@ include file="../common/footer.jsp" %>
</body>
</html>