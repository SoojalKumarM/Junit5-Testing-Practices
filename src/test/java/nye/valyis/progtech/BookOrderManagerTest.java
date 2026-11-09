package nye.valyis.progtech;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BookOrderManagerTest {

    private BookOrderManager manager;
    private Book b1;
    private Book b2;

    @BeforeEach
    void setUp() {
        manager = new BookOrderManager();
        b1 = new Book("Effective Java", "Joshua Bloch", 8000);
        b2 = new Book("Java Concurrency in Practice", "Brian Goetz", 9000);
    }

    @Test
    void testConstructors() {
        BookOrderManager emptyManager = new BookOrderManager();
        assertEquals(0, emptyManager.getBookCount());

        BookOrderManager nullManager = new BookOrderManager(null);
        assertEquals(0, nullManager.getBookCount());

        List<Book> list = Arrays.asList(b1, b2);
        BookOrderManager listManager = new BookOrderManager(list);
        assertEquals(2, listManager.getBookCount());
    }

    @Test
    void testAddBookSuccess() {
        manager.addBook(b1);
        assertEquals(1, manager.getBookCount());
        assertTrue(manager.getBooks().contains(b1));
    }

    @Test
    void testAddBookExceptions() {
        assertThrows(IllegalArgumentException.class, () -> manager.addBook(null));

        Book invalidBook = new Book("Negative Price", "Author", -500);
        assertThrows(IllegalArgumentException.class, () -> manager.addBook(invalidBook));
    }

    @Test
    void testRemoveBook() {
        manager.addBook(b1);
        assertFalse(manager.removeBook(null));
        assertFalse(manager.removeBook(b2));
        assertTrue(manager.removeBook(b1));
        assertEquals(0, manager.getBookCount());
    }

    @Test
    void testCalculateTotalPrice() {
        assertEquals(0, manager.calculateTotalPrice());

        manager.addBook(b1);
        manager.addBook(b2);
        assertEquals(17000, manager.calculateTotalPrice());
    }

    @Test
    void testCalculateDiscountedTotalPriceValid() {
        manager.addBook(b1); // 8000
        manager.addBook(b2); // 9000 -> total 17000

        assertEquals(17000.0, manager.calculateDiscountedTotalPrice(0), 0.001);
        assertEquals(15300.0, manager.calculateDiscountedTotalPrice(10), 0.001);
        assertEquals(0.0, manager.calculateDiscountedTotalPrice(100), 0.001);
    }

    @ParameterizedTest
    @ValueSource(doubles = {-0.1, -10.0, 100.1, 150.0})
    void testCalculateDiscountedTotalPriceInvalid(double discount) {
        assertThrows(IllegalArgumentException.class, () -> manager.calculateDiscountedTotalPrice(discount));
    }

    @Test
    void testFindBooksByAuthor() {
        manager.addBook(b1);
        manager.addBook(b2);

        assertTrue(manager.findBooksByAuthor(null).isEmpty());
        assertTrue(manager.findBooksByAuthor("").isEmpty());
        assertTrue(manager.findBooksByAuthor("   ").isEmpty());
        assertTrue(manager.findBooksByAuthor("Unknown Author").isEmpty());

        List<Book> found = manager.findBooksByAuthor("joshua bloch");
        assertEquals(1, found.size());
        assertEquals(b1, found.get(0));
    }

    @Test
    void testClearOrders() {
        manager.addBook(b1);
        manager.addBook(b2);
        assertEquals(2, manager.getBookCount());

        manager.clearOrders();
        assertEquals(0, manager.getBookCount());
        assertTrue(manager.getBooks().isEmpty());
    }
}
