/**
 * header.jsp에서 사용
 */

function headerReady(params) {
    let fMsg = params.fMsg;
    let ctxPath = params.ctxPath;
    if(params.modal!=''){
    	$('#moaModal').modal('show');
    }
    // alert창 출력
    if (fMsg != '') {
        alert(fMsg);
    }

    // 로그아웃 버튼 클릭시 로그아웃(post) 실행
    $('#logout').on('click', function () {
        // form 태그 생성
        let $logoutForm = $('<form>');
        $logoutForm.attr('action', ctxPath + '/logout');
        $logoutForm.attr('method', 'post');
        // body에 추가
        $logoutForm.appendTo('body');
        // 전송(로그아웃 실행)
        $logoutForm.submit();
    }); // on End

    // 로그인 모달창 포커스
    $("#moaModal").on('shown.bs.modal', function () {
        $("#mbId").focus();
    });

} // headerReady End