package benchmarks;

import org.openjdk.jmh.annotations.*;
import streams.algebras.ExecTakeStreamAlg;
import streams.factories.*;
import streams.higher.Id;
import streams.higher.Pull;
import streams.higher.Push;

import java.util.stream.Stream;

@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
public class Benchmark_TakePipelines {

    // For map, count, operations
    private static final int N = Integer.getInteger("benchmark.N", 1000);

    // When we need to use different sizes
    private static final int N_small = Integer.getInteger("benchmark.N_small", 10);
    private static final int N_limit = Integer.getInteger("benchmark.N_limit", 250);
    private Long[] v, v_small;

    @Setup
    public void setUp() {
        v = Helper.fillArray(N);
        v_small = Helper.fillArray(N_small);
    }

    // Java 8 Benchmarks (Push)
    @Benchmark
    public Long limit_count_Java8Streams() {

        Long value = Stream
                .of(v_small)
                .flatMap(x -> Stream.of(v).map(y -> y * x))
                .limit(N_limit)
                .count();

        return value;
    }

    // Java 8 Benchmarks (Pull)
    @Benchmark
    public Long limit_count_iterate_Java8Streams() {
        RefCell<Long> acc = new RefCell<>(0L);

        Stream.of(v_small)
                .flatMap(x -> Stream.of(v).map(y -> y * x))
                .limit(N_limit)
                .iterator()
                .forEachRemaining(i -> acc.value++);

        return acc.value;
    }

    // Pull Factory Benchmarks
    @Benchmark
    public Long limit_count_AlgebrasPull() {
        ExecTakeStreamAlg<Id.t, Pull.t> alg = new ExecPullWithTakeFactory<>(new ExecPullFactory());

        Long value = Id.prj(
                alg.count(
                        alg.take(N_limit,
                                alg.flatMap(x -> alg.map(y -> x * y, alg.source(v)), alg.source(v_small))))).value;

        return value;
    }

    // Push Factory Benchmarks
    @Benchmark
    public Long limit_count_AlgebrasPush() {
        ExecTakeStreamAlg<Id.t, Push.t> alg = new ExecPushWithTakeFactory<>(new ExecPushFactory());

        Long value = Id.prj(
                alg.count(
                        alg.take(N_limit,
                                alg.flatMap(x -> alg.map(y -> x * y, alg.source(v)), alg.source(v_small))))).value;

        return value;
    }
}
