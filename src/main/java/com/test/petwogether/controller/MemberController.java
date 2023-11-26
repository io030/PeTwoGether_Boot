package com.test.petwogether.controller;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.test.petwogether.model.AdoptListDTO;
import com.test.petwogether.model.admin.AdminDTO;
import com.test.petwogether.model.admin.AnswerDTO;
import com.test.petwogether.model.admin.HealthDTO;
import com.test.petwogether.model.member.AdoptReviewDTO;
import com.test.petwogether.model.member.MemberDTO;
import com.test.petwogether.model.member.QuestionDTO;
import com.test.petwogether.model.member.StrayDTO;
import com.test.petwogether.model.petsitter.PetSitterDTO;
import com.test.petwogether.service.MemberService;

@Controller
public class MemberController {

	@Autowired
	private MemberService service;


	@GetMapping("/main/index")
	public String index() {

		return "main/index";
	}

	//설   
	@GetMapping("/member/login")
	public String login() {

		return "member/login";
	}

	//설
	@PostMapping(value="/member/loginok")
	public String loginok(String user, String id, String pass, HttpSession session, Model model) {

		String level = "";
		String sseq = "";


		if (user.equals("m")) {
			MemberDTO dto = service.mLoginCheck(id, pass);
			if (dto != null) {
				level = dto.getMlevel();
				sseq = dto.getMseq();
			}

		} else if (user.equals("p")) {
			PetSitterDTO dto = service.fLoginCheck(id, pass);
			if (dto != null) {
				level = dto.getPslevel();
				sseq = dto.getPsseq();
			}
		} else {
			AdminDTO dto = service.aLoginCheck(id, pass);
			if (dto != null) {
				level = dto.getAdlevel();
				sseq = dto.getAdseq();
			}
		}

		if (!level.equals("")) {
			//로그인 성공
			session.setAttribute("auth", id); 
			session.setAttribute("lv", level);
			session.setAttribute("sseq",sseq);

			return "redirect:/main/index";
		} else {
			//로그인 실패
			return "member/login";
		}

	}

	//설   
	@GetMapping("/member/logout")
	public String logout(HttpSession session) {

		session.removeAttribute("auth");
		session.removeAttribute("lv");   

		return "redirect:/member/login";
	}





	//지현 SRART ========


	//목록 읽기, RESTful
	//ResponseBody를 해주면, HTML 페이지 리턴이 아닌 데이터가 리턴이 됨
	//RestController를 사용하면 아래의 두 어노테이션을 하나로 합칠 수 있음
	@ResponseBody
	@GetMapping("/member/adoptreview")
	public ModelAndView adrlist(Model model, String page) {

		AdoptReviewDTO ardto = new AdoptReviewDTO();

		//페이징 처리

		System.out.println(page);
		//한 페이지 당 출력할 게시물 수
		int pageSize = 10;

		//총 게시물 수
		int totalCount = service.getTotalAReview();

		//총 페이지 수
		int totalPage = (int)(Math.ceil((double)totalCount/pageSize));

		//페이지바 태그
		String pagebar = "";

		//한번에 보여지는 페이지 수
		int blockSize = 10;

		int nowPage = 0;		//현재 페이지 번호(= page)
		int begin = 0;			//rnum 시작 위치
		int end = 0;			//rnum 끝 위치		

		if (page == null || page == "") nowPage = 1;
		else nowPage = Integer.parseInt(page);

		begin = ((nowPage - 1) * pageSize) + 1;
		end = begin + pageSize - 1; 

		//출력될 페이지 번호
		int n = 0;

		//루프 변수
		int loop = 0;

		loop = 1;
		n = ((nowPage -1)/blockSize) * blockSize + 1;

		if(n == 1) {
			pagebar += String.format(" <a href='/member/adoptreview?page=1'> [이전 10페이지]&nbsp;&nbsp;&nbsp;</a> ");
		} else {
			pagebar += String.format(" <a href='/member/adoptreview?page=%d'> [이전 10페이지]&nbsp;&nbsp;&nbsp;</a> ", n-1);			
		}

		while (!(loop > blockSize || n > totalPage)) {

			if (nowPage == n) {
				pagebar += String.format(" <a href='#!' style='color:#F8A1A4;'>%d</a> ", n);
			} else {
				pagebar += String.format(" <a href='/member/adoptreview?page=%d'>%d</a> ", n, n);
			}

			loop++;
			n++;

		}

		if (n > totalPage) {
			pagebar += String.format(" <a href='/member/adoptreview?page=%d'>&nbsp;&nbsp;&nbsp;[다음 10페이지]</a> ", totalPage);			
		} else {			
			pagebar += String.format("  <a href='/member/adoptreview?page=%d'>&nbsp;&nbsp;&nbsp;[다음 10페이지]</a> ", n);
		}

		HashMap<String, Integer> map = new HashMap<String, Integer>();

		//rownum에 맞춰서 데이터를 잘라와야함
		map.put("begin", begin);
		map.put("end", end);


		//페이징 처리 끝

		List<AdoptReviewDTO> adrlist = service.adrlist(map);

		//regdate 처리
		for(int i=0; i<adrlist.size(); i++) {

			adrlist.get(i).getArregdate();

			String regdate = (adrlist.get(i).getArregdate()).substring(0, 10);

			adrlist.get(i).setArregdate(regdate);
		}


		//RESTful로 데이터를 얻어와서 html로 연결
		ModelAndView modelAndView = new ModelAndView();

		modelAndView.addObject("adrlist", adrlist);
		modelAndView.addObject("pagebar", pagebar);
		modelAndView.setViewName("member/adoptreview");

		//System.out.println(adrlist.toString());


		return modelAndView;
	}

