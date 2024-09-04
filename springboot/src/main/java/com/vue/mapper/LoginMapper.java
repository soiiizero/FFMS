package com.vue.mapper;

import com.vue.entity.Admin;
import com.vue.entity.Member;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface LoginMapper {

    @Select("select adminId,adminName,sex,tel,email,cardId,career,role from admin where adminId = #{username} and pwd = #{password}")
    public Admin adminLogin(Integer username, String password);

    @Select("select memberId,memberName,tel,email,cardId,sex," +
            "career,role from member where memberId = #{username} and pwd = #{password}")
    public Member memberLogin(Integer username, String password);

}
