<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>댓글</title>
</head>
<body>
	<!-- 댓글 form 넣기 스토리보드 12번 -->
	<div id="replyArea"></div>
	<script>
//댓글등록하기
	function addReply(){
		let bNo = "${boardData.clsBrdNo}";	//글번호
		console.log("bNo == ", bNo);
		//FormData : js 지원 객체이므로 jQ객체($('#frm'))를 사용할수없다.
		//js 객체로 파일 저장
		let $obj = $("#file");
		let files = $obj[0].files;
		
		//폼의 일부 데이터만 저장
	 	let formData = new FormData();
		formData.append("clsBrdNo", bNo);		
		formData.append("file", files[0]);
		formData.append("clsBrdRepContent", $('#clsBrdRepContent').val());
		console.log("formData === ", formData);

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
				
				let str = "<table>";
				//댓글 리스트 리로드
				$.each(result, function(index, reply) {
					str += "<tr>";
					str += "<td>"+ reply.mbNickName +"</td>";
					str += "<td>"+ reply.clsBrdRepContent +"</td>";
					str += "<td>"+ reply.clsBrdRepDate +"</td>";
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
	</script>
<%--  //댓글목록가져오기
	let bNo = "${boardData.clsBrdNo}";	//글번호
		console.log("bNo == ", bNo);
		// 폼의 일부 데이터만 저장
	 	let formData = new FormData();
		formData.append("clsBrdNo", bNo);
		formData.append($('#file')[0].files[0], $('#file'));
		
		/* formData.append("fileNo", $('#file')); */
		formData.append("clsBrdRepContent", $('#clsBrdRepContent').val());
		console.log("formData === ", formData);
		
	$(function() {
		if(bNo != "") {
			
			$.ajax({
				url: "${ctxPath}/class/getreplylist",
				processData: false,   //urlencoded(쿼리스트링 형식) 처리 금지
			    contentType: false,
			    type: "post",
				data : formData,
				dataType : 'json'
			}).done((result)=>{
				console.log("rList = ",result);
				//printReply(result);  //가져온 정보를 화면에 출력
			}).fail(function(err) {
				$('#result').text('댓글 리스트를 가져올 수 없습니다');
			});
		}
	})  --%>

	<%--
//댓글 수정하기
$(function() {
		if(bNo != "") {
			
			$.ajax({
				url: "${ctxPath}/class/updatereply",
				method: "get",
				data : {clsBrdNo : bNo},
				dataType : 'json'
			}).done((result)=>{
				console.log("update = ",update);
				//printReply(result);  //가져온 정보를 화면에 출력
			}).fail(function(err) {
				$('#update').text('댓글 리스트를 가져올 수 없습니다');
			});
		}
	});	
 --%>
	<section>
		<!-- 댓글등록 화면에 출력 --><span id="add"></span>
		<form id="frmReply" action="${ctxPath}/class/addreply" method="post" enctype="multipart/form-data">
			<table>
			<tr>
				<td>${loginData.mbNickName}</td>
			</tr>
			<tr>
				<td>첨부파일 :&nbsp;<i class="far fa-save"></i>&nbsp; 
				<input type="file" id="file" name="file" style="width: 500px">
				</td>
			</tr>
			<tr>
				<td><textarea id="clsBrdRepContent" class="form-control" placeholder="댓글을 입력해주세요"
					style="height: 100px"></textarea></td>
			</tr>
			<tr>
				<td>
				<input type="button" onclick="javascript:addReply();" value="등록">
				<input type="reset" value="취소"></td>
			</tr>
			</table>
		</form>
		<div id="rTable"></div>
	</section>



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