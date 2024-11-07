package test;

import DAO.DocumentDAO;
import model.Document;
import model.Person;
import util.Date;

public class TestDocumentDAO  {
    public static void main(String[] args) {
        Date date = new Date(4,5,2005);
        Person person = new Person("Duy",date,"12345","HY","1234567","Nam");


        Document document = new Document("INT1017","NMLT","Sach tham khao","Duy","Duy",100,7);
        //DocumentDAO.getInstance().addDocument(document);       // Thêm sách
        DocumentDAO.getInstance().deleteDocument("INT1017");  // Xóa sách
        // GoogleBooksImporter importer = new GoogleBookImporter();
       //importer.importBooksToDatabase();

    }
}
