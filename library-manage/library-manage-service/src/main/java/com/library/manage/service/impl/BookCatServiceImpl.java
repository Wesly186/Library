package com.library.manage.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.library.manage.mapper.TbBookCatMapper;
import com.library.manage.pojo.TbBookCat;
import com.library.manage.pojo.TbBookCatExample;
import com.library.manage.pojo.TbBookCatExample.Criteria;
import com.library.manage.service.BookCatService;

@Service
public class BookCatServiceImpl implements BookCatService {

    @Autowired
    private TbBookCatMapper bookCatMapper;

    @Override
    public List<TbBookCat> getBookCatList(int parentId) {
	TbBookCatExample example = new TbBookCatExample();
	Criteria criteria = example.createCriteria();
	criteria.andParentIdEqualTo(parentId);
	List<TbBookCat> bookCats = bookCatMapper.selectByExample(example);
	return bookCats;
    }

    @Override
    public TbBookCat createNode(int parentId, String name) {
	TbBookCat bookCat = new TbBookCat();
	bookCat.setName(name);
	bookCat.setIsParent(false);
	bookCat.setParentId(parentId);
	bookCat.setSortOrder(1);
	bookCatMapper.insert(bookCat);
	// 查看父节点的isParent列是否为true，如果不是true改成true
	TbBookCat parentCat = bookCatMapper.selectByPrimaryKey(parentId);
	// 判断是否为true
	if (!parentCat.getIsParent()) {
	    parentCat.setIsParent(true);
	    // 更新父节点
	    bookCatMapper.updateByPrimaryKey(parentCat);
	}
	return bookCat;
    }

    @Override
    public void deleteNode(Integer parentId, Integer id) throws Exception {
	bookCatMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void updateNode(Integer id, String name) {
	TbBookCat bookCat = new TbBookCat();
	bookCat.setId(id);
	bookCat.setName(name);
	bookCatMapper.updateByPrimaryKeySelective(bookCat);
    }
}
