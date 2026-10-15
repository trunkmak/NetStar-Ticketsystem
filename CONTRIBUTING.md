# Am Lernprojekt weiterarbeiten

1. Ein offenes Issue aus dem passenden Meilenstein wählen und dessen Abschlusskriterien lesen.
2. Einen Branch anlegen, zum Beispiel `git switch -c codex/e1-filter`.
3. Eine kleine, erklärbare Änderung umsetzen, kompilieren und mit den Prüfwerten vergleichen.
4. Tatsächliche Ergebnisse im Testprotokoll festhalten; Aufgabenstatus und Roadmap aktualisieren.
5. Pull Request mit Beschreibung, Testergebnis und `Closes #<Nummer>` öffnen.

Für die Aufgabenbearbeitung orientieren wir uns an den Lernvorgaben: Lambdas ausschreiben, `Ticket.java` und `Beispieldaten.java` unverändert lassen und Java-Kommentare ohne Umlaute schreiben. Neue Funktionen sollen sich in eigenen Worten erklären lassen. Diese Regeln sind Lernkonventionen aus dem Aufgabenmaterial.

Quelltext liegt in `src/`, Lernunterlagen und Protokolle in `doku/`. Generierte Klassen, IDE-Einstellungen und lokale Zugangsdaten gehören nicht ins Repository. API-Dokumentation nach Änderungen mit `scripts/javadoc.ps1` neu erzeugen.

Nach einer erledigten Aufgabe den Aufgabenstatus aktualisieren und das zugehörige Issue schließen. Die Fortschrittsgrafik in der README wird aus Tabelle und Issues automatisch aktualisiert; teilweise erledigte Aufgaben zählen erst nach ihrem Abschluss zum Prozentwert.

Für Fehlerberichte: Eingabe, erwartetes Ergebnis, tatsächliche Ausgabe und JDK-Version angeben. Bei neuen Funktionen zuerst die Auswirkung auf die bestehenden Menüpunkte 8 und 9 festlegen.
