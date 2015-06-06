package gyri.taskkeeper.taskfragments;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.support.v4.content.CursorLoader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import gyri.taskkeeper.R;
import gyri.taskkeeper.data.TaskContract;

/**
 * Created by CWL on 05-01-2015.
 */




public  class DetailFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

    public static final String LOG_TAG = DetailFragment.class.getSimpleName();
    public static final String TASK_SHARE_HASH_TAG = " #TASKSETGO";
    private static final int TASK_DETAIL_LOADER = 1;
    public static final String TASK_ID_KEY = "task_id";
    private ShareActionProvider _ShareActionProvider;
    private String _TaskShareString;
    TextView _TaskTitleDetail;
    TextView _TaskDescriptionDetail;
    TextView _StartDateDetail;
    TextView _StartTimeDetail;
    TextView _StopDateDetail;
    TextView _StopTimeDetail;
    TextView _TimeLeftDetail;
    String _TaskStopDateString;
    String _TaskStopTimeString;
    String _TaskStartDateString;
    String _TaskStartTimeString;
    String _TimeLeftString;



    private static final String[] TASK_DETAIL_COLUMNS = {

            TaskContract.TaskEntry.TABLE_NAME + "." + TaskContract.TaskEntry._ID,
            TaskContract.TaskEntry.COLUMN_TASK_TITLE,
            TaskContract.TaskEntry.COLUMN_TASK_DESC,
            TaskContract.TaskEntry.COLUMN_START_DATETEXT,
            TaskContract.TaskEntry.COLUMN_STOP_DATETEXT,
            TaskContract.TaskEntry.COLUMN_START_TIMETEXT,
            TaskContract.TaskEntry.COLUMN_STOP_TIMETEXT,
    };


    public DetailFragment() {
        setHasOptionsMenu(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        _TaskTitleDetail = (TextView)rootView.findViewById(R.id.task_title_detail);
        _TaskDescriptionDetail = (TextView)rootView.findViewById(R.id.task_description_detail);
        _StartDateDetail = (TextView)rootView.findViewById(R.id.task_start_date_detail);
        _StartTimeDetail = (TextView)rootView.findViewById(R.id.task_start_time_detail);
        _StopDateDetail = (TextView)rootView.findViewById(R.id.task_stop_date_detail);
        _StopTimeDetail = (TextView)rootView.findViewById(R.id.task_stop_time_detail);
        _TimeLeftDetail = (TextView) rootView.findViewById(R.id.task_timeleft_detail);

        return rootView;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.detailfragment,menu);
        //Obtain Menu Item
        MenuItem _MenuItem = menu.findItem(R.id.action_share);
        //Get the Provider and hold onto it to set or change share intent
        _ShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(_MenuItem);

        //Attach an Intent to ShareActionProvider
        // If onLoadFinished happens before this, we can go ahead and set the share intent now.
        if(_ShareActionProvider!= null){
            _ShareActionProvider.setShareIntent(intentForShareActionProvider());
            Log.d(LOG_TAG, "ShareAction Provider Up");
            // startActivity(_ShareActionProvider);
        } else{
            Log.d(LOG_TAG,"Share Action Provider is Null");
        }
    }




    //Create appropriate Share Intent to Share the info
    private Intent intentForShareActionProvider(){
        Intent _ShareIntent = new Intent();
        _ShareIntent.setAction(Intent.ACTION_SEND);
        _ShareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        _ShareIntent.putExtra(Intent.EXTRA_TEXT,_TaskShareString+TASK_SHARE_HASH_TAG);
        _ShareIntent.setType("text/plain");
        return _ShareIntent;
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        Long taskRowId = getArguments().getLong(TASK_ID_KEY);


        return new CursorLoader(
                getActivity(),
                TaskContract.TaskEntry.buildTaskUri(taskRowId),
                TASK_DETAIL_COLUMNS,
                null,
                null,
                null
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        Log.d(LOG_TAG,"onLoadFinished");

        if(!data.moveToFirst()){ return;}
        Long _TaskRowId = data.getLong(TaskListFragment.COL_TASK_ID);


        String _TaskTitleString = data.getString(TaskListFragment.COL_TASK_TITLE);
        _TaskTitleDetail.setText(_TaskTitleString);
        String _TaskDescriptionString = data.getString(TaskListFragment.COL_TASK_DESC);
          _TaskDescriptionDetail.setText(_TaskDescriptionString);

        _TaskStartDateString = data.getString(TaskListFragment.COL_START_DATETEXT);
         _StartDateDetail.setText(_TaskStartDateString);

        _TaskStartTimeString = data.getString(TaskListFragment.COL_START_TIMETEXT);
        _StartTimeDetail.setText(_TaskStartTimeString);


        _TaskStopDateString = data.getString(TaskListFragment.COL_STOP_DATETEXT);
        _StopDateDetail.setText(_TaskStopDateString);

        _TaskStopTimeString = data.getString(TaskListFragment.COL_STOP_TIMETEXT);
        _StopTimeDetail.setText(_TaskStopTimeString);

        _TaskShareString = String.format("Task : %s DeadLine Set %s %s", _TaskTitleString,_TaskStopDateString,_TaskStopTimeString);

        // If onCreateOptionsMenu has already happened, we need to update the share intent now.
        if (_ShareActionProvider != null) {
            _ShareActionProvider.setShareIntent(intentForShareActionProvider());
        }
        findTimeLeft();


    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle _args = getArguments();
        Log.d(LOG_TAG,"DetailFragment OnActivity Created");
        if(_args != null && _args.containsKey(DetailFragment.TASK_ID_KEY)) {
            Log.d(LOG_TAG,"DetailFragment Bundle Arguements Received");
            getLoaderManager().initLoader(TASK_DETAIL_LOADER, null, this);
        }
    }

    public void findTimeLeft(){

        String _StopDateTimeString = _TaskStopDateString + " " + _TaskStopTimeString;
        String _StartDateTimeString = _TaskStartDateString + " " + _TaskStartTimeString;
        Log.d(LOG_TAG,_StopDateTimeString);
        Date _FinishDate = null;
        Date _BeginDate = null;
        Date _CurrentDate = new Date();
        Long _PercentageDone = 0l;



        SimpleDateFormat _Format = new SimpleDateFormat("dd/mm/yyyy HH:mm");
        try{
            _FinishDate = _Format.parse(_StopDateTimeString);
            _BeginDate = _Format.parse(_StartDateTimeString);
        } catch (ParseException e){
            Log.d(LOG_TAG , e.toString());
        }

        Long _diffCurr = _FinishDate.getTime() - _CurrentDate.getTime();
        Long _diffMinutesCurr = _diffCurr / (60 * 1000) % 60;
        Long _diffHoursCurr = _diffCurr / (60 * 60 * 1000);
        //Log.d(LOG_TAG , _diffHoursCurr.toString() + " Hours" + _diffMinutesCurr + " minutes");
        if(_diffMinutesCurr > 0 || _diffHoursCurr > 0){
            _TimeLeftString = _diffHoursCurr.toString() + " Hours" + _diffMinutesCurr + " minutes" + " left";

        } else {
            _TimeLeftString = " 0 Hours" + " 0 minutes" + " left";

        }


        _TimeLeftDetail.setText(_TimeLeftString);

//        Long _diffStart = _FinishDate.getTime() - _BeginDate.getTime();
//        Long _diffMinutesStart = _diffStart / (60 * 1000) % 60;
//        Long _diffHoursStart = _diffStart / (60 * 60 * 1000);
//
//        if(_diffStart > 0)
//            _PercentageDone = (_diffCurr/_diffStart)*100;



    }
}


