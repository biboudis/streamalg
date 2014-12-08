package gadt;

import gadt.primitives.NumberHigh;

/**
 * Created by bibou on 12/5/14.
 */
public class IntLit extends Expr<NumberHigh>  {
    public NumberHigh value;

    public IntLit(int i) {
        this.value = new NumberHigh(i);
    }

    @Override
    public <C> App<C, NumberHigh> accept(Visitor<C> v) {
        return v.caseIntLit(this);
    }
}
