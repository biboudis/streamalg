package gadt;

/**
 * Created by bibou on 12/5/14.
 */
public abstract class Expr<T> implements App<Expr.t, T> {

    public static class t {

    }

    static <A> Expr<A> prj(App<Expr.t, A> app) {
        return (Expr<A>) app;
    };

    abstract <C> App<C, T> accept(Visitor<C> v);
}
