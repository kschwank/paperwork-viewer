package org.ako.pwv.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Documents {

    List<Document> documentList = new ArrayList<>();

    public void add(Document document) {
        documentList.add(document);
    }

    public List<Document> getList() {
        Collections.sort(documentList);
        Collections.reverse(documentList);
        return documentList;
    }
}
