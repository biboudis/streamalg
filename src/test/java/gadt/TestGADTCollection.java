package gadt;

import gadt.collection.List;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * Authors:
 *      Aggelos Biboudis (@biboudis)
 *      Nick Palladinos (@NickPalladinos)
 */
public class TestGADTCollection {

    @Test
    public void testFilter(){
        List<Integer> list = new List<>();

        list.add(3);

        list.add(5);

        List<Integer> list2 = List.prj(list.remove(x -> x>3));

        assertEquals(list2.count(), 1);
        assertEquals(list2.get(0).intValue(), 3);
    }
}
