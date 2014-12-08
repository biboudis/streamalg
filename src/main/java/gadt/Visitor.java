package gadt;

/**
 * Created by bibou on 12/5/14.
 */
public interface Visitor<C> {
    <A> App<C, A> caseIntLit (IntLit expr);
    <A> App<C, A> caseBoolLit (BoolLit expr);
    <A> App<C, A> casePlus (Plus expr);
    <Y, A> App<C, A> caseIf (If<Y> expr);
}
