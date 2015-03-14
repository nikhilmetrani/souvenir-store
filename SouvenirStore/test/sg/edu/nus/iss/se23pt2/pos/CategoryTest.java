/**
 * 
 */
package sg.edu.nus.iss.se23pt2.pos;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sg.edu.nus.iss.se23pt2.pos.exception.InvalidVendorException;
import sg.edu.nus.iss.se23pt2.pos.exception.VendorExistsException;

/**
 * @author Nikhil Metrani
 *
 */
public class CategoryTest {

	String v1Name = null;
	String v2Name = null;
	String v3Name = null;
	String v1Description = null;
	String v2Description = null;
	String v3Description = null;
	Vendor v1 = null;
	Vendor v2 = null;
	Vendor v3 = null;
	Category c1 = null;
	Category c2 = null;
	String c1Code = null;
	String c2Code = null;
	String c1Name = null;
	String c2Name = null;
	
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
		v1 = new Vendor(v1Name, v1Description);
		v2 = new Vendor(v2Name, v2Description);
		v3 = new Vendor(v3Name, v3Description);
		c1Code = "STA";
		c2Code = "SWE";
		c1Name = "Stationary items";
		c2Name = "Assorted sweets";
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
	public void instantiateTest() {
		
		/* Attributes return null on default instantiate */
		c1 = new Category();
		assertNull(c1.getCode());
		assertNull(c1.getName());
		assertNull(c1.getAllVendors());
		
		c1 = new Category(c1Code, c1Name);
		assertEquals(c1.getCode(), c1Code);
		assertEquals(c1.getName(), c1Name);
		assertNull(c1.getAllVendors());
	}
	
	@Test
	public void setGetCodeTest() {
		
		/* Return null of instantiate */
		c1 = new Category();
		assertNull(c1.getCode());
		
		/* Must set string value to Name attribute */
		c1.setCode(c1Code);
		assertEquals(c1Code, c1.getCode());
		
		/* Must not set null name attribute */
		c1.setCode(null);
		assertNotEquals(c1.getCode(), null);
		
		/* Must return previously set value */
		assertEquals(c1Code, c1.getCode());
		
	}
	
	@Test
	public void setGetNameTest() {
		
		/* Return null of instantiate */
		c1 = new Category();
		assertNull(c1.getName());
		
		/* Must set string value to Name attribute  */
		c1.setName(c1Name);
		assertEquals(c1.getName(), c1Name);
		
		/* Must not set null name attribute */
		c1.setName(null);
		assertNotEquals(c1.getName(), null);
		
		/* Must return previously set value*/
		assertEquals(c1.getName(), c1Name);
		
	}
	
	@Test
	public void toStringTest() {
		/* Return null of instantiate */
		c1 = new Category();
		assertNull(c1.toString());
	
		/* Must return name and description separated by comma */
		c1.setCode(c1Code);
		c1.setName(c1Name);
		assertEquals(c1.toString(), c1Code + "," + c1Name);
		
		/* Must return code and name provided during instantiation separated by comma */
		c1 = new Category(c1Code, c1Name);
		assertEquals(c1.toString(), c1Code + "," + c1Name);
		
		/* Must return code+comma */
		c1 = new Category();
		c1.setCode(c1Code);
		assertEquals(c1.toString(), c1Code + ",");
		
		/* Must return comma+name */
		c1 = new Category();
		c1.setName(c1Name);
		assertEquals(c1.toString(), "," + c1Name);
	}
	
	@Test
	public void equalsTest() {
		
		/* Must return false when name does not match */
		c1 = new Category(c1Code, c1Name);
		c2 = new Category(c1Code, c2Name);
		assertFalse(c1.equals(c2));
		
		/* Must return false when code does not match */
		c1 = new Category(c1Code, c1Name);
		c2 = new Category(c2Code, c1Name);
		assertFalse(c1.equals(c2));
		
		/* Must return true when both code and name match */
		c1 = new Category(c1Code, c1Name);
		c2 = new Category(c1Code, c1Name);
		assertTrue(c1.equals(c2));
	}
	
