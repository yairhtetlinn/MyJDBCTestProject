package com.jdc.statement.test;

import static org.junit.jupiter.api.Assertions.*;

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
import com.jdc.statement.dao.MemberDao;
import com.jdc.statement.dto.Member;
import com.jdc.statement.dto.Member.Role;

@TestMethodOrder(OrderAnnotation.class)
class MemberDaoTest {
	
	MemberDao dao;
	static Member input; //input ကို ခေါ်မယ့် Class က static ဖြစ်နေလို့ static ပြောင်းပေးတာ

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		DatabaseInitializer.truncate("message","member");
// foreign key ချိတ်ထားတဲ့ Table တွေကို ဖျက်ရင် Foreign Key အလုပ်ခံရတဲ့ Table ကို မဖျက်ပဲ Foreign Key လှမ်းလုပ်တဲ့ Table
// ကို အရင်ဖျက်ရမယ် ။ အခု message က member ကို references လှမ်းလုပ်ထားမလို့ message ကို အရင်ဖျက်တာ
		input = new Member("yairhtetlinn@gmail.com", "YairHtetLinn", "Yair123", LocalDate.of(2001, 9, 26), Role.Admin);
	}

	@BeforeEach 
	void setUp() throws Exception {
		dao = new MemberDao(ConnectionManager.getInstance());
	}

	@Order(1)
	@Test
	void testCreateMemberOk() {
		var result = dao.createMember(input);
		assertEquals(1, result);
	}
	
	@Test
	@Order(2)
	void testCreateMemberNull() {
		dao.createMember(null);
		assertThrows(IllegalArgumentException.class,
				()->dao.createMember(null));
	}
	
	@Test
	@Order(3)
	void testMemberDuplicateKey() {
		var exception = assertThrows(MessageDaoException.class,
				()-> dao.createMember(input) );
		assertEquals("Email has been already existed", exception.getMessage());
	}
	
	@Test
	@Order(4)
	void testCreateMemberNullName() {
		var NullData = new Member("testone@gmail.com", null, "Testone", LocalDate.of(2001, 9, 26), Role.Admin);
		var exception = assertThrows(MessageDaoException.class, ()->dao.createMember(NullData));
		assertEquals("Member Name must not be Null", exception.getMessage());
		
		var EmptyData = new Member("testone@gmail.com", "", "Testone", LocalDate.of(2001, 9, 26), Role.Admin);
		exception = assertThrows(MessageDaoException.class, ()->dao.createMember(EmptyData));
		assertEquals("Member Name must not be empty", exception.getMessage());
	}
	
	@Test
	@Order(5)
	void testCreateMemberNullEmail() {
		var NullData = new Member(null, "Yair Htet Linn", "Testone", LocalDate.of(2001, 9, 26), Role.Admin);
		var exception = assertThrows(MessageDaoException.class, ()->dao.createMember(NullData));
		assertEquals("Member Email must not be Null", exception.getMessage());
		
		var EmptyData = new Member("", "Yair Htet Linn", "Testone", LocalDate.of(2001, 9, 26), Role.Admin);
		exception = assertThrows(MessageDaoException.class, ()->dao.createMember(EmptyData));
		assertEquals("Member Email must not be empty", exception.getMessage());
	}
	
	@Test
	@Order(6)
	void testCreateMemberNullDob() {
		var NullData = new Member("testone@gmail.com", "Yair Htet Linn", "Testone", null , Role.Admin);
		var exception = assertThrows(MessageDaoException.class, ()->dao.createMember(NullData));
		assertEquals("Member Name must not be Null", exception.getMessage());
	}
	
	@Test
	@Order(7)
	void testCreateMemberNullPassword() {
		var NullData = new Member("testone@gmail.com", "Yari Htet Linn", null, LocalDate.of(2001, 9, 26), Role.Admin);
		var exception = assertThrows(MessageDaoException.class, ()->dao.createMember(NullData));
		assertEquals("Member Name must not be Null", exception.getMessage());
		
		var EmptyData = new Member("testone@gmail.com", "Yair Hte Linn", "", LocalDate.of(2001, 9, 26), Role.Admin);
		exception = assertThrows(MessageDaoException.class, ()->dao.createMember(EmptyData));
		assertEquals("Member Name must not be empty", exception.getMessage());
	}

	@Test
	@Order(8)
	void testFindByEmail() {
		var result = dao.findByEmail(input.email());
		assertEquals(input, result);
	}
	
	@Test
	@Order(9)
	void testFindByEmailNotFound() {
		var result = dao.findByEmail("%s1".formatted(input.email()));
		assertNull(result);
	}
	
	@Test
	@Order(10)
	void testFindByEmailNull() {
		assertThrows(IllegalArgumentException.class, ()-> dao.findByEmail(null));
	}

	@Test
	@Order(11)
	void testChangePassword() {
		var NewPass = "NewPassword";
		var result = dao.changePassword(input.email(), input.password(), NewPass);
		assertEquals(1, result);
		
		var member = dao.findByEmail(input.email());
		assertEquals(NewPass, member.password());
		assertEquals(input.name(), member.name());
		assertEquals(input.dob(), member.dob());
		assertEquals(input.role(), member.role());
	}
	
	@Test
	@Order(12)
	void testChangePasswordNotFound() {
		var exception = assertThrows(MessageDaoException.class, ()-> dao.changePassword("%s".formatted(input.email()), input.password(), "NewPassword"));
		assertEquals("Please Check Email", exception.getMessage());
	}
	
	
	@Test
	@Order(13)
	void testChangePasswordNullEmail() {
		var exception = assertThrows(MessageDaoException.class, ()-> dao.changePassword(null, "NewPassword", "NewPass"));
		assertEquals("Email Must Not Be NULL", exception.getMessage());
		exception = assertThrows(MessageDaoException.class, ()-> dao.changePassword("","NewPassword", "NewPass"));
		assertEquals("Email Must Not Be EMPTY", exception.getMessage());
	}
	
	@Test
	@Order(14)
	void testChangePasswordNullOldPass() {
		var exception = assertThrows(MessageDaoException.class, ()-> dao.changePassword(input.email(), null, "NewPassword"));
		assertEquals("Old Password Must Not Be NULL", exception.getMessage());
		exception = assertThrows(MessageDaoException.class, ()-> dao.changePassword(input.email(), "", "NewPassword"));
		assertEquals("Old Password Must Not Be EMPTY", exception.getMessage());
	}
	
	@Test
	@Order(15)
	void testChangePasswordNullNewPass() {
		var exception = assertThrows(MessageDaoException.class, ()-> dao.changePassword(input.email(), "NewPassword", null));
		assertEquals("New Password Must Not Be NULL", exception.getMessage());
		exception = assertThrows(MessageDaoException.class, ()-> dao.changePassword(input.email(), "NewPassword", ""));
		assertEquals("New Password Must Not Be EMPTY", exception.getMessage());
	}
	
	@Test
	@Order(16)
	void testChangePasswordUnmatchPass() {
		var exception = assertThrows(MessageDaoException.class, ()-> dao.changePassword(input.email(), input.password(), "NewPass"));
		assertEquals("Please check OLDPASSWORD ", exception.getMessage());
	}
	
	@Test
	@Order(17)
	void testChangePasswordSamePass() {
		var exception = assertThrows(MessageDaoException.class, ()-> dao.changePassword(input.email(), "NewPassword", "NewPassword"));
		assertEquals("The Psswords are SAME", exception.getMessage());
	}
	

}
