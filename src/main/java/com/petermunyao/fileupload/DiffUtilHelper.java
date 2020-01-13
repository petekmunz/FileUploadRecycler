package com.petermunyao.fileupload;

import android.util.Log;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;
import java.util.List;

public class DiffUtilHelper extends DiffUtil.Callback {

    private final List<Document> mOldDocs;
    private final List<Document> mNewDocs;

    public DiffUtilHelper(List<Document> mOldDocs, List<Document> mNewDocs) {
        this.mOldDocs = mOldDocs;
        this.mNewDocs = mNewDocs;
    }

    @Override
    public int getOldListSize() {
        return mOldDocs.size();
    }

    @Override
    public int getNewListSize() {
        return mNewDocs.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return mOldDocs.get(oldItemPosition).getDocumentName().equals(mNewDocs.get(
                newItemPosition).getDocumentName());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        final Document oldDoc = mOldDocs.get(oldItemPosition);
        final Document newDoc = mNewDocs.get(newItemPosition);

        Log.d("LLama Diff Old", oldDoc.getDocumentName());
        Log.d("LLama Diff New", newDoc.getDocumentName());
        return oldDoc.getDocumentName().equals(newDoc.getDocumentName());
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}
