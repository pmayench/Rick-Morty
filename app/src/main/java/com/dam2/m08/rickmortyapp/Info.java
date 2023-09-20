package com.dam2.m08.rickmortyapp;

public class Info
{
    //region Variables
    int count, pages;
    String next, prev;
    //endregion

    //region Constructor
    public Info(int count, int pages, String next, String prev)
    {
        this.count = count;
        this.pages = pages;
        this.next = next;
        this.prev = prev;
    }
    public Info(){}
    //endregion

    //region Getters i Setters
    public int getCount() {return count;}
    public void setCount(int count) {this.count = count;}

    public int getPages() {return pages;}
    public void setPages(int pages) {this.pages = pages;}

    public String getNext() {return next;}
    public void setNext(String next) {this.next = next;}

    public String getPrev() {return prev;}
    public void setPrev(String prev) {this.prev = prev;}
    //endregion
}
