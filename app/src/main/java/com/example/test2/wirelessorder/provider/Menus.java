package com.example.test2.wirelessorder.provider;

import android.net.Uri;
import android.provider.BaseColumns;

public final class Menus  implements BaseColumns {

    public static final String AUTHORITY = "com.example.test2.wirelessorder.provider.MENUS";

    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/menu1");

    public static final String DEFAULT_SORT_ORDER = "typeId DESC";

    public static final String _ID = "_id";
    public static final String TYPE_ID = "typeId";
    public static final String NAME= "name";
    public static final String PRICE= "price";
    public static final String PIC= "pic";
    public static final String REMARK= "remark";
}
