package com.vue.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vue.entity.Member;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface MemberMapper {

    @Select("select * from member")
    IPage<Member>  findAll(Page page);

    @Select("select * from member")
    public List<Member> findAll();

    @Select("select * from member where memberId = #{memberId}")
    public Member findById(Integer memberId);

    @Delete("delete from member where memberId = #{memberId}")
    public int deleteById(Integer memberId);

    @Update("update member set memberName = #{memberName}," +
            "tel = #{tel}, email = #{email},pwd = #{pwd},cardId = #{cardId},sex = #{sex}," +
            "career = #{career},role = #{role} where memberId = #{memberId}")
    public int update(Member member);

    @Options(useGeneratedKeys = true,keyProperty = "memberId")
    @Insert("insert into member(memberName,tel,email,pwd,cardId,sex,career,role) " +
            "values(#{memberName},#{tel},#{email},#{pwd},#{cardId},#{sex},#{career},#{role})")
    public int add(Member member);
}
