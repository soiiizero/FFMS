package com.vue.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vue.entity.Income;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface IncomeMapper {
    @Select("select * from income")
    List<Income> findAll();

    @Select("select * from income")
    IPage<Income> findAll(Page page);

    @Select("select * from income where incomeId = #{incomeId}")
    Income findById(Integer incomeId);

    @Delete("delete from income where incomeId = #{incomeId}")
    int delete(Integer incomeId);

    @Update("update income set incomeName = #{incomeName},money = #{money},incomeway = #{incomeway}," +
            "label = #{label},notes = #{notes},datetime = #{datetime} where incomeId = #{incomeId}")
    int update(Income income);

    @Options(useGeneratedKeys = true,keyProperty = "incomeId")
    @Insert("insert into income(incomeName,money,incomeway,label,notes,datetime)" +
            " values(#{incomeName},#{money},#{incomeway},#{label},#{notes},#{datetime})")
    int add(Income income);

    /**
     * 查询最后一条记录的incomeId,返回给前端达到自增效果
     * @return incomeId
     */
    @Select("select incomeId from income order by incomeId desc limit 1")
    Income findOnlyIncomeId();
}
