package sg.edu.nus.iss.se23pt2.pos;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MemberDiscount extends Discount{

    public MemberDiscount(String discountCode, String description, String startDate, String periodInDays, double discountPercentage, String applicableTo)
    {
        super(discountCode, description, startDate, periodInDays, discountPercentage, applicableTo);
    }
    
    /**
	 * Nikhil Metrani
	 * Override equals() implementation
	 * */
    @Override
	public boolean equals(Object obj) {
		if(obj instanceof MemberDiscount){
			MemberDiscount discount = (MemberDiscount) obj;
            return discount.getDiscCode().equals(this.getDiscCode());
        }
		return false;
	}

    @Override
    public Boolean isForMemberOnly()
    {
        return true;
    }
    
    public boolean isMemberFirstDiscount(){
        return "MEMBER_FIRST".equals(this.getDiscCode());
    }

    @Override
    public boolean isValid(Customer customer, String transDate) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        if(!(customer instanceof Member))
            return false;

        if(this.isMemberFirstDiscount() && !((Member)customer).isFirstPurchase())
            return false;

        if("ALWAYS".equals(this.getStartDate()) && "ALWAYS".equals(this.getPeriodInDays()))
            return true;
        else {
            Date startDate = df.parse(this.getStartDate());
            if("ALWAYS".equals(this.getPeriodInDays()))
            	return true;
            else{
            	Date endDate = new Date((startDate.getTime()+Long.parseLong(this.getPeriodInDays())*86400000));
            	if (df.parse(transDate).after(startDate) && df.parse(transDate).before(endDate)) {
            		return true;
            	}
            }
        }
        return false;
    }
}
