package org.example;

public class FieldMask {
    public static final int ID = 1;      // 00001
    public static final int TITLE = 1 << 1; // 00010
    public static final int AUTHOR = 1 << 2; // 00100
    public static final int PRICE = 1 << 3; // 01000
    public static final int GENRE = 1 << 4; // 10000

    public static final int ALL = ID | TITLE | AUTHOR | PRICE | GENRE;

    private int mask;

    public FieldMask(int mask) {
        this.mask = mask;
    }

    // включено ли поле в маску
    public boolean has(int field) {
        return (mask & field) != 0;
    }

    public int getMask() {
        return mask;
    }
}
