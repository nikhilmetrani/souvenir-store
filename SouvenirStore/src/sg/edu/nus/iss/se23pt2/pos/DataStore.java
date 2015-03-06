//
//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : SouvenirStore
//  @ File Name : DataStore.java
//  @ Date : 06/03/2015
//  @ Author : Jaya Vignesh
//
//

package sg.edu.nus.iss.se23pt2.pos;

public abstract class DataStore
{
    public abstract <T> void create(T obj);
    
    public abstract <T> void update(T obj);
    
    public abstract <T> void load(SouvenirStore store);
    
    public abstract <T> void remove(T obj);

}
