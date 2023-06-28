package classesAuxiliaires;

import java.nio.ByteBuffer;

public class Segment
{
    private final byte index_premier;
    private final byte index;
    private final byte index_dernier;
    private final byte[] donnees;

    public Segment(byte index_premier, byte index, byte index_dernier, byte[] donnees)
    {
        this.index_premier = index_premier;
        this.index = index;
        this.index_dernier = index_dernier;
        this.donnees = donnees;
    }

    public Segment(byte[] segment)
    {
        ByteBuffer buffer_segment = ByteBuffer.wrap(segment);

        this.index_premier = buffer_segment.get();
        this.index = buffer_segment.get();
        this.index_dernier = buffer_segment.get();

        byte[] donnees = new byte[buffer_segment.remaining()];
        buffer_segment.get(donnees);

        this.donnees = donnees;
    }

    public byte[] getSegment()
    {
        ByteBuffer buffer_segment = ByteBuffer.allocate(donnees.length + 3);

        buffer_segment.put(index_premier);
        buffer_segment.put(index);
        buffer_segment.put(index_dernier);
        buffer_segment.put(donnees);

        return buffer_segment.array();
    }

    public byte[] getDonnees()
    {
        return donnees;
    }
}