	@ResponseBody 
	@GetMapping("/member/adoptreviewsearch")
	public List<AdoptReviewDTO> arlist(String word){

		List<AdoptReviewDTO> adrlist = service.adrsearch(word);

		for (AdoptReviewDTO dto : adrlist) {

			dto.setArregdate(dto.getArregdate().substring(0,10));

		}

		System.out.println();
		System.out.println(adrlist);

		return adrlist;

	}//adoptreviewsearch


	//게시물 자세히보기
	@GetMapping("/member/adoptreviewdetail")
	public String adrdetail(String arseq, Model model, HttpSession session, String arviews) {		

		AdoptReviewDTO ardto = service.getadrdetail(arseq);
		int arseqs = Integer.parseInt(arseq);
		int arview = 0;

		if(arviews == null || arviews.equals(0) || arviews =="") {
			arview = Integer.parseInt(ardto.getArview());
			arview++;
		} else {
			arview = Integer.parseInt(arviews);
		}

		//조회수 증가

		int result = service.updatearview(arseq);

		model.addAttribute("arview", arview);
		model.addAttribute("ardto", ardto);
		model.addAttribute("arseqs", arseqs);

		return "/member/adoptreviewdetail";
	}

	//게시물 작성 폼
	@GetMapping("/member/adoptreviewadd")
	public String adoptreviewadd() {

		return "member/adoptreviewadd";
	}

	//게시물 등록하기
	@PostMapping("/member/adoptreviewaddok")
	public String adoptreviewaddok(HttpServletRequest req, HttpSession session) {

		AdoptReviewDTO ardto = new AdoptReviewDTO();

		//MultipartRequest와 동일
		MultipartHttpServletRequest multi = (MultipartHttpServletRequest)req;

		//작성되는 게시물의 게시물 번호 알아오기 > detail 페이지 이동을 위해서
		int maxarseq = (service.getmaxarseq() + 1);
		String arseq = maxarseq + "" ;

		String artitle = multi.getParameter("artitle");
		String arcontent = multi.getParameter("arcontent");
		String mseq = (String)session.getAttribute("sseq");

		MultipartFile attach = multi.getFile("arfile");

		String filename = attach.getOriginalFilename();
		//업로드한 파일이 저장될 경로
		String path = req.getRealPath("/files");


		if(filename != null && filename !="" && filename.length() != 0) {
			//파일명 중복 방지 > 파일명 확인하는 method 인 getFileName 생성
			filename = getFileName(path, filename);

		} else {
			filename ="null";
		}

		//파일 이동
		File file2 = new File(path + "\\" + filename);

		ardto.setArfile(filename);

		try {

			attach.transferTo(file2);

		} catch (Exception e) {
		}


		//(arseq, mseq, artitle, arcontent, arregdate, arview, arfile)
		ardto.setArtitle(artitle);
		ardto.setArcontent(arcontent);
		ardto.setMseq(mseq);
		ardto.setArseq(arseq);		


		int result = service.adradd(ardto);	


		if(result == 1){ 

			return "redirect:/member/adoptreviewdetail?arseq=" + arseq;

		} else { 

			return "redirect:/member/adoptreview";

		}


	}//adoptreviewaddok

