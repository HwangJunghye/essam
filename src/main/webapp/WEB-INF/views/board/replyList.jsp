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
			<td>첨부파일 :<i class='fas fa-save' style='color:#666;'>	<input type="file" name="file" id="file"></td>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td colspan=2><textarea rows=3 cols=100 name="clsBrdRepContent" id="clsBrdRepContent"></textarea></td>
			<td><input type="button" onclick="javascript:addReply();" value="등록"> <input type="reset" value="취소"></td>
		</tr>
		</table>
		</form>

		<!-- 댓글리스트 -->
	 	<span id="rTable">
		</span>

	<script>
	
//댓글등록하기
	let bNo = "${boardData.clsBrdNo}";  //글번호
	console.log("bNo == ", bNo);
	let mi = "${loginData.mbId}";  //글번호
	console.log("login mbId == ", mi);

	function addReply(){ 
		
		//FormData : js 지원 객체이므로 jQ객체($('#frm'))를 사용할수없다.
		//js 객체로 파일 저장
		let $obj = $("#file");
		let files = $obj[0].files;
		
		//폼의 일부 데이터만 저장
	 	let formData = new FormData();
		formData.append("clsBrdNo", bNo);		
		formData.append("file", files[0]);
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
				alert("등록되었습니다.")
				//$('#add').text('댓글등록 성공');
				//console.log(result);
				// 댓글 폼 reset
				$('#frmReply')[0].reset();
				
				displayRList(result);//댓글리스트 출력

				$('#clsBrdRepContent').val('');
			}).fail(function(err) {
				//$('#add').text('댓글등록 실패');
			});
		}
	}

//댓글목록 가져오기	
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
			
			displayRList(result);//댓글리스트 출력
			
			$('#clsBrdRepContent').val('');
		}).fail(function(err) {
			$('#rTable').text('댓글 리스트를 가져올 수 없습니다');
		})
	}
	//댓글리스트 출력 함수
	function displayRList(result) {
		let str = "<table width='86%' align='center' class='table table-hover'>";
		$.each(result, function(index, reply) {
			console.log("reply mbId ==", reply.mbId);
			console.log("loginData mbId ==", mi);
			str += "<tr>";
			str += "<td style='white-space:nowrap'>"+ reply.mbNickName +"</td>";
			if(reply.fileNo != null){
				str += "<td><a href='${ctxPath}/download?fileNo="+reply.fileNo+"'><i class='fas fa-save' style='width:24px;color:#666;'></i></a></td>";
			}else{
				str += "<td>"+""+"</td>";
			}
			//str += "<td><a href='${ctxPath}/download?fileNo="+reply.fileNo+"'><i class='fas fa-save' style='width:24px;color:#666;'></i></a></td>";
			str += "<td>"+ reply.clsBrdRepContent +"</td>";
			str += "<td>"+ reply.clsBrdRepDate +"</td>";
			//str += "<td><a href='#' onclick='javascript:isRDelete("+ reply.clsBrdRepNo +","+ reply.clsBrdNo +");'><i class='fas fa-backspace'></i></a></td>";

			if(reply.mbId == mi){
				str += "<td><a href='javascript:void(0);' onclick='javascript:isRDelete("+ reply.clsBrdRepNo +","+ reply.clsBrdNo +");'><i class='fas fa-backspace'></i></a></td>";
			}
			str += "</tr>";
		});	
		str += "</table>";
		$('#rTable').html(str);		
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
function isRDelete(clsBrdRepNo, clsBrdNo) {
	let msg = confirm('해당 댓글을 정말 삭제하시겠습니까?'); 
	const paramD = {
			clsBrdRepNo: clsBrdRepNo,
			clsBrdNo: clsBrdNo
		}
	if(msg) {			
		$.ajax({
			url: "${ctxPath}/deletereply",
			method: "post",
			data: paramD,
			dataType : 'json'
		}).done((result)=> {
			console.log("dreplyList = ",result);
			
			displayRList(result);//댓글리스트 출력
		}).fail(function(err) {
			$('#result').text('서버와 통신할 수 없습니다');
		});			
	}
}
</script>

</body>
</html>