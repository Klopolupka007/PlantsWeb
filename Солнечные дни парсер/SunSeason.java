import java.util.LinkedList;

public class SunSeason {
    LinkedList<SunDay> sdl = new LinkedList<>();
    double means;

    public SunSeason(LinkedList<SunDay> sdl) {
        this.sdl = sdl;
        int summ = 0;
        for (SunDay sd : sdl) {
            summ += sd.inMinute;
        }
        if (sdl.size() > 0) {
            means = (double) summ / sdl.size();
        }
    }

    void setMeans()
    { int summ = 0;
        for(SunDay sd:sdl)
        {
            summ +=sd.inMinute;
        }
        if(sdl.size()>0) {
            means = (double) summ / sdl.size();
        }
    }
}
