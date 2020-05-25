package com.swust.dao;

import java.lang.Long;
import java.lang.Integer;
import java.lang.String;
import java.util.Date;
import com.swust.entity.Hello;

public interface HelloDao {
  Hello selectByPrimaryKey(Long id);

  int deleteByPrimaryKey(Long id);

  int updateByPrimaryKey(Hello record);

  int insertSelective(Hello record);
}
