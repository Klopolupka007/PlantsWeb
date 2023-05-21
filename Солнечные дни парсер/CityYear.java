import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.io.IOException;
import java.util.LinkedList;

public class CityYear {
    String city;
    SunSeason winter = new SunSeason(new LinkedList<>());
    SunSeason spring = new SunSeason(new LinkedList<>());
    SunSeason summer = new SunSeason(new LinkedList<>());
    SunSeason autumn = new SunSeason(new LinkedList<>());

    CityYear(String city, WebDriver driver) throws IOException, InterruptedException {
        this.city = city;

        driver.findElement(By.xpath("//*[@id=\"content\"]/main/article/form/div/input")).sendKeys(city);
        WebElement ele = driver.findElement(By.cssSelector("    font-family: Geneva,Helvetica,sans-serif;\n" +
                "    font-size: 1em;\n" +
                "    cursor: default;\n" +
                "    text-align: left;\n" +
                "    padding: 2px 5px;\n" +
                "    white-space: nowrap;"));
        //System.out.println("+=+"+selectedList);
        Thread.sleep(1000);

        Actions action = new Actions(driver);

        action.moveToElement(ele);

        setSeasons(winter,spring,summer,autumn);
    }

    private static void setSeasons(SunSeason winter, SunSeason spring, SunSeason summer, SunSeason autumn) throws IOException {

        for(int i =1;i<=12;i++) {
            if((i==12)||(i==1)||(i==2)) {
                Document doc = Jsoup.connect("https://dateandtime.info/ru/citysunrisesunset.php?id=472045&month=" + i + "&year=2023").get();
                setDays(winter.sdl,doc.html());
            }
            if((i==3)||(i==4)||(i==5)) {
                Document doc = Jsoup.connect("https://dateandtime.info/ru/citysunrisesunset.php?id=472045&month=" + i + "&year=2023").get();
                setDays(spring.sdl,doc.html());
            }
            if((i==6)||(i==7)||(i==8)) {
                Document doc = Jsoup.connect("https://dateandtime.info/ru/citysunrisesunset.php?id=472045&month=" + i + "&year=2023").get();
                setDays(summer.sdl,doc.html());
            }
            if((i==9)||(i==10)||(i==11)) {
                Document doc = Jsoup.connect("https://dateandtime.info/ru/citysunrisesunset.php?id=472045&month=" + i + "&year=2023").get();
                setDays(autumn.sdl,doc.html());
            }

            winter.setMeans();
            autumn.setMeans();
            summer.setMeans();
            spring.setMeans();
        }
    }
    private static void setDays(LinkedList<SunDay> sdl, String html) {

        for(int i = html.indexOf("Календарь восхода и захода солнца");i<html.indexOf("Календарь сумерек");i++)
        {
            if(check(html.charAt(i),html.charAt(i+1),html.charAt(i+2),html.charAt(i+3),html.charAt(i+4),html.charAt(i+5),html.charAt(i+6),html.charAt(i+7)))
            {
                int index = i;
                String s = "";
                for(int ii = index;ii<index+5;ii++)
                {
                    s+=html.charAt(ii);
                }
                sdl.add(new SunDay(s));

            }
        }

    }
    private static boolean check(char charAt, char charAt1, char charAt2, char charAt3, char charAt4, char charAt5, char charAt6, char charAt7) {
        if(Character.isDigit(charAt)&&Character.isDigit(charAt1)&&Character.isDigit(charAt3)&&Character.isDigit(charAt4)&&Character.isDigit(charAt6)&&Character.isDigit(charAt7))
        {
            if(charAt5 ==':'&& charAt2 == ':')
            {
                return true;
            }
        }
        return false;
    }
}
