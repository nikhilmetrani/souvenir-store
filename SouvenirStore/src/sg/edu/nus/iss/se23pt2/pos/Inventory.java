package sg.edu.nus.iss.se23pt2.pos;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import javax.swing.JOptionPane;
import sg.edu.nus.iss.se23pt2.pos.datastore.DataStoreFactory;
import sg.edu.nus.iss.se23pt2.pos.exception.InvalidCategoryCodeException;
import sg.edu.nus.iss.se23pt2.pos.exception.UpdateFailedException;
import sg.edu.nus.iss.se23pt2.pos.gui.ProductOrderPanel;

public class Inventory {

    private Map<String, Category> categories;
    private Map<String, Product> products;
    private Map<String, ArrayList<Vendor>> vendors;
    private Map<String, Discount> discounts;

    public Inventory() {
        this(null, null, null, null);
    }

    public Inventory(Map<String, Product> products, Map<String, Category> categories, Map<String, ArrayList<Vendor>> vendors, Map<String, Discount> discounts) {
        this.products = products;
        this.categories = categories;
        this.vendors = vendors;
        this.discounts = discounts;
    }

    public void replinishProducts(ArrayList<Product> products) {
        if (null == products) {
            return;
        }

        Iterator<Product> ip = products.iterator();
        Product p;
        while (ip.hasNext()) {
            p = this.getProduct(ip.next().getId());
            p.addQuantity(p.getOrderQuantity());
            DataStoreFactory dsFactory = DataStoreFactory.getInstance();
            try {
                dsFactory.getProductDS().update(p);
            } catch (UpdateFailedException | IOException ufe) {
                JOptionPane.showMessageDialog(null,
                        "Error :: " + ufe.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }

    }

    public List<Product> getProducts() {
        if (null == this.products) {
            return new ArrayList<>();
        }
        ArrayList<Product> p = new ArrayList();
        Iterator<Product> ip = this.products.values().iterator();
        while (ip.hasNext()) {
            p.add(ip.next());
        }
        return p;
    }

    public List<Product> getProducts(String catId) {
        if (null == this.products) {
            return new ArrayList<>();
        }
        ArrayList<Product> p = new ArrayList();
        Iterator<Product> ip = this.products.values().iterator();
        Product prod;
        while (ip.hasNext()) {
            prod = ip.next();
            if (catId.toLowerCase().equals(prod.getCategoryCode().toLowerCase())) {
                p.add(prod);
            }
        }
        return p;
    }

    public List<Product> getProductsBelowThresholdQuantity() {
        if (null == this.products) {
            return new ArrayList<>();
        }
        ArrayList<Product> p = new ArrayList();
        Iterator<Product> ip = this.products.values().iterator();
        Product prod;
        while (ip.hasNext()) {
            prod = ip.next();
            if (prod.getQuantity() < prod.getReorderThresholdQuantity()) {
                p.add(prod);
            }
        }
        return p;
    }

    public List<Product> getProductsBelowThresholdQuantity(String catId) {
        if (null == this.products) {
            return new ArrayList<>();
        }
        ArrayList<Product> p = new ArrayList();
        Iterator<Product> ip = this.products.values().iterator();
        Product prod;
        while (ip.hasNext()) {
            prod = ip.next();
            if (catId.toLowerCase().equals(prod.getCategoryCode().toLowerCase())) {
                if (prod.getQuantity() < prod.getReorderThresholdQuantity()) {
                    p.add(prod);
                }
            }
        }
        return p;
    }

    public Product getProduct(String prodId) {
        if (this.products != null) {
            for (Product product : this.products.values()) {
                if (product.getId().equals(prodId)) {
                    return product;
                }
            }
        }
        return null;
    }

    public Product addProduct(Product product) {
        if (this.products.containsKey(product.getId())) {
            return this.products.get(product.getId());
        }
        this.products.put(product.getId(), product);
        return product;
    }

    public boolean removeProduct(String prodId) {
        if (this.products.containsKey(prodId)) {
            this.products.remove(prodId);
            return true;
        }
        return false;
    }

    public PurchaseOrder generatePurchaseOrder() {
        PurchaseOrder pr = new PurchaseOrder();
        return pr;
    }

    public PurchaseOrder generatePurchaseOrder(String catId) {
        PurchaseOrder pr = new PurchaseOrder();
        return pr;
    }

    public List<Category> getCategories() {
        if (null == this.categories) {
            return null;
        }
        ArrayList<Category> c = new ArrayList();
        Iterator<Category> ic = this.categories.values().iterator();
        while (ic.hasNext()) {
            c.add(ic.next());
        }
        return c;
    }

    public Category getCategory(String code)  throws InvalidCategoryCodeException {
        if (this.categories.containsKey(code))
            return this.categories.get(code);
        throw new InvalidCategoryCodeException("The given category" + code + "does not exist");
    }
    
    public Category addCategory(String catId, String name) throws InvalidCategoryCodeException {

        Category cat = new Category(catId, name);
        return this.addCategory(cat);
    }

    public Category addCategory(Category category) {
        if (this.categories.containsKey(category.getCode())) {
            return this.categories.get(category.getCode());
        }
        this.categories.put(category.getCode(), category);
        return category;
    }

    public boolean removeCategory(String catId) {
        if (this.categories.containsKey(catId)) {
            this.categories.remove(catId);
            return true;
        }
        return false;
    }

    public List<Discount> getDiscounts() {
        if (null == this.discounts) {
            return null;
        }
        ArrayList<Discount> d = new ArrayList<Discount>();
        Iterator<Discount> id = this.discounts.values().iterator();
        while (id.hasNext()) {
            d.add(id.next());
        }
        return d;
    }

    public Discount addDisc(String discCode, String discDesc, String startDate, String periodInDays, double discPct, String appTo) {
        Discount disc = new Discount(discCode, discDesc, startDate, periodInDays, discPct, appTo);
        return this.addDisc(disc);
    }

    public Discount addDisc(Discount disc) {
        if (this.discounts.containsKey(disc.getDiscCode())) {
            return this.discounts.get(disc.getDiscCode());
        }
        this.discounts.put(disc.getDiscCode(), disc);
        return disc;
    }

    public boolean removeDisc(String discCode) {
        if (this.discounts.containsKey(discCode)) {
            this.discounts.remove(discCode);
            return true;
        }
        return false;
    }

    public ArrayList<Vendor> getVendors(String categoryCode) {
        if (null == this.vendors) {
            return null;
        }

        if (this.vendors.containsKey(categoryCode)) {
            return this.vendors.get(categoryCode);
        } else {
            return null;
        }
    }

    public boolean addVendor(String categoryCode, Vendor vendor) {
        if (this.vendors.containsKey(categoryCode)) {
            ArrayList<Vendor> vendorsForCategory = vendors.get(categoryCode);
            if (isExsitingVendor(vendorsForCategory, vendor)) {
                return false;
            } else {
                vendorsForCategory.add(vendor);
            }
        } else {
            ArrayList<Vendor> vendorsForCategory = new ArrayList<>();
            vendorsForCategory.add(vendor);
            vendors.put(categoryCode, vendorsForCategory);
        }
        return false;
    }

    private boolean isExsitingVendor(ArrayList<Vendor> vendorsForCategory, Vendor vendor) {
        boolean isExisted = false;
        for (int i = 0; i < vendorsForCategory.size(); i++) {
            String nameInList = vendorsForCategory.get(i).getName().toUpperCase();
            String nameToBeRemoved = vendor.getName().toUpperCase();
            if (nameInList.equals(nameToBeRemoved)) {
                isExisted = true;
                break;
            }
        }
        return isExisted;
    }

    public boolean removeVendor(String categoryCode, Vendor vendor) {
        ArrayList<Vendor> vendorsForCategory = vendors.get(categoryCode);
        for (int i = 0; i < vendorsForCategory.size(); i++) {
            String nameInList = vendorsForCategory.get(i).getName().toUpperCase();
            String nameToBeRemoved = vendor.getName().toUpperCase();
            if (nameInList.equals(nameToBeRemoved)) {
                vendorsForCategory.remove(i);
                return true;
            }
        }
        return false;
    }
}
