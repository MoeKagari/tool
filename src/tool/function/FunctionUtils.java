
package tool.function;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.RandomAccess;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntConsumer;
import java.util.function.IntFunction;
import java.util.function.IntPredicate;
import java.util.function.IntSupplier;
import java.util.function.IntUnaryOperator;
import java.util.function.ObjIntConsumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import tool.iterator.IteratorUtils;

/**
 * 结合java8的 function包,lambda表达式,stream 使用
 * 
 * @author MoeKagari
 * @see Supplier
 * @see Consumer
 * @see Function
 * @see Stream
 */
public class FunctionUtils {
	public static String emptyString() {
		return "";
	}

	public static <S> String emptyString(S s) {
		return "";
	}

	/** 数学上的除法,并不是编程中的除法"/" */
	public static double division(int a, int b) {
		return a * 1.0 / b;
	}

	public static <S> boolean isNull(S obj) {
		return obj == null;
	}

	public static <S> boolean isNotNull(S obj) {
		return obj != null;
	}

	public static <S> S returnSelf(S s) {
		return s;
	}

	public static boolean isTrue(boolean b) {
		return b == true;
	}

	public static <S> boolean isTrue(Predicate<? super S> p, S s) {
		return isTrue(p.test(s));
	}

	public static <S, T> boolean isTrue(BiPredicate<? super S, ? super T> bp, S s, T t) {
		return isTrue(bp.test(s, t));
	}

	public static boolean isFalse(boolean b) {
		return b == false;
	}

	public static <S> boolean isFalse(Predicate<? super S> p, S s) {
		return isFalse(p.test(s));
	}

	public static <S, T> boolean isFalse(BiPredicate<? super S, ? super T> bp, S s, T t) {
		return isFalse(bp.test(s, t));
	}

	/*----------------------------------------------------------------------------------------------------------*/

	/** @see Collections#emptyList() */
	public static <S> List<S> emptyList() {
		return Collections.emptyList();
	}

	/** @see Collections#emptyMap() */
	public static <S, T> Map<S, T> emptyMap() {
		return Collections.emptyMap();
	}

	@SafeVarargs
	public static <S> List<S> asList(S... ss) {
		return Arrays.asList(ss);
	}

	@SafeVarargs
	public static <S> Stream<S> stream(S... ss) {
		return Arrays.stream(ss);
	}

	public static IntStream stream(int... is) {
		return Arrays.stream(is);
	}

	public static DoubleStream stream(double... ds) {
		return Arrays.stream(ds);
	}

	public static LongStream stream(long... ls) {
		return Arrays.stream(ls);
	}

	/*----------------------------------------------------------------------------------------------------------*/

	public static IntSupplier addIntSupplier(IntSupplier is1, IntSupplier is2) {
		return () -> is1.getAsInt() + is2.getAsInt();
	}

	@SafeVarargs
	public static <S> S use(S s, Consumer<S>... cons) {
		for (Consumer<S> con : cons) {
			con.accept(s);
		}
		return s;
	}

	public static <S, T, U> Function<S, U> andThen(Function<S, T> fun1, Function<? super T, ? extends U> fun2) {
		return fun1.andThen(fun2);
	}

	/*----------------------------------------------------------------------------------------------------------*/
	private final static Consumer<?> EMPTY_CONSUMER = obj -> {};

	@SuppressWarnings("unchecked")
	public static <S> Consumer<S> emptyConsumer() {
		return (Consumer<S>) EMPTY_CONSUMER;
	}

	public static <S> Runnable getRunnable(Consumer<? super S> con, S s) {
		return () -> con.accept(s);
	}

	public static <S, T> Runnable getRunnable(BiConsumer<? super S, ? super T> con, S s, T t) {
		return () -> con.accept(s, t);
	}

	public static <S, T, U> Runnable getRunnable(TeConsumer<? super S, ? super T, ? super U> tc, S s, T t, U u) {
		return () -> tc.accept(s, t, u);
	}

