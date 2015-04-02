/**
 *
 */
package sg.edu.nus.iss.se23pt2.pos;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Debasish Routaray
 *
 */
public class ShoppingCartTest extends TestCase{

	private ArrayList<Item> items = null;
    private ArrayList<Discount> discounts = null;
    private ArrayList<Discount> discounts1 = null;
    private ArrayList<Discount> discounts2= null;
    private ArrayList<Discount> discounts3 = null;
    private ArrayList<Discount> discounts4 = null;
    private ArrayList<Discount> memberDiscountList = null;
    private ArrayList<Discount> memberDiscountList1 = null;
    private ArrayList<Discount> memberDiscountList2 = null;
    private ArrayList<Discount> memberDiscountList3 = null;
    private ArrayList<Discount> memberDiscountList4 = null;
    private Discount discount1,discount2,discount3,discount4,discount5,discount6,discount7,discount8,discount9,discount10,discount11,discount12
    ,discount13,discount14,discount15,discount16,discount17,discount18,discount19,discount20= null;
    private Customer cust,cust1,cust2,cust3,cust4,member,member1,member2,member3,member4 = null;
    private Item item;
 	private SimpleDateFormat dateFormat;
 	private ShoppingCart shoppingCart,shoppingCart1,shoppingCart2,shoppingCart3,shoppingCart4,shoppingCart5,shoppingCart6,shoppingCart7,shoppingCart8,shoppingCart9;
 	private SouvenirStore souvenirStore,souvenirStore1,souvenirStore2,souvenirStore3,souvenirStore4,souvenirStore5,souvenirStore6,souvenirStore7,souvenirStore8,souvenirStore9 = null;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {

		items = new ArrayList<Item>();
	    discounts = new ArrayList<Discount>();
	    //discount = new Discount();
	    //private int points = 0;
	    //private String date = null;
	    //private double totalPriceBeforeDisc = 0.0;
	    //private List<Item> finalItemList = null;

	    dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		item = new Item();
		item.setPrice(20.5f);
		Product product = new Product();
		product.setId("MUG/2");
		item.setProduct(product);
		item.setQuantity(5);

		Item item1 = new Item();
		item1.setPrice(10.15f);
		Product product1= new Product();
		product1.setId("LED/1");
		item1.setProduct(product1);
		item1.setQuantity(25);
		ArrayList<Item> items = new ArrayList<Item>();
		items.add(item);
		items.add(item1);
		shoppingCart = new ShoppingCart();
	    shoppingCart.setItems(items);
//Test Maximum discount for public when disount start date is future date i.e. transaction date > start date > discount end date
//discount set for public for disc tc1
	    cust = new Customer();
		//cust.setId("Fkldjf234");
	    shoppingCart.setCustomer(cust);
	    shoppingCart.setDate("2015-03-24");
	    souvenirStore = new SouvenirStore();
        discount1 = new Discount("Discount1", "Non-member Discount", "2015-07-07", "120", 15, "A");
        discount2 = new Discount("Discount2", "Non-member Discount", "2015-01-01", "240", 10.5, "A");
        discounts = new ArrayList<Discount>();
        /*Map<String, Discount> discountMap = new HashMap<String, Discount>();
        discountMap.put("1", discount1);
        discountMap.put("2", discount2);*/
        discounts.add(discount1);
        discounts.add(discount2);
        souvenirStore.setDiscountList(discounts);

        //Test Maximum discount for member when disount start date is future date i.e. transaction date > start date > discount end date
        //setting discount for member for disc tc2
        member = new Member();
        member.setId("NUS STUD");
        shoppingCart1 = new ShoppingCart();
        shoppingCart1.setDate("2015-03-24");
        memberDiscountList = new ArrayList<Discount>();
        souvenirStore1 = new SouvenirStore();
        discount3 = new MemberDiscount("Discount3", "Member Discount", "2015-07-07", "120", 30, "M");
        discount4 = new MemberDiscount("Discount4", "Member Discount", "2015-01-01", "240", 20.5, "M");
        memberDiscountList.add(discount3);
        memberDiscountList.add(discount4);
        shoppingCart1.setCustomer(member);
        souvenirStore1.setDiscountList(memberDiscountList);

       //Test Maximum discount for public when discount start date > transaction date > discount end date
      //discount set for public for disc tc3
	    cust1 = new Customer();
		//cust.setId("Fkldjf234");
	    shoppingCart2 = new ShoppingCart();
	    shoppingCart2.setCustomer(cust1);
	    shoppingCart2.setDate("2015-03-24");
	    souvenirStore2 = new SouvenirStore();
        discount5 = new Discount("Discount5", "Non-member Discount", "2014-12-12", "180", 15, "A");
        discount6 = new Discount("Discount6", "Non-member Discount", "2015-01-01", "240", 10.5, "A");
        discounts1 = new ArrayList<Discount>();
        discounts1.add(discount5);
        discounts1.add(discount6);
        souvenirStore2.setDiscountList(discounts1);

        Item item2 = new Item();
		item2.setPrice(20.0f);
		Product product2 = new Product();
		product2.setId("MUG/2");
		item2.setProduct(product2);
		item2.setQuantity(5);

		Item item3 = new Item();
		item3.setPrice(10.0f);
		Product product3= new Product();
		product3.setId("LED/1");
		item3.setProduct(product3);
		item3.setQuantity(20);
		ArrayList<Item> items2 = new ArrayList<Item>();
		items2.add(item2);
		items2.add(item3);
		shoppingCart2.setItems(items2);
        //Test Maximum discount for member when discount start date > transaction date > discount end date
        //setting discount for member for disc tc4
        Member member1 = new Member();
        member1.setRedeemable(false);
        member1.setId("NUS Staff");
        shoppingCart3 = new ShoppingCart();
        shoppingCart3.setDate("2015-03-24");
        memberDiscountList1 = new ArrayList<Discount>();
        souvenirStore3 = new SouvenirStore();
        discount7 = new MemberDiscount("Discount7", "Member Discount", "2014-12-12", "180", 30, "M");
        discount8 = new MemberDiscount("Discount8", "Member Discount", "2015-01-01", "240", 20.5, "M");
        memberDiscountList1.add(discount7);
        memberDiscountList1.add(discount8);
        shoppingCart3.setCustomer(member1);
        souvenirStore3.setDiscountList(memberDiscountList1);
        shoppingCart3.setItems(items2);

//      Test Maximum discount for public when discount start date > discount end date > transaction date
        //discount set for public for disc tc5
	    cust2 = new Customer();
		//cust.setId("Fkldjf234");
	    shoppingCart4 = new ShoppingCart();
	    shoppingCart4.setCustomer(cust2);
	    shoppingCart4.setDate("2015-03-24");
	    souvenirStore4 = new SouvenirStore();
        discount9 = new Discount("Discount9", "Non-member Discount", "2014-12-12", "10", 25, "A");
        discount10 = new Discount("Discount10", "Non-member Discount", "2015-01-01", "210", 10.5, "A");
        discounts2 = new ArrayList<Discount>();
        discounts2.add(discount9);
        discounts2.add(discount10);
        souvenirStore4.setDiscountList(discounts2);


// Test Maximum discount for member when discount start date > discount end date > transaction date
        //discount set for member for disc tc6

        member2 = new Member();
        member2.setId("NUS Staff");
        shoppingCart5 = new ShoppingCart();
        shoppingCart5.setDate("2015-03-24");
        memberDiscountList2 = new ArrayList<Discount>();
        souvenirStore5 = new SouvenirStore();
        discount11 = new MemberDiscount("Discount11", "Member Discount", "2014-12-12", "10", 30, "M");
        discount12 = new MemberDiscount("Discount12", "Member Discount", "2015-01-01", "240", 20.5, "M");
        memberDiscountList2.add(discount11);
        memberDiscountList2.add(discount12);
        shoppingCart5.setCustomer(member2);
        souvenirStore5.setDiscountList(memberDiscountList2);


 // 7. Test Maximum discount for public when discount start date > transaction date and period in days is -ve
        cust3 = new Customer();
		//cust.setId("Fkldjf234");
	    shoppingCart6 = new ShoppingCart();
	    shoppingCart6.setCustomer(cust3);
	    shoppingCart6.setDate("2015-03-24");
	    souvenirStore6 = new SouvenirStore();
        discount13 = new Discount("Discount13", "Non-member Discount", "2014-12-12", "-10", 25, "A");
        discount14 = new Discount("Discount14", "Non-member Discount", "2015-05-05", "-10", 10, "A");
        discounts3 = new ArrayList<Discount>();
        discounts3.add(discount13);
        discounts3.add(discount14);
        souvenirStore6.setDiscountList(discounts3);
        shoppingCart6.setItems(items2);


    //8. Test Maximum discount for member when discount start date > transaction date and period in days is -ve
        member3 = new Member();
        member3.setId("NUS Staff");
        shoppingCart7 = new ShoppingCart();
        shoppingCart7.setDate("2015-03-24");
        memberDiscountList3 = new ArrayList<Discount>();
        souvenirStore7 = new SouvenirStore();
        discount15 = new MemberDiscount("Discount15", "Member Discount", "2014-12-12", "-10", 30, "M");
        discount16 = new MemberDiscount("Discount16", "Member Discount", "2015-01-01", "-240", 20.5, "M");
        memberDiscountList3.add(discount15);
        memberDiscountList2.add(discount16);
        shoppingCart7.setCustomer(member3);
        shoppingCart7.setItems(items2);
        souvenirStore7.setDiscountList(memberDiscountList3);


//9. Test Maximum discount for public when discount transaction date > start date  and period in days is -ve
        cust4 = new Customer();
  		//cust.setId("Fkldjf234");
  	    shoppingCart8 = new ShoppingCart();
  	    shoppingCart8.setCustomer(cust4);
  	    shoppingCart8.setDate("2015-03-24");
  	    souvenirStore8 = new SouvenirStore();
        discount17 = new Discount("Discount17", "Non-member Discount", "2015-04-06", "-20", 25, "A");
        discount18 = new Discount("Discount18", "Non-member Discount", "2015-03-05", "-10", 10, "A");
	    discounts4 = new ArrayList<Discount>();
	    discounts4.add(discount17);
	    discounts4.add(discount18);
	    souvenirStore8.setDiscountList(discounts4);

//10. Test Maximum discount for member when discount transaction date > start date and period in days is -ve
	    member4 = new Member();
        member4.setId("NUS Staff");
        shoppingCart9 = new ShoppingCart();
        shoppingCart9.setDate("2015-03-24");
        memberDiscountList4 = new ArrayList<Discount>();
        souvenirStore9 = new SouvenirStore();
        discount19 = new MemberDiscount("Discount19", "Member Discount", "2015-04-06", "-20", 30, "M");
        discount20 = new MemberDiscount("Discount20", "Member Discount", "2015-03-05", "-10", 20.5, "M");
        memberDiscountList4.add(discount19);
        memberDiscountList4.add(discount20);
        shoppingCart9.setCustomer(member4);
        souvenirStore9.setDiscountList(memberDiscountList4);



	    /*transaction1.setDate("2013-09-28");
		transaction1.setItems(items);
		transaction1.setId(21389);
		transaction1.setCustomer(cust);


		transaction2 = new Transaction();
		*/
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		/*v1 = null;
		v2 = null;
		v3 = null;*/
	}

