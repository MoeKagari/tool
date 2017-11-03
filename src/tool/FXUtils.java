package tool;

import java.util.function.Consumer;

import org.apache.commons.lang3.exception.ExceptionUtils;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import javafx.util.converter.IntegerStringConverter;

public class FXUtils {
	public static void showExceptionDialog(Throwable throwable) {
		TextArea textArea = new TextArea();
		textArea.setEditable(false);
		textArea.setWrapText(false);
		textArea.setText(ExceptionUtils.getStackTrace(throwable));
		//textArea.setMaxWidth(Double.MAX_VALUE);
		//textArea.setMaxHeight(Double.MAX_VALUE);

		Alert alert = new Alert(AlertType.ERROR, throwable.getMessage(), ButtonType.CLOSE);
		DialogPane dialogPane = alert.getDialogPane();
		dialogPane.setExpandableContent(textArea);
		alert.showAndWait();
	}

	public static VBox createVBox(int spacing, Pos alignment, boolean haveBorder, Node... children) {
		VBox vbox = new VBox(spacing, children);
		vbox.setAlignment(alignment);
		if (haveBorder) {
			vbox.setBorder(createNewBorder());
		}
		return vbox;
	}

	public static HBox createHBox(int spacing, Pos alignment, boolean haveBorder, Node... children) {
		HBox hbox = new HBox(spacing, children);
		hbox.setAlignment(alignment == null ? Pos.CENTER : alignment);
		if (haveBorder) {
			hbox.setBorder(createNewBorder());
		}
		return hbox;
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

	public static <T> void addNumberColumn(TableView<T> table) {
		TableColumn<T, T> numberCol = new TableColumn<>();
		numberCol.setMinWidth(50);
		numberCol.setSortable(false);
		numberCol.setCellFactory(param -> {
			return new TableCell<T, T>() {
				@Override
				protected void updateItem(T item, boolean empty) {
					super.updateItem(item, empty);
					if (this.getTableRow() != null && item != null) {
						this.setText(String.valueOf(this.getTableRow().getIndex() + 1));
					} else {
						this.setText("");
					}
				}
			};
		});
		numberCol.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		table.getColumns().add(numberCol);
	}

	public static Border createNewBorder() {
		return new Border(new BorderStroke(Color.LIGHTGRAY, BorderStrokeStyle.SOLID, new CornerRadii(0), new BorderWidths(1)));
	}

	public static void addCloseConfirmationWindow(Stage stage, String title, String contentText) {
		addCloseConfirmationWindow(stage, title, contentText, null);
	}

	public static void addCloseConfirmationWindow(Stage stage, String title, String contentText, Consumer<WindowEvent> otherWhenClose) {
		stage.setOnCloseRequest(ev -> {
			if (stage.isIconified()) {
				ev.consume();
				return;
			}

			Alert alert = new Alert(AlertType.NONE, contentText, ButtonType.YES, ButtonType.NO);
			alert.setTitle(title);

			//使 alert 居 stage 中央
			double[] location = getStageCenterLocation(stage);
			//alert 的 size [376,103]
			//alert显示之前 不能直接由alert获取
			//随着contentText 的长度变化 ,可能会有偏差
			alert.setX(location[0] - 376 / 2);
			alert.setY(location[1] - 103 / 2);

			alert.initOwner(stage);
			if (alert.showAndWait().orElse(null) != ButtonType.YES) {
				ev.consume();
			} else {
				if (otherWhenClose != null) {
					otherWhenClose.accept(ev);
				}
			}
		});
	}

	public static double[] getStageCenterLocation(Stage stage) {
		return new double[] {
				stage.getX() + stage.getWidth() / 2,
				stage.getY() + stage.getHeight() / 2//
		};
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
}
