# Budjetointisovellus BudgetApp 

Budjetointisovellus BudgetApp on harjoitustyö Helsingin yliopiston tietojenkäsittelytieteen kurssille ohjelmistotekniikka.

Sovelluksen avulla käyttäjä voi luoda oman budjetin ja seurata toteutuneita kuluja. 

## Dokumentaatio

[Vaatimusmäärittely](dokumentointi/vaatimusmaarittely.md)

[Työaikakirjanpito](dokumentointi/tyoaikakirjanpito.md)

## Komentorivitoiminnot

### Testaus
Testit suoritetaan komennolla
```
mvn test 
```

Testikattavuusraportti luodaan komennolla 
```
mvn jacoco:report
```

Kattavuusraporttia voi tarkastella avaamalla selaimella tiedosto *target/site/jacoco/index.html*.

### Suoritettavan jarin generointi
Suoritettava jar-tiedosto generoidaan komennolla
```
mvn package
```
Komento luo hakemistoon *target* jar-tiedoston BudgetApp-1.0-SNAPSHOT.jar. Jar-tiedoston suorittaminen vaatii, että koneelle on asennettu Javan versio 1.8. Tiedoston voi suorittaa komennolla
```
java -jar target/BudgetApp-1.0-SNAPSHOT.jar
```

### JavaDoc
JavaDocia ei ole vielä lisätty.

### Checkstyle
Tiedoston checkstyle.xml määrittelemät tarkistukset suoritetaan komennolla
```
mvn jxr:jxr checkstyle:checkstyle
```
Mahdollisia virheilmoituksia voi tarkastella avaamalla selaimella tiedosto *target/site/checkstyle.html*.
