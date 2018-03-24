package tool;

import java.io.File;
import java.util.Collection;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.converter.IntegerStringConverter;
import tool.function.FunctionUtils;

public class FXUtils {
	private static void addChildren(Pane pane, Node... children) {
		pane.getChildren().addAll(children);
	}

	private static void addChildren(Pane pane, Collection<? extends Node> children) {
		pane.getChildren().addAll(children);
	}

	public static EventHandler<ActionEvent> makeEventHandler(Runnable r) {
		return ev -> {
			r.run();
		};
	}

	public static <T> ChangeListener<T> makeChangeListener(Runnable r) {
		return (source, oldValue, newValue) -> {
			r.run();
		};
	}

	public static <T> ChangeListener<T> makeChangeListener(BiConsumer<T, T> bc) {
		return (source, oldValue, newValue) -> {
			bc.accept(oldValue, newValue);
		};
	}

	public static void showExceptionDialog(Throwable throwable) {
		showExceptionDialog(null, throwable);
	}

	public static void showExceptionDialog(Stage parent, Throwable throwable) {
		showExceptionDialog(parent, throwable, null);
	}

	public static void showExceptionDialog(Stage parent, Throwable throwable, String defaultContentText) {
		TextArea textArea = new TextArea();
		textArea.setEditable(false);
		textArea.setWrapText(false);
		textArea.setText(ExceptionUtils.getStackTrace(throwable));

		String contentText = Optional.ofNullable(defaultContentText).map(s -> s + "\n").orElse("")
				+
				Optional.ofNullable(throwable.getMessage()).orElse("");

		Alert alert = new Alert(AlertType.ERROR, contentText, ButtonType.CLOSE);
		if (parent != null) alert.initOwner(parent);
		alert.getDialogPane().setExpandableContent(textArea);
		alert.showAndWait();
	}

	public static void setText(Labeled label, String text) {
		if (FunctionUtils.isFalse(StringUtils.equals(label.getText(), text))) {
			label.setText(text);
		}
	}

	public static void setToolTipText(Control label, String toolTipText) {
		if (StringUtils.isBlank(toolTipText)) {
			label.setTooltip(null);
			return;
		}

		Tooltip toolTip = label.getTooltip();
		if (toolTip == null) {
			label.setTooltip(new Tooltip(toolTipText));
			return;
		}

		if (FunctionUtils.isFalse(StringUtils.equals(toolTip.getText(), toolTipText))) {
			toolTip.setText(toolTipText);
		}
	}

	public static String getImageUrl(String filepath) {
		return getImageUrl(new File(filepath));
	}

	public static String getImageUrl(File file) {
		return file.toURI().toString();
	}

	public static ScrollPane createScrollPane(Node content) {
		return createScrollPane(content, FunctionUtils.emptyConsumer());
	}

	public static ScrollPane createScrollPane(Node content, Consumer<ScrollPane> init) {
		ScrollPane scrollPane = new ScrollPane(content);
		init.accept(scrollPane);
		return scrollPane;
	}

	public static TabPane createTabPane(Tab... tabs) {
		return createTabPane(FunctionUtils.emptyConsumer(), tabs);
	}

	public static TabPane createTabPane(Consumer<TabPane> init, Tab... tabs) {
		TabPane tabPane = new TabPane(tabs);
		init.accept(tabPane);
		return tabPane;
	}

	public static Button createButton(String text, Runnable value) {
		Button button = new Button(text);
		button.setOnAction(FXUtils.makeEventHandler(value));
		return button;
	}

	public static Button createButton(String text, EventHandler<ActionEvent> value) {
		Button button = new Button(text);
		button.setOnAction(value);
		return button;
	}

