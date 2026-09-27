import org.example.FieldMask;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class FieldMaskTest {

    @Test
    void hasReturnsTrueForIncludedField() {
        FieldMask mask = new FieldMask(FieldMask.TITLE | FieldMask.PRICE);
        assertTrue(mask.has(FieldMask.TITLE));
        assertTrue(mask.has(FieldMask.PRICE));
    }

    @Test
    void hasReturnsFalseForExcludedField() {
        FieldMask mask = new FieldMask(FieldMask.TITLE | FieldMask.PRICE);
        assertFalse(mask.has(FieldMask.AUTHOR));
        assertFalse(mask.has(FieldMask.ID));
        assertFalse(mask.has(FieldMask.GENRE));
    }

    @Test
    void allMaskContainsEveryField() {
        FieldMask mask = new FieldMask(FieldMask.ALL);
        assertTrue(mask.has(FieldMask.ID));
        assertTrue(mask.has(FieldMask.TITLE));
        assertTrue(mask.has(FieldMask.AUTHOR));
        assertTrue(mask.has(FieldMask.PRICE));
        assertTrue(mask.has(FieldMask.GENRE));
    }

    @Test
    void emptyMaskHasNothing() {
        FieldMask mask = new FieldMask(0);
        assertFalse(mask.has(FieldMask.TITLE));
        assertEquals(0, mask.getMask());
    }

    @Test
    void fieldsAreDistinctBits() {
        // каждое поле — отдельная степень двойки, пересечений быть не должно
        assertEquals(0, FieldMask.ID & FieldMask.TITLE);
        assertEquals(0, FieldMask.TITLE & FieldMask.AUTHOR);
        assertEquals(0, FieldMask.PRICE & FieldMask.GENRE);
    }

    @Test
    void maskStoresExactBitValue() {
        // TITLE=2, PRICE=8 -> вместе 10
        FieldMask mask = new FieldMask(FieldMask.TITLE | FieldMask.PRICE);
        assertEquals(10, mask.getMask());
    }

    @Test
    void addingFieldChangesMask() {
        int base = FieldMask.TITLE;                 // 2
        int withAuthor = base | FieldMask.AUTHOR;   // 2 | 4 = 6
        assertEquals(6, withAuthor);
        assertNotEquals(base, withAuthor);
    }

    @Test
    void allEqualsSumOfAllBits() {
        // 1+2+4+8+16 = 31
        assertEquals(31, FieldMask.ALL);
    }
}