package tool.function.funcinte;

/** int,int → object */
@FunctionalInterface
public interface BiIntFunction<S> {
	public S apply(int a, int b);
}
