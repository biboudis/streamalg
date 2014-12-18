package algebras;

import base.BaseTest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import streams.algebras.ExecIterateStreamAlg;
import streams.factories.ExecPullFactory;
import streams.factories.ExecPullWithIterateFactory;
import streams.factories.PullFactory;
import streams.higher.App;
import streams.higher.Id;
import streams.higher.Pull;

import java.util.Iterator;
import java.util.concurrent.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Authors:
 * Aggelos Biboudis (@biboudis)
 * Nick Palladinos (@NickPalladinos)
 */
public class TestAlgebrasFlatMapAlgebras extends BaseTest {

    public Long[] v_outer, v_inner;

    @Before
    public void setUp() {
        v_outer = IntStream.range(0, 5).mapToObj(i -> (long) (i % 5)).toArray(Long[]::new);
        v_inner = IntStream.range(0, 5).mapToObj(i -> (long) (i % 5)).toArray(Long[]::new);
    }

    @Test
    public void testPull() {
        PullFactory algebra = new PullFactory();

        App<Pull.t, Long> map = algebra.map(y -> {
            System.out.println("inner: " + y);
            return (long) 10 * y;
        }, algebra.source(v_inner));

        App<Pull.t, Long> flatMap = algebra.flatMap(y -> map, algebra.source(v_outer));

        Pull<Long> prj = Pull.prj(flatMap);

        prj.hasNext();

        System.out.println(prj.next());

        Assert.assertEquals(
                "inner: 0\n" +
                        "0\n",
                outContent.toString());
    }

    @Test
    public void testJava8StreamsPushWithPull() {
        Iterator<Long> iterator = Stream.of(v_outer)
                .flatMap(x -> {
                    return Stream.of(v_inner).map(y -> {
                        System.out.println("inner: " + y);
                        return x * y;
                    });
                }).iterator();

        iterator.hasNext();

        System.out.println(iterator.next());

        Assert.assertEquals(
                "inner: 0\n" +
                        "inner: 1\n" +
                        "inner: 2\n" +
                        "inner: 3\n" +
                        "inner: 4\n" +
                        "0\n",
                outContent.toString());
    }

    @Test(expected = TimeoutException.class)
    public void testJava8StreamsPushWithInfinite() throws ExecutionException, InterruptedException, TimeoutException {
        ExecutorService service = Executors.newSingleThreadExecutor();
        Future<Long> result = service.submit(() -> {
            Stream<Long> longStream = Stream
                    .iterate(0L, i -> i + 2);

            Iterator<Long> iterator = Stream.of(v_outer)
                    .flatMap(x -> longStream.map(y -> x * y))
                    .iterator();

            iterator.hasNext();

            return iterator.next();
        });

        result.get(1000, TimeUnit.MILLISECONDS);
    }

    @Test
    public void testPullAlgebraWithInfinite() {
        ExecIterateStreamAlg<Id.t, Pull.t> algebra = new ExecPullWithIterateFactory<>(new ExecPullFactory());

        App<Pull.t, Long> flatMap = algebra.flatMap(x -> algebra.map(y -> {
            System.out.println("inner: " + y);
            return x * y;
        }, algebra.iterate(0L, i -> i + 2)), algebra.source(v_outer));

        Pull<Long> prj = Pull.prj(flatMap);

        prj.hasNext();

        System.out.println(prj.next());

        Assert.assertEquals(
                "inner: 0\n" +
                        "0\n",
                outContent.toString());
    }
}
