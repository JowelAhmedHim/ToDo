package com.example.todoapp;

import android.content.Context;
import android.graphics.Paint;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder>{

    private Context context;
    private ArrayList<TaskModel> taskModels;
    private DbHelper dbHelper;

    public TaskAdapter(Context context, ArrayList<TaskModel> taskModels) {
        this.context = context;
        this.taskModels = taskModels;
        dbHelper = new DbHelper(context);
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_task_item,parent,false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        TaskModel taskModel = taskModels.get(position);
        holder.task_cb.setText(taskModel.getTaskName());
        holder.task_cb.setChecked(taskModel.getStatus() == 1);

        if (holder.task_cb.isChecked()){
            holder.task_cb.setPaintFlags(holder.task_cb.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }else {
            holder.task_cb.setPaintFlags(holder.task_cb.getPaintFlags() | Paint.ANTI_ALIAS_FLAG);
        }

    }

    @Override
    public int getItemCount() {
        return taskModels.size();
    }

    class TaskViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {

        CheckBox task_cb;
        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);

            task_cb = itemView.findViewById(R.id.task_cb);
            task_cb.setOnClickListener(v->{
                TaskModel taskModel = taskModels.get(getAdapterPosition());
                taskModel.setStatus(task_cb.isChecked()?1:0);
                dbHelper.updateTask(taskModel);
                notifyItemChanged(getAdapterPosition());
            });
            task_cb.setOnCreateContextMenuListener(this);

        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.add(getAdapterPosition(),0,0,"Delete");
        }
    }
}