	public static <S, T> Consumer<S> getConsumer(BiConsumer<? super S, ? super T> bc, T t) {
		return s -> bc.accept(s, t);
	}

	public static <S, T, U> Consumer<S> getConsumer(TeConsumer<? super S, ? super T, ? super U> tc, T t, U u) {
		return s -> tc.accept(s, t, u);
	}

	/*----------------------------------------------------------------------------------------------------------*/

	public static void ifRunnable(boolean b, Runnable run) {
		if (b) run.run();
	}

	public static void ifNotRunnable(boolean b, Runnable run) {
		ifRunnable(!b, run);
	}

	/*-----------------------------------------Runnable-----------------------------------------------------------------*/

	public static <S> void ifRunnable(int value, IntPredicate pre, Runnable run) {
		ifRunnable(pre.test(value), run);
	}

	public static <S> void ifNotRunnable(int value, IntPredicate pre, Runnable run) {
		ifNotRunnable(pre.test(value), run);
	}

	public static <S> void ifRunnable(S s, Predicate<? super S> pre, Runnable run) {
		ifRunnable(pre.test(s), run);
	}

	public static <S> void ifNotRunnable(S s, Predicate<? super S> pre, Runnable run) {
		ifNotRunnable(pre.test(s), run);
	}

	/*--------------------------------------------Consumer--------------------------------------------------------------*/

	public static <S> void ifConsumer(int value, IntPredicate pre, IntConsumer con) {
		if (pre.test(value)) con.accept(value);
	}

	public static <S> void ifNotConsumer(int value, IntPredicate pre, IntConsumer con) {
		ifConsumer(value, pre.negate(), con);
	}

	public static <S> void ifConsumer(S s, Predicate<? super S> pre, Consumer<? super S> con) {
		if (pre.test(s)) con.accept(s);
	}

	public static <S> void ifNotConsumer(S s, Predicate<? super S> pre, Consumer<? super S> con) {
		ifConsumer(s, pre.negate(), con);
	}

	/*-------------------------------------------Function---------------------------------------------------------------*/

	public static int ifFunction(int value, IntPredicate pre, IntUnaryOperator fun, int defaultValue) {
		return pre.test(value) ? fun.applyAsInt(value) : defaultValue;
	}

	public static int ifNotFunction(int value, IntPredicate pre, IntUnaryOperator fun, int defaultValue) {
		return ifFunction(value, pre.negate(), fun, defaultValue);
	}

	public static <S> S ifFunction(int value, IntPredicate pre, IntFunction<? extends S> fun, S defaultValue) {
		return pre.test(value) ? fun.apply(value) : defaultValue;
	}

	public static <S> S ifNotFunction(int value, IntPredicate pre, IntFunction<? extends S> fun, S defaultValue) {
		return ifFunction(value, pre.negate(), fun, defaultValue);
	}

	public static <S, T> T ifFunction(S s, Predicate<? super S> pre, Function<? super S, ? extends T> fun, T defaultValue) {
		return pre.test(s) ? fun.apply(s) : defaultValue;
	}

	public static <S, T> T ifNotFunction(S s, Predicate<? super S> pre, Function<? super S, ? extends T> fun, T defaultValue) {
		return ifFunction(s, pre.negate(), fun, defaultValue);
	}

	public static <S> int ifFunction(S s, Predicate<? super S> pre, ToIntFunction<? super S> fun, int defaultValue) {
		return pre.test(s) ? fun.applyAsInt(s) : defaultValue;
	}

	public static <S> int ifNotFunction(S s, Predicate<? super S> pre, ToIntFunction<? super S> fun, int defaultValue) {
		return ifFunction(s, pre.negate(), fun, defaultValue);
	}

	/*------------------------------------------notNull----------------------------------------------------------------*/

	public static <S> boolean notNull(S s, Predicate<? super S> pre, boolean defaultValue) {
		return s != null ? pre.test(s) : defaultValue;
	}

