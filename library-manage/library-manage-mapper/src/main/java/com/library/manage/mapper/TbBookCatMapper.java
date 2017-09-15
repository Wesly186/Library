package com.library.manage.mapper;

import com.library.manage.pojo.TbBookCat;
import com.library.manage.pojo.TbBookCatExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TbBookCatMapper {
    int countByExample(TbBookCatExample example);

    int deleteByExample(TbBookCatExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TbBookCat record);

    int insertSelective(TbBookCat record);

    List<TbBookCat> selectByExample(TbBookCatExample example);

    TbBookCat selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TbBookCat record, @Param("example") TbBookCatExample example);

    int updateByExample(@Param("record") TbBookCat record, @Param("example") TbBookCatExample example);

    int updateByPrimaryKeySelective(TbBookCat record);

    int updateByPrimaryKey(TbBookCat record);
}