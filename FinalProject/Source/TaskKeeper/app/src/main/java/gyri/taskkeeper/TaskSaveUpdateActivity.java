package gyri.taskkeeper;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

import gyri.taskkeeper.entities.TaskEntity;
import gyri.taskkeeper.taskHelpers.DataHandler;
import gyri.taskkeeper.taskfragments.DatePickerFragment;
import gyri.taskkeeper.taskfragments.TimePickerFragment;


public class TaskSaveUpdateActivity extends ActionBarActivity {

    private static final String LOG_TAG = TaskSaveUpdateActivity.class.getSimpleName();
    EditText _TaskTitle;
    EditText _TaskDescription;
    Button _StartDate;
    Button _StartTime;
    Button _StopDate;
    Button _StopTime;
    Button _Save;
    Boolean _IsStartDate;
    Boolean _IsStartTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_save_update);


        if (savedInstanceState == null) {


            _TaskTitle = (EditText) findViewById(R.id.task_title);
            _TaskDescription = (EditText) findViewById(R.id.task_description);
            _StartDate = (Button) findViewById(R.id.start_date);
            _StartTime = (Button) findViewById(R.id.start_time);
            _StopDate = (Button) findViewById(R.id.stop_date);
            _StopTime = (Button) findViewById(R.id.stop_time);
            _Save = (Button) findViewById(R.id.task_save);

            _StartDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDatePicker(_StartDate);
                }
            });
            _StopDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDatePicker(_StopDate);
                }
            });
            _StartTime.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showTimePicker(_StartTime);
                }
            });
            _StopTime.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showTimePicker(_StopTime);
                }
            });
            _Save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    saveTask();
                }
            });

            setCurrentDateAndTime();

        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
    public void showTimePicker(TextView _TimeParam){

        TextView _StartTimeValidator = (TextView)findViewById(R.id.start_time);
        Bundle _args = getCurrentDateTimeDetails();

        if(_TimeParam == _StartTimeValidator)
            _IsStartTime = true;
        else
            _IsStartTime = false;

        TimePickerFragment _TimeFrag = new TimePickerFragment();
        _TimeFrag.setArguments(_args);
        // Set Call back to capture selected date
        _TimeFrag.setCallBack(_OnTime);
        _TimeFrag.show(getSupportFragmentManager(), "Time Picker");
    }




    public void showDatePicker(TextView _DateParam){

        TextView _StartDateValidator = (TextView)findViewById(R.id.start_date);
        Bundle _args = getCurrentDateTimeDetails();

        if(_DateParam == _StartDateValidator)
            _IsStartDate = true;
        else
            _IsStartDate = false;

        // Call fragment with respective arguments
        DatePickerFragment _DateFrag = new DatePickerFragment();
        _DateFrag.setArguments(_args);

        //  Set Call back to capture selected date
        _DateFrag.setCallBack(_Ondate);
        _DateFrag.show(getSupportFragmentManager(), "Date Picker");
    }


    DatePickerDialog.OnDateSetListener _Ondate = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

            String prefixString = "0";
            String dayOfMonthString = String.valueOf(dayOfMonth);
            String monthOfYearString = String.valueOf(monthOfYear+1);
            String yearString = String.valueOf(year);

            if(dayOfMonth < 10)
                dayOfMonthString = prefixString + dayOfMonthString;
            if((monthOfYear+1) < 10)
                monthOfYearString = prefixString + monthOfYearString;

            if(_IsStartDate)
                _StartDate.setText("StartDate: "+dayOfMonthString+"/"+monthOfYearString+"/"+yearString);
            else
                _StopDate.setText("StopDate: "+dayOfMonthString+"/"+monthOfYearString+"/"+yearString);
        }
    };



    TimePickerDialog.OnTimeSetListener _OnTime = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

            String prefixString = "0";
            String hourOfDayString = String.valueOf(hourOfDay);
            String minuteString = String.valueOf(minute);

            if(hourOfDay < 10)
                hourOfDayString = prefixString + hourOfDayString;
            if(minute < 10)
                minuteString = prefixString + minuteString;

            if(_IsStartTime)
                _StartTime.setText("StartTime: "+hourOfDayString+":"+minuteString);
            else
                _StopTime.setText("StopTime: "+hourOfDayString+":"+minuteString);

        }
    };

    public void setCurrentDateAndTime(){
        Bundle _args;
        _args = getCurrentDateTimeDetails();
        int dayOfMonth = _args.getInt("day");
        int monthOfYear = _args.getInt("month")+1;
        int year = _args.getInt("year");
        int hourOfDay = _args.getInt("hour");
        int minute = _args.getInt("minute");
        String prefixString = "0";
        String dayOfMonthString = String.valueOf(dayOfMonth);
        String monthOfYearString = String.valueOf(monthOfYear);
        String yearString = String.valueOf(year);
        String hourOfDayString = String.valueOf(hourOfDay);
        String minuteString = String.valueOf(minute);

        if(dayOfMonth < 10)
            dayOfMonthString = prefixString + dayOfMonthString;
        if(monthOfYear < 10)
            monthOfYearString = prefixString + monthOfYearString;
        if(hourOfDay < 10)
            hourOfDayString = prefixString + hourOfDayString;
        if(minute < 10)
            minuteString = prefixString + minuteString;


        _StartDate.setText("StartDate: "+dayOfMonthString+"/"+monthOfYearString+"/"+yearString);
        _StopDate.setText("StopDate: "+dayOfMonthString+"/"+monthOfYearString+"/"+yearString);
        _StartTime.setText("StartTime: "+hourOfDayString+":"+minuteString);
        _StopTime.setText("StopTime: "+hourOfDayString+":"+minuteString);
    }

    public Bundle getCurrentDateTimeDetails(){

        //Setup Current Date and Time Details to Bundle
        Calendar calender = Calendar.getInstance();
        Bundle _args = new Bundle();
        _args.putInt("year" , calender.get(Calendar.YEAR));
        _args.putInt("month" , calender.get(Calendar.MONTH));
        _args.putInt("day" , calender.get(Calendar.DAY_OF_MONTH));
        _args.putInt("hour" , calender.get(Calendar.HOUR_OF_DAY));
        _args.putInt("minute" , calender.get(Calendar.MINUTE));
        return _args;
    }


    public TaskEntity getTaskData(){
        String _StartDateButtonText = "StartDate: ";
        String _StopDateButtonText = "StopDate: ";
        String _StartTimeButtonText = "StartTime: ";
        String _StopTimeButtonText = "StopTime: ";

        TaskEntity _TaskEntity = new TaskEntity();
        _TaskEntity.set_TaskTitle(_TaskTitle.getText().toString());
        _TaskEntity.set_TaskDescription(_TaskDescription.getText().toString());
        _TaskEntity.set_StartDate(_StartDate.getText().toString().substring(_StartDateButtonText.length()));
        _TaskEntity.set_StartTime(_StartTime.getText().toString().substring(_StartTimeButtonText.length()));
        _TaskEntity.set_StopDate(_StopDate.getText().toString().substring(_StopDateButtonText.length()));
        _TaskEntity.set_StopTime(_StopTime.getText().toString().substring(_StopTimeButtonText.length()));

        return _TaskEntity;
    }

    public void saveTask(){
        TaskEntity _TaskToSave = getTaskData();
        FetchTasksTask _FetchTask = new FetchTasksTask(getApplicationContext());
        _FetchTask.execute(_TaskToSave);
    }

    public class FetchTasksTask extends AsyncTask<TaskEntity,Void,Long> {

        private final String LOG_TAG = FetchTasksTask.class.getSimpleName();
        Uri.Builder _UriBuilder = new Uri.Builder();
        Context _Context;

        public FetchTasksTask(Context _Context){
            this._Context = _Context;
        }


        @Override
        protected Long doInBackground(TaskEntity... params) {

            Long _TaskRowId = null;
            if(params.length == 0){
                return null;
            }

            TaskEntity _PrmTaskEntity = params[0];
            String TasksJsonStr = null;

            try {
                DataHandler _DataHandler = new DataHandler(_Context);
                _TaskRowId = _DataHandler.addTask(_PrmTaskEntity);


            } catch (Exception e) {
                Log.e(LOG_TAG, "Error ", e);
            }
            return _TaskRowId;
        }

        @Override
        protected void onPostExecute(Long aLong) {
            super.onPostExecute(aLong);
            Toast.makeText(getApplicationContext(),"Task Saved",Toast.LENGTH_LONG).show();
            Intent _Intent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(_Intent);
        }
    }





}
