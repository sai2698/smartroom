package com.example.navee.smartroom;

import android.graphics.drawable.BitmapDrawable;

/**
 * Created by navee on 6/5/2017.
 */

public class item {
    String label;
    Boolean bool;

    public item(String label, Boolean bool) {
        this.label = label;
        this.bool = bool;
    }

    public String getLabel() {
        return label;
    }

    public Boolean getBool() {
        return bool;
    }
}
