package gadt;

/**
 * Created by bibou on 12/5/14.
 */
public class BoolLit extends Expr<Boolean> {
    Boolean value;

    public BoolLit(Boolean i) {
        this.value = i;
    }

    @Override
    public <C> App<C, Boolean> accept(Visitor<C> v) {
        return v.caseBoolLit(this);
    }
}
