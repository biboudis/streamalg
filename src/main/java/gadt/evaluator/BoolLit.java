package gadt.evaluator;

import gadt.App;

/**
 * Authors:
 *      Aggelos Biboudis (@biboudis)
 *      Nick Palladinos (@NickPalladinos)
 */

public class BoolLit extends Expr<Boolean> {
    final Boolean value;

    public BoolLit(Boolean i) {
        this.value = i;
    }

    @Override
    public <C> App<C, Boolean> accept(Visitor<C> v) {
        return v.caseBoolLit(this);
    }
}
