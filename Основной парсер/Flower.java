import java.util.ArrayList;
import java.util.List;

public class Flower {
    String nameK;
    String nameL;
    String opisanie;
    String areal;
    String drugie_vidi;
    String ekologia;
    String resursi;
    String vozdelivanie;
    String himicheski_sostav;
    String sirie;
    String farmackologicheskie_svoistva;
    String primenenie_v_medecine;

    public Flower(String nameK, String nameL, String opisanie, String areal, String drugie_vidi, String ekologia, String resursi, String vozdelivanie, String himicheski_sostav, String sirie, String farmackologicheskie_svoistva, String primenenie_v_medecine) {
        this.nameK = nameK;
        this.nameL = nameL;
        this.opisanie = opisanie;
        this.areal = areal;
        this.drugie_vidi = drugie_vidi;
        this.ekologia = ekologia;
        this.resursi = resursi;
        this.vozdelivanie = vozdelivanie;
        this.himicheski_sostav = himicheski_sostav;
        this.sirie = sirie;
        this.farmackologicheskie_svoistva = farmackologicheskie_svoistva;
        this.primenenie_v_medecine = primenenie_v_medecine;
    }


    public Flower(List<Abzac> indexes, ArrayList<String> abzac, String allAboutFlower,FlowerInSoderjanie flowerInSoderjanie) {

        nameK = flowerInSoderjanie.nameKirill;
        nameL = flowerInSoderjanie.nameLatin;

        if(check(indexes,abzac.get(0)))
        {
            opisanie = setAbz(allAboutFlower,getIndex(indexes,abzac.get(0)),getNextIndex(indexes,getIndex(indexes,abzac.get(0))));
        }
        else{
            opisanie = "null";
        }
        if(check(indexes,abzac.get(1)))
        {
             drugie_vidi= setAbz(allAboutFlower,getIndex(indexes,abzac.get(1)),getNextIndex(indexes,getIndex(indexes,abzac.get(1))));
        }
        else{
             drugie_vidi= "null";
        }
        if(check(indexes,abzac.get(2)))
        {
             areal= setAbz(allAboutFlower,getIndex(indexes,abzac.get(2)),getNextIndex(indexes,getIndex(indexes,abzac.get(2))));
        }
        else{
             areal= "null";
        }
        if(check(indexes,abzac.get(3)))
        {
             ekologia= setAbz(allAboutFlower,getIndex(indexes,abzac.get(3)),getNextIndex(indexes,getIndex(indexes,abzac.get(3))));
        }
        else{
            ekologia = "null";
        }
        if(check(indexes,abzac.get(4)))
        {
             resursi= setAbz(allAboutFlower,getIndex(indexes,abzac.get(4)),getNextIndex(indexes,getIndex(indexes,abzac.get(4))));
        }
        else{
             resursi= "null";
        }
        if(check(indexes,abzac.get(5)))
        {
             vozdelivanie= setAbz(allAboutFlower,getIndex(indexes,abzac.get(5)),getNextIndex(indexes,getIndex(indexes,abzac.get(5))));
        }
        else{
            vozdelivanie = "null";
        }
        if(check(indexes,abzac.get(6)))
        {
             himicheski_sostav = setAbz(allAboutFlower,getIndex(indexes,abzac.get(6)),getNextIndex(indexes,getIndex(indexes,abzac.get(6))));
        }
        else{
            himicheski_sostav = "null";
        }
        if(check(indexes,abzac.get(7)))
        {
             sirie= setAbz(allAboutFlower,getIndex(indexes,abzac.get(7)),getNextIndex(indexes,getIndex(indexes,abzac.get(7))));
        }
        else{
            sirie = "null";
        }
        if(check(indexes,abzac.get(8)))
        {
            farmackologicheskie_svoistva = setAbz(allAboutFlower,getIndex(indexes,abzac.get(8)),getNextIndex(indexes,getIndex(indexes,abzac.get(8))));
        }
        else{
            farmackologicheskie_svoistva = "null";
        }
        if(check(indexes,abzac.get(9)))
        {
            primenenie_v_medecine = setAbz(allAboutFlower,getIndex(indexes,abzac.get(9)),getNextIndex(indexes,getIndex(indexes,abzac.get(9))));
        }
        else{
            primenenie_v_medecine = "null";
        }


    }


    Flower(String [] abz,FlowerInSoderjanie fis)
    {
        this.nameK = fis.nameKirill;
        this.nameL = fis.nameLatin;
        this.opisanie = abz[0];
        this.areal = abz[1];
        this.drugie_vidi = abz[2];
        this.ekologia = abz[3];
        this.resursi = abz[4];
        this.vozdelivanie = abz[5];
        this.himicheski_sostav = abz[6];
        this.sirie = abz[7];
        this.farmackologicheskie_svoistva = abz[8];
        this.primenenie_v_medecine = abz[9];
    }

    private int getNextIndex(List<Abzac> indexes,int index)
    {
        if(index<indexes.size()) {
            return indexes.get(index + 1).indexStart;
        }
        return -1;
    }

    private int getIndex(List<Abzac> indexes, String s) {
        int i =0;
        for(Abzac a:indexes){
            if(a.name.equals(s))
            {
                return i;
            }
            i++;
        }
        return -1;
    }

    private boolean check(List<Abzac> indexes, String s) {

        for(Abzac a:indexes){
            if(a.name.equals(s))
            {
                return true;
            }
        }
        return false;
    }

    private String setAbz(String allAboutFlower, int indexCurr, int indexNext) {
        String s = "";
        while (indexCurr<indexNext)
        {
            s+=allAboutFlower.charAt(indexCurr);
            indexCurr++;
        }
        return s;
    }
}
