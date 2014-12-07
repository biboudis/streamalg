package gadt;

/**
 * Created by bibou on 12/5/14.
 */
public class EvalVisitor<R> implements Visitor<Expr.t> {
    
    public R eval(Expr<R> expr) {
        return null; // expr.accept(this);
    }

    @Override
    public App<Expr.t, Integer> caseIntLit(IntLit expr) {
        return expr;
    }

    @Override
    public App<Expr.t, Boolean> caseBoolLit(BoolLit expr) {
        return expr;
    }

    @Override
    public App<Expr.t, Integer> casePlus(Plus expr) {
        return null; // Expr.prj(eval(expr.right)) + Expr.prj(eval(expr.right));
    }

    @Override
    public <Y, R> App<Expr.t, R> caseIf(If<Y> expr) {
        return null;
    }
}
