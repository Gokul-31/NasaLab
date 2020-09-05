package com.example.nasalab;

import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

public class POJO_D {
    CD1_Collection collection;

    public class CD1_Collection{
        ArrayList<CD2_Item> items;

        public class CD2_Item {
            String href;

            public String getHref() {
                return href;
            }
        }

        public ArrayList<CD2_Item> getItems() {
            return items;
        }
    }

    public CD1_Collection getCollection() {
        return collection;
    }
}
