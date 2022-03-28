/*
 *Author: Jose Nick Flores
 * Date 13 Feb 22
 * Description: Class Reader file is part 02/04 of project.
 * class will keep track of a person's name,phone,and card.
 * Methods are addBook, removeBook, hasBook, Overrided toString, hashcode,and equals, and getters and setter.
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Reader {
        public final static int CARD_NUMBER_=0;
        public final static int NAME_ = 1;
        public final static int PHONE_ =2;
        public final static int BOOK_COUNT_ = 3;
        public final static int BOOK_START_ = 4;
        //int bookcount=0;
        private int cardNumber;
        private String name;
        private String phone;
        private ArrayList<Book> books = new ArrayList<Book>();

        public Reader(int cardNumber, String name, String phone) {
                this.cardNumber = cardNumber;
                this.name = name;
                this.phone = phone;
        }


        public int getBookCount() {
                return BOOK_COUNT_;
        }

        public int getCardNumber() {
                return cardNumber;
        }

        public void setCardNumber(int cardNumber) {
                this.cardNumber = cardNumber;
        }

        public String getName() {
                return name;
        }

        public void setName(String name) {
                this.name = name;
        }

        public String getPhone() {
                return phone;
        }

        public void setPhone(String phone) {
                this.phone = phone;
        }

        public ArrayList<Book> getBooks() {
                return books;
        }

        public void setBooks(ArrayList<Book> books) {
                this.books = books;
        }

        public Code addBook(Book book) {
                if (hasBook(book)) {
                        return Code.BOOK_ALREADY_CHECKED_OUT_ERROR;
                } else{
                books.add(book);
                return Code.SUCCESS;
                }

        }
        public Code removeBook(Book book){
                try{
                        if(!hasBook(book)) {
                                return Code.READER_DOESNT_HAVE_BOOK_ERROR;
                        }else if(books.remove(book)){
                                return Code.SUCCESS;
                        }
                }catch(Exception e){
                        System.out.println(e);
                        return Code.READER_COULD_NOT_REMOVE_BOOK_ERROR;
                }
                return Code.READER_COULD_NOT_REMOVE_BOOK_ERROR;
        }
        public boolean hasBook(Book book){
                return books.contains(book);
        }

        @Override
        public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                Reader reader = (Reader) o;
                return cardNumber == reader.cardNumber && Objects.equals(name, reader.name) && Objects.equals(phone, reader.phone);
        }

        @Override
        public int hashCode() {
                return Objects.hash(cardNumber, name, phone);
        }

        @Override
        public String toString() {
                return name +
                        " (" + cardNumber  +
                        ") has checked out { " +
                        books.toString()+"}";
        }
}

