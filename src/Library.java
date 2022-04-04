/*
Author: Jose Nick Flors
Date: March 28,2022
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
import java.util.*;
import java.util.HashMap;
import java.util.Objects;

public class Library {
//-----------FIELDS------
        static final int LENDING_LIMIT = 5;
        String name;
        static int libraryCard;
        private List<Reader> readers = new ArrayList<Reader>();
        private HashMap<String,Shelf> shelves =new HashMap<>();
        private HashMap<Book,Integer> books = new HashMap<>();
        private HashMap<Integer,String> test = new HashMap<>();
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
        }
//----------------INI----------------------
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
                if(records<0){
                        //System.out.println("error?");
                        return errorCode(records);
                }
                System.out.println("Parsing "+ records+"books");
                initBooks(records,scan);
                listBooks();
                /*scan is being parsed and when previous method ends
                scan leaves off from where it left off*/

                records = convertInt(scan.nextLine(), Code.SHELF_COUNT_ERROR);
                if(records<0){
                        return errorCode(records);
                }
                System.out.println("Parsing "+ records+" shelves");
                initShelves(records, scan);
                listShelves(true);

                //final parse
                System.out.println("Parsing "+ records+" readers");
                records = convertInt(scan.nextLine(), Code.READER_COUNT_ERROR);
                if(records<0){
                        return errorCode(records);
                }
                initReader(records,scan);
                listReaders();
                //System.out.println("Parse Complete!");

                return Code.UNKNOWN_ERROR;
        }
//---------------------BOOK STUFF---------------------------------------------
        private Code initBooks(int bookCount,Scanner scan){
                String isbn,title,subject,author;
                int pageCount;
                LocalDate date;
                //new book instance; New book
                while(bookCount>0){
                        String book = scan.nextLine();
                        System.out.println("Parsing book: " +book);
                        String[] split =  book.split(",");
                        //TODO: Length of SPLIT LESS than MAX INDEX
                        if(split.length<Book.DUE_DATE_){
                                //error
                                return Code.UNKNOWN_ERROR;
                        }

                        //System.out.println("This line below is from initBooks:");
                        //System.out.println("This is books: "+books);
                        //System.out.println("This is test: "+test);
                        isbn = split[Book.ISBN_];
                        title = split[Book.TITLE_];
                        subject = split[Book.SUBJECT_];
                        pageCount = convertInt(split[Book.PAGE_COUNT_],Code.PAGE_COUNT_ERROR);
                        author = split[Book.AUTHOR_];
                        date = convertDate(split[Book.DUE_DATE_], Code.DATE_CONVERSION_ERROR);
                        //add new book instance to list
                        //System.out.println(new Book(isbn,title,subject,pageCount,author,date));
                        Book newBook = new Book(isbn,title,subject,pageCount,author,date);
                        //System.out.println("This is books: "+books.put(newBook,1));
                        addBook(newBook);
                        bookCount--;
                }

                return Code.SUCCESS;
        }
        public Code addBook(Book newBook){
                //try{
                if(getBooks().containsKey(newBook)){
                        books.put(newBook,books.get(newBook)+1);
                        System.out.println(books.get(newBook)
                                +" copies of [" + newBook.getTitle()
                                +"] in the stacks.");
                }else{
                        books.put(newBook,1);
                        System.out.println("["+newBook.getTitle()+"] added to the stacks.");
                }
                for(Map.Entry<String,Shelf> s : shelves.entrySet()){
                        if(s.getValue().getSubject().equalsIgnoreCase(newBook.getSubject())){
                                addBookToShelf(newBook,s.getValue());
                                return Code.SUCCESS;
                        }
                }
                System.out.println("No shelf for ["+newBook.getSubject()+"] books");
                return Code.SHELF_EXISTS_ERROR;
//                }catch (NullPointerException e){
//                        System.out.println("first iteration");
//                        books.put(newBook,1);
//                        System.out.println("["+newBook.getTitle()+"] added to the stacks. Through catch");;
//                        return Code.UNKNOWN_ERROR;
//                }
        }
        private Code addBookToShelf(Book book, Shelf shelf){
                if(returnBook(book).equals(Code.SUCCESS)){
                        shelf.addBook(book);
                        return Code.SUCCESS;
                }else if(!shelf.getSubject().equals(book.getSubject())){
                        return Code.SHELF_SUBJECT_MISMATCH_ERROR;
                }else if(shelf.addBook(book).equals(Code.SUCCESS)){
                        System.out.println("[" +  book.getTitle() +"] added to shelf");
                        return Code.SUCCESS;
                }else{
                        System.out.println("Could not add ["+book+"] to shelf");
                        return shelf.addBook(book);
                }
        }
        public Book getBookByISBN(String isbn){
                for(Book b: books.keySet()){
                        if(b.getIsbn().equals(isbn)){
                                return b;
                        }
                }
                System.out.println("ERROR: Could not find a book with isbn: ["+isbn+"]");
                return null;
        }
        public int listBooks(){
                //TODO: StringBuilder
                int totalCount=0;
                for(Book b: books.keySet()){
                        System.out.println(books.get(b)+
                                "copies of "+ b);
                        totalCount+=books.get(b);
                }
                return totalCount;
        }
        public Code checkOutBook(Reader reader ,Book book){
                if(reader == null){
                        return Code.UNKNOWN_ERROR;
                }
                if(!readers.contains(reader)){
                        System.out.println("["+reader.getName()+"] doesn't have an account here");
                        return Code.READER_NOT_IN_LIBRARY_ERROR;
                }else{
                        if(reader.getBooks().size()>=LENDING_LIMIT){
                                System.out.println("["+reader.getName()+"] has reached the lending limit, ("+
                                        LENDING_LIMIT+")");
                                return Code.BOOK_LIMIT_REACHED_ERROR;
                        }
                }
                if(!books.containsKey(book)){
                        System.out.println("ERROR: could not find ["+book+"]");
                        return  Code.BOOK_NOT_IN_INVENTORY_ERROR;
                }
                for(Shelf s: shelves.values()){
                        if(s.getSubject().equals(book.getSubject())){
                                if(s.getBookCount(book)<1){
                                        System.out.println("ERROR: no copies of ["+book+"] remain");
                                        return Code.BOOK_NOT_IN_INVENTORY_ERROR;
                                }
                                if(!reader.addBook(book).equals(Code.SUCCESS)){
                                        System.out.println("Couldn't checkout ["+book+"]");
                                }else {
                                        Code c = s.removeBook(book);
                                        if (c.equals(Code.SUCCESS)) {
                                                System.out.println("[" + book + "] checked out successfully");
                                                return c;
                                        }else {
                                                return c;
                                        }
                                }
                        }
                }
                System.out.println("No shelf for ["+book.getSubject()+"] books!");
                return Code.SHELF_EXISTS_ERROR;
        }
        public Code returnBook(Book book){
                for(String s: shelves.keySet()){
                        if(s.equals(book.getSubject())){
                                shelves.get(s).addBook(book);
                                return Code.SUCCESS;
                        }
                }
                System.out.println("No shelf for ["+book+"]");
                return Code.SHELF_EXISTS_ERROR;
        }
        public Code returnBook(Reader reader,Book book){
                if(reader == null){
                        return Code.UNKNOWN_ERROR;
                }
                if(!reader.getBooks().contains(book)){
                        System.out.println("["+reader.getName()+"] doesn't have ["
                                +book.getTitle()+"] checked out");
                        return Code.READER_DOESNT_HAVE_BOOK_ERROR;
                }
                if(!books.containsKey(book)){
                        return Code.BOOK_NOT_IN_INVENTORY_ERROR;
                }
                System.out.println("["+reader.getName()+"] is returning ["+book+"]");
                Code c = reader.removeBook(book);
                if(c.equals(Code.SUCCESS)){
                        returnBook(book);
                        return Code.SUCCESS;
                }else{
                        System.out.println("Could not return ["+book+"]");
                        return c;
                }

        }
