package com.library.manage.service;

import com.library.common.pojo.EUDateGridRsp;
import com.library.manage.pojo.TbBook;

public interface BookService {

    void addBook(TbBook book) throws Exception;

    EUDateGridRsp getBookList(Integer page, Integer rows);

    void deleteById(long parseInt) throws Exception;

    void updateBook(TbBook book);

    void borrowBook(String username, String title) throws Exception;

    void returnBook(String username, String title) throws Exception;

    boolean checkBookName(String bookname);

}
