package gadt;

import gadt.primitives.BooleanHigh;

/**
 * Created by bibou on 12/5/14.
 */
public class BoolLit extends Expr<BooleanHigh> {
    BooleanHigh value;

    public BoolLit(boolean i) {
        this.value = new BooleanHigh(i);
    }

    @Override
    public <C> App<C, BooleanHigh> accept(Visitor<C> v) {
        return v.caseBoolLit(this);
    }
}
