using System;
using System.Linq;

// A Fluent API of Streams a la carte for C# using extension methods.

namespace Stream
{
	class App<C, T>
	{
		public object Value = null;
	}

	interface IStreamAlg<C>
	{
		App<C, T> OfArray<T> (T[] array);

		App<C, R> Map<T, R> (App<C, T> app, Func<T, R> f);

		App<C, T> Filter<T> (App<C, T> app, Func<T, bool> predicate);
	}

	interface IExecStreamAlg<E, C> : IStreamAlg<C>
	{
		App<E, int> Length<T> (App<C, T> app);

		App<E, T[]> ToArray<T> (App<C, T> app);
	}

	interface IExtStreamAlg<C> : IStreamAlg<C>
	{
		App<C, R> FlatMap<T, R> (App<C, T> app, Func<T, App<C, R>> f);
	}

	interface IExecExtStreamAlg<E, C> : IExecStreamAlg<E, C>, IExtStreamAlg<C>
	{
	}

	class LogStreamAlg<C> : IStreamAlg<C>
	{
		private readonly IStreamAlg<C> alg;

		public LogStreamAlg (IStreamAlg<C> alg)
		{
			this.alg = alg;
		}

		public App<C, T> OfArray<T> (T[] array)
		{
			return alg.OfArray (array);
		}

		public App<C, R> Map<T, R> (App<C, T> app, Func<T, R> f)
		{
			return alg.Map (app, x => {
				Console.WriteLine ("Map: " + x);
				return f (x);
			});
		}

		public App<C, T> Filter<T> (App<C, T> app, Func<T, bool> predicate)
		{
			return alg.Filter (app, x => {
				Console.WriteLine ("Filter: " + x);
				return predicate (x);
			});
		}
	}

	class ExtLogStreamAlg<C> : LogStreamAlg<C>, IExtStreamAlg<C>
	{
		private readonly IExtStreamAlg<C> alg;

		public ExtLogStreamAlg (IExtStreamAlg<C> alg)
			: base (alg)
		{
			this.alg = alg;
		}

		public App<C, R> FlatMap<T, R> (App<C, T> app, Func<T, App<C, R>> f)
		{
			return alg.FlatMap (app, x => {
				Console.WriteLine ("FlatMap: " + x);
				return f (x);
			});
		}
	}

	static class Stream
	{

		public static Func<F, App<C, T>> OfArray<F, C, T> (T[] array) where F : IStreamAlg<C>
		{
			return alg => alg.OfArray (array);
		}

		public static Func<F, App<C, R>> Map<F, C, T, R> (this Func<F, App<C, T>> streamF, Func<T, R> f) where F : IStreamAlg<C>
		{
			return alg => alg.Map (streamF (alg), f);
		}

		public static Func<F, App<C, T>> Filter<F, C, T> (this Func<F, App<C, T>> streamF, Func<T, bool> predicate) where F : IStreamAlg<C>
		{
			return alg => alg.Filter (streamF (alg), predicate);
		}

		public static Func<F, App<E, int>> Length<F, E, C, T> (this Func<F, App<C, T>> streamF) where F : IExecStreamAlg<E, C>
		{
			return alg => alg.Length (streamF (alg));
		}

		public static Func<F, App<E, T[]>> ToArray<F, E, C, T> (this Func<F, App<C, T>> streamF) where F : IExecStreamAlg<E, C>
		{
			return alg => alg.ToArray (streamF (alg));
		}
	}

	static class ExtStream
	{
		public static Func<F, App<C, R>> FlatMap<F, C, T, R> (this Func<F, App<C, T>> streamF, Func<T, Func<F, App<C, R>>> f) where F : IExtStreamAlg<C>
		{
			return alg => alg.FlatMap (streamF (alg), x => f (x) (alg));
		}
	}

	class MainClass
	{
		static int[] data = Enumerable.Range (1, 100).ToArray ();

		public static App<E, int> Example1<E, C> (IExecStreamAlg<E, C> alg)
		{
			return alg.Length (alg.Filter (alg.Map (alg.OfArray (data), x => x * x), x => x % 2 == 0));
		}

		public static App<E, int> Example2<E, C> (IExecExtStreamAlg<E, C> alg)
		{
			return alg.Length (alg.Filter (alg.FlatMap (alg.OfArray (data),
				y => alg.Map (alg.OfArray (data), x => x * x)), x => x % 2 == 0));
		}

		public static App<E, int> Example1Fluent<F, E, C> (F alg) where F : IExecStreamAlg<E, C>
		{

			Func<F, App<E, int>> streamF =
				Stream.OfArray<F, C, int> (data)
				.Map (x => x * x)
				.Filter (x => x % 2 == 0)
				.Length<F, E, C, int> ();

			return streamF (alg);
		}


		public static App<E, int> Example2Fluent<F, E, C> (F alg) where F : IExecExtStreamAlg<E, C>
		{
			Func<F, App<E, int>> streamF =
				Stream.OfArray<F, C, int> (data)
				.FlatMap (x => Stream.OfArray<F, C, int> (data).Map (y => y * y))
				.Filter (x => x % 2 == 0)
				.Length<F, E, C, int> ();

			return streamF (alg);
		}

		static class StreamAlgWrapper
		{
			public static StreamAlgWrapper<F, E, C, T> OfArray<F, E, C, T> (F alg, T[] array) where F : IExecStreamAlg<E, C>
			{
				return new StreamAlgWrapper<F, E, C, T> (alg.OfArray (array), alg);
			}
		}

		class StreamAlgWrapper<F, E, C, T> where F : IExecStreamAlg<E, C>
		{
			private readonly App<C, T> app;
			private readonly F alg;

			public StreamAlgWrapper (F alg)
			{
				this.alg = alg;
			}

			public StreamAlgWrapper (App<C, T> app, F alg)
			{
				this.app = app;
				this.alg = alg;
			}

			public StreamAlgWrapper<F, E, C, R> Map<R> (Func<T, R> f)
			{
				return new StreamAlgWrapper<F, E, C, R> (alg.Map (app, f), alg);
			}

			public StreamAlgWrapper<F, E, C, T> Filter (Func<T, bool> predicate)
			{
				return new StreamAlgWrapper<F, E, C, T> (alg.Filter (app, predicate), alg);
			}

			public App<E, int> Length ()
			{
				return alg.Length (app);
			}

			public App<E, T[]> ToArray ()
			{
				return alg.ToArray (app);
			}
		}

		public static App<E, int> ExampleTestSteamWrapper<F, E, C> (F alg) where F : IExecStreamAlg<E, C>
		{
			App<E, int> result =
				StreamAlgWrapper.OfArray<F, E, C, int> (alg, data)
				.Map (x => x * x)
				.Filter (x => x % 2 == 0)
				.Length ();

			return result;
		}


		public static void Main (string[] args)
		{


		}
	}
}
