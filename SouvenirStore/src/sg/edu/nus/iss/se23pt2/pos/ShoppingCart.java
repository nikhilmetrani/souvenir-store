package sg.edu.nus.iss.se23pt2.pos;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

//
//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : SouvenirStore
//  @ File Name : ShoppingCart.java
//  @ Date : 3/8/2015
//  @ Author : Nikhil Metrani
//	@ Author: Niu Yiming (added methods: getHighestDiscount, calcDiscPmt, calcFinalPmt)
//

public class ShoppingCart {

    private ArrayList<Item> items;
    private ArrayList<Discount> discounts;
    private Discount discount;
    private Customer customer;
    private int points;
    private String date;
    private double totalPriceBeforeDisc = 0.0;
    private List<Item> finalItemList = null;
    
    public ShoppingCart() {
		this.items = new ArrayList<Item>();
		this.customer = new Customer();
		this.points = 0;
		this.discount = null;
		//TO-DO
		//this.date = 
    }
	
    public void setItems(ArrayList<Item> items) {
		this.items = items;
	}

	public ArrayList<Item> getItems() {
		return this.items;
	}

    public Discount getDiscount() {
		return discount;
	}

	public void setDiscount(Discount discount) {
		this.discount = discount;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}


    public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public String getDate(){
		return this.date;
	}
	
	public void setDate(String date) {
		this.date = date;
	}
	
    public Item addToCart(Product p, int quantity) {
		//TO-DO
		Item item = new Item();
		return item;
    }
    
    public Item removeFromCart(Product p, int quantity) {
		//TO-DO
		Item item = new Item();
		return item;
    }
    
    
    public void setMember(Member m) {
		this.customer = m;
    }
    
	public Member getMember() {
		//TO-DO - type cast customer to member
		return (Member)this.customer;
    }
	
    
    // Get the highest discount based on customer type and shopping date
    private double getHighestDiscount(Customer cust, ArrayList<Discount> discounts
    		, String transDate) throws ParseException {
    	
    	Discount disc;
    	double highestDesc = 0;
    	// Members are eligible for all discounts
    	if (cust instanceof Member) {
    		for(int i = 1; i <= discounts.size(); i++) {
    			disc = discounts.get(i);
    			
    			if(disc.isValid(disc, transDate)) {
    				if(disc.getDiscPct() > highestDesc) highestDesc = disc.getDiscPct();
    				else continue;
    			}
    		}
    		return highestDesc;
    	} else {
    		for(int i = 1; i <= discounts.size(); i++) {
    			// Public are only eligible for discounts that applicable to all customers
    			if(discounts.get(i).getApplicableTo().equals('A')) {
    				disc = discounts.get(i);
    				
    				if(disc.isValid(disc, transDate)) {
        				if(disc.getDiscPct() > highestDesc) highestDesc = disc.getDiscPct();
        				else continue;
        			}
    			}
    		}
    		return highestDesc;
    	}
    }
    
    //returns total price of the items after final check out 
    public Double getTotalPriceBeforeDiscount() {
    	finalItemList = new ArrayList<Item>();
    	//iterate the list of items to fetch the total price of the items
    	for(Item item : finalItemList){
    		if(item != null){
    			totalPriceBeforeDisc = totalPriceBeforeDisc+item.getPrice();
    		}
    	}
    	return totalPriceBeforeDisc;
    }

    /*******************************************************Added by Yiming***********************************************************************/
    
    // To calculate the discounted payment amount
    public double getTotalPriceAfterDiscount(Customer cust, double totalPriceBeforeDisc
    		, ArrayList<Discount> discounts) throws ParseException {
    	double finalDiscount = this.getHighestDiscount(cust, discounts, this.date);
    	return totalPriceBeforeDisc*(100-finalDiscount)/100;
    }
    
