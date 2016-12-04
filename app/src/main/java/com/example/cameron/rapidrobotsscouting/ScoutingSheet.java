package com.example.cameron.rapidrobotsscouting;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Set;


public class ScoutingSheet extends AppCompatActivity {
    int recordIndex = 0;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    private void writeToFile(String data, Context context) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("config.txt", Context.MODE_APPEND));
            outputStreamWriter.append(data);
            outputStreamWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
            //("Exception", "File write failed: " + e.toString());
        }
    }

    private String readFromFile(Context context) {

        String ret = "";

        try {
            InputStream inputStream = context.openFileInput("config.txt");

            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ((receiveString = bufferedReader.readLine()) != null) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            //e.("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            e.printStackTrace();
            //e.("login activity", "Can not read file: " + e.toString());
        }

        return ret;
    }


    private String ReadCheckbox(int id) {
        String ret = "False";
        CheckBox tmpcheckbox = (CheckBox) findViewById(id);
        if (tmpcheckbox.isChecked()) {
            ret = "True";
        }
        return ret;
    }

    public void SetCheckbox(int id, String value){
        value = value.trim();
        CheckBox tmpcheckbox = (CheckBox) findViewById(id);
        if (value.equals("True")){
            tmpcheckbox.setChecked(true);
        }
        else{
            tmpcheckbox.setChecked(false);
        }
    }



    private String ReadTextBox(int id) {

        String ret;
        EditText tmp = (EditText) findViewById(id);
        ret = tmp.getText().toString();

        return ret;
    }

    public void SetTextbox(int id, String value){
        value = value.trim();
        EditText tmp = (EditText) findViewById(id);
        tmp.setText(value);
    }


    private String ReadCapBallHeight(int id) {
        String ret;
        String [] CapBallOptions = getResources().getStringArray(R.array.CapBallHeightArray);
        Spinner spin = (Spinner) findViewById(id);
        int item = (int) spin.getSelectedItemId();
        ret = CapBallOptions[item];
        //ret = (String) String.valueOf(item);

        return ret;
    }
    private String ReadParkingPos(int id) {
        String ret;
        String [] ParkingOptions = getResources().getStringArray(R.array.ParkingPositionsArray);
        Spinner spin = (Spinner) findViewById(id);
        int item = (int) spin.getSelectedItemId();
        ret = ParkingOptions[item];
        //ret = (String) String.valueOf(item);

        return ret;
    }
    public static int useLoop(String[] arr, String targetValue) {
        int i=0;
        for(String s: arr){
            if(s.equals(targetValue)) {
                break;
            }
            i++;
        }
        return i;
    }

    private void SetCapBallHeight(int id,String value) {
        int itemid;
        value = value.trim();
        String [] CapBallOptions = getResources().getStringArray(R.array.CapBallHeightArray);
        Spinner spin = (Spinner) findViewById(id);
        itemid = useLoop(CapBallOptions,value);
        spin.setSelection(itemid);
        //ret = (String) String.valueOf(item);
    }
    private void SetParkingPos(int id, String value) {
        int itemid;
        value = value.trim();
        String [] ParkingOptions = getResources().getStringArray(R.array.ParkingPositionsArray);
        Spinner spin = (Spinner) findViewById(id);
        itemid = useLoop(ParkingOptions,value);
        spin.setSelection(itemid);
        //ret = (String) String.valueOf(item);
    }
    private void ClearGUIValues(){
        SetTextbox(R.id.teamNumberTxt, "");

        // StrAuBeaconActivation + ", " +
        SetCheckbox(R.id.AubeaconActivation, "False");
        //        StrAuPartialParking + ", " +
        SetParkingPos(R.id.SpinnerParkingPos, "No Specific position");
        //      StrAuMovedCapBall + ", " +
        SetCheckbox(R.id.AuMovedCapBall, "False");
        //      StrAuCenter + ", "+
        SetCheckbox(R.id.checkBoxAuCenter, "False");
        //      StrAuNumberPartCenterVortex  + ", " +
        SetTextbox(R.id.particlesInCenterTxt, "");
        //      StrAuCorner + ", " +
        SetCheckbox(R.id.CheckBoxAuCorner, "False");
        //      StrAuNumberPartCornerVortex + ", "+w
        SetTextbox(R.id.particlesInCornerVortexTxt, "");
        //      StrToBeaconActivation + ", " +
        SetCheckbox(R.id.ToBeaconActivation, "False");
        //      StrToCenterPart + ", " +
        SetCheckbox(R.id.ToCenterParticles, "False");
        //      StrToCornerPart + ", " +
        SetCheckbox(R.id.ToCornerParticles, "False");
        //StrCapBallHeight + ", ";
        SetCapBallHeight(R.id.ToCapBallHeight, "None");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MediaPlayer mp = MediaPlayer.create(this, R.raw.prettygood);
        mp.start();
        setContentView(R.layout.activity_scouting_sheet);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Spinner spinner = (Spinner) findViewById(R.id.ToCapBallHeight);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.CapBallHeightArray, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String StrTeamNumber = ReadTextBox(R.id.teamNumberTxt);

                String StrAuBeaconActivation = ReadCheckbox(R.id.AubeaconActivation);

                String StrAuMovedCapBall = ReadCheckbox(R.id.AuMovedCapBall);

                String StrParkingPos =  ReadParkingPos(R.id.SpinnerParkingPos);

                String StrAuCorner = ReadCheckbox(R.id.CheckBoxAuCorner);

                String StrAuCenter = ReadCheckbox(R.id.checkBoxAuCenter);

                String StrAuNumberPartCenterVortex = ReadTextBox(R.id.particlesInCenterTxt);

                String StrAuNumberPartCornerVortex = ReadTextBox(R.id.particlesInCornerVortexTxt);

                String StrCapBallHeight = ReadCapBallHeight(R.id.ToCapBallHeight);

                String StrToBeaconActivation = ReadCheckbox(R.id.ToBeaconActivation);

                String StrToCenterPart = ReadCheckbox(R.id.ToCenterParticles);

                String StrToCornerPart = ReadCheckbox(R.id.ToCornerParticles);

                String record = StrTeamNumber + ", " +
                        StrAuBeaconActivation + ", " +
                        StrParkingPos + "," +
                        StrAuMovedCapBall + ", " +
                        StrAuCenter + ", "+
                        StrAuNumberPartCenterVortex  + ", " +
                        StrAuCorner + ", " +
                        StrAuNumberPartCornerVortex + ", "+
                        StrToBeaconActivation + ", " +
                        StrToCenterPart + ", " +
                        StrToCornerPart + ", " +
                    StrCapBallHeight + ", ";

                writeToFile(record, view.getContext());
                Snackbar.make(view, "Saved File", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                    //                String record = StrTeamNumber + ", " +
                ClearGUIValues();
                }

        });


        Button buttonNext = (Button) findViewById(R.id.readNextFileButton);
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textString;
                textString = readFromFile(view.getContext());
                String[] values = textString.split(",");
                int idBaseOffset = (recordIndex-1) * 13;
                recordIndex=recordIndex+1;
                if (idBaseOffset<0) {
                    idBaseOffset = 0;
                }
                if (idBaseOffset>=values.length-1){
                    idBaseOffset = 0;
                    recordIndex=0;
                }

                if (values.length>=12) {
                    //                String record = StrTeamNumber + ", " +
                    SetTextbox(R.id.teamNumberTxt, values[idBaseOffset + 0]);

                    // StrAuBeaconActivation + ", " +
                    SetCheckbox(R.id.AubeaconActivation, values[idBaseOffset + 1]);
                    SetParkingPos(R.id.SpinnerParkingPos,values[idBaseOffset + 2]);
                    //      StrAuMovedCapBall + ", " +
                    SetCheckbox(R.id.AuMovedCapBall, values[idBaseOffset + 3]);
                    //      StrAuCenter + ", "+
                    SetCheckbox(R.id.checkBoxAuCenter, values[idBaseOffset + 4]);
                    //      StrAuNumberPartCenterVortex  + ", " +
                    SetTextbox(R.id.particlesInCenterTxt, values[idBaseOffset + 5]);
                    //      StrAuCorner + ", " +
                    SetCheckbox(R.id.CheckBoxAuCorner, values[idBaseOffset + 6]);
                    //      StrAuNumberPartCornerVortex + ", "+
                    SetTextbox(R.id.particlesInCornerVortexTxt, values[idBaseOffset + 7]);
                    //      StrToBeaconActivation + ", " +
                    SetCheckbox(R.id.ToBeaconActivation, values[idBaseOffset + 8]);
                    //      StrToCenterPart + ", " +
                    SetCheckbox(R.id.ToCenterParticles, values[idBaseOffset + 9]);
                    //      StrToCornerPart + ", " +
                    SetCheckbox(R.id.ToCornerParticles, values[idBaseOffset + 10]);
                    //StrCapBallHeight + ", ";
                    SetCapBallHeight(R.id.ToCapBallHeight,values[idBaseOffset+11]);
                }
                else {
                    ClearGUIValues();
                    Toast.makeText(view.getContext(),"File Empty!!!", Toast.LENGTH_SHORT).show();
                }

            }
        });
        Button buttonClear = (Button) findViewById(R.id.ClearTeamDataButton);

        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    Context context;
                    context = view.getContext();
                    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("config.txt", Context.MODE_PRIVATE));
                    outputStreamWriter.close();
                    Toast.makeText(view.getContext(),"File cleared", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                    //("Exception", "File write failed: " + e.toString());
                }
            }
        });


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scouting_sheet, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "ScoutingSheet Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.cameron.rapidrobotsscouting/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "ScoutingSheet Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.cameron.rapidrobotsscouting/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}


