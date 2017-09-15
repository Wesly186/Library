package com.library.manage.mapper;

import com.library.manage.pojo.TbBook;
import com.library.manage.pojo.TbBookExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TbBookMapper {
    int countByExample(TbBookExample example);

    int deleteByExample(TbBookExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TbBook record);

    int insertSelective(TbBook record);

    List<TbBook> selectByExampleWithBLOBs(TbBookExample example);

    List<TbBook> selectByExample(TbBookExample example);

    TbBook selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") TbBook record, @Param("example") TbBookExample example);

    int updateByExampleWithBLOBs(@Param("record") TbBook record, @Param("example") TbBookExample example);

    int updateByExample(@Param("record") TbBook record, @Param("example") TbBookExample example);

    int updateByPrimaryKeySelective(TbBook record);

    int updateByPrimaryKeyWithBLOBs(TbBook record);

    int updateByPrimaryKey(TbBook record);
}