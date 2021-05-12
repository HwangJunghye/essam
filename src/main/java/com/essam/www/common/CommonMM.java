package com.essam.www.common;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;

import com.essam.www.bean.BoardBean;
import com.essam.www.bean.ClassBean;
import com.essam.www.bean.CurriculumBean;
import com.essam.www.bean.MemberBean;
import com.essam.www.bean.TeacherBean;
import com.essam.www.constant.Constant;
import com.essam.www.curriculum.CurriculumMM;
import com.essam.www.curriculum.ICurriculumDao;
import com.essam.www.eclass.IClassDao;
import com.essam.www.member.IMemberDao;

@Service
public class CommonMM {
	private static final Logger logger = LoggerFactory.getLogger(CommonMM.class);
	
	@Autowired
	private ICommonDao coDao;
	@Autowired
	private IMemberDao mDao;
	@Autowired
	private IClassDao cDao;	
	@Autowired
	private ICurriculumDao crDao;
//	검색페이지 이동	
//	검색 결과 가져오기(ajax)	

	/**
	 * (CO01)메인페이지로 이동<br>
	 * 학생회원인 경우 마이클래스도 가져오기<br>
	 * 공통적으로 new,hot 클래스 목록 가져오기
	 * @Author 고연미 on 28/04/2021
	 */
	public ModelAndView goIndex(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		List<ClassBean> cList = null;
		List<ClassBean> hList = null;
		int myClassCnt = 0;

		// My 클래스 정보 가져오기 (학생 로그인인 경우)
		MemberBean loginData = new MemberBean();
		loginData = (MemberBean) request.getSession().getAttribute("loginData");

		if (loginData != null) {
			if (loginData.getMbType() == 1) {
				cList = getClassList("my", loginData.getMbId());
				// 가져온 정보를 mav "myList" 에 넣기
				mav.addObject("myList", cList);
				// 수강중인 클래스 수 가져와 담기
				myClassCnt = coDao.getMyClsCnt(loginData.getMbId());
				mav.addObject("myClassCnt", myClassCnt);
			}
		}

		// new 클래스 정보 가져오기
		cList = getClassList("new", "");
		// 가져온 정보를 mav "nList" 에 넣기
		mav.addObject("nList", cList);
		// hot 클래스 정보 가져오기
		hList = getClassList("hot", "");
		// 가져온 정보를 mav "nList" 에 넣기
		mav.addObject("hList", hList);

		// index.jsp로 이동하기 위해 viewname 지정
		mav.setViewName("common/index"); // .jsp

		return mav;
	}
	public ModelAndView getMyClass(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		List<ClassBean> cList = null;

		// My 클래스 정보 가져오기 (학생 로그인인 경우)
		MemberBean loginData = new MemberBean();
		loginData = (MemberBean) request.getSession().getAttribute("loginData");

		if (loginData != null) {
			if (loginData.getMbType() == 1) {
				cList = getClassList("my", loginData.getMbId());
				// 가져온 정보를 mav "myList" 에 넣기
				mav.addObject("myList", cList);
			}
		}
		mav.setViewName("common/ifr_myClass"); // .jsp

		return mav;
	}
	/**
	 * (CO02) 클래스 리스트 가져오기
	 * @Author 고연미 on 28/04/2021
	 */
	public List<ClassBean> getClassList(String str, String mbId) {
		List<ClassBean> cList = null;
		switch(str) {
		case "new":
			cList = coDao.getClassListNew();
			break;
		case "hot":
			cList = coDao.getClassListHot();
			break;
		case "my":
			cList = coDao.getClassListMy(mbId);
			break;
		}
		return cList;
	}	

	// 검색 페이지 이동
	public ModelAndView goSearch(Integer cate1No, Integer cate2No, String keyword) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("common/search"); // .jsp
		StringBuffer sb = new StringBuffer("");

		boolean keywordFlag = keyword != null && !keyword.equals("");
		boolean cate1Flag = cate1No != null && (cate1No > 0 && cate1No < Constant.cate1Name.length);
		boolean cate2Flag = cate2No != null && (cate2No > 0 && cate2No < Constant.cate2Name.length);

