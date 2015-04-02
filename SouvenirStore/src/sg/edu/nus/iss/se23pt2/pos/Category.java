package sg.edu.nus.iss.se23pt2.pos;

import java.util.ArrayList;
import java.util.Iterator;
import sg.edu.nus.iss.se23pt2.pos.exception.*;

//
//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : SouvenirStore
//  @ File Name : Category.java
//  @ Date : 3/8/2015
//  @ Author : Nikhil Metrani
//
//
/**
 *  */
public class Category {

    /**
     *      */
    private String code = null;

    /**
     *      */
    private String name = null;

    /**
     *      */
    public ArrayList<Vendor> vendors;

    /**
     *
     * @throws sg.edu.nus.iss.se23pt2.pos.exception.InvalidCategoryCodeException */
    public Category()  throws InvalidCategoryCodeException {
        this(null, null);
    }

    /**
     * @param code
     * @param name
     * @throws sg.edu.nus.iss.se23pt2.pos.exception.InvalidCategoryCodeException
     */
    public Category(String code, String name)  throws InvalidCategoryCodeException {
        this.setCode(code);
        this.name = name;
        this.vendors = null;
    }

    public void setCode(String code) throws InvalidCategoryCodeException {
        if (null != code) {
            if (3 == code.length() && (!code.contains(" "))) {
                this.code = code.toUpperCase();
            }
            else {
                throw new InvalidCategoryCodeException("The category code " + code + " is invalid.\nCategory"
                        + " code must be\n1. Three characters in length\n"
                        + "2. Must not contain white spaces");
            }
        }
    }

    /**
     * @return
     */
    public String getCode() {
        return this.code;
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
     * @param vendor
     * @throws sg.edu.nus.iss.se23pt2.pos.exception.VendorExistsException
     */
    public Vendor addVendor(Vendor vendor) throws VendorExistsException {
        if (null != vendor) {
            if (null == this.vendors) {
                this.vendors = new ArrayList();
            }

            if (this.vendors.contains(vendor)) {
                throw new VendorExistsException("The vendor: " + vendor.toString() + " already exists");
            } else {
                this.vendors.add(vendor);
                return this.vendors.get(this.vendors.size() - 1);
            }
        }
        return null;
    }

    /**
     *      */
    public Vendor addVendor(String name, String description) throws VendorExistsException {
        if ((null != name) && (null != description)) {
            if (null == this.vendors) {
                this.vendors = new ArrayList<Vendor>();
            }
            Vendor vendor = new Vendor(name, description);
            if (this.vendors.contains(vendor)) {
                throw new VendorExistsException("The vendor: " + vendor.toString() + " already exists");
            } else {
                this.vendors.add(vendor);
                return this.vendors.get(this.vendors.size() - 1);
            }
        }
        return null;
    }

    /**
     *      */
    public Vendor getVendor(String name) throws InvalidVendorException {
        if (null != this.vendors) {
            if (null != name) {
                Iterator<Vendor> iterator = this.vendors.iterator();
                Vendor vendor = null;
                while (iterator.hasNext()) {
                    vendor = iterator.next();
                    if (name == vendor.getName()) {
                        return vendor;
                    }
                }
            }
        }
        throw new InvalidVendorException("Given vendor: " + name + " does not exist");
    }

    /**
     *      */
    public Vendor getVendor(Integer index) throws IndexOutOfBoundsException {
        if (null != this.vendors) {
            if ((0 <= index) && (this.vendors.size() > index)) {
                return this.vendors.get(index);
            }
        }
        throw new IndexOutOfBoundsException("Invalid index: " + index.toString());
    }

    /**
     *      */
    public ArrayList<Vendor> getAllVendors() {
        if (null == this.vendors) {
            return null;
        }
        ArrayList<Vendor> alv = new ArrayList<Vendor>();

        Iterator<Vendor> iv = this.vendors.iterator();
        while (iv.hasNext()) {
            alv.add(iv.next().clone());
        }
        return alv;
    }

    /**
     *      */
    public Vendor removeVendor(String name) throws InvalidVendorException {
        if (null != this.vendors) {
            if (null != name) {
                Iterator<Vendor> iterator = this.vendors.iterator();
                Vendor vendor = null;
                while (iterator.hasNext()) {
                    vendor = iterator.next();
                    if (name == vendor.getName()) {
                        if (this.vendors.remove(vendor)) {
                            return vendor;
                        }
                    }
                }
            }
        }
        throw new InvalidVendorException("The vendor: " + name + " does not exist.");
    }

    /**
     *      */
    public Vendor removeVendor(Integer index) throws IndexOutOfBoundsException {
        if (null != this.vendors) {
            if ((0 <= index) && (this.vendors.size() > index)) {
                Vendor vendor = this.vendors.get(index);
                if (this.vendors.remove(vendor)) {
                    return vendor;
                }
            }
        }
        throw new IndexOutOfBoundsException("Invalid index: " + index.toString());
    }

    @Override
    public String toString() {
        if (null == this.code && null == this.name) {
            return null;
        } else {
            if (null != this.code && null == this.name) {
                return this.code + ",";
            } else if (null == this.code && null != this.name) {
                return "," + this.name;
            }
        }
        return this.code + "," + this.name;
    }

    /**
     *      */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Category) {
            if (this.code.equals(((Category) obj).getCode())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Category clone() {
        Category cat = null;
        try {
            cat = new Category(this.code, this.name);
        } catch (InvalidCategoryCodeException ex) {
            //Logger.getLogger(Category.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cat;
    }
}
