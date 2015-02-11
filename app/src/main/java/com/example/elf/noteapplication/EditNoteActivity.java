package com.example.elf.noteapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.SimpleTimeZone;


public class EditNoteActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);
        titleEditText = (EditText) findViewById(R.id.editTitle);
        noteEditText = (EditText) findViewById(R.id.editNote);
        dateText = (TextView) findViewById(R.id.dateFormatView);
        saveButton = (Button) findViewById(R.id.saveButton);
        btnCancel = (Button) findViewById(R.id.btnCancel);

        btnCancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult( RESULT_CANCELED, new Intent());
                finish();
            }
        });


        saveButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isInEditMode) {
                    Intent returnIntent = new Intent();
                    Date date = Calendar.getInstance().getTime();
                    Note note = new Note(titleEditText.getText().toString(), noteEditText.getText().toString(), date);
                    dateText.setText(note.getFormatDate());
                    returnIntent.putExtra("Note", note);
                    setResult(RESULT_OK, returnIntent);
                    finish();
                } else {
                    isInEditMode = true;
                    titleEditText.setEnabled(true);
                    noteEditText.setEnabled(true);
                    saveButton.setText("Save");
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Serializable extra = getIntent().getSerializableExtra("Note");
        if( extra != null ){
            //если есть экста то значит мы пришли из другого активити
            //выключаем режим редактирования и меняем кнопку
            isInEditMode = false;
            saveButton.setText("Edit");
            Note note = (Note) extra;
            titleEditText.setText(note.getTitle());
            noteEditText.setText(note.getNote());
            dateText.setText(note.getFormatDate());
            titleEditText.setEnabled(false);
            noteEditText.setEnabled(false);
        }else{
            //Включаем режим добавления записи, так как без
            //данных на это активити мы можем попасть только в режим добавления
            isAddinNote = true;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_note, menu);
        //Если в режиме добавления записи удаляем пункт меню удаление
        if( isAddinNote ){
            menu.removeItem( R.id.delet_note_menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder( this );
        alertBuilder.setTitle(R.string.warn_title);
        alertBuilder.setMessage(R.string.sure_ask);
        alertBuilder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Удаляем и передаем сигнал о том, что удалили
                Intent intent = new Intent();
                setResult(RESULT_DEL, intent);
                finish();
            }
            });
        alertBuilder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alertBuilder.create().show();
        return true;
    }

    //Константа сигнализирующая об удалении
    public static final int RESULT_DEL = -500;

    private boolean isInEditMode = true;
    private boolean isAddinNote = false;
    private EditText titleEditText;
    private EditText noteEditText;
    private TextView dateText;
    private Button saveButton;
    private Button btnCancel;
}
