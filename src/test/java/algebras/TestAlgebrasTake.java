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

    public Long[] v, v_small;

    @Before
    public void setUp() {
        v = IntStream.range(0, 15).mapToObj(Long::new).toArray(Long[]::new);
        v_small = IntStream.range(0, 5).mapToObj(Long::new).toArray(Long[]::new);
    }

    @Test
    public void testTakePull() {
        ExecTakeStreamAlg<Id.t, Pull.t> alg = new ExecPullWithTakeFactory<>(new ExecPullFactory());

        Long actual = Id.prj(alg.count(alg.take(5, alg.source(v)))).value;

        Long expected = Stream.of(v)
                .limit(5)
                .count();

        assertEquals(expected, actual);
    }

    @Test
    public void testTakePush() {
        ExecTakeStreamAlg<Id.t, Push.t> alg = new ExecPushWithTakeFactory<>(new ExecPushFactory());

        Long actual = Id.prj(alg.count(alg.take(5, alg.source(v)))).value;

        Long expected = java.util.stream.Stream.of(v)
                .limit(5)
                .count();

        assertEquals(expected, actual);
    }

    @Test
    public void testTakeCartPull() {
        ExecTakeStreamAlg<Id.t, Pull.t> alg = new ExecPullWithTakeFactory<>(new ExecPullFactory());

        Long actual = Id.prj(
                alg.reduce(0L, Long::sum,
                        alg.take(15,
                                alg.flatMap(x -> alg.map(y -> y * x, alg.source(v_small)), alg.source(v))))).value;


        Long expected = Stream
                .of(v)
                .flatMap(x -> Stream.of(v_small).map(y -> y * x))
                .limit(15)
                .reduce(0L, Long::sum);

        assertEquals(expected, actual);
    }

    @Test
    public void testTakeCartPush() {
        ExecTakeStreamAlg<Id.t, Push.t> alg = new ExecPushWithTakeFactory<>(new ExecPushFactory());

        Long actual = Id.prj(
                alg.reduce(0L, Long::sum,
                        alg.take(15,
                                alg.flatMap(x -> alg.map(y -> y * x, alg.source(v_small)), alg.source(v))))).value;

        Long expected = Stream
                .of(v) // 15
                .flatMap(x -> Stream
                        .of(v_small) // 5
                        .map(y -> {
                            //System.out.println("y: " + y);
                            return y;
                        }))
                .limit(15)
                .reduce(0L, Long::sum);

        assertEquals(expected, actual);
    }
}