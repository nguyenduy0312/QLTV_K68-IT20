import java.util.ArrayList;
import java.util.List;

/**
 * Class for managing documents in the library.
 */
public class DocumentManage {
    private List<Document> documentList;

    /**
     * Initializes a DocumentManage object with an empty document list.
     */
    public DocumentManage() {
        documentList = new ArrayList<>();
    }

    /**
     * Initializes a DocumentManage object with a provided document list.
     *
     * @param documentList The initial list of documents.
     */
    public DocumentManage(List<Document> documentList) {
        this.documentList = documentList;
    }

    /**
     * Gets the list of documents.
     *
     * @return The list of documents.
     */
    public List<Document> getDocumentList() {
        return documentList;
    }

    /**
     * Sets a new list of documents.
     *
     * @param documentList The new list of documents.
     */
    public void setDocumentList(List<Document> documentList) {
        this.documentList = documentList;
    }

    /**
     * Adds a document to the list.
     *
     * @param document The document to be added.
     */
    public void addDocument(Document document) {
        this.documentList.add(document);
    }

    /**
     * Removes a document from the list.
     *
     * @param document The document to be removed.
     */
    public void removeDocument(Document document) {
        this.documentList.remove(document);
    }

    /**
     * Edits a document's information in the list.
     *
     * @param document The document with updated information.
     */
    public void editDocument(Document document) {
        int index = documentList.indexOf(document);
        if (index != -1) {
            this.documentList.set(index, document);
        }
    }

    /**
     * Searches for documents by ID, title, or author.
     *
     * @param id     The ID of the document to search for.
     * @param title  The title of the document to search for.
     * @param author The author of the document to search for.
     * @return A list of documents that match the search criteria.
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
