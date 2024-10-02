module com.example.imtired {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;


    opens com.example.imtired to javafx.fxml;
    exports com.example.imtired;
}