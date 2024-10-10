import java.util.ArrayList;
import java.util.List;

/**
 * Lớp quản lý tài liệu trong thư viện.
 */
public class DocumentManage {
    private List<Document> documentList;

    /**
     * Khởi tạo một đối tượng DocumentManage với danh sách tài liệu rỗng.
     */
    public DocumentManage() {
        documentList = new ArrayList<>();
    }

    /**
     * Khởi tạo một đối tượng DocumentManage với danh sách tài liệu được cung cấp.
     *
     * @param documentList Danh sách tài liệu khởi tạo.
     */
    public DocumentManage(List<Document> documentList) {
        this.documentList = documentList;
    }

    /**
     * Lấy danh sách tài liệu.
     *
     * @return Danh sách tài liệu.
     */
    public List<Document> getDocumentList() {
        return documentList;
    }

    /**
     * Thiết lập danh sách tài liệu.
     *
     * @param documentList Danh sách tài liệu mới.
     */
    public void setDocumentList(List<Document> documentList) {
        this.documentList = documentList;
    }

    /**
     * Thêm tài liệu vào danh sách.
     *
     * @param document Tài liệu cần thêm.
     */
    public void addDocument(Document document) {
        this.documentList.add(document);
    }

    /**
     * Xóa tài liệu khỏi danh sách.
     *
     * @param document Tài liệu cần xóa.
     */
    public void removeDocument(Document document) {
        this.documentList.remove(document);
    }

    /**
     * Chỉnh sửa thông tin tài liệu trong danh sách.
     *
     * @param document Tài liệu với thông tin đã được chỉnh sửa.
     */
    public void editDocument(Document document) {
        int index = documentList.indexOf(document);
        if (index != -1) {
            this.documentList.set(index, document);
        }
    }

    /**
     * Tìm kiếm tài liệu theo ID, tiêu đề hoặc tác giả.
     *
     * @param id     ID của tài liệu cần tìm.
     * @param title  Tiêu đề của tài liệu cần tìm.
     * @param author Tác giả của tài liệu cần tìm.
     * @return Danh sách tài liệu khớp với các tiêu chí tìm kiếm.
     */
    public List<Document> searchDocument(String id, String title, String author) {
        List<Document> documentList1 = new ArrayList<>();

        for (Document document : documentList) {
            boolean matchesId = (id != null && document.getId().equalsIgnoreCase(id));
            boolean matchesTitle = (title != null && document.getTitle().equalsIgnoreCase(title));
            boolean matchesAuthor = (author != null && document.getAuthor().equalsIgnoreCase(author));

            if (matchesId || matchesTitle || matchesAuthor) {
                documentList1.add(document);
            }
        }
        return documentList1;
    }
}
// còn thiếu Tích hợp API tra cứu thông tin tài liệu
