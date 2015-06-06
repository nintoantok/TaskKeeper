package gyri.taskkeeper.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

/**
 * Created by CWL on 29-12-2014.
 */
public class TaskProvider extends ContentProvider {

     private static final UriMatcher sUriMatcher = buildUriMatcher();
     private TaskDbHelper mOpenHelper;

     private static final int TASK = 100;
     private static final int TASK_ID = 103;
     private static UriMatcher buildUriMatcher() {
         // I know what you're thinking.  Why create a UriMatcher when you can use regular
         // expressions instead?  Because you're not crazy, that's why.

         // All paths added to the UriMatcher have a corresponding code to return when a match is
         // found.  The code passed into the constructor represents the code to return for the root
         // URI.  It's common to use NO_MATCH as the code for this case.
         final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
         final String authority = TaskContract.CONTENT_AUTHORITY;

         // For each type of URI you want to add, create a corresponding code.
         matcher.addURI(authority, TaskContract.PATH_TASK, TASK);
         matcher.addURI(authority, TaskContract.PATH_TASK + "/#", TASK_ID);


//         matcher.addURI(authority, WeatherContract.PATH_LOCATION, LOCATION);
//         matcher.addURI(authority, WeatherContract.PATH_LOCATION + "/#", LOCATION_ID);


         return matcher;
     }













        @Override
    public boolean onCreate() {

        mOpenHelper = new TaskDbHelper(getContext());
        return true;


    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor retCursor;
        switch (sUriMatcher.match(uri)){
            case TASK:
            {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        TaskContract.TaskEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            case TASK_ID:
            {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        TaskContract.TaskEntry.TABLE_NAME,
                        projection,
                        TaskContract.TaskEntry._ID + "= '"+ ContentUris.parseId(uri)+ "'",
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: "+uri);
        }
        retCursor.setNotificationUri(getContext().getContentResolver(),uri);
        return retCursor;
    }



    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match){
            case TASK:
                return TaskContract.TaskEntry.CONTENT_TYPE;
            case TASK_ID:
                return TaskContract.TaskEntry.CONTENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: "+uri);
        }

    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {

        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        Uri returnUri;

        switch (match) {
            case TASK: {
                long _id = db.insert(TaskContract.TaskEntry.TABLE_NAME, null, values);
                if ( _id > 0 )
                    returnUri = TaskContract.TaskEntry.buildTaskUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }


    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        Uri returnUri;
        int rowsDeleted;

        switch (match) {
            case TASK: {
                rowsDeleted = db.delete(TaskContract.TaskEntry.TABLE_NAME, selection, selectionArgs);
                break;
            }default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if(null==selection || 0!=rowsDeleted) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;

    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        Uri returnUri;
        int rowsUpdated;

        switch (match) {
            case TASK: {
                rowsUpdated = db.update(TaskContract.TaskEntry.TABLE_NAME, values,selection, selectionArgs);
                break;
            }default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if(0!=rowsUpdated) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;

    }
}
