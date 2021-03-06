package algebras;

import org.junit.Before;
import org.junit.Test;
import streams.algebras.ExecIterateStreamAlg;
import streams.factories.ExecPullFactory;
import streams.factories.ExecPullWithIterateFactory;
import streams.higher.Id;
import streams.higher.Pull;

import java.util.Iterator;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestAlgebrasPull {

    public Long[] v, v_small;

    @Before
    public void setUp() {
        v = IntStream.range(0, 15).mapToObj(Long::new).toArray(Long[]::new);
        v_small = IntStream.range(0, 5).mapToObj(Long::new).toArray(Long[]::new);
    }

    @Test
    public void testFilterCountPull() {

        ExecPullFactory alg = new ExecPullFactory();

        Long actual = Id.prj(alg.count(alg.filter(x -> x % 2L == 0, alg.source(v)))).value;

        Long expected = java.util.stream.Stream.of(v)
                .filter(x -> x % 2L == 0L)
                .count();

        assertEquals(expected, actual);
    }

    @Test
    public void testMapCountPull() {
        ExecPullFactory alg = new ExecPullFactory();

        Long actual = Id.prj(alg.count(alg.map(x -> x ^ 2, alg.source(v)))).value;

        Long expected = java.util.stream.Stream.of(v)
                .map(x -> x ^ 2)
                .count();

        assertEquals(expected, actual);
    }

    @Test
    public void testFlatMapCountPull() {
        ExecPullFactory alg = new ExecPullFactory();

        Long actual = Id.prj(alg.count(alg.flatMap(x -> {
            return alg.map(y -> x * y, alg.source(v_small));
        }, alg.source(v)))).value;

        Long expected = java.util.stream.Stream.of(v)
                .flatMap(x -> java.util.stream.Stream.of(v_small).map(y -> x * y))
                .count();

        assertEquals(expected, actual);
    }

    @Test
    public void testReducePull() {
        ExecPullFactory alg = new ExecPullFactory();

        Long actual = Id.prj(alg.reduce(0L, Long::sum, alg.map(x -> x ^ 2, alg.source(v)))).value;

        Long expected = java.util.stream.Stream.of(v)
                .map(x -> x ^ 2)
                .reduce(0L, Long::sum);

        assertEquals(expected, actual);
    }

    @Test
    public void testIteratePull() {

        ExecIterateStreamAlg<Id.t, Pull.t> algebra = new ExecPullWithIterateFactory<>(new ExecPullFactory());

        Pull<Long> result = Pull.prj(algebra.iterate(0L, i -> i + 2));

        Iterator<Long> expected = Stream.<Long>iterate(0L, i -> i + 2).iterator();

        boolean assertFlag = false;
        int iterations = 100;
        while (result.hasNext() && expected.hasNext() && iterations-- > 0) {
            assertFlag = result.next().equals(expected.next());
        }

        assertTrue(assertFlag);
    }
}