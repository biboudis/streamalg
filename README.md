## StreamAlg

[![Build Status](https://travis-ci.org/biboudis/streamalg.svg?branch=master)](https://travis-ci.org/biboudis/streamalg)

The StreamAlg repository contains the source code artifact that accompanies the
_Streams à la carte: Extensible Pipelines with Object Algebras_ paper, to appear at [ECOOP15](http://2015.ecoop.org).

- Preprint: http://goo.gl/P2XO68
- Talk. PL Seminar 2014, NTUA, Athens: https://slides.com/biboudis/streamalg-presentation/

### Overview

We address extensibility shortcomings in libraries for lazy-streaming queries
with a new design. The architecture underlying this design borrows heavily from
Oliveira and Cook's object algebra solution to the expression problem, extended
with a design that exposes the push/pull character of the iteration, and an
encoding of higher-kinded polymorphism.

In this library we apply our design to Java and show that the addition of full
extensibility is accompanied by high performance, matching or exceeding that of
the original, highly-optimized Java streams library.

Current operators implemented: the basic set of sequential operators ```map```,
```filter```, ```reduce```, ```count```, ```take/limit``` and ```iterate```.

Current behaviors implemented: push, pull, fused pull, logging, id (for
blocking terminal operators), future (for non-blocking terminal operators).

### Getting Started

Clone the project:
```shell
git clone git@github.com:biboudis/streamalg.git
```
The project is built with maven and its dependencies are automatically resolved: Guava, JMH and JUnit. To run the test suite simply run:
```shell
mvn test
```
The tests cover all examples included in the paper (operators, behaviors) and cases used as motivation as well. The ```streams``` package is covered at:	87% classes,	90% methods,	91% lines.

Benchmarks are reproduced by executing:
```shell
sh run_benchmarks.sh
```

### Team

Aggelos Biboudis ([@biboudis](https://twitter.com/biboudis)), Nick Palladinos
([@NickPalladinos](https://twitter.com/NickPalladinos)), George Fourtounis
([@gf0ur](https://twitter.com/gf0ur)) and
[Yannis Smaragdakis](http://cgi.di.uoa.gr/~smaragd/).
