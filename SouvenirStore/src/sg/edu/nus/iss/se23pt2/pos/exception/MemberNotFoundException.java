/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.se23pt2.pos.exception;

/**
 *
 * @author Nick
 */
public class MemberNotFoundException extends Exception{
    /**
    * 
    */
    private static final long serialVersionUID = 1L;

    public MemberNotFoundException(String msg){
        super(msg);
    }
}
