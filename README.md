# Notes App -- UI Test Automation

## 1. Projekt célja

A projekt célja a kiválasztott **Notes App webalkalmazás** fő felhasználói funkcióinak automatizált felület tesztelése.

A tesztautomatizálás Selenium WebDriver és JUnit Jupiter segítségével, Page Object Model alapú struktúrában készült.

A projekt a webalkalmazás funkcionális ellenőrzésére, a tesztek újrahasznosíthatóságára, olvashatóságára és automatizált futtatására fókuszál.

---

## 2. Tesztelt alkalmazás

**SUT (System Under Test):**

https://practice.expandtesting.com/notes/app/

A tesztelt alkalmazás a Practice Expand Testing Notes App gyakorló webalkalmazása.

A tesztek a regisztráció során létrehozott egyedi tesztfelhasználókkal és dinamikusan generált tesztadatokkal dolgoznak.

---

## 3. Technológiák

| Technológia | Verzió |
|---|---:|
| Java | 21 |
| Selenium WebDriver | 4.41.0 |
| WebDriverManager | 6.3.3 |
| JUnit Jupiter | 6.0.3 |
| Maven Surefire Plugin | 3.5.4 |
| Maven Surefire Report Plugin | 3.5.4 |
| Maven | projekt build / tesztfuttatás |
| Chrome / ChromeDriver | WebDriverManager által kezelt |

### Alkalmazott tesztelési megoldások

- Selenium WebDriver alapú UI automatizálás
- JUnit Jupiter
- Page Object Model
- közös `BasePage` és `BaseTest`
- explicit wait használata
- dinamikus tesztadat-generálás UUID segítségével
- CSV alapú adatvezérelt tesztelés
- Shadow DOM kezelés
- CRUD műveletek tesztelése
- kategóriaszűrés és keresés tesztelése

---

## 4. Előfeltételek

A projekt futtatásához szükséges:

- Java JDK 21
- Maven
- Google Chrome
- internetkapcsolat

A ChromeDriver telepítését a WebDriverManager kezeli.

---

## 5. Projektstruktúra

### Fontosabb könyvtárak

**`base`** – közös tesztelési és Page Object funkciók (`BasePage`, `BaseTest`).

**`pages`** – a tesztelt alkalmazás oldalait és modal ablakait reprezentáló Page Object osztályok.

**`tests`** – az automatizált tesztesetek.

**`models`** – tesztadat-modellek.

**`utils`** – segédosztályok, például CSV-olvasás és tesztadat-generálás.

**`resources`** – tesztadatforrások.

---

## 6. Automatizált tesztek

A projekt **13 automatizált tesztmetódust** tartalmaz.

| Tesztosztály | Teszt | Cél |
|---|---|---|
| `OpenHomePageTest` | `openHomePage` | Home oldal megnyitása |
| `RegisterTest` | `successfulRegistration` | Regisztráció |
| `LoginTest` | `successfulLogin` | Bejelentkezés |
| `PrivacyTest` | `privacySettingsTest` | Adatkezelési / Privacy funkció |
| `AddNoteTest` | `createNewNote` | Új note létrehozása |
| `CreateManyNotesTest` | `createManyNotes` | Sorozatos adatbevitel |
| `CreateNotesFromCsvTest` | `createNotesFromCsv` | Adatbevitel CSV adatforrásból |
| `CategoryFilterTest` | `filterNotesByCategory` | Kategóriaszűrés |
| `SearchTest` | `searchNote` | Note keresése |
| `EditTest` | `editNote` | Meglévő note módosítása |
| `DeleteTest` | `deleteNote` | Note törlése |
| `DeleteTest` | `cancelDeleteNote` | Törlés megszakítása |
| `LogoutTest` | `logout` | Kijelentkezés |

---

## 7. Vizsgakövetelmények lefedettsége

| Követelmény | Lefedettség |
|---|---|
| Regisztráció | Automatizált |
| Bejelentkezés | Automatizált |
| Adatkezelési nyilatkozat használata | Automatizált; a CI környezetben a Privacy toolbar környezetfüggő |
| Adatok listázása | Automatizált |
| Több oldalas lista bejárása | N/A – a tesztelt SUT-ban nincs ilyen funkció |
| Új adat bevitel | Automatizált |
| Ismételt és sorozatos adatbevitel adatforrásból | Automatizált |
| Meglévő adat módosítása | Automatizált |
| Adat vagy adatok törlése | Automatizált |
| Adatok lementése felületről | N/A – a tesztelt SUT-ban nincs ilyen funkció |
| Kijelentkezés | Automatizált |

