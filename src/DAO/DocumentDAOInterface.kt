package DAO;

import model.Document

interface DocumentDAOInterface {
    // Phương thức thêm tài liệu
    fun addDocument(document: Document?)

    // Phương thức cập nhật tài liệu
    fun updateDocument(document: Document?)

    // Phương thức xóa tài liệu
    fun deleteDocument(maSach: String?)

    // Phương thức tìm tài liệu theo mã sách
    fun findDocumentById(maSach: String?): Document?

    // Phương thức tìm tài liệu theo tên sách
    fun findDocumentByTitle(tenSach: String?): Document?

    // Phương thức tìm tài liệu theo thể loại
    fun findDocumentByCategory(theLoai: String?): Document?

    // Phương thức tìm tất cả tài liệu
    fun findAllDocuments(): List<Document?>?
}
