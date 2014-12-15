package gadt;

import gadt.*;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * Authors:
 *      Aggelos Biboudis (@biboudis)
 *      Nick Palladinos (@NickPalladinos)
 */
public class TestGADTEvaluator {

    @Test
    public void testPlus() {
        Plus expression = new Plus(new IntLit(3), new Plus(new IntLit(2), new IntLit(5)));

        assertEquals((Integer) 10, Id.prj(expression.accept(new EvalVisitor())).value);
    }

    @Test
    public void testIf() {
        If<Integer> expression = new If<>(new BoolLit(true),
                new Plus(new IntLit(1), new Plus(new IntLit(1), new IntLit(1))),
                new Plus(new IntLit(5), new Plus(new IntLit(5), new IntLit(5))));

        assertEquals(3, Id.prj(expression.accept(new EvalVisitor())).value.intValue());
    }
}
