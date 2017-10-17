package tool.function;

@FunctionalInterface
public interface BiIntFunction<S> {
	public S apply(int a, int b);
}
