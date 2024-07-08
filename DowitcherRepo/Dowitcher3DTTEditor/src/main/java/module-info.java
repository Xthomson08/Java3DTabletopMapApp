module edu.augustana.Dowitcher3DTTEditor {
    requires javafx.controls;
    requires javafx.fxml;
	requires javafx.graphics;
	requires java.desktop;
	requires com.google.gson;
	requires javafx.base;

    opens edu.augustana.Dowitcher3DTTEditor to javafx.fxml;
    opens edu.augustana.Dowitcher3DTTEditor.datamodel to com.google.gson;
    exports edu.augustana.Dowitcher3DTTEditor;
}
