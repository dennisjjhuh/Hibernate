package chap01.crud;

import static org.junit.Assert.*;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Test;

import util.HibernateUtil;

public class MemberTest {
	
	private static final String NAME = "DENNIS";
	private static final String MESSAGE = "HELLO";
	SessionFactory factory = HibernateUtil.getSessionFactory();
	
	@Test
	public void test() {

		Member member = new Member(NAME, MESSAGE);
		
		//Create Api
		insert(member);
		
		//Read Api
		Member selectedMember = selectById(1);
		assertEquals(MESSAGE, selectedMember.getMessage());
		
		//Update Api
		selectedMember.setMessage("HELLO HIBERNATE");
		update(selectedMember);	
		Member updatedMember = selectById(1);
		assertEquals("HELLO HIBERNATE", updatedMember.getMessage());
		
		//Delete Api
		delete(updatedMember);
		Member deletedMember = selectById(1);
		assertNull(deletedMember);
	}
	
	public void delete(Member member) {
		System.out.println("Data deleted in Hibernate DB.");
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		session.delete(member);
		session.getTransaction().commit();
	}

	public void update(Member member) {
		System.out.println("Data updated in Hibernate DB.");
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		session.update(member);
		session.getTransaction().commit();
	}

	private Member selectById(int id) {
		System.out.println("Data read in Hibernate DB.");		
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		Member selectedMember = (Member) session.get(Member.class, id);
		session.getTransaction().commit();
		
		return selectedMember;
	}

	public void insert(Member member) {
		System.out.println("Data inserted in Hibernate DB.");		
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		session.save(member);
		session.getTransaction().commit();
	}

}