//-----------------------SHELF STUFF---------------------------------------------
        private Code initShelves(int shelfCount,Scanner scan){
                //public final static int SHELF_NUMBER=0;
                //public final static int SUBJECT_=1;
                int shelfNumber;
                String subject;
                if(shelfCount < 1){
                        return Code.SHELF_COUNT_ERROR;
                }
                int count = shelfCount;
                while(count>0){
                        //System.out.println("should be next lines from shelf: " + scan.nextLine());
                        String shelf = scan.nextLine();
                        System.out.println("Parsing Shelf: "+shelf);
                        String[] split =  shelf.split(",");
                        shelfNumber  =  Integer.parseInt(split[Shelf.SHELF_NUMBER]);
                        subject = split[Shelf.SUBJECT_];
                        addShelf(new Shelf(shelfNumber,subject));
                        count--;
                }
                if(shelves.size() != shelfCount){
                        System.out.println("Number of shelves doesn't match expected");
                        return Code.SHELF_NUMBER_PARSE_ERROR;
                }
                return Code.SUCCESS;
        }
        public Code listShelves(Boolean showBooks){
                if(showBooks.equals(true)){
                        for(Shelf s: shelves.values()){
                                s.listBooks();
                        }
                }else{
                        for(Shelf s: shelves.values()){
                                System.out.println(s);
                        }
                }
                return Code.SUCCESS;
        }
        public Code addShelf(Shelf shelf){
                if(shelves.containsKey(shelf.getSubject())){
                        System.out.println("ERROR: Shelf already exists ["+shelf+"]");
                        return Code.SHELF_EXISTS_ERROR;
                }else{
                        shelves.put(shelf.getSubject(),shelf);
                        shelf.setShelfNumber(shelves.size()+1);
                }
                for(Map.Entry<Book,Integer> b: books.entrySet()){
                        if(b.getKey().getSubject().equals(shelf.getSubject())){
                                addBookToShelf(b.getKey(),shelf);
                        }
                }
                return  Code.SUCCESS;
        }
        public Code addShelf(String shelfSubject){
                addShelf(new Shelf(shelves.size()+1,shelfSubject));
                return Code.SUCCESS;
        }
        public Shelf getShelf(Integer shelfNumber){
                for(Shelf s: shelves.values()){
                        if(s.getShelfNumber()==shelfNumber){
                                return s;
                        }
                }
                System.out.println("No shelf number ["+shelfNumber+"] found");
                return null;
        }
        public Shelf getShelf(String subject){
                for(String s: shelves.keySet()){
                        if(s.equals(subject)){
                                return shelves.get(s);
                        }
                }
                System.out.println("No shelf for ["+subject+"] books");
                return null;
        }
