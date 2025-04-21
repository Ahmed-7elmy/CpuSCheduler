module com.os.cpu_scheduler {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.dlsc.formsfx;
    requires java.xml;
    
    exports com.os.cpu_scheduler;  // Export the main package to make the app class accessible
    opens com.os.cpu_scheduler to javafx.fxml;  // Open the package for JavaFX reflection
    opens com.os.cpu_scheduler.controller to javafx.fxml;  // Open the controller package for reflection
    
    // Add this line to allow JavaFX to access your Process class properties
    opens com.os.cpu_scheduler.model to javafx.base;
}