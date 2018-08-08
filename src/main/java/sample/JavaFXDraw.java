package sample;

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;


public class JavaFXDraw extends Application {
    //
//    private final Button resetButton = new Button("Reset");
//    private Image image;
//    private double screenWidth;
//    private double screenHeight;
//    private int currentX, currentY, oldX, oldY;
//
//    @Override
//    public void start(Stage primaryStage) {
//
//        StackPane root = new StackPane();
//        Screen screen = Screen.getPrimary();
//
//        Rectangle2D rect = screen.getVisualBounds();
//        this.screenWidth = rect.getWidth();
//        this.screenHeight = rect.getHeight();
//
//        Canvas canvas = new Canvas(screenWidth / 2 + 50,
//                screenHeight / 2 + 150);
//        final GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
//
//
//        //RESET BUTTON
//        resetButton.setOnAction(actionEvent -> {
//            graphicsContext.clearRect(1, 1,
//                    graphicsContext.getCanvas().getWidth() - 2,
//                    graphicsContext.getCanvas().getHeight() - 2);
//        });
//        resetButton.setTranslateX(10);
//
//
//        // Set up the pen color chooser
//        ChoiceBox<String> colorChooser = new ChoiceBox<>(
//                FXCollections.observableArrayList(
//                        "Black", "Blue", "Red", "Green", "Brown", "Orange", "Kornelowy"
//                ));
//        // Select the first option by default
//        colorChooser.getSelectionModel().selectFirst();
//
//        colorChooser.getSelectionModel().
//                selectedIndexProperty().addListener(
//                (ChangeListener) (ov, old, newval) -> {
//                    Integer idx = (Integer) newval;
//                    Color newColor;
//                    switch (idx) {
//                        case 0:
//                            newColor = Color.BLACK;
//                            break;
//                        case 1:
//                            newColor = Color.BLUE;
//                            break;
//                        case 2:
//                            newColor = Color.RED;
//                            break;
//                        case 3:
//                            newColor = Color.GREEN;
//                            break;
//                        case 4:
//                            newColor = Color.BROWN;
//                            break;
//                        case 5:
//                            newColor = Color.ORANGE;
//                            break;
//                        case 6:
//                            newColor = Color.rgb(100, 150, 100);
//                            break;
//                        default:
//                            newColor = Color.BLACK;
//                            break;
//                    }
//
//                    graphicsContext.setStroke(newColor);
//
//                });
//        colorChooser.setTranslateX(5);
//
//
//        //PEN SIZE
//        ChoiceBox<String> sizeChooser = new ChoiceBox<>(
//                FXCollections.observableArrayList(
//                        "1", "2", "3"
//                ));
//
//        // Select the first option by default
//        sizeChooser.getSelectionModel().select(1);
//        sizeChooser.getSelectionModel()
//                .selectedIndexProperty().addListener(
//                (ChangeListener) (ov, old, newval) -> {
//                    Number idx = (Number) newval;
//
//                    switch (idx.intValue()) {
//                        case 0:
//                            graphicsContext.setLineWidth(1);
//                            break;
//                        case 1:
//                            graphicsContext.setLineWidth(15);
//                            break;
//                        case 2:
//                            graphicsContext.setLineWidth(30);
//                            break;
//                        default:
//                            graphicsContext.setLineWidth(4);
//                            break;
//                    }
//                });
//        sizeChooser.setTranslateX(15);
//
//
//        //MOUSE PRESSED
//        canvas.addEventHandler(MouseEvent.MOUSE_PRESSED,
//                (MouseEvent event) -> {
//                    graphicsContext.beginPath();
//                    graphicsContext.moveTo(
//                            event.getX(), event.getY()
//                    );
//                    graphicsContext.stroke();
//                    System.out.println("MOUSE_PRESSED!");
//
//                });
//
//        canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED,
//                (MouseEvent event) -> {
//                    graphicsContext.lineTo(
//                            event.getX(), event.getY()
//                    );
//                    graphicsContext.stroke(); // coord x,y when drag mouse
//                });
//
//        canvas.addEventHandler(MouseEvent.MOUSE_RELEASED,
//                (MouseEvent event) -> {
//                    SnapshotParameters params = new SnapshotParameters();
//                    params.setFill(Color.TRANSPARENT);
//
//                    canvas.getGraphicsContext2D()
//                            .drawImage(canvas.snapshot(params, new WritableImage((int) screenWidth, (int) screenHeight)), screenWidth, screenHeight);
//                    image = canvas.snapshot(new SnapshotParameters(), new WritableImage((int) screenWidth, (int) screenHeight));
//
//                });
//
//        HBox buttonBox = new HBox();
//        buttonBox.getChildren().addAll(colorChooser,
//                sizeChooser,
//                resetButton);
//
//        initDraw(graphicsContext, canvas.getLayoutX(),
//                canvas.getLayoutY());
//
//        BorderPane container = new BorderPane();
//        container.setTop(buttonBox);
//
//        container.setCenter(canvas);
//
//        root.getChildren().add(container);
//        Scene scene = new Scene(root,
//                rect.getHeight(), rect.getWidth());
//        primaryStage.setTitle("JavaFX Draw");
//        primaryStage.setScene(scene);
//        primaryStage.show();
//    }
//
//
//    private void initDraw(GraphicsContext gc,
//                          double x, double y) {
//        double canvasWidth = gc.getCanvas().getWidth();
//        double canvasHeight = gc.getCanvas().getHeight();
//
//        gc.fill();
//        gc.strokeRect(
//                x,             //x of the upper left corner
//                y,             //y of the upper left corner
//                canvasWidth,   //width of the rectangle
//                canvasHeight); //height of the rectangle
//
//    }
//
//    public static void main(String[] args) {
//        Application.launch(JavaFXDraw.class, args);
//    }
    private WritableImage snapshot;

