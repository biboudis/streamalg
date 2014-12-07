package gadt;

/**
 * Created by bibou on 12/5/14.
 */
public interface Visitor<C> {
    App<C, NumberHigh> caseIntLit (IntLit expr);
    App<C, BooleanHigh> caseBoolLit (BoolLit expr);
    <A> App<C, A> casePlus (Plus expr);
    <Y, A> App<C, A> caseIf (If<Y> expr);
}
