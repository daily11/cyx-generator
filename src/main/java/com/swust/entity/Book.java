package com.swust.entity;

import java.io.Serializable;
import java.lang.Integer;
import java.lang.String;
import java.util.Date;

public class Book implements Serializable {
  private Integer id;
  private String name;
  private Date gmtCreate;
  private Date gmtModified;

  public void setId(Integer id) {this.id = id; }
  public Integer getId() { return id;}

  public void setName(String name) {this.name = name; }
  public String getName() { return name;}

  public void setGmtCreate(Date gmtCreate) {this.gmtCreate = gmtCreate; }
  public Date getGmtCreate() { return gmtCreate;}

  public void setGmtModified(Date gmtModified) {this.gmtModified = gmtModified; }
  public Date getGmtModified() { return gmtModified;}


}