	//@formatter:off
	private static VBox createVBox(int spacing, Pos alignment, boolean haveBorder, Consumer<VBox> init) {
		VBox box = new VBox(spacing);
		box.setAlignment(alignment);
		if (haveBorder) 	box.setBorder(createNewBorder());		
		init.accept(box);
		return box;
	}
	public static VBox createVBox(int spacing, Pos alignment, boolean haveBorder, Node... children) {
		return createVBox(spacing, alignment, haveBorder, FunctionUtils.emptyConsumer(), children);
	}
	public static VBox createVBox(int spacing, Pos alignment, boolean haveBorder, Consumer<VBox> init, Node... children) {
		return createVBox(spacing, alignment, haveBorder, init.andThen(box->{
			addChildren(box, children);
		})) ;
	}
	public static VBox createVBox(int spacing, Pos alignment, boolean haveBorder, Collection<? extends Node> children) {
		return createVBox(spacing, alignment, haveBorder, FunctionUtils.emptyConsumer(), children);
	}
	public static VBox createVBox(int spacing, Pos alignment, boolean haveBorder, Consumer<VBox> init,  Collection<? extends Node> children) {
		return createVBox(spacing, alignment, haveBorder, init.andThen(box->{
			addChildren(box, children);
		})) ;
	}
	
	private static HBox createHBox(int spacing, Pos alignment, boolean haveBorder, Consumer<HBox> init ) {
		HBox box = new HBox(spacing);
		box.setAlignment(alignment);
		if (haveBorder) 	box.setBorder(createNewBorder());		
		init.accept(box);
		return box;
	}
	public static HBox createHBox(int spacing, Pos alignment, boolean haveBorder, Node... children) {
		return createHBox(spacing, alignment, haveBorder, FunctionUtils.emptyConsumer(), children);
	}
	public static HBox createHBox(int spacing, Pos alignment, boolean haveBorder, Consumer<HBox> init, Node... children) {
		return createHBox(spacing, alignment, haveBorder, init.andThen(box->{
			addChildren(box, children);
		})) ;
	}
	public static HBox createHBox(int spacing, Pos alignment, boolean haveBorder, Collection<? extends Node>  children) {
		return createHBox(spacing, alignment, haveBorder, FunctionUtils.emptyConsumer(), children);
	}
	public static HBox createHBox(int spacing, Pos alignment, boolean haveBorder, Consumer<HBox> init, Collection<? extends Node> children) {
		return createHBox(spacing, alignment, haveBorder, init.andThen(box->{
			addChildren(box, children);
		})) ;
	}
	//@formatter:on

	public static Region createHBoxBlank() {
		Region blank = new Region();
		HBox.setHgrow(blank, Priority.ALWAYS);
		return blank;
	}

	public static Region createVBoxBlank() {
		Region blank = new Region();
		VBox.setVgrow(blank, Priority.ALWAYS);
		return blank;
	}

	public static GridPane createGridPane() {
		return createGridPane(0, 0, FunctionUtils.emptyConsumer());
	}

	public static GridPane createGridPane(Consumer<GridPane> init) {
		return createGridPane(0, 0, init);
	}

	public static GridPane createGridPane(int hgap, int vgap) {
		return createGridPane(hgap, vgap, FunctionUtils.emptyConsumer());
	}

	public static GridPane createGridPane(int hgap, int vgap, Consumer<GridPane> init) {
		GridPane gridPane = new GridPane();
		gridPane.setHgap(hgap);
		gridPane.setVgap(vgap);
		init.accept(gridPane);
		return gridPane;
	}

	/** {@link GridPane}用 , 非百分数 */
	public static ColumnConstraints createColumnConstraints(double percent) {
		ColumnConstraints columnConstraints = new ColumnConstraints();
		columnConstraints.setPercentWidth(percent);
		return columnConstraints;
	}

	/** {@link GridPane}用 , 非百分数 */
	public static RowConstraints createRowConstraints(double percent) {
		RowConstraints rowConstraints = new RowConstraints();
		rowConstraints.setPercentHeight(percent);
		return rowConstraints;
	}

	public static void setStageSize(Stage stage, int width, int height) {
		stage.setWidth(width);
		stage.setHeight(height);
	}