	private String getFileName(String path, String filename) {


		// 저장 폴더내의 파일명을 중복되지 않게 만들기
		// path = "resource/files"
		// filename = "test.txt"

		// 덮어쓰기 할때 오류가 나니까 이렇게 이름이 바뀌게 해주자.
		// test.txt > test(1).txt > test(2).txt
		int n = 1; // 인덱스 숫자
		int index = filename.lastIndexOf("."); // 확장자 위치

		String tempName = filename.substring(0, index);
		String tempExt = filename.substring(index); 

		while (true) {

			File file = new File(path + "\\" + filename);

			if (file.exists()) {
				// 있다. > 중복 > 파일명 변경

				filename = String.format("%s(%d)%s", tempName, n, tempExt);
				n++;

			} else {
				// 없다. > 사용 가능한 파일명
				return filename;
			}
		}

	} // getFileName

	//게시물 수정하기
	@GetMapping("/member/adoptreviewedit")
	public String adoptrevieweddit(String arseq, Model model) {

		AdoptReviewDTO ardto = service.editadr(arseq);
		int arseqs = Integer.parseInt(arseq);
		int arviews = Integer.parseInt(ardto.getArview());

		model.addAttribute("ardto", ardto);
		model.addAttribute("arseqs", arseqs);
		model.addAttribute("arviews", arviews);


		return "member/adoptreviewedit";
	}

	@PostMapping("/member/adoptrevieweditok")
	public String adoptrevieweditok(HttpServletRequest req, HttpSession session) {

		AdoptReviewDTO ardto = new AdoptReviewDTO();

		//MultipartRequest와 동일
		MultipartHttpServletRequest multi = (MultipartHttpServletRequest)req;

		//작성되는 게시물의 게시물 번호 알아오기 > detail 페이지 이동을 위해서
		String arseq = multi.getParameter("arseq");
		String arviews = multi.getParameter("arview");
		String artitle = multi.getParameter("artitle");
		String arcontent = multi.getParameter("arcontent");
		String mseq = (String)session.getAttribute("sseq");

		String arview = (Integer.parseInt(arviews) - 1) + "";


		MultipartFile attach = multi.getFile("arfile");


		String filename = attach.getOriginalFilename();
		//업로드한 파일이 저장될 경로
		//String rpath = req.getRealPath("/files");
		String path = "C:\\class\\code\\springboot\\petwogether\\petwogether\\src\\main\\resources\\static\\files";

		if(filename != null && filename !="" && filename.length() != 0) {
			//파일명 중복 방지 > 파일명 확인하는 method 인 getFileName 생성
			filename = getFileName(path, filename);				
		}

		//파일 이동
		File file2 = new File(path + "\\" + filename);

		ardto.setArfile(filename);

		try {

			attach.transferTo(file2);

		} catch (Exception e) {
		}

		//(arseq, mseq, artitle, arcontent, arregdate, arview, arfile)
		ardto.setArtitle(artitle);
		ardto.setArcontent(arcontent);
		ardto.setMseq(mseq);
		ardto.setArseq(arseq);		
		ardto.setArseq(arview);		

		int vresult = service.reducearview(arseq);
		int result = service.updateadr(ardto);	


		if(result == 1){ 

			return "redirect:/member/adoptreviewdetail?arseq=" + arseq;


		} else { 

			return "redirect:/member/adoptreview";

		}


	} //adoptrevieweditok


