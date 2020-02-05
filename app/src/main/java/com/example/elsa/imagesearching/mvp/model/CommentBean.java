package com.example.elsa.imagesearching.mvp.model;



public class CommentBean {
    private String id;
    private String comment;

    public CommentBean(String id, String comment) {
        this.id = id;
        this.comment = comment;
    }

    public CommentBean() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "CommentBean{" +
                "id='" + id + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }
}
