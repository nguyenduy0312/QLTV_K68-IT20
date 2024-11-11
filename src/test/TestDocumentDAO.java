package test;

import DataBase.GoogleBooksImporter;
import DataBase.JDBCConnection;
import DAO.DocumentDAO;
import model.Document;
import model.Person;
import util.Date;

public class TestDocumentDAO {
    public static void main(String[] args) {
        Document document2 = new Document("Book1", "Bí kíp tán gái", "Sach tán gái", "HoangDiem", "HoangOanh", 1, 1000);
        // DocumentDAO.getInstance().addDocument(document1);                                         // Thêm sách thành công
        // DocumentDAO.getInstance().updateDocument(document2);                                       // Cập nhật sách thành công
        // Document documentSearchh = DocumentDAO.getInstance().findDocumentById("Book1");            // Tìm sach theo id thành công
        // Document documentSearch = DocumentDAO.getInstance().findDocumentByTitle("Bí kíp tán gái"); // Tìm sách theo tên thành công
        // Document documentSearch = DocumentDAO.getInstance().findDocumentByCategory("Sach tán gái"); // Tìm sách theo tiêu đề thành công
    }
}
