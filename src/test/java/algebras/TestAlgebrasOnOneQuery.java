package algebras;

import base.BaseTest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import streams.algebras.ExecStreamAlg;
import streams.factories.*;
import streams.higher.App;
import streams.higher.Future;
import streams.higher.Id;

import java.util.concurrent.ExecutionException;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;

/**
 * Authors:
 * Aggelos Biboudis (@biboudis)
 * Nick Palladinos (@NickPalladinos)
 */
public class TestAlgebrasOnOneQuery extends BaseTest {

    public Long[] v, v_small;
    Long expected;

    @Before
    public void setUp() {
        v = IntStream.range(0, 5).mapToObj(Long::new).toArray(Long[]::new);
        v_small = IntStream.range(0, 3).mapToObj(Long::new).toArray(Long[]::new);

        expected = java.util.stream.Stream.of(v)
                .flatMap(x -> java.util.stream.Stream.of(v_small).map(y -> x * y))
                .reduce(0L, Long::sum);
    }

    <E, C> App<E, Long> cartCountStream(ExecStreamAlg<E, C> alg) {
        return alg.reduce(0L, Long::sum, alg.flatMap(x -> alg.map(y -> x * y, alg.source(v_small)), alg.source(v)));
    }

    @Test
    public void testFlatMapCountPush() {
        Long actual = Id.prj(cartCountStream(new ExecPushFactory())).value;

        assertEquals(expected, actual);
    }

    @Test
    public void testFlatMapCountPull() {
        Long actual = Id.prj(cartCountStream(new ExecPullFactory())).value;

        assertEquals(expected, actual);
    }

    @Test
    public void testFlatMapCountFusedPull() {
        Long actual = Id.prj(cartCountStream(new ExecFusedPullFactory())).value;

        assertEquals(expected, actual);
    }

    @Test
    public void testFlatMapCountFuturePush() throws ExecutionException, InterruptedException {
        Future<Long> actual = Future.prj(cartCountStream(new ExecFutureFactory<>(new ExecPushFactory())));

        assertEquals(expected, actual.get());
    }

    @Test
    public void testFlatMapCountFuturePull() throws ExecutionException, InterruptedException {
        Future<Long> actual = Future.prj(cartCountStream(new ExecFutureFactory<>(new ExecPullFactory())));

        assertEquals(expected, actual.get());
    }

    @Test
    public void testFlatMapCountPushLog() throws ExecutionException, InterruptedException {
        Long actual = Id.prj(cartCountStream(new LogFactory<>(new ExecPushFactory()))).value;

        Assert.assertEquals(
                "flatMap : 0 -> 0\n" +
                        "map: 0 -> 0\n" +
                        "map: 1 -> 0\n" +
                        "map: 2 -> 0\n" +
                        "flatMap : 1 -> 1\n" +
                        "map: 0 -> 0\n" +
                        "map: 1 -> 1\n" +
                        "map: 2 -> 2\n" +
                        "flatMap : 2 -> 2\n" +
                        "map: 0 -> 0\n" +
                        "map: 1 -> 2\n" +
                        "map: 2 -> 4\n" +
                        "flatMap : 3 -> 3\n" +
                        "map: 0 -> 0\n" +
                        "map: 1 -> 3\n" +
                        "map: 2 -> 6\n" +
                        "flatMap : 4 -> 4\n" +
                        "map: 0 -> 0\n" +
                        "map: 1 -> 4\n" +
                        "map: 2 -> 8\n",
                outContent.toString());
        assertEquals(expected, actual);
    }

    @Test
    public void testFlatMapCountPullLog() throws ExecutionException, InterruptedException {
        Long actual = Id.prj(cartCountStream(new LogFactory<>(new ExecPushFactory()))).value;

        Assert.assertEquals(
                "flatMap : 0 -> 0\n" +
                        "map: 0 -> 0\n" +
                        "map: 1 -> 0\n" +
                        "map: 2 -> 0\n" +
                        "flatMap : 1 -> 1\n" +
                        "map: 0 -> 0\n" +
                        "map: 1 -> 1\n" +
                        "map: 2 -> 2\n" +
                        "flatMap : 2 -> 2\n" +
                        "map: 0 -> 0\n" +
                        "map: 1 -> 2\n" +
                        "map: 2 -> 4\n" +
                        "flatMap : 3 -> 3\n" +
                        "map: 0 -> 0\n" +
                        "map: 1 -> 3\n" +
                        "map: 2 -> 6\n" +
                        "flatMap : 4 -> 4\n" +
                        "map: 0 -> 0\n" +
                        "map: 1 -> 4\n" +
                        "map: 2 -> 8\n",
                outContent.toString());
        assertEquals(expected, actual);
    }
}