	public static void setStageLocationToCenter(Stage stage) {
		Rectangle2D bounds = Screen.getPrimary().getVisualBounds();

		double minX = bounds.getMinX();
		double minY = bounds.getMinY();
		double maxX = bounds.getMaxX();
		double maxY = bounds.getMaxY();
		//System.out.println(bounds.getWidth() + "" + bounds.getHeight());
		//System.out.println(Arrays.toString(new double[] { minX, minY, maxX, maxY }));

		stage.setX((maxX - minX - stage.getWidth()) / 2);
		stage.setY((maxY - minY - stage.getHeight()) / 2);
	}

	public static void setMinMaxWidth(Region node, double width) {
		node.setMinWidth(width);
		node.setMaxWidth(width);
	}

	public static void setMinMaxHeight(Region node, double height) {
		node.setMinHeight(height);
		node.setMaxHeight(height);
	}

	public static void setMinMaxSize(Region node, double width, double height) {
		setMinMaxWidth(node, width);
		setMinMaxHeight(node, height);
	}

	public static Background createBackground(Paint fill) {
		return new Background(new BackgroundFill(fill, new CornerRadii(0), new Insets(0)));
	}

	public static double[] getStageCenterLocation(Stage stage) {
		return new double[] {
				stage.getX() + stage.getWidth() / 2,
				stage.getY() + stage.getHeight() / 2
		};
	}

	public static boolean showCloseConfirmationWindow(Stage stage) {
		return FXUtils.showCloseConfirmationWindow(stage, "退出", "确定退出?");
	}

	public static boolean showCloseConfirmationWindow(Stage stage, String title, String contentText) {
		if (stage.isIconified()) {
			//先解除stage的最小化 , 不然无法正常显示
			stage.setIconified(false);
		}

		Alert alert = new Alert(AlertType.NONE, contentText, ButtonType.YES, ButtonType.NO);
		alert.setTitle(title);
		alert.initOwner(stage);

		//使 alert 居 stage 中央
		double[] stageCenterLocation = getStageCenterLocation(stage);
		//alert 的 size [376,103]
		//alert显示之前 不能直接由alert获取
		//随着contentText 的长度变化 ,可能会有偏差
		alert.setX(stageCenterLocation[0] - 376 / 2);
		alert.setY(stageCenterLocation[1] - 103 / 2);

		return alert.showAndWait().orElse(null) == ButtonType.YES;
	}

	//@formatter:off  create new border
	private final static Border DEFAULT_BORDER = createNewBorder(Color.LIGHTGRAY, 1, 1, 1, 1);
	public static Border createNewBorder() {
		return DEFAULT_BORDER;
	}
	public static Border createNewBorder(Paint stoke) {
		return createNewBorder(stoke, 1, 1, 1, 1);
	}
	public static Border createNewBorder(int top, int right, int bottom, int left) {
		return createNewBorder(Color.LIGHTGRAY, top, right, bottom, left);
	}
	public static Border createNewBorder(boolean top, boolean right, boolean bottom, boolean left) {
		return createNewBorder(Color.LIGHTGRAY, top?1:0, right?1:0, bottom?1:0, left?1:0);
	}
	public static Border createNewBorder(Paint stoke, int top, int right, int bottom, int left) {
		return new Border(new BorderStroke(stoke, BorderStrokeStyle.SOLID, new CornerRadii(0), new BorderWidths(top, right, bottom, left)));
	}	
	//@formatter:on

	//@formatter:off  create new label
	public static Label createNewLabel(String text) {
		return createNewLabel(text, Pos.CENTER_LEFT, FunctionUtils.emptyConsumer());
	}	
	public static Label createNewLabel(String text, Consumer<Label> init) {
		return createNewLabel(text, Pos.CENTER_LEFT, init);
	}
	public static Label createNewLabel(String text, Pos alignment) {
		return createNewLabel(text, alignment, FunctionUtils.emptyConsumer());
	}
	public static Label createNewLabel(String text, Pos alignment, Consumer<Label> init) {
		Label label = new Label(text);
		label.setAlignment(alignment);
		init.accept(label);
		return label;
	}
	public static Label createNewLabel(String text, double minMaxWidth) {
		return createNewLabel(text, minMaxWidth, Pos.CENTER_LEFT, FunctionUtils.emptyConsumer());
	}
	public static Label createNewLabel(String text, double minMaxWidth, Consumer<Label> init) {
		return createNewLabel(text, minMaxWidth, Pos.CENTER_LEFT, init);
	}
	public static Label createNewLabel(String text, double minMaxWidth, Pos alignment) {
		return createNewLabel(text, minMaxWidth, alignment, FunctionUtils.emptyConsumer());
	}
	public static Label createNewLabel(String text, double minMaxWidth, Pos alignment, Consumer<Label> init) {
		Label label = new Label(text);
		label.setAlignment(alignment);
		setMinMaxWidth(label, minMaxWidth);
		init.accept(label);
		return label;
	}
	//@formatter:on

