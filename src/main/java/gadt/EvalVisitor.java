package gadt;

/**
 * Created by bibou on 12/5/14.
 */
public class EvalVisitor implements Visitor<Id.t> {

    @Override
    public App<Id.t, Integer> caseIntLit(IntLit expr) {
        return Id.newA(expr.value);
    }

    @Override
    public App<Id.t, Boolean> caseBoolLit(BoolLit expr) {
        return Id.newA(expr.value);
    }

    @Override
    public App<Id.t, Integer> casePlus(Plus expr) {

        Id<Integer> left = Id.prj(expr.left.accept(this));
        Id<Integer> right = Id.prj(expr.right.accept(this));

        return Id.newA(left.value + right.value);
    }

    @Override
    public <T> App<Id.t, T> caseIf(If<T> expr) {
        Id<Boolean> test = Id.prj(expr.x.accept(this));

        return test.value ? expr.y.accept(this): expr.z.accept(this);
    }
}
