package gadt;

/**
 * Created by bibou on 12/5/14.
 */
public abstract class Expr<T> {
    public abstract <C> App<C, T> accept(Visitor<C> v);
}
