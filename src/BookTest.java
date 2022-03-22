/*
 * Jose Nick Flores
 * Project 01 Part 01/04 Book.java
 * Description: Testing for Book class. Tests Constructor, accessors and mutators, and  equals method
 */
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class BookTest {
    //variables for accessor dummy constructor instance of "fieldGetBook"
    String get_isbn = "FieldGetISBN";
    String get_title = "FieldGetTITLE";
    String get_subject = "FieldGetSUBJECT";
    int get_page_count = 69;
    String get_author = "FieldGetAUTHOR";
    LocalDate get_due_date = LocalDate.of(1999,1,20);

    //variables for mutators dummy constructor instance of "fieldSetBook"
    String set_isbn = "FieldSetISBN";
    String set_title = "FieldSetTITLE";
    String set_subject = "FieldSetSUBJECT";
    int set_page_count = 100;
    String set_author = "FieldSetAUTHOR";
    LocalDate set_due_date = LocalDate.of(5,5,5);

    //dummy constructor instance with empty parameters
    Book book = new Book("","","",0,"",LocalDate.of(1,1,1));

    //constructor instance for accessor and mutator tests
    Book fieldGetBook = new Book(get_isbn, get_title, get_subject,get_page_count,get_author,get_due_date);
    Book fieldSetBook = new Book(set_isbn, set_title, set_subject, set_page_count,set_author, set_due_date);

    @Test
    void Book() {
        Book bookA = null;
        assertEquals(bookA, null);
        Book bookB = new Book("","","",0,"", LocalDate.now());
        //System.out.println("The date is: " + LocalDate.now());
        assertNotEquals(bookB,null);
    }

    @Test
    void getIsbn() {
        assertEquals("FieldGetISBN",fieldGetBook.getIsbn());
    }

    @Test
    void setIsbn() {
        fieldSetBook.setIsbn("notOriginal");
        assertNotEquals("FieldSetISBN", fieldSetBook.getIsbn());
        assertEquals("notOriginal",fieldSetBook.getIsbn());
    }

    @Test
    void getTitle() {
        assertEquals("FieldGetTITLE",fieldGetBook.getTitle());
    }

    @Test
    void setTitle() {
        fieldSetBook.setTitle("notOriginal");
        assertNotEquals("FieldSetTITLE", fieldSetBook.getTitle());
        assertEquals("notOriginal",fieldSetBook.getTitle());
    }

    @Test
    void getSubject() {
        assertEquals("FieldGetSUBJECT",fieldGetBook.getSubject());
    }

    @Test
    void setSubject() {
        fieldSetBook.setSubject("notOriginal");
        assertNotEquals("FieldSetSUBJECT", fieldSetBook.getSubject());
        assertEquals("notOriginal",fieldSetBook.getSubject());
    }

    @Test
    void getPage_count() {
        assertEquals(69,fieldGetBook.getPage_count());
    }

    @Test
    void setPage_count() {
        fieldSetBook.setPage_count(700);
        assertNotEquals(100,fieldSetBook.getPage_count());
        assertEquals(700,fieldSetBook.getPage_count());
    }

    @Test
    void getAuthor() {
        assertEquals("FieldGetAUTHOR",fieldGetBook.getAuthor());
    }

    @Test
    void setAuthor() {
        fieldSetBook.setAuthor("notOriginal");
        assertNotEquals("FieldSetAUTHOR", fieldSetBook.getAuthor());
        assertEquals("notOriginal",fieldSetBook.getAuthor());
    }

    @Test
    void getDue_date() {
        assertEquals(LocalDate.of(1999,1,20), fieldGetBook.getDue_date());
    }

    @Test
    void setDue_date() {
        fieldSetBook.setDue_date(LocalDate.of(12,12,12));
        assertNotEquals(LocalDate.of(5,5,5), fieldSetBook.getDue_date());
        assertEquals(LocalDate.of(12,12,12),fieldSetBook.getDue_date());
    }

    @Test
    void testEquals() {
        Book book1 = new Book("book1's","book1's","book1's",420,"book1's",LocalDate.of(1,1,1));
        Book book2 = new Book("book1's","book1's","book1's",420,"book1's",LocalDate.of(1,1,1));
        assertEquals(book1,book2);

    }


}