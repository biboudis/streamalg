package gadt;

/**
 * Created by bibou on 12/5/14.
 */
public class IntLit extends Expr<Integer>  {
    public Integer value;

    public IntLit(Integer i) {
        this.value = i;
    }

    @Override
    public <C> App<C, Integer> accept(Visitor<C> v) {
        return v.caseIntLit(this);
    }
}
