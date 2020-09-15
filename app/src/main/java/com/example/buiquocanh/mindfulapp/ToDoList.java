package com.example.buiquocanh.mindfulapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.InputType;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ToDoList extends AppCompatActivity implements tdlRecyclerTouchHelper.RecyclerItemTouchHelperListener{
    private ArrayList<Task> tasks;
    private tdlRecyclerViewAdapter adapter;
    private RecyclerView recyclerVIew;
    private ConstraintLayout constraintLayout;
    DataBaseHelper dataBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_list);

        tasks = new ArrayList<>();

        Toolbar toDoListActivityToolBar = findViewById(R.id.myToolBar);
        setSupportActionBar(toDoListActivityToolBar);

        TextView title = toDoListActivityToolBar.findViewById(R.id.toolbarTitle);
        title.setText("To-Do List");
        title.setTextSize(15);
        title.setTextColor(Color.WHITE);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initData();
    }


    private void initData() {
        dataBaseHelper = new DataBaseHelper(this);
        getData();
        initRecyclerView();
    }


    private void getData() {
        Cursor data = dataBaseHelper.getData();
        while (data.moveToNext()) {
            tasks.add(new Task(data.getString(1).equals("1"), data.getString(2)));
        }
    }

    private void initRecyclerView() {
        constraintLayout = findViewById(R.id.tdlConstraintLayout);
        recyclerVIew = findViewById(R.id.toDoList);
        adapter = new tdlRecyclerViewAdapter(this, tasks);
        recyclerVIew.setAdapter(adapter);
        recyclerVIew.setLayoutManager(new LinearLayoutManager(this));

        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new tdlRecyclerTouchHelper(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerVIew);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater tdlMenuInflater = getMenuInflater();
        tdlMenuInflater.inflate(R.menu.todolist_item_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.addNewTask:
                openInputDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof tdlRecyclerViewAdapter.ViewHolder) {
            // get the removed item name to display it in snack bar
            String name = tasks.get(viewHolder.getAdapterPosition()).getActivityName();

            // backup of removed item for undo purpose
            final Task deletedItem = tasks.get(viewHolder.getAdapterPosition());
            final int deletedIndex = viewHolder.getAdapterPosition();

            // remove the item from recycler view
            adapter.removeItem(viewHolder.getAdapterPosition());

            // showing snack bar with Undo option
            Snackbar snackbar = Snackbar
                    .make(constraintLayout, name + " removed from cart!", Snackbar.LENGTH_LONG);

            snackbar.addCallback(new Snackbar.Callback() {
                @Override
                public void onDismissed(Snackbar transientBottomBar, int event) {
                    super.onDismissed(transientBottomBar, event);
                    if (event != Snackbar.Callback.DISMISS_EVENT_ACTION)
                        dataBaseHelper.deleteData(deletedItem.getActivityName());
                }
            });

            snackbar.setAction("UNDO", new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    // undo is selected, restore the deleted item
                    adapter.restoreItem(deletedItem, deletedIndex);
                }
            });
            snackbar.setActionTextColor(Color.YELLOW);
            snackbar.show();
        }
    }

    private void openInputDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Title");

        // Set up the input
        final EditText input = new EditText(this);
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                addData(input.getText().toString());
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

    public void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void addData(String newTask) {
        boolean insertData = dataBaseHelper.addData("0", newTask);
        if (insertData)
            tasks.add(new Task(false, newTask));
        else
            toastMessage("fail");
    }

}
