# Testprotokoll

Ausgeführt am **15.10.2026**, Windows, **JDK 25.0.1**. Die fünf Java-Dateien wurden unverändert mit `javac -encoding UTF-8 -d build/classes src/*.java` kompiliert. Jede Konsolenprüfung startet mit frischen Beispieldaten. `/` trennt aufeinanderfolgende Eingabezeilen.

Die ersten 15 Fälle wurden bei der Repository-Vorbereitung mit einem temporären Java-Prüfprogramm über die öffentlichen Methoden ausgeführt; die übrigen Fälle durch echte Prozesse von `Main` mit den unten aufgeführten Eingaben. Hier stehen tatsächliche Resultate. Diese einmalige Importprüfung ist kein dauerhaft mitgelieferter `Selbsttest.java`; T1 bleibt eine eigene Lernaufgabe.

| Nr. | Menüpunkt / Methode | Eingabe / Ausgangslage | Erwartetes Ergebnis | Tatsächliches Ergebnis | Bestanden |
| --- | --- | --- | --- | --- | --- |
| 1 | Anzahl offen | Frische Beispieldaten; öffentliche Methoden | 6 | 6 | ja |
| 2 | Kritische offene Titel | Frische Beispieldaten; öffentliche Methoden | [Mailserver nicht erreichbar, Backup schlaegt fehl] | [Mailserver nicht erreichbar, Backup schlaegt fehl] | ja |
| 3 | Drei dringendste IDs | Frische Beispieldaten; öffentliche Methoden | [2, 5, 1] | [2, 5, 1] | ja |
| 4 | Kundentitel sortiert | Frische Beispieldaten; öffentliche Methoden | [Firewall-Regel anpassen, Neuen Drucker einrichten, VPN-Verbindung bricht ab] | [Firewall-Regel anpassen, Neuen Drucker einrichten, VPN-Verbindung bricht ab] | ja |
| 5 | Kritisch offen Schmidt | Frische Beispieldaten; öffentliche Methoden | true | true | ja |
| 6 | Kritisch offen Autohaus | Frische Beispieldaten; öffentliche Methoden | false | false | ja |
| 7 | Anzahl pro Bearbeiter | Frische Beispieldaten; öffentliche Methoden | {Anna Berger=3, Lena Fischer=3, Tom Weber=4} | {Anna Berger=3, Lena Fischer=3, Tom Weber=4} | ja |
| 8 | Durchschnitt 2.3 mit Toleranz | Frische Beispieldaten; öffentliche Methoden | true | true | ja |
| 9 | Offene pro Kunde | Frische Beispieldaten; öffentliche Methoden | {Baeckerei Hoffmann=1, Mueller Logistik=3, Schmidt & Soehne=2} | {Baeckerei Hoffmann=1, Mueller Logistik=3, Schmidt & Soehne=2} | ja |
| 10 | Leere Liste Durchschnitt | Frische Beispieldaten; öffentliche Methoden | 0.0 | 0.0 | ja |
| 11 | Neue ID und Status | Frische Beispieldaten; öffentliche Methoden | 11:OFFEN | 11:OFFEN | ja |
| 12 | Queue erledigt zuerst ID 2 | Frische Beispieldaten; öffentliche Methoden | ERLEDIGT | ERLEDIGT | ja |
| 13 | Undo LIFO ID 5 offen ID 2 erledigt | Frische Beispieldaten; öffentliche Methoden | OFFEN:ERLEDIGT | OFFEN:ERLEDIGT | ja |
| 14 | Gemeinsame Liste aktuell | Frische Beispieldaten; öffentliche Methoden | 7 | 7 | ja |
| 15 | Queue gesamte Reihenfolge | Frische Beispieldaten; öffentliche Methoden | [2, 5, 1, 7, 9, 3] | [2, 5, 1, 7, 9, 3] | ja |
| 16 | Menü 1: Anfangsbestand | 1 / 0 | 10 Tickets, #1 zuerst | #1, #2, #3, #4, #5, #6, #7, #8, #9, #10 | ja |
| 17 | Menü 7: Startauswertung | 7 / 0 | 6 offen, Durchschnitt 2.3 | 6 offen, Durchschnitt 2.3 | ja |
| 18 | Menüs 3/4/7: Live-Auswertung | 7 / 3 / 7 / 4 / 7 / 0 | Offene Tickets: 6, 5, 6 | ['6', '5', '6'] | ja |
| 19 | Fehlerfall: Priorität als Text | 2 / Test / Kunde / Person / abc / 1 / 0 | Zahl-Meldung, weiterhin 10 Tickets, Programmende | Zahl-Meldung; 10 Tickets; Programm beendet. | ja |
| 20 | Fehlerfall: Priorität außerhalb Bereich | 2 / Test / Kunde / Person / 5 / 0 | Prioritaet muss zwischen 1 und 4 liegen. | Bereichsmeldung | ja |
| 21 | Fehlerfall: Leere Queue | 3 / 3 / 3 / 3 / 3 / 3 / 3 / 7 / 0 | Keine offenen Tickets vorhanden.; 0 offen | Keine offenen Tickets vorhanden.; 0 offen | ja |
| 22 | Fehlerfall: Leerer Undo-Stack | 4 / 0 | Keine Erledigung zum Rueckgaengigmachen vorhanden. | Leerer Stack abgefangen | ja |
| 23 | Z3: Suche großgeschrieben | 8 / SERVER / 0 | Treffer #2 und #10 | #2, #10 | ja |
| 24 | Z4: Tom Weber | 9 / Tom Weber / 0 | Treffer #2 | #2 | ja |
| 25 | Z4: Unbekannter Bearbeiter | 9 / Niemand / 0 | Kein Treffer, Meldung und Programmende | Kein Treffer, Meldung und Programmende | ja |
| 26 | Fehlerfall: Unbekannter Menüpunkt | 99 / 0 | Ungueltige Eingabe.; Programmende | Ungueltige Eingabe.; Programmende | ja |
| 27 | Eingabeende im Hauptmenü |  | Programm beendet, ohne Ausnahme | Programm beendet. | ja |

**Ergebnis: 27 von 27 Prüfungen bestanden.**

## Weitere Prüfungen und Grenzen

- `javadoc` hat `doku/api/index.html` erfolgreich erzeugt. Eine Warnung bleibt: Standardkonstruktor von `TicketSystem` ohne Kommentar. D1 bleibt teilweise offen.
- Die Queue-Prüfwerte berücksichtigen Z1: #2, #5, #1, #7, #9, #3. Die FIFO-Prüfwerte aus A3/A4 gelten vor dieser Erweiterung.
- Die unveränderten Quelltexte wurden byteweise mit dem IntelliJ-Ausgangsprojekt verglichen.
- Das [vollständige Konsolenbeispiel](beispielausgabe.txt) zeigt den realen Lauf für `7 / 0`.
- E1–E4 und T2 wurden nicht als umgesetzt geprüft; sie sind im Code noch nicht vorhanden. Dieses Protokoll behauptet keine vollständige Eingabevalidierung.
- Für wiederholbare Regressionstests ist T1 vorgesehen. Der GitHub-Workflow prüft derzeit Kompilieren, Startauswertung und Javadoc.
