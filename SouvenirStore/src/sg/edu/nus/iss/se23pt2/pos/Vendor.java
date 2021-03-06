package sg.edu.nus.iss.se23pt2.pos;

//
//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : SouvenirStore
//  @ File Name : Vendor.java
//  @ Date : 3/8/2015
//  @ Author : Nikhil Metrani
//
//
/**
 *  */
public class Vendor {

    /**
     *      */
    private String name;

    /**
     *      */
    private String description;

    /**
     * For future implementation
     */
    //public PurchaseOrder po;
    /**
     *      */
    public Vendor() {
        this(null, null);
    }

    /**
     * @param name
     * @param description
     */
    public Vendor(String name, String description) {
        this.name = name;
        this.description = description;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        if (null != name) {
            this.name = name;
        }
    }

    /**
     * @return
     */
    public String getName() {
        return this.name;
    }

    /**
     * @param description
     */
    public void setDescription(String description) {
        if (null != description) {
            this.description = description;
        }
    }

    /**
     * @return
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * For future implementation
     */
    /*public void placeOrder(PurchaseOrder po) {
     this.po = po;
     return;
     }
     */
    /**
     *
     * @return  */
    /*public PurchaseOrder dispatchOrder() {
     return this.po;
     }
     */
    @Override
    public String toString() {
        if (null == this.name && null == this.description) {
            return null;
        } else {
            if (null != this.name && null == this.description) {
                return this.name + ",";
            } else if (null == this.name && null != this.description) {
                return "," + this.description;
            }
        }
        return this.name + "," + this.description;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Vendor) {
            if (this.name.equals(((Vendor) obj).getName()) && this.description.equals(((Vendor) obj).getDescription())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Vendor clone() {
        Vendor vendor = new Vendor(this.name, this.description);
        return vendor;
    }

}
