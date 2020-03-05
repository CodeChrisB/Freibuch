package com.example.foodgent.AppData.Logic;

import java.util.ArrayList;

public interface SaveArrayAble<T> {
    ArrayList<T> getArray();

    boolean addTo(T object);
    void removeObject( T object);
    void removeAll();
    ArrayList<T> getObject();
    void setObject(ArrayList<T> objectList);
}
