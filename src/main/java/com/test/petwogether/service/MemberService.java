package com.test.petwogether.service;

import java.util.HashMap;
import java.util.List;

import com.test.petwogether.model.AdoptListDTO;
import com.test.petwogether.model.admin.AdminDTO;
import com.test.petwogether.model.admin.AnswerDTO;
import com.test.petwogether.model.admin.HealthDTO;
import com.test.petwogether.model.member.AdoptReviewDTO;
import com.test.petwogether.model.member.MemberDTO;
import com.test.petwogether.model.member.QuestionDTO;
import com.test.petwogether.model.member.StrayDTO;
import com.test.petwogether.model.petsitter.PetSitterDTO;

public interface MemberService {


	MemberDTO mLoginCheck(String id, String pass);

	PetSitterDTO fLoginCheck(String id, String pass);

	AdminDTO aLoginCheck(String id, String pass);



	//지현 START ============

	List<AdoptReviewDTO> adrlist(HashMap<String, Integer> map);

	AdoptReviewDTO getadrdetail(String arseq);

	int getmaxarseq();

	int adradd(AdoptReviewDTO ardto);

	AdoptReviewDTO editadr(String arseq);

	int updateadr(AdoptReviewDTO ardto);

	int adrdel(String arseq);

	int updatearview(String arseq);

	int reducearview(String arseq);

	List<AdoptReviewDTO> adrsearch(String word);

	int getTotalAReview();

	//지현 END ============

	// 동균 START

	int getTotalQuestion();

	List<QuestionDTO> getQList(HashMap<String, Integer> map);

	List<QuestionDTO> qnaSearch(String word);

	QuestionDTO getQuestion(String qseq);

	AnswerDTO getAnswer(String qseq);

	int questionAdd(QuestionDTO qdto);

	int answerAdd(AnswerDTO dto);

	int qnaDel(String qseq);

	int answerDel(String qseq);

	int questionEdit(QuestionDTO qdto);


	// 동균 END

	// 설화 시작

	//설
	List<AdoptListDTO> adoptlist(HashMap<String, Integer> map, String type);

	//설
	AdoptListDTO adoptlistview(String aseq);

	//설
	int getTotallist(String type);

	// 설화 끝

	//유찬 부분 시작=============

	List<StrayDTO> straylist();

	int addStray(StrayDTO dto);

	int delStray(String sseq);

	//유찬 부분 끝===============

	// 영우 시작

	   int getTotalHInfo();

	   List<HealthDTO> hilist(HashMap<String, Integer> map);

	   List<HealthDTO> hisearch(String word);

	   HealthDTO gethidetail(String hseq);

	   int updatehview(String hseq);

	// 영우 끝


}
