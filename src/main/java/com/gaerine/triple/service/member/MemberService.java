package com.gaerine.triple.service.member;

import com.gaerine.triple.domain.member.Member;

public interface MemberService {
    Member register(Member mb);

    Member socialRegister(Member mb);

    Member findById(Long mbId);
}
