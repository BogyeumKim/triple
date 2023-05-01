package com.gaerine.triple.service.member;

import com.gaerine.triple.domain.member.Member;

import java.util.Map;
import java.util.Optional;

public interface MemberService {
    Member register(Member mb);

    Member socialRegister(Map<String, String> snsData);

    Optional<String> findById(String userId);

    Optional<String> findBySocialId(String userId);
}
