module at.ac.fhcampuswien.aabcnews {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires okhttp3;
    requires com.google.gson;

    opens at.ac.fhcampuswien.aabcnews to javafx.fxml, com.google.gson; // wir müssen die struktur von Programm
    // offen haben/ erlauben
    exports at.ac.fhcampuswien.aabcnews;
}