//-------------------------READER----------------------------------------------
        private Code initReader(int readerCount,Scanner scan){

                int cardNumber,bookcount,bookstart;
                String name,phone,isbn;
                LocalDate date;

                if(readerCount <=0){
                        System.out.println(Code.READER_COUNT_ERROR);
                        return Code.READER_COUNT_ERROR;
                }
                while(readerCount>0){
                        String reader= scan.nextLine();
                        System.out.println("Parsing Reader: " +reader);
                        String[] split =  reader.split(",");
                        System.out.println(split.length);
                        cardNumber = Integer.parseInt(split[Reader.CARD_NUMBER_]);
                        name = split[Reader.NAME_];
                        phone = split[Reader.PHONE_];
                        bookcount = Integer.parseInt(split[Reader.BOOK_COUNT_]);
                        Reader newReader =  new Reader(cardNumber, name, phone);
                        addReader(newReader);
                        //System.out.println("should be final lines from reader: " + scan.nextLine());
                        int index=0;
                        for(int i = 0; i<bookcount;i++) {
                                isbn = split[Reader.BOOK_START_ + index];
                                date = convertDate(split[Reader.BOOK_START_ + 1 + index], Code.DATE_CONVERSION_ERROR);
                                if(getBookByISBN(isbn) !=null){
                                        checkOutBook(getReaderByCard(cardNumber), getBookByISBN(isbn));
                                }else{
                                        System.out.println("ERROR");
                                }
                            index+=2;
                        }

                        readerCount--;
                }
                return Code.SUCCESS;
        }
        public int listReaders(){
                for(Reader r: readers){
                        System.out.println(r);
                }
                return readers.size();
        }
        public int listReaders(Boolean showBooks){
                int count=1;
                if(showBooks.equals(true)){
                        for(Reader r:readers){
                                System.out.println(r.getName()+
                                        "(#"+count+") has the following books");
                                System.out.println("["+r.getBooks()+"]");
                                count+=1;
                        }
                        return count;
                }else{
                        for(Reader r: readers){
                                System.out.println(r);
                        }
                        return  readers.size();
                }
        }
        public Code addReader(Reader reader){
                if(readers.contains(reader)){
                        System.out.println(reader.getName()+
                                "already has an account!");
                        return Code.READER_ALREADY_EXISTS_ERROR;
                }
                for(Reader r: readers) {
                        if (r.getCardNumber() == reader.getCardNumber()) {
                                System.out.println("[" + reader.getName() + "]"
                                        + "and" + "[" + r.getName() + "]" +
                                        "have the same card number!");
                                return Code.READER_CARD_NUMBER_ERROR;
                        }
                }
                readers.add(reader);
                System.out.println("["+reader.getName()+"]"+
                        "added to the library!");
                if(reader.getCardNumber()>libraryCard){
                        libraryCard = reader.getCardNumber();
                }

                return Code.SUCCESS;
        }
        public Reader getReaderByCard(int cardNumber){
                for(Reader r: readers){
                        if(r.getCardNumber()==cardNumber){
                                return r;
                        }
                }
                System.out.println("Could not find a reader with card #["+cardNumber+"]"
                );
                return null;
        }
        public static int getLibraryCardNumber(){

                return libraryCard+1;
        }
        public Code removeReader(Reader reader){
                if(readers.contains(reader)){
                        if(reader.getBooks()==null){
                                readers.remove(reader);
                                return Code.SUCCESS;
                        }else{
                                System.out.println("["+reader.getName()+"] must return all books!");
                                return Code.READER_STILL_HAS_BOOKS_ERROR;
                        }
                }else{
                        System.out.println("["+reader+"]" +
                                "is not part of this Library");
                        return Code.READER_NOT_IN_LIBRARY_ERROR;
                }
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
        public static LocalDate convertDate(String date, Code errorCode){
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
}

