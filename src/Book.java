/*
*Jose Nick Flores
* Project 01 Part 01/04 Book.java
* Description: Class for Book that will be used in future project
* contains getters and setters, overridded equals, hashcode, toString
 */
import java.time.LocalDate;
import java.util.Objects;

public class Book {
    public final static int ISBN_ =0;
    public final static int TITLE_=1;
    public final static int SUBJECT_=2;
    public final static int PAGE_COUNT_=3;
    public final static int AUTHOR_=4;
    public final static int DUE_DATE_=5;
    private String isbn;
    private String title;
    private String subject;
    private int page_count;
    private String author;
    private LocalDate due_date;

    public Book(String isbn, String title, String subject, int page_count, String author, LocalDate due_date) {
        this.isbn = isbn;
        this.title = title;
        this.subject = subject;
        this.page_count = page_count;
        this.author = author;
        this.due_date = due_date;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getPage_count() {
        return page_count;
    }

    public void setPage_count(int page_count) {
        this.page_count = page_count;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public LocalDate getDue_date() {
        return due_date;
    }

    public void setDue_date(LocalDate due_date) {
        this.due_date = due_date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return page_count == book.page_count && Objects.equals(isbn, book.isbn) && Objects.equals(title, book.title) && Objects.equals(subject, book.subject) && Objects.equals(author, book.author) && Objects.equals(due_date, book.due_date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isbn, title, subject, page_count, author, due_date);
    }

    @Override
    public String toString() {
        return title +
                " by " + author +
                "[ISBN]:" + isbn;
    }

}
