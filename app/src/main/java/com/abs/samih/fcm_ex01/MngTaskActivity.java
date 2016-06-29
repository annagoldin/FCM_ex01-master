package com.abs.samih.fcm_ex01;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MngTaskActivity extends AppCompatActivity {
    private MyTaskAdapter myTaskAdapter;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mng_task_activity);
        listView = (ListView) findViewById(R.id.listView);
        myTaskAdapter = new MyTaskAdapter(this,R.layout.item_my_task);
        listView.setAdapter(myTaskAdapter);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent intent = new Intent(MngTaskActivity.this, AddTaskActivity.class);
                startActivity(intent);
            }
        });
       // replacing . with _
        String email= FirebaseAuth.getInstance().getCurrentUser().getEmail().replace('.', '_');
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(email);
        databaseReference.child("MyTasks").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                myTaskAdapter.clear();
                for (DataSnapshot ds:dataSnapshot.getChildren())
                {

                    MyTask myTask = ds.getValue(MyTask.class);
                    myTaskAdapter.add(myTask);
                    myTask.setTaskKey(ds.getKey());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();


    }
}