	@Test
	public void testTotalPriceBeforeDiscount() {
		Double totalPriceOfItems = (double) (20.5f*5)+(double)(10.15f*25);
		Double totalPriceBeforeDiscount = shoppingCart.getTotalPriceBeforeDiscount(shoppingCart.getItems());
		assertNotSame(totalPriceBeforeDiscount,0.0);
		assertEquals(totalPriceOfItems, totalPriceBeforeDiscount);
	}

	@Test
	public void testMaximumNonMemberDiscountWithBoundaryConditions() {
		//dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String transDate = shoppingCart.getDate();
		Double expectedDiscount = 10.5;
		Double maxDiscount = 0.0;
		maxDiscount = shoppingCart.getHighestDiscount(souvenirStore.getDiscountList());
		//should be 10.5
		assertEquals(expectedDiscount, maxDiscount);
		expectedDiscount = 15.0;
		transDate = shoppingCart2.getDate();
		maxDiscount = shoppingCart2.getHighestDiscount(souvenirStore2.getDiscountList());
		//should be 15.0
		assertEquals(expectedDiscount, maxDiscount);
		expectedDiscount = 10.5;
		transDate = shoppingCart4.getDate();
		maxDiscount = shoppingCart4.getHighestDiscount(souvenirStore4.getDiscountList());
		//should be 10.5
		assertEquals(expectedDiscount, maxDiscount);
		expectedDiscount = 0.0;
		transDate = shoppingCart6.getDate();
		maxDiscount = shoppingCart6.getHighestDiscount(souvenirStore6.getDiscountList());
		//should be 0.0
		assertEquals(expectedDiscount, maxDiscount);
		expectedDiscount = 0.0;
		transDate = shoppingCart8.getDate();
		maxDiscount = shoppingCart8.getHighestDiscount(souvenirStore8.getDiscountList());
		//should be 0.0
		assertEquals(expectedDiscount, maxDiscount);
	}
	
	
	@Test
	public void testMaximumMemberDiscountWithBoundaryConditions() {

		    //dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String transDate = shoppingCart1.getDate();
			Double expectedDiscount = 20.5;
			Double maxDiscount = 0.0;
			maxDiscount = shoppingCart1.getHighestDiscount(souvenirStore1.getDiscountList());
			//should be 20.5
			assertEquals(expectedDiscount, maxDiscount);
			expectedDiscount = 30.0;
			transDate = shoppingCart3.getDate();
			maxDiscount = shoppingCart3.getHighestDiscount(souvenirStore3.getDiscountList());
			//should be 30.0
			assertEquals(expectedDiscount, maxDiscount);
			expectedDiscount = 20.5;
			transDate = shoppingCart5.getDate();
			maxDiscount = shoppingCart5.getHighestDiscount(souvenirStore5.getDiscountList());
			//should be 20.5
			assertEquals(expectedDiscount, maxDiscount);
			expectedDiscount = 0.0;
			transDate = shoppingCart7.getDate();
			maxDiscount = shoppingCart7.getHighestDiscount(souvenirStore7.getDiscountList());
			//should be 0.0
			assertEquals(expectedDiscount, maxDiscount);
			expectedDiscount = 0.0;
			transDate = shoppingCart9.getDate();
			maxDiscount = shoppingCart9.getHighestDiscount(souvenirStore9.getDiscountList());
			//should be 0.0
			assertEquals(expectedDiscount, maxDiscount);

	}


