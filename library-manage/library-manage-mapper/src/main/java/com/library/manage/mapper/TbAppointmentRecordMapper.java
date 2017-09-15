package com.library.manage.mapper;

import com.library.manage.pojo.TbAppointmentRecord;
import com.library.manage.pojo.TbAppointmentRecordExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TbAppointmentRecordMapper {
    int countByExample(TbAppointmentRecordExample example);

    int deleteByExample(TbAppointmentRecordExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TbAppointmentRecord record);

    int insertSelective(TbAppointmentRecord record);

    List<TbAppointmentRecord> selectByExample(TbAppointmentRecordExample example);

    TbAppointmentRecord selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TbAppointmentRecord record, @Param("example") TbAppointmentRecordExample example);

    int updateByExample(@Param("record") TbAppointmentRecord record, @Param("example") TbAppointmentRecordExample example);

    int updateByPrimaryKeySelective(TbAppointmentRecord record);

    int updateByPrimaryKey(TbAppointmentRecord record);
}