module com.example.imgprocessing {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.imgprocessing to javafx.fxml;
    exports com.example.imgprocessing;
}