import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.IOException;
import java.util.LinkedList;

public class Main {

    public static String [] cities = new String[]{
            "Саратов",
            "Тюмень",
            "Тольятти",
            "Ижевск",
            "Барнаул",
            "Иркутск",
            "Ульяновск",
            "Хабаровск",
            "Ярославль",
            "Владивосток",
            "Махачкала",
            "Томск",
            "Оренбург",
            "Кемерово",
            "Новокузнецк",
            "Рязань",
            "Астрахань",
            "Набережные Челны",
            "Пенза",
            "Липецк",
            "Киров"
    };

    static String url = "https://dateandtime.info/ru/citysunrisesunset.php?id=472045&month=5&year=2023";


    public static void main(String[] args) throws IOException, InterruptedException {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\DENIS\\Documents\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.get(url);

        LinkedList<CityYear> cyl = new LinkedList<>();

        for(int i =0;i< cities.length;i++)
        {
            cyl.add(new CityYear(cities[i],driver));
        }

        //System.out.println();
    }



}
