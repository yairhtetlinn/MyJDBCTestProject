package com.jdc.statement.test;

/*import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.jdc.statement.ConnectionManager;
import com.jdc.statement.DatabaseInitializer;
import com.jdc.statement.dao.MessageDao;
import com.jdc.statement.dto.Message;

*/

//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MessageDaoTest {
	
/*	MessageDao dao;
	Message message = new Message("Yair Htet Linn", "Hi");


	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		DatabaseInitializer.truncate("message");
	}
	
	@BeforeEach
	void setUpBefore() {
		dao= new MessageDao(ConnectionManager.getInstance());
	}

	@Test
	@Order(1)
	void testCreateMesssage() {
		var result = dao.createMessage(message);
		assertEquals(1, result.id());
	}
	
	@Test
	@Order(2)
	void testFindByIdFound() {
		var result = dao.findById(1);
		assertNotNull(result);
		
		assertEquals(message.title(), result.title());
		assertEquals(message.message(), result.message());
		assertNotNull(result.postAt());
	}
	
	@Test
	@Order(3)
	void testFindByIdNotFound() {
		var result = dao.findById(2);
		assertNull(result);
	}
	
	@Test
	@Order(4)
	void testUpdate() {
		
		var title = "New Yair Htet Linn";
		var message = "New Hi";
			
		var count = dao.update(1,title,message);
		assertEquals(1, count); // ငါလုပ်တဲ့ Test သာမှန်ရင် Database ထဲမှာ နံပါတ် 1 နဲ့ စတဲ့ Database အကြောင်းက တစ်ကြောင်းသွားရှိလိမ့်မယ်
								// အဲ့လိုလုပ်လို့ ဝင်သွားတဲ့ 1 နဲ့ Database အကြောင်းက Database ထဲမှာ တစ်ကြောင်းပဲရှိရမှာ ဖြစ်လို့ သူ့ကို 1 နဲ့ ညီတာ
								// assertEqual ရဲ့ 1 က ငါတို့ထည့်လိုက်တဲ့ Database အကြောင်းအရေအတွက်ကို ေဖာ်ပြတာ
		
		var result = dao.findById(1);
			
		assertEquals(title, result.title());
		assertEquals(message, result.message());
		assertNotNull(result.postAt());
	}
	
	@Test
	@Order(5)
	void testUpdateNotFound() {
		
		var title = "New Yair Htet Linn";
		var message = "New Hi";
		
		var count = dao.update(2,title,message);
		assertEquals(0, count);// Database ထဲမှာ နံပါတ် 2 နဲ့ ဝင်နေတဲ့ အကြောင်းအရေအတွက် က တစ်ခုမှမရှိရမှာမလို့ သူက 0 နဲ့ ညီရတာ
		
		
	}
	
	@Test
	@Order(6)
	void deleteFound() {
		int count = dao.deleteById(1);
		assertEquals(1, count);
	}
	
	@Test
	@Order(7)
	void deleteNotFound() {
		int count = dao.deleteById(1);
		assertEquals(0, count);
	}
	
	@Test
	@Order(8)
	void testCreateMessage() {
		var messages = List.of(
				new Message("Title 1", "message 1"),
				new Message("Title 2", "message 2"),
				new Message("Title 3","message 3")
				);
		
		var list = dao.createMessages(messages);
		
		assertEquals(2, list.get(0).id());
		assertEquals(3, list.get(1).id());
		assertEquals(4, list.get(2).id());
	}
	
	@Test
	@Order(9)
	void testCreateMessageEmpty() {
		var messages = new ArrayList<Message>(); //  ArrayList ကို ဒီလိုရေးထားရင် အခွံကြီး သူထဲမှာ ဘာ Data မှ
		//မရှိဘူး ။ သူ့ထဲကို Data ထည့်ပေးချင်ရင် အောက်မှာ Method တွေဆက်ရေးပေးဖို့လိုမယ်။ တို့က ဒီတစ်ခါ List ထဲမှာ ဘာမှမပါရင် 
		//ဘာဖြစ်မလဲ သိချင်လို့ အလွတ်ကြီးထည့်ပေးလိုက်တာ
		
		var list = dao.createMessages(messages);
		
		assertTrue(list.isEmpty());
	}
	
	@Test
	@Order(10)
	void testCreateMessageNull() {
		var list = dao.createMessages(null);
		
		assertTrue(list.isEmpty());
	}
*/
}