	//게시물 삭제하기
	@GetMapping("/member/adoptreviewdelok")
	public String adoptreviewdelok(String arseq) {

		int result = service.adrdel(arseq);


		if(result == 1){ 

			return "redirect:/member/adoptreview";

		} else { 

			return "redirect:/member/adoptreviewdetail?arseq=" + arseq;

		}


	}//adoptreviewdelok




	//지현 END  =========

	// 동균 START 
	@ResponseBody
	@GetMapping("/member/qnalist")
	public ModelAndView qnaList(Model model, String page) {

		QuestionDTO qdto = new QuestionDTO();

		//페이징 처리

		System.out.println(page);
		//한 페이지 당 출력할 게시물 수
		int pageSize = 10;

		//총 게시물 수

		int totalCount = service.getTotalQuestion();

		//총 페이지 수
		int totalPage = (int)(Math.ceil((double)totalCount/pageSize));

		//페이지바 태그
		String pagebar = "";

		//한번에 보여지는 페이지 수
		int blockSize = 10;

		int nowPage = 0;      //현재 페이지 번호(= page)
		int begin = 0;         //rnum 시작 위치
		int end = 0;         //rnum 끝 위치      

		if (page == null || page == "") nowPage = 1;
		else nowPage = Integer.parseInt(page);

		begin = ((nowPage - 1) * pageSize) + 1;
		end = begin + pageSize - 1; 

		//출력될 페이지 번호
		int n = 0;

		//루프 변수
		int loop = 0;

		loop = 1;
		n = ((nowPage -1)/blockSize) * blockSize + 1;

		if(n == 1) {
			pagebar += String.format(" <a href='/member/qnalist?page=1'> [이전 10페이지]&nbsp;&nbsp;&nbsp;</a> ");
		} else {
			pagebar += String.format(" <a href='/member/qnalist?page=%d'> [이전 10페이지]&nbsp;&nbsp;&nbsp;</a> ", n-1);         
		}

		while (!(loop > blockSize || n > totalPage)) {

			if (nowPage == n) {
				pagebar += String.format(" <a href='#!' style='color:#F8A1A4;'>%d</a> ", n);
			} else {
				pagebar += String.format(" <a href='/member/qnalist?page=%d'>%d</a> ", n, n);
			}

			loop++;
			n++;

		}

		if (n > totalPage) {
			pagebar += String.format(" <a href='/member/qnalist?page=%d'>&nbsp;&nbsp;&nbsp;[다음 10페이지]</a> ", totalPage);         
		} else {         
			pagebar += String.format("  <a href='/member/qnalist?page=%d'>&nbsp;&nbsp;&nbsp;[다음 10페이지]</a> ", n);
		}

		HashMap<String, Integer> map = new HashMap<String, Integer>();

		//rownum에 맞춰서 데이터를 잘라와야함
		map.put("begin", begin);
		map.put("end", end);


		//페이징 처리 끝

		List<QuestionDTO> qList= service.getQList(map); //adrlist

		//regdate 처리
		for(int i=0; i<qList.size(); i++) {

			qList.get(i).getQregdate();

			String regdate = (qList.get(i).getQregdate()).substring(0, 10);

			qList.get(i).setQregdate(regdate);
		}


		//RESTful로 데이터를 얻어와서 html로 연결
		ModelAndView modelAndView = new ModelAndView();

		modelAndView.addObject("qList", qList);
		modelAndView.addObject("pagebar", pagebar);
		modelAndView.setViewName("member/qnalist");

		//System.out.println(adrlist.toString());


		return modelAndView;
	}

	@ResponseBody 
	@GetMapping("/member/qnasearch")
	public List<QuestionDTO> qnaSList(String word){

		List<QuestionDTO> qnaSList = service.qnaSearch(word);

		for (QuestionDTO dto : qnaSList) {

			dto.setQregdate(dto.getQregdate().substring(0,10));

		}


		return qnaSList;

	}//adoptreviewsearch

