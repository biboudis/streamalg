package streams;

import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Authors:
 *      Aggelos Biboudis (@biboudis)
 *      Nick Palladinos (@NickPalladinos)
 */
public interface StreamAlg<C> {
    // Object Algebra
    <T> App<C, T> source(T[] array);
    <T, R> App<C, R> map(Function<T, R> f, App<C, T> app);
    <T, R> App<C, R> flatMap(Function<T, App<C, R>> f, App<C, T> app);
    <T> App<C, T> filter(Predicate<T> f, App<C, T> app);

    // Weakening Object Algebra to Abstract Factory
    <T> long count(App<C, T> app);
    <T> T reduce(T identity, BinaryOperator<T> accumulator, App<C, T> app);

    // Default Methods
    static  <F extends StreamAlg<C>, C, T>
    Function<F, App<C,T>> Source(T[] array) {
        return fact -> fact.source(array);
    }

    static <F extends StreamAlg<C>, C, T, R>
    Function<F, App<C, R>> Map(Function<F, App<C, T>> self,
                               Function<T, R> f) {
        return fact -> fact.<T,R>map(f, self.apply(fact));
    }

    static <F extends StreamAlg<C>, C, T, R>
    Function<F, App<C, R>> FlatMap(Function<F, App<C, T>> self,
                                   Function<T, Function<F , App<C, R>>> f) {
        return fact -> fact.<T,R>flatMap(x -> f.apply(x).apply(fact), self.apply(fact));
    }

    static <F extends StreamAlg<C>, C, T>
    Function<F, App<C, T>> Filter(Function<F, App<C, T>> self,
                                   Predicate<T> predicate) {
        return fact -> fact.<T>filter(predicate, self.apply(fact));
    }

    static <F extends StreamAlg<C>, C, T>
    Function<F, Long> Count(Function<F, App<C, T>> self) {
        return fact -> fact.count(self.apply(fact));
    }

    static <F extends StreamAlg<C>, C, T>
    Function<F, T> Reduce(Function<F, App<C, T>> self,
                          T identity, BinaryOperator<T> accumulator) {
        return fact -> fact.<T>reduce(identity, accumulator, self.apply(fact));
    }
}

