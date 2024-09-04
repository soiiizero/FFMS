package com.vue.mapper;

import com.vue.entity.Admin;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AdminMapper {

    @Select("select * from admin")
    public List<Admin> findAll();

    @Select("select * from admin where adminId = #{adminId}")
    public Admin findById(Integer adminId);

    @Delete("delete from admin where adminId = #{adminId}")
    public int deleteById(int adminId);

    @Update("update admin set adminName = #{adminName},sex = #{sex}," +
            "tel = #{tel}, email = #{email},pwd = #{pwd},cardId = #{cardId},career = #{career},role = #{role} where adminId = #{adminId}")
    public int update(Admin admin);

    @Options(useGeneratedKeys = true,keyProperty = "adminId")
    @Insert("insert into admin(adminName,sex,tel,email,pwd,cardId,role) " +
            "values(#{adminName},#{sex},#{tel},#{email},#{pwd},#{cardId},#{career},#{role})")
    public int add(Admin admin);
}
