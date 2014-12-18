package benchmarks;

import org.openjdk.jmh.annotations.*;
import streams.factories.ExecFusedPullFactory;
import streams.factories.ExecPullFactory;
import streams.higher.Id;

@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
public class Benchmark_FusedPipelines {

    // For fusion operations
    private static final int F = Integer.getInteger("benchmark.F", 1000);

    public Long[] v_for_fused_map, v_for_fused_filter;

    @Setup
    public void setUp() {
        v_for_fused_map = Helper.fillArray(F);
        v_for_fused_filter = Helper.fillArray(F);
    }

    @Benchmark
    public Long filters_Algebras_NotFusedPull() {
        ExecPullFactory alg = new ExecPullFactory();

        Long value = Id.prj(alg.count(
                alg.filter(x -> x > 7,
                        alg.filter(x -> x > 6,
                                alg.filter(x -> x > 5,
                                        alg.filter(x -> x > 4,
                                                alg.filter(x -> x > 3,
                                                        alg.filter(x -> x > 2,
                                                                alg.filter(x -> x > 1,
                                                                        alg.filter(x -> x > 0, alg.source(v_for_fused_filter))))))))))).value;

        return value;
    }

    @Benchmark
    public Long filters_Algebras_FusedPull() {
        ExecFusedPullFactory alg = new ExecFusedPullFactory();

        Long value = Id.prj(alg.count(
                alg.filter(x -> x > 7,
                        alg.filter(x -> x > 6,
                                alg.filter(x -> x > 5,
                                        alg.filter(x -> x > 4,
                                                alg.filter(x -> x > 3,
                                                        alg.filter(x -> x > 2,
                                                                alg.filter(x -> x > 1,
                                                                        alg.filter(x -> x > 0, alg.source(v_for_fused_filter))))))))))).value;

        return value;
    }

    @Benchmark
    public Long maps_Algebras_NotFusedPull() {
        ExecPullFactory alg = new ExecPullFactory();

        Long value = Id.prj(alg.reduce(0L, Long::sum,
                alg.<Long, Long>map(x -> x + 1,
                        alg.<Long, Long>map(x -> x + 1,
                                alg.<Long, Long>map(x -> x + 1,
                                        alg.<Long, Long>map(x -> x + 1,
                                                alg.<Long, Long>map(x -> x + 1,
                                                        alg.<Long, Long>map(x -> x + 1,
                                                                alg.<Long, Long>map(x -> x + 1, alg.source(v_for_fused_map)))))))))).value;

        return value;
    }

    @Benchmark
    public Long maps_Algebras_FusedPull() {
        ExecFusedPullFactory alg = new ExecFusedPullFactory();

        Long value = Id.prj(alg.reduce(0L, Long::sum,
                alg.<Long, Long>map(x -> x + 1,
                        alg.<Long, Long>map(x -> x + 1,
                                alg.<Long, Long>map(x -> x + 1,
                                        alg.<Long, Long>map(x -> x + 1,
                                                alg.<Long, Long>map(x -> x + 1,
                                                        alg.<Long, Long>map(x -> x + 1,
                                                                alg.<Long, Long>map(x -> x + 1, alg.source(v_for_fused_map)))))))))).value;

        return value;
    }

    @Benchmark
    public Long filters_Java8Streams() {
        Long value = java.util.stream.Stream.of(v_for_fused_filter)
                .filter(x -> x > 0)
                .filter(x -> x > 1)
                .filter(x -> x > 2)
                .filter(x -> x > 3)
                .filter(x -> x > 4)
                .filter(x -> x > 5)
                .filter(x -> x > 6)
                .filter(x -> x > 7)
                .count();
        return value;
    }

    @Benchmark
    public Long maps_Java8Streams() {
        Long value = java.util.stream.Stream.of(v_for_fused_map)
                .map(x -> x + 1)
                .map(x -> x + 1)
                .map(x -> x + 1)
                .map(x -> x + 1)
                .map(x -> x + 1)
                .map(x -> x + 1)
                .map(x -> x + 1)
                .reduce(0L, Long::sum);

        return value;
    }
}
