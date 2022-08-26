import hu.unideb.inf.foxandhounds.game.Vector2;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class VectorTests {
    @Test
    public void testEquality() {
        Vector2 v1 = new Vector2(1, 2);
        Vector2 v2 = new Vector2(3, 4);
        List<Vector2> list = new ArrayList<>();
        list.add(v1);
        list.add(v2);

        assertTrue(list.contains(new Vector2(1, 2)));
        assertTrue(list.contains(new Vector2(3, 4)));
        assertFalse(list.contains(new Vector2(9, 9)));

        assertTrue(v2.equals(new Vector2(3, 4)));
    }
}
