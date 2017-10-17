package tool.function;

@FunctionalInterface
public interface IntObjFunction<S, T> {
	public T apply(int i, S s);
}
