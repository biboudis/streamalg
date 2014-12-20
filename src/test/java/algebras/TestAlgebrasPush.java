package algebras;

import org.junit.Before;
import org.junit.Test;
import streams.factories.ExecPushFactory;
import streams.higher.Id;

import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;

public class TestAlgebrasPush {

    public Long[] v, v_small;

    @Before
    public void setUp() {
        v = IntStream.range(0, 15).mapToObj(Long::new).toArray(Long[]::new);
        v_small = IntStream.range(0, 5).mapToObj(Long::new).toArray(Long[]::new);
    }

    @Test
    public void testFilterCountPush() {

        ExecPushFactory alg = new ExecPushFactory();

        Long actual = Id.prj(alg.count(alg.filter(x -> x % 2L == 0, alg.source(v)))).value;

        Long expected = java.util.stream.Stream.of(v)
                .filter(x -> x % 2L == 0L)
                .count();

        assertEquals(expected, actual);
    }

    @Test
    public void testMapCountPush() {
        ExecPushFactory alg = new ExecPushFactory();

        Long actual = Id.prj(alg.count(alg.map(x -> x ^ 2, alg.source(v)))).value;

        Long expected = java.util.stream.Stream.of(v)
                .map(x -> x ^ 2)
                .count();

        assertEquals(expected, actual);
    }

    @Test
    public void testFlatMapCountPush() {
        ExecPushFactory alg = new ExecPushFactory();

        Long actual = Id.prj(alg.count(alg.flatMap(x -> {
            return alg.map(y -> x * y, alg.source(v_small));
        }, alg.source(v)))).value;

        Long expected = java.util.stream.Stream.of(v)
                .flatMap(x -> java.util.stream.Stream.of(v_small).map(y -> x * y))
                .count();

        assertEquals(expected, actual);
    }

    @Test
    public void testReducePush() {
        ExecPushFactory alg = new ExecPushFactory();

        Long actual = Id.prj(alg.reduce(0L, Long::sum, alg.map(x -> x ^ 2, alg.source(v)))).value;

        Long expected = java.util.stream.Stream.of(v)
                .map(x -> x ^ 2)
                .reduce(0L, Long::sum);

        assertEquals(expected, actual);
    }

}