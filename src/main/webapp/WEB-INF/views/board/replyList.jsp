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
	
	$("#success").click(function() {
		if(bBean != "") {
						
			$.ajax({
				url: "${ctxPath}/class/addReply",
				method: "get",
				data : {clsBrdRepNo:clsBrdRepNo, clsBrdNo:clsBrdNo,
							mbId:mbId, fileNo:fileNo, clsBrdRepDate:clsBrdRepDate,
							clsBrdRepContent:clsBrdRepContent},
				dataType : 'json'
			}).done((success)=>{
				console.log("suc = ",suc);
				printReply(result);  //가져온 정보를 화면에 출력
			}).fail(function(err) {
				$('#suc').text('댓글등록실패');
			});
		}
	});
	
//댓글목록가져오기
	let bNo = "${boardData.clsBrdNo}";	//글번호
	
	$(function() {
		if(bNo != "") {
			
			$.ajax({
				url: "${ctxPath}/class/getreplylist",
				method: "get",
				data : {clsBrdNo : bNo},
				dataType : 'json'
			}).done((result)=>{
				console.log("rList = ",result);
				//printReply(result);  //가져온 정보를 화면에 출력
			}).fail(function(err) {
				$('#result').text('댓글 리스트를 가져올 수 없습니다');
			});
		}
	});
	
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
	
</script>
<section>
		<!-- 댓글등록 화면에 출력 -->
		<div class="input-group">
		첨부파일 :&nbsp;<i class="far fa-save"></i>&nbsp;
			<input type="file"  class="form-control" id="inputGroupFileAddon04" 
			aria-describedby="inputGroupFileAddon04" aria-label="Upload" 
			style="width: 500px">
		</div>
		<br>
		<div class="form-floating">
			<textarea class="form-control" placeholder="댓글을 입력해주세요" 
				id="floatingTextarea2" style="height: 100px"></textarea>
		  	<a type="button" id="success" class="btn btn-outline-secondary" href="${ctxPath}/class/boardlist">등록</a>
		  	<a type="button" class="btn btn-outline-secondary" href="${ctxPath}/class/getreplylist">취소</a>
		</div>
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