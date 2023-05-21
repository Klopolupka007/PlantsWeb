import com.google.gson.Gson;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import com.itextpdf.text.pdf.parser.SimpleTextExtractionStrategy;
import com.itextpdf.text.pdf.parser.TextExtractionStrategy;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;



public class SimpleTextExtractor {

    static ArrayList<String> abzac = new ArrayList<>();


    public static void main(String[] args) throws IOException {

        abzac.add("Описание. ");
        abzac.add("Другие виды. ");
        abzac.add("Ареал.");
        abzac.add("Экология. ");
        abzac.add("Ресурсы. ");
        abzac.add("Возделывание. ");
        abzac.add("Химический состав. ");
        abzac.add("Сырьё. ");
        abzac.add("Фармакологические свойства. ");
        abzac.add("Применение в медицине. ");
        abzac.add("Литература");




        String fileUSSR = "растения СССР.pdf";
        String fileAtlas2021 = "Атлас 2021.pdf";
        String fileCilicin_A = "Цицилин_А.pdf";

        String dirtySod =  parserSoderjanie(fileAtlas2021);
        //System.out.println(dirtySod);
        String mediumDirtySod = easyClean(dirtySod);
        //System.out.println(mediumDirtySod);
        List<FlowerInSoderjanie> list = new LinkedList<>();

        parseForCreateObjects(list,mediumDirtySod);

        //System.out.println(list.size());
        for(int i =0;i<list.size();i++)
        {
            if(i!=list.size()-1)
            {
                list.get(i).setEndPage(list.get(i+1));
            }
            else
            {
                list.get(i).numberEndPage = 618;
            }
        }

        LinkedList<Flower> fullLis = new LinkedList<>();
        //parseFULL(list,fileAtlas2021,fullLis);
        parseFULL2(list,fileAtlas2021,fullLis);

        System.out.println("[");
        for(int i =0;i<list.size();i++)
        {
            System.out.println("\""+list.get(i).nameKirill+"\",");
        }
        System.out.println("]");
        Gson g = new Gson();

        String line = g.toJson(fullLis); // Строка для записи
        Path pathToFile = Paths.get("example.txt"); // Получаем путь до файла

// Записываем строку в файл
// Указываем путь, строку, стандарт кодирования символов
// опции записи в файл
        Files.writeString(pathToFile, line, StandardCharsets.UTF_8, StandardOpenOption.CREATE,
                StandardOpenOption.APPEND);
    }

    private static void parseFULL2(List<FlowerInSoderjanie> list, String fileAtlas2021, LinkedList<Flower> fullLis) throws IOException {
        int currPIndex = 0;
        String s = "";
        String [] fullInfoList = new String[list.size()];

        PdfReader reader = new PdfReader(fileAtlas2021);

        for (int i = 19; i <618; ++i) {
            TextExtractionStrategy strategy = new SimpleTextExtractionStrategy();
            String text = PdfTextExtractor.getTextFromPage(reader, i, strategy);

            if(currPIndex != findPageIndex(i,list)) {
                currPIndex = findPageIndex(i, list);
                if(currPIndex!=-1) {
                    fullInfoList[currPIndex] = s;
                }
                s = "";
                i--;
            }
            else
            {
                s+=text;
            }

        }
        reader.close();

        for(int i =1;i< fullInfoList.length;i++)
        {
            parseToObject(fullInfoList[i],list.get(i),i,fullLis);
        }

    }

    private static void parseToObject(String all, FlowerInSoderjanie fis, int index, LinkedList<Flower> fullLis) {
        List<Abzac> aLi= new ArrayList<>();
        setAL(aLi,all);

        String[] params = new String[aLi.size()];

        for(int i =0;i<params.length;i++)
        {
            if(aLi.get(i).indexStart!=-1)
            {
                params[i] = setNotNullParam(i,aLi,all);
            }
            else
            {
                params[i] = null;
            }
        }

        fullLis.add(new Flower(params,fis));

    }

    private static String setNotNullParam(int index, List<Abzac> aLi, String all) {

        String s = "";

        int start = aLi.get(index).indexStart;
        int end = aLi.get(index).indexEnd;
        for(int i = start;i<end;i++)
        {
            s +=all.charAt(i);
        }

        return s;
    }

    private static void setAL(List<Abzac> aLi, String all) {

        //if(all==null)
        //{
        //    return;
        //}

        for(int i =0;i< abzac.size();i++)
        {

            if(all.contains(abzac.get(i))){
                aLi.add(new Abzac(abzac.get(i),all.indexOf(abzac.get(i))));
            }
            else
            {
                aLi.add(new Abzac(abzac.get(i),-1));
            }
        }
        //Collections.sort(aLi);
        for(int i =0;i<aLi.size();i++)
        {
            if(i!=aLi.size()-1)
            {
                aLi.get(i).setIndexEnd(aLi.get(i+1));
            }
            else
            {
                aLi.get(i).indexEnd = all.length();
            }
        }
    }

