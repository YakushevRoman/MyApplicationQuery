package com.example.user301.myapplicationquery;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    // константа для лога
    private static final String LOG_TAG = "myLogs";
    //масивы наших данных
    public static final String name[] = {"Китай", "США", "Бразилия", "Россия", "Япония",
            "Германия", "Египет", "Италия", "Франция", "Канада"};
    public static final int people[] = { 1400, 311, 195, 142, 128, 82, 80, 60, 66, 35 };
    public static final String region[] = { "Азия", "Америка", "Америка", "Европа", "Азия",
            "Европа", "Африка", "Европа", "Европа", "Америка" };
    // определяем кнопки
    Button buttonAll, buttonFunc, buttonPeople, buttonSort, buttonGroup, buttonHaving;
    EditText editTextFunc, editTextPeople, editTextRegion;
    RadioGroup radioGroupSort;

    DBHelper dbHelper;

    SQLiteDatabase sqLiteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // находим все элементы
        radioGroupSort = findViewById(R.id.rgSort);

        editTextFunc = findViewById(R.id.etFunc);
        editTextPeople = findViewById(R.id.etPeople);
        editTextRegion = findViewById(R.id.etRegionPeople);

        buttonAll = findViewById(R.id.btnAll);
        buttonAll.setOnClickListener(this);

        buttonFunc = findViewById(R.id.btnFunc);
        buttonFunc.setOnClickListener(this);

        buttonPeople = findViewById(R.id.btnPeople);
        buttonPeople.setOnClickListener(this);

        buttonSort = findViewById(R.id.btnSort);
        buttonSort.setOnClickListener(this);

        buttonGroup = findViewById(R.id.btnGroup);
        buttonGroup.setOnClickListener(this);

        buttonHaving = findViewById(R.id.btnHaving);
        buttonHaving.setOnClickListener(this);

        // создаем базу данных
        dbHelper = new DBHelper(this);
        // подключаемся к базе
        sqLiteDatabase = dbHelper.getWritableDatabase();
        // получаем курсор по всем записям
        Cursor cursor = sqLiteDatabase.query("mytable",
                null,
                null,
                null,
                null,
                null,
                null);
        // проверяем если ли записи в таблице
        if (cursor.getCount() == 0){
            // если нет щаполняем
            ContentValues contentValues = new ContentValues();
            //
            for (int i = 0; i < name.length; i++){
                contentValues.put("name", name[i]);
                contentValues.put("people", people[i]);
                contentValues.put("region", region[i]);
                Log.d(LOG_TAG,"id = " + sqLiteDatabase.insert("mytable",null,contentValues));
            }
        }
        sqLiteDatabase.close();
        onClick(buttonAll);
    }

    @Override
    public void onClick(View v) {

    }

    // создаем класс для создания базы данных
    private class DBHelper extends SQLiteOpenHelper{

        public DBHelper(Context context) {
            super(context, "myDB", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("create table mytable ("
                +"id integet primary key autoincrement,"
                +"name text,"
                + "people integer,"
                +"region text"+");");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}
