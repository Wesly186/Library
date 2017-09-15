package com.library.manage.mapper;

import java.util.List;

import com.library.manage.pojo.TbBook;

public interface TbBookMapperCustom {

    int getRestBookNum(Long bid);
    
    List<TbBook> searchBook(String keyword);
}
