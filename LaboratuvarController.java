import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import java.io.*;
import java.util.Scanner;

public class LaboratuvarController {

    @FXML private TableView<TestSonucu> tableViewSonuclar;
    @FXML private TableColumn<TestSonucu, String> colTestAdi;
    @FXML private TableColumn<TestSonucu, String> colHastaAdi;
    @FXML private TableColumn<TestSonucu, String> colSonucDegeri;
    @FXML private TableColumn<TestSonucu, String> colReferansAraligi;
    @FXML private TableColumn<TestSonucu, String> colBirim;
    @FXML private TableColumn<TestSonucu, String> colDurum;

    @FXML private TextField txtTestAdi;
    @FXML private TextField txtHastaAdi;
    @FXML private TextField txtSonucDegeri;
    @FXML private TextField txtReferansAraligi;
    @FXML private TextField txtBirim;
    @FXML private Label lblDurumMesaji;

    private ObservableList<TestSonucu> sonuclarListesi;
    private final String DOSYA_ADI = "veriler.txt";

    @FXML
    public void initialize() {
        sonuclarListesi = FXCollections.observableArrayList();


        colTestAdi.setCellValueFactory(new PropertyValueFactory<>("testAdi"));
        colHastaAdi.setCellValueFactory(new PropertyValueFactory<>("hastaAdi"));
        colSonucDegeri.setCellValueFactory(new PropertyValueFactory<>("sonucDegeri"));
        colReferansAraligi.setCellValueFactory(new PropertyValueFactory<>("referansAraligi"));
        colBirim.setCellValueFactory(new PropertyValueFactory<>("birim"));
        

        colDurum.setCellValueFactory(new PropertyValueFactory<>("durum"));
        
        colDurum.setCellFactory(column -> {
            return new TableCell<TestSonucu, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);

                    if (empty || getIndex() >= getTableView().getItems().size()) {
                        setText(null);
                        setStyle("");
                    } else {

                        TestSonucu mevcutSatir = getTableView().getItems().get(getIndex());
                        

                        String dogruDurum = durumHesapla(mevcutSatir.getSonucDegeri(), mevcutSatir.getReferansAraligi());
                        setText(dogruDurum);


                        if (dogruDurum.contains("Düşük") || dogruDurum.contains("Yüksek")) {
             
                            setStyle("-fx-text-fill: red; -fx-font-weight: bold;"); 
                        
                        } else if (dogruDurum.contains("Normal")) {
                 
                            setStyle("-fx-text-fill: green; -fx-font-weight: bold;"); 
                        
                        } else {
  
                            setStyle("-fx-text-fill: black;");
                        }
                    }
                }
            };
        });

        tableViewSonuclar.setItems(sonuclarListesi);
        verileriYukle(); 

        if (sonuclarListesi.isEmpty()) {
            sonuclarListesi.add(new TestSonucu("Hemoglobin", "Ayşe Yılmaz", "13.5", "12.0-15.0", "g/dL"));
            verileriKaydet();
        }
        lblDurumMesaji.setText("Hazır.");
    }

  
    private String durumHesapla(String sonucStr, String referansStr) {
        try {
            if (sonucStr == null || sonucStr.trim().isEmpty()) return "Belirsiz";
            double sonuc = Double.parseDouble(sonucStr.trim().replace(",", ".")); 
            
            if (referansStr == null || !referansStr.contains("-")) return "Belirsiz";
            String[] parcalar = referansStr.split("-");
            double min = Double.parseDouble(parcalar[0].trim());
            double max = Double.parseDouble(parcalar[1].trim());

            if (sonuc < min) return "Düşük";
            if (sonuc > max) return "Yüksek";
            return "Normal";
            
        } catch (Exception e) {
            return "Belirsiz"; 
        }
    }

    @FXML
    private void sonucEkle() {
        String t = txtTestAdi.getText().trim();
        String h = txtHastaAdi.getText().trim();
        String s = txtSonucDegeri.getText().trim();
        String r = txtReferansAraligi.getText().trim();
        String b = txtBirim.getText().trim();

        if (t.isEmpty() || h.isEmpty() || s.isEmpty() || r.isEmpty() || b.isEmpty()) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("Lütfen tüm alanları doldurunuz.");
            alert.showAndWait();
            return;
        }

        sonuclarListesi.add(new TestSonucu(t, h, s, r, b));
        verileriKaydet(); 
        
        txtTestAdi.clear(); txtHastaAdi.clear(); txtSonucDegeri.clear(); txtReferansAraligi.clear(); txtBirim.clear();
        lblDurumMesaji.setText("Eklendi.");
    }

    @FXML
    private void seciliyiSil() {
        TestSonucu secili = tableViewSonuclar.getSelectionModel().getSelectedItem();
        if (secili != null) {
            sonuclarListesi.remove(secili);
            verileriKaydet(); 
            lblDurumMesaji.setText("Silindi.");
        }
    }

    @FXML
    private void sonuclariYazdir() {
        System.out.println("--- RAPOR ---");
        for (TestSonucu item : sonuclarListesi) {
            String d = durumHesapla(item.getSonucDegeri(), item.getReferansAraligi());
            System.out.println(item.getTestAdi() + " -> " + d);
        }
    }

    private void verileriKaydet() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(DOSYA_ADI))) {
            for (TestSonucu s : sonuclarListesi) {
                writer.write(s.getTestAdi() + ";" + s.getHastaAdi() + ";" + s.getSonucDegeri() + ";" + s.getReferansAraligi() + ";" + s.getBirim());
                writer.newLine();
            }
        } catch (IOException e) { e.printStackTrace(); }
    }

    private void verileriYukle() {
        File dosya = new File(DOSYA_ADI);
        if (!dosya.exists()) return;
        try (Scanner scanner = new Scanner(dosya)) {
            while (scanner.hasNextLine()) {
                String satir = scanner.nextLine();
                if(satir.trim().isEmpty()) continue;
                String[] p = satir.split(";");
                if (p.length >= 5) sonuclarListesi.add(new TestSonucu(p[0], p[1], p[2], p[3], p[4]));
            }
        } catch (Exception e) { e.printStackTrace(); }
    }
}
