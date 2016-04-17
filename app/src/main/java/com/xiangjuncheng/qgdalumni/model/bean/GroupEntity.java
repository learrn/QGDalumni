/**
 * ����ʵ����
 */
package com.xiangjuncheng.qgdalumni.model.bean;

import cn.bmob.v3.BmobObject;

public class GroupEntity extends BmobObject{
	private int avatar;
	private String group;
	private String account;
	private String nick;
	private String trends;

    public GroupEntity(){
    }
	
	public GroupEntity(int avatar, String account, String nick, String trends,String group){
		this.avatar=avatar;
		this.account=account;
		this.nick=nick;
		this.trends=trends;
        this.group=group;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public int getAvatar() {
		return avatar;
	}

	public void setAvatar(int avatar) {
		this.avatar = avatar;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getTrends() {
		return trends;
	}

	public void setTrends(String trends) {
		this.trends = trends;
	}	
}