	//@formatter:off  add new column to table
	public static <T> void addNumberColumn(TableView<T> table) {
		addNumberColumn(table, 0);
	}
	public static <T> void addNumberColumn(TableView<T> table,int firstNumber) {
		TableColumn<T, T> numberCol = new TableColumn<>();
		numberCol.setMinWidth(50);
		numberCol.setSortable(false);
		numberCol.setCellFactory(param -> {
			return new TableCell<T, T>() {
				@Override
				protected void updateItem(T item, boolean empty) {
					super.updateItem(item, empty);
					if (this.getTableRow() != null && item != null) {
						this.setText(String.valueOf(firstNumber+this.getTableRow().getIndex() + 1));
					} else {
						this.setText("");
					}
				}
			};
		});
		numberCol.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		table.getColumns().add(numberCol);
	}
	
	public static <S, T> void addNewColumn(TableView<S> table, String name, Callback<TableColumn<S, T>, TableCell<S, T>> cell, Function<S, T> cellValue) {
		addNewColumn(table, name, cell, cellValue, FunctionUtils.emptyConsumer());
	}
	public static <S, T> void addNewColumn(TableView<S> table, String name, boolean sortable,  Callback<TableColumn<S, T>, TableCell<S, T>> cell, Function<S, T> cellValue) {
		addNewColumn(table, name, sortable,cell, cellValue, FunctionUtils.emptyConsumer());
	}
	public static <S, T> void addNewColumn(TableView<S> table, String name, Callback<TableColumn<S, T>, TableCell<S, T>> cell, Function<S, T> cellValue, Consumer<TableColumn<S, T>> init) {
		addNewColumn(table, name, false, cell, cellValue, init);
	}
	public static <S, T> void addNewColumn(TableView<S> table, String name,boolean sortable,  Callback<TableColumn<S, T>, TableCell<S, T>> cell, Function<S, T> cellValue, Consumer<TableColumn<S, T>> init) {
		TableColumn<S, T> tableColumn = new TableColumn<>();
		tableColumn.setText(name);
		tableColumn.setCellFactory(cell);
		tableColumn.setSortable(sortable);
		tableColumn.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(cellValue.apply(param.getValue())));
		init.accept(tableColumn);
		table.getColumns().add(tableColumn);
	}
	
	public static <S> void addNewStringColumn(TableView<S> table, String name, Function<S, String> cellValue) {
		addNewColumn(table, name, getStringTableCellFactory(), cellValue);
	}
	public static <S> void addNewStringColumn(TableView<S> table, String name, boolean sortable,Function<S, String> cellValue) {
		addNewColumn(table, name,sortable, getStringTableCellFactory(), cellValue);
	}
	public static <S> void addNewStringColumn(TableView<S> table, String name, Function<S, String> cellValue, Consumer<TableColumn<S, String>> init) {
		addNewColumn(table, name, getStringTableCellFactory(), cellValue, init);
	}
	public static <S> void addNewStringColumn(TableView<S> table, String name,boolean sortable, Function<S, String> cellValue, Consumer<TableColumn<S, String>> init) {
		addNewColumn(table, name,sortable, getStringTableCellFactory(), cellValue, init);
	}
	
	public static <S> void addNewIntegerColumn(TableView<S> table, String name, Function<S, Integer> cellValue) {
		addNewColumn(table, name, getIntegerTableCellFactory(), cellValue);
	}
	public static <S> void addNewIntegerColumn(TableView<S> table, String name,boolean sortable,  Function<S, Integer> cellValue) {
		addNewColumn(table, name,sortable, getIntegerTableCellFactory(), cellValue);
	}
	public static <S> void addNewIntegerColumn(TableView<S> table, String name, Function<S, Integer> cellValue, Consumer<TableColumn<S, Integer>> init) {
		addNewColumn(table, name, getIntegerTableCellFactory(), cellValue, init);
	}
	public static <S> void addNewIntegerColumn(TableView<S> table, String name,boolean sortable,  Function<S, Integer> cellValue, Consumer<TableColumn<S, Integer>> init) {
		addNewColumn(table, name,sortable, getIntegerTableCellFactory(), cellValue, init);
	}
	
	public static <S> void addNewImageColumn(TableView<S> table, String name, int size, Function<S, Image> cellValue) {
		addNewColumn(table, name, getImageTableCellFactory(size), cellValue);
	}
	public static <S> void addNewImageColumn(TableView<S> table, String name, int size,boolean sortable,  Function<S, Image> cellValue) {
		addNewColumn(table, name, sortable,getImageTableCellFactory(size), cellValue);
	}
	public static <S> void addNewImageColumn(TableView<S> table, String name, int size, Function<S, Image> cellValue, Consumer<TableColumn<S, Image>> init) {
		addNewColumn(table, name, getImageTableCellFactory(size), cellValue, init);
	}
	public static <S> void addNewImageColumn(TableView<S> table, String name, int size,boolean sortable, Function<S, Image> cellValue, Consumer<TableColumn<S, Image>> init) {
		addNewColumn(table, name,sortable, getImageTableCellFactory(size), cellValue, init);
	}
	
	public static <T> Callback<TableColumn<T, String>, TableCell<T, String>> getStringTableCellFactory() {
		return TextFieldTableCell.forTableColumn();
	}
	public static <T> Callback<TableColumn<T, Integer>, TableCell<T, Integer>> getIntegerTableCellFactory() {
		return TextFieldTableCell.forTableColumn(new IntegerStringConverter());
	}
	public static <T> Callback<TableColumn<T, Image>, TableCell<T, Image>> getImageTableCellFactory(int size) {
		return param -> new ImageViewTableCell<>(size);
	}
	private static class ImageViewTableCell<T> extends TableCell<T, Image> {
		private final ImageView imageView = new ImageView();

		public ImageViewTableCell(int size) {
			this.setMinSize(size, size);
			this.setMaxSize(size, size);
			this.setAlignment(Pos.CENTER);
			this.setGraphic(this.imageView);
		}

		@Override
		protected void updateItem(Image item, boolean empty) {
			super.updateItem(item, empty);
			this.imageView.setImage((empty || item == null) ? null : item);
		}
	}
	//@formatter:on

	//@formatter:off  有资源的 jfx node
	public static interface HaveResource<S>{public S getResource();}
	public static class FXResourceLabel<S> extends Label implements HaveResource<S>{
		private final S resource;
		public FXResourceLabel(S resource) {super();this.resource = resource;}
		public FXResourceLabel(S resource, String text) {super(text);this.resource = resource;}
		public FXResourceLabel(S resource, String text, Node graphic) {super(text, graphic);this.resource = resource;}
		@Override public S getResource() {return this.resource;}
	}
	public static class FXResourceButton<S> extends Button  implements HaveResource<S>{
		private final S resource;
		public FXResourceButton(S resource) {super();this.resource = resource;}
		public FXResourceButton(S resource, String text) {super(text);this.resource = resource;}
		public FXResourceButton(S resource, String text, Node graphic) {super(text, graphic);this.resource = resource;}
		@Override public S getResource() {return this.resource;}
	}
	public static class FXResourceRadioButton<S> extends RadioButton implements HaveResource<S>{
		private final S resource;
		public FXResourceRadioButton(S resource) {super();this.resource = resource;}
		public FXResourceRadioButton(S resource,String text) {super(text);this.resource = resource;}
		@Override public S getResource() {return this.resource;}
	}
	//@formatter:on
}
