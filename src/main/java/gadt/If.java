package gadt;

/**
 * Authors:
 *      Aggelos Biboudis (@biboudis)
 *      Nick Palladinos (@NickPalladinos)
 */
public class If<T> extends Expr<T>
{
    final Expr<Boolean> x;
    final Expr<T> y;
    final Expr<T> z;

    public If(Expr <Boolean> test, Expr<T> thenExpr, Expr<T> elseExpr) {
        x = test;
        y = thenExpr;
        z = elseExpr;
    }

    @Override
    public <C> App<C, T> accept(Visitor<C> v) {
        return v.caseIf(this);
    }
}
