# Vaatimusmäärittely

## Sovelluksen tarkoitus
Tämän sovelluksen avulla käyttäjä voi seurata omia tulojaan ja menojaan. Sovellukseen saatetaan lisätä mahdollisuus, että käyttäjällä voi olla useita toisistaan erillisiä budjetteja, joista jokaiseen liittyy oma tapahtumien seuranta. Sovellusta voi käyttää seurannan lisäksi myös talouden suunnitteluun, merkitsemällä sovellukseen toteutuneiden tapahtumien sijasta suunnitellut tai odotettavissa olevat tulot ja menot.


## Käyttäjät
Sovelluksella on vain yksi käyttäjärooli eli normaali käyttäjä. Sovelluksella voi alkuvaiheessa olla myös vain yksi käyttäjä per asennettu sovellus. Sovelluksesta saatetaan myöhemmin tehdä versio, jossa voi olla useita käyttäjiä, ja tällöin voi olla hyödyllistä ottaa käyttöön peruskäyttäjää laajemmilla oikeuksilla varustettuja käyttäjärooleja, joilla on oikeus nähdä tai hallinnoida määriteltyjä muiden käyttäjien tietoja. 


## Toiminnallisuudet

### Yleisnäkymä
- [x] Yleisnäkymä avautuu käyttäjän käynnistäessä sovelluksen
- [x] Käyttäjä näkee, kuinka paljon hänellä on tuloja ja menoja, ja taloutensa tasapainon (tulot - menot).
- [x] Talouden tasapaino -mittari näyttää tasapainon punaisella, jos se on miinuksella, ja vihreällä, jos se on plussalla.
- [x] Näkymässä on tapahtumien summat kategorioittain.
- [x] Näkymässä on piiraskaavio, joka näyttää menojen jakautumisen eri kategorioihin prosentuaalisina osuuksina.
- [x] Näkymästä voi siirtyä tarkastelemaan tapahtumia kategorioittain, kaikkia tapahtumia, tai lisäämään tulon tai menon.

### Tarkastelunäkymä kaikille tapahtumille
- [x] Tapahtumien rivit tulostuvat näytölle tapahtumajärjestyksessä, uusin ensin. 
- [x] Käyttäjä voi siirtyä lisäämään tapahtumiin uuden rivin.
- [x] Käyttäjä voi siirtyä muokkaamaan tapahtumaa. 
- [x] Käyttäjä voi poistaa tapahtuman. 

### Tarkastelunäkymä yhden kategorian tapahtumille
- [x] Näkymä tulostaa näytölle halutun kategorian tapahtumat.
- [x] Kategorian tapahtumien rivit tulostuvat näytölle tapahtumajärjestyksessä, uusin ensin. 
- [x] Käyttäjä voi siirtyä lisäämään tapahtumiin uuden rivin.
- [x] Käyttäjä voi siirtyä muokkaamaan tapahtumaa. 
- [x] Käyttäjä voi poistaa tapahtuman. 

### Tapahtuman lisääminen
- [x] Käyttäjä voi lisätä tapahtumiin tulorivejä ja menorivejä. 
- [x] Tulotapahtumille tulee automaattisesti tulokategoria. 
- [x] Menotapahtumille käyttäjä valitsee listalta kategorian.
- [x] Kummallekin tyypille käyttäjä syöttää tarkemman kuvauksen, halutun summan ja päivämäärän.
- [x] Tapahtuman lisäämisen voi perua ilman että mitään tallennetaan.
- [x] Lisäyslomakkeen kentät tyhjenevät, kun lomakkeelta poistutaan.

### Tapahtuman muokkaaminen ja poistaminen
- [x] Käyttäjä voi muuttaa mitä tahansa rivin tietoa. 
- [x] Käyttäjä voi poistaa minkä tahansa rivin. 
- [x] Tapahtuman muokkaamisen jälkeen ohjelma palaa tapahtumalistaan, josta hän siirtyi muokkauslomakkeelle.
- [x] Jos tapahtuman muokkaamiseen siirryttiin kategorian tapahtumalistauksesta, niin ohjelma palaa kyseisen tapahtuman kategorian mukaiseen listaukseen, eli jos kategoria on vaihdettu, niin ohjelma palaa uuden kategorian listaukseen.
- [x] Tapahtuman muokkaamisen voi perua ilman että mitään muutoksia tallennetaan.

### Tietojen tallennus
- [x] Tapahtumien tiedot tallennetaan tietokantaan. 

## Jatkokehitysideoita
Sovellusta voidaan täydentää myöhemmin esimerkiksi seuraavilla ominaisuuksilla ja toiminnallisuuksilla.
* Käyttäjä voi rajata näkyviin tapahtumat myös haluamansa ajankohdan mukaan.
* Mahdollisuus tyhjentää kerralla koko tapahtumalistaus (ja/tai budjetti) eli kaikki sen rivit.
* Mahdollisuus lisätä sovellukseen tapahtumien kanssa samanaikaisesti budjetin tai useita eri budjetteja, joihin voi liittää oman toteutuneiden tapahtumien seurannan. Näin sovelluksen avulla voisi samaan aikaan sekä suunnitella että seurata rahankäyttöä.
* Budjettiraportti näyttämään kategorioittain, kuinka paljon kategorialle budjetoidusta rahasta on käytetty ja käyttämättä.
* Mahdollisuus lisätä useampia  tapahtumaseurantoja (ja budjetteja), muokata niiden tietoja ja poistaa niitä. 
* Mahdollisuus lisätä useampia käyttäjiä, joilla jokaisella on oma tapahtumaseurantansa (ja budjettinsa).
* Usean käyttäjän sovellukseen toiminto uuden käyttäjän lisäämiseen, käyttäjien tunnistaminen, sisäänkirjautuminen ja uloskirjautuminen.
* Usean käyttäjän sovellukseen käyttäjille mahdollisuus muokata omia tietojaan esim. salasanaa.
* Usean käyttäjän sovellukseen käyttäjille mahdollisuus poistaa oma tunnuksensa ja kaikki siihen liittyvät tiedot.
* Usean käyttäjän sovellukseen mahdollisuus jakaa tietoja toisten käyttäjien kanssa.
* Jos sovellukseen lisätään useampia käyttäjiä, voi olla mielekästä tehdä jonkinlainen ylläpitäjän rooli, joka voi esimerkiksi hallinnoida kaikkia käyttäjätunnuksia ja nähdä tilastoja sovelluksen käytöstä. 
* Usean käyttäjän sovelluksessa voisi olla hyötyä jonkinlaisesta koordinaattoriroolista, jolla on oikeus hallinnoida oman ryhmänsä budjetteja laajemmin kuin peruskäyttäjällä. 
