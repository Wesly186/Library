package com.library.portal.service;

import java.util.List;

import com.library.manage.pojo.TbBook;

public interface BookService {

    List<TbBook> searchBook(String keyword) throws Exception;

    void appointmentBook(int uid, Long bid) throws Exception;

    TbBook getBookDetail(Long id) throws Exception;
}
