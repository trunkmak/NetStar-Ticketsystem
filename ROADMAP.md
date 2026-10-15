# Roadmap

Stand **15.10.2026**. Der vorhandene Stand erfüllt A1–B7 einschließlich der Erweiterung Z1 sowie Z2–Z4. Quellcode und Beispieldaten wurden beim Import unverändert übernommen. Die Zuordnung aller Aufgaben steht in [doku/aufgabenstatus.md](doku/aufgabenstatus.md).

Die drei Meilensteine gliedern die noch offenen Aufgabenteile. Es sind bewusst keine Termine erfunden. Laut Bonusblatt genügt zunächst mindestens eine gewählte Aufgabe je Bereich; diese Roadmap bietet die vollständige Auswahl an.

Die [vollständigen Aufgabenbeschreibungen mit Prüfwerten](doku/aufgaben.md) dienen als Arbeitsleitfaden. [Tipps und Tricks](doku/tipps-und-tricks.md) und der [Projektbericht](doku/projektbericht.md) halten Erfahrungen aus der bisherigen Bearbeitung fest.

## [M1 – Dokumentation abschließen](https://github.com/trunkmak/NetStar-Ticketsystem/milestone/1)

D1 und D2 vervollständigen. D3 (echtes Testprotokoll) und D4 (README) sind bei der Repository-Einrichtung erledigt.

- [ ] [D1: Javadoc vervollständigen und warnungsfrei erzeugen](https://github.com/trunkmak/NetStar-Ticketsystem/issues/1)
- [ ] [D2: Vollständiges UML-Klassendiagramm mit Bildexport](https://github.com/trunkmak/NetStar-Ticketsystem/issues/2)
- [x] [D3: Tatsächlich ausgeführtes Testprotokoll dokumentieren](https://github.com/trunkmak/NetStar-Ticketsystem/issues/3)
- [x] [D4: README mit Startanleitung und Collection-Begründungen](https://github.com/trunkmak/NetStar-Ticketsystem/issues/4)

## [M2 – Erweiterungen aus Teil 2](https://github.com/trunkmak/NetStar-Ticketsystem/milestone/2)

E1–E4: Filter, Sortier-Menü, Statistik und Bearbeiter-Ranking. Z3/Z4 bei der Menüumstellung erhalten.

- [ ] [E1: Filter kombinieren und Bearbeiterfilter ergänzen](https://github.com/trunkmak/NetStar-Ticketsystem/issues/5)
- [ ] [E2: Sortier-Menü mit Comparator-Map umsetzen](https://github.com/trunkmak/NetStar-Ticketsystem/issues/6)
- [ ] [E3: Partitionierung, Statustitel und Prioritätsstatistik](https://github.com/trunkmak/NetStar-Ticketsystem/issues/7)
- [ ] [E4: Bearbeiter mit den meisten offenen Tickets ermitteln](https://github.com/trunkmak/NetStar-Ticketsystem/issues/8)

## [M3 – Selbsttests und Fehlersuche](https://github.com/trunkmak/NetStar-Ticketsystem/milestone/3)

T1 als eigene Selbsttest-Klasse umsetzen. Für T2 zunächst die fehlende Originaldatei Fehlersuche.java beschaffen.

- [ ] [T1: Eigene Selbsttest-Klasse ohne Testframework schreiben](https://github.com/trunkmak/NetStar-Ticketsystem/issues/9)
- [ ] [T2: Fehlersuche-Vorlage beschaffen und sechs Fehler analysieren](https://github.com/trunkmak/NetStar-Ticketsystem/issues/10) — wartet auf die Originaldatei

## Empfohlene Reihenfolge und Abhängigkeiten

1. D1 abschließen und T1 als verlässliche Grundlage für Erweiterungen umsetzen.
2. Für E1/E2/E3 die Menübelegung gemeinsam festlegen. Vorgabe für Teil 2: 8 Filter, 9 Sortieren, 10 Statistik/Ranking. Bestehende Suche und Ersttreffersuche beispielsweise nach 11/12 verschieben und dokumentieren.
3. E1 und E2 ergänzen, danach E3 und das darauf aufbauende E4.
4. D2 am tatsächlichen Klassenstand vervollständigen und als Bild/PDF exportieren.
5. T2 fortsetzen, sobald die Originaldatei vorliegt. Die übrigen Aufgaben können unabhängig davon bearbeitet werden.

## Definition von erledigt

Eine Aufgabe ist abgeschlossen, wenn sie kompiliert, ihre Prüfwerte und relevanten Fehlerfälle tatsächlich ausgeführt wurden, die Ergebnisse im Testprotokoll stehen und README/Aufgabenstatus zum Verhalten passen. Bei Javadoc- und Diagrammaufgaben müssen die erzeugten Dateien zusätzlich geöffnet und geprüft werden. Ein Issue wird erst danach geschlossen.

Optionale spätere Verbesserungen außerhalb der Aufgabenblätter: Status als Enum, Datenkapselung, Eingabeende in Unterdialogen absichern und dauerhafte Speicherung. Sie sind hier bewusst keine zusätzlichen Pflichtaufgaben.
