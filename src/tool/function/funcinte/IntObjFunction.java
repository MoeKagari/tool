package tool.function.funcinte;

/** int,object → object */
@FunctionalInterface
public interface IntObjFunction<S, T> {
	public T apply(int i, S s);
}