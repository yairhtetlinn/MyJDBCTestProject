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

}