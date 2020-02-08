package com.pawelfilipiak.futrzak;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
/**@descrition:
 * a simple class for answers storing purposes
 *
*/
public class DataBaseUtil extends SQLiteOpenHelper{

    private static final int DATABASEVERSION = 2;


    public DataBaseUtil(Context context) {
        super(context, "furryball.db", null, DATABASEVERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE categories (name TEXT PRIMARY_KEY, answers TEXT)");
    }

    public String[] getCategories() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM categories;",null);
        String[] content = new String[cursor.getCount()];
        String temp = "";
        cursor.moveToFirst();

        for(int i = 0; i< content.length; i++){
            temp =  cursor.getString(cursor.getColumnIndex("name"));
            if(temp != null)
                content[i] = temp;
            else
                content[i] = "Default";
            if(i!=content.length-1)
                cursor.moveToNext();
        }

        return content;
    }

    public void addCategory(String name, String answers){
        SQLiteDatabase dbw = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("answers", answers);
        dbw.insert("categories", null, values);
        dbw.close();
    }

    public void removeCategory(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("categories", "name = ?", new String[] { name });
        db.close();
    }

    public void updateCategory(String name, String answers){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("answers", answers);

        db.update("categories", values, "name = ?",
                new String[] { name });
    }

    public boolean isCategoryExisting(String name){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query("categories",new String[]{"name"},"name=?", new String[]{name},null,null,null,null);
        if(cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    public String getCategoryAnswers(String name){
        SQLiteDatabase db = getReadableDatabase();
        if (name == null)
            return "Err: Blank Name";
        Cursor cursor = db.query("categories", new String[] { "name", "answers" }, "name=?", new String[] { name }, null, null, null, null);

        String answers = "";
        if (cursor != null)
            cursor.moveToFirst();
        if(cursor.getCount()>0)
            answers = cursor.getString(1);
        if(answers == null)
            return "Err: null";
        return answers;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS categories");
        onCreate(db);
    }


}
