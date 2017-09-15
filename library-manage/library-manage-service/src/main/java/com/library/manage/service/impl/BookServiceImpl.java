package com.library.manage.service.impl;

import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.library.common.exception.CustomException;
import com.library.common.pojo.EUDateGridRsp;
import com.library.manage.mapper.TbAppointmentRecordMapper;
import com.library.manage.mapper.TbBookMapper;
import com.library.manage.mapper.TbBookMapperCustom;
import com.library.manage.mapper.TbBorrowRecordMapper;
import com.library.manage.mapper.TbUserMapper;
import com.library.manage.pojo.TbAppointmentRecord;
import com.library.manage.pojo.TbAppointmentRecordExample;
import com.library.manage.pojo.TbBook;
import com.library.manage.pojo.TbBookExample;
import com.library.manage.pojo.TbBorrowRecord;
import com.library.manage.pojo.TbBorrowRecordExample;
import com.library.manage.pojo.TbBorrowRecordExample.Criteria;
import com.library.manage.pojo.TbUser;
import com.library.manage.pojo.TbUserExample;
import com.library.manage.service.BookService;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private TbUserMapper userMapper;
    @Autowired
    private TbBookMapper bookMapper;
    @Autowired
    private TbBookMapperCustom bookMapperCustom;
    @Autowired
    private TbBorrowRecordMapper borrowRecordMapper;
    @Autowired
    private TbAppointmentRecordMapper appointmentRecordMapper;

    @Override
    public void addBook(TbBook book) throws Exception {
	bookMapper.insertSelective(book);
    }

    @Override
    public EUDateGridRsp getBookList(Integer page, Integer rows) {
	// 查询商品列表
	TbBookExample example = new TbBookExample();
	// 分页处理
	PageHelper.startPage(page, rows);
	List<TbBook> list = bookMapper.selectByExampleWithBLOBs(example);
	// 创建一个返回值对象
	EUDateGridRsp result = new EUDateGridRsp();
	result.setRows(list);
	// 取记录总条数
	PageInfo<TbBook> pageInfo = new PageInfo<>(list);
	result.setTotal((int) pageInfo.getTotal());
	return result;
    }

    @Override
    public void deleteById(long id) {
	bookMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void updateBook(TbBook book) {
	bookMapper.updateByPrimaryKeySelective(book);
    }

    @Override
    public void borrowBook(String username, String title) throws CustomException {
	TbUserExample userExample=new TbUserExample();
	com.library.manage.pojo.TbUserExample.Criteria userCriteria = userExample.createCriteria();
	userCriteria.andUsernameEqualTo(username);
	List<TbUser> users = userMapper.selectByExample(userExample);
	Integer uid = users.get(0).getId();
	System.out.println(uid);
	
	TbBookExample bookExample=new TbBookExample();
	com.library.manage.pojo.TbBookExample.Criteria bookCriteria = bookExample.createCriteria();
	bookCriteria.andTitleEqualTo(title);
	List<TbBook> books = bookMapper.selectByExample(bookExample);
	Long bid = books.get(0).getId();
	System.out.println(bid);
	
	TbBorrowRecordExample example1 = new TbBorrowRecordExample();
	Criteria criteria1 = example1.createCriteria();
	criteria1.andUidEqualTo(uid);
	criteria1.andBidEqualTo(bid);
	criteria1.andReturnedEqualTo(false);
	// 已经借了这本书的不能再借
	List<TbBorrowRecord> borrowList1 = borrowRecordMapper.selectByExample(example1);
	if (borrowList1 != null && borrowList1.size() > 0) {
	    throw new CustomException(40007, "请不要重复借同一本书！");
	}
	// 有借了未还的不能再借
	TbBorrowRecordExample example2 = new TbBorrowRecordExample();
	Criteria criteria2 = example2.createCriteria();
	criteria2.andUidEqualTo(uid);
	criteria2.andReturnTimeLessThan(new Date());
	List<TbBorrowRecord> borrowList2 = borrowRecordMapper.selectByExample(example2);
	if (borrowList2 != null && borrowList2.size() > 0) {
	    throw new CustomException(40007, "请及时归还图书并缴违期费！");
	}
	// 都被借完了的不能再借
	int restBookNum = bookMapperCustom.getRestBookNum(bid);
	if (restBookNum <= 0) {
	    throw new CustomException(40007, "该本图书借光了！");
	}
	// 插入借书记录
	TbBorrowRecord record = new TbBorrowRecord();
	record.setUid(uid);
	record.setBid(bid);
	record.setBorrowTime(new Date());
	record.setBorrowDays(60);
	record.setReturned(false);
	record.setReturnTime(new DateTime().plusDays(60).toDate());
	borrowRecordMapper.insert(record);
	// 调整book表的借出数
	TbBook tbBook = bookMapper.selectByPrimaryKey(bid);
	tbBook.setBorrowedNum(tbBook.getBorrowedNum() + 1);
	bookMapper.updateByPrimaryKey(tbBook);
	// 如果有未过期且存在的预约记录则修改预约状态
	TbAppointmentRecordExample appointmentRecordExample = new TbAppointmentRecordExample();
	com.library.manage.pojo.TbAppointmentRecordExample.Criteria appointmentCriteria = appointmentRecordExample
		.createCriteria();
	appointmentCriteria.andUidEqualTo(uid);
	appointmentCriteria.andBidEqualTo(bid);
	appointmentCriteria.andExpireTimeLessThan(new Date());
	List<TbAppointmentRecord> list = appointmentRecordMapper.selectByExample(appointmentRecordExample);
	if (list != null && list.size() > 0) {
	    TbAppointmentRecord appointmentRecord = list.get(0);
	    appointmentRecord.setBorrowed(true);
	    appointmentRecordMapper.updateByPrimaryKey(appointmentRecord);
	}
    }

    @Override
    public void returnBook(String username, String title) throws CustomException {
	TbUserExample userExample=new TbUserExample();
	com.library.manage.pojo.TbUserExample.Criteria userCriteria = userExample.createCriteria();
	userCriteria.andUsernameEqualTo(username);
	List<TbUser> users = userMapper.selectByExample(userExample);
	Integer uid = users.get(0).getId();
	System.out.println(uid);
	
	TbBookExample bookExample=new TbBookExample();
	com.library.manage.pojo.TbBookExample.Criteria bookCriteria = bookExample.createCriteria();
	bookCriteria.andTitleEqualTo(title);
	List<TbBook> books = bookMapper.selectByExample(bookExample);
	Long bid = books.get(0).getId();
	System.out.println(bid);
	// 查看是否逾期
	TbBorrowRecordExample example1 = new TbBorrowRecordExample();
	Criteria criteria1 = example1.createCriteria();
	criteria1.andUidEqualTo(uid);
	criteria1.andBidEqualTo(bid);
	criteria1.andReturnTimeLessThan(new Date());
	criteria1.andReturnedEqualTo(false);
	List<TbBorrowRecord> borrowList1 = borrowRecordMapper.selectByExample(example1);
	if (borrowList1 != null && borrowList1.size() > 0) {
	    System.out.println("请先完成逾期缴费");
	    throw new CustomException(40007, "请先完成逾期缴费！");
	}
	// 修改借书记录的归还状态
	TbBorrowRecordExample example2 = new TbBorrowRecordExample();
	Criteria criteria2 = example2.createCriteria();
	criteria2.andUidEqualTo(uid);
	criteria2.andBidEqualTo(bid);
	criteria2.andReturnedEqualTo(false);
	List<TbBorrowRecord> borrowList2 = borrowRecordMapper.selectByExample(example1);
	if (borrowList2 != null && borrowList2.size() > 0) {
	    TbBorrowRecord tbBorrowRecord = borrowList2.get(0);
	    tbBorrowRecord.setReturned(true);
	    borrowRecordMapper.updateByPrimaryKey(tbBorrowRecord);
	}else{
	    throw new CustomException(4007, "没有需要归还的图书");
	}
	// 调整book表的借出数
	TbBook tbBook = bookMapper.selectByPrimaryKey(bid);
	tbBook.setBorrowedNum(tbBook.getBorrowedNum() - 1);
	bookMapper.updateByPrimaryKey(tbBook);
    }

    @Override
    public boolean checkBookName(String bookname) {
	TbBookExample example = new TbBookExample();
	com.library.manage.pojo.TbBookExample.Criteria criteria = example.createCriteria();
	criteria.andTitleEqualTo(bookname);
	List<TbBook> list = bookMapper.selectByExample(example);
	return list != null && list.size() > 0;
    }
}