	@Test
	public void testTotalPriceAfterDiscountForPublic() {
		Double totalPriceOfItems = (double) (20.0f*5)+(double)(10.0f*20);
		Double totalPriceBeforeDiscount = shoppingCart2.getTotalPriceBeforeDiscount(shoppingCart2.getItems());
		assertNotSame(totalPriceBeforeDiscount,0.0);
		assertEquals(totalPriceOfItems, totalPriceBeforeDiscount);
		String transDate = shoppingCart2.getDate();
		Double totalPriceAfterDiscount = 0.0;
		Double totalPriceAfterDiscount1 = 255.0;
		try {
			//12.final discount is +ve and customer is not a member

			totalPriceAfterDiscount =shoppingCart2.getTotalPriceAfterDiscount(shoppingCart2.getCustomer(), totalPriceBeforeDiscount
		    		, souvenirStore2.getDiscountList(),transDate);
			assertNotSame(totalPriceBeforeDiscount, totalPriceAfterDiscount);
			assertNotSame(0.0,totalPriceAfterDiscount);
			assertEquals(totalPriceAfterDiscount1, totalPriceAfterDiscount);


			//11.final discount is 0.0 and customer is not a member
			totalPriceAfterDiscount =shoppingCart6.getTotalPriceAfterDiscount(shoppingCart6.getCustomer(), totalPriceBeforeDiscount
		    		, souvenirStore6.getDiscountList(),transDate);
			assertEquals(totalPriceBeforeDiscount, totalPriceAfterDiscount);
			assertNotSame(0.0,totalPriceAfterDiscount);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		@Test
		public void testTotalPriceAfterDiscountForMember() {
			Double totalPriceOfItems = (double) (20.0f*5)+(double)(10.0f*20);
			Double totalPriceBeforeDiscount = shoppingCart3.getTotalPriceBeforeDiscount(shoppingCart3.getItems());
			assertNotSame(totalPriceBeforeDiscount,0.0);
			assertEquals(totalPriceOfItems, totalPriceBeforeDiscount);
			String transDate = shoppingCart3.getDate();
			Double totalPriceAfterDiscount = 0.0;
			Double totalPriceAfterDiscount1 = 210.0;
			try {
				//14.final discount is +ve and customer is a member
				totalPriceAfterDiscount =shoppingCart3.getTotalPriceAfterDiscount(shoppingCart3.getCustomer(), totalPriceBeforeDiscount
			    		, souvenirStore3.getDiscountList(),transDate);
				assertNotSame(totalPriceBeforeDiscount, totalPriceAfterDiscount);
				assertNotSame(0.0,totalPriceAfterDiscount);
				assertEquals(totalPriceAfterDiscount1, totalPriceAfterDiscount);

				//13.final discount is 0.0 and customer is a member
				totalPriceAfterDiscount =shoppingCart6.getTotalPriceAfterDiscount(shoppingCart6.getCustomer(), totalPriceBeforeDiscount
			    		, souvenirStore6.getDiscountList(),transDate);
				assertEquals(totalPriceBeforeDiscount, totalPriceAfterDiscount);
				assertNotSame(0.0,totalPriceAfterDiscount);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	}

		@Test
		public void testPayableAmountAfterDiscountForPublic() {
			Double totalPriceOfItems = (double) (20.0f*5)+(double)(10.0f*20);
			Double totalPriceBeforeDiscount = shoppingCart2.getTotalPriceBeforeDiscount(shoppingCart2.getItems());
			assertNotSame(totalPriceBeforeDiscount,0.0);
			assertEquals(totalPriceOfItems, totalPriceBeforeDiscount);
			String transDate = shoppingCart2.getDate();
			Double totalPriceAfterDiscount = 0.0;
			Double totalPriceAfterDiscount1 = 255.0;
			try {
				totalPriceAfterDiscount =shoppingCart2.getTotalPriceAfterDiscount(shoppingCart2.getCustomer(), totalPriceBeforeDiscount
			    		, souvenirStore2.getDiscountList(),transDate);
				assertNotSame(totalPriceBeforeDiscount, totalPriceAfterDiscount);
				assertNotSame(0.0,totalPriceAfterDiscount);
				assertEquals(totalPriceAfterDiscount1, totalPriceAfterDiscount);

				//15.Customer is a not a member and does not have loyality points,finalAmountToBePaid is same as totalPriceAfterDisc
				Double payableAmount = shoppingCart2.getPayableAmount(shoppingCart2.getCustomer(), totalPriceAfterDiscount, false, 0);
				assertEquals(totalPriceAfterDiscount, payableAmount);
				assertNotSame(0.0,totalPriceAfterDiscount);

				//16.Customer is a not a member and pass some loyality points,finalAmountToBePaid is same as totalPriceAfterDisc
				payableAmount = shoppingCart2.getPayableAmount(shoppingCart2.getCustomer(), totalPriceAfterDiscount, false, 120);
				assertEquals(totalPriceAfterDiscount, payableAmount);
				assertNotSame(0.0,totalPriceAfterDiscount);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		@Test
		public void testPayableAmountAfterDiscountForMember() {
			Double totalPriceOfItems = (double) (20.0f*5)+(double)(10.0f*20);
			Double totalPriceBeforeDiscount = shoppingCart3.getTotalPriceBeforeDiscount(shoppingCart2.getItems());
			assertNotSame(totalPriceBeforeDiscount,0.0);
			assertEquals(totalPriceOfItems, totalPriceBeforeDiscount);
			String transDate = shoppingCart3.getDate();
			Double totalPriceAfterDiscount = 0.0;
			Double totalPriceAfterDiscount1 = 210.0;
			try {
				
				totalPriceAfterDiscount =shoppingCart3.getTotalPriceAfterDiscount(shoppingCart3.getCustomer(), totalPriceBeforeDiscount
			    		, souvenirStore3.getDiscountList(),transDate);
				assertNotSame(totalPriceBeforeDiscount, totalPriceAfterDiscount);
				assertNotSame(0.0,totalPriceAfterDiscount);
				assertEquals(totalPriceAfterDiscount1, totalPriceAfterDiscount);
				Member member = (Member)shoppingCart3.getCustomer();
				//17.Customer is a member and does not want to redeem loyality points,finalAmountToBePaid is same as totalPriceAfterDisc
				//and points to redeemed <= totalPriceAfterDisc
				
				Double payableAmount = shoppingCart3.calcFinalPmt(member, totalPriceAfterDiscount, 0);
				assertEquals(totalPriceAfterDiscount, payableAmount);
				assertNotSame(0.0,payableAmount);

				//18.Customer is a a member and wants to redeem some loyality points(say 100),finalAmountToBePaid should be adjusted with totalPriceAfterDisc
				//and points to redeemed <= totalPriceAfterDisc

				Double payableAmount1 = 208.0;
				member.setRedeemable(true);
				member.setLoyaltyPoints(41);
				payableAmount = shoppingCart3.calcFinalPmt(member, totalPriceAfterDiscount, 40);
				assertEquals(payableAmount1, payableAmount);
				assertNotSame(0.0,payableAmount);

				//19.Customer is a member and wants to redeem whole loyality points.finalAmountToBePaid should be adjusted with totalPriceAfterDisc
				//points to redeemed <= totalPriceAfterDisc

				payableAmount1 = 209.0;
				member.setRedeemable(true);
				member.setLoyaltyPoints(20);
				payableAmount = shoppingCart3.calcFinalPmt(member, totalPriceAfterDiscount, member.getLoyaltyPoints());
				assertEquals(payableAmount1, payableAmount);

			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
