package com.example.test2.wirelessorder.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

import java.util.HashMap;

public class TableProvider extends ContentProvider {
    private DBHelper dbHelper;

    private static final int TABLES = 1;
    private static final int TABLES_ID = 2;
    private static HashMap<String, String> tblProjectionMap;

    private static final UriMatcher sUriMatcher;
    static {
        sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        sUriMatcher.addURI(Tables.AUTHORITY, "table", TABLES);
        sUriMatcher.addURI(Tables.AUTHORITY, "table/#", TABLES_ID);

        tblProjectionMap = new HashMap<String, String>();

        tblProjectionMap.put(Tables._ID, Tables._ID);
        tblProjectionMap.put(Tables.NUM, Tables.NUM);
        tblProjectionMap.put(Tables.DESCRIPTION, Tables.DESCRIPTION);
    }
    @Override
    public boolean onCreate() {
        dbHelper = new DBHelper(getContext());
        return true;
    }


    @Override
    public Cursor query( Uri uri,  String[] projection,  String selection,  String[] selectionArgs,  String sortOrder) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        switch (sUriMatcher.match(uri)){
            case TABLES:
                qb.setTables(DBHelper.TABLES_TABLE_NAME);
                qb.setProjectionMap(tblProjectionMap);
                break;
            case TABLES_ID:
                qb.setTables(DBHelper.TABLES_TABLE_NAME);
                qb.setProjectionMap(tblProjectionMap);
                qb.appendWhere(Tables._ID + "=" + uri.getPathSegments().get(1));
                break;
            default:
                throw new IllegalArgumentException("Uri錯誤" + uri);
        }

        String orderBy;
        if (TextUtils.isEmpty(sortOrder)) {
            orderBy = Tables.DEFAULT_SORT_ORDER;
        } else {
            orderBy = sortOrder;
        }
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = qb.query(db, projection, selection, selectionArgs, null, null, orderBy);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }


    @Override
    public String getType( Uri uri) {
        return null;
    }


    @Override
    public Uri insert( Uri uri,  ContentValues values) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long rowId = db.insert(DBHelper.TABLES_TABLE_NAME,Tables.NUM, values);
        if (rowId > 0) {
            Uri empUri = ContentUris.withAppendedId(Tables.CONTENT_URI, rowId);
            getContext().getContentResolver().notifyChange(empUri, null);
            return empUri;
        }
        return null;
    }

    @Override
    public int delete( Uri uri,  String selection,  String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri,  ContentValues values,  String selection,  String[] selectionArgs) {
        return 0;
    }
}
