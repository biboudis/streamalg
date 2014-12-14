package gadt;

/**
 * Authors:
 *      Aggelos Biboudis (@biboudis)
 *      Nick Palladinos (@NickPalladinos)
 */
public class Plus extends Expr<Integer> {
    final Expr<Integer> left;
    final Expr<Integer> right;

    public Plus(Expr<Integer> left, Expr<Integer> right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public <C> App<C, Integer> accept(Visitor<C> v) {
        return v.casePlus(this);
    }
}
