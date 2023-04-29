package com.gaerine.triple.service.member;

import com.gaerine.triple.domain.member.Member;
import com.gaerine.triple.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberServiceImpl implements MemberService{

    private final UserMapper mapper;

    @Override
    public Member findById(Long mbId) {
        return mapper.getMember(mbId);
    }

    @Override
    public Member register(Member mb) {
        mapper.save(mb);
        return mb;
    }
}
