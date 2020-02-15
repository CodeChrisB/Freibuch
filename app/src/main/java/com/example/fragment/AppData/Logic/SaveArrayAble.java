package com.example.fragment.AppData.Logic;

import java.util.ArrayList;

public interface SaveArrayAble<T> {
    ArrayList<T> getArray();
    void addTo(T object);
}
