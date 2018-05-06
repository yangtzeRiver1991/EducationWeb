package com.edu.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "t_document")
public class Document implements Serializable{
	private static final long serialVersionUID = 1155318835085392555L;

	private Integer id;
	
	private String title;
	
	private String tags;
	
	private String intro;
	
	private String content;
	
	private String uploadImgs;
	
	private Integer lookNum;
	
	private Integer commentNum;
	
	private String createTime;
	
	private String createIp;
	
	private Integer status;//0正常 1废弃
	
	private Integer isHot;//0正常 1热门
	
	private Integer isTop;//0正常 1置顶
	
	private String carouselImg;//置顶时的轮播图片
	
	private PageResult<Comment> commentList;
	
	private List<Attachment> attachments;
	
	private Map<String, Object> upDownDocument;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "title",nullable = false,unique = true)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	
	@Column(name = "tags",nullable = false)
	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	@Column(name = "intro",nullable = false,length = 500)
	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	
	@Column(name = "content",nullable = false,length = 60000)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	
	@Column(name = "uploadImgs",nullable = false,length = 1000)
	public String getUploadImgs() {
		return uploadImgs;
	}

	public void setUploadImgs(String uploadImgs) {
		this.uploadImgs = uploadImgs;
	}

	@Column(name = "looknum",nullable = false)
	public Integer getLookNum() {
		return lookNum;
	}

	public void setLookNum(Integer lookNum) {
		this.lookNum = lookNum;
	}

	@Column(name = "commentnum",nullable = false)
	public Integer getCommentNum() {
		return commentNum;
	}

	public void setCommentNum(Integer commentNum) {
		this.commentNum = commentNum;
	}

	@Column(name = "createtime",nullable = false)
	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	@Column(name = "createIp",nullable = false)
	public String getCreateIp() {
		return createIp;
	}

	public void setCreateIp(String createIp) {
		this.createIp = createIp;
	}

	@Column(name = "status",nullable = false)
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	@Column(name = "isHot",nullable = false)
	public Integer getIsHot() {
		return isHot;
	}

	public void setIsHot(Integer isHot) {
		this.isHot = isHot;
	}

	@Column(name = "isTop",nullable = false)
	public Integer getIsTop() {
		return isTop;
	}

	public void setIsTop(Integer isTop) {
		this.isTop = isTop;
	}
	
	@Column(name = "carouselImg",nullable = true,length=1000)
	public String getCarouselImg() {
		return carouselImg;
	}

	public void setCarouselImg(String carouselImg) {
		this.carouselImg = carouselImg;
	}

	@Transient
	public PageResult<Comment> getCommentList() {
		return commentList;
	}

	public void setCommentList(PageResult<Comment> commentList) {
		this.commentList = commentList;
	}

	@Transient
	public List<Attachment> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<Attachment> attachments) {
		this.attachments = attachments;
	}

	@Transient
	public Map<String, Object> getUpDownDocument() {
		return upDownDocument;
	}

	public void setUpDownDocument(Map<String, Object> upDownDocument) {
		this.upDownDocument = upDownDocument;
	}
		
}
