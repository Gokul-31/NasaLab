package com.example.nasalab;

import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

public class POJO_ {
    C1_Collection collection;

    public class C1_Collection{
        ArrayList<C2_Item> items;

        public class C2_Item implements Serializable {
            ArrayList<C3_Data> data;
            ArrayList<C4_Link> links;

            public class C3_Data{
                String description;
                String nasa_id;
                String title;
                String media_type;

                public String getDescription() {
                    return description;
                }

                public String getNasa_id() {
                    return nasa_id;
                }

                public String getTitle() {
                    return title;
                }

                public String getMedia_type() {
                    return media_type;
                }
            }

            public class C4_Link{
                String render;
                String href;

                public String getRender() {
                    return render;
                }

                public String getHref() {
                    return href;
                }
            }

            public ArrayList<C3_Data> getData() {
                return data;
            }

            public ArrayList<C4_Link> getLinks() {
                return links;
            }
        }

        public ArrayList<C2_Item> getItems() {
            return items;
        }
    }

    public C1_Collection getCollection() {
        return collection;
    }
}
