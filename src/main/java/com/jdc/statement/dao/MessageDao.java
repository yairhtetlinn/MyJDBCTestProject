package com.jdc.statement.dao;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import com.jdc.statement.ConnectionManager;
import com.jdc.statement.MessageDaoException;
import com.jdc.statement.dto.Member;
import com.jdc.statement.dto.Member.Role;
import com.jdc.statement.dto.Message;
import com.jdc.statement.utils.StringUitls;

public class MessageDao {
	
	private ConnectionManager manager;
	private MemberDao memberDao;

	public MessageDao(ConnectionManager manager) {
		super();
		this.manager = manager;
		memberDao = new MemberDao(manager);
	}
	
	public Message create(Message message) {		
		validate(message);
		try(var conn = manager.getConnection();
				var stmt = conn.prepareStatement("""
						INSERT into member (email,title,message)
						values (?,?,?)
						""",Statement.RETURN_GENERATED_KEYS)){
			stmt.setString(1, message.member().email());
			stmt.setString(2, message.title());
			stmt.setString(3, message.message());
			
			stmt.executeUpdate();
			var result = stmt.getGeneratedKeys();
			if(result.next()) {
				return message.CloneWithId(result.getInt(1));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Message findById(int id) {
		try(var conn = manager.getConnection();
				var stmt = conn.prepareStatement("""
						SELECT ms.id id, ms.title title,ms.message message,ms.post_at postAt,ms.email email,
						mb.name name,mb.role role,mb.password password,mb.dob dobb
						FROM message ms inner join member mb 
						ON mb.email=ms.email 
						WHERE ms.id=?
						""")){
			stmt.setInt(1, id);
			
			var result = stmt.executeQuery();
			
			while(result.next()) {
				return new Message(
						result.getInt("id"), 
						result.getString("title"), 
						result.getString("message"), 
						result.getTimestamp("postAt").toLocalDateTime(), 
						new Member(result.getString("email"), 
								result.getString("name"), 
								result.getString("passwrod"), 
								result.getDate("dobb").toLocalDate(), 
								Role.valueOf(result.getString("role"))));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void validate(Message message) {
		if(message == null) {
			throw new IllegalArgumentException();
		}
		
		if(StringUitls.isEmpty(message.title())) {
			throw new MessageDaoException("This is NO TITLE for this MESSAGE");
		}
		
		if(StringUitls.isEmpty(message.message())) {
			throw new MessageDaoException("This is NO MESSAGE LETTER ");
		}
		
		if(null == message.member()) {
			throw new MessageDaoException("This is NO MEMBER for this MESSAGE");
		}
		
		if(null == memberDao.findByEmail(message.member().email())) {
			throw new MessageDaoException("INVALID MEMBER for this MESSAGE");
		}
		
	}
	
	public int save(Message message) {
		validate(message);
		
		try(var conn = manager.getConnection();
				var stmt= conn.prepareStatement("UPDATE message SET title = ?,message = ? WHERE id = ?")){
			stmt.setString(1, message.title());
			stmt.setString(2, message.message());
			stmt.setInt(3, message.id());
			
			return stmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return 0;
	}
	
	//Member တိုင်းမှာ email ရှိရမှာ ဖြစ်တဲ့အတွက် email ကို လက်ခံပြီး အလုပ်လုပ်တဲ့ Method ကိုရေးပေးလိုက်တာ
	//Search တွေ Update တွေမှာ အခြေအနေကို ကြည့်ပြီး primary key ကို သုံး
	public List<Message> searchByMember(String email){
		return null;
	}
		
	public List<Message> search(String memberName,String Keyword){
		return null;
	}
	
	public int update(Message message) {
		return 0;
	}
	

	
	
}
