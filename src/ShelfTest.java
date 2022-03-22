/*
 *Jose Nick Flores
 * Project 01 Part 03/04 ShelfTest.java
 * Description: Class for Shelf that will be used in future project
 * contains getters and setters, overridded equals, hashcode, toString. Tests all these things
 */
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class ShelfTest {

    @Test
    void addBook() {
        Book book = new Book("isbn", "title", "sci-fi", 0,"author", LocalDate.now());
        Shelf shelf = new Shelf(0,"sci-fi");
        assertEquals(Code.SUCCESS,shelf.addBook(book));
        System.out.println(shelf.getBookCount(book));
        Book book1 =new Book("isbn", "title", "sci-fi", 0,"author", LocalDate.now());
        assertEquals(Code.SUCCESS,shelf.addBook(book1));
        System.out.println(shelf.getBookCount(book1));
        Book book2 =new Book("isbn", "title", "romance", 0,"author", LocalDate.now());
        assertEquals(Code.SHELF_SUBJECT_MISMATCH_ERROR,shelf.addBook(book2));
        System.out.println(shelf.getBookCount(book2));

    }
    @Test
    void getBookCount() {
        Book book =new Book("isbn", "title", "sci-fi", 0,"author", LocalDate.now());
        Book book1 =new Book("isbn", "notTitle", "sci-fi", 0,"author", LocalDate.now());

        Shelf shelf = new Shelf(0,"sci-fi");
        HashMap<Book, Integer> setBook = new HashMap<>();
        int random = (int) Math.random()+5;
        setBook.put(book, random);
        shelf.setBooks(setBook);
        assertEquals(random,shelf.getBookCount(book));
        System.out.println(shelf.removeBook(book));
        //removes all books and checks count
        for(int i=random-1; i>0; i--){
            shelf.removeBook(book);
            System.out.println(shelf.getBookCount(book));
        }
        System.out.println(shelf.getBookCount(book));
        System.out.println(shelf.getBookCount(book1));
    }

    @Test
    void listBooks() {
        Book book =new Book("isbn", "Monte Cristo", "sci-fi", 0,"author", LocalDate.now());
        Shelf shelf = new Shelf(0,"sci-fi");
        shelf.addBook(book);
        System.out.println(shelf.listBooks());
    }

    @Test
    void removeBook() {
        Book book =new Book("isbn", "title", "sci-fi", 0,"author", LocalDate.now());
        Shelf shelf = new Shelf(0,"sci-fi");
        System.out.println(shelf.removeBook(book));
        //adding book
        System.out.println(shelf.addBook(book));
        System.out.println(shelf.getBookCount(book));
        //remove added book
        System.out.println(shelf.removeBook(book));
        System.out.println(shelf.getBookCount(book));
        //remove all book
        System.out.println(shelf.removeBook(book));
        System.out.println(shelf.getBookCount(book));
    }
}