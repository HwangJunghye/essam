<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>댓글</title>
<style type="text/css">
.input-div {
	vertical-align: middle;
}
</style>
</head>
<body>
		<!-- 댓글등록 -->
		<span id="add"></span>
		<form id="frmReply" action="${ctxPath}/class/addreply" method="post" enctype="multipart/form-data">
		<table width="86%" align="center">
		<tr>
			<td>${loginData.mbNickName}</td>
			<td>첨부파일 :	<input type="file" name="file" id="file"></td>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td colspan=2><textarea rows=3 cols=80 name="clsBrdRepContent" id="clsBrdRepContent"></textarea></td>
			<td><input type="button" onclick="javascript:addReply();" value="등록"> <input type="reset" value="취소"></td>
		</tr>
		</table>
		</form>

		<!-- 댓글리스트 -->
	 	<span id="rTable">
	 	<%-- <c:forEach var="reply" items="${rList}">
			<tr>
				<td>${reply.mbNickName}</td>
				<td><a href="${ctxPath}/download?fileNo=${reply.fileNo}"><i class="fas fa-save" style="width:24px;color:#666;"></i></a></td>
				<td>${reply.clsBrdRepContent}</td>
				<td>${reply.clsBrdRepDate}</td>
			</tr>
		</c:forEach> --%>
		</span>
	</section>

	<!-- 댓글 form 넣기 스토리보드 12번 -->
	<div id="replyArea"></div>
	
	<script>
	
//댓글등록하기
let bNo = "${boardData.clsBrdNo}";  //글번호
	console.log("bNo == ", bNo);
let brNo = "${reply.clsBrdRepNo}";	//댓글번호
	console.log("brNo == ", brNo);

	function addReply(){
		
		//FormData : js 지원 객체이므로 jQ객체($('#frm'))를 사용할수없다.
		//js 객체로 파일 저장
		let $obj = $("#file");
		let files = $obj[0].files;
		
		//폼의 일부 데이터만 저장
	 	let formData = new FormData();
		formData.append("clsBrdNo", bNo);		
		formData.append("file", files[0]);
		formData.append("clsBrdRepDate", files[0]);
		formData.append("clsBrdRepContent", $('#clsBrdRepContent').val());

		if(bNo != "") {
			//댓글등록
			$.ajax({
				url: "${ctxPath}/class/addreply",
				processData: false,   //urlencoded(쿼리스트링 형식) 처리 금지
			    contentType: false,
				type: "post",
				data : formData,
				dataType : 'json'
			}).done((result)=>{
				$('#add').text('댓글등록 성공');
				//console.log(result);
				
				let str = "<table width='86%' align='center'>";
				//댓글 리스트 리로드
				$.each(result, function(index, reply) {
					str += "<tr>";
					str += "<td>"+ reply.mbNickName +"</td>";
					str += "<td><a href='${ctxPath}/download?fileNo="+reply.fileNo+"'><i class='fas fa-save' style='width:24px;color:#666;'></i></a></td>";
					str += "<td>"+ reply.clsBrdRepContent +"</td>";
					str += "<td>"+ reply.clsBrdRepDate +"</td>";
					str += "<td><a href='${ctxPath}/deletereply?clsBrdRepNo="+ reply.clsBrdRepNo +"&clsBrdNo="+ reply.clsBrdNo +"'><i class='fas fa-backspace'></i></a></td>";
					str += "</tr>";
				});	
				str += "</table>";

				$('#rTable').html(str);
				$('#clsBrdRepContent').val('');
				$('#clsBrdRepContent').focus();
			}).fail(function(err) {
				$('#add').text('댓글등록 실패');
			});
		}
	}

//댓글목록가져오기	
	$(document).ready(function() {
		getReplyList();
	});
	
	function getReplyList(){
		let replyurl = "${ctxPath}/class/getreplylist"; //mapping할 value
		$.ajax({
			url: replyurl,
		    method: "get",
			data : {clsBrdNo : bNo},
			dataType : 'json'
		}).done((result)=>{
			console.log("rList = ",result);
			let str = "<table width='86%' align='center'>";
			$.each(result, function(index, reply) {
				str += "<tr>";
				str += "<td>"+ reply.mbNickName +"</td>";
				str += "<td><a href='${ctxPath}/download?fileNo="+reply.fileNo+"'><i class='fas fa-save' style='width:24px;color:#666;'></i></a></td>";
				str += "<td>"+ reply.clsBrdRepContent +"</td>";
				str += "<td>"+ reply.clsBrdRepDate +"</td>";
				str += "<td><a href='${ctxPath}/deletereply?clsBrdRepNo="+ reply.clsBrdRepNo +"&clsBrdNo="+ reply.clsBrdNo +"'><i class='fas fa-backspace'></i></a></td>";
				str += "</tr>";
			});	
			str += "</table>";
			$('#rTable').html(str);
			$('#clsBrdRepContent').val('');
			$('#clsBrdRepContent').focus();
		}).fail(function(err) {
			$('#rTable').text('댓글 리스트를 가져올 수 없습니다');
		})
	}
	
	function getFormatDate(date){
		let year = date.getFullYear();
		let month = (1+date.getMonth());
		month = month >= 10 ? month : '0' + month;
		let day = date.getDate();
		day = day >= 10 ? day : '0' + day;
		return year + '-' + month + '-' + day;
	}

//댓글 삭제
$(".fas fa-backspace").on("click", ".fas fa-backspace", function() {
	if(bNo != bNo)
		return;

	let msg = confirm('해당 댓글을 정말 삭제하시겠습니까?'); 
	if(msg) {
		console.log("삭제 댓글 : "+ $(this).data("#rTable"));
		const param = {
			rTable: $(this).data("clsBrdRepContent"),  	// 삭제할 댓글번호
			clsBrdNo: bNo  						// 글번호. 나머지 첨부파일 정보 반환 
		};					
		$.ajax({
			url: "${ctxPath}/deletereply",
			method: "post",
			data: param,
			dataType : 'json'
		}).done((result)=> {
			console.log("dreplyList = ",result);
		}).fail(function(err) {
			$('#result').text('서버와 통신할 수 없습니다');
		});	
	}
});

//댓글 수정하기		
		//폼의 일부 데이터만 저장
	 	let formData = new FormData();
		formData.append("clsBrdRepNo", brNo);		
		formData.append("fileNo", $('#fileNo').val());
		formData.append("clsBrdRepContent", $('#r_contents').val());
		console.log("formData === ", formData);
		
	function updateReply(){
		if(brNo != "") {
			$.ajax({
				url: "${ctxPath}/class/updatereply",
			    type: "post",
				data : formData,
				dataType : 'json'
			}).done((result)=>{
				console.log("update = ",update);
			}).fail(function(err) {
				$('#update').text('댓글수정실패');
			});
		}
	};
</script>

	<!-- <div class="input-group">
  <input type="file" class="form-control" id="inputGroupFile04" aria-describedby="inputGroupFileAddon04" aria-label="Upload">
  <button class="btn btn-outline-secondary" type="button" id="inputGroupFileAddon04">Button</button>
</div>
<br>
<div class="form-floating">
  <textarea class="form-control" placeholder="Leave a comment here" id="floatingTextarea2" style="height: 100px"></textarea>
  <label for="floatingTextarea2">Comments</label>
</div> -->

</body>
</html>