	@GetMapping("/member/qnadetail")
	public String qnadetail(String qseq, Model model) {      

		QuestionDTO qdto = service.getQuestion(qseq);

		AnswerDTO adto = service.getAnswer(qseq);

		int qseqI = Integer.parseInt(qseq);

		model.addAttribute("qdto", qdto);

		model.addAttribute("adto", adto);

		model.addAttribute("qseqI", qseqI);

		return "member/qnadetail";
	}


	@GetMapping("/member/questionadd")
	public String questionadd() {

		return "member/questionadd";
	}


	@PostMapping("/member/questionaddok")
	public String questionaddok(HttpServletRequest req, HttpSession session) {

		QuestionDTO qdto = new QuestionDTO();

		//MultipartRequest와 동일
		MultipartHttpServletRequest multi = (MultipartHttpServletRequest)req;

		//작성되는 게시물의 게시물 번호 알아오기 > detail 페이지 이동을 위해서
		//int maxarseq = (service.getmaxarseq() + 1);
		//int maxqseq = (service.getmaxqseq() + 1);

		String qtitle = multi.getParameter("qtitle");
		String qcontent = multi.getParameter("qcontent");
		String mseq = (String)session.getAttribute("sseq");

		MultipartFile attach = multi.getFile("qfile");

		String filename = attach.getOriginalFilename();
		//업로드한 파일이 저장될 경로
		String path = req.getRealPath("/files");

		if(filename != null && filename !="" && filename.length() != 0) {
			//파일명 중복 방지 > 파일명 확인하는 method 인 getFileName 생성
			filename = getFileName(path, filename);

		} else {
			filename ="null";
		}

		//파일 이동
		File file2 = new File(path + "\\" + filename);

		qdto.setQfile(filename);

		try {

			attach.transferTo(file2);

		} catch (Exception e) {
		}


		//(arseq, mseq, artitle, arcontent, arregdate, arview, arfile)
		qdto.setQtitle(qtitle);
		qdto.setQcontent(qcontent);
		qdto.setMseq(mseq);


		int result = service.questionAdd(qdto);   


		if(result == 1){ 

			return "redirect:/member/qnalist";

		} else { 

			return "redirect:/member/questionadd";

		}


	}

	@PostMapping("/member/answeraddok")
	public String answeraddok(AnswerDTO dto) {

		int result = service.answerAdd(dto);   

		if(result == 1){ 

			return "redirect:/member/qnadetail?qseq=" + dto.getQseq();

		} else { 

			return "redirect:/member/qnalist";

		}
	}

	@GetMapping("/member/qnadelok")
	public String qnadelok(String qseq) {

		int result2 = service.answerDel(qseq);

		int result = service.qnaDel(qseq);


		if(result == 1){ 

			return "redirect:/member/qnalist";

		} else { 

			return "redirect:/member/qnadetail?qseq=" + qseq;

		}

	}

	@GetMapping("/member/questionedit")
	public String questionedit(Model model,String qseq) {

		QuestionDTO qdto = service.getQuestion(qseq);

		model.addAttribute("qseq",qseq);
		model.addAttribute("qdto",qdto);



		return "member/questionedit";
	}


	@PostMapping("/member/questioneditok")
	public String questioneditok(HttpServletRequest req, HttpSession session) {

		QuestionDTO qdto = new QuestionDTO();

		//MultipartRequest와 동일
		MultipartHttpServletRequest multi = (MultipartHttpServletRequest)req;

		//작성되는 게시물의 게시물 번호 알아오기 > detail 페이지 이동을 위해서
		//int maxarseq = (service.getmaxarseq() + 1);
		//int maxqseq = (service.getmaxqseq() + 1);

		String qtitle = multi.getParameter("qtitle");
		String qcontent = multi.getParameter("qcontent");
		String mseq = (String)session.getAttribute("sseq");

		String qseq = multi.getParameter("qseq");

		MultipartFile attach = multi.getFile("qfile");

		String filename = attach.getOriginalFilename();
		//업로드한 파일이 저장될 경로
		String path = req.getRealPath("/static/files");

		if(filename != null && filename !="" && filename.length() != 0) {
			//파일명 중복 방지 > 파일명 확인하는 method 인 getFileName 생성
			filename = getFileName(path, filename);

		} else {
			filename ="null";
		}

		//파일 이동
		File file2 = new File(path + "\\" + filename);

		qdto.setQfile(filename);

		try {

			attach.transferTo(file2);

		} catch (Exception e) {
		}


		//(arseq, mseq, artitle, arcontent, arregdate, arview, arfile)
		qdto.setQtitle(qtitle);
		qdto.setQcontent(qcontent);
		qdto.setMseq(mseq);
		qdto.setQseq(qseq);

		int result = service.questionEdit(qdto);   


		if(result == 1){ 

			return "redirect:/member/qnadetail?qseq=" + qseq;

		} else { 

			return "redirect:/member/questionedit";

		}

	}


