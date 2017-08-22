package tool.function.funcinte;

@FunctionalInterface
public interface IntObjPredicate<S> {
	public boolean test(int i, S s);
}
