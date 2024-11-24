module com.polimerconsumer.gestures24 {
    requires javafx.controls;
    requires javafx.fxml;
    requires kotlin.stdlib;
    requires java.desktop;


    opens com.polimerconsumer.gestures24 to javafx.fxml;
    exports com.polimerconsumer.gestures24;
}