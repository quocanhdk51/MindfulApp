package com.example.buiquocanh.mindfulapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class tdlRecyclerViewAdapter extends RecyclerView.Adapter<tdlRecyclerViewAdapter.ViewHolder>{

    private ArrayList<Task> tasks;
    private Context context;
    private DataBaseHelper dataBaseHelper;

    //This is my todo list recyclerview adpation
    public tdlRecyclerViewAdapter(Context context, ArrayList<Task> tasks) {
        this.context = context;
        this.tasks = tasks;
        dataBaseHelper = new DataBaseHelper(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_context, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        final Task task = tasks.get(i);
        if(task.getStatus())
            viewHolder.statusCheckBox.setChecked(true);
        else
            viewHolder.statusCheckBox.setChecked(false);

        viewHolder.activityName.setText(task.getActivityName());

        viewHolder.statusCheckBox.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (((CheckBox) v).isChecked()) {
                    tasks.get(i).setStatus(true);
                    dataBaseHelper.updateTaskStatus("1", task.getActivityName());
                }
                else {
                    tasks.get(i).setStatus(false);
                    dataBaseHelper.updateTaskStatus("0", task.getActivityName());
                }
            }
        });

        viewHolder.foreGround.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {
                openInputDialogForEditingData(i, task.getActivityName());
                return false;
            }
        });


    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        CheckBox statusCheckBox;
        TextView activityName;
        RelativeLayout foreGround, backGround;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            statusCheckBox = itemView.findViewById(R.id.statusCheckBox);
            activityName = itemView.findViewById(R.id.activityName);
            foreGround = itemView.findViewById(R.id.foreGround);
            backGround = itemView.findViewById(R.id.backGround);

        }
    }

    public void removeItem(int position) {
        tasks.remove(position);
        // notify the item removed by position
        // to perform recycler view delete animations
        // NOTE: don't call notifyDataSetChanged()
        notifyItemRemoved(position);
    }

    public void restoreItem(Task task, int position) {
        tasks.add(position, task);
        // notify item added by position
        notifyItemInserted(position);
    }

    private void openInputDialogForEditingData(final int pos, final String oldTaskName) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Title");

        // Set up the input
        final EditText input = new EditText(context);
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                tasks.get(pos).setActivityName(input.getText().toString());
                dataBaseHelper.editTask(input.getText().toString(), oldTaskName);
                notifyItemChanged(pos);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }
}
