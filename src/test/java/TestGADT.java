import gadt.*;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by bibou on 12/7/14.
 */
public class TestGADT {

    @Before
    public void setUp() {

    }

    @Test
    public void testEvaluator() {
        Expr<Integer> expression = new Plus(new IntLit(2), new IntLit(3));

        //App<Expr.t, Integer> app = new EvalVisitor<Integer>().eval(expression);
    }
}
