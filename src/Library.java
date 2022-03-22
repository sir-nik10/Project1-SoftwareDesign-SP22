/*
Author: Jose Nick Flors
Date: March
Filename: Library.java; Project 4/4
Abstract: Library.java will read data from a file,
         initialize that data into memory,
         and retrieve relevant information
         from other classes.
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Library {

        static final int LENDING_LIMIT = 5;
        String name;
        static int libraryCard;
        List<Reader> readers  =  new ArrayList<>();
        HashMap<String, Shelf> shelves  = new HashMap<>();
        HashMap<Book, Integer>  books = new HashMap<>();
//-----------fields------
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
//-------------------get and set---------
        public Library(String name) {
                this.name = name;
                this.readers = null;
                this.shelves = null;
                this.books = null;
        }
//--------------constructor---------------
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

                //LIST BOOKS GOES HERE

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
                while(bookCount>0){
                        System.out.println("should be first 9 lines: " + scan.nextLine());
                        bookCount--;
                }
                return Code.UNKNOWN_ERROR;
        }
        private Code initShelves(int shelfCount,Scanner scan){
                while(shelfCount>0){
                        System.out.println("should be next lines from shelf: " + scan.nextLine());
                        shelfCount--;
                }
                return Code.UNKNOWN_ERROR;
        }
        private Code initReader(int readerCount,Scanner scan){
                while(readerCount>0){
                        System.out.println("should be final lines from reader: " + scan.nextLine());
                        readerCount--;
                }
                return Code.UNKNOWN_ERROR;
        }
//----------------inits-------------
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
                return LocalDate.now();
        }
//------------converters---------------
        public static Code checkForInvalidParse(int parsedRecord){
                if(parsedRecord < 0){
                        for(Code c :Code.values()){
                                if(c.getCode() == parsedRecord){
                                        return c;
                                }
                        }
                }else if(parsedRecord > 0){
                        return Code.SUCCESS;
                }
                System.out.println("No conditions met");
                return Code.UNKNOWN_ERROR;
        }
}
