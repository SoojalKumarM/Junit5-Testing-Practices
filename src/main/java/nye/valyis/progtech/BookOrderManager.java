package nye.valyis.progtech;

import java.util.ArrayList;
import java.util.List;

public class BookOrderManager {
    private List<Book> books;

    public BookOrderManager() {
        this.books = new ArrayList<>();
    }

    public BookOrderManager(List<Book> books) {
        this.books = books != null ? new ArrayList<>(books) : new ArrayList<>();
    }

    public void addBook(Book book) {
        if (book == null) {
            throw new IllegalArgumentException("Book cannot be null");
        }
        if (book.getPrice() < 0) {
            throw new IllegalArgumentException("Book price cannot be negative");
        }
        books.add(book);
    }

    public boolean removeBook(Book book) {
        if (book == null) {
            return false;
        }
        return books.remove(book);
    }

    public int calculateTotalPrice() {
        int total = 0;
        for (Book book : books) {
            total += book.getPrice();
        }
        return total;
    }

    public double calculateDiscountedTotalPrice(double discountPercentage) {
        if (discountPercentage < 0 || discountPercentage > 100) {
            throw new IllegalArgumentException("Discount percentage must be between 0 and 100");
        }
        int total = calculateTotalPrice();
        return total * (1 - discountPercentage / 100.0);
    }

    public List<Book> getBooks() {
        return new ArrayList<>(books);
    }

    public int getBookCount() {
        return books.size();
    }

    public List<Book> findBooksByAuthor(String author) {
        List<Book> result = new ArrayList<>();
        if (author == null || author.trim().isEmpty()) {
            return result;
        }
        for (Book book : books) {
            if (author.equalsIgnoreCase(book.getAuthor())) {
                result.add(book);
            }
        }
        return result;
    }

    public void clearOrders() {
        books.clear();
    }
}
