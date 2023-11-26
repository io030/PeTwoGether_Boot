package com.test.petwogether.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.test.petwogether.model.AdoptListDTO;
import com.test.petwogether.model.admin.AdminDAO;
import com.test.petwogether.model.admin.AdminDTO;
import com.test.petwogether.model.admin.AnswerDTO;
import com.test.petwogether.model.admin.HealthDTO;
import com.test.petwogether.model.member.AdoptReviewDTO;
import com.test.petwogether.model.member.MemberDAO;
import com.test.petwogether.model.member.MemberDTO;
import com.test.petwogether.model.member.QuestionDTO;
import com.test.petwogether.model.member.StrayDTO;
import com.test.petwogether.model.petsitter.PetSitterDAO;
import com.test.petwogether.model.petsitter.PetSitterDTO;

@Service
@Primary
public class MemberServiceImpl implements MemberService {

	@Autowired
	private MemberDAO mdao;

	@Autowired
	private PetSitterDAO pdao;

	@Autowired
	private AdminDAO ddao;

	@Override
	public MemberDTO mLoginCheck(String id, String pass) {

		MemberDTO dto = new MemberDTO();
		dto.setMid(id);
		dto.setMpw(pass);

		return mdao.mLoginCheck(dto);

	}

	@Override
	public PetSitterDTO fLoginCheck(String id, String pass) {

		PetSitterDTO dto = new PetSitterDTO();
		dto.setPsid(id);
		dto.setPspw(pass);

		return pdao.pLoginCheck(dto);
	}

	@Override
	public AdminDTO aLoginCheck(String id, String pass) {

		AdminDTO dto = new AdminDTO();
		dto.setAdid(id);
		dto.setAdpw(pass);

		return ddao.aLoginCheck(dto);
	}

	//지현START=======

	@Override
	public List<AdoptReviewDTO> adrlist(HashMap<String, Integer> map) {

		return mdao.adrlist(map);
	}

	@Override
	public AdoptReviewDTO getadrdetail(String arseq) {

		return mdao.getadrdetail(arseq);
	}

	@Override
	public int getmaxarseq() {

		return mdao.getmaxarseq();
	}

	@Override
	public int adradd(AdoptReviewDTO ardto) {

		return mdao.adradd(ardto);
	}

	@Override
	public AdoptReviewDTO editadr(String arseq) {

		return mdao.editadr(arseq);
	}


	@Override
	public int updateadr(AdoptReviewDTO ardto) {

		return mdao.updateadr(ardto);
	}

	@Override
	public int adrdel(String arseq) {

		return mdao.deladr(arseq);
	}

	@Override
	public int updatearview(String arseq) {

		return mdao.updatearview(arseq);
	}

	@Override
	public int reducearview(String arseq) {

		return mdao.reducearview(arseq);
	}

	@Override
	public List<AdoptReviewDTO> adrsearch(String word) {

		return mdao.adrsearch(word);
	}

	@Override
	public int getTotalAReview() {

		return mdao.gettotalareview();
	}

	//지현END =======

	// 동균 START

	@Override
	public int getTotalQuestion() {

		return mdao.getTotalQuestion();
	}

	@Override
	public List<QuestionDTO> getQList(HashMap<String, Integer> map) {

		return mdao.getQList(map);
	}

	@Override
	public List<QuestionDTO> qnaSearch(String word) {

		return mdao.qnaSearch(word);
	}

	@Override
	public AnswerDTO getAnswer(String qseq) {

		return mdao.getAnswer(qseq);
	}

	@Override
	public QuestionDTO getQuestion(String qseq) {

		return mdao.getQuestion(qseq);
	}

	@Override
	public int questionAdd(QuestionDTO qdto) {

		return mdao.questionAdd(qdto);
	}

	@Override
	public int answerAdd(AnswerDTO dto) {

		return mdao.answerAdd(dto);
	}

	@Override
	public int qnaDel(String qseq) {

		return mdao.qnaDel(qseq);
	}

	@Override
	public int answerDel(String qseq) {

		return mdao.answerDel(qseq);
	}

	@Override
	public int questionEdit(QuestionDTO qdto) {

		return mdao.questionEdit(qdto);
	}
	// 동균 END

	// 설화 시작

	//설
	@Override
	public List<AdoptListDTO> adoptlist(HashMap<String, Integer> map, String type) {

		if (type == null) {
			return mdao.adoptlist(map);
		} else if (type.equals("강아지")) {
			return mdao.adoptlistdog(map);
		} else if (type.equals("고양이")) {
			return mdao.adoptlistcat(map);
		} else {
			return mdao.adoptlistother(map);
		}
	}

	//설
	@Override
	public AdoptListDTO adoptlistview(String aseq) {

		return mdao.adoptlistview(aseq);
	}

	//설
	@Override
	public int getTotallist(String type) {

		return mdao.gettotalist(type);
	}

	// 설화 끝

	//유찬 시작 =========

	@Override
	public List<StrayDTO> straylist() {

		return mdao.straylist();
	}

	@Override
	public int addStray(StrayDTO dto) {

		return mdao.addstray(dto);
	}

	@Override
	public int delStray(String sseq) {

		return mdao.delStray(sseq);
	}

	//유찬 끝 =========

	// 영우 시작



	   @Override
	   public int getTotalHInfo() {

	      return mdao.gettotalhinfo();
	   }

	   @Override
	   public List<HealthDTO> hilist(HashMap<String, Integer> map) {

	      return mdao.hilist(map);
	   }

	   @Override
	   public List<HealthDTO> hisearch(String word) {

	      return mdao.hisearch(word);
	   }

	   @Override
	   public HealthDTO gethidetail(String hseq) {

	      return mdao.gethidetail(hseq);
	   }

	   @Override
	   public int updatehview(String hseq) {

	      return mdao.updatehview(hseq);
	   }

	// 영우 끝
}
