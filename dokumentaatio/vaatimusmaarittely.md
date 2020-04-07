# Vaatimusmäärittely

## Sovelluksen tarkoitus
Tämän budjetointisovelluksen avulla käyttäjä voi luoda oman budjetin ja seurata toteutuneita tuloja ja menoja. Sovellukseen saatetaan lisätä mahdollisuus, että käyttäjällä voi olla useita toisistaan erillisiä budjetteja, joista jokaiseen liittyy oma tapahtumien seuranta.


## Käyttäjät
Sovelluksella on vain yksi käyttäjärooli eli normaali käyttäjä. Sovelluksella voi alkuvaiheessa olla myös vain yksi käyttäjä per asennettu sovellus. Sovelluksesta saatetaan myöhemmin tehdä versio, jossa voi olla useita käyttäjiä, ja tällöin voi olla hyödyllistä ottaa käyttöön peruskäyttäjää laajemmilla oikeuksilla varustettuja käyttäjärooleja, joilla on oikeus nähdä tai hallinnoida määriteltyjä muiden käyttäjien tietoja. 


## Suunnitellut toiminnallisuudet

### Tarkastelunäkymä
* Tarkastelunäkymä avautuu käyttäjän käynnistäessä sovelluksen.
* Tapahtumien rivit tulostuvat näytölle kategorioittain.
* Budjetin rivit tulostuvat näytölle kategorioittan.
* Käyttäjä voi siirtyä lisäämään budjettiin tai tapahtumiin uuden rivin.
* Käyttäjä voi siirtyä muokkaamaan budjetin tai tapahtumien rivejä.
* Käyttäjä voi poistaa budjetin tai taphtumien rivin.
* Käyttäjä näkee, kuinka paljon hänellä on rahaa käytettävissä, käytettynä ja jäljellä käytettäväksi, sekä budjetissa käytettävissä, budjetoituna ja  budjetoimatta.

### Tapahtuman lisääminen
* Käyttäjä voi lisätä tapahtumiin tulorivejä ja menorivejä. (tehty)
* Tulotapahtumille tulee automaattisesti tulokategoria. (tehty)
* Menotapahtumille käyttäjä valitsee listalta kategorian ja syöttää tarkemman kuvauksen, halutun summan ja päivämäärän. (tehty)

### Tapahtuman muokkaaminen ja poistaminen
* Käyttäjä voi muuttaa mitä tahansa rivin tietoa. (tehty)
* Käyttäjä voi poistaa minkä tahansa rivin. (tehty)

### Budjettikohteen lisääminen
* Käyttäjä voi lisätä budjettiin tulorivejä, joiden perusteella lasketaan budjetille käytettävissä oleva raha.
* Budjetoitavalle käyttökohteelle käyttäjä antaa kategorian, tarkemman kuvauksen ja syöttää halutun summan.

### Budjettikohteen muokkaaminen ja poistaminen
* Käyttäjä voi muuttaa mitä tahansa budjettirivin tietoa.
* Käyttäjä voi poistaa minkä tahansa budjettirivin.

### Tietojen tallennus
* Tapahtumien ja budjetin tiedot tallennetaan tietokantaan. (tehty niiltä osin kuin toiminto toteutettu sovelluksessa)

## Jatkokehitysideoita
Sovellusta voidaan täydentää myöhemmin ajan salliessa esimerkiksi seuraavilla ominaisuuksilla ja toiminnallisuuksilla.

* Käyttäjä voi listata tapahtumat myös käyttöajankohdan mukaan.
* Budjettiraportti näyttää kategorioittain, kuinka paljon kategorialle budjetoidusta rahasta on käytetty ja käyttämättä.
* Mahdollisuus tyhjentää kerralla koko budjetti eli kaikki sen rivit.
* Mahdollisuus lisätä useampia budjetteja ja tapahtumaseurantoja, muokata niiden tietoja ja poistaa niitä. 
* Mahdollisuus lisätä useampia käyttäjiä, joilla jokaisella on oma budjettinsa ja tapahtumaseurantansa.
* Usean käyttäjän sovellukseen toiminto uuden käyttäjän lisäämiseen, käyttäjien tunnistaminen, sisäänkirjautuminen ja uloskirjautuminen.
* Usean käyttäjän sovellukseen käyttäjille mahdollisuus muokata omia tietojaan esim. salasanaa.
* Usean käyttäjän sovellukseen käyttäjille mahdollisuus poistaa oma tunnuksensa ja kaikki siihen liittyvät tiedot.
* Usean käyttäjän sovellukseen mahdollisuus jakaa budjetteja toisten käyttäjien kanssa.
* Jos sovellukseen lisätään useampia käyttäjiä, voi olla mielekästä tehdä jonkinlainen ylläpitäjän rooli, joka voi esimerkiksi hallinnoida kaikkia käyttäjätunnuksia ja nähdä tilastoja sovelluksen käytöstä. 
* Usean käyttäjän sovelluksessa voisi olla hyötyä jonkinlaisesta koordinaattoriroolista, jolla on oikeus hallinnoida oman ryhmänsä budjetteja laajemmin kuin peruskäyttäjällä. 
