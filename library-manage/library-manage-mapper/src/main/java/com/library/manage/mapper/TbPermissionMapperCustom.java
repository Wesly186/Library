package com.library.manage.mapper;

import java.util.List;

import com.library.manage.pojo.TbPermission;

public interface TbPermissionMapperCustom {
    
    List<TbPermission> selectMenusByUserId(int userId);

    List<TbPermission> selectPermissionsByUserId(int userId);
}