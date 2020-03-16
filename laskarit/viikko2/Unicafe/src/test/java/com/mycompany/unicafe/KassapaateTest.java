package com.mycompany.unicafe;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class KassapaateTest {
    
    Kassapaate kassa;
    Maksukortti korttiRahakas;
    Maksukortti korttiRahaton;
    
    @Before
    public void setUp() {
        kassa = new Kassapaate();
        korttiRahakas = new Maksukortti(500);
        korttiRahaton = new Maksukortti(0);
    }        

    
    @Test
    public void luodunKassapaatteenRahamaaraOikein() {
        assertEquals(100000, kassa.kassassaRahaa());
    }
    
    @Test
    public void lounaitaMyytyAlussa0() {
        assertEquals(0, kassa.edullisiaLounaitaMyyty()+kassa.maukkaitaLounaitaMyyty());
    }
    
    
    @Test
    public void edullisenLounaanKateisostoKasvattaaKassanRahamaaraaOikeinKunMaksuOnRiittava() {
        kassa.syoEdullisesti(500);
        assertEquals(100240, kassa.kassassaRahaa());
    }
    
    @Test
    public void maukkaanLounaanKateisostoKasvattaaKassanRahamaaraaOikeinKunMaksuOnRiittava() {
        kassa.syoMaukkaasti(500);
        assertEquals(100400, kassa.kassassaRahaa());       
    }
    
    
    @Test
    public void edullisenLounaanKateisostostaSaaVaihtorahanOikeinKunMaksuOnRiittava() {
        assertEquals(260, kassa.syoEdullisesti(500));
    }
    
    @Test
    public void maukkaanLounaanKateisostostaSaaVaihtorahanOikeinKunMaksuOnRiittava() {
        assertEquals(100, kassa.syoMaukkaasti(500));
    }
    
    
    @Test
    public void edullisenLounaanKateisostoKasvattaaMyytyjenLounaidenMaaraaOikeinKunMaksuOnRiittava() {
        kassa.syoEdullisesti(500);
        assertEquals(1, kassa.edullisiaLounaitaMyyty()+kassa.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void maukkaanLounaanKateisostoKasvattaaMyytyjenLounaidenMaaraaOikeinKunMaksuOnRiittava() {
        kassa.syoMaukkaasti(500);
        assertEquals(1, kassa.edullisiaLounaitaMyyty()+kassa.maukkaitaLounaitaMyyty());
    }
    

    @Test
    public void edullisenLounaanKateisostoEiKasvataKassanRahamaaraaJosMaksuEiOleRiittava() {
        kassa.syoEdullisesti(200);
        assertEquals(100000, kassa.kassassaRahaa());        
    }
    
    @Test
    public void maukkaanLounaanKateisostoEiKasvataKassanRahamaaraaJosMaksuEiOleRiittava() {
        kassa.syoMaukkaasti(200);
        assertEquals(100000, kassa.kassassaRahaa());
    }
    
    
    @Test
    public void edullisenLounaanKateisostoPalauttaaKokoMaksunVaihtorahanaJosMaksuEiOleRiittava() {
        assertEquals(200, kassa.syoEdullisesti(200));
    }
    
    @Test
    public void maukkaanLounaanKateisostoPalauttaaKokoMaksunVaihtorahanaJosMaksuEiOleRiittava() {
        assertEquals(200, kassa.syoMaukkaasti(200));
    }
    
    
    @Test
    public void edullisenLounaanKateisostoEiKasvataMyytyjenLounaidenMaaraaJosMaksuEiOleRiittava() {
        kassa.syoEdullisesti(200);
        assertEquals(0, kassa.edullisiaLounaitaMyyty()+kassa.maukkaitaLounaitaMyyty());   
    }

    @Test
    public void maukkaanLounaanKateisostoEiKasvataMyytyjenLounaidenMaaraaJosMaksuEiOleRiittava() {
        kassa.syoMaukkaasti(200);
        assertEquals(0, kassa.edullisiaLounaitaMyyty()+kassa.maukkaitaLounaitaMyyty());
    }
    
    
    @Test
    public void edullisenLounaanKorttiostoVeloittaaSummanKortiltaJosKortillaTarpeeksiRahaa() {
        kassa.syoEdullisesti(korttiRahakas);
        assertEquals(260, korttiRahakas.saldo());
    }
    
    @Test
    public void maukkaanLounaanKorttiostoVeloittaaSummanKortiltaJosKortillaTarpeeksiRahaa() {
        kassa.syoMaukkaasti(korttiRahakas);
        assertEquals(100, korttiRahakas.saldo());
    }
    
    
    @Test
    public void edullisenLounaanKorttiostoPalauttaaTrueJosKortillaTarpeeksiRahaa() {
        assertEquals(true, kassa.syoEdullisesti(korttiRahakas));
    }
    
    @Test
    public void maukkaanLounaanKorttiostoPalauttaaTrueJosKortillaTarpeeksiRahaa() {
        assertEquals(true, kassa.syoMaukkaasti(korttiRahakas));
    }
    
    
    @Test
    public void edullisenLounaanKorttiostoKasvattaaMyytyjenLounaidenMaaraaOikeinJosKortillaTarpeeksiRahaa() {
        kassa.syoEdullisesti(korttiRahakas);
        assertEquals(1, kassa.edullisiaLounaitaMyyty()+kassa.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void maukkaanLounaanKorttiostoKasvattaaMyytyjenLounaidenMaaraaOikeinJosKortillaTarpeeksiRahaa() {
        kassa.syoMaukkaasti(korttiRahakas);
        assertEquals(1, kassa.edullisiaLounaitaMyyty()+kassa.maukkaitaLounaitaMyyty());
    }
    
    
    @Test
    public void edullisenLounaanKorttiostoEiVeloitaKorttiaJosKortillaEiOleTarpeeksiRahaa() {
        kassa.syoEdullisesti(korttiRahaton);
        assertEquals(0, korttiRahaton.saldo());
    }
    
    @Test
    public void maukkaanLounaanKorttiostoEiVeloitaKorttiaJosKortillaEiOleTarpeeksiRahaa() {
        kassa.syoMaukkaasti(korttiRahaton);
        assertEquals(0, korttiRahaton.saldo());
    }
    
    
    @Test
    public void edullisenLounaanKorttiostoEiKasvataMyytyjenLounaidenMaaraaJosKortillaEiOleTarpeeksiRahaa() {
        kassa.syoEdullisesti(korttiRahaton);
        assertEquals(0, kassa.edullisiaLounaitaMyyty()+kassa.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void maukkaanLounaanKorttiostoEiKasvataMyytyjenLounaidenMaaraaJosKortillaEiOleTarpeeksiRahaa() {
        kassa.syoMaukkaasti(korttiRahaton);
        assertEquals(0, kassa.edullisiaLounaitaMyyty()+kassa.maukkaitaLounaitaMyyty());
    }
    
    
    @Test
    public void edullisenLounaanKorttiostoPalauttaaFalseJosKortillaEiOleTarpeeksiRahaa() {
        assertEquals(false, kassa.syoEdullisesti(korttiRahaton));
    }
    
    @Test
    public void maukkaanLounaanKorttiostoPalauttaaFalseJosKortillaEiOleTarpeeksiRahaa() {
        assertEquals(false, kassa.syoMaukkaasti(korttiRahaton));
    }
    
    
    @Test
    public void edullisenLounaanKorttiostoEiMuutaKassanRahamaaraa() {
       kassa.syoEdullisesti(korttiRahakas);
       kassa.syoEdullisesti(korttiRahaton);
       assertEquals(100000, kassa.kassassaRahaa());
    }
    
    @Test
    public void maukkaanLounaanKorttiostoEiMuutaKassanRahamaaraa() {
       kassa.syoMaukkaasti(korttiRahakas);
       kassa.syoMaukkaasti(korttiRahaton);
       assertEquals(100000, kassa.kassassaRahaa());
    }
    
    
    @Test
    public void kortinLatausMuuttaaKortinSaldoaOikeinKunLataussummaPositiivinen() {
        kassa.lataaRahaaKortille(korttiRahakas, 500);
        assertEquals(1000, korttiRahakas.saldo());
    }
    
    @Test
    public void kortinLatausEiMuutaSaldoaKunLataussummaNegatiivinen() {
        kassa.lataaRahaaKortille(korttiRahakas, -100);
        assertEquals(500, korttiRahakas.saldo());
    }
    
    @Test
    public void kortinLatausKasvattaaKassanRahamaaraaLadatullaSummalla() {
        kassa.lataaRahaaKortille(korttiRahakas, 500);
        assertEquals(100500, kassa.kassassaRahaa());
    }
    
    @Test
    public void kortinLatausEiMuutaKassanRahamaaraaJosLataussummaNegatiivinen() {
        kassa.lataaRahaaKortille(korttiRahakas, -100);
        assertEquals(100000, kassa.kassassaRahaa());
    }    
    
}
