package main.java.test;

import main.java.DAO.DocumentDAO;
import main.java.model.Document;
import main.java.model.Person;
import main.java.util.Date;

public class TestDocumentDAO  {
    public static void main(String[] args) {
        Date date = new Date(4,5,2005);
        Person person = new Person("Duy",date,"12345","HY","1234567","Nam");

        Document document = new Document("INT1010","NMLT","Sach tham khao","Hoang","Hoang",100,7);
       // DocumentDAO.getInstance().addDocument(document);       // Thêm sách
       // DocumentDAO.getInstance().deleteDocument("INT1010");  // Xóa sách
    }
}
