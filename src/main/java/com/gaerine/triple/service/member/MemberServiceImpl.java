package com.gaerine.triple.service.member;

import com.gaerine.triple.domain.login.LoginVO;
import com.gaerine.triple.domain.member.Member;
import com.gaerine.triple.mapper.UserMapper;
import com.gaerine.triple.web.SessionConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberServiceImpl implements MemberService{

    private final UserMapper mapper;

    @Override
    public Optional<Member> socialLogin(String socialId) {
        Optional<Member> memberInfo = Optional.ofNullable(mapper.getSocailMemberInfo(socialId));
        return memberInfo;
    }

    @Override
    public Optional<Member> login(LoginVO vo) {
        Optional<Member> memberInfo = Optional.ofNullable(mapper.getMemberInfo(vo.getUser_id(),vo.getUser_pw()));
        return memberInfo;
    }

    @Override
    public Optional<String> findById(String userId) {
        return mapper.getMemberId(userId);
    }

    @Override
    public Optional<String> findBySocialId(String socialId) {
        return mapper.getSocialId(socialId);
    }

    @Override
    public Member register(Member mb) {
        Optional<String> findId = mapper.getMemberId(mb.getUser_id());
        log.info("findId={}",findId);

        if(findId.isEmpty()){
            mapper.save(mb);
        }
        return mb;
    }

    @Override
    public Member socialRegister(Map<String, String> snsData) {

        Member member = new Member();
        member.setUser_id(snsData.get("email"));
        member.setUser_pw("");
        member.setUser_nick(snsData.get("nickname"));
        member.setSocial_id(snsData.get("id"));

        Optional<String> socialId = mapper.getSocialId(snsData.get("id"));
        log.info("socialId={}",socialId);

        if(socialId.isEmpty()){
            mapper.socialSave(member);
        }

        return member;
    }
}
