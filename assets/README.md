# Grafiken und Icons

Eigenständig für dieses Lernprojekt erstellte Vektorgrafiken. Die SVG-Dateien enthalten Titel und Bildbeschreibung; die PNG-Versionen sind für die direkte Anzeige in GitHub eingebunden. Texte und Farben lassen sich in den SVG-Quelldateien bearbeiten. Es werden keine externen Schriften, Bilder oder Skripte geladen.

| Grafik | SVG-Quelle | PNG-Version | Einsatz |
| --- | --- | --- | --- |
| Projektbanner | [SVG](netstar-banner.svg) | [PNG](netstar-banner.png) | Einstieg in der README; die drei sichtbaren Tickets entsprechen der tatsächlichen Startreihenfolge |
| Projektfortschritt | [SVG-Vorlage](fortschritt.svg) | – | Automatisch aktualisierte Anzeige ganz oben in der README; Gesamtfortschritt und vier Aufgabenbereiche |
| Sechs Lernbausteine | [SVG](lernbausteine.svg) | [PNG](lernbausteine.png) | Übersicht zu Tickets, Collections, Priorität, Undo, Streams und Auswertung |
| Ticket-Ablauf | [SVG](ticket-ablauf.svg) | [PNG](ticket-ablauf.png) | Datenfluss einschließlich Rückkehr in die PriorityQueue nach Undo |
| Projektzeichen | [SVG](netstar-mark.svg) | [PNG](netstar-mark.png) | Stern und Ticket als kompaktes Projektmotiv |

## Icon-Satz

| Ticket | Collections | Priorität | Rückgängig | Streams | Auswertung |
| --- | --- | --- | --- | --- | --- |
| ![Ticket](icons/ticket.svg) | ![Collections](icons/collections.svg) | ![Priorität](icons/priority.svg) | ![Rückgängig](icons/undo.svg) | ![Streams](icons/streams.svg) | ![Auswertung](icons/analytics.svg) |

Farben: Dunkelblau `#10273d`, Türkis `#29d7ce`, Amber `#ffbd66` und Violett `#ad9cff`. Die Ablaufgrafik ergänzt die Architekturübersicht; sie ersetzt nicht das noch offene vollständige UML-Klassendiagramm aus D2.

## Automatische Fortschrittsanzeige

`scripts/fortschritt.py` liest die 27 Aufgaben aus `doku/aufgabenstatus.md`. Die Basisaufgaben einschließlich Z1–Z4 erhalten ihren Stand aus dieser Tabelle. Für Aufgaben mit einem zugehörigen GitHub-Issue (Titel beginnt zum Beispiel mit `D1:`) gilt zusätzlich der Issue-Status: geschlossen ist erledigt, wieder geöffnet ist noch offen. Bei einem offenen Issue bleiben die in der Tabelle dokumentierten Zustände „teilweise“ und „blockiert“ erhalten; das Label `blocked` markiert ebenfalls eine Blockade.

Alle Aufgaben zählen gleich viel. Der Prozentwert berücksichtigt ausschließlich vollständig erledigte Aufgaben. Teilweise erledigte Aufgaben werden getrennt angezeigt. Die Bereiche Basis/Zusatz, Dokumentation, Erweiterungen und Testen zeigen jeweils erledigt/gesamt.

Der Workflow `.github/workflows/fortschritt.yml` aktualisiert die Grafik bei Änderungen an der Statustabelle, am Generator oder am Workflow sowie bei Änderungen an Issues. Er kann auch manuell gestartet werden. Die veröffentlichte Grafik liegt im Zweig `codex/progress`; die README zeigt diese automatisch gepflegte Version. Die hier gespeicherte SVG-Datei ist die lokale Ausgangsversion. Es ist kein externer Grafikdienst und kein zusätzlich hinterlegter Zugangsschlüssel erforderlich.

Lokal aus der Tabelle neu erzeugen:

```sh
python scripts/fortschritt.py
```

Optional können mit `--issues-json <Datei>` gespeicherte GitHub-Issue-Daten berücksichtigt werden. Eine zweite passende Aufgabe mit derselben ID wird als Fehler gemeldet, damit keine unklare Zuordnung unbemerkt den Fortschritt verändert.
