public class SunDay {
    String lightDay;
    int inMinute;
    public SunDay(String lightDay) {
        this.lightDay = lightDay;
        inMinute = intoMinute(lightDay);
    }

    private int intoMinute(String lightDay) {

        String h = "";
        String m = "";
        int indexDP = -1;
        for(int i =0;lightDay.charAt(i)!=':';i++)
        {
            indexDP = i;
            h += lightDay.charAt(i);
        }
        indexDP ++;
        for(int i = indexDP +1;i<lightDay.length();i++)
        {
            m+=lightDay.charAt(i);
        }

        return Integer.parseInt(m)+Integer.parseInt(h)*60;

    }

}
