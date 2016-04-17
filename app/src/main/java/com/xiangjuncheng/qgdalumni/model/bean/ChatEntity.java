package com.xiangjuncheng.qgdalumni.model.bean;

import cn.bmob.v3.BmobObject;

public class ChatEntity extends BmobObject{
	private String content;
	private String time;
	private String sendAccount;
	private String sendName;
	private String receiveAccount;

	public ChatEntity(String content, String time, String sendAccount,String sendName){
		this.content = content;
		this.time = time;
		this.sendAccount = sendAccount;
		this.sendName = sendName;
	}

	public ChatEntity(){
	}

	public String getReceiveAccount() {
		return receiveAccount;
	}

	public void setReceiveAccount(String receiveAccount) {
		this.receiveAccount = receiveAccount;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}

	public String getSendAccount() {
		return sendAccount;
	}

	public void setSendAccount(String sendAccount) {
		this.sendAccount = sendAccount;
	}

	public String getSendName() {
		return sendName;
	}

	public void setSendName(String sendName) {
		this.sendName = sendName;
	}
}
