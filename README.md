# Notes App -- UI Test Automation

## 1. Projekt célja

A projekt célja a kiválasztott **Notes App webalkalmazás** fő
felhasználói funkcióinak automatizált felület tesztelése.

A tesztautomatizálás Selenium WebDriver és JUnit segítségével, Page
Object Model alapú struktúrában készült.

A projekt a web alkalmazás felületének funkcionális ellenőrzésére, a
tesztek újrahasznosíthatóságára, olvashatóságára és automatizált
futtatására fókuszál.

------------------------------------------------------------------------

## 2. Tesztelt alkalmazás

**SUT (System Under Test):**

https://practice.expandtesting.com/notes/app/

A tesztelt alkalmazás a Practice Expand Testing Notes App gyakorló
webalkalmazása.

A tesztek a regisztrációt követően létrehozott tesztfelhasználóval és
tesztadatokkal dolgoznak.

------------------------------------------------------------------------

## 3. Technológiák

  Technológia                                      Verzió
  ----------------------- -------------------------------
  Java                                                 21
  Selenium WebDriver                               4.41.0
  WebDriverManager                                  6.3.3
  JUnit Jupiter                                     6.0.3
  Maven Surefire Plugin                             3.5.4
  Maven                     projekt build / tesztfuttatás
  Chrome / ChromeDriver     WebDriverManager által kezelt

### Alkalmazott tesztelési megoldások

-   Selenium WebDriver alapú UI automatizálás
-   JUnit 5 teszt framework
-   Page Object Model
-   közös `BasePage` és `BaseTest`
-   explicit wait használata
-   dinamikus tesztadat-generálás UUID segítségével
-   CSV alapú adatvezérelt tesztelés
-   Shadow DOM kezelés
-   CRUD műveletek tesztelése
-   kategóriaszűrés és keresés tesztelése

------------------------------------------------------------------------

## 4. Előfeltételek

A projekt futtatásához szükséges:

-   Java JDK 21
-   Maven
-   Google Chrome
-   internetkapcsolat

A ChromeDriver telepítését a projektben használt WebDriverManager
kezeli.

------------------------------------------------------------------------

## 5. Projektstruktúra


### Fontosabb könyvtárak

**`base`**

A közös tesztelési és Page Object funkciókat tartalmazza.

-   `BasePage`
-   `BaseTest`

**`pages`**

A tesztelt alkalmazás oldalait és modal ablakait reprezentáló Page
Object osztályokat tartalmazza.

**`tests`**

Az automatizált teszteseteket tartalmazza.

**`models`**

A tesztadatok objektummodelljeit tartalmazza.

**`utils`**

Segédosztályok, például:

-   CSV olvasás
-   tesztadat-generálás

**`resources`**

A teszteléshez használt adatforrásokat tartalmazza.

------------------------------------------------------------------------

## 6. Automatizált tesztek

A projekt jelenleg 13 automatizált tesztmetódust tartalmaz.

  -----------------------------------------------------------------------------
  Tesztosztály               Teszt                      Cél
  -------------------------- -------------------------- -----------------------
  `OpenHomePageTest`         `openHomePage`             Home oldal megnyitása

  `RegisterTest`             `successfulRegistration`   Regisztráció

  `LoginTest`                `successfulLogin`          Bejelentkezés

  `PrivacyTest`              `privacySettingsTest`      Adatkezelési / Privacy
                                                        funkció

  `AddNoteTest`              `createNewNote`            Új note létrehozása

  `CreateManyNotesTest`      `createManyNotes`          Sorozatos adatbevitel

  `CreateNotesFromCsvTest`   `createNotesFromCsv`       Adatbevitel CSV
                                                        adatforrásból

  `CategoryFilterTest`       `filterNotesByCategory`    Kategóriaszűrés

  `SearchTest`               `searchNote`               Note keresése

  `EditTest`                 `editNote`                 Meglévő note módosítása

  `DeleteTest`               `deleteNote`               Note törlése

  `DeleteTest`               `cancelDeleteNote`         Törlés megszakítása

  `LogoutTest`               `logout`                   Kijelentkezés
  -----------------------------------------------------------------------------

------------------------------------------------------------------------

## 7. Vizsgakövetelmények lefedettsége

  -----------------------------------------------------------------------
  Követelmény                         Lefedettség
  ----------------------------------- -----------------------------------
  Regisztráció                        Automatizált

  Bejelentkezés                       Automatizált

  Adatkezelési nyilatkozat használata Automatizált

  Adatok listázása                    Automatizált

  Több oldalas lista bejárása         N/A -- a tesztelt SUT-ban nincs
                                      ilyen funkció

  Új adat bevitel                     Automatizált

  Ismételt és sorozatos adatbevitel   Automatizált
  adatforrásból                       

  Meglévő adat módosítás              Automatizált

  Adat vagy adatok törlése            Automatizált

  Adatok lementése felületről         N/A -- a tesztelt SUT-ban nincs
                                      ilyen funkció

  Kijelentkezés                       Automatizált
  -----------------------------------------------------------------------

A két N/A követelmény indoklása a tesztdokumentációban részletesen
szerepel.

