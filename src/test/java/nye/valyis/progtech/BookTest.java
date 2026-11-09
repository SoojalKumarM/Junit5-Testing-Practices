package nye.valyis.progtech;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BookTest {

    private Book book;

    @BeforeEach
    void setUp() {
        book = new Book("Clean Code", "Robert C. Martin", 10000);
    }

    @Test
    void testGettersAndSetters() {
        assertEquals("Clean Code", book.getTitle());
        assertEquals("Robert C. Martin", book.getAuthor());
        assertEquals(10000, book.getPrice());

        book.setTitle("Refactoring");
        book.setAuthor("Martin Fowler");
        book.setPrice(12000);

        assertEquals("Refactoring", book.getTitle());
        assertEquals("Martin Fowler", book.getAuthor());
        assertEquals(12000, book.getPrice());
    }

    @Test
    void testEqualsAndHashCode() {
        Book sameBook = new Book("Clean Code", "Robert C. Martin", 10000);
        Book differentTitle = new Book("Design Patterns", "Robert C. Martin", 10000);
        Book differentAuthor = new Book("Clean Code", "Martin Fowler", 10000);
        Book differentPrice = new Book("Clean Code", "Robert C. Martin", 15000);

        // Reflexive & Symmetric
        assertEquals(book, book);
        assertEquals(book, sameBook);
        assertEquals(book.hashCode(), sameBook.hashCode());

        // Null and different class checks
        assertNotEquals(null, book);
        assertNotEquals("Some String", book);

        // Field differences
        assertNotEquals(book, differentTitle);
        assertNotEquals(book, differentAuthor);
        assertNotEquals(book, differentPrice);
    }

    @Test
    void testToString() {
        String expected = "Book{title='Clean Code', author='Robert C. Martin', price=10000}";
        assertEquals(expected, book.toString());
    }
}
