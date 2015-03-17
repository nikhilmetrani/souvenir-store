package sg.edu.nus.iss.se23pt2.pos;

import java.util.Comparator;

/**
 * 
 * @author Rushabh Shah
 * Date    18/3/2015
 * 
 */
public class ItemSort implements Comparator<Item> {

	@Override
	public int compare(Item item1, Item item2) {
		return item1.getProduct().getId().compareTo(item2.getProduct().getId());
	}

}
