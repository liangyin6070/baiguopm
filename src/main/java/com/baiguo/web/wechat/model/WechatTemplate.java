package com.baiguo.web.wechat.model;

import java.util.Date;
/**
 * 
 * @description 微信公众号回复消息模版表映射类
 * @author ldw
 * @datetime 2017年3月23日 上午11:40:29
 */
public class WechatTemplate {
    private Integer id;
    private String matchText;
    private String accessType;
    private String msgType;
    private String mediaId;
    private String title;
    private String musicUrl;
    private String hdMusicUrl;
    private String thumbMediaId;
    private Integer articleCount;
    private String picUrl;
    private String url;
    private Boolean publishStatus;
    private Date createdTime;
    private Integer createdUserId;
    private Date modifiedTime;
    private Integer modifiedUserId;
    private String content;
    private String description;
    private String articles;
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMatchText() {
        return matchText;
    }

    public void setMatchText(String matchText) {
        this.matchText = matchText == null ? null : matchText.trim();
    }

    public String getAccessType() {
        return accessType;
    }

    public void setAccessType(String accessType) {
        this.accessType = accessType == null ? null : accessType.trim();
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType == null ? null : msgType.trim();
    }

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId == null ? null : mediaId.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getMusicUrl() {
        return musicUrl;
    }

    public void setMusicUrl(String musicUrl) {
        this.musicUrl = musicUrl == null ? null : musicUrl.trim();
    }

    public String getHdMusicUrl() {
        return hdMusicUrl;
    }

    public void setHdMusicUrl(String hdMusicUrl) {
        this.hdMusicUrl = hdMusicUrl == null ? null : hdMusicUrl.trim();
    }

    public String getThumbMediaId() {
        return thumbMediaId;
    }

    public void setThumbMediaId(String thumbMediaId) {
        this.thumbMediaId = thumbMediaId == null ? null : thumbMediaId.trim();
    }

    public Integer getArticleCount() {
        return articleCount;
    }

    public void setArticleCount(Integer articleCount) {
        this.articleCount = articleCount;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl == null ? null : picUrl.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public Boolean getPublishStatus() {
        return publishStatus;
    }

    public void setPublishStatus(Boolean publishStatus) {
        this.publishStatus = publishStatus;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Integer getCreatedUserId() {
        return createdUserId;
    }

    public void setCreatedUserId(Integer createdUserId) {
        this.createdUserId = createdUserId;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public Integer getModifiedUserId() {
        return modifiedUserId;
    }

    public void setModifiedUserId(Integer modifiedUserId) {
        this.modifiedUserId = modifiedUserId;
    }
    
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getArticles() {
        return articles;
    }

    public void setArticles(String articles) {
        this.articles = articles == null ? null : articles.trim();
    }
}