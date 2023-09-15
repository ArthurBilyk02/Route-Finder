module com.example.route_finding_algorithm {
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.web;

	requires org.controlsfx.controls;
	requires com.dlsc.formsfx;
	requires org.kordamp.ikonli.javafx;
	requires org.kordamp.bootstrapfx.core;
	requires com.almasb.fxgl.all;

	opens com.example.route_finding_algorithm to javafx.fxml;
	exports com.example.route_finding_algorithm;
}