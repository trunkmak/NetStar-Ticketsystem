# Aufgabenstatus

Abgleich vom **15.10.2026** gegen Quellcode und die [Originalquellen](quellen/README.md). „Erledigt“ bezeichnet den belegten Programm- oder Dokumentationsstand, keine Abnahme durch die Lehrkraft. Die Funktionsprüfung ist im [Testprotokoll](testprotokoll.md) dokumentiert.

Die vollständige [Aufgabenübersicht mit Arbeitsaufträgen und Prüfwerten](aufgaben.md) ergänzt diese Statustabelle. [Projektbericht](projektbericht.md) und [Tipps und Tricks](tipps-und-tricks.md) beschreiben das Vorgehen und die Lernerfahrungen.

## Tagesprojekt

Quelle: Tagesprojekt S. 2–3, ergänzende Regeln S. 4.

| ID | Aufgabe | Status | Nachweis / Besonderheit |
| --- | --- | --- | --- |
| A1 | Liste, Beispiele laden, alle Tickets anzeigen | Erledigt | `TicketSystem.hinzufuegen`, `Main` Menü 1; zehn Tickets |
| A2 | Anlegen, größte ID + 1, Status OFFEN | Erledigt | `anlegen`, Menü 2; neue ID 11 |
| A3 | Queue, Erledigen, leere Queue | Durch Z1 erweitert | `PriorityQueue`, `poll`; erstes Ticket #2 statt #1 |
| A4 | Stack, letzte Erledigung rückgängig | Durch Z1 erweitert | `push`/`pop`; nach zwei Erledigungen #5 wieder OFFEN, #2 bleibt ERLEDIGT |
| A5 | Map je Bearbeiter, Kunden-Set | Erledigt | Menüs 5/6, Bearbeiter 3/3/4 Tickets, vier Kunden |
| A6 | Menü und ungültige Eingaben | Erledigt im geforderten Umfang | Text statt Zahl, Werte außerhalb 1–4 und leere Queue abgefangen; abruptes Eingabeende während eines Dialogs siehe Grenzen |
| B1 | Vier benannte Lambdas | Erledigt | `istOffen`, `istKritisch`, `titelMitBearbeiter`, `zeileAusgeben` |
| B2 | Offene Tickets zählen | Erledigt | `anzahlOffen()` → 6 |
| B3 | Kritische offene Titel / Ausgabe | Erledigt | `kritischeOffeneTitel`, `offeneTicketsAusgeben` |
| B4 | Dringendste offene Tickets sortieren und begrenzen | Erledigt | `dringendsteOffene(3)` → #2, #5, #1 |
| B5 | Kundentitel und Existenzprüfung | Erledigt | `titelVonKunde`, `hatKritischesOffenesTicket` |
| B6 | Anzahl pro Bearbeiter | Erledigt | `groupingBy` + `counting` |
| B7 | Durchschnitt und Menü 7 | Erledigt | Durchschnitt 2,3; Auswertungen im Menü eingebunden |
| Z1 | PriorityQueue mit Lambda | Erledigt | Priorität aufsteigend, bei Gleichstand ID aufsteigend |
| Z2 | Offene Tickets je Kunde | Erledigt | `offeneTicketsProKunde`, Menü 7 |
| Z3 | Suche ohne Groß-/Kleinschreibung | Erledigt | `ticketsSuchen`, Menü 8 |
| Z4 | Erstes kritisches Ticket eines Bearbeiters | Erledigt | `erstesKritischesTicket`, `findFirst`, `Optional`, Menü 9 |

Die FIFO-Prüfwerte des Basisblatts für A3/A4 sind **vor Z1** gültig. Das Priorisieren im aktuellen Stand ist beabsichtigt. Auch beim Undo wird ein Ticket wieder nach Priorität eingeordnet; „hinten anhängen“ beschreibt die jetzige Queue nicht korrekt.

## Bonusaufgaben

Quelle: Bonusblatt S. 1–4.

| ID | Aufgabe | Status | Nachweis / Restarbeit |
| --- | --- | --- | --- |
| D1 | Javadoc und HTML | Teilweise | Methoden dokumentiert, `doku/api/index.html` erzeugt; Warnung zum Standardkonstruktor und veralteter Undo-Kommentar offen |
| D2 | Vollständiges UML-Klassendiagramm als Bild/PDF | Teilweise | Architekturdiagramm und Inventar vorhanden; vollständiges UML mit allen Sichtbarkeiten/Multiplizitäten und Bildexport offen |
| D3 | Testprotokoll mit mindestens 8 Fällen, davon 2 Fehlerfälle | Erledigt | Tatsächlich ausgeführte Prüfungen in `doku/testprotokoll.md` |
| D4 | README, Collection-Begründung, Konsolenbeispiel | Erledigt | `README.md` |
| E1 | Predicates kombinieren, Bearbeiterfilter | Offen | Noch keine `vonBearbeiter`-/`filtern`-Methoden; Menü 8 bereits für Z3 belegt |
| E2 | Sortier-Menü mit Comparator-Map | Offen | Noch keine `sortiert`-Methode; Menü 9 bereits für Z4 belegt |
| E3 | Partitionierung, Status-Titel, vollständige Statistik | Offen | Bestehender Durchschnitt allein erfüllt E3 nicht |
| E4 | Ranking nach offenen Tickets | Offen | Bestehende Anzahl pro Bearbeiter zählt alle Tickets und ist kein Ranking |
| T1 | Eigene Selbsttest-Klasse ohne Framework | Offen | Kein `Selbsttest.java`; die einmalige Importprüfung ersetzt die Lernaufgabe nicht |
| T2 | Sechs Fehler analysieren und korrigieren | Blockiert durch fehlende Vorlage | `Fehlersuche.java` fehlt; Originaldatei besorgen, dann Symptome und Korrekturen dokumentieren |

## Grenzen des aktuellen Lernstands

- Nur Arbeitsspeicher, keine Datenbank oder Dateiablage.
- `getTickets()` gibt die veränderbare interne Liste zurück; Änderungen daran können Map, Set und Queue inkonsistent machen. Die Auswertung verwendet bewusst dieselbe Listenreferenz.
- Prioritätsvalidierung erfolgt im Menü; direkte Aufrufe von `anlegen` validieren nicht selbst.
- `dringendsteOffene` erwartet eine nichtnegative Anzahl; negative Werte führen zu einer Ausnahme.
- Ein Eingabeende im Hauptmenü wird behandelt; ein Abbruch mitten im Anlegedialog ist nicht abgesichert.
- Die API-Dokumentation kann erzeugt werden, ist aber noch nicht warnungsfrei.

Diese Grenzen werden transparent dokumentiert; der hochgeladene Java-Code wurde für die Repository-Einrichtung nicht verändert.