	@Test
	public void addVendorTest() {
		
		/* Must return vendor that was added to the list */
		c1 = new Category(c1Code, c1Name);
		Vendor vx = null, vy = null;
		try {
			vx = c1.addVendor(v1);
			assertTrue(true);
		}
		catch (VendorExistsException e) {
			fail("Must not fail to add new vendor");
		}
		assertTrue(vx.equals(v1));
		
		vy = new Vendor(v2Name, v2Description); 
		try {
			vx = c1.addVendor(v2Name, v2Description);
			assertTrue(true);
		}
		catch (VendorExistsException e) {
			fail("Must not fail to add new vendor");
		}
		assertTrue(vy.equals(vx));
		
		//* Must throw error when vendor already exists */
		c1 = new Category(c1Code, c1Name);
		try {
			vx = c1.addVendor(v1);
			try {
				vy = c1.addVendor(v1);
				fail("Must throw exception when trying to add existing Vendor");
			}
			catch (VendorExistsException e){
				assertTrue(true);
			}
		}
		catch (VendorExistsException e) {
			fail("Must not fail to add new vendor");
		}
	}
	
	@Test
	public void getVendorTest() {
		c1 = new Category();
		
		/* Must throw InvalidVendorException on instantiate */
		
		try {
			c1.getVendor(v1Name);
			fail("Must throw InvalidVendorException exception");
		}
		catch (InvalidVendorException e) {
			assertTrue(true);
		}
		
		/* Must throw IndexOutOfBoundsException on instantiate */
		try {
			c1.getVendor(2);
			fail("Must throw IndexOutOfBoundsException exception");
		}
		catch (IndexOutOfBoundsException e) {
			assertTrue(true);
		}
		
		/* Must return matching vendor */
		try {
			c1.addVendor(v1);
			c1.addVendor(v2);
			c1.addVendor(v3);
			assertTrue(true);
		}
		catch (VendorExistsException e) {
			fail("Must not fail to add new vendor");
		}
		
		Vendor vx = null;
		
		try {
			vx = c1.getVendor(0);
		}
		catch (IndexOutOfBoundsException e) {
			fail("Must not throw exception");
		}
		assertEquals(vx, v1);
		
		vx = null;
		try {
			vx = c1.getVendor(v2.getName());
		}
		catch (InvalidVendorException e) {
			fail("Must not throw exception");
		}
		assertEquals(vx, v2);
	}
	
	@Test
	public void removeVendorTest() {
		c1 = new Category();
		
		try {
			c1.addVendor(v1);
			c1.addVendor(v2);
			c1.addVendor(v3);
			assertTrue(true);
		}
		catch (VendorExistsException e) {
			fail("Must not fail to add new vendor");
		}
		
		/* Must throw IndexOutOfBoundsException exception */
		try {
			c1.removeVendor(5);
			fail("IndexOutOfBoundsException is expected");
		}
		catch (IndexOutOfBoundsException e) {
			assertTrue(true);
		}
		
		Vendor vx = null;
		
		/* Must remove given item from list */
		try {
			vx = c1.removeVendor(0);
			
			assertEquals(v1, vx);
			assertTrue(v1.equals(vx));
		}
		catch (IndexOutOfBoundsException e) {
			fail("Must not fail to remove vendor");
		}
		
		/* Must throw InvalidVendorException */
		try {
			c1.removeVendor("RandomName");
			fail("InvalidVendorException is expected");
		}
		catch (InvalidVendorException e) {
			assertTrue(true);
		}
		
		/* Must remove given item from list */
		try {
			vx = c1.removeVendor(1);
			
			assertEquals(v3, vx);
			assertTrue(v3.equals(vx));
		}
		catch (IndexOutOfBoundsException e) {
			fail("Must not fail to remove vendor");
		}
	}
	
	@Test
	public void getAllVendorsTest() {
		
		/* Must return null on instantiate */
		c1 = new Category();
		assertNull(c1.getAllVendors());
		
		ArrayList<Vendor> vendors = null;
		try {
			c1.addVendor(v1Name, v1Description);
			c1.addVendor(v2Name, v2Description);
			c1.addVendor(v3Name, v3Description);
			
			vendors = c1.getAllVendors();
			
			/* Must not return null after adding vendors */
			assertNotNull(vendors);
			
			/* size of vendors must be equal to the number of vendors added */
			assertEquals(3, vendors.size());
		}
		catch (VendorExistsException e) {
			fail("Must not fail to add new vendor");
		}
		
		/* Modifying  vendors returned by getAllVendors must not affect the vendors attribute*/
		vendors.get(0).setName(v2Name);
		vendors.get(0).setDescription(v2Description);
		
		assertFalse(c1.getVendor(0).equals(vendors.get(0)));
		
	}
}
