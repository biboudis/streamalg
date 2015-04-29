import scala.language.higherKinds
import scala.language.implicitConversions

// A Fluent API of Streams a la carte for Scala using implicit classes.

object Stream {

  // Stream algebra for intermediate operators. C can be Pull, Push, etc.
  trait StreamAlg[C[_]] {
    def ofArray[T](array: Array[T]) : C[T]
    def map[T,R](f : T => R, app : C[T]) : C[R]
    def flatMap[T,R](f : T => C[R], app : C[T]) : C[R]
    def filter[T](f : T => Boolean, app : C[T]) : C[T]
  }

  // Algebra of terminal operators.
  trait ExecStreamAlg[C[_], E[_]] extends StreamAlg[C] {
    def sum[T](app : C[T]) : E[Long]
    def count[T](app : C[T]) : E[Long]
  }

  def ofArray[T, C[_], E[_], F <: ExecStreamAlg[C, E]](array: Array[T]) : F => C[T] = {
    alg => alg.ofArray(array)
  }

  trait Push[T] // dummy Push stream
  trait Pull[T] // dummy Pull stream
  type Id[A] = A // just a container of A

  // in a second phase we can scrap this with a macro 
  implicit class RichReader[T, C[_], E[_], F <% ExecStreamAlg[C, E]](func : F => C[T]) {

    def map[R](f : T => R) : F => C[R] = {
      alg => alg.map(f, func(alg))
    }

    def flatMap[R](f : T => C[R]) : F => C[R] = {
      alg => alg.flatMap(f, func(alg))
    }

    def filter(p : T => Boolean) : F => C[T] = {
      alg => alg.filter(p, func(alg))
    }

    def count() : F => E[Long] = {
      alg => alg.count(func(alg))
    }

    def sum() : F => E[Long] = {
      alg => alg.sum(func(alg))
    }
  }

  def query[T,  C[_], E[_]](array: Array[Int])(alg : ExecStreamAlg[C, E]) : E[Long] = {
    Stream.ofArray[Int, C, E, ExecStreamAlg[C, E]](array).filter((x:Int) => x%2==0).count()(alg)
  }
}