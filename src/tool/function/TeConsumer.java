package tool.function;

@FunctionalInterface
public interface TeConsumer<S, T, U> {
	public void accept(S s, T t, U u);
}
