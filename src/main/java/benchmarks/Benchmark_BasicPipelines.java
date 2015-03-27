package benchmarks;

import org.openjdk.jmh.annotations.*;
import streams.factories.ExecPullFactory;
import streams.factories.ExecPushFactory;
import streams.higher.Id;

import java.util.stream.Stream;

@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
public class Benchmark_BasicPipelines {

    // For map, count, operations
    private static final int N = Integer.getInteger("benchmark.N", 1000);

    // When we need to use different sizes
    private static final int N_small = Integer.getInteger("benchmark.N_small", 10);

    private Long[] v, v_small;

    @Setup
    public void setUp() {
        v = Helper.fillArray(N);
        v_small = Helper.fillArray(N_small);
    }

    // Baseline Benchmarks
    @Benchmark
    public long reduce_Baseline() {
        long value = 0L;
        for (int i = 0; i < v.length; i++) {
            value += v[i];
        }
        return value;
    }

    @Benchmark
    public long filter_reduce_Baseline() {
        long value = 0L;
        for (int i = 0; i < v.length; i++) {
            if (v[i] % 2 == 0)
                value += v[i];
        }
        return value;
    }

    @Benchmark
    public long filter_map_reduce_Baseline() {
        long value = 0L;
        for (int i = 0; i < v.length; i++) {
            if (v[i] % 2 == 0)
                value += v[i] * v[i];
        }
        return value;
    }

    @Benchmark
    public long cart_reduce_Baseline() {
        long value = 0L;
        for (int d = 0; d < v.length; d++) {
            for (int dp = 0; dp < v_small.length; dp++) {
                value += v[d] * v_small[dp];
            }
        }
        return value;
    }

    // Java 8 Benchmarks (fundamentally Push)
    @Benchmark
    public Long reduce_Java8Streams() {
        Long value = java.util.stream.Stream.of(v)
                .reduce(0L, Long::sum);
        return value;
    }

    @Benchmark
    public Long filter_reduce_Java8Streams() {
        Long value = java.util.stream.Stream.of(v)
                .filter(x -> x % 2L == 0L)
                .reduce(0L, Long::sum);
        return value;
    }

    @Benchmark
    public Long filter_map_reduce_Java8Streams() {
        Long value = java.util.stream.Stream.of(v)
                .filter(x -> x % 2L == 0L)
                .map(d -> d * d)
                .reduce(0L, Long::sum);
        return value;
    }

    @Benchmark
    public Long cart_reduce_Java8Streams() {
        Long value = java.util.stream.Stream.of(v)
                .flatMap(d -> Stream.of(v_small).map(dP -> dP * d))
                .reduce(0L, Long::sum);
        return value;
    }

    // Push Factory Benchmarks
    @Benchmark
    public Long reduce_AlgebrasPush() {
        ExecPushFactory alg = new ExecPushFactory();
        Long value = Id.prj(
                alg.reduce(0L, Long::sum,
                        alg.source(v))).value;
        return value;
    }

    @Benchmark
    public Long filter_reduce_AlgebrasPush() {
        ExecPushFactory alg = new ExecPushFactory();
        Long value = Id.prj(
                alg.reduce(0L, Long::sum,
                        alg.filter(x -> x % 2L == 0L,
                                alg.source(v)))).value;
        return value;
    }

    @Benchmark
    public Long filter_map_reduce_AlgebrasPush() {
        ExecPushFactory alg = new ExecPushFactory();
        Long value = Id.prj(
                alg.reduce(0L, Long::sum,
                        alg.map(d -> d * d,
                                alg.filter(x -> x % 2L == 0L,
                                        alg.source(v))))).value;
        return value;
    }

    @Benchmark
    public Long cart_reduce_AlgebrasPush() {
        ExecPushFactory alg = new ExecPushFactory();
        Long value = Id.prj(
                alg.reduce(0L, Long::sum,
                        alg.flatMap(x ->
                                        alg.map(y -> x * y,
                                                alg.source(v_small)),
                                alg.source(v)))).value;
        return value;
    }

    // Pull Factory Benchmarks
    @Benchmark
    public Long reduce_AlgebrasPull() {
        ExecPullFactory alg = new ExecPullFactory();
        Long value = Id.prj(
                alg.reduce(0L, Long::sum,
                        alg.source(v))).value;
        return value;
    }

    @Benchmark
    public Long filter_reduce_AlgebrasPull() {
        ExecPullFactory alg = new ExecPullFactory();
        Long value = Id.prj(
                alg.reduce(0L, Long::sum,
                        alg.filter(x -> x % 2L == 0L,
                                alg.source(v)))).value;
        return value;
    }

    @Benchmark
    public Long filter_map_reduce_AlgebrasPull() {
        ExecPullFactory alg = new ExecPullFactory();
        Long value = Id.prj(
                alg.reduce(0L, Long::sum,
                        alg.map(d -> d * d,
                                alg.filter(x -> x % 2L == 0L,
                                        alg.source(v))))).value;
        return value;
    }

    @Benchmark
    public Long cart_reduce_AlgebrasPull() {
        ExecPullFactory alg = new ExecPullFactory();
        Long value = Id.prj(
                alg.reduce(0L, Long::sum,
                        alg.flatMap(x ->
                                        alg.map(y -> x * y,
                                                alg.source(v_small)),
                                alg.source(v)))).value;
        return value;
    }
}
