package com.essam.www.d;

import org.apache.ibatis.annotations.Insert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DMM {
	@Autowired
	private IDDao DD;

	public int[] memberjoin() {
		int i = 1;
		int[] cate1No = new int[i];
		int[] cate2No = new int[i];
		
		for(i=1; i<9; i++) {
			return cate2No;
		}
			return cate1No;
	}
	
//	계정관리 이동하기	
//	회원정보 수정 실행	
//	회원정보 가져오기 

}
