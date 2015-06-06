package gyri.taskkeeper.taskfragments;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.Fragment;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

import gyri.taskkeeper.AboutActivity;
import gyri.taskkeeper.DetailActivity;
import gyri.taskkeeper.R;
import gyri.taskkeeper.TaskSaveUpdateActivity;
import gyri.taskkeeper.data.TaskContract;
import gyri.taskkeeper.taskHelpers.TaskAdapter;

/**
 * A placeholder fragment containing a simple view.
 * Created by Ninto on 19/12/2014.
 */

public class TaskListFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String SELECTED_KEY = "list_item_selected";
    private static String tempTask;
    private static final int TASK_LOADER = 0;
    public static int _Position;


    private static final String[] TASK_COLUMNS = {

            TaskContract.TaskEntry.TABLE_NAME + "." + TaskContract.TaskEntry._ID,
            TaskContract.TaskEntry.COLUMN_TASK_TITLE,
            TaskContract.TaskEntry.COLUMN_TASK_DESC,
            TaskContract.TaskEntry.COLUMN_START_DATETEXT,
            TaskContract.TaskEntry.COLUMN_STOP_DATETEXT,
            TaskContract.TaskEntry.COLUMN_START_TIMETEXT,
            TaskContract.TaskEntry.COLUMN_STOP_TIMETEXT,
    };

    //Indices To above Column
    public static final int COL_TASK_ID = 0;
    public static final int COL_TASK_TITLE = 1;
    public static final int COL_TASK_DESC = 2;
    public static final int COL_START_DATETEXT = 3;
    public static final int COL_STOP_DATETEXT = 4;
    public static final int COL_START_TIMETEXT = 5;
    public static final int COL_STOP_TIMETEXT = 6;


    TaskAdapter _taskListAdapter;
    ListView _taskListViewLayout;
    private final String LOG_TAGER = TaskListFragment.class.getSimpleName();

    public TaskListFragment() {
    }


    public interface Callback{
        public void onItemSelected(Long _TaskRowId);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

        if(_Position != ListView.INVALID_POSITION){
            outState.putInt(SELECTED_KEY,_Position);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }




    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_common, menu);

    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        int id = item.getItemId();
        if(id==R.id.action_about){
            Intent _AboutIntent = new Intent(getActivity(),AboutActivity.class);
            startActivity(_AboutIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        Button _AddNewTaskButton = (Button)rootView.findViewById(R.id.new_task_btn);


        //Set Click Listener To the Button
        _AddNewTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent _TaskSaveIntent = new Intent(getActivity(),TaskSaveUpdateActivity.class);
                startActivity(_TaskSaveIntent);

            }
        });

        _taskListAdapter = new TaskAdapter(getActivity(),null,0);
        _taskListViewLayout = (ListView) rootView.findViewById(R.id.listView_tasks);
        _taskListViewLayout.setAdapter(_taskListAdapter);

        //OnClicking an Item
        _taskListViewLayout.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                TaskAdapter _Adapter = (TaskAdapter) parent.getAdapter();
                Cursor _TempCursor = _Adapter.getCursor();
                if(null != _TempCursor && _TempCursor.moveToPosition(position)){
                    Long _TaskRowId =_TempCursor.getLong(COL_TASK_ID);
                    Log.d(LOG_TAGER, "Task Row Id in List Click "+_TaskRowId);
                    ((Callback) getActivity()).onItemSelected(_TaskRowId);

                    /*
                    Intent _DetailViewIntent = new Intent(getActivity(),DetailActivity.class);
                    _DetailViewIntent.putExtra(DetailFragment.TASK_ID_KEY,_TaskRowId);
                    startActivity(_DetailViewIntent);
                    */

                }
                _Position = position;
            }
        });
        if(savedInstanceState != null && savedInstanceState.containsKey(SELECTED_KEY)){
            _Position = savedInstanceState.getInt(SELECTED_KEY);
        }

        return rootView;
    }



    @Override
    public void onStart(){
        super.onStart();


    }



    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        String sortOrder = TaskContract.TaskEntry.COLUMN_STOP_DATETEXT + " DESC";
        return new CursorLoader(
                getActivity(),
                TaskContract.TaskEntry.CONTENT_URI,
                TASK_COLUMNS,
                null,
                null,
                sortOrder
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        _taskListAdapter.swapCursor(data);
        if(_Position != ListView.INVALID_POSITION){
            _taskListViewLayout.setSelection(_Position);
        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        _taskListAdapter.swapCursor(null);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(TASK_LOADER,null,this);
    }

}