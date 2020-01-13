package com.petermunyao.fileupload;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.petermunyao.fileupload.databinding.DocCardBinding;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {

    private List<Document> mList = new ArrayList<>();
    private Context mContext;
    private ChooseFileInterface fileInterface;

    public RecyclerAdapter(Context mContext, ChooseFileInterface fileInterface) {
        this.mContext = mContext;
        this.fileInterface = fileInterface;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        DocCardBinding myView = DataBindingUtil.inflate(
                LayoutInflater.from(mContext), R.layout.doc_card, parent, false);
        return new MyViewHolder(myView.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        Document doc = mList.get(position);
        holder.binding.setDocument(doc);
        holder.binding.btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fileInterface.chooseFile(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void setList(List<Document> newList) {
        //Bug is here - mList(the oldList) is same is same as newList. They should be different.
        if(!mList.isEmpty()){
            Log.d("LLama Diff Before", mList.get(0).getDocumentName());
        }
        Log.d("LLama In Set", "Inside");
        final DiffUtilHelper diffCallBack = new DiffUtilHelper(mList, newList);
        final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallBack);
        mList.clear();
        mList.addAll(newList);
        diffResult.dispatchUpdatesTo(this);
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        DocCardBinding binding;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }
    }

}
