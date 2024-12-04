package com.jdc.statement.test;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;
import java.time.LocalDate;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.jdc.statement.ConnectionManager;
import com.jdc.statement.DatabaseInitializer;
import com.jdc.statement.MessageDaoException;
import com.jdc.statement.dao.MessageDao;
import com.jdc.statement.dto.Member;
import com.jdc.statement.dto.Member.Role;
import com.jdc.statement.dto.Message;

@TestMethodOrder(OrderAnnotation.class)
class MessageDaoTest {
	
	MessageDao dao;
	static Message message;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		
		//Truncate Tables
		DatabaseInitializer.truncate("member","message");
		
		//Create Members
		try(var conn = ConnectionManager.getInstance().getConnection();
				var stmt = conn.prepareStatement("""
						insert into member values (?,?,?,?,?)
						""")){
			stmt.setString(1, "Found@gmail.com");
			stmt.setString(2, "FoundedUser");
			stmt.setString(3, "FoundedUser");
			stmt.setDate(4, Date.valueOf("2000-02-02"));
			stmt.setString(5 , Role.Member.name());
			
			stmt.addBatch();
			
			stmt.setString(1, "NotFound@gmail.com");
			stmt.setString(2, "NotFoundedUser");
			stmt.setString(3, "NotFoundedUser");
			stmt.setDate(4, Date.valueOf("2000-02-02"));
			stmt.setString(5 , Role.Member.name());
			
			stmt.addBatch();
			
			stmt.executeBatch();
			
		}
		message = new Message("Test Title", "Test Message", 
					new Member("Found@gmail.com", "FoundedUser", "FoundedUser"
							, LocalDate.of(2002, 02, 20), Role.Member));
	}

	@BeforeEach
	void setUp() throws Exception {
		dao = new MessageDao(ConnectionManager.getInstance());
	}

	@Order(1)
	@Test
	void testCreate() {
		var result = dao.create(message);
		assertEquals(1, result.id());
	}
	
	@Order(2)
	@Test
	void testCreateNull() {
		assertThrows(IllegalArgumentException.class,
				()->dao.create(null));
	}
	
	@Order(3)
	@Test
	void testCreateNullMember() {
		var NullMember = new Message("Title", "Member", null );
		var exception = assertThrows(MessageDaoException.class, 
				()->dao.create(NullMember));
		assertEquals("This is NO MEMBER for this MESSAGE", exception.getMessage());
	}
	
	@Order(4)
	@Test
	void testCreateInvalidMember() {
		var InvalidMember = new Message("Title", "member", 
				new Member("Yair Htet Linn", "Yair ", null, null, null));
		var exception = assertThrows(MessageDaoException.class, 
				()->dao.create(InvalidMember));
		assertEquals("INVALID MEMBER for this MESSAGE", exception.getMessage());
		
	}
	
	@Order(5)
	@Test
	void testCreateNullTitle() {
		var NullTitle = new Message(null, "member", message.member());
		var exception = assertThrows(MessageDaoException.class, 
				()->dao.create(NullTitle));
		assertEquals("This is NO TITLE for this MESSAGE", exception.getMessage());

		var EmptyTitle = new Message("", "member", message.member());
		exception = assertThrows(MessageDaoException.class, 
				()->dao.create(EmptyTitle));
		assertEquals("This is NO TITLE for this MESSAGE", exception.getMessage());
	}
	
	@Order(6)
	@Test
	void testCreateNullMessage() {
		var NullMessage = new Message("Tite", null, message.member());
		var exception = assertThrows(MessageDaoException.class, 
				()->dao.create(NullMessage));
		assertEquals("This is NO MESSAGE LETTER ", exception.getMessage());

		var EmptyMessage = new Message("", "member", message.member());
		exception = assertThrows(MessageDaoException.class, 
				()->dao.create(EmptyMessage));
		assertEquals("This is NO MESSAGE LETTER ", exception.getMessage());
	}
	
	@Order(7)
	@Test
	void testFindById() {
		var result = dao.findById(1);
		assertNotNull(result);
		assertEquals(message.CloneWithId(1), result);
	}
	
	@Order(8)
	@Test
	void testFindByIdNotFound() {
		var result = dao.findById(2);
		assertNull(result);
	}
	
	@Order(9)
	@Test
	void testSaveOk() {
		var target = dao.findById(1);
		//Variable ကို Database ထဲက Data ကို findById နဲ့ ရှာပြီးထည့်ပေးတယ်
		//ပြီးမှ အဲ့တန်ဖိုးကို အောက်မှ ReAssign ပြန်လုပ်တယ်
		target = target.CloneWithTitle("New Title").CloneWithMessage("New Message");
		var result = dao.save(target);
		assertEquals(1, result);
		assertEquals(target, dao.findById(1));
	}
	
	@Order(10)
	@Test
	void testSaveNull() {
		assertThrows(IllegalArgumentException.class, ()->dao.save(null));
	}
	
	@Order(11)
	@Test
	void testSaveNullTitle() {
		var Nulltarget = dao.findById(1).CloneWithTitle(null).CloneWithMessage("New Message");
		var exception = assertThrows(MessageDaoException.class, ()->dao.save(Nulltarget));
		//Lambda တို့ကိုထည့်မယ့် Variable က Final ဖြစ်ရမှာမလို့ target ကို findbyId တန်းရှာပေးလိုက်တာ
		//အပေါ်ကလို reassign ပြန်မလုပ်ပေးတော့ဘူး
		assertEquals("This is NO TITLE for this MESSAGE", exception.getMessage());
		
		var Emptytarget = dao.findById(1).CloneWithTitle("").CloneWithMessage("New Message");
	    exception = assertThrows(MessageDaoException.class, ()->dao.save(Emptytarget));
	    assertEquals("This is NO TITLE for this MESSAGE", exception.getMessage());
	}
	
	@Order(12)
	@Test
	void testSaveNullMessage() {
		var NullMessage = dao.findById(1).CloneWithTitle("New Title").CloneWithMessage(null);
	    var exception = assertThrows(MessageDaoException.class, ()->dao.save(NullMessage));
	    assertEquals("This is NO MESSAGE LETTER ", exception.getMessage());
	    
	    var EmptyMessage = dao.findById(1).CloneWithTitle("New Title").CloneWithMessage("");
	    exception = assertThrows(MessageDaoException.class, ()->dao.save(EmptyMessage));
	    assertEquals("This is NO MESSAGE LETTER ", exception.getMessage());
	}

	@Test
	@Order(13)
	void testSearchByMember() {
		var search = dao.searchByMember(message.member().email());
		assertEquals(1, search);
	}

	@Test
	@Order(14)
	void testSearchByMemberNull() {
		assertThrows(IllegalArgumentException.class, 
				()->dao.searchByMember(null));
	}
	
	@Test
	@Order(15)
	void testSearchByMemberNotFound() {
		var search = dao.searchByMember(message.member().email().concat(""));
		assertEquals(0, search);
	}
	
	@Test
	@Order(16)
	void testSearch() {
		var result = dao.search("found", "message");
		assertEquals(1, result);
	}
	
	@Test
	@Order(17)
	void testSearchWithNoArgument() {
		var result = dao.search(null, null);
		assertEquals(1, result);
	}
	
	@Test
	@Order(18)
	void testSearchWithNoMember() {
		var result = dao.search("found", "");
		assertEquals(1, result);
	}
	
	@Test
	@Order(19)
	void testSearchNoKeyword() {
		var result = dao.search("", "message");
		assertEquals(1, result);
	}
	
	@Test
	@Order(20)
	void testSearchNotFound() {
		var result = dao.search("end", "");
		assertEquals(0, result);
	}
}