    @Override
    public void start(Stage primaryStage) {

        Canvas canvas = new Canvas(400, 400);
        final GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        initDraw(graphicsContext);

        VBox vBox = new VBox();

        StackPane stackPane = new StackPane();

        stackPane.getChildren().add(canvas);


        vBox.getChildren().add(stackPane);
        Button button = addButtons();
        vBox.getChildren().add(button);
        Scene scene = new Scene(vBox, 400, 430);
        primaryStage.setTitle("Digit recognizer");
        primaryStage.setScene(scene);
        primaryStage.show();


        canvas.addEventHandler(MouseEvent.MOUSE_PRESSED,
                event -> {
                    graphicsContext.beginPath();
                    graphicsContext.moveTo(event.getX(), event.getY());
                    graphicsContext.stroke();
                });

        canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED,
                event -> {
                    graphicsContext.lineTo(event.getX(), event.getY());
                    graphicsContext.stroke();
                });

        canvas.addEventHandler(MouseEvent.MOUSE_RELEASED,
                event -> {
                    SnapshotParameters parameters = new SnapshotParameters();
                    parameters.setFill(Color.TRANSPARENT);
                    this.snapshot = canvas.snapshot(parameters, new WritableImage((int) scene.getWidth(), (int) scene.getHeight()));
                });

    }

    private Button addButtons() {
        Button saveButton = new Button("Save image");
        saveButton.setOnAction(event -> {
            System.out.println("Saving!");
            if (snapshot != null) {
                saveFile(snapshot);
            } else {
                System.out.println("Image is empty!");
            }
        });
        return saveButton;
    }

    private void saveFile(WritableImage snapshot) {
        File file = new File("saved.png");
        if (snapshot != null) {
            try {
                ImageIO.write(SwingFXUtils.fromFXImage(snapshot, null), "png", file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void initDraw(GraphicsContext gc) {
        double canvasWidth = gc.getCanvas().getWidth();
        double canvasHeight = gc.getCanvas().getHeight();

        gc.setFill(Color.LIGHTGRAY);
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(10);

        gc.fill();
        gc.strokeRect(0, 0, canvasWidth, canvasHeight);

        gc.setFill(Color.RED);
        gc.setStroke(Color.BLUE);
        gc.setLineWidth(1);
    }
}