/*
Author: Jose Nick Flors
Date: March
Filename: Library.java; Project 4/4
Abstract: Library.java will read data from a file,
         initialize that data into memory,
         and retrieve relevant information
         from other classes.
 */
import javax.sound.midi.Soundbank;
import java.io.File;
import java.io.FileNotFoundException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Library {
//-----------FIELDS------
        static final int LENDING_LIMIT = 5;
        String name;
        static int libraryCard;
        List<Reader> readers  =  new ArrayList<>();
        HashMap<String, Shelf> shelves  = new HashMap<>();
        HashMap<Book, Integer>  books = new HashMap<>();
//-------------------GET AND SET---------
        public String getName() {
                return name;
        }

        public void setName(String name) {
                this.name = name;
        }

        public static int getLibraryCard() {
                return libraryCard;
        }

        public static void setLibraryCard(int libraryCard) {
                Library.libraryCard = libraryCard;
        }

        public List<Reader> getReaders() {
                return readers;
        }

        public void setReaders(List<Reader> readers) {
                this.readers = readers;
        }

        public HashMap<String, Shelf> getShelves() {
                return shelves;
        }

        public void setShelves(HashMap<String, Shelf> shelves) {
                this.shelves = shelves;
        }

        public HashMap<Book, Integer> getBooks() {
                return books;
        }

        public void setBooks(HashMap<Book, Integer> books) {
                this.books = books;
        }
//--------------CONSTRUCTOR--------------------
        public Library(String name) {
                this.name = name;
                this.readers = null;
                this.shelves = null;
                this.books = null;
        }
//----------------INITS----------------------
        public Code init(String filename){
                File f = new File(filename);
                Scanner scan = null;
                try {
                        scan = new Scanner(f);
                } catch (FileNotFoundException e) {
                        return Code.FILE_NOT_FOUND_ERROR;
                }

                //first line in library00 for book
                Integer records = convertInt(scan.nextLine(), Code.BOOK_COUNT_ERROR);
                initBooks(records,scan);
                listBooks();
                //TODO:LIST BOOKS GOES HERE
                /*scan is being parsed and when previous method ends
                scan leaves off from where it left off*/

                records = convertInt(scan.nextLine(), Code.SHELF_COUNT_ERROR);
                initShelves(records, scan);

                //LIST SHELVES

                //final parse
                records = convertInt(scan.nextLine(), Code.READER_COUNT_ERROR);
                initReader(records,scan);

                //LIST READERS
                //System.out.println("Parse Complete!");

                return Code.UNKNOWN_ERROR;
        }
        private Code initBooks(int bookCount,Scanner scan){
                String isbn,title,subject,author;
                int pageCount;
                LocalDate date;
                //new book instance; New book
                while(bookCount>0){
                        String[] split =  scan.nextLine().split(",");
                        System.out.println("This line below is from initBooks:");
                        isbn = split[Book.ISBN_];
                        title = split[Book.TITLE_];
                        subject = split[Book.SUBJECT_];
                        pageCount = Integer.parseInt(split[Book.PAGE_COUNT_]);
                        author = split[Book.AUTHOR_];
                        date = convertDate(split[Book.DUE_DATE_], Code.DATE_CONVERSION_ERROR);
                        //add new book instance to list
                        //System.out.println(new Book(isbn,title,subject,pageCount,author,date));
                        addBook(new Book(isbn,title,subject,pageCount,author,date));
                        bookCount--;
                }

                return Code.SUCCESS;
        }
        private Code initShelves(int shelfCount,Scanner scan){
                //public final static int SHELF_NUMBER=0;
                //public final static int SUBJECT_=1;
                int shelfNumber;
                String subject;
                if(shelfCount < 1){
                        return Code.SHELF_COUNT_ERROR;
                }
                while(shelfCount>0){
                        //System.out.println("should be next lines from shelf: " + scan.nextLine());
                        String[] split =  scan.nextLine().split(",");
                        shelfNumber  =  Integer.parseInt(split[Shelf.SHELF_NUMBER]);
                        subject = split[Shelf.SUBJECT_];
                        addShelf(new Shelf(shelfNumber,subject));
                        shelfCount--;
                }
                if(shelves.size() != shelfCount){
                        System.out.println("Number of shelves doesn't match expected");
                        return Code.SHELF_NUMBER_PARSE_ERROR;
                }
                return Code.SUCCESS;
        }
        private Code initReader(int readerCount,Scanner scan){
                //public final static int CARD_NUMBER=0;
                //        public final static int NAME_ = 1;
                //        public final static int PHONE_ =2;
                //        public static int BOOK_COUNT_ = 3;
                //        public final static int BOOK_START_ = 4;
                //        public Reader(int cardNumber, String name, String phone);

                int cardNumber,bookcount,bookstart;
                String name,phone,isbn;
                LocalDate date;

                if(readerCount <=0){
                        return Code.READER_COUNT_ERROR;
                }
                while(readerCount>0){
                        String[] split =  scan.nextLine().split(",");
                        cardNumber = Integer.parseInt(split[Reader.CARD_NUMBER_]);
                        name = split[Reader.NAME_];
                        phone = split[Reader.PHONE_];
                        bookcount = Integer.parseInt(split[Reader.BOOK_COUNT_]);
                        //System.out.println("should be final lines from reader: " + scan.nextLine());
                        new Reader(cardNumber, name, phone);
                        int index=0;
                        for(int i = 0; i<bookcount;i++) {
                                isbn = split[Reader.BOOK_START_ + index];
                                date = convertDate(split[Reader.BOOK_START_ + 1 + index], Code.DATE_CONVERSION_ERROR);
                                getBookByISBN(isbn);
                                //TODO: If getBookISBN != null{call checkoutbook()}
                            index+=2;
                        }
                        readerCount--;
                }
                return Code.SUCCESS;
        }
//-----------------------CONVERTERS-------------------------------
        public static int convertInt(String recordCountString, Code code){
                int records;
                try {
                        records = Integer.parseInt(recordCountString);
                        //should catch if Exception
                        //else go here:
                        Code c = checkForInvalidParse(records);
                        if(c.equals(Code.SUCCESS)){
                                return records;
                        }else{
                                System.out.println("Error while parsing: [" + records + "]");
                                System.out.println("Error message: [" + c.getMessage() + "]");
                                return c.getCode();
                        }

                }catch (NumberFormatException e){
                        System.out.println("Value which caused the error: [" + recordCountString + "]");
                        System.out.println("Error message: [" + code.getMessage() + "]");
                        return code.getCode();
                }
        }
        public static LocalDate convertDate(String date, Code code){
                //split the string into year,month,day
                Integer[] dateSplit = new Integer[3];
                Integer i =0;
                int index = 0;
                for(String s :date.split("-")){
                        try{
                                i = Integer.parseInt(s);
                                dateSplit[index] = i;
                                index++;
                        }catch (NumberFormatException e) {
                                System.out.println("ERROR: " + Code.DATE_CONVERSION_ERROR +
                                        ", could not parse [" + s + "]  in [" + date + "]");
                                System.out.println("Using default date (01-jan-1970)");
                                return LocalDate.of(1970, 1, 1);
                        }
//                        }catch(DateTimeException dateTimeException){
//                                System.out.println("ERROR: " + dateTimeException  + ", " +
//                                        "could not parse [" + s + "]  in [" + date+ "]");
//                                System.out.println("Using default date (01-jan-1970)");
//                                return LocalDate.of(1970, 1, 1);
//                        }
                }
                //first case, date == 0000
                if(date.equals("0000")){
                        return LocalDate.of(1970,1,1);
                //if data length is missing one year, month, or day
                }else if(dateSplit.length != 3) {
                        System.out.println(Code.DATE_CONVERSION_ERROR + "," +
                                "could not parse [" + date + "]");
                        System.out.println("Using default date(01-Jan-1970)");
                        return LocalDate.of(1970, 1, 1);
                //next 3 cases are if one of the year,month,or day is negative.
                //returns corresponding message
                }else if(dateSplit[0] < 0){
                        //year
                        System.out.println("Error converting date: Year ["+
                                dateSplit[0]+"]");
                        System.out.println("Using default date (01-jan-1970)");
                        return LocalDate.of(1970, 1, 1);
                }else if(dateSplit[1] < 0){
                        System.out.println("Error converting date: Month ["+
                                dateSplit[1]+"]");
                        System.out.println("Using default date (01-jan-1970)");
                        return LocalDate.of(1970, 1, 1);
                }else if(dateSplit[2] < 0){
                        //day
                        System.out.println("Error converting date: Day ["+
                                dateSplit[2]+"]");
                        System.out.println("Using default date (01-jan-1970)");
                        return LocalDate.of(1970, 1, 1);
                //lastly,this case is that the date String is a valid date
                }else{
                     return LocalDate.of(dateSplit[0],
                            dateSplit[1],
                            dateSplit[2]);
                }
        }
        public static Code checkForInvalidParse(int parsedRecord){
                if(parsedRecord > 0){
                        return Code.SUCCESS;
                }
                System.out.println("No conditions met");
                return Code.UNKNOWN_ERROR;
        }
//-----------------LIST AND ADD STUFF--------------------------
        public int listBooks(){
                return 0;
        }
        public Code addBook(Book book){
                return Code.SUCCESS;
        }
        public Code addShelf(Shelf shelf){
                return  Code.SUCCESS;
        }
        public Code addShelf(String shelfSubject){
                return Code.SUCCESS;
        }
        //-----------------ERROR CODE-----------------------
        private Code errorCode(int codeNumber) {
                if (codeNumber < 0) {
                        for (Code c : Code.values()) {
                                if (c.getCode() == codeNumber) {
                                        return c;
                                }
                        }
                }
                return Code.UNKNOWN_ERROR;
        }
//------------------CUSTOM GET STUFF METHODS ------------------
        public Book getBookByISBN(String isbn){
                return null;
        }
}
