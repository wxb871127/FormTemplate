package com.provider;

import android.app.Application;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import template.com.templatedb.DaoManager;

public class CommonProvider extends ContentProvider {
    //这里的AUTHORITY就是我们在AndroidManifest.xml中配置的authorities
    private static final String AUTHORITY = "com.provider.CommonProvider";

    //匹配成功后的匹配码
    private static final int MATCH_CODE = 100;

    private static UriMatcher uriMatcher;

    static {
        //匹配不成功返回NO_MATCH(-1)
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        //添加我们需要匹配的uri
        uriMatcher.addURI(AUTHORITY,"drug", MATCH_CODE);
        uriMatcher.addURI(AUTHORITY, "commonExpression", MATCH_CODE);
    }

    protected String getTable(Uri uri) {
        return uri.getPath().substring(1);
    }

    @Override
    public boolean onCreate() {
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {


        String table = getTable(uri);
        Log.e("xxxxxxx", uri+"  table=" + table);

        return DaoManager.getInstance().getDataBase().query(getTable(uri), projection, selection, selectionArgs, null, null, sortOrder);
//        DaoManager.getInstance(new Application()).getDataBase().query(, , , )
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
