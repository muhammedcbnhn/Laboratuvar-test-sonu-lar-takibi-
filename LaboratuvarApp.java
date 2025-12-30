package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.net.URL;

public class LaboratuvarApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // Dosya yolunu src kök dizinine göre güncelledik (view/ kaldırıldı)
            URL fxmlLocation = getClass().getResource("/laboratuvar_arayuzu.fxml");

            if (fxmlLocation == null) {
                System.err.println("Hata: FXML dosyası bulunamadı! Lütfen dosyanın 'src' klasörü içinde olduğundan emin olun.");
                return;
            }

            FXMLLoader loader = new FXMLLoader(fxmlLocation);

            // VBox yerine Parent kullanmak daha güvenlidir,
            // FXML'deki en dış eleman ne olursa olsun hata almazsın.
            Parent root = loader.load();

            Scene scene = new Scene(root);

            primaryStage.setScene(scene);
            primaryStage.setTitle("JavaFX Laboratuvar Test Sonuçları Yönetimi");
            primaryStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}