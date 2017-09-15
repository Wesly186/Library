package com.library.rest.service;

import java.util.List;

import com.library.manage.pojo.TbBook;

public interface BookService {

    List<TbBook> searchBook(String keyword);

    void appointmentBook(int uid, Long bid);

    TbBook getBookDetail(Long bid);
}
