package org.example;

public class Book {
    private int id;
    private String title;
    private String author;
    private float price;
    private Genre genre;

    public Book(int id, String title, String author, float price, Genre genre) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.price = price;
        this.genre = genre;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public float getPrice() { return price; }
    public Genre getGenre() { return genre; }

    public void setId(int id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }
    public void setAuthor(String author) { this.author = author; }
    public void setPrice(float price) { this.price = price; }
    public void setGenre(Genre genre) { this.genre = genre; }

    @Override
    public String toString() {
        return "Book{id=" + id + ", title='" + title + "', author='" + author +
                "', price=" + price + ", genre=" + genre + "}";
    }
}
