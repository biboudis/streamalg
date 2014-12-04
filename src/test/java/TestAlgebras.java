import com.google.common.collect.Iterators;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;

public class TestAlgebras {

    public Long[] array;

    @Before
    public void setUp() {
        array = IntStream.range(0, 100).mapToObj(i -> new Long(i % 5)).toArray(Long[]::new);
    }

    @Test
    public void testFilterCountPush(){

        PushAlg alg = new PushAlg();

        long actual = alg.length(alg.filter(x -> (long)x % 2L == 0, alg.source(array)));

        long expected = java.util.stream.Stream.of(array)
                .filter(x -> x % 2L == 0L)
                .count();

        assertEquals(expected, actual);
    }

    @Test
    public void testFilterCountPull(){

        PullAlg alg = new PullAlg();

        long actual = alg.length(alg.filter(x -> x % 2L == 0, alg.source(array)));

        long expected = java.util.stream.Stream.of(array)
                .filter(x -> x % 2L == 0L)
                .count();

        assertEquals(expected, actual);
    }

    @Test
    public void testMapPush(){
        PushAlg alg = new PushAlg();

        long actual = alg.length(alg.map(x -> (long) x ^ 2, alg.source(array)));

        long expected = java.util.stream.Stream.of(array)
                .map(x -> x^2)
                .count();

        assertEquals(expected, actual);
    }

    @Test
    public void testMapPull(){
        PullAlg alg = new PullAlg();

        long actual = alg.length(alg.map(x -> x ^ 2, alg.source(array)));

        long expected = java.util.stream.Stream.of(array)
                .map(x -> x^2)
                .count();

        assertEquals(expected, actual);
    }

    @Test
    public void testFlatMapPush(){
        PushAlg alg = new PushAlg();

        long actual = alg.length(alg.flatMap(x -> {
            PushAlg inner = new PushAlg();
            return inner.map(y -> (long)x * (long) y, alg.source(array));
        }, alg.source(array)));

        long expected = java.util.stream.Stream.of(array)
                .flatMap(x -> java.util.stream.Stream.of(array).map(y -> x * y))
                .count();

        assertEquals(expected, actual);
    }

    @Test
    public void testFlatMapPull(){
        PullAlg alg = new PullAlg();

        long actual = alg.length(alg.flatMap(x -> {
            PullAlg inner = new PullAlg();
            return inner.map(y -> (long) x * (long) y, alg.source(array));
        }, alg.source(array)));

        long expected = java.util.stream.Stream.of(array)
                .flatMap(x -> java.util.stream.Stream.of(array).map(y -> x * y))
                .count();

        assertEquals(expected, actual);
    }

//    @Test
//    public void testLog(){
//        Streams.of(array)
//                .map(x -> x + 1)
//                .filter(x -> x % 2L==0)
//                .flatMap(x -> Streams.of(array).map(y -> x * y).log())
//                .log()
//                .count();
//
//        assert true;
//    }
}