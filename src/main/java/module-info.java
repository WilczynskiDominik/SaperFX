module com.example.saperfx {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.saperfx to javafx.fxml;
    exports com.example.saperfx;
}