package com.library.manage.service;

import java.util.List;

import com.library.manage.pojo.TbBookCat;

public interface BookCatService {

    List<TbBookCat> getBookCatList(int parentId);

    TbBookCat createNode(int parentId, String name);

    void deleteNode(Integer parentId, Integer id) throws Exception;

    void updateNode(Integer id, String name);
}
