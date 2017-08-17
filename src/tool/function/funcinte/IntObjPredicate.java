package tool.function.funcinte;

/** int,obj -> boolean */
@FunctionalInterface
public interface IntObjPredicate<S> {
	public boolean test(int i, S s);
}
