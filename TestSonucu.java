

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TestSonucu {

    private final StringProperty testAdi;
    private final StringProperty hastaAdi;
    private final StringProperty sonucDegeri;
    private final StringProperty referansAraligi;
    private final StringProperty birim;
    private final StringProperty durum; 

    public TestSonucu(String testAdi, String hastaAdi, String sonucDegeri, String referansAraligi, String birim) {
        this.testAdi = new SimpleStringProperty(testAdi);
        this.hastaAdi = new SimpleStringProperty(hastaAdi);
        this.sonucDegeri = new SimpleStringProperty(sonucDegeri);
        this.referansAraligi = new SimpleStringProperty(referansAraligi);
        this.birim = new SimpleStringProperty(birim);

        this.durum = new SimpleStringProperty(rastgeleDurumBelirle(sonucDegeri));
    }

    private String rastgeleDurumBelirle(String deger) {

        String[] durumlar = {"Normal", "Yüksek", "Düşük"};
        int index = (int) (Math.random() * durumlar.length);
        return durumlar[index];
    }

    
    public StringProperty testAdiProperty() { return testAdi; }
    public StringProperty hastaAdiProperty() { return hastaAdi; }
    public StringProperty sonucDegeriProperty() { return sonucDegeri; }
    public StringProperty referansAraligiProperty() { return referansAraligi; }
    public StringProperty birimProperty() { return birim; }
    public StringProperty durumProperty() { return durum; }

    
    public String getTestAdi() { return testAdi.get(); }
    public String getHastaAdi() { return hastaAdi.get(); }
    public String getSonucDegeri() { return sonucDegeri.get(); }
    public String getReferansAraligi() { return referansAraligi.get(); }
    public String getBirim() { return birim.get(); }
    public String getDurum() { return durum.get(); }
}
