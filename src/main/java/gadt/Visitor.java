package gadt;

/**
 * Created by bibou on 12/5/14.
 */

// In Haskell with GADTs

//  data Expr a where
//      IntLit      :: Int  -> Expr Int
//      BoolLit     :: Bool -> Expr Bool
//      Plus        :: Expr Int -> Expr Int -> Expr Int
//      If          :: Expr Bool -> Expr Y -> Expr Y

public interface Visitor<C> {
    <A> App<C, A> caseIntLit (IntLit expr);
    <A> App<C, A> caseBoolLit (BoolLit expr);
    <A> App<C, A> casePlus (Plus expr);
    <Y, A> App<C, A> caseIf (If<Y> expr);
}
