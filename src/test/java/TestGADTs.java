import gadt.*;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * Created by bibou on 12/7/14.
 */
public class TestGADTs {

    @Before
    public void setUp() {

    }

    @Test
    public void testPlusEvaluator() {

        Plus expression = new Plus(new IntLit(3), new Plus(new IntLit(2), new IntLit(5)));

        assertEquals(10, NumberHigh.prj(expression.accept((Visitor) new EvalVisitor())).value.intValue());
    }
}
