package gadt.evaluator;

import gadt.App;

/**
 * Authors:
 *      Aggelos Biboudis (@biboudis)
 *      Nick Palladinos (@NickPalladinos)
 */
public abstract class Expr<T> {
    public abstract <C> App<C, T> accept(Visitor<C> v);
}