    // To calculate the final payment based on member's decision if or not redeeming loyalty points
    // This method is open only to members. For public customers the discount amount is considered final
    public double calcFinalPmt(Member mem, double discPmt
    		, boolean isRedeemLoyaltyPoints, int pointsRedeemed) {
    	if (isRedeemLoyaltyPoints) {
    		if (mem.getLoyaltyPoints() >= pointsRedeemed) {
    			double newLoyaltyPoints = discPmt-pointsRedeemed;
    			
    			//Update the member's loyalty points
    			mem.deductLoyaltyPoints(pointsRedeemed);
    			
    			System.out.println("Member "+mem.getName()+": "+pointsRedeemed+" Loyalty Points have been redeemed!");
    			System.out.println("Member "+mem.getName()+": "+newLoyaltyPoints+" Loyalty Points remained!");
    			//TO-DO: Display the same message on UI.
    			return newLoyaltyPoints;
    		} else {
    			System.out.println("Member: "+mem.getName()+"'s Loyalty Points not enough for redemption!");
    			//TO-DO: Display the same message on UI.
    			return discPmt;
    		}
    	} else {
    		return discPmt;
    	}
    }
    /*******************************************************Added by Yiming***********************************************************************/
    
    /********************************************************Added by Debasish********************************************************************/
    
    // Calculate the discounted payment based on customer type
    private double getPayableAmount(Customer customer,double totalPriceAfterDisc,boolean isRedeemable,int pointsRedeemed) {
    	double finalAmountToBePaid = totalPriceAfterDisc;
    	//if Member wants to redeem the points then this block of code will be executed to calculate the final amount
    	if(customer != null && customer instanceof Member){
			Member member = getMember();
			//fetch the loyality points for the member
			int currentLoyalityPoints = member.getLoyaltyPoints();
			//check if the member wants to redeem the loyality points or not
			if(isRedeemable){
				//Current loyality point should be more than the points the customer wants to redeeem
				if (currentLoyalityPoints >= pointsRedeemed) {
	    			finalAmountToBePaid = calculateNewPoints(totalPriceAfterDisc,pointsRedeemed,currentLoyalityPoints,member);
	    			return finalAmountToBePaid;
				}else{
					return totalPriceAfterDisc;
				}
			}else{
				return totalPriceAfterDisc;
			}
		}else{
			return totalPriceAfterDisc;
		}
    }
		
    
    private double calculateNewPoints(Double price,int pointsRedeemed,int currentLoyalityPoints,Member member){
    	/*if clause is applicable when discounted amount(say 50.5) is equal to or more than the points the user wants to redeem(45) with current loyality 
    	 * points of the user is 70. In such case, updated loyality points = loyality points left[points related to final price - redeemed] + (current loyality points - points redeemed)= (50.5-45)+(70-45)
    	 * 
    	 * else clause  is applicable when discounted amount(say 50.5) is less than the points the user wants to redeem(55) with current loyality 
    	 * points of the user is 70. In such case, updated loyality points = current loyality points + points related to final price (50.5+70)
    	*/
    	double updatedLoyalityPoints = 0.0;
    	double finalAmountToBePaid = 0.0;
    	if(price.intValue() >= pointsRedeemed){
			finalAmountToBePaid = price - pointsRedeemed;
			member.deductLoyaltyPoints(pointsRedeemed);
			updatedLoyalityPoints = member.getLoyaltyPoints()+finalAmountToBePaid;
			member.addLoyaltyPoints((int)Math.round(updatedLoyalityPoints));
			return finalAmountToBePaid;
		}else{
			updatedLoyalityPoints = price+currentLoyalityPoints;
			member.addLoyaltyPoints((int)Math.round(updatedLoyalityPoints));
			finalAmountToBePaid = price;
			return finalAmountToBePaid;
		}
    }
    
    /********************************************************Added by Debasish********************************************************************/
    public Transaction confirmTransaction() {
		Transaction t = new Transaction();
		//TO-DO
		return t;
    }
}
