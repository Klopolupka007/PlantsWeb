public class Abzac implements Comparable<Abzac> {
    String name;
    int indexStart;
    int indexEnd;

    public Abzac(String name, int index) {
        this.name = name;
        this.indexStart = index;
    }

    public Abzac(String name, int indexStart, int indexEnd) {
        this.name = name;
        this.indexStart = indexStart;
        this.indexEnd = indexEnd;
    }

    public void setIndexEnd(Abzac next)
    {
        indexEnd = next.indexStart-1;
    }

    @Override
    public int compareTo(Abzac o) {
        return this.indexStart -o.indexStart;
    }
}
