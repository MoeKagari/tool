package tool.function.funcinte;

/** 三个obj的Consumer */
@FunctionalInterface
public interface TeConsumer<S, T, U> {
	public void accept(S s, T t, U u);
}