    private static int findPageIndex(int x, List<FlowerInSoderjanie> list) {
        for(int i =0;i<list.size();i++)
        {
            if((x<=list.get(i).numberEndPage)&&(x>=list.get(i).numberPage))
            {
                return i;
            }
        }
        return -1;
    }

    /*private static void parseFULL(List<FlowerInSoderjanie> list, String fileAtlas2021, LinkedList<Flower> fullLis) throws IOException {



        // считаем, что программе передается один аргумент - имя файла
        PdfReader reader = new PdfReader(fileAtlas2021);

        String aboutFlower="";
        boolean flag = false;
        int indexForList = 1;
        String allAboutFlower = "";
        // не забываем, что нумерация страниц в PDF начинается с единицы.
        for (int i = 18; i < 620 && indexForList<list.size(); ++i) {
            TextExtractionStrategy strategy = new SimpleTextExtractionStrategy();
            String text = PdfTextExtractor.getTextFromPage(reader, i, strategy);



            if(list.get(indexForList).numberPage!=i)
            {
                allAboutFlower = allAboutFlower + text;
            }
            else
            {
                if(indexForList==list.size()-1)
                {
                    //System.out.println();
                }
                setFlower(allAboutFlower,list.get(indexForList),fullLis);
                allAboutFlower = "";
                indexForList++;

                i--;
            }

        }


        // убираем за собой
        reader.close();
    }

    private static void setFlower(String allAboutFlower, FlowerInSoderjanie flowerInSoderjanie, LinkedList<Flower> fullLis) {

        List<Abzac> indexes = new LinkedList<>();

        setIndexes(indexes,allAboutFlower);
        Collections.sort(indexes);
        if(!indexes.get(indexes.size()-1).name.equals("Литература"))
        {
            System.out.println();
        }

        fullLis.add(new Flower(indexes,abzac,allAboutFlower,flowerInSoderjanie));
    }*/
    /*
    private static void setIndexes(List<Abzac> indexes, String allAboutFlower) {

        for(String s:abzac)
        {
            if(allAboutFlower.contains(s))
            {
                indexes.add(new Abzac(s,allAboutFlower.indexOf(s)));
            }
        }

    }

    private static int getNextIndex(String s, List<Abzac> indexes) {

        for(int i =0;i<indexes.size();i++)
        {
            if(indexes.get(i).name.equals(s))
            {
                return i;
            }
        }
        return -1;
    }

    private static boolean checkIndexes(String s, List<Abzac> indexes) {
        for(Abzac a: indexes)
        {
            if(a.name.equals(s))
            {
                return true;
            }
        }
        return false;
    }
*/

    private static void parseForCreateObjects(List<FlowerInSoderjanie> list, String mediumDirtySod) {
        StringBuilder clear = new StringBuilder();
        for(int i =0;i<mediumDirtySod.length()-1;i++)
        {
            if(Character.isDigit(mediumDirtySod.charAt(i))&&Character.isLetter(mediumDirtySod.charAt(i+1)))
            {
                //System.out.println(mediumDirtySod.charAt(i-2)+""+mediumDirtySod.charAt(i-1)+""+mediumDirtySod.charAt(i)+""+mediumDirtySod.charAt(i+1)+""+mediumDirtySod.charAt(i+2)+""+mediumDirtySod.charAt(i+3));
                clear.append(mediumDirtySod.charAt(i)).append("\n");
            }
            clear.append(mediumDirtySod.charAt(i));
        }

        List<String> l = new LinkedList<>();
        int flag = 0;
        String s = "";
        for(int i =0;i<clear.length();i++)
        {
            if(clear.charAt(i) != '\n') {
                if (flag % 2 == 0) {
                    s += clear.charAt(i);
                }
            }
            else {
                flag ++;
                if(!s.equals("")) {
                    l.add(s);
                }
                //System.out.print(s);
                s="";
            }
        }

        for(int i =0;i<l.size();i++)
        {

           // System.out.print(l.get(i)+" , ");
        }

        clear.append(mediumDirtySod.charAt(mediumDirtySod.length()-1));
        //System.out.println(clear);
        StringBuilder clear2 = new StringBuilder();
        clear2.append("\n0\n");
        for(int i =0;i<clear.length()-1;i++)
        {
            if(Character.isDigit(clear.charAt(i))&&clear.charAt(i+1)=='\n')
            {
                //System.out.println(mediumDirtySod.charAt(i-2)+""+mediumDirtySod.charAt(i-1)+""+mediumDirtySod.charAt(i)+""+mediumDirtySod.charAt(i+1)+""+mediumDirtySod.charAt(i+2)+""+mediumDirtySod.charAt(i+3));
                clear2.append(clear.charAt(i)).append("\n");
            }
            clear2.append(clear.charAt(i));
        }
        clear2.append(clear.charAt(clear.length()-1));

        //System.out.println(clear2);

        //boolean flag = false;
        clear2.append("\n0\n");
        String objStr = "";
        for(int i =2;i<clear2.length();i++) {
            if (clear2.toString().charAt(i-2) == '\n' && Character.isDigit(clear2.toString().charAt(i - 1)) && clear2.toString().charAt(i) == '\n') {
                if(objStr.length()>0) {
                    setList(list, objStr);
                }
                objStr = "";
            }
            else
            {
                objStr = objStr+clear2.toString().charAt(i);
            }
        }


    }

