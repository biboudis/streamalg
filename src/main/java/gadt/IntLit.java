package gadt;

/**
 * Created by bibou on 12/5/14.
 */
public class IntLit extends Expr<Integer>  {
    int value;

    public IntLit(int i) {
        this.value = i;
    }

    @Override
    <C> App<C, Integer> accept(Visitor<C> v) {
        return v.caseIntLit(this);
    }
}