	public static <S> int notNull(S s, ToIntFunction<? super S> handler, int defaultValue) {
		return s != null ? handler.applyAsInt(s) : defaultValue;
	}

	public static <S, T> T notNull(S s, Function<? super S, ? extends T> handler, T defaultValue) {
		return s != null ? handler.apply(s) : defaultValue;
	}

	public static <S> void notNull(S s, Consumer<? super S> handler) {
		if (s != null) handler.accept(s);
	}

	public static <S> void notNull(S s, Runnable run) {
		if (s != null) run.run();
	}

	/*-----------------------------------------forEach-----------------------------------------------------------------*/

	public static <S, T> void forEach(S[] ss, T[] ts, BiConsumer<? super S, ? super T> consu) {
		if (ss.length != ts.length) throw new RuntimeException("两数组长度不合");
		for (int i = 0; i < ss.length; i++) {
			consu.accept(ss[i], ts[i]);
		}
	}

	public static <S> void forEach(S[] ss, int[] is, ObjIntConsumer<? super S> consu) {
		if (ss.length != is.length) throw new RuntimeException("两数组长度不合");
		for (int i = 0; i < ss.length; i++) {
			consu.accept(ss[i], is[i]);
		}
	}

	public static <S> void forEach(S[] ss, Consumer<? super S> consu) {
		Arrays.stream(ss).forEach(consu);
	}

	public static void forEachInt(int[] intArray, IntConsumer consu) {
		IntStream.of(intArray).forEach(consu);
	}

	public static <S> void forEachUseIndex(S[] ss, IntObjConsumer<? super S> consu) {
		IntStream.range(0, ss.length).forEach(index -> consu.accept(index, ss[index]));
	}

	public static <S> void forEachUseIndex(List<S> ss, IntObjConsumer<? super S> consu) {
		if (ss instanceof RandomAccess) {
			IntStream.range(0, ss.size()).forEach(index -> consu.accept(index, ss.get(index)));
		} else {
			IteratorUtils.forEachUseIndex(ss.iterator(), consu);
		}
	}

	/*-----------------------------------------other-----------------------------------------------------------------*/

	public static <S, T extends S> Stream<T> down(Stream<S> parentStream, Class<T> childClass) {
		return parentStream.filter(childClass::isInstance).map(childClass::cast);
	}

	/** 返回的list为{@link ArrayList} */
	public static <S, T extends S> List<T> down(List<S> parentList, Class<T> childClass) {
		List<T> result = new ArrayList<>();
		down(parentList.stream(), childClass).forEach(result::add);
		return result;
	}

	/** 返回的list为{@link ArrayList} */
	public static <S, T> List<T> toListUseIndex(List<S> ss, IntObjFunction<? super S, ? extends T> fun) {
		if (ss instanceof RandomAccess) {
			return IntStream.range(0, ss.size()).mapToObj(index -> fun.apply(index, ss.get(index))).collect(Collectors.toCollection(ArrayList::new));
		} else {
			return IteratorUtils.toListUseIndex(ss.iterator(), fun);
		}
	}

	public static <S, T, U> void toMapForEach(S[] ss, Function<? super S, ? extends T> key, Function<? super S, ? extends U> value, BiConsumer<? super T, ? super U> con) {
		FunctionUtils.forEach(ss, s -> con.accept(key.apply(s), value.apply(s)));
	}

	public static <S, T, U> void toMapForEach(Stream<S> stream, Function<? super S, ? extends T> key, Function<? super S, ? extends U> value, BiConsumer<? super T, ? super U> con) {
		stream.forEach(s -> con.accept(key.apply(s), value.apply(s)));
	}

	public static <S, T, U> void toMapForEach(List<S> list, Function<? super S, ? extends T> key, Function<? super S, ? extends U> value, BiConsumer<? super T, ? super U> con) {
		list.forEach(s -> con.accept(key.apply(s), value.apply(s)));
	}
}
