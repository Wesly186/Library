package com.library.manage.mapper;

import com.library.manage.pojo.TbRolePermission;
import com.library.manage.pojo.TbRolePermissionExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TbRolePermissionMapper {
    int countByExample(TbRolePermissionExample example);

    int deleteByExample(TbRolePermissionExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TbRolePermission record);

    int insertSelective(TbRolePermission record);

    List<TbRolePermission> selectByExample(TbRolePermissionExample example);

    TbRolePermission selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TbRolePermission record, @Param("example") TbRolePermissionExample example);

    int updateByExample(@Param("record") TbRolePermission record, @Param("example") TbRolePermissionExample example);

    int updateByPrimaryKeySelective(TbRolePermission record);

    int updateByPrimaryKey(TbRolePermission record);
}