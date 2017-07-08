package tool;

import java.util.Arrays;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntConsumer;
import java.util.function.IntFunction;
import java.util.function.IntPredicate;
import java.util.function.IntSupplier;
import java.util.function.ObjIntConsumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;

/**
 * 结合java8的 function包,lambda表达式,stream 使用
 * @author MoeKagari
 */
public class FunctionUtils {

	public static interface BiIntObjFunction<T> {
		public T apply(int a, int b);
	}

	public static int[] arrayCopy(int[] is) {
		return Arrays.copyOf(is, is.length);
	}

	public static double division(int a, int b) {
		return a * 1.0 / b;
	}

	public static boolean isNull(Object obj) {
		return obj == null;
	}

	public static boolean isNotNull(Object obj) {
		return obj != null;
	}

	public static boolean isTrue(boolean b) {
		return b == true;
	}

	public static boolean isFalse(boolean b) {
		return b == false;
	}

	public static <S> S returnSelf(S s) {
		return s;
	}

	/*----------------------------------------------------------------------------------------------------------*/

	public static Runnable getRunable(Runnable run) {
		return run;
	}

	public static <S> Runnable getRunnable(S s, Consumer<S> con) {
		return () -> con.accept(s);
	}

	public static <S, T> Runnable getRunnable(S s, T t, BiConsumer<S, T> con) {
		return () -> con.accept(s, t);
	}

	/*----------------------------------------------------------------------------------------------------------*/

	public static void ifHandle(boolean b, Runnable run) {
		if (b) run.run();
	}

	public static void ifNotHandle(boolean b, Runnable run) {
		ifHandle(!b, run);
	}

	public static int ifHandle(boolean b, IntSupplier sup, int defaultValue) {
		return b ? sup.getAsInt() : defaultValue;
	}

	public static int ifNotHandle(boolean b, IntSupplier sup, int defaultValue) {
		return ifHandle(!b, sup, defaultValue);
	}

	public static <S> S ifHandle(boolean b, Supplier<S> sup, S defaultValue) {
		return b ? sup.get() : defaultValue;
	}

	public static <S> S ifNotHandle(boolean b, Supplier<S> sup, S defaultValue) {
		return ifHandle(!b, sup, defaultValue);
	}

	/*----------------------------------------------------------------------------------------------------------*/

	public static <S> S ifHandle(int value, IntPredicate pre, IntFunction<S> fun, S defaultValue) {
		return pre.test(value) ? fun.apply(value) : defaultValue;
	}

	public static <S> S ifNotHandle(int value, IntPredicate pre, IntFunction<S> fun, S defaultValue) {
		return ifHandle(value, pre.negate(), fun, defaultValue);
	}

	public static <S> void ifHandle(S s, Predicate<S> pre, Runnable run) {
		if (pre.test(s)) run.run();
	}

	public static <S> void ifNotHandle(S s, Predicate<S> pre, Runnable run) {
		ifHandle(s, pre.negate(), run);
	}

	public static <S> void ifHandle(S s, Predicate<S> pre, Consumer<S> con) {
		if (pre.test(s)) con.accept(s);
	}

	public static <S> void ifNotHandle(S s, Predicate<S> pre, Consumer<S> con) {
		ifHandle(s, pre.negate(), con);
	}

	/*----------------------------------------------------------------------------------------------------------*/

	public static <S> boolean notNull(S s, Predicate<S> pre, boolean defaultValue) {
		return s != null ? pre.test(s) : defaultValue;
	}

	public static <S> int notNull(S s, ToIntFunction<S> handler, int defaultValue) {
		return s != null ? handler.applyAsInt(s) : defaultValue;
	}

	public static <S, T> T notNull(S s, Function<S, T> handler, T defaultValue) {
		return s != null ? handler.apply(s) : defaultValue;
	}

	public static <S> void notNull(S s, Consumer<S> handler) {
		if (s != null) handler.accept(s);
	}

	/*----------------------------------------------------------------------------------------------------------*/

	public static <S> void forEach(S[] ss, int[] is, ObjIntConsumer<S> consu) {
		if (ss.length != is.length) return;
		int len = ss.length;
		for (int i = 0; i < len; i++) {
			consu.accept(ss[i], is[i]);
		}
	}

	public static <S> void forEach(S[] ss, Consumer<S> consu) {
		for (S s : ss) {
			consu.accept(s);
		}
	}

	public static <S> void forEach(S[] ss, ObjIntConsumer<S> consu) {
		for (int i = 0; i < ss.length; i++) {
			consu.accept(ss[i], i);
		}
	}

	public static void forEach(int[] intArray, IntConsumer consu) {
		for (int i : intArray) {
			consu.accept(i);
		}
	}

}