	// 동균 END

	// 설화 시작
	//설 1.27
	@GetMapping("/member/adoptlist")
	public String adoptlist(Model model, String page, HttpServletRequest req) {

		String type = req.getParameter("type");

		//페이징 처리

		System.out.println(page);
		//한 페이지 당 출력할 게시물 수
		int pageSize = 9;

		//총 게시물 수
		int totalCount = service.getTotallist(type); //타입에 따라 달라짐

		//총 페이지 수
		int totalPage = (int)(Math.ceil((double)totalCount/pageSize));

		//페이지바 태그
		String pagebar = "";

		//한번에 보여지는 페이지 수
		int blockSize = 10;

		int nowPage = 0;      //현재 페이지 번호(= page)
		int begin = 0;         //rnum 시작 위치
		int end = 0;         //rnum 끝 위치      

		if (page == null || page == "") nowPage = 1;
		else nowPage = Integer.parseInt(page);

		begin = ((nowPage - 1) * pageSize) + 1;
		end = begin + pageSize - 1; 

		//출력될 페이지 번호
		int n = 0;

		//루프 변수
		int loop = 0;

		loop = 1;
		n = ((nowPage -1)/blockSize) * blockSize + 1;

		if (type == null) {

			if(n == 1) {
				pagebar += String.format(" <a href='/member/adoptlist?page=1'> [이전 10페이지]&nbsp;&nbsp;&nbsp;</a> ");
			} else {
				pagebar += String.format(" <a href='/member/adoptlist?page=%d'> [이전 10페이지]&nbsp;&nbsp;&nbsp;</a> ", n-1);         
			}

			while (!(loop > blockSize || n > totalPage)) {

				if (nowPage == n) {
					pagebar += String.format(" <a href='#!' style='color:#F8A1A4;'>%d</a> ", n);
				} else {
					pagebar += String.format(" <a href='/member/adoptlist?page=%d'>%d</a> ", n, n);
				}

				loop++;
				n++;

			}

			if (n > totalPage) {
				pagebar += String.format(" <a href='/member/adoptlist?page=%d'>&nbsp;&nbsp;&nbsp;[다음 10페이지]</a> ", totalPage);         
			} else {         
				pagebar += String.format("  <a href='/member/adoptlist?page=%d'>&nbsp;&nbsp;&nbsp;[다음 10페이지]</a> ", n);
			}

		} else {

			if(n == 1) {
				pagebar += String.format(" <a href='/member/adoptlist?type=%s&page=1'> [이전 10페이지]&nbsp;&nbsp;&nbsp;</a> ", type);
			} else {
				pagebar += String.format(" <a href='/member/adoptlist?type=%s&page=%d'> [이전 10페이지]&nbsp;&nbsp;&nbsp;</a> ", type, n-1);         
			}

			while (!(loop > blockSize || n > totalPage)) {

				if (nowPage == n) {
					pagebar += String.format(" <a href='#!' style='color:#F8A1A4;'>%d</a> ", n);
				} else {
					pagebar += String.format(" <a href='/member/adoptlist?type=%s&page=%d'>%d</a> ", type, n, n);
				}

				loop++;
				n++;

			}

			if (n > totalPage) {
				pagebar += String.format(" <a href='/member/adoptlist?type=%s&page=%d'>&nbsp;&nbsp;&nbsp;[다음 10페이지]</a> ", type, totalPage);         
			} else {         
				pagebar += String.format("  <a href='/member/adoptlist?type=%s&page=%d'>&nbsp;&nbsp;&nbsp;[다음 10페이지]</a> ", type, n);
			}

		}


		HashMap<String, Integer> map = new HashMap<String, Integer>();

		//rownum에 맞춰서 데이터를 잘라와야함
		map.put("begin", begin);
		map.put("end", end);


		//페이징 처리 끝

		List<AdoptListDTO> list = service.adoptlist(map, type);

		model.addAttribute("list", list);
		model.addAttribute("type", type);
		model.addAttribute("pagebar", pagebar);

		return "member/adoptlist";
	}

