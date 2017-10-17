package tool.function;

@FunctionalInterface
public interface TeFunction<S, T, U, V> {
	public V apply(S s, T t, U u);
}
