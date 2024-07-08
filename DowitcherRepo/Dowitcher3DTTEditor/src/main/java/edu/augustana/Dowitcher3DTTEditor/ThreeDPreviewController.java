package edu.augustana.Dowitcher3DTTEditor;

import edu.augustana.Dowitcher3DTTEditor.datamodel.TerrainMap;
import edu.augustana.Dowitcher3DTTEditor.datamodel.Tile.SHAPETYPE;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Camera;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.TriangleMesh;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;
import javafx.stage.Stage;

public class ThreeDPreviewController {
	private static final float WIDTH = 1400;
	private static final float HEIGHT = 700;
	private double anchorX;
	private double anchorY;
	private double anchorAngleX = 0;
	private double anchorAngleY = 0;
	private final DoubleProperty angleX = new SimpleDoubleProperty(0);
	private final DoubleProperty angleY = new SimpleDoubleProperty(0);
	private double tileSize = 20;

	@SuppressWarnings("incomplete-switch")

	public void startSquare() {

		tileSize = (-16 / 90) * (App.getMap().getWidth() - 10) + 20;
		Stage threeDStage = new Stage();
		TerrainMap map = App.getMap();
		PhongMaterial texture = new PhongMaterial(Color.DARKOLIVEGREEN);
		SmartGroup group = new SmartGroup();
		for (int i = 0; i < map.getWidth(); i++) {
			for (int j = 0; j < map.getHeight(); j++) {
				if (map.getTile(i, j).exists()) {
					double tileHeight = map.getTile(i, j).getZElevation();
					Box tile = new Box(tileSize, tileHeight, tileSize);
					tile.setTranslateX(tileSize * (i - map.getWidth() / 2.0));
					tile.setTranslateY(tileHeight / -2.0);
					tile.setTranslateZ(tileSize * (j - map.getHeight() / 2.0));
					tile.setMaterial(texture);
					if (map.getTile(i, j).getPointy() == true) {
						for (int k = 0; k < 50 + 1; k++) {
							Box pyramids = new Box(tileSize - k, 1, tileSize - k);
							pyramids.setTranslateX(tileSize * (i - map.getWidth() / 2.0));
							pyramids.setTranslateY(-(tileHeight + k));
							pyramids.setTranslateZ(tileSize * (j - map.getHeight() / 2.0));
							pyramids.setMaterial(texture);
							group.getChildren().add(pyramids);
						}
					}
					group.getChildren().add(tile);
				}
			}
		}
		Text text1 = new Text();
		text1.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
		text1.setX(-70);
		text1.setY(70);
		text1.setStroke(Color.DARKOLIVEGREEN);
		text1.setText("Mouse/Arrows Control");
		Text text2 = new Text();
		text2.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));
		text2.setX(-112);
		text2.setY(90);
		text2.setStroke(Color.DARKOLIVEGREEN);
		text2.setText("Scroll/NumPad 2 & 8: Zoom in/out");
		Group textgroup = new Group(text1, text2);
		group.getChildren().add(textgroup);
		Camera camera = new PerspectiveCamera();
		Scene scene = new Scene(group, WIDTH, HEIGHT, true);
		scene.setFill(Color.BEIGE);
		scene.setCamera(camera);
		group.setTranslateX(WIDTH / 2.0);
		group.setTranslateY(HEIGHT / 2.0);
		group.setTranslateZ(map.getHeight() * tileSize - 700);

		initMouseControl(group, scene, threeDStage);

		threeDStage.addEventHandler(KeyEvent.KEY_PRESSED, evt -> {
			switch (evt.getCode()) {

			case DOWN:
				group.rotateByX(6);
				break;
			case UP:
				group.rotateByX(-6);
				break;
			case LEFT:
				group.rotateByY(6);
				break;
			case RIGHT:
				group.rotateByY(-6);
				break;
			case NUMPAD2:
				group.translateZProperty().set(group.getTranslateZ() + 16);
				break;
			case NUMPAD8:
				group.translateZProperty().set(group.getTranslateZ() - 16);
				break;
			}
		});

		threeDStage.setScene(scene);
		threeDStage.setTitle("Square 3D preview");
		threeDStage.show();

	}

	@SuppressWarnings({ "unused", "incomplete-switch" })
	public void startHexagonal() {

		tileSize = (-16 / 90) * (App.getMap().getWidth() - 10) + 20;
		SmartGroup group = new SmartGroup();
		Stage threeDStage = new Stage();
		TerrainMap map = App.getMap();
		PhongMaterial texture1 = new PhongMaterial(Color.GOLDENROD);
		PhongMaterial texture2 = new PhongMaterial(Color.DARKGOLDENROD);
		for (int i = 0; i < map.getWidth(); i++) {
			for (int j = 0; j < map.getHeight(); j++) {
				if (map.getTile(i, j).exists()) {
					double tileHeight = map.getTile(i, j).getZElevation();
					for (int s = 0; s < 3; s++) {
						Box HexagoneRectangle = new Box(1.1 * tileSize, 1.1 * tileSize / Math.sqrt(3), tileHeight);
						
						HexagoneRectangle.setTranslateY(tileSize * (i - map.getHeight() / 2.0));
						HexagoneRectangle.setTranslateZ(tileHeight / -2);
						if (i % 2 != 0) {
							HexagoneRectangle.setTranslateX(tileSize * (j - map.getWidth() / 2.0) + tileSize / 2.0);
						} else {
							HexagoneRectangle.setTranslateX(tileSize * (j - map.getWidth() / 2.0));
						}
						HexagoneRectangle.setRotate((60 * s));
						if (i % 2 == 0 ) {
							HexagoneRectangle.setMaterial(texture1);
						} else {
							HexagoneRectangle.setMaterial(texture2);
						}
						group.getChildren().add(HexagoneRectangle);
					}
					if (map.getTile(i, j).getPointy() == true) {
						for (int k = 0; k < 10; k++) {
							for (int n = 0; n < 3; n++) {
								Box miniHexagoneRectangle = new Box((1.1 - 0.1 * k) * tileSize,
										(1.1 - 0.1 * k) * tileSize / Math.sqrt(3), 2);
								miniHexagoneRectangle.setTranslateY(tileSize * (i - map.getHeight() / 2.0));
								miniHexagoneRectangle.setTranslateZ(-(tileHeight + (k * 2)));
								if (i % 2 != 0) {
									miniHexagoneRectangle
											.setTranslateX(tileSize * (j - map.getWidth() / 2.0) + tileSize / 2.0);
								} else {
									miniHexagoneRectangle.setTranslateX(tileSize * (j - map.getWidth() / 2.0));
								}
								miniHexagoneRectangle.setRotate((60 * n));
								if (i % 2 == 0) {
									miniHexagoneRectangle.setMaterial(texture1);
								} else {
									miniHexagoneRectangle.setMaterial(texture2);
								}
								group.getChildren().add(miniHexagoneRectangle);
							}
						}
					}
				}
			}
		}

		Camera camera = new PerspectiveCamera();
		Scene scene = new Scene(group, WIDTH, HEIGHT, true);
		scene.setFill(Color.BEIGE);
		scene.setCamera(camera);
		group.setTranslateX(WIDTH / 2.0);
		group.setTranslateY(HEIGHT / 2.0);
		group.setTranslateZ(map.getHeight() * tileSize - 700);
		initMouseControl(group, scene, threeDStage);
		threeDStage.addEventHandler(KeyEvent.KEY_PRESSED, evt -> {
			switch (evt.getCode()) {
			case DOWN:
				group.rotateByX(+6);
				break;
			case UP:
				group.rotateByX(-6);
				break;
			case LEFT:
				group.rotateByZ(6);
				break;
			case RIGHT:
				group.rotateByZ(-6);
				break;
			case NUMPAD2:
				group.translateZProperty().set(group.getTranslateZ() + 16);
				break;
			case NUMPAD8:
				group.translateZProperty().set(group.getTranslateZ() - 16);
				break;
			}
		});
		threeDStage.setScene(scene);
		threeDStage.setTitle("Hexagon 3D preview");
		threeDStage.show();

	}

	class SmartGroup extends Group {
		Rotate rotate;
		Transform transform = new Rotate();

		void rotateByX(int angle) {
			rotate = new Rotate(angle, Rotate.X_AXIS);
			transform = transform.createConcatenation(rotate);
			this.getTransforms().clear();
			this.getTransforms().addAll(transform);
		}

		void rotateByY(int angle) {
			rotate = new Rotate(angle, Rotate.Y_AXIS);
			transform = transform.createConcatenation(rotate);
			this.getTransforms().clear();
			this.getTransforms().addAll(transform);
		}

		void rotateByZ(int angle) {
			rotate = new Rotate(angle, Rotate.Z_AXIS);
			transform = transform.createConcatenation(rotate);
			this.getTransforms().clear();
			this.getTransforms().addAll(transform);
		}
	}

	private void initMouseControl(SmartGroup group, Scene scene, Stage stage) {
		Rotate xRotate;
		Rotate yRotate;
		if (App.getMap().getTileShapeType() == SHAPETYPE.HEXAGON) {
			group.getTransforms().addAll(xRotate = new Rotate(0, Rotate.X_AXIS),
					yRotate = new Rotate(0, Rotate.Z_AXIS));
		} else {
			group.getTransforms().addAll(xRotate = new Rotate(0, Rotate.X_AXIS),
					yRotate = new Rotate(0, Rotate.Y_AXIS));
		}
		xRotate.angleProperty().bind(angleX);
		yRotate.angleProperty().bind(angleY);

		scene.setOnMousePressed(evt -> {
			anchorX = evt.getSceneX();
			anchorY = evt.getSceneY();
			anchorAngleX = angleX.get();
			anchorAngleY = angleY.get();
		});

		scene.setOnMouseDragged(evt -> {
			angleX.set(anchorAngleX - (anchorY - evt.getSceneY()));
			angleY.set(anchorAngleY + anchorX - evt.getSceneX());
		});

		stage.addEventHandler(ScrollEvent.SCROLL, evt -> {
			double scroll = evt.getDeltaY();
			group.translateZProperty().set(group.getTranslateZ() + scroll);
		});
	}

}
