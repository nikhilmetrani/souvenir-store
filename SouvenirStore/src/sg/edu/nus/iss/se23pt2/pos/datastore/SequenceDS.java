package sg.edu.nus.iss.se23pt2.pos.datastore;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.ArrayList;

import sg.edu.nus.iss.se23pt2.pos.Sequence;
import sg.edu.nus.iss.se23pt2.pos.SouvenirStore;
import sg.edu.nus.iss.se23pt2.pos.exception.DataLoadFailedException;

public class SequenceDS extends DataStore {

    private static final String fileName = "Sequence.dat";

    public SequenceDS() throws AccessDeniedException, IOException {
        super(fileName);
    }

    @Override
    public ArrayList<Sequence> load (SouvenirStore store)
            throws DataLoadFailedException {
        String line;
        String[] elements;
        Sequence sequence = null;
        ArrayList<Sequence> sequences = new ArrayList<Sequence>();
        try {
            while ((line = this.read()) != null) {
                if(line.trim().isEmpty())
                    continue;

                elements = line.split(",");
                sequence = new Sequence(elements[0]);
                sequence.setPrefix(elements[1]);
                sequence.setNextNumber(Integer.parseInt(elements[2]));
                sequence.setIncrementBy(Integer.parseInt(elements[3]));
                sequences.add(sequence);
            }
        } catch (IOException e) {
            throw new DataLoadFailedException(e.getMessage());
        } finally {
            this.close();
        }
        return sequences;
    }

    @Override
    protected <T> boolean matchData(T obj, String data) {
        String key = ((Sequence) obj).getSequenceType();
        if (data.indexOf( key + ",") > 0)
            return true;
        return false;
    }

}
