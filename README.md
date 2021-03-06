# BudgetApp: sovellus oman talouden seurantaan ja suunnitteluun

Budjetointisovellus BudgetApp on harjoitustyö Helsingin yliopiston tietojenkäsittelytieteen kurssille ohjelmistotekniikka.

Sovelluksen avulla käyttäjä voi seurata toteutuneita tulojaan ja menojaan tai suunnitella talouttaan. 

## Dokumentaatio

[Käyttöohje](dokumentaatio/kayttoohje.md)

[Vaatimusmäärittely](dokumentaatio/vaatimusmaarittely.md)

[Arkkitehtuuri](dokumentaatio/arkkitehtuuri.md)

[Testausdokumentti](dokumentaatio/testaus.md)

[Työaikakirjanpito](dokumentaatio/tyoaikakirjanpito.md)

## Releaset

[versio 0.1 (pre-release) - viikko 5](https://github.com/jmlii/ot-harjoitus/releases/tag/v0.1)

[versio 0.2 (pre-release) - viikko 6](https://github.com/jmlii/ot-harjoitus/releases/tag/v0.2)

[versio 1.0 - loppupalautus](https://github.com/jmlii/ot-harjoitus/releases/tag/v1.0)

## Komentorivitoiminnot

### Projektin koodin suorittaminen
Projektin koodin voi suorittaa komentorivillä komennolla
```
mvn compile exec:java -Dexec.mainClass=budgetapp.Main
```
Projektin koodin voi suorittaa myös esim. NetBeansissa.

### Suoritettavan jarin generointi
Suoritettava jar-tiedosto generoidaan komennolla
```
mvn package
```
Komento luo hakemistoon *target* jar-tiedoston *BudgetApp-1.0-SNAPSHOT.jar*. Jar-tiedoston suorittaminen vaatii, että koneelle on asennettu Javan versio 1.8. Tiedoston voi suorittaa komennolla
```
java -jar target/BudgetApp-1.0-SNAPSHOT.jar
```

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

### Javadoc
Javadoc generoidaan komennolla
```
mvn javadoc:javadoc
```
Javadocia voi tarkastella avaamalla selaimella tiedosto *target/site/apidocs/index.html*.

### Checkstyle
Tiedoston [checkstyle.xml](BudgetApp/checkstyle.xml) määrittelemät tarkistukset suoritetaan komennolla
```
mvn jxr:jxr checkstyle:checkstyle
```
Mahdollisia virheilmoituksia voi tarkastella avaamalla selaimella tiedosto *target/site/checkstyle.html*.
