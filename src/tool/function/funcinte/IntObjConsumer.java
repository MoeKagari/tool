package tool.function.funcinte;

/** int,obj 的 consumer */
@FunctionalInterface
public interface IntObjConsumer<S> {
	public void accept(int i, S s);
}
