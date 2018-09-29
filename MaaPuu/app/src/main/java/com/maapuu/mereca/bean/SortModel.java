package com.maapuu.mereca.bean;

public class SortModel {

	private String name;   //显示的数据
	private String id;
	private String back;
	private String back1;
	private String is_friend;
	private String sortLetters;  //显示数据拼音的首字母
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSortLetters() {
		return sortLetters;
	}
	public void setSortLetters(String sortLetters) {
		this.sortLetters = sortLetters;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBack() {
		return back;
	}

	public void setBack(String back) {
		this.back = back;
	}

	public String getBack1() {
		return back1;
	}

	public void setBack1(String back1) {
		this.back1 = back1;
	}

	public String getIs_friend() {
		return is_friend;
	}

	public void setIs_friend(String is_friend) {
		this.is_friend = is_friend;
	}
}
