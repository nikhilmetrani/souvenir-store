/**
 * 
 */
package sg.edu.nus.iss.se23pt2.pos;

//import sg.edu.nus.iss.se23pt2.pos.*;
import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Nikhil Metrani
 *
 */
public class VendorTest extends TestCase{

	String v1Name = null;
	String v2Name = null;
	String v3Name = null;
	String v1Description = null;
	String v2Description = null;
	String v3Description = null;
	Vendor v1 = null;
	Vendor v2 = null;
	Vendor v3 = null;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		
		v1Name = "Vendor 1";
		v2Name = "Vendor 2";
		v3Name = "Vendor 3";
		v1Description = "Vendor 1 description";
		v2Description = "Vendor 2 description";
		v3Description = "Vendor 3 description";
		v3 = new Vendor(v3Name, v3Description);
		
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		v1 = null;
		v2 = null;
		v3 = null;
	}

	@Test
	public void testInstantiateTest() {
		
		/** Return null of instantiate */
		v1 = new Vendor();
		assertNull(v1.getName());
		assertNull(v1.getDescription());
		
		/** Return parameters provided during instantiation */
		v2 = new Vendor(v2Name, v2Description);
		assertEquals(v2.getName(), v2Name);
		assertEquals(v2.getDescription(), v2Description);
	}
	
	@Test
	public void testSetGetNameTest() {
		
		/** Return null of instantiate */
		v1 = new Vendor();
		assertNull(v1.getName());
		
		/** Must set string value to Name attribute  */
		v1.setName(v1Name);
		assertEquals(v1.getName(), v1Name);
		
		/** Must not set null name attribute */
		v1.setName(null);
		assertNotSame(v1.getName(), null);
		
		/** Must return previously set value*/
		assertEquals(v1.getName(), v1Name);
	}
	
	@Test
	public void testSetGetDescriptionTest() {
		
		/** Return null of instantiate */
		v1 = new Vendor();
		assertNull(v1.getDescription());
		
		/** Must set string value to Name attribute  */
		v1.setDescription(v1Description);
		assertEquals(v1.getDescription(), v1Description);
		
		/** Must not set null description attribute */
		v1.setDescription(null);
		assertNotSame(v1.getDescription(), null);
		
		/** Must return previously set value*/
		assertEquals(v1.getDescription(), v1Description);
	}
	
	@Test
	public void testToStringTest() {
		/** Return null of instantiate */
		v1 = new Vendor();
		assertNull(v1.toString());
	
		/** Must return name and description separated by comma */
		v1.setName(v1Name);
		v1.setDescription(v1Description);
		assertEquals(v1.toString(), v1Name + "," + v1Description);
		
		/** Must return name and description provided during instantiation separated by comma */
		v1 = new Vendor(v1Name, v1Description);
		assertEquals(v1.toString(), v1Name + "," + v1Description);
		
		/** Must return name+comma */
		v1 = new Vendor();
		v1.setName(v1Name);
		assertEquals(v1.toString(), v1Name + ",");
		
		/** Must return comma+description */
		v1 = new Vendor();
		v1.setDescription(v1Description);
		assertEquals(v1.toString(), "," + v1Description);
	}
	
	@Test
	public void testEqualsTest() {
		
		/** Must return false when description does not match */
		v1 = new Vendor(v1Name, v1Description);
		v2 = new Vendor(v1Name, v2Description);
		assertFalse(v1.equals(v2));
		
		/** Must return false when name does not match */
		v1 = new Vendor(v1Name, v1Description);
		v2 = new Vendor(v2Name, v1Description);
		assertFalse(v1.equals(v2));
		
		/** Must return true when both name and description not match */
		v1 = new Vendor(v1Name, v1Description);
		v2 = new Vendor(v1Name, v1Description);
		assertTrue(v1.equals(v2));
	}
	
	@Test
	public void testCloneTest() {
		
		v1 = new Vendor(v1Name, v1Description);
		
		/* Must return copy of self */
		v2 = v1.clone();
		assertEquals(v1, v2);
		assertTrue(v1.equals(v2));
		
		/* Modification of clone must not affect original object */
		v2.setName(v2Name);
		assertNotSame(v1, v2);
		assertFalse(v1.equals(v2));
	}
}
