package tool.optional;

import java.util.Optional;
import java.util.OptionalInt;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.ToIntFunction;
import java.util.stream.Stream;

/**
 * 结合java8的 optional,lambda表达式
 * @author MoeKagari
 * @see Optional
 * @see Stream
 */
public class OptionalUtils {
	public static <S, T, U> void toMapIfPresent(Optional<S> op, Function<? super S, ? extends T> key, Function<? super S, ? extends U> value, BiConsumer<T, U> con) {
		op.ifPresent(obj -> {
			con.accept(key.apply(obj), value.apply(obj));
		});
	}

	public static <S> OptionalInt objToInt(Optional<S> op, ToIntFunction<S> fun) {
		return op.isPresent() ? OptionalInt.of(fun.applyAsInt(op.get())) : OptionalInt.empty();
	}
}
