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
     * Searches for a document by its ID.
     *
     * @param id The ID of the document to search for.
     * @return The document with the corresponding ID if found; otherwise, returns null.
     */
    public Document searchBookById(String id) {
        for (Document document : documentList) {
            if (document.getId().equals(id)) {
                return document;
            }
        }
        return null;
    }

    /**
     * Searches for documents by their title.
     *
     * @param title The title of the documents to search for.
     * @return A list of documents with the corresponding title. If no documents are found, the list will be empty.
     */
    public List<Document> searchBookByTitle(String title) {
        List<Document> tmpList = new ArrayList<>();
        for (Document document : documentList) {
            if (document.getTitle().equals(title)) {
                tmpList.add(document);
            }
        }
        return tmpList;
    }

    /**
     * Searches for documents by their author.
     *
     * @param author The author of the documents to search for.
     * @return A list of documents with the corresponding author. If no documents are found, the list will be empty.
     */
    public Document searchBookByAuthor(String author) {
        for (Document document : documentList) {
            if (document.getAuthor().equals(author)) {
                return document;
            }
        }
        return null;
    }
}
