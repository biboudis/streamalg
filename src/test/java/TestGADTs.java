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
    public void testPlus() {

        Plus expression = new Plus(new IntLit(3), new Plus(new IntLit(2), new IntLit(5)));

        assertEquals(10, NumberHigh.prj(expression.accept((Visitor) new EvalVisitor())).value.intValue());
    }

    @Test
    public void testIf() {

        If expression = new If(new BoolLit(true),
                new Plus(new IntLit(1), new Plus(new IntLit(1), new IntLit(1))),
                new Plus(new IntLit(5), new Plus(new IntLit(5), new IntLit(5))));

        assertEquals(3, NumberHigh.prj(expression.accept((Visitor) new EvalVisitor())).value.intValue());
    }
}
