package com.example.d.todo_app;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {
    Context context;
    Cursor cursor;
    DatabaseHelper db;
    SQLiteDatabase mDatabase;
    public CustomAdapter(Context context, Cursor cursor,DatabaseHelper db){
        this.context=context;
        this.cursor=cursor;
        this.db=db;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.item_row_todo,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        if (!cursor.moveToPosition(position)) {
            return;
        }
        String task=cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_2));
        final int id =cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COL_1));


        holder.tvTodoText.setText(task);
        holder.delButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               //DELETE DATABSE
                mDatabase=db.getWritableDatabase();
                mDatabase.delete(DatabaseHelper.TABLE_NAME,DatabaseHelper.COL_1+"="+id,null);
                // todoItems.remove(position);
                swapCursor(mDatabase.query
                        (DatabaseHelper.TABLE_NAME,
                                null,
                                null,
                                null,
                                null,
                                null,
                                null));}
        });
        holder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog=new Dialog(context);
                dialog.setContentView(R.layout.dialog_layout);
                dialog.setTitle("Re-type the new task below");

                Button dialogEditButton=(Button)dialog.findViewById(R.id.dialogButton);
                dialogEditButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText dialogueText=(EditText)dialog.findViewById(R.id.dialogEditText);
                        String dialogTask=dialogueText.getText().toString();
                        ContentValues cv=new ContentValues();
                        cv.put(DatabaseHelper.COL_2,dialogTask);
                        mDatabase=db.getWritableDatabase();
                        mDatabase.update(DatabaseHelper.TABLE_NAME,cv,DatabaseHelper.COL_1 +"="+id,null );
                        swapCursor(mDatabase.query
                                (DatabaseHelper.TABLE_NAME,
                                        null,
                                        null,
                                        null,
                                        null,
                                        null,
                                        null));
                        dialog.cancel();
                    }
                });
                dialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }
    public void swapCursor(Cursor myCursor){
        if(cursor!=null){
            cursor.close();
        }

        cursor=myCursor;
        if(myCursor!=null){
            notifyDataSetChanged();
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
    TextView tvTodoText;
    Button editButton;
    Button delButton;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvTodoText=(TextView)itemView.findViewById(R.id.tvTodoText);
            editButton=(Button)itemView.findViewById(R.id.editButton);
            delButton=(Button)itemView.findViewById(R.id.delButton);

        }
    }
}
