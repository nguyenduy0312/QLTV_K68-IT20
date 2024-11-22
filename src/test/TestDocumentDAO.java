package test;

import DAO.UserDAO;
import DataBase.GoogleBooksImporter;
import DataBase.JDBCConnection;
import DAO.DocumentDAO;
import model.Account;
import DataBase.GoogleBooksImporter;
import model.Document;
import model.Person;
import model.User;
import util.Date;




public class TestDocumentDAO {
    public static void main(String[] args) {
        Document document = new Document("11", "Lập trình Java cơ bản", "Nguyễn Văn A", "Giáo trình", "NXB Khoa Học", 100, 30);
        DocumentDAO a = new DocumentDAO();
        a.addDocument(document);

        // DocumentDAO.getInstance().updateDocument(document2);                                       // Cập nhật sách thành công

        // Document documentSearchh = DocumentDAO.getInstance().findDocumentById("Book1");            // Tìm sach theo id thành công

        // Document documentSearch = DocumentDAO.getInstance().findDocumentByTitle("Bí kíp tán gái"); // Tìm sách theo tên thành công

        // Document documentSearch = DocumentDAO.getInstance().findDocumentByCategory("Sach tán gái"); // Tìm sách theo tiêu đề thành công
    }
}
