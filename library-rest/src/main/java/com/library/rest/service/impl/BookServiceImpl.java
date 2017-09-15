package com.library.rest.service.impl;

import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.library.manage.mapper.TbAppointmentRecordMapper;
import com.library.manage.mapper.TbBookMapper;
import com.library.manage.mapper.TbBookMapperCustom;
import com.library.manage.pojo.TbAppointmentRecord;
import com.library.manage.pojo.TbBook;
import com.library.rest.service.BookService;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private TbBookMapper bookMapper;
    @Autowired
    private TbBookMapperCustom bookMapperCustom;
    @Autowired
    private TbAppointmentRecordMapper appointmentRecordMapper;

    @Override
    public List<TbBook> searchBook(String keyword) {
	List<TbBook> searchBook = bookMapperCustom.searchBook(keyword);
	return searchBook;
    }

    @Override
    public void appointmentBook(int uid, Long bid) {
	TbAppointmentRecord record = new TbAppointmentRecord();
	record.setAppointmentTime(new Date());
	record.setBid(bid);
	record.setBorrowed(false);
	record.setExpireTime(new DateTime().plusDays(7).toDate());
	record.setUid(uid);
	appointmentRecordMapper.insert(record);
    }

    @Override
    public TbBook getBookDetail(Long bid) {
	TbBook book = bookMapper.selectByPrimaryKey(bid);
	return book;
    }
}