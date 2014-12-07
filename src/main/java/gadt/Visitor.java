package gadt;

/**
 * Created by bibou on 12/5/14.
 */
interface Visitor<C> {
    App<C, Integer> caseIntLit (IntLit expr);
    App<C, Boolean> caseBoolLit (BoolLit expr);
    App<C, Integer> casePlus (Plus expr);
    <Y, R> App<C, R> caseIf (If<Y> expr);
}
