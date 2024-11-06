package DAO

import model.Document

// Import List đúng cách từ thư viện java.util
// Import lớp Document
interface DocumentDAOInterface {
    fun addDocument(document: Document?) // Phương thức thêm tài liệu
    fun updateDocument(document: Document?) // Phương thức cập nhật tài liệu
    fun deleteDocument(maSach: String?) // Phương thức xóa tài liệu
    fun findDocumentById(maSach: String?): Document? // Phương thức tìm tài liệu theo mã sách
    fun findDocumentByTitle(tenSach: String?): Document? // Phương thức tìm tài liệu theo ten sách
    fun findDocumentByCategory(theLoai: String?): Document? // Phương thức tìm tài liệu theo the loai
    fun findAllDocuments(): List<Document?>? // Phương thức tìm tất cả tài liệu
}
