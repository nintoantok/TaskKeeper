package gyri.taskkeeper.data;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by CWL on 27-12-2014.
 */
public class TaskContract {

    public static final String CONTENT_AUTHORITY = "gyri.taskkeeper";

    // Use CONTENT_AUTHORITY to create the base of all URI's which apps will use to contact
    // the content provider.

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    // Possible paths (appended to base content URI for possible URI's)
    // For instance, content://com.example.android.sunshine.app/weather/ is a valid path for
    // looking at weather data. content://com.example.android.sunshine.app/givemeroot/ will fail,
    // as the ContentProvider hasn't been given any information on what to do with "givemeroot".
    // At least, let's hope not.  Don't be that dev, reader.  Don't be that dev.

    public static final String PATH_TASK = "Task";
    private static final String LOG_TAG = TaskContract.class.getSimpleName();


    /* Inner class that defines the table contents of the weather table */
    public static final class TaskEntry implements BaseColumns {

        public static final String TABLE_NAME = "Task";

        // Description pf task
        public static final String COLUMN_TASK_TITLE = "task_title";

        // Description pf task
        public static final String COLUMN_TASK_DESC = "task_description";


        // Date, stored as Text with format yyyy-MM-dd
        public static final String COLUMN_START_DATETEXT = "start_date";
        // Date, stored as Text with format yyyy-MM-dd
        public static final String COLUMN_STOP_DATETEXT = "stop_date";

        // Time, stored as Text with format HH:MM
        public static final String COLUMN_START_TIMETEXT = "start_time";

        // Time, stored as Text with format HH:MM
        public static final String COLUMN_STOP_TIMETEXT = "stop_time";


        /* TODO Uncomment for
        4b - Adding ContentProvider to our Contract
        https://www.udacity.com/course/viewer#!/c-ud853/l-1576308909/m-1637521471
        */
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_TASK).build();

        public static final String CONTENT_TYPE =
                "vnd.android.cursor.dir/" + CONTENT_AUTHORITY + "/" + PATH_TASK;
        public static final String CONTENT_ITEM_TYPE =
                "vnd.android.cursor.item/" + CONTENT_AUTHORITY + "/" + PATH_TASK;

        public static Uri buildTaskUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
        public static String getStartDateFromUri(Uri uri) {
            return uri.getQueryParameter(COLUMN_START_DATETEXT);
        }

    }
    public static Date getDateFromDb(String _DateText){
        SimpleDateFormat _DbDateFormat = new SimpleDateFormat("dd/mm/yyyy");
        try{
            return _DbDateFormat.parse(_DateText);
        } catch (ParseException e){
            Log.d(LOG_TAG,"Unsupported Date Format");
            return null;
        }
    }

    // public static Time write method to handle time as a date time object to compare with current time

}