	//설 1.27
	@GetMapping("/member/adoptlistview")
	public String adoptlistview(Model model, String page, HttpServletRequest req) {

		String aseq = req.getParameter("aseq");
		System.out.println(aseq);
		AdoptListDTO dto = service.adoptlistview(aseq);

		System.out.println(dto);

		model.addAttribute("dto", dto);

		return "member/adoptlistview";
	}

	// 설화 끝

	//유찬 시작 =========

	//유기동물찾기 목록
	@GetMapping("/member/stray")
	public String Stray(Model model) {

		List<StrayDTO> slist = service.straylist();

		model.addAttribute("slist", slist);

		return "member/stray";
	}

	@GetMapping("/member/addstray")
	public String AddStray() {


		return "member/addstray";
	}

	@GetMapping("/member/addstrayokay")
	public String AddStrayokay() {


		return "member/addstrayokay";
	}

	@PostMapping("/member/addstrayok")
	public String AddStrayok(HttpServletRequest req, HttpSession session) {

		StrayDTO dto = new StrayDTO();

		//파일
		//파일전용 req 
		MultipartHttpServletRequest multi = (MultipartHttpServletRequest)req;

		String mseq = (String)session.getAttribute("sseq");
		String stitle = multi.getParameter("stitle");
		String scontent = multi.getParameter("scontent");
		String slocal = multi.getParameter("slocal");
		String stel = multi.getParameter("stel");

		dto.setMseq(mseq);
		dto.setStitle(stitle);
		dto.setScontent(scontent);
		dto.setSlocal(slocal);
		dto.setStel(stel);

		//첨부 파일용
		MultipartFile sfile =  multi.getFile("sfile");

		// 파일 업로드 완료 > 파일이 어디 있는지 ?? > 임시 폴더에 저장 > 우리가 원하는 폴더로 이동
		String filename = sfile.getOriginalFilename();
		String path = req.getRealPath("C:\\class\\code\\springboot\\petwogether\\petwogether\\src\\main\\resources\\static\\files");

		if(filename != null && filename !="" && filename.length() != 0) {
			//파일명 중복 방지 > 파일명 확인하는 method 인 getFileName 생성
			filename = getFileName(path, filename);            
		}

		// 파일 이동
		File file3 = new File(path + "\\" + filename);

		dto.setSfile(filename);

		try {

			//attach.renameTo(file2) 자바에서 했던 방식 
			sfile.transferTo(file3); 

		} catch (Exception e) {
			// TODO: handle exception
		}



		int result = service.addStray(dto); //stray 등록


		if(result == 1){ 

			return "redirect:/member/addstrayokay";

		} else { 

			return "redirect:/member/stray";

		}

	}

	//유기동물 삭제하기
	@GetMapping("/member/straydelok")
	public String straydelok(String sseq) {

		//System.out.println(sseq);


		int result = service.delStray(sseq);


		if(result == 1){ 

			return "redirect:/member/stray";

		} else { 

			return "redirect:/member/stray";

		}


	}

	//유찬 끝 ========

