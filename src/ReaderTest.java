/*
*Author: Jose Nick Flores
* Date 13 Feb 22
* Description: Test class for Class file Reader.java. Test methods are accessor and mutator methods, addBook, removeBook,hasBook, and testEquals.
 */
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ReaderTest {
    //variable instances for field Setter test Getter test methods
    int card_number = 1800;
    String name = "Nick";
    String phone = "123-123-1234";
    //field getter instance of Reader to test get methods
    Reader fieldGetReader = new Reader(card_number,name,phone);
    //variable instance for setter tests
    int setCardNumber = 9000;
    String setName = "Flores";
    String  setPhone = "333-333-3333";
    //instance of Reader for setter tests
    Reader fieldSetReader = new Reader(card_number,name,phone);

    //constructor test
    @Test
    void Reader(){
        Reader reader = null;
        assertNull(reader);
        Reader reader1 = new Reader(0,"","");
        assertNotEquals(reader1,null);
    }
    @Test
    void getBookCount() {
        Reader bookCount = new Reader(0,"","");
        Book addBook = new Book("1", "",
                "",0,"",LocalDate.now());

        assertEquals(bookCount.getBookCount(),0);
        bookCount.addBook(addBook);
        assertEquals(bookCount.getBookCount(),1);
        bookCount.removeBook(addBook);
        assertEquals(bookCount.getBookCount(),0);
    }

    @Test
    void getCardNumber() {
        assertEquals(1800,fieldGetReader.getCardNumber());
    }

    @Test
    void setCardNumber() {
        fieldSetReader.setCardNumber(1234);
        assertNotEquals(fieldSetReader.getCardNumber(),setCardNumber);
        assertEquals(fieldSetReader.getCardNumber(),1234);
    }

    @Test
    void getName() {
        assertEquals("Nick",fieldGetReader.getName());
    }

    @Test
    void setName() {
        fieldSetReader.setName("New Name");
        assertNotEquals(fieldSetReader.getName(),setName);
        assertEquals(fieldSetReader.getName(),"New Name");
    }

    @Test
    void getPhone() {
        assertEquals("123-123-1234",fieldGetReader.getPhone());
    }

    @Test
    void setPhone() {
        fieldSetReader.setPhone("New Number");
        assertNotEquals(fieldSetReader.getPhone(),setPhone);
        assertEquals(fieldSetReader.getPhone(),"New Number");
    }

    @Test
    void addBook() {
        Reader addBookReader = new Reader(0,"","");

        Book dummyBook = new Book("1", "",
                "",0,"",LocalDate.now());
        assertEquals(addBookReader.addBook(dummyBook), Code.SUCCESS);
        assertNotEquals(addBookReader.addBook(dummyBook),Code.SUCCESS);
        assertEquals(addBookReader.addBook(dummyBook),Code.BOOK_ALREADY_CHECKED_OUT_ERROR);
    }

    @Test
    void removeBook() {
        Reader readRemoveBook =  new Reader(0,"","");
        Book bookToBeRemoved = new Book("","","",0,"",LocalDate.now());
        assertEquals(readRemoveBook.removeBook(bookToBeRemoved),Code.READER_DOESNT_HAVE_BOOK_ERROR);
        readRemoveBook.addBook(bookToBeRemoved);
        assertEquals(readRemoveBook.removeBook(bookToBeRemoved),Code.SUCCESS);
    }

    @Test
    void hasBook() {
        Reader readHasBook = new Reader(0,"","");
        Book hasBook  = new Book("","","",0,"",LocalDate.now());
        assertEquals(readHasBook.hasBook(hasBook),false);
        readHasBook.addBook(hasBook);
        assertEquals(readHasBook.hasBook(hasBook),true);
    }

    @Test
    void testEquals() {
        Reader testEqualsReader = new Reader(card_number,name,phone);
        Reader testEqualsDifferent = new Reader(01, "Jose","831");
        assertNotEquals(testEqualsReader,testEqualsDifferent);
        Reader testEqualsSame = new  Reader(1800,"Nick","123-123-1234");
        assertEquals(testEqualsReader, testEqualsSame);



    }
}