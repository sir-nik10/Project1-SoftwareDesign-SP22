/*
 *Jose Nick Flores
 * Date: March 28,2022
 * Project 01 Part 03/04 Shelf.java
 * Description: Class for Shelf that will be used in future project
 * puts books, removes books, lists books aswell.
 */
import java.util.HashMap;
import java.util.Objects;

public class Shelf {
    public final static int SHELF_NUMBER=0;
    public final static int SUBJECT_=1;
    private int shelfNumber;
    private String subject;
    private HashMap<Book, Integer> books = new HashMap<Book, Integer>();

    //-----------------get/set-------------------------------
    public HashMap<Book, Integer> getBooks() {
        return books;
    }

    public int getShelfNumber() {
        return shelfNumber;
    }

    public void setShelfNumber(int shelfNumber) {
        shelfNumber = shelfNumber;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setBooks(HashMap<Book, Integer> books) {
        this.books = books;
    }
    //-----------------get/set-------------------------------


    public Shelf(int shelfNumber, String subject){
        this.shelfNumber = shelfNumber;
        this.subject = subject;
    }
    public Code addBook(Book book) {
        if(books.containsKey(book) && book.getSubject().equals(this.subject)){
            books.put(book,getBookCount(book)+1);
            return Code.SUCCESS;
        }else if(!books.containsKey(book) && book.getSubject().equals(subject)){
            books.put(book,1);
            return Code.SUCCESS;
        }else{
            return Code.SHELF_SUBJECT_MISMATCH_ERROR;
        }
    }

    public int getBookCount(Book book){
      int count=0;
      if(!books.containsKey(book)){
          return -1;
      }else{
          return books.get(book);
      }
    }

    public String listBooks(){
        return books.size() + ":"+
                shelfNumber +":"+
                subject+"\n" + books.toString();
    }

    public Code removeBook(Book book){
        if(!books.containsKey(book)){
            System.out.println(book.getTitle()+"is not in shelf " + subject);
            return Code.BOOK_NOT_IN_INVENTORY_ERROR;
        }else if(books.containsKey(book) && books.get(book) == 0){
            System.out.println("No copies of "+book.getTitle()+"remain on shelf " + subject);
            return Code.BOOK_NOT_IN_INVENTORY_ERROR;
        }else{
            System.out.println(book.getTitle()+ " successfully removed from shelf "+ subject );
            books.put(book,getBookCount(book)-1);
            return Code.SUCCESS;
        }

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Shelf shelf = (Shelf) o;
        return shelfNumber == shelf.shelfNumber && Objects.equals(subject, shelf.subject);
    }

    @Override
    public int hashCode() {
        return Objects.hash(shelfNumber, subject);
    }

    @Override
    public String toString() {
        return shelfNumber +
                ": " + subject;
    }

}
