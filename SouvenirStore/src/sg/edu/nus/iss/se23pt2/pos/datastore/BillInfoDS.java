//
//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : SouvenirStore
//  @ File Name : BillInfoDS.java
//  @ Date : 06/03/2015
//  @ Author : Jaya Vignesh
//
//

package sg.edu.nus.iss.se23pt2.pos.datastore;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.ArrayList;

import sg.edu.nus.iss.se23pt2.pos.SouvenirStore;
import sg.edu.nus.iss.se23pt2.pos.StoreKeeper;

public class BillInfoDS extends DataStore
{
    private static final String fileName = "BillInfo.dat";

    public BillInfoDS () throws AccessDeniedException, IOException {
        super(fileName);
    }

    @Override
    public <T> void create (T obj) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public <T> void update (T obj) {
        // TODO Auto-generated method stub
        
    }
 
    @Override
    public ArrayList<StoreKeeper> load (SouvenirStore store) {
        // TODO Auto-generated method stub
        return null;
        
    }

    @Override
    public <T> void remove (T obj) {
        // TODO Auto-generated method stub
        
    }
}
