/**
 * 
 */
package sg.edu.nus.iss.se23pt2.pos;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sg.edu.nus.iss.se23pt2.pos.exception.InsufficientQuantityException;

/**
 * @author Nikhil Metrani
 *
 */
public class ProductTest {

	Category c1 = null;
	Category c2 = null;
	String c1Code = null;
	String c2Code = null;
	String c1Name = null;
	String c2Name = null;
	
	Product p1 = null;
	Product p2 = null;
	Product p3 = null;
	Product p4 = null;
	
	String p1Id = null;
	String p1Name = null;
	String p1Description = null;
	String p1Barcode = null;
	
	Float p1PriceZero = Float.parseFloat("0.00");
	Float p1Price = Float.parseFloat("10.50");
	Integer p1Quantity = 55;
	Integer p1RTQuantity = 10;
	Integer p1OrderQuantity = 20;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		c1Code = "STA";
		c2Code = "SWE";
		c1Name = "Stationary items";
		c2Name = "Assorted sweets";
		c1 = new Category(c1Code, c1Name);
		c1 = new Category(c2Code, c2Name);
		
		p1Id = "1";
		p1Name = "Fountain pen";
		p1Description = "Golden tip fountain pen";
		p1Barcode = "BARC001P001";
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		c1 = null;
		c2 = null;
	}

	@Test
	public void instantiateTest() {
		
		p1 = new Product();
		
		/* Attributes must return null or 0 on default instantiation */
		assertNull(p1.getId());
		assertNull(p1.getName());
		assertNull(p1.getDescription());
		assertNull(p1.getBarcode());
		assertEquals(0, p1.getOrderQuantity().intValue());
		assertEquals(0, p1.getPrice().intValue());
		assertEquals(0, p1.getReorderThresholdQuantity().intValue());
		assertEquals(0, p1.getQuantity().intValue());
		
		/* Attributes must return values set during instantiation */
		p1 = new Product(p1Id, p1Name);
		assertEquals(p1Id, p1.getId());
		assertEquals(p1Name, p1.getName());
		
		/* No need to re-test previously tested attributes */
		p1 = new Product(p1Id, p1Name, p1Description);
		assertEquals(p1Description, p1.getDescription());
		
		p1 = new Product(p1Id, p1Name, p1Description, 22);
		assertEquals(22, p1.getQuantity().intValue());
		
		p1 = new Product(p1Id, p1Name, p1Description, 22, 10);
		assertEquals(10, p1.getReorderThresholdQuantity().intValue());
		
		p1 = new Product(p1Id, p1Name, p1Description, 22, 10, p1Price);
		assertEquals(p1Price, p1.getPrice());
		
		p1 = new Product(p1Id, p1Name, p1Description, 22, 10, p1Price, p1Barcode);
		assertEquals(p1Barcode, p1.getBarcode());
		
		p1 = new Product(p1Id, p1Name, p1Description, 22, 10, p1Price, p1Barcode, 15);
		assertEquals(15, p1.getOrderQuantity().intValue());
		
		p1 = new Product(p1Id, p1Name, p1Description, 22, 10, p1Price, p1Barcode, 15, c1);
		assertEquals(c1, p1.getCategory());
		
		/* Ensure category code is appended as expected */
		assertEquals(c1.getCode() + "/" + p1Id, p1.getId());
		
	}
	
	@Test
	public void setGetIdTest() {
		
		/* Must return null after default instantiation */
		p1 = new Product();
		assertNull(p1.getId());
		
		/* Must return value set by setId */
		p1.setId(p1Id);
		assertEquals(p1Id, p1.getId());
		
		/* Must return Category code + "/" + value set by setId */
		p1.setCategory(c1);
		//p1.setId(p1Id);
		assertEquals( p1.getCategory().getCode() + "/" + p1Id.toString(), p1.getId());
		
		/* If category code exists in id then it must not be prepended again */
		p1.setId(p1.getCategory().getCode() + "/" + p1Id.toString());
		assertEquals( p1.getCategory().getCode() + "/" + p1Id.toString(), p1.getId());
		
		p1.setId(null);
		/* Existing value must not be set to null */
		assertNotNull(p1.getId());
		/* Existing value must be retained */
		assertEquals(p1.getCategory().getCode() + "/" + p1Id.toString(), p1.getId());
		
	}
	
	@Test
	public void setGetNameTest() {
		/* Must return null after default instantiation */
		p1 = new Product();
		assertNull(p1.getName());
		
		/* Must return value set by setName */
		p1.setName(p1Name);
		assertEquals(p1Name, p1.getName());
		
		p1.setName(null);
		/* Existing value must not be set to null */
		assertNotNull(p1.getName());
		/* Existing value must be retained */
		assertEquals(p1Name, p1.getName());
	}
	
	@Test
	public void setGetDescriptionTest() {
		/* Must return null after default instantiation */
		p1 = new Product();
		assertNull(p1.getDescription());
		
		/* Must return value set by setDescription */
		p1.setDescription(p1Description);
		assertEquals(p1Description, p1.getDescription());
		
		p1.setDescription(null);
		/* Existing value must not be set to null */
		assertNotNull(p1.getDescription());
		/* Existing value must be retained */
		assertEquals(p1Description, p1.getDescription());
	}

	@Test
	public void setGetPriceTest() {
		
		/* Must return 0 after default instantiation */
		p1 = new Product();
		assertEquals(p1PriceZero, p1.getPrice());
		
		/* Must return value set by setQuantity */
		p1.setPrice(p1Price);
		assertEquals(p1Price, p1.getPrice());
		
		p1.setPrice(null);
		/* Existing value must not be set to null */
		assertNotNull(p1.getPrice());
		/* Existing value must be retained */
		assertEquals(p1Price, p1.getPrice());
		
	}
	
	@Test
	public void setGetQuantityTest() {
		
		/* Must return 0 after default instantiation */
		p1 = new Product();
		assertEquals(0, p1.getQuantity().intValue());
		
		/* Must return value set by setQuantity */
		p1.setQuantity(p1Quantity);
		assertEquals(p1Quantity, p1.getQuantity());
		
		p1.setQuantity(null);
		/* Existing value must not be set to null */
		assertNotNull(p1.getQuantity());
		/* Existing value must be retained */
		assertEquals(p1Quantity, p1.getQuantity());
		
		Integer negativeValue = -3;
		p1.setQuantity(negativeValue);
		/* Existing value must not be set to negative value */
		assertNotEquals(negativeValue, p1.getQuantity());
		/* Existing value must be retained */
		assertEquals(p1Quantity, p1.getQuantity());
	}
	
	@Test
	public void addQuantityTest() {
		
		p1 = new Product();
		p1.setQuantity(p1Quantity);
		assertEquals(p1Quantity, p1.getQuantity());
		
		/* Must add given quantity to existing quantity */
		p1.addQuantity(p1OrderQuantity);
		assertEquals(new Integer(p1Quantity + p1OrderQuantity), p1.getQuantity());
		
		/* Must not add negative quantity, existing quantity must be retained */
		Integer negativeValue = -3;
		p1.addQuantity(negativeValue);
		assertEquals(new Integer(p1Quantity + p1OrderQuantity), p1.getQuantity());
	}
	
	@Test
	public void deductQuantityTest() {
		
		p1 = new Product();
		p1.setQuantity(p1Quantity);
		assertEquals(p1Quantity, p1.getQuantity());
		
		/* Must subtract given quantity from existing quantity */
		try {
			p1.deductQuantity(5);
			assertEquals(new Integer(50), p1.getQuantity());
		}
		catch (InsufficientQuantityException e) {
			fail("InsufficientQuantityException was not expected");
		}
		
		/* Must not deduct negative quantity, existing quantity must be retained */
		Integer negativeValue = -3;
		try {
			p1.deductQuantity(negativeValue);
			assertEquals(new Integer(50), p1.getQuantity());
		}
		catch (InsufficientQuantityException e) {
			fail("InsufficientQuantityException was not expected");
		}
		/* Must throw exception if given parameter is higher than existing quantity */
		
		try {
			p1.deductQuantity(60);
			fail("InsufficientQuantityException expected");
		}
		catch (InsufficientQuantityException e) {
			assertTrue(true);
		}
	}
	
	@Test
	public void setGetReorderThresholdQuantityTest() {
		
		/* Must return 0 after default instantiation */
		p1 = new Product();
		assertEquals(0, p1.getReorderThresholdQuantity().intValue());
		
		/* Must return value set by setQuantity */
		p1.setReorderThresholdQuantity(p1RTQuantity);
		assertEquals(p1RTQuantity, p1.getReorderThresholdQuantity());
		
		p1.setReorderThresholdQuantity(null);
		/* Existing value must not be set to null */
		assertNotNull(p1.getReorderThresholdQuantity());
		/* Existing value must be retained */
		assertEquals(p1RTQuantity, p1.getReorderThresholdQuantity());
		
		Integer negativeValue = -3;
		p1.setReorderThresholdQuantity(negativeValue);
		/* Existing value must not be set to negative value */
		assertNotEquals(negativeValue, p1.getReorderThresholdQuantity());
		/* Existing value must be retained */
		assertEquals(p1RTQuantity, p1.getReorderThresholdQuantity());
	}
	
	@Test
	public void setGetOrderQuantityTest() {
		
		/* Must return 0 after default instantiation */
		p1 = new Product();
		assertEquals(0, p1.getOrderQuantity().intValue());
		
		/* Must return value set by setQuantity */
		p1.setOrderQuantity(p1OrderQuantity);
		assertEquals(p1OrderQuantity, p1.getOrderQuantity());
		
		p1.setOrderQuantity(null);
		/* Existing value must not be set to null */
		assertNotNull(p1.getOrderQuantity());
		/* Existing value must be retained */
		assertEquals(p1OrderQuantity, p1.getOrderQuantity());
		
		Integer negativeValue = -3;
		p1.setOrderQuantity(negativeValue);
		/* Existing value must not be set to negative value */
		assertNotEquals(negativeValue, p1.getOrderQuantity());
		/* Existing value must be retained */
		assertEquals(p1OrderQuantity, p1.getOrderQuantity());
	}
	
	@Test
	public void setGetCategoryTest() {
		/* Must return null after default instantiation */
		p1 = new Product();
		assertNull(p1.getCategory());
		
		/* Must return value set by setDescription */
		p1.setCategory(c1);
		assertEquals(c1, p1.getCategory());
		
		p1.setCategory(null);
		/* Existing value must not be set to null */
		assertNotNull(p1.getCategory());
		/* Existing value must be retained */
		assertEquals(c1, p1.getCategory());
	}
	
	@Test
	public void setGetBarcodeTest() {
		/* Must return null after default instantiation */
		p1 = new Product();
		assertNull(p1.getBarcode());
		
		/* Must return value set by setDescription */
		p1.setBarcode(p1Barcode);
		assertEquals(p1Barcode, p1.getBarcode());
		
		p1.setBarcode(null);
		/* Existing value must not be set to null */
		assertNotNull(p1.getBarcode());
		/* Existing value must be retained */
		assertEquals(p1Barcode, p1.getBarcode());
	}
	
	@Test
	public void equalsTest() {
		
		/* Must return true when id matches */
		p1 = new Product(p1Id, p1Name);
		p2 = new Product(p1Id, p1Name);
		assertTrue(p1.equals(p2));
		
		/* Must return false when id does not match */
		p1 = new Product(p1Id, p1Name);
		p2 = new Product("dummy", p1Name);
		assertFalse(p1.equals(p2));
	}
	
	@Test
	public void toStringTest() {
		
		/* Must return <,,,0,0,0.0,,0> on default instantiation */
		p1 = new Product();
		assertEquals(",,,0,0,0.0,,0", p1.toString());
		
		/* Must return Id,name,description,quantity,price,barcode,reorderThresholdquantity,orderQuantity */
		p1 = new Product(p1Id, p1Name, p1Description, 22, 10, p1Price, p1Barcode, 15, c1);
		
		StringBuilder stb = new StringBuilder();
		stb = stb.append(c1.getCode() + "/" + p1Id + ",")
				.append(p1Name).append(",")
				.append(p1Description).append(",")
				.append(22).append(",")
				.append(10).append(",")
				.append(p1Price).append(",")
				.append(p1Barcode).append(",")
				.append(15);
		
		assertEquals(stb.toString(), p1.toString());
		
		/* Null attributes must be replaced by empty string */
		p1 = new Product(p1Id, p1Name, null, 22, 10, p1Price, null, 15, c1);
		
		stb = new StringBuilder();
		stb = stb.append(c1.getCode() + "/" + p1Id + ",")
				.append(p1Name).append(",")
				.append(",") //Description is null
				.append(22).append(",")
				.append(10).append(",")
				.append(p1Price).append(",")
				.append(",") //Barcode is null
				.append(15);
		
		assertEquals(stb.toString(), p1.toString());
		
		
	}
}
