package com.test.petwogether.model.member;

import java.util.HashMap;
import java.util.List;

import com.test.petwogether.model.AdoptListDTO;
import com.test.petwogether.model.admin.AnswerDTO;
import com.test.petwogether.model.admin.HealthDTO;

public interface MemberDAO {

	MemberDTO mLoginCheck(MemberDTO dto);

	// 지현 START =======

	List<AdoptReviewDTO> adrlist(HashMap<String, Integer> map);

	AdoptReviewDTO getadrdetail(String arseq);

	int getmaxarseq();

	int adradd(AdoptReviewDTO ardto);

	AdoptReviewDTO editadr(String arseq);

	int updateadr(AdoptReviewDTO ardto);

	int deladr(String arseq);

	int updatearview(String arseq);

	int reducearview(String arseq);

	List<AdoptReviewDTO> adrsearch(String word);

	int gettotalareview();

	// 지현 END =======

	// 동균 START

	int getTotalQuestion();

	List<QuestionDTO> getQList(HashMap<String, Integer> map);

	List<QuestionDTO> qnaSearch(String word);

	AnswerDTO getAnswer(String qseq);

	QuestionDTO getQuestion(String qseq);

	int questionAdd(QuestionDTO qdto);

	int answerAdd(AnswerDTO dto);

	int qnaDel(String qseq);

	int answerDel(String qseq);	

	int questionEdit(QuestionDTO qdto);

	// 동균 END

	// 설화 시작

	//설
	List<AdoptListDTO> adoptlist(HashMap<String, Integer> map);

	//설
	List<AdoptListDTO> adoptlistdog(HashMap<String, Integer> map);

	//설
	List<AdoptListDTO> adoptlistcat(HashMap<String, Integer> map);

	//설
	List<AdoptListDTO> adoptlistother(HashMap<String, Integer> map);

	//설
	AdoptListDTO adoptlistview(String aseq);

	//설
	int gettotalist(String type);


	// 설화 끝

	//유찬 시작 =====

	List<StrayDTO> straylist();

	int addstray(StrayDTO dto);

	int delStray(String sseq);

	//유찬 끝 ====

	// 영우 시작

	  List<HealthDTO> hilist(HashMap<String, Integer> map);

	   int gettotalhinfo();

	   List<HealthDTO> hisearch(String word);

	   HealthDTO gethidetail(String hseq);

	   int updatehview(String hseq);

	// 영우 끝

}
