package gadt;

import gadt.primitives.NumberHigh;

/**
 * Created by bibou on 12/5/14.
 */
public class Plus extends Expr<NumberHigh> {
    Expr<NumberHigh> left, right;

    public Plus(Expr<NumberHigh> left, Expr<NumberHigh> right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public <C> App<C, NumberHigh> accept(Visitor<C> v) {
        return v.casePlus(this);
    }
}
