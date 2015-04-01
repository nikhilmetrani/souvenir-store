package sg.edu.nus.iss.se23pt2.pos;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class StoreKeeperTest {

	StoreKeeper storeKeeper1 = null;
	StoreKeeper storeKeeper2 = null;
	String sk1Name = null;
	String sk2Name = null;
	String sk1Password = null;
	String sk2Password = null;
	String sk3Password = null;	
	
	@Before
	public void setUp() throws Exception {
		sk1Name = "Sangaran";
		sk2Name = "Thayalan";
		sk1Password = "234567";
		sk2Password = "987654";
		sk3Password = "345678";
	}
	
	
	@After
	public void tearDown() throws Exception {		
	}
	
	
	@Test
	public void instantiateTest() {
		/* Instantiating the object without values. Return Null */
		storeKeeper1 = new StoreKeeper();
		assertNull(storeKeeper1.getName());
		assertNull(storeKeeper1.getPassword());
		
		/* Instantiating the object with values */
		storeKeeper1 = new StoreKeeper(sk1Name,sk1Password);
		assertEquals(storeKeeper1.getName(),sk1Name);
		assertEquals(storeKeeper1.getPassword(),sk1Password);
	}
	
	
	@Test
	public void setGetNameTest() {
		/* Object without name parameter. Return Null */
		storeKeeper1 = new StoreKeeper();
		assertNull(storeKeeper1.getName());
		
		/* Setting the name for the object */
		storeKeeper1.setName(sk1Name);
		assertEquals(storeKeeper1.getName(),sk1Name);
		
		/* Should not set the null value to Name. Return previous value */
		storeKeeper1.setName(null);
		assertNotSame(storeKeeper1.getName(),null);
	}
	
	
	@Test
	public void setGetPasswordTest() {
		/* Object without password parameter. Return Null */
		storeKeeper1 = new StoreKeeper();
		assertNull(storeKeeper1.getPassword());
		
		/* Setting the password for the object */
		storeKeeper1.setPassword(sk1Password);
		assertEquals(storeKeeper1.getPassword(),sk1Password);
		
		/* Should not set the null value to password. Return previous value */
		storeKeeper1.setPassword(null);
		assertNotSame(storeKeeper1.getPassword(),null);
	}
	
	
	@Test
	public void toStringTest() {
		/* Object without name and password. Return Null */
		storeKeeper1 = new StoreKeeper();
		assertNull(storeKeeper1.toString());	
		
		/* Setting name and password to object */
		storeKeeper1.setName(sk1Name);
		storeKeeper1.setPassword(sk1Password);
		assertEquals(storeKeeper1.toString(), sk1Name + "," + sk1Password);		
		
		/* Instantiating the object with name and password */
		storeKeeper1 = new StoreKeeper(sk1Name, sk1Password);
		assertEquals(storeKeeper1.toString(), sk1Name + "," + sk1Password);		
	}
	
	
	@Test
	public void equalsTest() {		
		/* Return true when name matches but password does not match */
		storeKeeper1 = new StoreKeeper(sk1Name, sk1Password);
		storeKeeper2 = new StoreKeeper(sk1Name, sk2Password);
		assertTrue(storeKeeper1.equals(storeKeeper2));		
		
		/* Return false when name does not matches but password match */
		storeKeeper1 = new StoreKeeper(sk1Name, sk1Password);
		storeKeeper2 = new StoreKeeper(sk2Name, sk1Password);
		assertFalse(storeKeeper1.equals(storeKeeper2));		
		
		/* Return true when both name and password match */
		storeKeeper1 = new StoreKeeper(sk1Name, sk1Password);
		storeKeeper2 = new StoreKeeper(sk1Name, sk1Password);
		assertTrue(storeKeeper1.equals(storeKeeper2));
	}
	
	
	@Test
	public void validatePasswordTest() {
		/* Return true when new password match with the existing password */
		storeKeeper1 = new StoreKeeper(sk1Name, sk1Password);
		assertTrue(storeKeeper1.validatePassword(sk1Password));
		
		/* Return false when new password does not match with the existing password */
		storeKeeper1 = new StoreKeeper(sk1Name, sk1Password);
		assertFalse(storeKeeper1.validatePassword(sk2Password));
	}
	
	
	@Test
	public void isPasswordValidTest() {
		/* Return true when current password matches and new password matches with the confirm password */
		storeKeeper1 = new StoreKeeper(sk1Name, sk1Password);
		assertTrue(storeKeeper1.isPasswordValid(sk1Password, sk2Password, sk2Password));
		
		/* Return false when current password matches and new password does not match with the confirm password */
		storeKeeper1 = new StoreKeeper(sk1Name, sk1Password);
		assertFalse(storeKeeper1.isPasswordValid(sk1Password, sk2Password, sk1Password));
		
		/* Return false when current password does not match and new password matches with the confirm password */
		storeKeeper1 = new StoreKeeper(sk1Name, sk1Password);
		assertFalse(storeKeeper1.isPasswordValid(sk2Password, sk3Password, sk3Password));
		
		/* Return false when current password does not match and new password does not match with the confirm password */
		storeKeeper1 = new StoreKeeper(sk1Name, sk1Password);
		assertFalse(storeKeeper1.isPasswordValid(sk2Password, sk3Password, sk1Password));
	}
}
