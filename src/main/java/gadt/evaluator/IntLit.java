package gadt.evaluator;

import gadt.App;

/**
 * Authors:
 * Aggelos Biboudis (@biboudis)
 * Nick Palladinos (@NickPalladinos)
 */
public class IntLit extends Expr<Integer> {
    public final Integer value;

    public IntLit(Integer i) {
        this.value = i;
    }

    @Override
    public <C> App<C, Integer> accept(Visitor<C> v) {
        return v.caseIntLit(this);
    }
}
