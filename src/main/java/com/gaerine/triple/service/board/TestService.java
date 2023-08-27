package com.gaerine.triple.service.board;

import com.gaerine.triple.domain.board.World;
import com.gaerine.triple.domain.member.Member;
import com.gaerine.triple.mapper.BoardMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TestService {

    private final BoardMapper mapper;

//    @Transactional(readOnly = true)
    public List<World> test(){
        return mapper.selectWorld();
    }


    @Transactional(readOnly = true)
    public Member testLocal(){
        return mapper.getMemberTest();
    }

    public Member testClude(){
        return mapper.getMemberTest();
    }
}
