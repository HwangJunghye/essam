/**
 * join.jsp에서 사용
 */
 	let ctxPath;
	
	function joinReady(params){
		ctxPath = params.ctxPath;
		$('#mbIdInput').on('focusout',function(evt){
			idCheck();
		}); // on End
		
		$('#mbPwd,#mbPwdcheck').on('focusout',function(evt){
			pwdCheck();
		}); // on End
	} // joinReady End
	
	function idCheck(){
		let mbId = $('#mbIdInput').val();
		if(mbId==''){
			$('#idCheckResult').empty().append('아이디를 입력하세요').css({"color":"red","font-size":"8px"});
			return false;
		}
		// 아이디 정규식 검사
		const regExp = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i;

		if (!mbId.match(regExp)){
			$('#idCheckResult').empty().append('아이디는 이메일 형식으로 입력해주세요').css({"color":"red","font-size":"8px"});
			return false;
		}		
		
		let isOk = false; // 아이디 중복 체크용 변수
		$.ajax({
			url:ctxPath + "/checkemail",
			data:{mbId:mbId},
			dataType:'json',
			method:'get',
			async:false
		}).done(function(data){
			if(data.isOk == 'true'){
				$('#idCheckResult').empty().append(data.msg).css({"color":"blue","font-size":"8px"});
				isOk = true;
			}else{
				$('#idCheckResult').empty().append(data.msg).css({"color":"red","font-size":"8px"});
				isOk = false;
			}
		}).fail(function(err){
			$('#idCheckResult').empty().append('서버 통신 실패').css({"color":"red","font-size":"8px"});
		}); // ajax End
		return isOk;
	} // idCheck End
	
	function pwdCheck(){
		let mbPwd = $('#mbPwd').val();
		let mbPwdcheck = $('#mbPwdcheck').val();
		
		if(mbPwd==''){
			$('#pwdCheckResult').empty().append('비밀번호를 입력해주세요').css({"color":"red","font-size":"8px"});
			return false;
		}else{
			$('#pwdCheckResult').empty();
			
			// 비밀번호 정규식 검사
			
			if(mbPwd != mbPwdcheck){
				$('#pwdMatchResult').empty().append('비밀번호가 일치하지 않습니다').css({"color":"red","font-size":"8px"});
				return false;
			}else{
				$('#pwdMatchResult').empty().append('비밀번호가 일치합니다').css({"color":"blue","font-size":"8px"});
				return true;
			}
		}
	} // pwdCheck End
	
	
	// 회원가입시 마지막으로 form 검사
	function joinFormCheck() {
		if(idCheck() && pwdCheck()){
			return true;
		}else{
			alert('입력한 값이 유효하지 않습니다');
			return false;
		}
	}
 
 