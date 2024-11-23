package com.jdc.statement.dao;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import com.jdc.statement.ConnectionManager;
import com.jdc.statement.dto.Member;
import com.jdc.statement.dto.Message;

public class MessageDao {
	
	private ConnectionManager manager;

	public MessageDao(ConnectionManager manager) {
		super();
		this.manager = manager;
	}
	
/*	public List<Message> createMessages(List<Message> messages){
		
		List<Message> list = new ArrayList<Message>();
		
		if(null == messages) {
			return list;
		}
		
		var sql = "insert into message (title,message) values (?,?)";
		
		try(var conn= manager.getConnection();
				var stmt = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS)) {
			
			for(var m: messages) {
				stmt.setString(1, m.title());
				stmt.setString(2, m.message());
				stmt.addBatch();
			}
			
		    stmt.executeBatch();//executeBactch() က 
											//batch လုပ်ပြီးထည့်လိုက်တဲ့ အကြောင်းအရေအတွက် စုစုပေါင်းကို int array အနေနဲ့ ပြန်ပေးတယ်
			
			var keys = stmt.getGeneratedKeys();
			
			var index = 0;
			while(keys.next()) {
				list.add(messages.get(index).CloneWithId(keys.getInt(1)));
//သူက messages ကရတဲ့ တန်ဖိုးထဲကို Array ထဲထည့်မှာမလို့ အပြင်ကနေ variable တစ်လုံးကို 0 နဲ့ ယူပြီး get() method ကိုသုံးပြီး 0 ကနေ
//စပြီး တန်ဖိုးတွေကို စီပေးလိုက်တာ 
				index ++;
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	public Message createMessage(Message data) {
		
		var sql = "insert into message (title,message) values (?,?)";
		
		try(var conn= manager.getConnection();
				var stmt = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS)) {
			
			stmt.setString(1, data.title());
			stmt.setString(2, data.message());
			
		 stmt.executeUpdate();
		 
		 var result = stmt.getGeneratedKeys();
		 
		 if(result.next()) {
			 var id = result.getInt(1);
			 return data.CloneWithId(id);
		 }
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public Message findById (int id) {
		
		var sql = "select * from message where id = ?";
		
		try(var conn = manager.getConnection();
				var stmt = conn.prepareStatement(sql)) {
			
			stmt.setInt(1, id);
			
			var resultSet = stmt.executeQuery();
			
			if(resultSet.next()) {
				return new Message(
						resultSet.getInt("id"), 
						resultSet.getString("title"),
						resultSet.getString("message"),
						resultSet.getTimestamp("post_at").toLocalDateTime());//Message ထဲကို object ဆောက်ဖို့ အတွက် 
																			//LocalDateTime ပြောင်းပေး Message က LocalDateTime ဖြစ်နေလို့
																			// သူ့ကို သုံးချင်ရင် Database ထဲက column ကို not null ပြောင်းပေးရတယ် 
																			// မဟုတ်ရင် Null Pointer Exception တက်ပြီ
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public int update(int id, String title, String message) {
		var sql = "update message set title =?, message =? where id = ?";
		
		try(var conn = manager.getConnection();
				var stmt = conn.prepareStatement(sql)) {
			
			stmt.setString(1, title);
			stmt.setString(2, message);
			stmt.setInt(3, id);
			
			return stmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	return 0;
	}
	
	public int deleteById(int id) {
		
		var sql = "delete from message where id=?";
		
		try(var conn = manager.getConnection();
				var stmt = conn.prepareStatement(sql)) {
			
			stmt.setInt(1, id);
			
			return stmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return 0;
	}
*/
	public Message create(Message message) {
		return null;
	}
	
	public List<Message> search(String memberName,String Keyword){
		return null;
	}
	
	public List<Message> searchByMember(String memberName,Member member){
		return null;
	}
	
	
	public int update(Message message) {
		return 0;
	}
	
	public Message findById(int id) {
		return null;
	}
	
	
}
