package com.gaerine.triple.service.member;

import com.gaerine.triple.domain.login.LoginVO;
import com.gaerine.triple.domain.member.Member;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public interface MemberService {
    Member register(Member mb);

    Member socialRegister(Map<String, String> snsData);

    Optional<String> findById(String userId);

    Optional<String> findBySocialId(String userId);

    Optional<Member> login(LoginVO vo);

    Optional<Member> socialLogin(String socialId);

    Member readMember(Long id);

    int removeMember(Long id);
}
