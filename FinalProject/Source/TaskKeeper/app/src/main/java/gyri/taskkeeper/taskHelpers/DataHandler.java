package gyri.taskkeeper.taskHelpers;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.util.Log;

import gyri.taskkeeper.data.TaskContract;
import gyri.taskkeeper.entities.TaskEntity;

/**
 * Created by CWL on 30-12-2014.
 */
public class DataHandler {

    Context _Context;
    private final String LOG_TAG = DataHandler.class.getSimpleName();

    public DataHandler(Context _Context){
        this._Context = _Context;
    }

    public long addTask(TaskEntity _TaskEntity){
        Long _TaskRowId = null;
        Log.v(LOG_TAG, "Inserting" + _TaskEntity.get_TaskTitle());
        ContentValues _TaskValues = new ContentValues();
        _TaskValues.put(TaskContract.TaskEntry.COLUMN_TASK_TITLE , _TaskEntity.get_TaskTitle());
        _TaskValues.put(TaskContract.TaskEntry.COLUMN_TASK_DESC , _TaskEntity.get_TaskDescription());
        _TaskValues.put(TaskContract.TaskEntry.COLUMN_START_DATETEXT , _TaskEntity.get_StartDate());
        _TaskValues.put(TaskContract.TaskEntry.COLUMN_STOP_DATETEXT , _TaskEntity.get_StopDate());
        _TaskValues.put(TaskContract.TaskEntry.COLUMN_START_TIMETEXT , _TaskEntity.get_StartTime());
        _TaskValues.put(TaskContract.TaskEntry.COLUMN_STOP_TIMETEXT , _TaskEntity.get_StopTime());

        Uri _TaskInsertUri = _Context.getContentResolver().insert(TaskContract.TaskEntry.CONTENT_URI , _TaskValues);
        _TaskRowId = ContentUris.parseId(_TaskInsertUri);
        Log.v(LOG_TAG , "Insert Done. RowId :" + _TaskRowId);
        return _TaskRowId;
    }

    public int updateTask(TaskEntity _TaskEntity){

        Long _TaskRowId = _TaskEntity.get_Id();
        Log.v(LOG_TAG, "Inserting" + _TaskEntity.get_TaskTitle());
        ContentValues _UpdateTaskValues = new ContentValues();
        _UpdateTaskValues.put(TaskContract.TaskEntry._ID , _TaskRowId);
        _UpdateTaskValues.put(TaskContract.TaskEntry.COLUMN_TASK_TITLE , _TaskEntity.get_TaskTitle());
        _UpdateTaskValues.put(TaskContract.TaskEntry.COLUMN_TASK_DESC , _TaskEntity.get_TaskDescription());
        _UpdateTaskValues.put(TaskContract.TaskEntry.COLUMN_START_DATETEXT , _TaskEntity.get_StartDate());
        _UpdateTaskValues.put(TaskContract.TaskEntry.COLUMN_STOP_DATETEXT , _TaskEntity.get_StopDate());
        _UpdateTaskValues.put(TaskContract.TaskEntry.COLUMN_START_TIMETEXT , _TaskEntity.get_StartTime());
        _UpdateTaskValues.put(TaskContract.TaskEntry.COLUMN_STOP_TIMETEXT , _TaskEntity.get_StopTime());

        int _Count = _Context.getContentResolver().update(
                TaskContract.TaskEntry.CONTENT_URI, _UpdateTaskValues, TaskContract.TaskEntry._ID + "= ?",
                new String[] { Long.toString(_TaskRowId)});


        Log.v(LOG_TAG , "Update Done. RowId :" + _TaskRowId + "  Count:"+_Count);
        return _Count;
    }


    public int deleteTask(TaskEntity _TaskEntity){

        Long _TaskRowId = _TaskEntity.get_Id();
        int _Count = _Context.getContentResolver().delete(
                TaskContract.TaskEntry.CONTENT_URI,TaskContract.TaskEntry._ID + "= ?",
                new String[] { Long.toString(_TaskRowId)});
        Log.v(LOG_TAG , "Update Done. RowId :" + _TaskRowId + "  Count:"+_Count);
        return _Count;
    }


}
