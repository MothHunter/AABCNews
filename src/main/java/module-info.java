module at.ac.fhcampuswien.aabcnews {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.google.gson;
    requires okhttp3;
    exports at.ac.fhcampuswien.aabcnews;
    opens at.ac.fhcampuswien.aabcnews to com.google.gson, javafx.fxml;
}