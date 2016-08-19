package gui.view.applicationpane.imageviewer;

import gui.controller.ImageViewerController;
import gui.controller.exceptions.ControllerException;
import gui.view.applicationpane.SubViews;
import java.awt.image.BufferedImage;
import java.util.concurrent.atomic.AtomicReference;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;

/**
 * Created by ca on 16/07/16.
 */
public class ImageViewer implements SubViews {

    private final BooleanProperty livePreviewLoading;
    private final BooleanProperty livePreviewRunning;

    private final Pane imagePane;
    private BorderPane webcamPanel;
    private ImageView imageView;
    private final ImageViewerController controller;

    public ImageViewer(ImageViewerController controller) {
        if (controller == null) {
            throw new IllegalArgumentException("The passed controller is null.");
        }
        this.livePreviewRunning = new SimpleBooleanProperty(false);
        this.livePreviewLoading = new SimpleBooleanProperty(false);
        this.controller = controller;
        this.imagePane = createImagePane();
    }

    private VBox createImagePane() {
        webcamPanel = createWebcamPanel();
        Button previewButton = createPreviewButton();
        VBox pane = new VBox(webcamPanel, previewButton);
        pane.setAlignment(Pos.CENTER);
        pane.setFillWidth(true);
        pane.setSpacing(5);
        return pane;
    }

    private BorderPane createWebcamPanel() {
        BorderPane panel = new BorderPane();
        panel.setMaxSize(Double.MAX_VALUE, Double.MIN_VALUE);
        panel.setMinSize(400, 300);
        panel.setBackground(new Background(new BackgroundFill(Paint.valueOf("#727272"), CornerRadii.EMPTY, Insets.EMPTY)));
        Text previewText = new Text("Vorschau");
        previewText.setFill(Paint.valueOf("#FAFAFA"));
        panel.setCenter(previewText);
        return panel;
    }

    private Button createPreviewButton() {
        Button preview = new Button();
        StringProperty buttonText = new SimpleStringProperty("Vorschau");
        preview.textProperty().bind(buttonText);
        preview.disableProperty().bind(livePreviewLoading);

        livePreviewLoading.addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (newValue == true && oldValue == false) {
                buttonText.set("Vorschau wird geladen");
            } else if (newValue == false && oldValue == true) {
                buttonText.set("Vorschau stoppen");
            }
        });
        livePreviewRunning.addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (newValue == false && oldValue == true) {
                buttonText.set("Vorschau");
            }
        });

        preview.setOnAction((ActionEvent event) -> {
            event.consume();
            // do sth with the button
            if (!livePreviewLoading.get() && !livePreviewRunning.get()) {
                // not loading AND not running
                startLivePreview();
            } else if (livePreviewRunning.get() && livePreviewLoading.not().get()) {
                // running AND not loading
                livePreviewRunning.set(false);
                // TODO improve this and show hint text again
                imageView.setVisible(false);
            }
        });
        return preview;
    }

    private void startLivePreview() {
        // TODO dont create a new ImageView every time
        imageView = createAndConfigureImageView();
        ObjectProperty<Image> imageProperty = new SimpleObjectProperty<>();
        imageView.imageProperty().bind(imageProperty);

        livePreviewLoading.set(true);
        try {
            boolean isReady = controller.isCameraReadyForLiveImage();
            livePreviewRunning.set(isReady);
            livePreviewLoading.set(!isReady);
        } catch (ControllerException ex) {
            // TODO handle error
            ex.printStackTrace();
        }

        // TODO extract and improve this!
        Task<Void> livePreviewTask;
        livePreviewTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                final AtomicReference<WritableImage> ref = new AtomicReference<>();
                BufferedImage img;
                while (livePreviewRunning.get()) {
                    img = controller.getLiveImage();
                    if (img != null) {
                        ref.set(SwingFXUtils.toFXImage(img, ref.get()));
                        img.flush();
                        Platform.runLater(() -> {
                            imageProperty.set(ref.get());
                        });
                    }
                }
                return null;
            }
        };
        Thread th = new Thread(livePreviewTask);
        th.setDaemon(true);
        th.start();
    }

    private ImageView createAndConfigureImageView() {
        ImageView imgView = new ImageView();
        imgView.preserveRatioProperty().set(true);
        imgView.fitWidthProperty().bind(webcamPanel.widthProperty());
        imgView.fitHeightProperty().bind(webcamPanel.heightProperty());
        webcamPanel.centerProperty().set(imgView);
        return imgView;
    }

    @Override
    public Node getNode() {
        return imagePane;
    }
}
