package com.example.todoapp;

public class Constants {

    public static final String DB_NAME = "TASK_DB";
    public static final int DB_VERSION = 1;

    public static final String TABLE_NAME = "TASK_TABLE";
    public static final String COLUMN_ID = "_ID";
    public static final String COLUMN_TASK = "TASK_NAME";
    public static final String COLUMN_STATUS = "TASK_STATUS";

    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "( "+
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_TASK + " TEXT NOT NULL, "+
            COLUMN_STATUS + " INTEGER NOT NULL );";

    public static final String DROP_TABLE = "DROP TABLE "+TABLE_NAME;
    public static final String SELECT_TABLE = "SELECT * FROM "+TABLE_NAME;





}
