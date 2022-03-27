package com.example.todoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton fab;
    private RecyclerView recyclerView;
    private ArrayList<TaskModel> arrayList = new ArrayList<>();
    private TaskAdapter taskAdapter;
    private DbHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DbHelper(this);
        arrayList = dbHelper.getData();

        fab = findViewById(R.id.fab);
        recyclerView = findViewById(R.id.recycler_view);
        fab.setOnClickListener(v -> {
            showDialog();
        });

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        taskAdapter = new TaskAdapter(this,arrayList);
        recyclerView.setAdapter(taskAdapter);


    }



    private void showDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        View view = LayoutInflater.from(this).inflate(R.layout.task_dialog, null);

        EditText taskName = view.findViewById(R.id.task_name);
        Button cancelBtn = view.findViewById(R.id.cancel_btn);
        Button addBtn = view.findViewById(R.id.add_btn);

        builder.setView(view);

        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);

        cancelBtn.setOnClickListener(v->{
            alertDialog.dismiss();
        });

        addBtn.setOnClickListener(v->{
            String task = taskName.getText().toString();
            if (task.equals("")){
                Toast.makeText(this, "Empty", Toast.LENGTH_SHORT).show();
            }else {
                TaskModel taskModel = new TaskModel(task,0);
                arrayList.add(taskModel);
                long id = dbHelper.insertTask(taskModel);
                taskAdapter.notifyDataSetChanged();
                alertDialog.dismiss();
            }
        });
        alertDialog.show();


    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==0){
           deleTask(item.getGroupId());
        }
        return super.onContextItemSelected(item);
    }

    private void deleTask(int position) {
        dbHelper.deleteTask(arrayList.get(position).getTaskId());
        arrayList.remove(position);
        taskAdapter.notifyItemRemoved(position);
    }
}