A `CategoryFilterTest` a Work, Home és Personal kategóriák szűrését
ellenőrzi. Ez kategóriaszűrés, és nem kerül többoldalas lista
bejárásaként dokumentálásra.

------------------------------------------------------------------------

## 8. Tesztadatok

A projekt kétféle tesztadat-kezelést alkalmaz.

### Dinamikus tesztadatok

A `RandomDataGenerator` UUID segítségével egyedi email címeket és note
címeket generál.

Ez lehetővé teszi, hogy a tesztek egymástól függetlenül, új
tesztfelhasználóval fussanak.

### CSV alapú tesztadatok

A `src/test/resources/notes.csv` fájl note adatokat tartalmaz.

A `CsvReader` beolvassa az adatokat, majd `Note` objektumokká alakítja
őket.

A CSV adatforrást használó tesztek:

-   `CreateNotesFromCsvTest`
-   `CategoryFilterTest`

------------------------------------------------------------------------

## 9. Automatizált tesztek futtatása

A projekt gyökérkönyvtárában futtatható:

``` bash
mvn test
```

Ez elindítja a Maven Surefire Plugin által kezelt JUnit teszteket.

### Egy adott tesztosztály futtatása

Például:

``` bash
mvn -Dtest=LoginTest test
```

### Egy adott tesztmetódus futtatása

Például:

``` bash
mvn -Dtest=LoginTest#successfulLogin test
```

------------------------------------------------------------------------

## 10. Manuális tesztfuttatás

A manuális tesztek a `documentation/Vizsgaremek_Tesztmatrix.xlsx`
dokumentumban található tesztmátrix alapján hajthatók végre.

A manuális futtatás során:

1.  Nyisd meg a tesztelt Notes App alkalmazást.
2.  Hajtsd végre a tesztmátrixban szereplő lépéseket.
3.  Ellenőrizd a várt eredményeket.
4.  Rögzítsd a tényleges eredményt.
5.  Eltérés esetén dokumentáld a hibát.

A manuális tesztesetek és az automatizált tesztek célja azonos
funkcionális ellenőrzési pontok lefedése.

------------------------------------------------------------------------

## 11. PrivacyTest speciális működése

A Privacy funkció dinamikusan megjelenő Shadow DOM komponensben
található.

A tesztelt környezetben a Privacy komponens megjelenését a dinamikusan
betöltődő reklámblokkok is befolyásolhatják.

Ezért a `PrivacyTest` külön WebDriver inicializálást használ,
reklámblokkolás nélkül.

A teszt:

1.  megnyitja a Home oldalt;
2.  az oldal aljára görget;
3.  a dinamikus oldalbetöltés miatt szükség esetén ismételt scrollozást
    végez;
4.  megkeresi a Privacy komponenst a Shadow DOM-ban;
5.  megnyitja a Privacy beállításokat;
6.  ellenőrzi, hogy a Privacy Settings megjelenik.

A PrivacyTest stabilitását 10 egymást követő futtatással ellenőriztük.

------------------------------------------------------------------------

## 12. Teszteredmények

A jelenlegi tesztkészlet utolsó ellenőrzött futtatása:

-   Automatizált tesztek: **13**
-   PASS: **13**
-   FAIL: **0**
-   Pass rate: **100%**

A PrivacyTest külön stabilitási ellenőrzése:

-   Futások száma: **10**
-   PASS: **10**
-   FAIL: **0**
-   Pass rate: **100%**

A részletes tesztmátrix és a vezetői tesztjelentés a `documentation`
könyvtárban található.

------------------------------------------------------------------------

## 13. Tesztdokumentáció

A projekt dokumentációja:

### Tesztmátrix

`documentation/Vizsgaremek_Tesztmatrix.xlsx`

Tartalmazza:

-   követelmény mátrix
-   automatizált tesztesetek
-   futtatási összesítő
-   N/A követelmények indoklása
-   benyújtási checklist

### Vezetői tesztjelentés

`documentation/Vezetoi_Tesztjelentes.docx`

Tartalmazza:

-   vezetői összefoglaló
-   teszteredmények
-   követelmény-lefedettség
-   automatizált tesztterület
-   technikai megvalósítás
-   ismert korlátozások
-   következő lépések

------------------------------------------------------------------------

## 14. Ismert korlátozások

A tesztelt Notes App jelenlegi funkcionalitása nem tartalmaz külön
pagination alapú, többoldalas listabejárást, illetve felületről történő
export / download funkciót.

Ezek ezért N/A státuszban szerepelnek a követelmény-mátrixban, és nem
kerültek mesterségesen más funkciókkal azonosításra.

A vizsgaremek végleges változatában a tesztfuttatás automatizálása és az
automatizált tesztjelentés külön dokumentálásra kerül.

------------------------------------------------------------------------

## 15. Projekt státusz

A projekt jelenlegi fejlesztési verziója tartalmazza a kiválasztott SUT
fő funkcióinak automatizált UI tesztelését, a Page Object Model
struktúrát, az adatvezérelt tesztelést és a tesztdokumentáció alapjait.

A vizsgára történő végleges benyújtás előtt a projekt GitHub alapú
automatikus tesztfuttatással és automatizált tesztjelentéssel kerül
kiegészítésre.