    public static String removeLastChar(String str) {
        if (str == null || str.length() == 0) {
            return str;
        }
        return str.substring(0, str.length() - 1);
    }
    private static void setList(List<FlowerInSoderjanie> list, String objStr) {

        int numberPage;
        String nameKirill = "";
        String nameLatin = "";
        objStr = removeLastChar(objStr);
        objStr = removeLastChar(objStr);
        int index = 0;
        for(int i =0;objStr.charAt(i)!='\n';i++)
        {
            index = i+1;
            if(Character.isLetter(objStr.charAt(i))) {
                nameKirill = nameKirill + objStr.charAt(i);
            }
        }
        for(int i =index;!(objStr.charAt(i)=='.'&&objStr.charAt(i+1)=='.');i++)
        {
            index = i+1;
            nameLatin = nameLatin + objStr.charAt(i);
        }
        String strNumber="";
        for(int i =index;i<objStr.length();i++)
        {
            if(Character.isDigit(objStr.charAt(i))){
                strNumber = strNumber + objStr.charAt(i);
            }
        }
        numberPage = Integer.parseInt(strNumber);
        nameLatin = nameLatin.replace("\n","");

        if (nameLatin.charAt((nameLatin.length() - 1)) == ' ') {
                nameLatin = removeLastChar(nameLatin);
        }

        list.add(new FlowerInSoderjanie(numberPage,nameKirill,nameLatin));

    }

    private static String easyClean(String dirtySod) {

        dirtySod = dirtySod.replace("\nА\n","");
        dirtySod = dirtySod.replace("\nБ\n","");
        dirtySod = dirtySod.replace("\nВ\n","");
        dirtySod = dirtySod.replace("\nГ\n","");
        dirtySod = dirtySod.replace("\nД\n","");
        dirtySod = dirtySod.replace("\nЕ\n","");
        dirtySod = dirtySod.replace("\nЁ\n","");
        dirtySod = dirtySod.replace("\nЖ\n","");
        dirtySod = dirtySod.replace("\nЗ\n","");
        dirtySod = dirtySod.replace("\nИ\n","");
        dirtySod = dirtySod.replace("\nЙ\n","");
        dirtySod = dirtySod.replace("\nК\n","");
        dirtySod = dirtySod.replace("\nЛ\n","");
        dirtySod = dirtySod.replace("\nМ\n","");
        dirtySod = dirtySod.replace("\nН\n","");
        dirtySod = dirtySod.replace("\nО\n","");
        dirtySod = dirtySod.replace("\nП\n","");
        dirtySod = dirtySod.replace("\nР\n","");
        dirtySod = dirtySod.replace("\nС\n","");
        dirtySod = dirtySod.replace("\nТ\n","");
        dirtySod = dirtySod.replace("\nУ\n","");
        dirtySod = dirtySod.replace("\nФ\n","");
        dirtySod = dirtySod.replace("\nХ\n","");
        dirtySod = dirtySod.replace("\nЦ\n","");
        dirtySod = dirtySod.replace("\nЧ\n","");
        dirtySod = dirtySod.replace("\nШ\n","");
        dirtySod = dirtySod.replace("\nЩ\n","");
        dirtySod = dirtySod.replace("\nЪ\n","");
        dirtySod = dirtySod.replace("\nЫ\n","");
        dirtySod = dirtySod.replace("\nЬ\n","");
        dirtySod = dirtySod.replace("\nЭ\n","");
        dirtySod = dirtySod.replace("\nЮ\n","");
        dirtySod = dirtySod.replace("\nЯ\n","");


        return dirtySod;
    }

    public static String parserSoderjanie(String file) throws IOException {
        StringBuilder sod = new StringBuilder();
        boolean flag = false;
        // считаем, что программе передается один аргумент - имя файла
        PdfReader reader = new PdfReader(file);
        //System.out.println(reader.getNumberOfPages());
        // не забываем, что нумерация страниц в PDF начинается с единицы.
        for (int i = 1; i <= reader.getNumberOfPages(); ++i) {
            TextExtractionStrategy strategy = new SimpleTextExtractionStrategy();
            String text = PdfTextExtractor.getTextFromPage(reader, i, strategy);

            if (workWithPage(text) > 0) {
                flag = true;
            }
            if(text.contains("АДЕНОСТИЛЕС РОМБОЛИСТНЫЙ")&&!(workWithPage(text) > 0))
            {
                flag = false;
            }
            if (flag)
            {
                //System.out.println(text + "\n");
                sod.append(text);
            }
        }
        // убираем за собой
        reader.close();
        return sod.toString();
    }

    private static int workWithPage(String text) {

        String sod = "СОДЕРЖАНИЕ";

        if(!text.contains(sod))
        {
            return -1;
        }

        return text.indexOf(sod);

    }
}
