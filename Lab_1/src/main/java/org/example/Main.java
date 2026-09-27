package org.example;

public class Main {
    public static void main(String[] args) {
        BookDatabase db = new BookDatabase();
        db.add(new Book(1, "Java Basics", "Ivan Petrov", 25.5f, Genre.SCIENCE));
        db.add(new Book(2, "History of Rome", "Anna Smith", 30.0f, Genre.HISTORY));
        db.add(new Book(3, "Magic World", "Ivan Petrov", 15.0f, Genre.FANTASY));

        System.out.println("=== Поиск по автору 'Ivan Petrov' ===");
        for (Book b : db.findByAuthor("Ivan Petrov")) {
            System.out.println(b);
        }

        System.out.println("\n=== Печать по маске (title + price) ===");
        FieldMask titlePrice = new FieldMask(FieldMask.TITLE | FieldMask.PRICE);
        for (Book b : db.getAll()) {
            BookDatabase.printByMask(b, titlePrice);
        }

        System.out.println("\n=== Копирование: книгам Ivan Petrov ставим цену и жанр source ===");
        Book source = new Book(0, "", "Ivan Petrov", 99.9f, Genre.POETRY);
        FieldMask matchMask = new FieldMask(FieldMask.AUTHOR);       // куда копировать
        FieldMask copyMask  = new FieldMask(FieldMask.PRICE | FieldMask.GENRE); // что копировать
        int changed = db.copyByMask(source, matchMask, copyMask);
        System.out.println("Изменено книг: " + changed);
        for (Book b : db.getAll()) {
            System.out.println(b);
        }
    }
}