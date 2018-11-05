package com.olczyk.android.androidarchitectureapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;


@EActivity(R.layout.activity_add_note)
public class AddEditNoteActivity extends AppCompatActivity {

    public static final String EXTRA_ID =
            "com.olczyk.android.androidarchitectureapp.EXTRA_ID";

    public static final String EXTRA_TITLE =
            "com.olczyk.android.androidarchitectureapp.EXTRA_TITLE";

    public static final String EXTRA_DESCRIPTION =
            "com.olczyk.android.androidarchitectureapp.EXTRA_DESCRIPTION";

    public static final String EXTRA_PRIORITY =
            "com.olczyk.android.androidarchitectureapp.EXTRA_PRIORITY";

    @ViewById
    EditText editTextTitle;

    @ViewById
    EditText editTextDescription;

    @ViewById
    NumberPicker numberPickerPriority;

    @AfterViews
    public void aVoid() {
        numberPickerPriority.setMinValue(1);
        numberPickerPriority.setMaxValue(10);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        Intent intent = getIntent();
        if(intent.hasExtra(EXTRA_ID)){
            setTitle("Edit note");
            editTextTitle.setText(intent.getExtras().getString(EXTRA_TITLE));
            editTextDescription.setText(intent.getExtras().getString(EXTRA_DESCRIPTION));
            numberPickerPriority.setValue(intent.getExtras().getInt(EXTRA_PRIORITY));
        } else {
            setTitle("Add note");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_note_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.saveNote:
                saveNote();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void saveNote() {
        String title = editTextTitle.getText().toString();
        String description = editTextDescription.getText().toString();
        int priority = numberPickerPriority.getValue();
        if(title.trim().isEmpty() || description.trim().isEmpty()){
            Toast.makeText(this, "Please insert the title and decription",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        Log.i("LOG", "ANA New note is: " + title);

        Intent data = new Intent();
        data.putExtra(EXTRA_TITLE, title);
        data.putExtra(EXTRA_DESCRIPTION, description);
        data.putExtra(EXTRA_PRIORITY, priority);
        int id = getIntent().getIntExtra(EXTRA_ID,-1);
        if(id != -1){
            data.putExtra(EXTRA_ID, id);
        }
        setResult(RESULT_OK, data);
        finish();
    }
}
