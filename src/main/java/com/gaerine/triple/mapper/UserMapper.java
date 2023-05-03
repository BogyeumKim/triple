package com.gaerine.triple.mapper;

import com.gaerine.triple.domain.member.Member;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Mapper
public interface UserMapper {
    void save(Member mb);

    void socialSave(Member mb);

    @Select("select user_id from member where user_id=#{id}")
    Optional<String> getMemberId(String userId);

    @Select("select social_id from member where social_id=#{id}")
    Optional<String> getSocialId(String soocialId);

    @Select("select * from member where user_id=#{id} and user_pw=#{pw}")
    Member getMemberLogin(@Param("id") String userId, @Param("pw") String userPw);

    @Select("select * from member where social_id=#{id}")
    Member getMemberSocialLogin(String socialId);

    @Select("select * from member where member_id=#{id}")
    Member getMember(Long Id);

    @Delete("delete from member where member_id=#{id}")
    int deleteMember(Long id);
}
