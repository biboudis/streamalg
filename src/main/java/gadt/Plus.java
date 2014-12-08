package gadt;

/**
 * Created by bibou on 12/5/14.
 */
public class Plus extends Expr<Integer> {
    Expr<Integer> left, right;

    public Plus(Expr<Integer> left, Expr<Integer> right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public <C> App<C, Integer> accept(Visitor<C> v) {
        return v.casePlus(this);
    }
}
