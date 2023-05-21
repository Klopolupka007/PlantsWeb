public class FlowerInSoderjanie {
    int numberPage;
    int numberEndPage;
    String nameKirill;
    String nameLatin;


    public FlowerInSoderjanie(int numberPage, String nameKirill, String nameLatin) {
        this.numberPage = numberPage;
        this.nameKirill = nameKirill;
        this.nameLatin = nameLatin;
    }

    public void setEndPage(FlowerInSoderjanie next)
    {
       numberEndPage = next.numberPage-1;
    }

}
