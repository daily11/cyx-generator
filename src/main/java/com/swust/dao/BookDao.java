package com.swust.dao;

import java.lang.Integer;
import java.lang.String;
import java.util.Date;
import com.swust.entity.Book;

public interface BookDao {
  Book selectByPrimaryKey(Long id);

  int deleteByPrimaryKey(Integer id);

  int updateByPrimaryKey(Book record);

  int insertSelective(Book record);
}
