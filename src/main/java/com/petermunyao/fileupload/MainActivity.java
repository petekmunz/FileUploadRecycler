package com.petermunyao.fileupload;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.util.Log;

import java.util.List;

public class MainActivity extends AppCompatActivity implements ChooseFileInterface {

    private static final int DOC_REQUEST_CODE = 123;
    private RecyclerAdapter mAdapter;
    private int positionOnRecycler;
    private HelperViewModel helperViewModel;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        helperViewModel = ViewModelProviders.of(this).get(HelperViewModel.class);
        mRecyclerView = findViewById(R.id.Recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this, RecyclerView.VERTICAL, false));
        mRecyclerView.setHasFixedSize(true);

        helperViewModel.getmLiveData().observe(this, new Observer<List<Document>>() {
            @Override
            public void onChanged(List<Document> documents) {
                if (!documents.isEmpty()) {
                    Log.d("LLama", "Is called");
                    if (mAdapter == null) {
                        Log.d("LLama", "Is null");
                        mAdapter = new RecyclerAdapter(MainActivity.this, MainActivity.this);
                        mRecyclerView.setAdapter(mAdapter);
                    }
                    mAdapter.setList(documents);
                    Log.d("LLama Adapter", String.valueOf(mAdapter.hashCode()));
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == DOC_REQUEST_CODE && resultCode == RESULT_OK) {
            Uri imageUri = data.getData();
            Cursor returnCursor =
                    getContentResolver().query(imageUri, null, null, null, null);
            int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
            returnCursor.moveToFirst();
            String docName = returnCursor.getString(nameIndex);
            returnCursor.close();
            helperViewModel.updateDocumentName(positionOnRecycler, docName);
            Log.d("LLama", "Changed Value");
        }
    }

    @Override
    public void chooseFile(int position) {
        positionOnRecycler = position;
        Intent docIntent = new Intent(Intent.ACTION_GET_CONTENT);
        docIntent.setType("image/jpeg");
        startActivityForResult(Intent.createChooser(docIntent, "Choose File"), DOC_REQUEST_CODE);
    }
}
