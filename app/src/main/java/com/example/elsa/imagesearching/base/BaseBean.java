package com.example.elsa.imagesearching.base;

import java.util.List;

public class BaseBean<T> {
    private boolean success;
    private int status;
    private List<T> data;

    public boolean isSuccess() {
        return success;
    }

    public List<T> getData() {
        return data;
    }

}
