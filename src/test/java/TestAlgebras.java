import org.junit.Before;
import org.junit.Test;
import streams.PullAlg;
import streams.PushAlg;
import streams.PushWithTakeAlg;

import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;

public class TestAlgebras {

    public Long[] v, v_inner;

    @Before
    public void setUp() {
        v = IntStream.range(0, 100).mapToObj(i -> new Long(i % 5)).toArray(Long[]::new);
        v_inner = IntStream.range(0, 10).mapToObj(i -> new Long(i % 5)).toArray(Long[]::new);
    }

    @Test
    public void testFilterCountPush(){

        PushAlg alg = new PushAlg();

        long actual = alg.length(alg.filter(x -> (long)x % 2L == 0, alg.source(v)));

        long expected = java.util.stream.Stream.of(v)
                .filter(x -> x % 2L == 0L)
                .count();

        assertEquals(expected, actual);
    }

    @Test
    public void testFilterCountPull(){

        PullAlg alg = new PullAlg();

        long actual = alg.length(alg.filter(x -> x % 2L == 0, alg.source(v)));

        long expected = java.util.stream.Stream.of(v)
                .filter(x -> x % 2L == 0L)
                .count();

        assertEquals(expected, actual);
    }

    @Test
    public void testMapPush(){
        PushAlg alg = new PushAlg();

        long actual = alg.length(alg.map(x -> (long) x ^ 2, alg.source(v)));

        long expected = java.util.stream.Stream.of(v)
                .map(x -> x^2)
                .count();

        assertEquals(expected, actual);
    }

    @Test
    public void testMapPull(){
        PullAlg alg = new PullAlg();

        long actual = alg.length(alg.map(x -> x ^ 2, alg.source(v)));

        long expected = java.util.stream.Stream.of(v)
                .map(x -> x^2)
                .count();

        assertEquals(expected, actual);
    }

    @Test
    public void testFlatMapPush(){
        PushAlg alg = new PushAlg();

        long actual = alg.length(alg.flatMap(x -> {
            PushAlg inner = new PushAlg();
            return inner.map(y -> (long)x * (long) y, alg.source(v_inner));
        }, alg.source(v)));

        long expected = java.util.stream.Stream.of(v)
                .flatMap(x -> java.util.stream.Stream.of(v_inner).map(y -> x * y))
                .count();

        assertEquals(expected, actual);
    }

    @Test
    public void testFlatMapPull(){
        PullAlg alg = new PullAlg();

        long actual = alg.length(alg.flatMap(x -> {
            PullAlg inner = new PullAlg();
            return inner.map(y -> (long) x * (long) y, alg.source(v_inner));
        }, alg.source(v)));

        long expected = java.util.stream.Stream.of(v)
                .flatMap(x -> java.util.stream.Stream.of(v_inner).map(y -> x * y))
                .count();

        assertEquals(expected, actual);
    }

    @Test
    public void testTakePush(){
        PushWithTakeAlg alg = new PushWithTakeAlg();

        long actual = alg.length(alg.take(5, alg.source(v)));

        long expected = java.util.stream.Stream.of(v)
                .limit(5)
                .count();

        assertEquals(expected, actual);
    }

//    @Test
//    public void testLog(){
//        streams.Streams.of(v)
//                .map(x -> x + 1)
//                .filter(x -> x % 2L==0)
//                .flatMap(x -> streams.Streams.of(v).map(y -> x * y).log())
//                .log()
//                .count();
//
//        assert true;
//    }
}