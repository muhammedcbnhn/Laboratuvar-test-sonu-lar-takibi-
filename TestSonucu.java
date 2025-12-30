// src/model/TestSonucu.java

package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TestSonucu {

    private final StringProperty testAdi;
    private final StringProperty hastaAdi;
    private final StringProperty sonucDegeri;
    private final StringProperty referansAraligi;
    private final StringProperty birim;
    private final StringProperty durum; // Normal, Yüksek, Düşük

    public TestSonucu(String testAdi, String hastaAdi, String sonucDegeri, String referansAraligi, String birim) {
        this.testAdi = new SimpleStringProperty(testAdi);
        this.hastaAdi = new SimpleStringProperty(hastaAdi);
        this.sonucDegeri = new SimpleStringProperty(sonucDegeri);
        this.referansAraligi = new SimpleStringProperty(referansAraligi);
        this.birim = new SimpleStringProperty(birim);

        // Basit bir durum hesaplama (Bu gerçekte daha karmaşık olacaktır)
        this.durum = new SimpleStringProperty(rastgeleDurumBelirle(sonucDegeri));
    }

    private String rastgeleDurumBelirle(String deger) {
        // Bu kısım normalde gerçek değer karşılaştırmasına göre yapılmalıdır.
        // Şimdilik sadece göstermek için rastgele bir durum atıyoruz.
        String[] durumlar = {"Normal", "Yüksek", "Düşük"};
        int index = (int) (Math.random() * durumlar.length);
        return durumlar[index];
    }

    // Getter metotları (JavaFX TableView için StringProperty kullanmak önemlidir)
    public StringProperty testAdiProperty() { return testAdi; }
    public StringProperty hastaAdiProperty() { return hastaAdi; }
    public StringProperty sonucDegeriProperty() { return sonucDegeri; }
    public StringProperty referansAraligiProperty() { return referansAraligi; }
    public StringProperty birimProperty() { return birim; }
    public StringProperty durumProperty() { return durum; }

    // Değerleri almak için standart getter'lar (zorunlu olmasa da iyidir)
    public String getTestAdi() { return testAdi.get(); }
    public String getHastaAdi() { return hastaAdi.get(); }
    public String getSonucDegeri() { return sonucDegeri.get(); }
    public String getReferansAraligi() { return referansAraligi.get(); }
    public String getBirim() { return birim.get(); }
    public String getDurum() { return durum.get(); }
}