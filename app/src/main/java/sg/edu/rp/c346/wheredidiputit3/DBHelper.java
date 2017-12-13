package sg.edu.rp.c346.wheredidiputit3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by 15017608 on 28/11/2017.
 */

public class DBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VER_ITEM = 1;
    //table item
    private static final String DATABASE = "item.db";
    private static final String TABLE_ITEM = "item";
    private static final String COLUMN_ID_ITEM = "itemId";
    private static final String ITEM_TITLE = "itemTitle";
    private static final String ITEM_PLACE = "itemPlace";

    //table place
//    private static final int DATABASE_VER_PLACE = 1;
//    private static final String DATABASE_PLACE = "place.db";
//    private static final String TABLE_PLACE = "place";
//    private static final String COLUMN_ID_PLACE = "placeId";
// private static final String PLACE_TITLE = "placeTitle";



    public DBHelper(Context context) {
        super(context,DATABASE, null, 1);
//        super(context,DATABASE_PLACE, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableSqlItem = "CREATE TABLE " + TABLE_ITEM +  "("
                + COLUMN_ID_ITEM + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + ITEM_TITLE + " TEXT," + ITEM_PLACE + " TEXT)";

//        String createTableSqlPlace = "CREATE TABLE " + TABLE_PLACE +  "("
//                + COLUMN_ID_PLACE + " INTEGER PRIMARY KEY AUTOINCREMENT,"
//                + PLACE_TITLE + " TEXT)";

        db.execSQL(createTableSqlItem);
        //        db.execSQL(createTableSqlPlace);
        Log.i("info" ,"created tables");

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEM);
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLACE);

        // Create table(s) again
        onCreate(db);

    }


    public void insertItem(String itemTitle, String itemPlace) {
        // Get an instance of the database for writing
        SQLiteDatabase db = this.getWritableDatabase();
        // We use ContentValues object to store the values for
        //  the db operation
        ContentValues values = new ContentValues();
        // Store the column name as key and the description as value
        values.put(ITEM_TITLE, itemTitle);
        values.put(ITEM_PLACE, itemPlace);
        // Store the column name as key and the date as value
        // Insert the row into the TABLE_TASK
        db.insert(TABLE_ITEM, null, values);
        // Close the database connection
        db.close();
    }

//    public void insertPlace(String ptitle) {
//        // Get an instance of the database for writing
//        SQLiteDatabase db = this.getWritableDatabase();
//        // We use ContentValues object to store the values for
//        //  the db operation
//        ContentValues values = new ContentValues();
//        // Store the column name as key and the description as value
//        values.put(PLACE_TITLE, ptitle);
//        // Store the column name as key and the date as value
//        // Insert the row into the TABLE_TASK
//        db.insert(TABLE_PLACE, null, values);
//        // Close the database connection
//    }

    public ArrayList<Item> getItem() {
        ArrayList<Item> itemList = new ArrayList<Item>();
        String selectQuery = "SELECT " + COLUMN_ID_ITEM + " ,"
                + ITEM_TITLE + " ," + ITEM_PLACE
                + " FROM " + TABLE_ITEM;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String title = cursor.getString(1);
                String place = cursor.getString(2);
                Item obj = new Item(id,title,place);
                itemList.add(obj);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return itemList;
    }

    public int deleteItem(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String condition = COLUMN_ID_ITEM + "= ?";
        String[] args = {String.valueOf(id)};
        int result = db.delete(TABLE_ITEM, condition, args);
        db.close();
        return result;
    }

    public int updateItem(Item item){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ITEM_TITLE, item.getItemTitle());
        values.put(ITEM_PLACE, item.getItemPlace());
        String condition = COLUMN_ID_ITEM + "= ?";
        String[] args = {String.valueOf(item.getId())};
        int result = db.update(TABLE_ITEM, values, condition, args);
        db.close();
        return result;
    }
//    public ArrayList<String> getPlace() {
//        ArrayList<String> placeList = new ArrayList<String>();
//        String selectQuery = "SELECT "
//                + PLACE_TITLE
//                + " FROM " + TABLE_PLACE;
//
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery(selectQuery, null);
//
//        if (cursor.moveToFirst()) {
//            do {
//                String title = cursor.getString(0);
////                ToDoItem obj = new ToDoItem(title);
//                placeList.add(title);
//            } while (cursor.moveToNext());
//        }
//        cursor.close();
//        db.close();
//        return placeList;
//    }
}
