# Vaatimusmäärittely

## Sovelluksen tarkoitus
Tämän budjetointisovelluksen avulla käyttäjä voi luoda oman budjetin ja seurata toteutuneita kuluja. Sovellukseen saatetaan lisätä mahdollisuus, että käyttäjällä voi olla useita toisistaan erillisiä budjetteja, joista jokaiseen liittyy oma kulujen seuranta.


## Käyttäjät
Sovelluksella on vain yksi käyttäjärooli eli normaali käyttäjä. Sovelluksella voi alkuvaiheessa olla myös vain yksi käyttäjä per asennettu sovellus. Sovelluksesta saatetaan myöhemmin tehdä versio, jossa voi olla useita käyttäjiä, ja tällöin voi olla hyödyllistä ottaa käyttöön peruskäyttäjää laajemmilla oikeuksilla varustettuja käyttäjärooleja, joilla on oikeus nähdä tai hallinnoida määriteltyjä muiden käyttäjien tietoja. 


## Suunnitellut toiminnallisuudet

### Tarkastelunäkymä
* Tarkastelunäkymä avautuu käyttäjän käynnistäessä sovelluksen.
* Budjetin rivit tulostuvat näytölle kategorioittan.
* Käyttäjä voi siirtyä lisäämään budjettiin uuden rivin.
* Käyttäjä voi siirtyä muokkaamaan budjetin rivejä.
* Käyttäjä voi poistaa budjetin rivin.
* Käyttäjä näkee, kuinka paljon hänellä on budjetissa rahaa käytettävissä, budjetoituna, budjetoimatta, käytettynä ja jäljellä käytettäväksi.

### Tapahtuman lisääminen
* Käyttäjä voi lisätä tapahtumiin tulorivejä ja menorivejä. (tehty)
* Käyttäjä antaa kategorian, tarkemman kuvauksen ja syöttää halutun summan ja antaa päivämäärän. (tehty)

### Kulutapahtuman muokkaaminen ja poistaminen
* Käyttäjä voi muuttaa mitä tahansa rivin tietoa. (tehty)
* Käyttäjä voi poistaa minkä tahansa rivin. (tehty)

### Budjettikohteen lisääminen
* Käyttäjä voi lisätä budjettiin tulorivejä, joiden perusteella lasketaan budjetille käytettävissä oleva raha.
* Budjetoitavalle rahalle käyttäjä antaa kategorian, tarkemman kuvauksen ja syöttää halutun summan joko budjetoitavaksi tai käytetyksi ja antaa käyttökohteelle käyttöpäivämäärän.


### Budjettikohteen muokkaaminen
* Käyttäjä voi muuttaa mitä tahansa rivin tietoa.


## Jatkokehitysideoita
Sovellusta voidaan täydentää myöhemmin ajan salliessa esimerkiksi seuraavilla ominaisuuksilla ja toiminnallisuuksilla.

* Käyttäjä voi listata budjetin rivit myös käyttöajankohdan mukaan.
* Budjettiraportti näyttää kategorioittain, kuinka paljon kategorialle budjetoidusta rahasta on käytetty ja käyttämättä.
* Mahdollisuus tyhjentää kerralla koko budjetti eli kaikki sen rivit.
* Mahdollisuus lisätä useampia budjetteja, muokata budjettien tietoja ja poistaa budjetteja. 
* Mahdollisuus lisätä useampia käyttäjiä, joilla jokaisella on oma budjettinsa.
* Usean käyttäjän sovellukseen toiminto uuden käyttäjän lisäämiseen, käyttäjien tunnistaminen, sisäänkirjautuminen ja uloskirjautuminen.
* Usean käyttäjän sovellukseen käyttäjille mahdollisuus muokata omia tietojaan esim. salasanaa.
* Usean käyttäjän sovellukseen käyttäjille mahdollisuus poistaa oma tunnuksensa ja kaikki siihen liittyvät tiedot.
* Usean käyttäjän sovellukseen mahdollisuus jakaa budjetteja toisten käyttäjien kanssa.
* Jos sovellukseen lisätään useampia käyttäjiä, voi olla mielekästä tehdä jonkinlainen ylläpitäjän rooli, joka voi esimerkiksi hallinnoida kaikkia käyttäjätunnuksia ja nähdä tilastoja sovelluksen käytöstä. 
* Usean käyttäjän sovelluksessa voisi olla hyötyä jonkinlaisesta koordinaattoriroolista, jolla on oikeus hallinnoida oman ryhmänsä budjetteja laajemmin kuin peruskäyttäjällä. 
