import java.util.Comparator;
public class Publication{
    private String _authoList;
    private String _title;
    private int _publicationYear;
    private String _magazine;
    private int _volume;
    private int _number;
    private String _pages;
    private int _citationsNumber = 0;
    private String _DOI;

    Publication(String authoList, String title, int publicationYear, String magazine, int citation, int volume, int number, String pages, String DOI){
        _authoList = authoList;
        _title = title;
        _publicationYear = publicationYear;
        _magazine = magazine;
        _citationsNumber = citation;
        _volume = volume;
        _number = number;
        _pages = pages;
        _DOI = DOI;
    }

    public int getYear(){
        return _publicationYear;
    }
    public int getCitation(){
        return _citationsNumber;
    }
    public String getAuthorList(){
        return _authoList;
    }

    public static Comparator<Publication> yearComparator = new Comparator<Publication>() {

        public int compare(Publication s1, Publication s2) {
            int y1 = s1.getYear();
            int y2 = s2.getYear();

            //descending order
            return y2-y1;
    }};

    public static Comparator<Publication> citationsComparator = new Comparator<Publication>() {

        public int compare(Publication s1, Publication s2) {
            int y1 = s1.getCitation();
            int y2 = s2.getCitation();

            //descending order
            return y2-y1;
    }};

    public String toStringP(int t) {
        if(t==1) return _authoList + "," + _title + "," +_magazine + "," + _pages + "," + _volume + ","+_number + "," + _publicationYear + "," + _DOI + "\n\n";
        else return _authoList + "," + _title + "," +_magazine + "," + _pages + "," + _volume + "," +_number+","+ _publicationYear + "," + _DOI + "(Times cited="+ _citationsNumber+ ")\n\n";
    }
}