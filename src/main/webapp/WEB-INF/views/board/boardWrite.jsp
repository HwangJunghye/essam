<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctxPath" value="<%= request.getContextPath() %>"/>
<% //Author : 고연미 %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>e-쌤</title>
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
			<td align="left" style="padding:20px 0;">
				<h6><i class="fab fa-edge-legacy"></i> 클래스 <i class="fas fa-angle-right"></i> <span style="font-weight: bold;background-color:#f4edd8;">${clsName}</span></h6>
				<hr class="hr_${mbColor}"></td>
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
				<th>첨부파일</th>
				<td align="left">
					<%-- <c:set var="files" value="${boardData.filesInfo}" />
					<c:if test="${!empty files}">
						<c:forEach var="file" items="${files}">
							<c:if test="${file.fileTypeNo == 1}"><i class="far fa-file-image"></i></c:if>
							<c:if test="${file.fileTypeNo == 2}"><i class="far fa-file-video"></i></c:if>
							<c:if test="${file.fileTypeNo == 3}"><i class="far fa-file-alt"></i></c:if>
							<a href="${ctxPath}/download?fileNo=${file.fileNo}">${file.origFileName}</a> 
							<a href="javascript:void(0);" onclick="fncDelFile(${file.fileNo})" id="delFile"><i class="fas fa-backspace"></i></a><br>
						</c:forEach>
					</c:if> --%>
					<!-- 첨부파일 출력 영역 -->
					<div><span id="result" style="font-size:12px;color:red;"></span>
						<ul id="attachment" style="list-style-type:circle;"></ul>
					</div>
					<p><br><input type="file" name="files" id="files" multiple/></p></td>
			</tr>
			</table><br>
			<p><button>등록</button> <input type="button" value="이전으로" onClick="history.back();"></p>
			</form><br><br><br><br>
		</div>
		
		<script>
		let bNo = "${boardData.clsBrdNo}";	//글번호
		
		$(function() {
			if(bNo != "") {
				//첨부파일 가져오기
				$.ajax({
					url: "${ctxPath}/class/getfilelist",
					method: "get",
					data : {clsBrdNo : bNo},
					dataType : 'json'
				}).done((result)=>{
					let fileList = result;	//첨부파일 리스트
					console.log("fileList = ",fileList);
					printAttachment(fileList);  //가져온 정보를 화면에 출력
				}).fail(function(err) {
					$('#result').text('첨부파일 리스트를 가져올 수 없습니다');
				});
			}

			//첨부파일 삭제
			$("#attachment").on("click", ".delete_attachment", function() {
				if("${loginData.mbId}" != "${boardData.mbId}")
					return;

				let msg = confirm('해당 파일을 정말 삭제하시겠습니까?'); 
				if(msg) {
					//console.log("삭제 파일번호 : "+ $(this).data("fileno"));
					const param = {
						fileNo: $(this).data("fileno"),  	// 삭제할 파일번호
						clsBrdNo: bNo  						// 글번호. 나머지 첨부파일 정보 반환 
					};					
					$.ajax({
						url: "${ctxPath}/class/delbrdfile",
						method: "post",
						data: param,
						dataType : 'json'
					}).done((result)=> { 
						printAttachment(result);
						console.log("nfileList = ",result);
					}).fail(function(err) {
						$('#result').text('서버와 통신할 수 없습니다');
					});	
				}
			});
		});
		//첨부파일 출력
		function printAttachment(fileList) {

			const $ul=$("#attachment"); 
			//첨부파일 출력전 기존파일 목록 삭제
			$ul.empty(); 
			
			//파일 목록이 있으면
			if(fileList != null) {
				//화면에 각 파일 출력
	 			$.each(fileList, function(i, attachment){
					//<ul> <li></li> </ul>
					const $li=$("<li></li>").appendTo($ul);
					//첨부파일에 아이콘 추가
					if(attachment.fileTypeNo == 1)
						$('<i class="far fa-file-image" style="margin-right:10px"></i>').appendTo($li);
					if(attachment.fileTypeNo == 2)
						$('<i class="far fa-file-video" style="margin-right:10px"></i>').appendTo($li);
					if(attachment.fileTypeNo == 3)
						$('<i class="far fa-file-alt" style="margin-right:10px"></i>').appendTo($li);
					
					//첨부파일에 대한 링크를 파일명에 추가
					const queryString = "${ctxPath}/download?fileNo="+ attachment.fileNo;
					$("<a>").attr("href",queryString).text(attachment.origFileName).appendTo($li);
						
					//date-xxx 속성 : $(this).data(xxx)로 값을 가져옴. 주의:xxx는 소문자만 사용.
					$li.append("&nbsp;<span class='delete_attachment' data-fileno='" 
							   + attachment.fileNo + "'><i class='fas fa-backspace'></i></sapn>");
					$(".delete_attachment").css("cursor","pointer").attr("title","파일 삭제");
				});//each End 
			}
		}
		</script>

	<!---------- 본문 끝 ---------->
	</div>
</div>
</section>
<%@ include file="../common/footer.jsp" %>
</body>
</html>