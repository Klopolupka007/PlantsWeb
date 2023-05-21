public class Main {

    public static void m(String[] args) {
        String alf ="АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ";

        for(int i =0;i< alf.length();i++)
        {
            System.out.print("dirtySod = dirtySod.replace(");
            System.out.print("\"\\n"+alf.charAt(i)+"\\");
            System.out.println("n\",\"\");");
        }

    }
}
