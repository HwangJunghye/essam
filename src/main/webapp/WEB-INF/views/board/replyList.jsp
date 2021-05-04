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
					str += "<td>"+ reply.fileNo +"</td>";
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

//댓글목록가져오기
	$(document).ready(function() {
		getReplyList();
	});
		
	function getReplyList(){
		let replyurl = "${ctxPath}/class/getreplylist"; //mapping할 value
		let reply_bNo = "${boardData.clsBrdNo}"; //글번호

			$.ajax({
				url: replyurl+reply_bNo,
				processData: false,   //urlencoded(쿼리스트링 형식) 처리 금지
			    contentType: false,
			    method: "get",
				data : {clsBrdNo : reply_bNo},
				dataType : 'json'
			}).done((result)=>{
				let comments = "";
				if(result.length < 1){
					comments = "등록된 댓글이 없습니다.";
				}else{
					$(result).each(function(){
						comments +='<br/>';
						comments +='<strong>';
						comments +='작성자 : ' + this.mbId;
						comments +='</strong>';
						comments +='작성날짜 : ' + this.clsBrdRepDate;
						comments +='<br/> <p>';
						comments +='댓글내용 : ';
						comments +=this.clsBrdRepContent;
						comments +='</p>';
						comments +='<br/>';
						comments +='<button type="button" class="btn btn-outline-success" id="replyUpdateBtn"';
						comments +='data-clsBrdRepNo='+this.clsBrdRepNo+'>'
						comments +='댓글수정';
						comments +='</button>';
						comments +='<button type="button" class="btn btn-outline-success" id="replyDeleteBtn"';
						comments +='data-clsBrdRepNo='+this.clsBrdRepNo+'>';
						comments +='댓글삭제';
						comments +='</button>';
						comments +='<br/>';
					});
				};
					$("#replylist").html(comments);
				console.log("rList = ",result);
				//printReply(result);  //가져온 정보를 화면에 출력
			}).fail(function(err) {
				$('#result').text('댓글 리스트를 가져올 수 없습니다');
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

//댓글 수정하기

	let brNo = "${reply.clsBrdRepNo}";	//댓글번호
		console.log("brNo == ", brNo);
		
		//폼의 일부 데이터만 저장
	 	let formData = new FormData();
		formData.append("clsBrdRepNo", brNo);		
		formData.append("fileNo", $('#fileNo').val());
		formData.append("clsBrdRepContent", $('#clsBrdRepContent').val());
		console.log("formData === ", formData);
		
	$(function() {
	
		if(brNo != "") {
			
			$.ajax({
				url: "${ctxPath}/class/updatereply",
				processData: false,   //urlencoded(쿼리스트링 형식) 처리 금지
			    contentType: false,
			    type: "post",
				data : formData,
				dataType : 'json'
			}).done((result)=>{
				console.log("update = ",update);
				//printReply(result);  //가져온 정보를 화면에 출력
			}).fail(function(err) {
				$('#update').text('댓글수정실패');
			});
		}
	});	
</script>
	<section>
		<!-- 댓글등록 -->
		<span id="add"></span>
		<form id="frmReply" action="${ctxPath}/class/addreply" method="post"
			enctype="multipart/form-data">
			<table>
				<tr>
					<td>${loginData.mbNickName}</td>
				</tr>
				<tr>
					<td>첨부파일 :&nbsp;<i class="far fa-save"></i>&nbsp; <input
						type="file" name="file"></td>
				</tr>

				<tr>
					<td><textarea rows=3 cols=42 name="r_contents" id="r_contents"></textarea></td>
					<td><input type="button" onclick="javascript:addReply();"
						value="등록"> <input type="reset" value="취소"></td>
				</tr>
			</table>
		</form>

 	<!-- 댓글리스트 -->
		<table>
			<tr>
				<td>${reply.mbNickName}</td>
				<td>${reply.fileNo}</td>
				<td>${reply.clsBrdRepContent}</td>
				<td>${reply.clsBrdRepDate}</td>
			</tr>
		</table>
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