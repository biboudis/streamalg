package gadt;

/**
 * Created by bibou on 12/5/14.
 */
public class If<T> extends Expr<T>
{
    Expr <Boolean> x;
    Expr <T> y,z;

    @Override
    public <C> App<C, T> accept(Visitor<C> v) {
        return v.caseIf(this);
    }
}
