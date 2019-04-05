package com.mt.demo.web.model;

import java.io.Serializable;


public class Student implements Serializable{
  private static final long serialVersionUID = 1L;
  private int id;
  private String sname;
  private int sage;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getSname() {
    return sname;
  }

  public void setSname(String sname) {
    this.sname = sname;
  }

  public int getAage() {
    return sage;
  }

  public void setAage(int sage) {
    this.sage = sage;
  }

  @Override
  public String toString() {
    return "Student{" +
        "id=" + id +
        ", sname='" + sname + '\'' +
        ", sage=" + sage +
        '}';
  }
}
