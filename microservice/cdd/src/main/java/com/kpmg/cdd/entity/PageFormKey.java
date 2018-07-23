package com.kpmg.cdd.entity;

import java.io.Serializable;

public class PageFormKey implements Serializable {
    private static final long serialVersionUID = 1915931239973206786L;
    private Integer id;

    private Integer pageId;

    private String title;

    private String content;

    private String rollBack;

    private String currentOption;

    private String postUrl;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPageId() {
        return pageId;
    }

    public void setPageId(Integer pageId) {
        this.pageId = pageId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getRollBack() {
        return rollBack;
    }

    public void setRollBack(String rollBack) {
        this.rollBack = rollBack == null ? null : rollBack.trim();
    }

    public String getCurrentOption() {
        return currentOption;
    }

    public void setCurrentOption(String currentOption) {
        this.currentOption = currentOption == null ? null : currentOption.trim();
    }

    public String getPostUrl() {
        return postUrl;
    }

    public void setPostUrl(String postUrl) {
        this.postUrl = postUrl == null ? null : postUrl.trim();
    }
}