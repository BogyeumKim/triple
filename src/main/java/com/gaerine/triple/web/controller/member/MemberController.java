package com.gaerine.triple.web.controller.member;

import com.gaerine.triple.domain.member.Member;
import com.gaerine.triple.service.member.MemberService;
import com.gaerine.triple.web.SessionConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@Slf4j
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService service;

    // 멤버 조회
    @GetMapping("/{id}")
    public ResponseEntity<Member> getMember(@PathVariable("id") Long id,@SessionAttribute(name = SessionConst.LOGIN_MEMBER,required = false) Optional<Member> loginMember){
        log.info("getMember ={}",loginMember);

        Member member = null;
        if(loginMember.isPresent() && loginMember.get().getMember_id().equals(id)){
            member =service.readMember(id);
        }
        return member != null ? ResponseEntity.status(HttpStatus.OK).body(member) : ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    // 멤버 가입
    @PostMapping("/")
    public ResponseEntity<String> signUp(@RequestBody Member member){
        Member newMember = service.register(member);
        log.info("newMember={}",newMember);
        return newMember.getMember_id() != null ? ResponseEntity.status(HttpStatus.CREATED).build() : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    // 멤버 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMember(@PathVariable("id") Long id,@SessionAttribute(name = SessionConst.LOGIN_MEMBER,required = false) Optional<Member> loginMember){
        int result = 0;
        if(loginMember.isPresent() && loginMember.get().getMember_id().equals(id)){
            result = service.removeMember(id);
        }
        return result == 1 ? ResponseEntity.status(HttpStatus.OK).body("삭제 완료") : ResponseEntity.status(HttpStatus.FORBIDDEN).body("접근 불가");
    }


}
