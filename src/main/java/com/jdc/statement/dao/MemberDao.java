package com.jdc.statement.dao;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.jdc.statement.ConnectionManager;
import com.jdc.statement.MessageDaoException;
import com.jdc.statement.dto.Member;
import com.jdc.statement.dto.Member.Role;
import com.jdc.statement.utils.StringUitls;

public class MemberDao {
	private ConnectionManager manager;

	public MemberDao(ConnectionManager manager) {
		super();
		this.manager = manager;
	}
	
	public int createMember(Member member) {
		
		//Validation
		if(member == null) {
			throw new IllegalArgumentException();
		}
		
		//email
		if(StringUitls.isEmpty(member.email())) {
			throw new MessageDaoException("Member Email must not be empty");
		}
		
		//name
		if(StringUitls.isEmpty(member.name())) {
			throw new MessageDaoException("Member Name must not be empty");
		}
		
		//password
		if(StringUitls.isEmpty(member.password())) {
			throw new MessageDaoException("Member Password must not be empty");
		}
		
		//dob
		if(null == member.dob()) {
			throw new MessageDaoException("Member Date Of Bath must not be Null");
		}
		
		try(var conn = manager.getConnection();
				var stmt = conn.prepareStatement(""" 
							insert into member values (?,?,?,?,?)
						""")){
			stmt.setString(1, member.email());
			stmt.setString(2, member.name());
			stmt.setString(3, member.password());
			stmt.setDate(4, Date.valueOf(member.dob()));
			stmt.setString(5, member.role().name());
			
			return stmt.executeUpdate();
			
			
		} catch (SQLException e) {
			throw new MessageDaoException("Email has been already existed");
		}
		
		
	}
	
	public Member findByEmail(String email) {
		
		if(StringUitls.isEmpty(email)) {
			throw new IllegalArgumentException();
		}
		
		try(var conn = manager.getConnection();
				var stmt = conn.prepareStatement("""
						select * from member where email = ?
						""")){
			stmt.setString(1, email);
			
			var result = stmt.executeQuery();
			
			while(result.next()) {
				return new Member(result.getString("email"), 
						result.getString("name"), 
						result.getString("password"), 
						result.getDate("dob").toLocalDate(), 
						Role.valueOf(result.getString("role")));
				//Enum ဖြစ်တဲ့အတွက် သူ့ရဲ့ Class ကို ပြန်ပေးရတယ်
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public int changePassword(String email,String oldPass, String newPass) {
		
		//Validation
		
		if(StringUitls.isEmpty(email)) {
			throw new MessageDaoException("Email Must Not Be EMPTY");
		}
		
		
		if(StringUitls.isEmpty(oldPass)) {
			throw new MessageDaoException("Old Password Must Not Be EMPTY");
		}
		
		if(StringUitls.isEmpty(newPass)) {
			throw new MessageDaoException("New Password Must Not Be EMPTY");
		}
		if(oldPass.equals(newPass)) {
			throw new MessageDaoException("The Psswords are SAME");
		}
		
		try(var connection = manager.getConnection();
				var stmt = connection.prepareStatement("""
						select * from member where email = ?
						""",ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE)){
			stmt.setString(1, email);
			var result = stmt.executeQuery();
			
			if(result.next()) {
				if(!oldPass.equals(result.getString("password"))){
					throw new MessageDaoException("Please check OLDPASSWORD");
				}
				result.updateString("password", newPass);
				result.updateRow();
				return 1;
			}
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		throw new MessageDaoException("Please Check Email");
	}
}
	