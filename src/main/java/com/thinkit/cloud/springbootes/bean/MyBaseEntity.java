package com.thinkit.cloud.springbootes.bean;

import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * 自定义基础base实体类
 *
 */
public class MyBaseEntity {

	/**
	 * 创建人ID  create_User_Id
	 */
	@ApiModelProperty(value = "创建人ID")
	private Long createUserId;

  /**
   * 创建人ID  create_User_Name
   */
  @ApiModelProperty(value = "创建人姓名")
	private String createUserName;
	
	/**
	 * 修改人ID  update_User_Id
	 */
	@ApiModelProperty(value = "修改人ID")
	private Long updateUserId;
	
  /**
   * 修改人ID  update_user_name
   */
  @ApiModelProperty(value = "修改人ID")
	private String updateUserName;
	
	/**
	 * 创建时间  create_Time
	 */
	@ApiModelProperty(value = "创建时间")
	private java.util.Date createTime;
	
	 /**
	 * 创建时间Begin
	 */
	private String  createTimeBegin;
	/**
	 * 创建时间End
	 */
	private String createTimeEnd;
	/**
	 * 更新时间  update_Time
	 */
	@ApiModelProperty(value = "更新时间")
	private java.util.Date updateTime;
	
	 /**
	 * 更新时间Begin
	 */
	private String  updateTimeBegin;
	/**
	 * 更新时间End
	 */
	private String updateTimeEnd;
	
	/**
	 * 分页数
	 */
	@ApiModelProperty(value = "分页数")
	private Integer page = 1;
	
	/**
	 * 分页默认每页10条
	 */
	@ApiModelProperty(value = "分页默认每页10条")
	private Integer limit = 10;
	
	public Long getCreateUserId() {
		return createUserId;
	}
	public void setCreateUserId(Long createUserId) {
		this.createUserId = createUserId;
	}
	public Long getUpdateUserId() {
		return updateUserId;
	}
	public void setUpdateUserId(Long updateUserId) {
		this.updateUserId = updateUserId;
	}
	public java.util.Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}
	public String getCreateTimeBegin() {
		return createTimeBegin;
	}
	public void setCreateTimeBegin(String createTimeBegin) {
		this.createTimeBegin = createTimeBegin;
	}
	public String getCreateTimeEnd() {
		return createTimeEnd;
	}
	public void setCreateTimeEnd(String createTimeEnd) {
		this.createTimeEnd = createTimeEnd;
	}
	
    public java.util.Date getUpdateTime() {
        return updateTime;
    }
    public void setUpdateTime(java.util.Date updateTime) {
        this.updateTime = updateTime;
    }
    public String getUpdateTimeBegin() {
        return updateTimeBegin;
    }
    public void setUpdateTimeBegin(String updateTimeBegin) {
        this.updateTimeBegin = updateTimeBegin;
    }
    public String getUpdateTimeEnd() {
        return updateTimeEnd;
    }
    public void setUpdateTimeEnd(String updateTimeEnd) {
        this.updateTimeEnd = updateTimeEnd;
    }
    public String getCreateUserName() {
        return createUserName;
    }
    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }
    public String getUpdateUserName() {
        return updateUserName;
    }
    public void setUpdateUserName(String updateUserName) {
        this.updateUserName = updateUserName;
    }
	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	public Integer getLimit() {
		return limit;
	}
	public void setLimit(Integer limit) {
		this.limit = limit;
	}

}
