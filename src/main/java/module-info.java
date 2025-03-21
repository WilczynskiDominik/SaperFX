module com.example.saperfx {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.saperfx to javafx.fxml;
    exports com.example.saperfx;
    exports com.example.saperfx.Saper;
    opens com.example.saperfx.Saper to javafx.fxml;
}