		if (!keywordFlag && !cate1Flag && !cate2Flag) {
			sb.append("클래스 검색");
		} else {
			if (cate1Flag) {
				mav.addObject("cate1No", cate1No);
				sb.append(Constant.cate1Name[cate1No]);
			}
			if (cate1Flag && cate2Flag) {
				sb.append(" > ");
			}
			if (cate2Flag) {
				mav.addObject("cate2No", cate2No);
				sb.append(Constant.cate2Name[cate2No]);
			}
			if (keywordFlag && (cate1Flag || cate2Flag)) {
				sb.append(", ");
			}
			if (keywordFlag) {
				mav.addObject("keyword", keyword);
				sb.append("검색어 : ");
				sb.append(keyword);
			}
		}
		mav.addObject("navtext", sb.toString());
		return mav;
	}

	/**
	 * 클래스 검색 결과 가져오기<br>
	 * pageNo : 페이지 번호<br>
	 * cate1No,cate2No : 카테고리1,2 번호<br>
	 * keyword : 검색 키워드
	 */
	public Map<String, Object> getSearchList(Integer pageSize, Integer pageNo, 
			Integer cate1No, Integer cate2No, String keyword) {
		String keywords[] = null;
		if (keyword != null) {
			// 문자열 좌우 공백 제거
			keyword.replaceAll("(^\\p{Z}+|\\p{Z}+$)", "");
			// 공백을 기준으로 문자열 자르기
			keywords = keyword.split("\\s+");
		}

		List<ClassBean> cList = coDao.getSearchList((pageSize==null?20:pageSize), pageNo, cate1No, cate2No, keywords);
		CListDecending cListDecending = new CListDecending();
		Collections.sort(cList, cListDecending);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("pageNo", pageNo); // 페이지 번호
		result.put("pageSize", pageSize); // 검색하는 페이지의 크기
		result.put("cate1No", cate1No); // 카테고리1
		result.put("cate2No", cate2No); // 카테고리2
		result.put("keyword", keyword); // 키워드
		result.put("cList", cList); // 검색결과
		result.put("searchSize", (cList == null ? 0 : cList.size())); // 검색 결과수
		// return ResponseEntity.ok(cList);
		return result;
	}

	/**
	 * (CO05, CO06)클래스소개 이동, 클래스정보 가져오기
	 * @Author 고연미 on 28/04/2021
	 */
	public ModelAndView goClassInfo(String clsNo, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		String sessionId, mbId = null;
		
		//클래스번호가 없으면 메인으로 이동
		if(StringUtils.isEmpty(clsNo)) {
			mav.setViewName("redirect:/");			
		} else {
			//클래스정보 가져와 bean에 담기
			ClassBean cb = cDao.getClassInfo(clsNo);
			
			//가져온 클래스정보가 있으면 조회수 추가
			if(!ObjectUtils.isEmpty(cb)) {
				//loginData가 있으면
				if(!ObjectUtils.isEmpty(request.getSession().getAttribute("loginData"))) {
					//mbId 가져오기
					MemberBean loginData = (MemberBean)request.getSession().getAttribute("loginData");
					mbId = loginData.getMbId();
				} 
				sessionId = request.getSession().getId();
				//같은 sessionId와 clsNo가 등록된 데이터가 없으면
				if(coDao.getClsViewCnt(clsNo, sessionId) < 1) {
					//조회수 추가
					if(!coDao.addClsView(clsNo, sessionId, mbId))
						logger.info("클래스 조회수 추가 실패.");					
				}
			}
			mav.addObject("classInfo", cb);
			//mav에 네비타이틀 추가
			mav.addObject("navtext", "클래스 > "+ cb.getClsName());
			
			//강사 정보 가져와 mav에 담기 (MemberMM)
			TeacherBean tb = mDao.getTeacherProfile(cb.getMbId());
			mav.addObject("teacherInfo", tb);
			
			//커리큘럼 리스트 가져와 mav에 담기 (CurriculumMM)
			List<CurriculumBean> crList = crDao.getCurriculumList(clsNo, 1);
			mav.addObject("curriList", crList);
			
			mav.setViewName("class/classinfo_main");			
		}
		return mav;
	}
}

// List<ClassBean> 내림차순 정렬
class CListDecending implements Comparator<ClassBean>{
	@Override
	public int compare(ClassBean a, ClassBean b) {
		String aClsNo = a.getClsNo();
		String bClsNo = b.getClsNo();
		
		return bClsNo.compareTo(aClsNo);
	}
}