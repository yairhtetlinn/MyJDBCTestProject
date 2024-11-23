package com.jdc.statement.test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.jdc.statement.ConnectionManager;
import com.jdc.statement.DatabaseInitializer;
import com.jdc.statement.MessageDaoException;
import com.jdc.statement.dao.MemberDao;
import com.jdc.statement.dto.Member;
import com.jdc.statement.dto.Member.Role;

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

	@Test
	void testCreateMemberOk() {
		var result = dao.createMember(input);
		assertEquals(1, result);
	}
	
	@Test
	void testCreateMemberNull() {
		dao.createMember(null);
		assertThrows(IllegalArgumentException.class,
				()->dao.createMember(null));
	}
	
	@Test
	void testMemberDuplicateKey() {
		var exception = assertThrows(MessageDaoException.class,
				()-> dao.createMember(input) );
		assertEquals("Email has been already existed", exception.getMessage());
	}
	
	@Test
	void testCreateMemberNullName() {
		var NullData = new Member("testone@gmail.com", null, "Testone", LocalDate.of(2001, 9, 26), Role.Admin);
		var exception = assertThrows(MessageDaoException.class, ()->dao.createMember(NullData));
		assertEquals("Member Name must not be Null", exception.getMessage());
		
		var EmptyData = new Member("testone@gmail.com", "", "Testone", LocalDate.of(2001, 9, 26), Role.Admin);
		exception = assertThrows(MessageDaoException.class, ()->dao.createMember(EmptyData));
		assertEquals("Member Name must not be empty", exception.getMessage());
	}
	
	@Test
	void testCreateMemberNullEmail() {
		var NullData = new Member(null, "Yair Htet Linn", "Testone", LocalDate.of(2001, 9, 26), Role.Admin);
		var exception = assertThrows(MessageDaoException.class, ()->dao.createMember(NullData));
		assertEquals("Member Email must not be Null", exception.getMessage());
		
		var EmptyData = new Member("", "Yair Htet Linn", "Testone", LocalDate.of(2001, 9, 26), Role.Admin);
		exception = assertThrows(MessageDaoException.class, ()->dao.createMember(EmptyData));
		assertEquals("Member Email must not be empty", exception.getMessage());
	}
	
	@Test
	void testCreateMemberNullDob() {
		var NullData = new Member("testone@gmail.com", "Yair Htet Linn", "Testone", null , Role.Admin);
		var exception = assertThrows(MessageDaoException.class, ()->dao.createMember(NullData));
		assertEquals("Member Name must not be Null", exception.getMessage());
	}
	
	@Test
	void testCreateMemberNullPassword() {
		var NullData = new Member("testone@gmail.com", "Yari Htet Linn", null, LocalDate.of(2001, 9, 26), Role.Admin);
		var exception = assertThrows(MessageDaoException.class, ()->dao.createMember(NullData));
		assertEquals("Member Name must not be Null", exception.getMessage());
		
		var EmptyData = new Member("testone@gmail.com", "Yair Hte Linn", "", LocalDate.of(2001, 9, 26), Role.Admin);
		exception = assertThrows(MessageDaoException.class, ()->dao.createMember(EmptyData));
		assertEquals("Member Name must not be empty", exception.getMessage());
	}

	@Test
	void testFindByEmail() {
		var result = dao.findByEmail(input.email());
		assertEquals(input, result);
	}
	
	@Test
	void testFindByEmailNotFound() {
		var result = dao.findByEmail("%s1".formatted(input.email()));
		assertNull(result);
	}
	
	@Test
	void testFindByEmailNull() {
		assertThrows(IllegalArgumentException.class, ()-> dao.findByEmail(null));
	}

	@Test
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
	void testChangePasswordNotFound() {
		fail("Not yet implemented");
	}
	
	
	@Test
	void testChangePasswordNullEmail() {
		fail("Not yet implemented");
	}
	
	@Test
	void testChangePasswordNullOldPass() {
		fail("Not yet implemented");
	}
	
	@Test
	void testChangePasswordNullNewPass() {
		fail("Not yet implemented");
	}
	
	@Test
	void testChangePasswordUnmatchPass() {
		fail("Not yet implemented");
	}
	
	@Test
	void testChangePasswordSamePass() {
		fail("Not yet implemented");
	}
	

}
