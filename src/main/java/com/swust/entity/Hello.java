package com.swust.entity;

import java.io.Serializable;
import java.lang.Long;
import java.lang.Integer;
import java.lang.String;
import java.util.Date;

public class Hello implements Serializable {
  /**
  *  主键
  */
  private Long id;
  /**
  *  状态码
  */
  private Integer code;
  /**
  *  返回结果
  */
  private String msg;
  /**
  *  创建时间
  */
  private Date createdTime;
  /**
  *  更新时间
  */
  private Date updatedTime;

  public void setId(Long id) {this.id = id; }
  public Long getId() { return id;}

  public void setCode(Integer code) {this.code = code; }
  public Integer getCode() { return code;}

  public void setMsg(String msg) {this.msg = msg; }
  public String getMsg() { return msg;}

  public void setCreatedTime(Date createdTime) {this.createdTime = createdTime; }
  public Date getCreatedTime() { return createdTime;}

  public void setUpdatedTime(Date updatedTime) {this.updatedTime = updatedTime; }
  public Date getUpdatedTime() { return updatedTime;}


}