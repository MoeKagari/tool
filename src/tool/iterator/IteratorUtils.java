package tool.iterator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

import tool.function.IntObjConsumer;
import tool.function.IntObjFunction;

public class IteratorUtils {
	public static <S> void forEach(Iterator<S> iter, Consumer<S> con) {
		while (iter.hasNext()) {
			con.accept(iter.next());
		}
	}

	public static <S> void forEachUseIndex(Iterator<S> iter, IntObjConsumer<? super S> con) {
		int index = 0;
		while (iter.hasNext()) {
			con.accept(index, iter.next());
			index++;
		}
	}

	/** 返回的list为{@link ArrayList} */
	public static <S, T> List<T> toListUseIndex(Iterator<S> iter, IntObjFunction<? super S, ? extends T> fun) {
		List<T> result = new ArrayList<>();

		int index = 0;
		while (iter.hasNext()) {
			result.add(fun.apply(index, iter.next()));
			index++;
		}

		return result;
	}
}
