package com.snapdeal.entity;

import java.util.ArrayList;
import java.util.List;

import javax.mail.internet.NewsAddress;

public class CourierDetails {

	List<String> localLane = new ArrayList<String>();
	List<String> pcwr = new ArrayList<String>();
	List<String> pcwor = new ArrayList<String>();
	List<String> npcwr = new ArrayList<String>();
	
	
	public List<String> getLocalLane() {
		return localLane;
	}
	public void setLocalLane(List<String> localLane) {
		this.localLane = localLane;
	}
	public List<String> getPcwr() {
		return pcwr;
	}
	public void setPcwr(List<String> pcwr) {
		this.pcwr = pcwr;
	}
	public List<String> getPcwor() {
		return pcwor;
	}
	public void setPcwor(List<String> pcwor) {
		this.pcwor = pcwor;
	}
	public List<String> getNpcwr() {
		return npcwr;
	}
	public void setNpcwr(List<String> npcwr) {
		this.npcwr = npcwr;
	}
	
	
}
