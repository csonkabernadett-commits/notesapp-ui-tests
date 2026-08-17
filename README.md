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

A tesztek a szükséges tesztfelhasználókkal, valamint dinamikusan generált és CSV-ből betöltött tesztadatokkal dolgoznak.

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
