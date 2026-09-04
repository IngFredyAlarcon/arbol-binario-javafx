module co.edu.uptc {
    requires javafx.controls;
    requires javafx.fxml;

    opens co.edu.uptc to javafx.fxml;
    opens co.edu.uptc.presentation.controller to javafx.fxml;
    exports co.edu.uptc;
}
