/**
 * mypage.jsp에서 사용
 */
 
 function mypageReady(params){
 	const ctxPath = params.ctxPath;
 
 	$('#pwChange').on('click',function(){
		let mbPwd = $('#mbPwd').val();
		let newMbPwd = $('#newMbPwd').val();
		let mbPwdcheck = $('#mbPwdcheck').val();
		
		if(mbPwd==''){
			alert('기존 비밀번호을 입력하세요');
			return false;
		}
		if (newMbPwd==''){
			alert('새로운 비밀번호를 입력하세요');
			return false;
		}
		if (newMbPwd!=mbPwdcheck){
			alert('비밀번호가 일치하지 않습니다');
			return false;
		}
		
		// 비밀번호 변경 ajax 요청
		$.ajax({
			url: ctxPath + '/changepassword',
			data:{mbPwd:mbPwd,newMbPwd:newMbPwd},
			method: 'post',
			dataType:'json'
		}).done(function(data){
			alert(data.msg);
			return true;
		}).fail(function(err){
			alert('서버와 연결 할 수 없습니다.');
			return false;
		}); // ajax End
	}); // on End
 }