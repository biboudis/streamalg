## StreamAlg

[![Build Status](https://travis-ci.org/biboudis/streamalg.svg?branch=master)](https://travis-ci.org/biboudis/streamalg)

The StreamAlg repository contains the source code artifact that accompanies the
[http://goo.gl/P2XO68](_Streams à la carte: Extensible Pipelines with Object Algebras_) paper, to appear at the
[29th European Conference on Object-Oriented Programming (ECOOP'15)](http://2015.ecoop.org/).

### Overview

We address extensibility shortcomings in libraries for lazy-streaming queries
with a new design. The architecture underlying this design borrows heavily from
Oliveira and Cook's object algebra solution to the expression problem, extended
with a design that exposes the push/pull character of the iteration, and an
encoding of higher-kinded polymorphism.

In this library we apply our design to Java and show that the addition of full
extensibility is accompanied by high performance, matching or exceeding that of
the original, highly-optimized Java streams library.

In this repository we present a fundamental set of sequential operators ```map```,
```filter```, ```reduce```, ```count```, ```take/limit``` and ```iterate```.

Additionally we present the behaviors that are discussed in the paper: push, pull, fused pull, logging, id (for
blocking terminal operators), future (for non-blocking terminal operators).

### Getting Started

#### Prerequisites
The project runs with Java 8.

#### Testing
Clone the project:
```shell
git clone git@github.com:biboudis/streamalg.git
```
The project is built with maven and its dependencies are automatically resolved: Guava, JMH and JUnit. To run the test suite simply run:
```shell
mvn test
```
The tests cover all examples included in the paper (operators, behaviors) and cases used as motivation as well. The ```streams``` package is covered at:	87% classes,	90% methods,	91% lines.

#### Running Benchmarks
Benchmarks are reproduced by executing:
```shell
sh run_benchmarks.sh
```

### Project Structure
The basic packages of this artifact are the following:

#### Object Algebras
- StreamAlg: describes the object algebra of the intermediate operators of streams.
- TakeStreamAlg: adds to streams the ```take``` combinator.
- ExecStreamAlg: adds to streams the terminal operators.
- ExecIterateStreamAlg: adds to streams the ```iterate``` terminal operator.
- ExecTakeStreamAlg: unifies terminal operators with the algebra with the ```take``` combinator.

#### Factories
The following factories implement different combinations of behaviors:

- ExecFusedPullFactory
- ExecFutureFactory
- ExecPullFactory
- ExecPullWithIterateFactory
- ExecPullWithTakeFactory
- ExecPushFactory
- ExecPushWithTakeFactory
- LogFactory
- PullFactory
- PushFactory

#### Higher Kinded types
The types that participate in higher-kinded polymorphism scenarios are: ```Future```, ```Id```, ```Pull``` and ```Push```.

#### GADT encoding
The encoding appears in the ```gadt.evaluator``` package.

#### Fluent APIs
In ```streamalg/fluent/Stream.cs``` and ```streamalg/fluent/Stream.scala``` we describe a
possible solution of obtaining fluent APIs of Streams a la carte in C# and Scala.

### Benchmarks
The ```run_benchmarks.sh``` script simply builds the JMH benchmarks über-jar and then uses the command line interface
of JMH to pass the arguments of the experiments. The script will run all benchmarks in
```streamalg/src/main/java/benchmarks/*``` and their description is included in the paper.
The user can run the benchmark script as is or by passing a regular-expression for filter to select only some of them. The
micro-benchmark suite can be passed the number of elements ```N``` for map, count, operations with large number of elements, ```N_small```
that is used for cart and limit/take examples. ```N_limit``` is used as the parameter for ```limit``` and ```F``` is used for
benchmarks with fused pipelines.

For more information on JMH the user can run it directly,
e.g., to get the help dialog ```java -jar target/microbenchmarks.jar -h```.

We omitted baseline tests from the paper (although we included them in the repo) as the focus of the paper
is not on comparing hand-optimized tight loops with streaming pipelines.
We have investigated this in previous work (http://arxiv.org/abs/1406.6631) and it is something that
we would like to investigate about the OpenJDK specifically in the immediate future.

We include three basic categories of benchmarks: _basic pipelines_ with various combinations about both Pull and Push algebras,
_fused pipelines_ to exercise map and filter fusion and help with the comparison between the non-fused pipelines,
_iterator pipelines_ to demonstrate differences of the Pull algebra and the obtaining of an iterator from Java 8 Streams
and _take pipelines_ (the ```take``` operator is the same as the ```limit``` operator in Java 8 Streams.

### Team

Aggelos Biboudis ([@biboudis](https://twitter.com/biboudis)), Nick Palladinos
([@NickPalladinos](https://twitter.com/NickPalladinos)), George Fourtounis
([@gf0ur](https://twitter.com/gf0ur)) and
[Yannis Smaragdakis](http://www.di.uoa.gr/~smaragd/).
