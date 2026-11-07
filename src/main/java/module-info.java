module org.example.tarea_fonda_fxml {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires net.synedra.validatorfx;

    opens org.example.tarea_fonda_fxml to javafx.fxml;
    exports org.example.tarea_fonda_fxml;
}