module kozmikoda.utilitytoolbox {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.jfoenix;


    opens kozmikoda.utilitytoolbox to javafx.fxml;
    exports kozmikoda.utilitytoolbox;
}