module com.example.os_cpu_scheduler {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires java.xml;

    opens com.example.os_cpu_scheduler to javafx.fxml;
    exports com.example.os_cpu_scheduler;
}