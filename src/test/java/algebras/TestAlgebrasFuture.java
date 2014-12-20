package algebras;

import org.junit.Before;
import org.junit.Test;
import streams.algebras.ExecStreamAlg;
import streams.factories.ExecFutureFactory;
import streams.factories.ExecPullFactory;
import streams.higher.Future;
import streams.higher.Pull;

import java.util.concurrent.ExecutionException;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;

/**
 * Authors:
 * Aggelos Biboudis (@biboudis)
 * Nick Palladinos (@NickPalladinos)
 */
public class TestAlgebrasFuture {

    public Long[] v;

    @Before
    public void setUp() {
        v = IntStream.range(0, 15).mapToObj(Long::new).toArray(Long[]::new);
    }

    @Test
    public void testFilterCountPull() throws ExecutionException, InterruptedException {

        ExecStreamAlg<Future.t, Pull.t> alg = new ExecFutureFactory(new ExecPullFactory());

        Future<Long> actual = Future.prj(alg.count(alg.filter(x -> x % 2L == 0, alg.source(v))));

        Long expected = java.util.stream.Stream.of(v)
                .filter(x -> x % 2L == 0L)
                .count();

        assertEquals(expected, actual.get());
    }

    @Test
    public void testMapCountPull() throws ExecutionException, InterruptedException {
        ExecStreamAlg<Future.t, Pull.t> alg = new ExecFutureFactory(new ExecPullFactory());

        Future<Long> actual = Future.prj(alg.count(alg.map(x -> x ^ 2, alg.source(v))));

        Long expected = java.util.stream.Stream.of(v)
                .map(x -> x ^ 2)
                .count();

        assertEquals(expected, actual.get());
    }
}
