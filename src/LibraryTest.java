import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class LibraryTest {

    @BeforeEach
    void setUp() {
    }
    @AfterEach
    void tearDown() {
    }

    @Test
    void init() {
        Library test = new Library("test");
        test.init("Library00.csv");
        assertNotNull(test.init("Library00.csv"));
    }

    @Test
    void convertInt() {
        Library test = new Library("test");
//      for records {
            //success because string of int value is string of int value
            assertEquals(7, test.convertInt("7", Code.BOOK_COUNT_ERROR));
            //expected failure because string of int value is array of chars
            assertNotEquals(7, test.convertInt("name", Code.BOOK_COUNT_ERROR));
            //expected output for NumberFormateException
            assertEquals(Code.BOOK_COUNT_ERROR.getCode(), test.convertInt("name", Code.BOOK_COUNT_ERROR));
//      }
//      for Shelf{
            //success because String is an int value
            assertEquals(4, test.convertInt("4", Code.SHELF_COUNT_ERROR));
            //expected failure for same reason as above, String is none int parse-able String
            assertNotEquals(4, test.convertInt("name", Code.SHELF_COUNT_ERROR));
            //expected format for NumberFormat Exception
            assertEquals(Code.SHELF_COUNT_ERROR.getCode(), test.convertInt("name", Code.SHELF_COUNT_ERROR));
//      }
//      for Reader{
           //all same tests as previous 2 but for initReader
            //success
            assertEquals(4, test.convertInt("4", Code.READER_COUNT_ERROR));
            //fail
            assertNotEquals(4, test.convertInt("name", Code.READER_COUNT_ERROR));
            //expected for fail
            assertEquals(Code.READER_COUNT_ERROR.getCode(), test.convertInt("name", Code.READER_COUNT_ERROR));
//      }
//      Check for negative Int{
        //expected for negative int
        assertEquals(-3, test.convertInt("-3", Code.READER_COUNT_ERROR));
        //not equal to what is Code.SUCCESS.getCode() -> 0
        assertNotEquals(0,test.convertInt("-4", Code.READER_COUNT_ERROR));
        //Expected for negative value that does not corresspond to a specific Code Error.
        //Unknown Error is printed.
        assertEquals(-999, test.convertInt("-5", Code.READER_COUNT_ERROR));
//      }
    }
    @Test
    void convertDate(){
        Library test = new Library("test");
        //test case of String date == 0000
        assertEquals(LocalDate.of(1970,1,1),
                test.convertDate("0000",Code.DATE_CONVERSION_ERROR));
        //not applicable string date returns NumberFormatException
        assertEquals(LocalDate.of(1970,1,1),
                test.convertDate("yyyy-mm",Code.DATE_CONVERSION_ERROR));
        //
        assertEquals(LocalDate.of(1970,1,1),
                test.convertDate("-0001-01-01",Code.DATE_CONVERSION_ERROR));
        assertEquals(LocalDate.of(1970,1,1),
                test.convertDate("0000--01-01",Code.DATE_CONVERSION_ERROR));
        assertEquals(LocalDate.of(1970,1,1),
                test.convertDate("0000-00--01",Code.DATE_CONVERSION_ERROR));
        assertEquals(LocalDate.of(0001,01,01),
                test.convertDate("0001-01-01",Code.DATE_CONVERSION_ERROR));
        //DateTimeException not catching?
//        assertEquals(LocalDate.of(9999,99,99),
//                test.convertDate("9999-99-99",Code.DATE_CONVERSION_ERROR));
//        assertEquals(LocalDate.of(0000,00,00),
//                test.convertDate("0000-00-00",Code.DATE_CONVERSION_ERROR));
    }
    //-----BOOK--------
    @Test
    void addBook(){
        //CASE ERROR! SUBJECT OF BOOK AND
        //SUBJECT OF SHELF MUST BE SAME CASE
        //SANITIZE BEFORE FUNCTIONS AND OPERATIONS
        Library test = new Library("test");
        Book newBook = new Book("1337","Headfirst Java","education",1337,"Grady Booch",LocalDate.now());
        Shelf newShelf = new Shelf(1,"education");
        //CONDITION WHERE BOOK DOES NOT EXIST and
        //IF SHELF DOES NOT EXIST
        assertEquals(Code.SHELF_EXISTS_ERROR,test.addBook(newBook));
        //CONDITION WHERE BOOK EXISTS and
        //IF SHELF DOES NOT EXIST
        HashMap<Book,Integer> books  = new HashMap<>();
        books.put(newBook,1);
        test.setBooks(books);
        assertEquals(Code.SHELF_EXISTS_ERROR,test.addBook(newBook));
        //CONDITION WHERE BOOK DOES EXIST and
        //IF SHELF EXISTS
        HashMap<String, Shelf> shelves = new HashMap<>();
        shelves.put(newShelf.getSubject(),newShelf);
        test.setShelves(shelves);
        assertEquals(Code.SUCCESS,test.addBook(newBook));
        //CONDITION WHERE BOOK EXISTS and
        //IF SHELF EXISTS
        assertEquals(Code.SUCCESS,test.addBook(newBook));
    }

    @Test
    void getBookByISBN(){

    }
}