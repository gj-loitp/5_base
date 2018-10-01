package vn.loitp.function.epub.model;

public class BookInfoData {
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
