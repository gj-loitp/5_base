package com.function.epub.model;

import com.core.base.BaseModel;

public class BookInfoData extends BaseModel {
    private static final BookInfoData ourInstance = new BookInfoData();

    public static BookInfoData getInstance() {
        return ourInstance;
    }

    private BookInfoData() {
    }

    private BookInfo bookInfo;

    public BookInfo getBookInfo() {
        return bookInfo;
    }

    public void setBookInfo(BookInfo bookInfo) {
        this.bookInfo = bookInfo;
    }
}
