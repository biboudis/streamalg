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
    App<C, Integer> caseIntLit (IntLit expr);
    App<C, Boolean> caseBoolLit (BoolLit expr);
    App<C, Integer> casePlus (Plus expr);
    <T> App<C, T> caseIf (If<T> expr);
}
