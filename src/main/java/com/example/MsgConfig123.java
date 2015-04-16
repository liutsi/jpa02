package com.example;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 加了Entity注解之后，系统加载会首先创建MsgConfig1这张表，表里有个id字段
 * @author qcliu
 *
 */
@Entity(name="msgconfig")
public class MsgConfig123 {

    private String lpurl;
    private String siteName;
    private String assetName;
    private BigDecimal assetId;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String instance;
    
	public String getAssetName() {
		return assetName;
	}
	public void setAssetName(String assetName) {
		this.assetName = assetName;
	}
	public String getLpurl() {
		return lpurl;
	}
	public void setLpurl(String lpurl) {
		this.lpurl = lpurl;
	}
	public String getSiteName() {
		return siteName;
	}
	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}
	public BigDecimal getAssetId() {
		return assetId;
	}
	public void setAssetId(BigDecimal assetId) {
		this.assetId = assetId;
	}
	public String getInstance() {
		return instance;
	}
	public void setInstance(String instance) {
		this.instance = instance;
	}
    
}
