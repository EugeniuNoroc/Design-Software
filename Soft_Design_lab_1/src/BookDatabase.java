import java.util.ArrayList;
import java.util.List;

public class BookDatabase {
    private final List<Book> books = new ArrayList<>();

    public void add(Book book) {
        books.add(book);
    }

    public List<Book> getAll() {
        return books;
    }

    // Задание 4: поиск по полю
    public List<Book> findByAuthor(String author) {
        List<Book> result = new ArrayList<>();
        for (Book b : books) {
            if (b.getAuthor().equals(author)) {
                result.add(b);
            }
        }
        return result;
    }

    // Сравнение двух книг только по полям из маски
    private boolean equalsByMask(Book a, Book b, FieldMask mask) {
        if (mask.has(FieldMask.ID)     && a.getId()     != b.getId())     return false;
        if (mask.has(FieldMask.TITLE)  && !a.getTitle().equals(b.getTitle()))   return false;
        if (mask.has(FieldMask.AUTHOR) && !a.getAuthor().equals(b.getAuthor())) return false;
        if (mask.has(FieldMask.PRICE)  && a.getPrice()  != b.getPrice())  return false;
        if (mask.has(FieldMask.GENRE)  && a.getGenre()  != b.getGenre())  return false;
        return true;
    }

    // Доп. задание: merge — оставляем по одной книге из групп, равных по маске
    public void mergeByMask(FieldMask mask) {
        List<Book> merged = new ArrayList<>();
        for (Book b : books) {
            boolean duplicate = false;
            for (Book m : merged) {
                if (equalsByMask(b, m, mask)) {
                    duplicate = true;
                    break;
                }
            }
            if (!duplicate) {
                merged.add(b);
            }
        }
        books.clear();
        books.addAll(merged);
    }

    // Доп. задание: копирование данных.
    // matchMask  - по каким полям искать совпадающие книги
    // copyMask   - какие поля скопировать из source
    public void copyByMask(Book source, FieldMask matchMask, FieldMask copyMask) {
        for (Book b : books) {
            if (equalsByMask(b, source, matchMask)) {
                if (copyMask.has(FieldMask.ID))     b.setId(source.getId());
                if (copyMask.has(FieldMask.TITLE))  b.setTitle(source.getTitle());
                if (copyMask.has(FieldMask.AUTHOR)) b.setAuthor(source.getAuthor());
                if (copyMask.has(FieldMask.PRICE))  b.setPrice(source.getPrice());
                if (copyMask.has(FieldMask.GENRE))  b.setGenre(source.getGenre());
            }
        }
    }

    // Задание 5: статический метод печати полей согласно маске
    public static void printByMask(Book book, FieldMask mask) {
        StringBuilder sb = new StringBuilder("Book: ");
        if (mask.has(FieldMask.ID))     sb.append("id=").append(book.getId()).append(" ");
        if (mask.has(FieldMask.TITLE))  sb.append("title=").append(book.getTitle()).append(" ");
        if (mask.has(FieldMask.AUTHOR)) sb.append("author=").append(book.getAuthor()).append(" ");
        if (mask.has(FieldMask.PRICE))  sb.append("price=").append(book.getPrice()).append(" ");
        if (mask.has(FieldMask.GENRE))  sb.append("genre=").append(book.getGenre()).append(" ");
        System.out.println(sb.toString().trim());
    }
}