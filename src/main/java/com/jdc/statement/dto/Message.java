package com.jdc.statement.dto;

import java.time.LocalDateTime;

public record Message(
		int id,
		String title,
		String message,
		LocalDateTime postAt,
		Member member){
	
	public Message(String title, String message,Member member) {
		this(0,title,message,null,member);
	}

	
	public Message CloneWithId(int id) {
		return new Message(id, this.title, this.message,this.postAt(),this.member);
	}
	
	
	public Message CloneWithTitle(String title) {
		return new Message(this.id, title, this.message,this.postAt(),this.member);
	}
	
	
	public Message CloneWithMessage(String message) {
		return new Message(this.id, this.title, message,this.postAt(),this.member);
	}

}
