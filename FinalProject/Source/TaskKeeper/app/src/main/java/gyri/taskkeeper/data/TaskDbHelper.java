package gyri.taskkeeper.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import gyri.taskkeeper.data.TaskContract.TaskEntry;
/**
 * Created by CWL on 29-12-2014.
 */
public class TaskDbHelper extends SQLiteOpenHelper {

    // If you change the database schema, you must increment the database version.
    private static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "taskkeeper.db";

    public TaskDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        /**
         * TODO YOUR CODE BELOW HERE FOR QUIZ
         * QUIZ - 4a - LocationEntry
         * https://www.udacity.com/course/viewer#!/c-ud853/l-1639338560/e-1633698599/m-1633698600
         **/

        final String SQL_CREATE_TASK_TABLE = "CREATE TABLE " + TaskEntry.TABLE_NAME + " (" +

                // Why AutoIncrement here, and not above?
                // Unique keys will be auto-generated in either case.  But for weather
                // forecasting, it's reasonable to assume the user will want information
                // for a certain date and all dates *following*, so the forecast data
                // should be sorted accordingly.
                TaskEntry._ID + " INTEGER PRIMARY KEY," +

                // the ID of the location entry associated with this weather data
                TaskEntry.COLUMN_TASK_TITLE + " TEXT NULL, " +
                TaskEntry.COLUMN_TASK_DESC + " TEXT NOT NULL, " +
                TaskEntry.COLUMN_START_DATETEXT + " TEXT NOT NULL, " +
                TaskEntry.COLUMN_START_TIMETEXT + " TEXT NOT NULL," +
                TaskEntry.COLUMN_STOP_DATETEXT + " TEXT NOT NULL," +
                TaskEntry.COLUMN_STOP_TIMETEXT + " TEXT NOT NULL" +


                // Set up the location column as a foreign key to location table.

                // To assure the application have just one weather entry per day
                // per location, it's created a UNIQUE constraint with REPLACE strategy
                ");";


        sqLiteDatabase.execSQL(SQL_CREATE_TASK_TABLE);


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        //  TODO Uncomment for
        // 4a - SQLiteOpenHelper onUpgrade() method
        //  https://www.udacity.com/course/viewer#!/c-ud853/l-1639338560/m-1633698602
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        // Note that this only fires if you change the version number for your database.
        // It does NOT depend on the version number for your application.
        // If you want to update the schema without wiping data, commenting out the next 2 lines
        // should be your top priority before modifying this method.
        sqLiteDatabase.execSQL("ALTER TABLE IF EXISTS " + TaskEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);

    }
}
