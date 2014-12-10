package gadt;

/**
 * Created by bibou on 12/5/14.
 */
public class EvalVisitor implements Visitor<Id.t> {

    <T> Id<T> eval(Expr<T> e){
        return Id.prj(e.accept(this));
    }

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

        Id<Integer> left = Id.prj(eval(expr.left));
        Id<Integer> right = Id.prj(eval(expr.right));

        return Id.newA(left.value + right.value);
    }

    @Override
    public <T> App<Id.t, T> caseIf(If<T> expr) {
        Id<Boolean> test = Id.prj(eval(expr.x));

        return test.value ? eval(expr.y): eval(expr.z);
    }
}
