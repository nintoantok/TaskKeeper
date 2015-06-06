/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package gyri.taskkeeper.test;

import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.test.AndroidTestCase;
import android.util.Log;

import gyri.taskkeeper.data.TaskContract.TaskEntry;


import java.util.Map;
import java.util.Set;

import gyri.taskkeeper.data.TaskContract;
import gyri.taskkeeper.data.TaskDbHelper;

public class TestProvider extends AndroidTestCase {

//    public static final String LOG_TAG = TestDb.class.getSimpleName();
//
//
////    public void testDeleteDb() throws Throwable {
////        mContext.deleteDatabase(TaskDbHelper.DATABASE_NAME);
////
////    }
//
//        public void testGetType() {
//            // content://com.example.android.sunshine.app/weather/
//            String type = mContext.getContentResolver().getType(TaskEntry.CONTENT_URI);
//            // vnd.android.cursor.dir/com.example.android.sunshine.app/weather
//            assertEquals(TaskEntry.CONTENT_TYPE, type);
//
//            // content://com.example.android.sunshine.app/weather/94074
//            type = mContext.getContentResolver().getType(
//                    TaskEntry.buildTaskUri(1));
//            // vnd.android.cursor.dir/com.example.android.sunshine.app/weather
//            assertEquals(TaskEntry.CONTENT_ITEM_TYPE, type);
//
//
//
//    }
//
//    // TODO Uncomment for
//    // 4a - JUnit testing
//    // https://www.udacity.com/course/viewer#!/c-ud853/l-1639338560/m-1633698603
//    public void testDeleteAllRecords(){
//        mContext.getContentResolver().delete(
//                TaskEntry.CONTENT_URI,
//                null,
//                null
//        );
//
//        Cursor cursor = mContext.getContentResolver().query(
//                TaskEntry.CONTENT_URI,
//                null,
//                null,
//                null,
//                null
//        );
//        assertEquals(0, cursor.getCount());
//        cursor.close();
//
//
//    }
//
//
//    public void testInsertReadProvider() {
//
//
//        // If there's an error in those massive SQL table creation Strings,
//        // errors will be thrown here when you try to get a writable database.
//
//
//        String TEST_START_DATETEXT = "29/12/2014";
//
//        // Fantastic.  Now that we have a location, add some weather!
//        ContentValues taskValues = createTaskValues();
//
//
//        /**
//         * TODO YOUR CODE BELOW HERE FOR QUIZ
//         * QUIZ - 4a - InsertReadDbTest
//         * https://www.udacity.com/course/viewer#!/c-ud853/l-1639338560/e-1633698604/m-1633698605
//         **/
//        long taskRowId;
//        Uri taskRowUri = mContext.getContentResolver().insert(TaskEntry.CONTENT_URI,taskValues);
//        taskRowId = ContentUris.parseId(taskRowUri);
//
//
//        // Verify we got a row back.
//        //assertTrue(taskRowId != -1);
//        Log.d(LOG_TAG, "New row id: " + taskRowId);
//
//
////        // Data's inserted.  IN THEORY.  Now pull some out to stare at it and verify it made
////        // the round trip.
////
////        // Specify which columns you want.
////        String[] Taskcolumns = {
////                TaskEntry._ID,
////                TaskEntry.COLUMN_TASK_TITLE,
////                TaskEntry.COLUMN_TASK_DESC,
////                TaskEntry.COLUMN_START_DATETEXT,
////                TaskEntry.COLUMN_START_TIMETEXT,
////                TaskEntry.COLUMN_STOP_DATETEXT,
////                TaskEntry.COLUMN_STOP_TIMETEXT,
////
////        };
//
//        // A cursor is your primary interface to the query results.
//        Cursor Taskcursor = mContext.getContentResolver().query(TaskEntry.CONTENT_URI,
//                null, // Columns for the "where" clause
//                null, // Values for the "where" clause
//                null, // columns to filter by row groups
//                null // sort order
//        );
//
//        if(Taskcursor.moveToFirst())
//            validateCursor(Taskcursor,taskValues);
//
//
//        Taskcursor.close();
//
//
//
//
//
//
//        // TODO Uncomment for
//        //4a - JUnit testing
//        //https://www.udacity.com/course/viewer#!/c-ud853/l-1639338560/m-1633698603
//
//    }
//
//    public void testUpdateTask() {
//        testDeleteAllRecords();
//        // Create a new map of values, where column names are the keys
//        ContentValues values = TestDb.createTaskValues();
//
//        Uri taskUri = mContext.getContentResolver().
//                insert(TaskEntry.CONTENT_URI, values);
//        long taskRowId = ContentUris.parseId(taskUri);
//
//        // Verify we got a row back.
//        assertTrue(taskRowId != -1);
//        Log.d(LOG_TAG, "New row id in testUpdate : " + taskRowId);
//
//        ContentValues updatedValues = new ContentValues(values);
//        updatedValues.put(TaskEntry._ID, taskRowId);
//        updatedValues.put(TaskEntry.COLUMN_TASK_DESC, "test new task desc yoyo");
//
//        int count = mContext.getContentResolver().update(
//                TaskEntry.CONTENT_URI, updatedValues, TaskEntry._ID + "= ?",
//                new String[] { Long.toString(taskRowId)});
//
//        assertEquals(count, 1);
//        Log.d(LOG_TAG,"assert update success");
//
//        // A cursor is your primary interface to the query results.
//        Cursor cursor = mContext.getContentResolver().query(
//                TaskEntry.buildTaskUri(taskRowId),
//                null,
//                null, // Columns for the "where" clause
//                null, // Values for the "where" clause
//                null // sort order
//        );
//        if(cursor.moveToFirst())
//            TestDb.validateCursor(cursor, updatedValues);
//
//        cursor.close();
//    }
//
//
//
//    /* TODO Uncomment for
//    4a - Simplify Tests
//    https://www.udacity.com/course/viewer#!/c-ud853/l-1639338560/e-1633698607/m-1615128666*/
//    static ContentValues createTaskValues() {
//        ContentValues taskValues = new ContentValues();
//        taskValues.put(TaskEntry.COLUMN_TASK_TITLE, "TestTask");
//        taskValues.put(TaskEntry.COLUMN_TASK_DESC, "Testing the task db");
//        taskValues.put(TaskEntry.COLUMN_START_DATETEXT, "29/12/2014");
//        taskValues.put(TaskEntry.COLUMN_START_TIMETEXT, "11:22");
//        taskValues.put(TaskEntry.COLUMN_STOP_DATETEXT, "20/12/2014");
//        taskValues.put(TaskEntry.COLUMN_STOP_TIMETEXT, "12:50");
//
//        return taskValues;
//    }
//
//
//
//    static void validateCursor(Cursor valueCursor, ContentValues expectedValues) {
//
//        assertTrue(valueCursor.moveToFirst());
//
//        Set<Map.Entry<String, Object>> valueSet = expectedValues.valueSet();
//        for (Map.Entry<String, Object> entry : valueSet) {
//            String columnName = entry.getKey();
//            int idx = valueCursor.getColumnIndex(columnName);
//            assertFalse(idx == -1);
//            String expectedValue = entry.getValue().toString();
//            assertEquals(expectedValue, valueCursor.getString(idx));
//        }
//        valueCursor.close();
//    }
//

}