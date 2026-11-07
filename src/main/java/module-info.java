module org.example.tarea_fonda_fxml {
    requires javafx.fxml;
    requires javafx.controls;
    requires com.google.gson;
    requires javafx.graphics;

    opens org.example.tarea_fonda_fxml.model to com.google.gson;
    opens org.example.tarea_fonda_fxml to javafx.graphics;
    opens org.example.tarea_fonda_fxml.controller to javafx.fxml;


    exports org.example.tarea_fonda_fxml;
}