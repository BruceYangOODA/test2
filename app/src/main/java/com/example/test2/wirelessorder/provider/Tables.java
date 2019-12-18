package com.example.test2.wirelessorder.provider;

import android.net.Uri;
import android.provider.BaseColumns;

public final class Tables  implements BaseColumns {

    public static final String AUTHORITY = "com.example.test2.wirelessorder.provider.TABLES";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/table");
    public static final String DEFAULT_SORT_ORDER = "num DESC";
    public static final String NUM = "num";
    public static final String DESCRIPTION= "description";
    public static final String _ID = "_id";
}
