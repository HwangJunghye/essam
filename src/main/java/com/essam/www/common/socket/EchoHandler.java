package com.essam.www.common.socket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.essam.www.bean.MemberBean;
import com.essam.www.member.MemberMM;

/**
 * @Title : 웹소켓 핸들러
 * @author : 고연미
 * @date : 2021.05.07
 */
public class EchoHandler extends TextWebSocketHandler {

	@Autowired
	private MemberMM mm;
	
	private static final Logger logger = LoggerFactory.getLogger(EchoHandler.class);
	// 접속한 모든 세션
	List<WebSocketSession> sessions = new ArrayList<WebSocketSession>();
	// 로그인한 user 정보 : mbId, WebSocketSession
	Map<String, WebSocketSession> userSessionsMap = new HashMap<String, WebSocketSession>();

	// 연결 해제시
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		System.out.println("afterConnectionClosed " + session + ", " + status);
		String socketId = session.getId();
		//DB 접속 user 정보 삭제
		if(!mm.delLoginMb(socketId))
			logger.warn("웹소켓 접속정보 DB 삭제 실패");
		
		userSessionsMap.remove(socketId);
		sessions.remove(session);
	}

	// 서버 접속 성공시
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		sessions.add(session);
		String socketId = session.getId();

		//httpSession 로그인정보 가져오기
		MemberBean loginData = getLoginData(session);

		//로그인정보가 있으면
		if (!ObjectUtils.isEmpty(loginData)) {
			String mbId = loginData.getMbId();
			//로그인한 user 정보 저장
			userSessionsMap.put(mbId, session);
			//DB에 로그인 user 정보 저장
			if(!mm.setLoginMb(mbId, socketId))
				logger.warn("웹소켓 접속정보 DB 등록/업데이트 실패");
		}
	}

	// 소켓에 메세지 전송시
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

		// message.getPayload() : 브라우저에서 소켓으로 보낸 메세지 내용
		String msg = message.getPayload();
		logger.info(msg);

		MemberBean loginData = getLoginData(session); 
		/*  메세지를 보내온 세션에게 메세지 전송
		if(!ObjectUtils.isEmpty(loginData)) { 
			String mbNickName = loginData.getMbNickName();
			session.sendMessage(new TextMessage(mbNickName + "님 반갑습니다.")); 
		}	 */

		// 학생Id와 메세지를 분리해 배열에 담기
		String[] msgArray = msg.split(","); // 학생Id,메세지

		// 현재 접속중인 학생에게 메세지 전송
		if (!ObjectUtils.isEmpty(userSessionsMap.get(msgArray[0]))) {
			WebSocketSession receiverSession = userSessionsMap.get(msgArray[0]);
			TextMessage tmpMsg = new TextMessage(msgArray[1]);
			receiverSession.sendMessage(tmpMsg);
			// DB에 메세지 전송정보 저장
			if(!ObjectUtils.isEmpty(loginData)) { 
				if(!mm.setLoginMsg(loginData.getMbId(), msgArray[0], msgArray[1]))
					logger.warn("웹소켓 메세지 전송정보 DB 등록 실패");
			}
		}
	}

	//httpSession 로그인정보 가져오기
	protected MemberBean getLoginData(WebSocketSession session) {
		Map<String, Object> httpSession = session.getAttributes();
		MemberBean loginData = (MemberBean)httpSession.get("loginData");
		return loginData;		
	}
}