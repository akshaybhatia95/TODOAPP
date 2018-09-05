package com.example.d.todo_app;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private SQLiteDatabase mDatabase;
    EditText etTask;
    Button addButton;
    ArrayList<String> todoListItems;
    RecyclerView recyclerView;
    DatabaseHelper db;
   Cursor cursor;
   CustomAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etTask=(EditText) findViewById(R.id.etTask);
        addButton=(Button)findViewById(R.id.addButton);
        todoListItems=new ArrayList<>();
        recyclerView=findViewById(R.id.rvTodo);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        db=new DatabaseHelper(this);
        mDatabase=db.getWritableDatabase();
        adapter=new CustomAdapter(this,getCursor(),db);
        recyclerView.setAdapter(adapter);


        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(etTask.getText().toString().trim().length()==0){
                   Toast.makeText(MainActivity.this,"Cannot add empty task",Toast.LENGTH_SHORT).show();
                   return;
               }
               String todoTask= etTask.getText().toString();
              // todoListItems.add(todoTask);

                ContentValues contentValues=new ContentValues();
                contentValues.put(DatabaseHelper.COL_2,todoTask);
                long result= mDatabase.insert(DatabaseHelper.TABLE_NAME,null,contentValues);
                adapter.swapCursor(getCursor());
                etTask.getText().clear();



            }

        });

    }

    public Cursor getCursor() {
        cursor=mDatabase.query
                (DatabaseHelper.TABLE_NAME,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null);
        final CustomAdapter adapter=new CustomAdapter(MainActivity.this,cursor,db);
        return cursor;
    }
}
