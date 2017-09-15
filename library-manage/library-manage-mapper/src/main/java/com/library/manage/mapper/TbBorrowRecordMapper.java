package com.library.manage.mapper;

import com.library.manage.pojo.TbBorrowRecord;
import com.library.manage.pojo.TbBorrowRecordExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TbBorrowRecordMapper {
    int countByExample(TbBorrowRecordExample example);

    int deleteByExample(TbBorrowRecordExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TbBorrowRecord record);

    int insertSelective(TbBorrowRecord record);

    List<TbBorrowRecord> selectByExample(TbBorrowRecordExample example);

    TbBorrowRecord selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TbBorrowRecord record, @Param("example") TbBorrowRecordExample example);

    int updateByExample(@Param("record") TbBorrowRecord record, @Param("example") TbBorrowRecordExample example);

    int updateByPrimaryKeySelective(TbBorrowRecord record);

    int updateByPrimaryKey(TbBorrowRecord record);
}