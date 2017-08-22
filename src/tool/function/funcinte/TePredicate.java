package tool.function.funcinte;

@FunctionalInterface
public interface TePredicate<S, T, U> {
	public boolean test(S s, T t, U u);
}
