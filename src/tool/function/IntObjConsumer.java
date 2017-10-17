package tool.function;

@FunctionalInterface
public interface IntObjConsumer<S> {
	public void accept(int i, S s);
}
