# Vaatimusmäärittely

## Sovelluksen tarkoitus
Tämän sovelluksen avulla käyttäjä voi seurata omia tulojaan ja menojaan. Sovellukseen saatetaan lisätä mahdollisuus, että käyttäjällä voi olla useita toisistaan erillisiä budjetteja, joista jokaiseen liittyy oma tapahtumien seuranta. Sovellusta voi käyttää seurannan lisäksi myös talouden suunnitteluun, merkitsemällä sovellukseen toteutuneiden tapahtumien sijasta suunnitellut tai odotettavissa olevat tulot ja menot.


## Käyttäjät
Sovelluksella on vain yksi käyttäjärooli eli normaali käyttäjä. Sovelluksella voi alkuvaiheessa olla myös vain yksi käyttäjä per asennettu sovellus. Sovelluksesta saatetaan myöhemmin tehdä versio, jossa voi olla useita käyttäjiä, ja tällöin voi olla hyödyllistä ottaa käyttöön peruskäyttäjää laajemmilla oikeuksilla varustettuja käyttäjärooleja, joilla on oikeus nähdä tai hallinnoida määriteltyjä muiden käyttäjien tietoja. 


## Suunnitellut toiminnallisuudet

### Yleisnäkymä
* Yleisnäkymä avautuu käyttäjän käynnistäessä sovelluksen
* Käyttäjä näkee, kuinka paljon hänellä on tuloja ja menoja, ja taloutensa tasapainon (tulot - menot).
* Näkymässä tapahtumien summat kategorioittain.
* Näkymästä voi siirtyä tarkastelemaan tapahtumia kategorioittain, kaikkia tapahtumia, tai lisäämään tulon tai menon. 

### Tarkastelunäkymä
* Tapahtumien rivit tulostuvat näytölle tapahtumajärjestyksessä, uusin ensin. (tehty)
* Tapahtumat voi listata kategorioittain. (tehty, mutta vain aloitusnäkymästä käsin)
* Käyttäjä voi siirtyä lisäämään tapahtumiin uuden rivin. (tehty)
* Käyttäjä voi siirtyä muokkaamaan tapahtumia. (tehty)
* Käyttäjä voi poistaa tapahtuman. (tehty)

### Tapahtuman lisääminen
* Käyttäjä voi lisätä tapahtumiin tulorivejä ja menorivejä. (tehty)
* Tulotapahtumille tulee automaattisesti tulokategoria. (tehty)
* Menotapahtumille käyttäjä valitsee listalta kategorian ja syöttää tarkemman kuvauksen, halutun summan ja päivämäärän. (tehty)

### Tapahtuman muokkaaminen ja poistaminen
* Käyttäjä voi muuttaa mitä tahansa rivin tietoa. (tehty)
* Käyttäjä voi poistaa minkä tahansa rivin. (tehty)

### Tietojen tallennus
* Tapahtumien tiedot tallennetaan tietokantaan. (tehty)

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
