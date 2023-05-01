package com.gaerine.triple.service.member;

import com.gaerine.triple.domain.member.Member;
import com.gaerine.triple.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberServiceImpl implements MemberService{

    private final UserMapper mapper;

    @Override
    public Optional<Member> login(String userId, String userPw) {
        Optional<Member> memberInfo = Optional.ofNullable(mapper.getMemberInfo(userId, userPw));
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
