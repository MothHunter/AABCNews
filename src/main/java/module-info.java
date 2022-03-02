module at.ac.fhcampuswien.aabcnews {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens at.ac.fhcampuswien.aabcnews to javafx.fxml;
    exports at.ac.fhcampuswien.aabcnews;
}