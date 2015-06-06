package gyri.taskkeeper.taskHelpers;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;

import gyri.taskkeeper.R;
import gyri.taskkeeper.taskfragments.TaskListFragment;

/**
 * Created by CWL on 04-01-2015.
 */
public class TaskAdapter extends CursorAdapter {

    public TaskAdapter(Context context, Cursor cursor, int flags){
        super(context,cursor,flags);
    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View _View = LayoutInflater.from(context).inflate(R.layout.list_item_task,parent,false);
        ViewHolder _ViewHolder = new ViewHolder(_View);
        _View.setTag(_ViewHolder);
        return _View;
    }


    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        Long _TaskRowId = cursor.getLong(TaskListFragment.COL_TASK_ID);
        ViewHolder _ViewHolder = (ViewHolder) view.getTag();

        _ViewHolder._IconView.setImageResource(R.drawable.ic_launcher);

        String _TaskTitleString = cursor.getString(TaskListFragment.COL_TASK_TITLE);
        _ViewHolder._TaskTitleView.setText(_TaskTitleString);

        String _TaskStopDateString = cursor.getString(TaskListFragment.COL_STOP_DATETEXT);
        _ViewHolder._TaskStopDateView.setText(_TaskStopDateString);

        String _TaskStopTimeString = cursor.getString(TaskListFragment.COL_STOP_TIMETEXT);
        TextView _TaskStopTime = (TextView)view.findViewById(R.id.list_item_stoptimetext);
        _ViewHolder._TaskStopTimeView.setText(_TaskStopTimeString);
    }

    public static class ViewHolder{

        public final ImageView _IconView;
        public final TextView _TaskTitleView;
        public final TextView _TaskStopDateView;
        public final TextView _TaskStopTimeView;

        public ViewHolder(View _View){
            _IconView = (ImageView) _View.findViewById(R.id.list_item_icon);
            _TaskTitleView = (TextView) _View.findViewById(R.id.list_item_task_title);
            _TaskStopDateView = (TextView) _View.findViewById(R.id.list_item_stopdatetext);
            _TaskStopTimeView = (TextView) _View.findViewById(R.id.list_item_stoptimetext);
        }


    }

}
