package gadt.evaluator;

import gadt.App;

/**
 * Authors:
 * Aggelos Biboudis (@biboudis)
 * Nick Palladinos (@NickPalladinos)
 */
// This is the universal visitor of http://www.jot.fm/issues/issue_2008_06/article2.pdf#page=8&zoom=180,-4,376
// In Haskell with GADTs this could have been:
/*
data Expr a where
      IntLit      :: Int  -> Expr Int
      BoolLit     :: Bool -> Expr Bool
      Plus        :: Expr Int -> Expr Int -> Expr Int
      If          :: Expr Bool -> Expr Y -> Expr Y
*/

//interface Visitor<R<_>>
public interface Visitor<C> {
    /*R<Integer>*/ App<C, Integer> caseIntLit(IntLit expr);

    /*R<Boolean>*/ App<C, Boolean> caseBoolLit(BoolLit expr);

    /*R<Integer>*/ App<C, Integer> casePlus(Plus expr);

    /*<Y>R<Y>*/    <T> App<C, T> caseIf(If<T> expr);
}