	// 영우 시작




	   
	   @ResponseBody
	   @GetMapping("/member/healthinfo")
	   public ModelAndView hilist(Model model, String page) {
	      
	      HealthDTO hdto = new HealthDTO();

	      
	      System.out.println(page);
	      int pageSize = 10;
	      
	      int totalCount = service.getTotalHInfo();
	      
	      int totalPage = (int)(Math.ceil((double)totalCount/pageSize));
	      
	      String pagebar = "";
	      
	      int blockSize = 10;
	      
	      int nowPage = 0;      //현재 페이지 번호(= page)
	      int begin = 0;         //rnum 시작 위치
	      int end = 0;         //rnum 끝 위치      
	      
	      if (page == null || page == "") nowPage = 1;
	      else nowPage = Integer.parseInt(page);
	      
	      begin = ((nowPage - 1) * pageSize) + 1;
	      end = begin + pageSize - 1; 
	      
	      int n = 0;
	      
	      int loop = 0;
	      
	      loop = 1;
	      n = ((nowPage -1)/blockSize) * blockSize + 1;
	      
	      if(n == 1) {
	         pagebar += String.format(" <a href='/member/healthinfo?page=1'> [이전 10페이지]&nbsp;&nbsp;&nbsp;</a> ");
	      } else {
	         pagebar += String.format(" <a href='/member/healthinfo?page=%d'> [이전 10페이지]&nbsp;&nbsp;&nbsp;</a> ", n-1);         
	      }
	      
	      while (!(loop > blockSize || n > totalPage)) {
	         
	         if (nowPage == n) {
	            pagebar += String.format(" <a href='#!' style='color:#F8A1A4;'>%d</a> ", n);
	         } else {
	            pagebar += String.format(" <a href='/member/healthinfo?page=%d'>%d</a> ", n, n);
	         }
	         
	         loop++;
	         n++;
	         
	      }
	      
	      if (n > totalPage) {
	         pagebar += String.format(" <a href='/member/healthinfo?page=%d'>&nbsp;&nbsp;&nbsp;[다음 10페이지]</a> ", totalPage);         
	      } else {         
	         pagebar += String.format("  <a href='/member/healthinfo?page=%d'>&nbsp;&nbsp;&nbsp;[다음 10페이지]</a> ", n);
	      }
	      
	      HashMap<String, Integer> map = new HashMap<String, Integer>();
	      
	      map.put("begin", begin);
	      map.put("end", end);


	      
	      List<HealthDTO> hilist = service.hilist(map);

	      for(int i=0; i<hilist.size(); i++) {
	         
	         hilist.get(i).getHregdate();
	         
	         String regdate = (hilist.get(i).getHregdate()).substring(0, 10);
	         
	         hilist.get(i).setHregdate(regdate);
	      }
	      
	       ModelAndView modelAndView = new ModelAndView();
	       
	       modelAndView.addObject("hilist", hilist);
	       modelAndView.addObject("pagebar", pagebar);
	       modelAndView.setViewName("member/healthinfo");
	       
	           

	           return modelAndView;
	   }
	   
	   @ResponseBody 
	   @GetMapping("/member/healthinfosearch")
	   public List<HealthDTO> hlist(String word){
	            
	      List<HealthDTO> hilist = service.hisearch(word);
	      
	      for (HealthDTO dto : hilist) {

	         dto.setHregdate(dto.getHregdate().substring(0,10));

	      }
	      
	      System.out.println();
	      System.out.println(hilist);

	      return hilist;
	      
	   }
	   
	   
	   @GetMapping("/member/healthinfodetail")
	   public String hidetail(String hseq, Model model, HttpSession session, String hviews) {      
	      
	      HealthDTO hdto = service.gethidetail(hseq);
	      int hseqs = Integer.parseInt(hseq);
	      int hview = 0;
	      
	      if(hviews == null || hviews.equals(0) || hviews =="") {
	         hview = Integer.parseInt(hdto.getHview());
	         hview++;
	      } else {
	         hview = Integer.parseInt(hviews);
	      }
	      
	      
	      int result = service.updatehview(hseq);
	      
	      model.addAttribute("hview", hview);
	      model.addAttribute("hdto", hdto);
	      model.addAttribute("hseqs", hseqs);
	      
	      return "/member/healthinfodetail";
	   }
	   
	   
	   
	   





	// 영우 끝





}
