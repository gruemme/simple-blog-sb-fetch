package com.example.mySimpleBlog.dto;

public class TagCount {

  private final String tag;
  private final Long count;

  public TagCount(String tag, Long count) {
    this.tag = tag;
    this.count = count;
  }

  public String getTag() {
    return tag;
  }

  public Long getCount() {
    return count;
  }

  @Override
  public String toString() {
    return "TagCount{" + "tag='" + tag + '\'' + ", count=" + count + '}';
  }
}
