package com.jdc.statement.dao;

import com.jdc.statement.ConnectionManager;
import com.jdc.statement.dto.Member;

public class MemberDao {
	private ConnectionManager manager;

	public MemberDao(ConnectionManager manager) {
		super();
		this.manager = manager;
	}
	
	public int createMember(Member member) {
		
		return 0;
	}
	
	public Member findByEmail(String email) {
		return null;
	}
	
	public int changePassword(String email,String oldPass, String newPass) {
		return 0;
	}
}
	