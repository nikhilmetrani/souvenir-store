//
//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : SouvenirStore
//  @ File Name : MemberDS.java
//  @ Date : 06/03/2015
//  @ Author : Jaya Vignesh
//
//

package sg.edu.nus.iss.se23pt2.pos.datastore;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.ArrayList;

import sg.edu.nus.iss.se23pt2.pos.Member;
import sg.edu.nus.iss.se23pt2.pos.SouvenirStore;
import sg.edu.nus.iss.se23pt2.pos.exception.DataLoadFailedException;

public class MemberDS extends DataStore
{
    private static final String fileName = "Member.dat";

    public MemberDS () throws AccessDeniedException, IOException {
        super(fileName);
    }

	@Override
	public ArrayList<Member> load(SouvenirStore store)
			throws DataLoadFailedException {
        String line;
        String[] elements;
        Member member;
        ArrayList<Member> members = new ArrayList<Member>();
        try {
            while ((line = this.read()) != null) {
                if(line.trim().isEmpty())
                    continue;

                elements = line.split(",");
                member = new Member(elements[0], elements[1]);
                member.addLoyaltyPoints(Integer.parseInt(elements[2]));
                members.add(member);
            }
        } catch (IOException e) {
            throw new DataLoadFailedException(e.getMessage());
        } finally {
            this.close();
        }
        return members;
	}

	@Override
	protected <T> boolean matchData(T obj, String data) {
        String key = ((Member) obj).getId();
        if (data.indexOf( "," + key + ",") > 0)
            return true;
        return false;
	}
}
