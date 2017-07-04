package com.example.rss.rssapp.BD;

import android.provider.BaseColumns;

/**
 * Created by mrezapue on 3/7/17.
 */

public class ScripDataBase {

    private static final String TAG = ScripDataBase.class.getSimpleName();


    public static final String ENTRADA_TABLE_NAME = "entrada";
    public static final String STRING_TYPE = "TEXT";
    public static final String INT_TYPE = "INTEGER";

    // Campos de la tabla entrada
    public static class ColumnEntradas {
        public static final String ID = BaseColumns._ID;
        public static final String TITLE = "title";
        public static final String DESCRIPCION = "descrpcion";
        public static final String URL = "url";
        public static final String URL_MINI= "thumb_url";
    }

    // Comando CREATE para la tabla ENTRADA
    public static final String CREATE_ENTRY =
            "CREATE TABLE " + ENTRADA_TABLE_NAME + "(" +
                    ColumnEntradas.ID + " " + INT_TYPE + " primary key autoincrement," +
                    ColumnEntradas.TITLE + " " + STRING_TYPE + " not null," +
                    ColumnEntradas.DESCRIPCION + " " + STRING_TYPE + "," +
                    ColumnEntradas.URL + " " + STRING_TYPE + "," +
                    ColumnEntradas.URL_MINI + " " + STRING_TYPE +")";

}
