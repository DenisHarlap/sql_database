import com.mySql.MySQLDatabase;


public class Main {

    public static void main(String[] args) {

        MySQLDatabase obj = new MySQLDatabase();
        obj.printSelectResult();
        obj.createCollectionOnSelect();
        obj.deleteSelectResult();
        obj.updateSelectResult();
        obj.closeConnection();
    }
}
