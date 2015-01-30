## StreamAlg

[![Build Status](https://magnum.travis-ci.com/biboudis/streamalg.svg?token=EYsxboxiFVSqpFARwkTX&branch=master)](https://magnum.travis-ci.com/biboudis/streamalg)

The StreamAlg repository contains the source code artifact that accompanies the
_Streams à la carte: Extensible Pipelines with Object Algebras_ currently under
review on ECOOP15.

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

Current behaviors implemented: push, pull, fused pull, logged, id (for
blocking), future (for async operators).

### Getting Started

Clone the project:
```
git clone git@github.com:biboudis/streamalg.git
```
The project is built with maven and to run the test suite simply run
```
mvn tests
```
Benchmarks are reproduced with:
```
sh run_benchmarks.sh
```

### Team

Aggelos Biboudis ([@biboudis](https://twitter.com/biboudis)), Nick Palladinos
([@NickPalladinos](https://twitter.com/NickPalladinos)), George Fourtounis
([@gf0ur](https://twitter.com/gf0ur)) and
[Yannis Smaragdakis](http://cgi.di.uoa.gr/~smaragd/)
