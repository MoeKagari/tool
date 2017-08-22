package tool.function.funcinte;

@FunctionalInterface
public interface IntObjFunction<S, T> {
	public T apply(int i, S s);
}
