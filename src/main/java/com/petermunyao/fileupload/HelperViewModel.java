package com.petermunyao.fileupload;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

public class HelperViewModel extends AndroidViewModel {

    private MutableLiveData<List<Document>> mLiveData;
    private List<Document> documents;

    public HelperViewModel(@NonNull Application application) {
        super(application);
        documents = new ArrayList<>();
        String[] docs = {
                "I.D",
                "K.R.A Copy",
                "Birth Certificate",
                "Passport",
                "Student I.D"
        };

        for(int i = 0; i < docs.length; i++){
            Document document = new Document();
            document.setDocumentType(docs[i]);
            document.setDocumentName("");
            documents.add(document);
        }
        mLiveData = new MutableLiveData<>();
        mLiveData.setValue(documents);
    }

    public MutableLiveData<List<Document>> getmLiveData() {
        return mLiveData;
    }

    public List<Document> getDocuments() {
        return documents;
    }

    public void updateDocumentName(int position, String docName){
        documents.get(position).setDocumentName(docName);
        mLiveData.setValue(documents);
    }
}
