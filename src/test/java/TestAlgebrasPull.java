import org.junit.Before;
import org.junit.Test;
import streams.*;

import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;

public class TestAlgebrasPull {

    public Long[] v, v_inner;

    @Before
    public void setUp() {
        v = IntStream.range(0, 10).mapToObj(i -> new Long(i % 5)).toArray(Long[]::new);
        v_inner = IntStream.range(0, 5).mapToObj(i -> new Long(i % 5)).toArray(Long[]::new);
    }

    @Test
    public void testFilterCountPush(){

        PushAlg alg = new PushAlg();

        long actual = alg.count(alg.filter(x -> (long) x % 2L == 0, alg.source(v)));

        long expected = java.util.stream.Stream.of(v)
                .filter(x -> x % 2L == 0L)
                .count();

        assertEquals(expected, actual);
    }

    @Test
    public void testFilterCountPull(){

        PullAlg alg = new PullAlg();

        long actual = alg.count(alg.filter(x -> x % 2L == 0, alg.source(v)));

        long expected = java.util.stream.Stream.of(v)
                .filter(x -> x % 2L == 0L)
                .count();

        assertEquals(expected, actual);
    }

    @Test
    public void testMapPull(){
        PullAlg alg = new PullAlg();

        long actual = alg.count(alg.map(x -> x ^ 2, alg.source(v)));

        long expected = java.util.stream.Stream.of(v)
                .map(x -> x^2)
                .count();

        assertEquals(expected, actual);
    }

    @Test
    public void testFlatMapPull(){
        PullAlg alg = new PullAlg();

        long actual = alg.count(alg.flatMap(x -> {
            PullAlg inner = new PullAlg();
            return inner.map(y -> (long) x * (long) y, alg.source(v_inner));
        }, alg.source(v)));

        long expected = java.util.stream.Stream.of(v)
                .flatMap(x -> java.util.stream.Stream.of(v_inner).map(y -> x * y))
                .count();

        assertEquals(expected, actual);
    }

    @Test
    public void testReducePull(){
        PullAlg alg = new PullAlg();

        long actual = alg.reduce(0L, Long::sum, alg.map(x -> x^2, alg.source(v)));

        long expected = java.util.stream.Stream.of(v)
                .map(x -> x^2)
                .reduce(0L, Long::sum);

        assertEquals(expected, actual);
    }
}