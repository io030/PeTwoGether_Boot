package com.test.petwogether.model.member;

import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import com.test.petwogether.model.AdoptListDTO;
import com.test.petwogether.model.admin.AnswerDTO;
import com.test.petwogether.model.admin.HealthDTO;

@Repository
@Primary
public class MemberDAOImpl implements MemberDAO {

	@Autowired
	private SqlSessionTemplate sql;

	@Override
	public MemberDTO mLoginCheck(MemberDTO dto) {

		return sql.selectOne("member.auth", dto);
	}

	// 지현 START ======

	@Override
	public List<AdoptReviewDTO> adrlist(HashMap<String, Integer> map) {

		return sql.selectList("member.adrlist", map);
	}

	@Override
	public AdoptReviewDTO getadrdetail(String arseq) {

		return sql.selectOne("member.getadrdetail", arseq);
	}

	@Override
	public int getmaxarseq() {

		return sql.selectOne("member.getmaxarseq");
	}

	@Override
	public int adradd(AdoptReviewDTO ardto) {

		return sql.insert("member.adradd", ardto);
	}

	@Override
	public AdoptReviewDTO editadr(String arseq) {

		return sql.selectOne("member.editadr", arseq);
	}

	@Override
	public int updateadr(AdoptReviewDTO ardto) {

		return sql.update("member.updateadr", ardto);
	}

	@Override
	public int deladr(String arseq) {

		return sql.delete("member.deladr", arseq);
	}

	@Override
	public int updatearview(String arseq) {

		return sql.update("member.updatearview", arseq);
	}

	@Override
	public int reducearview(String arseq) {

		return sql.update("member.reducearview", arseq);
	}

	@Override
	public List<AdoptReviewDTO> adrsearch(String word) {

		return sql.selectList("member.adrsearch", word);
	}

	@Override
	public int gettotalareview() {

		return sql.selectOne("member.gettotalareview");
	}

	// 지현 END ======

	// 동균 START

	@Override
	public int getTotalQuestion() {
		System.out.println("getTotalQuestion");
		// return sql.selectOne("member.getTotalQuestion");
		return sql.selectOne("member.gettotalareview");
	}

	@Override
	public List<QuestionDTO> getQList(HashMap<String, Integer> map) {

		return sql.selectList("member.getQList", map);
	}

	@Override
	public List<QuestionDTO> qnaSearch(String word) {

		return sql.selectList("member.qnaSearch", word);
	}

	@Override
	public AnswerDTO getAnswer(String qseq) {

		return sql.selectOne("member.getAnswer", qseq);
	}

	@Override
	public QuestionDTO getQuestion(String qseq) {

		return sql.selectOne("member.getQuestion", qseq);
	}

	@Override
	public int questionAdd(QuestionDTO qdto) {

		return sql.insert("member.questionAdd", qdto);
	}

	@Override
	public int answerAdd(AnswerDTO dto) {

		return sql.insert("member.answerAdd", dto);
	}

	@Override
	public int qnaDel(String qseq) {

		return sql.delete("member.qnaDel", qseq);
	}

	@Override
	public int answerDel(String qseq) {

		return sql.delete("member.answerDel", qseq);
	}

	@Override
	public int questionEdit(QuestionDTO qdto) {

		return sql.update("member.questionEdit", qdto);
	}
	// 동균 END

	// 설화 시작

	//설
	@Override
	public List<AdoptListDTO> adoptlist(HashMap<String, Integer> map) {

		return sql.selectList("member.adoptlist", map);
	}

	//설
	@Override
	public List<AdoptListDTO> adoptlistdog(HashMap<String, Integer> map) {

		return sql.selectList("member.adoptlistdog", map);
	}

	//설
	@Override
	public List<AdoptListDTO> adoptlistcat(HashMap<String, Integer> map) {

		return sql.selectList("member.adoptlistcat", map);
	}

	//설
	@Override
	public List<AdoptListDTO> adoptlistother(HashMap<String, Integer> map) {

		return sql.selectList("member.adoptlistother", map);
	}

	//설
	@Override
	public AdoptListDTO adoptlistview(String aseq) {

		return sql.selectOne("member.adoptlistview", aseq);
	}

	//설
	@Override
	public int gettotalist(String type) {

		if (type == null) {
			return sql.selectOne("member.gettotallist");
		} else {
			return sql.selectOne("member.gettotallist2", type);
		}

	}

	// 설화 끝

	//유찬 시작 ======

	@Override
	public List<StrayDTO> straylist() {

		return sql.selectList("member.straylist");
	}

	@Override
	public int addstray(StrayDTO dto) {

		return sql.insert("member.addstray", dto);
	}

	@Override
	public int delStray(String sseq) {

		return sql.delete("member.delstray", sseq);
	}

	//유찬 끝 ======

	//영우 시작

	   @Override
	   public List<HealthDTO> hilist(HashMap<String, Integer> map) {

	      return sql.selectList("member.hilist", map);
	   }

	   @Override
	   public int gettotalhinfo() {

	      return sql.selectOne("member.gettotalhinfo");
	   }

	   @Override
	   public List<HealthDTO> hisearch(String word) {

	      return sql.selectList("member.hisearch", word);
	   }

	   @Override
	   public HealthDTO gethidetail(String hseq) {

	      return sql.selectOne("member.gethidetail", hseq);
	   }

	   @Override
	   public int updatehview(String hseq) {

	      return sql.update("member.updatehview", hseq);
	   }

	//영우 끝

}
