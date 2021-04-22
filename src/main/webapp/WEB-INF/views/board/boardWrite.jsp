<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctxPath" value="<%= request.getContextPath() %>"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Board</title>
<link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.css">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<link rel="stylesheet" type="text/css" href="${ctxPath}/resources/css/basic.css">
<script src="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.js"></script>
<script>
//console.dir(${bList2 });
</script>
</head>
<body>
<%@ include file="../common/header.jsp" %>
<section>

<center>
<h3>클래스명 : ${clsName}</h3>
<h3><c:if test="${empty boardData}">글쓰기</c:if><c:if test="${!empty boardData}">글수정</c:if></h3>
<form action="class/boardwrite" id="frm" method="post" enctype="multipart/form-data">
<c:if test="${!empty boardData}">
	<input type="hidden" name="clsBrdNo" value="${boardData.clsBrdNo}">
	<input type="hidden" name="clsNo" value="${boardData.clsNo}">
	<input type="hidden" name="clsBrdType" value="${boardData.clsBrdType}">
</c:if>
<table>
<tr>
	<th>제목</th>
	<td><input type="text" name="clsBrdTitle" id="clsBrdTitle" value="${boardData.clsBrdTitle}" required/></td>
</tr>
<tr>
	<th>내용</th>
	<td><textarea name="clsBrdContent" id="clsBrdContent" rows="20" cols="60" required>${boardData.clsBrdContent}</textarea></td>
</tr>
<tr>
	<th>첨부파일</th>
	<td><c:set var="files" value="${boardData.files}" />
		<c:if test="${!empty files}">
			<c:forEach var="file" items="${files}">
				<img src="${ctxPath}/resources/images/icon_file.jfif" width="20" style="float:left;margin-top:3px">
				<i class="fa fa-paperclip"></i><a href="/download?fileNo=${file.fileNo}">${file.origFileName}</a> <input type="button" value="삭제" id="delFile" onclick="fncDelFile(${file.fileNo})"/>
			</c:forEach>
		</c:if>
		<input type="file" name="files" id="files" multiple/></td>
</tr>
</table>
<p><button>등록</button> <input type="button" value="이전으로" onClick="history.back();"></p>
</form>
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
</section>
<%@ include file="../common/footer.jsp" %>
</body>
</html>