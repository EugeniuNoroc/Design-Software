import org.example.Book;
import org.example.BookDatabase;
import org.example.FieldMask;
import org.example.Genre;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class BookDatabaseTest {

    private BookDatabase db;

    @BeforeEach
    void setUp() {
        db = new BookDatabase();
        db.add(new Book(1, "Java Basics", "Ivan Petrov", 25.5f, Genre.SCIENCE));
        db.add(new Book(2, "History of Rome", "Anna Smith", 30.0f, Genre.HISTORY));
        db.add(new Book(3, "Magic World", "Ivan Petrov", 15.0f, Genre.FANTASY));
    }

    @Test
    void findByAuthorReturnsMatchingBooks() {
        List<Book> found = db.findByAuthor("Ivan Petrov");
        assertEquals(2, found.size());
    }

    @Test
    void findByAuthorReturnsEmptyWhenNoMatch() {
        List<Book> found = db.findByAuthor("Unknown");
        assertTrue(found.isEmpty());
    }

    @Test
    void copyByMaskChangesOnlySelectedFields() {
        Book source = new Book(0, "SRC", "Ivan Petrov", 99.9f, Genre.POETRY);
        FieldMask matchMask = new FieldMask(FieldMask.AUTHOR);
        FieldMask copyMask  = new FieldMask(FieldMask.PRICE | FieldMask.GENRE);

        int changed = db.copyByMask(source, matchMask, copyMask);

        assertEquals(2, changed); // две книги Ivan Petrov

        for (Book b : db.getAll()) {
            if (b.getAuthor().equals("Ivan Petrov")) {
                assertEquals(99.9f, b.getPrice());   // цена скопирована
                assertEquals(Genre.POETRY, b.getGenre()); // жанр скопирован
            }
        }
    }

    @Test
    void copyByMaskDoesNotTouchFieldsOutsideCopyMask() {
        Book source = new Book(0, "SRC", "Ivan Petrov", 99.9f, Genre.POETRY);
        FieldMask matchMask = new FieldMask(FieldMask.AUTHOR);
        FieldMask copyMask  = new FieldMask(FieldMask.PRICE); // копируем только цену

        db.copyByMask(source, matchMask, copyMask);

        Book first = db.findByAuthor("Ivan Petrov").get(0);
        assertEquals(99.9f, first.getPrice());          // цена изменилась
        assertNotEquals("SRC", first.getTitle());       // title НЕ трогали
        assertNotEquals(Genre.POETRY, first.getGenre()); // genre НЕ трогали
    }

    @Test
    void copyByMaskSkipsNonMatchingBooks() {
        Book source = new Book(0, "SRC", "Ivan Petrov", 99.9f, Genre.POETRY);
        FieldMask matchMask = new FieldMask(FieldMask.AUTHOR);
        FieldMask copyMask  = new FieldMask(FieldMask.PRICE);

        db.copyByMask(source, matchMask, copyMask);

        // книга Anna Smith не должна измениться
        Book anna = db.findByAuthor("Anna Smith").get(0);
        assertEquals(30.0f, anna.getPrice());
    }
}