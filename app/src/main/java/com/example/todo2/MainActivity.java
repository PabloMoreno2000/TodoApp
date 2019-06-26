package com.example.todo2;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class MainActivity extends Activity {

    //ArrayList
    ArrayList<String> items;
    ArrayAdapter<String> itemsAdapter;
    ListView lvItems;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvItems  = (ListView) findViewById(R.id.lvItems);

        readItems();

        itemsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);

        lvItems.setAdapter(itemsAdapter);

        //items.add("First item");

        //setup the listener on creationg
        setUpListenViewListener();


    }

    public void onAddItem(View v) {
        EditText etItem = (EditText) findViewById(R.id.etNewItem);

        String name = etItem.getText().toString();

        itemsAdapter.add(name);

        etItem.setText("");

        writeItems();

        Toast.makeText(getApplicationContext(), "Item added to the list", Toast.LENGTH_LONG).show();
    }

    private void setUpListenViewListener() {
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                items.remove(position);

                itemsAdapter.notifyDataSetChanged();

                Log.i("MainActivity", "Removed item " + position);

                writeItems();

                return true;
            }
        });
    }

    //returns data in which data is stored
    private File getDataFile() {
        return new File(getFilesDir(), "todo.txt");
    }

    //Read items from the file system
    private void readItems() {
        try {
            //creating array which the content of the file
            items = new ArrayList<String>(FileUtils.readLines(getDataFile(), Charset.defaultCharset()));
        } catch(IOException e) {
            e.printStackTrace();

            //just load an empty list
            items = new ArrayList<>();
        }
    }

    //Write items to the filesystem
    private void writeItems() {
        try {
            FileUtils.writeLines(getDataFile(), items);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