A két N/A követelmény indoklása a tesztdokumentációban szerepel.

---

## 8. Tesztadatok

### Dinamikus tesztadatok

A `RandomDataGenerator` UUID segítségével egyedi email címeket és note címeket generál.

### CSV alapú tesztadatok

A `src/test/resources/notes.csv` fájl note adatokat tartalmaz. A `CsvReader` beolvassa az adatokat, majd `Note` objektumokká alakítja őket.

A CSV adatforrást használó tesztek:

- `CreateNotesFromCsvTest`
- `CategoryFilterTest`

---

## 9. Tesztek manuális futtatása

A projekt gyökérkönyvtárában:

```bash
mvn test
```

Egy adott tesztosztály:

```bash
mvn -Dtest=LoginTest test
```

Egy adott tesztmetódus:

```bash
mvn -Dtest=LoginTest#successfulLogin test
```

A manuális tesztesetek a `documentation/Vizsgaremek_Tesztmatrix_FINAL.xlsx` fájl tesztmátrixa alapján hajthatók végre.

---

## 10. GitHub Actions – automatizált futtatás

A projekt GitHub Actions workflow-t tartalmaz.

A teljes tesztkészlet automatikusan elindul:

- `push` eseményre
- `pull_request` eseményre
- manuálisan a GitHub Actions felületéről (`workflow_dispatch`)

A workflow JDK 21 környezetben futtatja a Maven teszteket.

A GitHub Actions futás minden alkalommal elkészíti:

- Maven Surefire XML/TXT teszteredményeket
- **HTML automatizált tesztjelentést**

A HTML riport a GitHub Actions futás **Artifacts** részében, `automated-test-report` néven érhető el.

A HTML riportot a Maven Surefire Report Plugin generálja a tesztek által létrehozott Surefire eredményfájlokból.

---

## 11. PrivacyTest speciális működése

A Privacy funkció dinamikusan megjelenő Shadow DOM komponensben található.

A Privacy komponens megjelenése a futtatási környezettől függhet. Emiatt a `PrivacyTest` külön WebDriver inicializálást használ reklámblokkolás nélkül, és a környezetfüggő hiányt diagnosztikai üzenettel kezeli.

Ha a Privacy toolbar elérhető, a teszt megnyitja a Privacy Settings felületet és ellenőrzi annak megjelenését. Ha a toolbar az adott CI környezetben nem érhető el, a teszt ezt kiírja a logba, és a build nem bukik el környezeti ok miatt.

---

## 12. Teszteredmények

A legutóbbi ellenőrzött tesztfuttatás:

- Automatizált tesztek: **13**
- PASS: **13**
- FAIL: **0**
- Skipped: **0**
- Pass rate: **100%**

A részletes automatizált tesztjelentés GitHub Actions futás után HTML formátumban az `automated-test-report` artifactban érhető el.

A részletes tesztmátrix és a vezetői tesztjelentés a `documentation` könyvtárban található.

---

## 13. Tesztdokumentáció

### Tesztmátrix

`documentation/Vizsgaremek_Tesztmatrix_FINAL.xlsx`

Tartalmazza:

- követelmény mátrix
- automatizált tesztesetek
- futtatási összesítő
- N/A követelmények indoklása
- benyújtási checklist

### Vezetői tesztjelentés

`documentation/Vezetoi_Tesztjelentes_FINAL.docx`

Tartalmazza:

- vezetői összefoglaló
- teszteredmények
- követelmény-lefedettség
- automatizált tesztterület
- technikai megvalósítás
- CI/CD és automatizált tesztjelentés
- ismert korlátozások

---

## 14. Ismert korlátozások

A tesztelt Notes App jelenlegi funkcionalitása nem tartalmaz külön pagination alapú, többoldalas listabejárást, illetve felületről történő export / download funkciót.

Ezek ezért N/A státuszban szerepelnek a követelmény-mátrixban, és nem kerültek mesterségesen más funkciókkal azonosításra.

A Privacy funkció megjelenése futtatási környezetfüggő lehet; a teszt ezt diagnosztikai üzenettel kezeli, miközben a lokális környezetben a funkció teljes ellenőrzése elvégezhető.

---

## 15. Projekt státusz

A projekt a vizsgaremek benyújtására kész állapotban van. Tartalmazza a kiválasztott SUT fő funkcióinak automatizált UI tesztelését, a Page Object Model struktúrát, adatvezérelt tesztelést, tesztdokumentációt, GitHub Actions alapú automatikus tesztfuttatást és automatikusan generált HTML tesztjelentést.
