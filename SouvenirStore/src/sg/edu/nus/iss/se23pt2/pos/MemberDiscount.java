package sg.edu.nus.iss.se23pt2.pos;

public class MemberDiscount extends Discount{

    public MemberDiscount(String discountCode, String description, String startDate, String periodInDays, Integer discountPercentage, String applicableTo)
    {
        super(discountCode, description, startDate, periodInDays, discountPercentage, applicableTo);
    }
}
