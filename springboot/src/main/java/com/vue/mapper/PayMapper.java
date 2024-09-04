package com.vue.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vue.entity.Pay;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PayMapper {
    @Select("select * from pay")
    List<Pay> findAll();

    @Select("select * from pay")
    IPage<Pay> findAll(Page page);

    @Select("select * from pay where payId = #{payId}")
    Pay findById(Integer payId);

    @Delete("delete from pay where payId = #{payId}")
    int delete(Integer payId);

    @Update("update pay set payName = #{payName},money = #{money},payway = #{payway}," +
            "label1 = #{label1},label2 = #{label2},notes = #{notes},datetime = #{datetime}," +
            "toperson = #{toperson},place = #{place} where payId = #{payId}")
    int update(Pay pay);

    @Options(useGeneratedKeys = true,keyProperty = "payId")
    @Insert("insert into pay(payName,money,payway,label1,label2,notes,datetime,toperson,place)" +
            " values(#{payName},#{money},#{payway},#{label1},#{label2},#{notes},#{datetime},#{toperson},#{place})")
    int add(Pay pay);

    /**
     * 查询最后一条记录的payId,返回给前端达到自增效果
     * @return payId
     */
    @Select("select payId from pay order by payId desc limit 1")
    Pay findOnlyPayId();
}
