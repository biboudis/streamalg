import org.junit.Before;
import org.junit.Test;
import streams.Id;
import streams.PushFactory;
import streams.PushWithTakeFactory;

import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;

public class TestAlgebrasPush {

    public Long[] v, v_inner;

    @Before
    public void setUp() {
        v = IntStream.range(0, 10).mapToObj(i -> (long) (i % 5)).toArray(Long[]::new);
        v_inner = IntStream.range(0, 5).mapToObj(i -> (long) (i % 5)).toArray(Long[]::new);
    }

    @Test
    public void testFilterCountPush(){

        PushFactory alg = new PushFactory();

        Long actual = Id.prj(alg.count(alg.filter(x -> x % 2L == 0, alg.source(v)))).value;

        Long expected = java.util.stream.Stream.of(v)
                .filter(x -> x % 2L == 0L)
                .count();

        assertEquals(expected, actual);
    }

    @Test
    public void testMapPush(){
        PushFactory alg = new PushFactory();

        Long actual = Id.prj(alg.count(alg.map(x -> x ^ 2, alg.source(v)))).value;

        Long expected = java.util.stream.Stream.of(v)
                .map(x -> x^2)
                .count();

        assertEquals(expected, actual);
    }

    @Test
    public void testFlatMapPush(){
        PushFactory alg = new PushFactory();

        Long actual = Id.prj(alg.count(alg.flatMap(x -> {
            PushFactory inner = new PushFactory();
            return inner.map(y -> x * y, alg.source(v_inner));
        }, alg.source(v)))).value;

        Long expected = java.util.stream.Stream.of(v)
                .flatMap(x -> java.util.stream.Stream.of(v_inner).map(y -> x * y))
                .count();

        assertEquals(expected, actual);
    }

    @Test
    public void testReducePush(){
        PushFactory alg = new PushFactory();

        Long actual = Id.prj(alg.reduce(0L, Long::sum, alg.map(x -> x ^ 2, alg.source(v)))).value;

        Long expected = java.util.stream.Stream.of(v)
                .map(x -> x^2)
                .reduce(0L, Long::sum);

        assertEquals(expected, actual);
    }

    @Test
    public void testTakePush(){
        PushWithTakeFactory alg = new PushWithTakeFactory();

        Long actual =  Id.prj(alg.count(alg.take(5, alg.source(v)))).value;

        Long expected = java.util.stream.Stream.of(v)
                .limit(5)
                .count();

        assertEquals(expected, actual);
    }
}