package com.example.elf.noteapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class ListNoteActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_note);
        notesListView = (ListView)findViewById(R.id.notesListView);
        notesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent noteEditIntent = new Intent( view.getContext(), EditNoteActivity.class);
                noteEditIntent.putExtra( "Note", notes.get(position));
                editingNoteId = position;
                startActivityForResult(noteEditIntent, 1);
            }
        });
        registerForContextMenu( notesListView );
        populateNotes();
    }


    //Обновляет список записей на активити
    public void populateNotes(){
        List<String> values = new ArrayList<String>();

        for( Note note: notes)
        {
            values.add(note.getTitle() + " " + note.getFormatDate());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, values );
        notesListView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list_note, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Handle action bar item clicks here. The action bar will
        //automatically handle clicks on the Home/Up button, so long
        //as you specify a parent activity in AndroidManifest.xml.
        Intent editNoteIntent = new Intent(this, EditNoteActivity.class);
        startActivityForResult( editNoteIntent, 1 );
        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        //Удаляем запись
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        notes.remove(menuInfo.position);
        populateNotes();
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
       if(resultCode == RESULT_CANCELED){
          return;
       }
       if(resultCode == RESULT_OK) {
           Serializable extra = data.getSerializableExtra("Note");
           if (extra != null) {
               //Проверяем обновление идет или добаление записи
               if (editingNoteId > -1) {
                   //Обновление
                   notes.set(editingNoteId, (Note) extra);
                   editingNoteId = -1;
               } else {
                   notes.add((Note) extra);
               }
               populateNotes();
           }
       }
    }

    private List<Note> notes = new ArrayList<Note>();
    private ListView notesListView;
    private int editingNoteId = -1;
}
