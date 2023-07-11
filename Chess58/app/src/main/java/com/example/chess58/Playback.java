    package com.example.chess58;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavType;

import com.google.android.material.internal.ParcelableSparseArray;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

    public class Playback extends AppCompatActivity {

    Toolbar toolbar;
    private ListView listView;
    private ArrayList<Recording> recordings;
    private ArrayList<String> recordNames;
    private Recording selectedRecording;
    private int selectedId;



        public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater mi = getMenuInflater();
        mi.inflate(R.menu.menu_playback, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.playback_ByDate:
                try {
                    sortListByDate();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.playback_playReplay:
                showSelectedItem(selectedId);
                break;
            case R.id.playback_ByTitle:
                sortListByTitle();
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    /**
     * bundle up recording and pass to next activity - play
     * @param position
     */
    private void showSelectedItem (int position) {
        // in case user did not select any item
        if (position < 0) return;
        // or else we pass information to next activity
        Bundle bundle = new Bundle();
        Intent intent = new Intent(this, Playback_play.class);
        bundle.putParcelable("recording", this.recordings.get(position));
        intent.putExtras(bundle);
        startActivity(intent);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playback);

        //setup tool bar
        toolbar = findViewById(R.id.toolbar_playback);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listView = findViewById(R.id.playback_list);
        recordings = AppData.ReadData();
        try {
            showRecordings();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // initialize selected item ID to -1, meaning no item has yet to be selected
        selectedId = -1;
        // select item from the list
        listView.setOnItemClickListener((parent, view, position, id) -> this.selectedId = position);
    }
    private void sortListByDate () throws ParseException {
        int length = recordings.size();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        for (int j = 0; j < length; j++) {
            Date date = format.parse(recordings.get(j).getDate());
            int index = j;
            for (int i = j; i < length; i++) {
                System.out.println(i);
                Recording current = recordings.get(i);
                Date temp = format.parse(current.getDate());
                if (date.compareTo(temp) < 0) {
                    date = temp;
                    index = i;
                }
            }
            Recording removed = recordings.remove(index);
            recordings.add(j, removed);
        }
        try {
            showRecordings();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void sortListByTitle (){
        recordings.sort(Comparator.comparing(Recording::getTitle));
        try {
            showRecordings();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * mocking data for recording
     *
     * @return recording
     */
//    private Recording hardcodeRecording () {
//        Recording r = new Recording();
//        r.SetTitle("game");
//        r.RecordMove(new Move("w", "P", null, 1,
//                0,2,0,1,1,
//                false, 'Q', true, false,false, false));
//        r.RecordMove(new Move("b", "P", null, 6,
//                2,5,2,1,1,
//                false, 'Q', true, false,false, false));
//        r.RecordMove(new Move("w", "P", null, 1,
//                3,3,3,1,1,
//                false, 'Q', true, false,false, false));
//        r.RecordMove(new Move("b", "P", null, 6,
//                4,4,4,1,1,
//                false, 'Q', true, false,false, false));
//        r.RecordMove(new Move("w", "P", null, 1,
//                5,3,5,1,1,
//                false, 'Q', true, false,false, false));
//        r.RecordMove(new Move("b", "P", null, 6,
//                7,4,7,1,1,
//                false, 'Q', true, false,false, false));
//        r.RecordMove(new Move("w", "P", null, 1,
//                1,3,1,1,1,
//                false, 'Q', true, false,false, false));
//        r.RecordMove(new Move("b", "R", null, 7,
//                7,5,7,1,1,
//                false, 'Q', true, false,false, false));
//        r.RecordMove(new Move("w", "N", null, 0,
//                1,2,2,1,1,
//                false, 'Q', true, false,false, false));
//        return r;
//
//    }

    /**
     * show recording list for playback
     * @throws Exception
     */
    protected void showRecordings () throws Exception {
        /*
        hardcoded data
         */
        /*
        recordings = new ArrayList<>();
        recordings.add(hardcodeRecording());
        recordings.add(new Recording());

         */
        recordNames = new ArrayList<>();
        for (Recording r: recordings) {
            recordNames.add(r.getTitle() + " " + r.getDate());
        }
        ArrayAdapter adapter = new ArrayAdapter<>(this, R.layout.recording, recordNames);
        listView.setAdapter(adapter);
    }

    protected void OpenMainActivity(View view){

    }
}
