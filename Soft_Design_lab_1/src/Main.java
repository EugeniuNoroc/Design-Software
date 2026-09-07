public class Main {
    public static void main(String[] args) {
        BookDatabase db = new BookDatabase();
        db.add(new Book(1, "Java Basics", "Ivan Petrov", 25.5f, Genre.SCIENCE));
        db.add(new Book(2, "History of Rome", "Anna Smith", 30.0f, Genre.HISTORY));
        db.add(new Book(3, "Java Basics", "Ivan Petrov", 25.5f, Genre.SCIENCE)); // дубликат
        db.add(new Book(4, "Magic World", "Ivan Petrov", 15.0f, Genre.FANTASY));

        System.out.println("=== Поиск по автору 'Ivan Petrov' ===");
        for (Book b : db.findByAuthor("Ivan Petrov")) {
            System.out.println(b);
        }

        System.out.println("\n=== Печать по маске (title + price) ===");
        FieldMask titlePrice = new FieldMask(FieldMask.TITLE | FieldMask.PRICE);
        for (Book b : db.getAll()) {
            BookDatabase.printByMask(b, titlePrice);
        }

        System.out.println("\n=== Merge по (title, author, price) ===");
        FieldMask mergeMask = new FieldMask(FieldMask.TITLE | FieldMask.AUTHOR | FieldMask.PRICE);
        db.mergeByMask(mergeMask);
        for (Book b : db.getAll()) {
            System.out.println(b);
        }

        System.out.println("\n=== Копирование: у книг того же автора ставим цену и жанр source ===");
        Book source = new Book(0, "", "Ivan Petrov", 99.9f, Genre.POETRY);
        FieldMask matchMask = new FieldMask(FieldMask.AUTHOR);
        FieldMask copyMask = new FieldMask(FieldMask.PRICE | FieldMask.GENRE);
        db.copyByMask(source, matchMask, copyMask);
        for (Book b : db.getAll()) {
            System.out.println(b);
        }

        System.out.println("\n=== Комбинирование масок ===");
        FieldMask m1 = new FieldMask(FieldMask.TITLE | FieldMask.AUTHOR);
        FieldMask m2 = new FieldMask(FieldMask.AUTHOR | FieldMask.PRICE);
        System.out.println("union:     " + m1.union(m2).getMask());     // TITLE|AUTHOR|PRICE
        System.out.println("intersect: " + m1.intersect(m2).getMask()); // AUTHOR
        System.out.println("minus:     " + m1.minus(m2).getMask());     // TITLE
    }
}