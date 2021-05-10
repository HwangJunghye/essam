/**
 * 웹소켓 통신
 * @author 고연미 on 2021.05.08
 */
 function websocketReady(params) {
		let mbId = params.mbId;
		let mbNickName = params.mbNickName;
		let mbType = params.mbType;
		let clsNo = params.clsNo;
    	let ctxPath = params.ctxPath;
		
		//전송할 메세지에 쉼표(,) 사용시 자동 삭제 ('' 으로 치환)
		$('#sendMsg').keyup(function(){
			let str = $('#sendMsg').val().replace( /,/gi, '');
			$('#sendMsg').val(str);
		});
		//웹소켓 통신
		send_message();

		function send_message() {
			//웹소켓 핸들러 path
			const wsUri = ctxPath + "/echo/";
			//웹소켓 연결
			websocket = new SockJS(wsUri);
			socket = websocket;

			//소켓 연결시
			websocket.onopen = function(evt) {
				console.log('info: websocket connection opened.');
				$('#btnSendMsg').on("click", function(evt) {
					evt.preventDefault();
					let str = $('#sendMsg').val();					
					//메세지가 있으면 전송
					if($.trim(str) != "")
						onOpen(str);
				})
			};

			//받은 메세지가 있으면
			websocket.onmessage = function(evt) {
				var data = evt.data;
				console.log("ReceivMessage : " + data + "\n");
				//푸쉬 알림(toastr)
				onMessage(evt);
			};

			//오류 발생시
			websocket.onerror = function(evt) {
				onError(evt);
			};
		}

		function onOpen(msg) {
			//강사인 경우
			if (mbType == 2 && clsNo != "") {
				//로그인한 학생목록(mbId) 가져오기
				$.ajax({
					url : ctxPath + "/class/getonstudents",
					method : "get",
					data : {
						clsNo : clsNo,
						mbId : mbId
					},
					dataType : 'json'
				}).done(function(result) {
					//로그인한 학생 수
					let rCnt = Object.keys(result).length;
					//로그인한 학생이 존재할 경우
					if(rCnt > 0) {
						$.each(result, function(index, item) {
							let sendData = item + "," + "[from "+ mbNickName +"] " + msg;
							console.log("sendData  => ", sendData);
							//메세지 전송
							websocket.send(sendData);
						});
						//alert("푸쉬알림 메세지를 전송하였습니다.");
						$('#sendMsg').val('');
					} else 
						alert("현재 로그인한 학생이 존재하지 않습니다.");
				}).fail(function(err) {
					$('#result').text('학생목록을 가져올 수 없습니다');
				});
			}
		}

		function onMessage(evt) {
			$('#messageArea').append(evt.data + "\n");
			let data = evt.data;

			// 토스트 알림
			// closeButton 생성여부
			toastr.options.closeButton = true;
			// 메시지가 표시되는 시간
			toastr.options.timeOut = 30000;	//30초
			// 메시지 위로 커서를 올렸다가 내렸을 때 표시되는 시간
			toastr.options.extendedTimeOut = 30000;	//30초
			// 만약 메시지 표시되는 시간과 올렸렸다가 내렸을 때 표시되는 시간을 0으로 하면 메시지는 사라지지 않는다.
			toastr.info(data, "Direct Message"); //하늘색
			//toastr.success(data,"다이렉트 메세지");	//연두색
		}

		function onError(evt) {
			console.log('Errors : ', evt.data);
		}
	
}	