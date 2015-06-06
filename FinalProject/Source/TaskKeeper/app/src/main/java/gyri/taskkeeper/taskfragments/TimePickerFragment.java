package gyri.taskkeeper.taskfragments;



import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;


/**
 * A simple {@link DialogFragment} subclass.
 */
public class TimePickerFragment extends DialogFragment {


    private static final String LOG_TAG = TimePickerFragment.class.getSimpleName();
    TimePickerDialog.OnTimeSetListener _OnDataSet;
    public TimePickerFragment() {
        // Required empty public constructor
    }

    public void setCallBack(TimePickerDialog.OnTimeSetListener onTime) {
        _OnDataSet = onTime;
    }

    private int hour, minute;

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
        hour = args.getInt("hour");
        minute = args.getInt("minute");

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new TimePickerDialog(getActivity(), _OnDataSet, hour, minute , DateFormat.is24HourFormat(getActivity()));
    }


}
