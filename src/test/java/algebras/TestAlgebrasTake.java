package algebras;

import org.junit.Before;
import org.junit.Test;
import streams.algebras.ExecTakeStreamAlg;
import streams.factories.ExecPullFactory;
import streams.factories.ExecPullWithTakeFactory;
import streams.factories.ExecPushFactory;
import streams.factories.ExecPushWithTakeFactory;
import streams.higher.Id;
import streams.higher.Pull;
import streams.higher.Push;

import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

public class TestAlgebrasTake {

    public int limit;
    public Long[] v, v_small;

    @Before
    public void setUp() {
        v = IntStream.range(0, 5).mapToObj(Long::new).toArray(Long[]::new);
        v_small = IntStream.range(0, 2).mapToObj(Long::new).toArray(Long[]::new);
        limit = 10;
    }

    @Test
    public void testTakePull() {
        ExecTakeStreamAlg<Id.t, Pull.t> alg = new ExecPullWithTakeFactory<>(new ExecPullFactory());

        Long actual = Id.prj(alg.count(alg.take(limit, alg.source(v)))).value;

        Long expected = Stream.of(v)
                .limit(limit)
                .count();

        assertEquals(expected, actual);
    }

    @Test
    public void testTakePush() {
        ExecTakeStreamAlg<Id.t, Push.t> alg = new ExecPushWithTakeFactory<>(new ExecPushFactory());

        Long actual = Id.prj(alg.count(alg.take(limit, alg.source(v)))).value;

        Long expected = java.util.stream.Stream.of(v)
                .limit(limit)
                .count();

        assertEquals(expected, actual);
    }

    @Test
    public void testTakeCartPull() {
        ExecTakeStreamAlg<Id.t, Pull.t> alg = new ExecPullWithTakeFactory<>(new ExecPullFactory());

        Long actual = Id.prj(
                alg.reduce(0L, Long::sum,
                        alg.take(limit,
                                alg.flatMap(x -> alg.map(y -> {
                                    System.out.println("y: " + y);
                                    return y * x;
                                }, alg.source(v)), alg.source(v_small))))).value;

        System.out.println("---");

        Long expected = Stream
                .of(v_small) // 15
                .flatMap(x -> Stream
                        .of(v) // 5
                        .map(y -> {
                            System.out.println("y: " + y);
                            return y * x;
                        }))
                .limit(limit)
                .reduce(0L, Long::sum);

        assertEquals(expected, actual);
    }

    @Test
    public void testTakeCartPush() {
        ExecTakeStreamAlg<Id.t, Push.t> alg = new ExecPushWithTakeFactory<>(new ExecPushFactory());

        Long actual = Id.prj(
                alg.reduce(0L, Long::sum,
                        alg.take(limit, alg.flatMap(x -> alg.map(y -> {
                            System.out.println("y: " + y);
                            return y * x;
                        }, alg.source(v)), alg.source(v_small))))).value;

        System.out.println("---");

        Long expected = Stream
                .of(v_small) // 5
                .flatMap(x -> Stream
                        .of(v) // 10
                        .map(y -> {
                            System.out.println("y: " + y);
                            return y * x;
                        }))
                .limit(limit)
                .reduce(0L, Long::sum);

        assertEquals(expected, actual);
    }
}