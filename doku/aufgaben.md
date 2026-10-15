# Aufgaben aus den Aufgabenblättern

Hier stehen alle **27 Aufgaben** aus dem Tagesprojekt und den Bonusaufgaben als lesbarer Arbeitsleitfaden. Die Aufgaben sind sinngemäß zusammengefasst; maßgeblich bleiben die Originalblätter von Christopher Partridge: [Tagesprojekt](quellen/tagesprojekt.pdf) und [Teil 2](quellen/teil-2-bonusaufgaben.pdf).

Der [Aufgabenstatus](aufgabenstatus.md) zeigt, was bereits umgesetzt ist. Die [Roadmap](../ROADMAP.md) verknüpft die offenen Aufgaben mit Issues und Meilensteinen. Die nachfolgenden Aufträge beschreiben die Übung, unabhängig vom aktuellen Bearbeitungsstand.

## Ausgangslage und Vorbereitung

Eine IT-Firma erhält Störungsmeldungen verschiedener Kunden. Die Java-Konsolenanwendung soll diese Tickets verwalten und auswerten. Ein Ticket hat ID, Titel, Kunde, Bearbeiter, Priorität und Status. Priorität 1 ist kritisch, 4 niedrig; die Statuswerte lauten `OFFEN`, `IN_BEARBEITUNG` und `ERLEDIGT`.

`Ticket.java`, `Beispieldaten.java` mit zehn Tickets und ein Menügerüst sind vorgegeben. Selbst zu bearbeiten sind vor allem `TicketSystem.java`, `Auswertung.java` und die Verknüpfung mit dem Menü. Die Prüfwerte gelten jeweils nach einem frischen Programmstart mit unveränderten Beispieldaten.

## Teil A – Tickets mit Collections verwalten

Quelle: Tagesprojekt, S. 1–2. Vorgesehen sind drei Blöcke von jeweils etwa 60 Minuten: A1/A2, A3/A4 und A5/A6.

### A1 – Tickets speichern und anzeigen

- **Auftrag:** In `TicketSystem` eine `List<Ticket>` anlegen und `hinzufuegen(Ticket ticket)` schreiben. In `Main` alle Beispieltickets hinzufügen; Menü 1 zeigt sie mit einer Schleife an.
- **Kontrolle:** Zehn Tickets werden angezeigt, #1 steht zuerst. Die Gesamtliste soll auch erledigte Tickets behalten.

### A2 – Neue Tickets anlegen

- **Auftrag:** `anlegen(titel, kunde, bearbeiter, prioritaet)` ergänzen. Die ID ist die bisher größte ID plus eins; der Anfangsstatus ist `OFFEN`. Menü 2 fragt die vier Eingabewerte ab.
- **Kontrolle:** Das erste neue Ticket hat ID 11 und ist anschließend über Menü 1 sichtbar.

### A3 – Offene Tickets in einer Queue bearbeiten

- **Auftrag:** Zunächst eine `LinkedList` als Queue verwenden. Nur offene Tickets beim Hinzufügen einreihen. `naechstesErledigen()` entnimmt mit `poll()` ein Ticket und setzt es auf `ERLEDIGT`. Für eine leere Queue eine Meldung ausgeben.
- **Kontrolle im Basisstand:** Erst #1, dann #2 erledigen. `poll()` kann `null` liefern; vor dem Zugriff prüfen.

### A4 – Erledigungen mit einem Stack zurücknehmen

- **Auftrag:** Erledigte Tickets auf einen Stack legen. `letzteErledigungRueckgaengig()` entnimmt mit `pop()` die letzte Erledigung, setzt ihren Status auf `OFFEN` und reiht sie wieder ein. Einen leeren Stack berücksichtigen.
- **Kontrolle im Basisstand:** Zweimal erledigen, einmal rückgängig: #2 ist wieder offen, #1 bleibt erledigt.

**Abweichung durch Z1:** Im vorhandenen Projekt arbeitet die Queue bereits nach Priorität und ID. Für A3 werden deshalb #2 und #5 zuerst erledigt. Bei A4 wird anschließend #5 wieder offen; #2 bleibt erledigt. Undo ordnet erneut nach Priorität ein.

### A5 – Bearbeiter zuordnen und Kunden sammeln

- **Auftrag:** Eine `Map<String, List<Ticket>>` für Bearbeiter und ihre Tickets sowie ein `Set<String>` für Kunden verwenden. Menü 5 zeigt die Bearbeitergruppen, Menü 6 die unterschiedlichen Kunden.
- **Kontrolle:** Anna Berger 3, Lena Fischer 3 und Tom Weber 4 Tickets; insgesamt vier Kunden ohne doppelte Namen. Für einen neuen Bearbeiter zuerst eine eigene Ticketliste in der Map anlegen.

### A6 – Menü und Fehlerbehandlung fertigstellen

- **Auftrag:** Menüs 1–6 vollständig verbinden und wiederholte Bedienung ermöglichen. Ungültige Eingaben und eine leere Warteschlange dürfen keinen Absturz auslösen.
- **Kontrolle:** Mehrere Funktionen nacheinander ausführen, Text statt einer Prioritätszahl eingeben und die Queue vollständig leeren. Im Projekt wird zusätzlich der Prioritätsbereich 1–4 geprüft.

## Teil B – Mit Lambdas und Streams auswerten

Quelle: Tagesprojekt, S. 2–3. Weitere drei Blöcke von jeweils etwa 60 Minuten sind für B1–B3, B4/B5 und B6/B7 vorgesehen. `Auswertung` erhält im Konstruktor die Liste von `system.getTickets()`.

### B1 – Benannte Lambdas vorbereiten

- **Auftrag:** Vier Attribute definieren: `Predicate<Ticket> istOffen`, `Predicate<Ticket> istKritisch`, `Function<Ticket, String> titelMitBearbeiter` und `Consumer<String> zeileAusgeben`.
- **Kontrolle:** Die Regeln prüfen `OFFEN` beziehungsweise Priorität 1, erzeugen Titel plus Bearbeiter in Klammern und geben eine Zeile mit vorangestelltem Bindestrich aus. In den folgenden Methoden wiederverwenden.

### B2 – Offene Tickets zählen

- **Auftrag:** `long anzahlOffen()` mit `filter` und `count` schreiben.
- **Kontrolle:** Ergebnis `6L`. Tickets mit `IN_BEARBEITUNG` zählen hier nicht als offen.

### B3 – Kritische offene Titel und offene Tickets ausgeben

- **Auftrag:** `kritischeOffeneTitel()` filtert nach offen und kritisch, wandelt in Titel um und sammelt sie als `List<String>`. `offeneTicketsAusgeben()` verwendet die benannte Function und den Consumer.
- **Kontrolle:** Die kritischen offenen Titel sind „Mailserver nicht erreichbar“ und „Backup schlaegt fehl“. Die Ausgabe aller offenen Tickets enthält sechs Zeilen mit Titel und Bearbeiter.

### B4 – Die dringendsten offenen Tickets bestimmen

- **Auftrag:** `List<Ticket> dringendsteOffene(int anzahl)` schreiben. Offene Tickets nach Priorität, bei Gleichstand nach ID sortieren und danach mit `limit(anzahl)` begrenzen.
- **Kontrolle:** Für drei Tickets lautet die Reihenfolge #2, #5, #1. Erst sortieren, dann begrenzen, damit die Auswahl den gesamten offenen Bestand berücksichtigt.

### B5 – Nach Kunde filtern und eine Existenzfrage beantworten

- **Auftrag:** `titelVonKunde(String kunde)` liefert alphabetisch sortierte Titel. `hatKritischesOffenesTicket(String kunde)` prüft mit `anyMatch`, ob mindestens ein Ticket alle drei Bedingungen erfüllt.
- **Kontrolle:** Autohaus Kraemer: „Monitor flackert“, „Server-Festplatte fast voll“, „WLAN im Besprechungsraum langsam“. Kritisch und offen: Schmidt & Soehne → `true`, Autohaus Kraemer → `false`.

### B6 – Tickets je Bearbeiter zählen

- **Auftrag:** `Map<String, Long> anzahlProBearbeiter()` mit `Collectors.groupingBy` und `Collectors.counting()` schreiben.
- **Kontrolle:** Anna 3, Lena 3, Tom 4. Hier werden alle Tickets gezählt, unabhängig vom Status.

### B7 – Durchschnitt berechnen und Auswertungen einbinden

- **Auftrag:** `double durchschnittPrioritaet()` mit `mapToInt` und `average` schreiben. Menü 7 soll alle Auswertungen anzeigen. Namen und wiederholte Regeln im Code überprüfen.
- **Kontrolle:** Durchschnitt der zehn Prioritätswerte: 2,3. Das aktuelle Projekt liefert bei einer leeren Liste ersatzweise `0.0`.

## Zusatzaufgaben Z1–Z4

Quelle: Tagesprojekt, S. 3. Diese Erweiterungen sind freiwillig und bauen auf Teil A/B auf.

### Z1 – Dringende Tickets zuerst

- **Auftrag:** Die FIFO-Queue durch eine `PriorityQueue<Ticket>` mit ausgeschriebenem Lambda-Comparator ersetzen. Kritische Tickets sollen zuerst herauskommen.
- **Kontrolle im Projekt:** Priorität aufsteigend, danach ID aufsteigend. Die offenen Tickets werden als #2, #5, #1, #7, #9, #3 erledigt. Die zusätzliche ID-Regel macht Gleichstände nachvollziehbar.

### Z2 – Offene Tickets je Kunde zählen

- **Auftrag:** Erst offene Tickets filtern, dann nach Kunde gruppieren und zählen.
- **Kontrolle:** Mueller Logistik 3, Schmidt & Soehne 2, Baeckerei Hoffmann 1. Ein Kunde ohne offene Tickets erscheint in dieser Gruppierung nicht automatisch mit der Anzahl 0.

### Z3 – Tickets über einen Suchtext finden

- **Auftrag:** Einen Menüpunkt ergänzen, der Suchtext einliest und alle Tickettitel auf diesen Teiltext durchsucht. Groß-/Kleinschreibung soll keine Rolle spielen.
- **Kontrolle im Projekt:** Menü 8, Eingabe `SERVER` → #2 und #10. Die Suche ist nicht auf offene Tickets beschränkt.

### Z4 – Erstes kritisches Ticket eines Bearbeiters finden

- **Auftrag:** Bearbeiter und Priorität filtern; `findFirst()` liefert ein `Optional<Ticket>`. Treffer oder eine Meldung für den Fall ohne Treffer ausgeben.
- **Kontrolle im Projekt:** Menü 9, Tom Weber → #2; Anna Berger → kein kritisches Ticket. „Erstes“ meint die Listenreihenfolge, nicht die Queue-Reihenfolge.

## Teil 2 – Dokumentation D1–D4

Quelle: Bonusblatt, S. 1–2. Richtwerte: D1/D2 jeweils etwa 40 Minuten, D3/D4 jeweils etwa 30 Minuten. Diese Angaben sind Orientierung, keine dokumentierten tatsächlichen Bearbeitungszeiten.

### D1 – Javadoc schreiben und erzeugen

- **Auftrag:** `TicketSystem`, `Auswertung` und ihre public-Methoden dokumentieren. Je Parameter `@param`, bei Rückgabewerten `@return` angeben. HTML erzeugen und die Startseite im Browser öffnen.
- **Ergebnis:** Kommentare im Quelltext und generierte HTML-Dateien. Im Repository liegt die API unter `doku/api/`; `doku/index.html` führt dorthin. Warnungen prüfen, insbesondere den bislang undokumentierten Standardkonstruktor.
- **Weiterarbeit:** [Issue #1](https://github.com/trunkmak/NetStar-Ticketsystem/issues/1).

### D2 – UML-Klassendiagramm zeichnen

- **Auftrag:** Ticket, TicketSystem, Auswertung und Main mit Attributtypen, public-Methoden, Sichtbarkeiten und Beziehungen einschließlich Multiplizitäten darstellen. Collection-Elementtypen ebenfalls nennen.
- **Ergebnis:** Ein Diagramm als PNG, PDF oder JPG unter `doku/`. Die vorhandene Architekturübersicht ist der Einstieg; vollständiges UML und Bildexport stehen noch aus.
- **Weiterarbeit:** [Issue #2](https://github.com/trunkmak/NetStar-Ticketsystem/issues/2).

### D3 – Ein echtes Testprotokoll führen

- **Auftrag:** Mindestens acht Fälle, darunter zwei Fehlerfälle, tatsächlich ausführen. Nummer, Menüpunkt/Methode, Eingabe, Erwartung, tatsächliches Ergebnis und bestanden ja/nein festhalten.
- **Ergebnis:** [Testprotokoll](testprotokoll.md). Ergebnisse dürfen erst nach dem Ausführen eingetragen werden. Die Repository-Vorbereitung hat 27 Prüfungen dokumentiert.
- **Nachweis:** [Issue #3](https://github.com/trunkmak/NetStar-Ticketsystem/issues/3), abgeschlossen.

### D4 – Eine hilfreiche README schreiben

- **Auftrag:** Zweck, Kompilieren/Starten, alle Menüpunkte, eine begründete Collection-Tabelle und einen Beispielablauf mit Konsolenausgabe beschreiben.
- **Ergebnis:** [README](../README.md) im Projektstamm.
- **Nachweis:** [Issue #4](https://github.com/trunkmak/NetStar-Ticketsystem/issues/4), abgeschlossen.

## Teil 2 – Erweiterungen E1–E4

Quelle: Bonusblatt, S. 2–3. Richtwert jeweils etwa 30 Minuten. Vorgesehen sind Menü 8 für Filter, 9 für Sortierung und 10 für Statistik/Ranking. Die vorhandenen Z3/Z4-Funktionen müssen bei der Menüumstellung erhalten bleiben.

### E1 – Predicates kombinieren

- **Auftrag:** `static Predicate<Ticket> vonBearbeiter(String name)` und `List<Ticket> filtern(Predicate<Ticket> filter)` ergänzen. Regeln mit `and`, `or` und `negate` verbinden, ohne neue Schleifen. Menü 8 zeigt drei Kombinationen und fragt danach nach einem Bearbeiter für dessen offene Tickets.
- **Kontrolle:** Offen UND kritisch → #2/#5; offen UND NICHT kritisch → #1/#3/#7/#9; kritisch ODER Anna → #1/#2/#3/#5/#7/#10. Tom Weber offen → #2; unbekannter Name → keine Treffer, kein Absturz.
- **Weiterarbeit:** [Issue #5](https://github.com/trunkmak/NetStar-Ticketsystem/issues/5).

### E2 – Ein Sortier-Menü anbieten

- **Auftrag:** Eine `Map<String, Comparator<Ticket>>` mit `id`, `prioritaet`, `titel`, `kunde` anlegen. Bei Gleichstand entscheidet die ID. `sortiert(String kriterium, boolean absteigend)` liefert die sortierten Tickets; `reversed()` kehrt den vollständigen Comparator um. Menü 9 fragt Kriterium und Richtung ab.
- **Kontrolle:** Titel aufsteigend: #5 zuerst, #6 zuletzt. Priorität absteigend: #8, #3, #6, #4, #9, #7, #1, #10, #5, #2. Bei `farbe` Meldung und Weiterlaufen.
- **Weiterarbeit:** [Issue #6](https://github.com/trunkmak/NetStar-Ticketsystem/issues/6).

### E3 – Eine vollständige Statistik ausgeben

- **Auftrag:** Mit `partitioningBy(istOffen)` offene und nicht offene Tickets trennen, Titel eines Status mit `joining(", ")` verbinden und über `mapToInt`/`summaryStatistics()` Prioritätskennzahlen ermitteln. Alle drei Auswertungen kommen in Menü 10.
- **Kontrolle:** Offen 6, nicht offen 4. Für `IN_BEARBEITUNG`: „WLAN im Besprechungsraum langsam, Server-Festplatte fast voll“. Priorität: Minimum 1, Maximum 4, Durchschnitt 2,3, Anzahl 10.
- **Weiterarbeit:** [Issue #7](https://github.com/trunkmak/NetStar-Ticketsystem/issues/7).

### E4 – Bearbeiter-Ranking nach offenen Tickets

- **Auftrag:** Offene Tickets filtern, pro Bearbeiter mit `groupingBy`/`counting` zählen und in `entrySet().stream()` mit `max(Map.Entry.comparingByValue())` den größten Wert suchen. Das Optional in Menü 10 behandeln.
- **Kontrolle:** Anna Berger führt mit 3 offenen Tickets. Nach sechsmal Erledigen lautet die Ausgabe „Keine offenen Tickets.“ Bei Gleichstand ist jeder führende Bearbeiter zulässig.
- **Weiterarbeit:** [Issue #8](https://github.com/trunkmak/NetStar-Ticketsystem/issues/8).

## Teil 2 – Testen und Fehlersuche T1/T2

Quelle: Bonusblatt, S. 3–4. Richtwert jeweils etwa 40 Minuten.

### T1 – Einen Selbsttest ohne Framework schreiben

- **Auftrag:** `Selbsttest.java` mit `main` und der Hilfsmethode `pruefe(String name, Object erwartet, Object tatsaechlich)` anlegen. Jede Prüfung zeigt OK/FEHLER samt beiden Werten; am Ende erscheint die Gesamtzahl bestandener Tests.
- **Kontrolle:** Mindestens acht Tests mit den Prüfwerten aus Teil 1, davon mindestens zwei zu Queue-Reihenfolge und Undo. Für jeden TicketSystem-Test ein frisches System verwenden. Ein absichtlich falscher Erwartungswert muss als Fehler erkannt, danach wieder entfernt werden.
- **Tipp aus dem Blatt:** `6L` für long-Erwartungen verwenden; double-Werte mit Toleranz vergleichen. Eine zusätzliche passende Überladung der Prüfmethode hilft dabei.
- **Weiterarbeit:** [Issue #9](https://github.com/trunkmak/NetStar-Ticketsystem/issues/9). Die einmalige Importprüfung ersetzt diese eigene Selbsttest-Klasse nicht.

### T2 – Sechs Fehler untersuchen und beheben

- **Auftrag:** Die vorgegebene `Fehlersuche.java` zuerst unverändert ausführen. Für jede ihrer sechs Methoden Symptom beobachten, Ursache erklären, Korrektur durchführen und Methode/Symptom/Ursache/Korrektur in `doku/fehlersuche.md` festhalten.
- **Kontrolle nach Korrektur:** Methode 1: sechs Titel; Methode 2: „Anzahl offen: 6“ und sechs Titel; Methode 3: acht Tickets; Methode 4: IDs [2, 5, 10]; Methode 5: 2,3; Methode 6: „VPN-Verbindung bricht ab“ beziehungsweise „Keine Tickets“.
- **Voraussetzung:** Die Originaldatei fehlt im gelieferten Material. Erst beschaffen, dann ihre konkreten Fehler untersuchen.
- **Weiterarbeit:** [Issue #10](https://github.com/trunkmak/NetStar-Ticketsystem/issues/10).

## Lernvorgaben und Abschluss

Die Aufgabenblätter verwenden ausgeschriebene Lambdas statt Methodenreferenzen mit `::`. `Ticket.java` und `Beispieldaten.java` bleiben unverändert. Im Java-Quelltext einschließlich Kommentaren sollen für diese Übung `ae`, `oe`, `ue`, `ss` statt Umlauten und ß verwendet werden. README und Protokolle dürfen Umlaute enthalten. Das ist eine Übungskonvention; Java unterstützt grundsätzlich Unicode.

Für Teil 2 zunächst eine Sicherung erstellen und mindestens eine Aufgabe je Bereich wählen. Z1–Z4 zählen ebenfalls als Erweiterungen. Recherche und Erklärungen sind erlaubt; entscheidend ist, den eigenen Code erklären zu können. Häufig kompilieren und mit echten Ausgaben prüfen. Die Abschlussbesprechung des Tagesprojekts erfolgt mit der Lehrkraft; das Bonusblatt beschreibt Teil 2 als Übung ohne verpflichtende Abgabe.

Zum Weiterarbeiten: [Tipps und Tricks](tipps-und-tricks.md) · [Mein Vorgehen und meine Lernerfahrungen](projektbericht.md) · [Aufgabenstatus](aufgabenstatus.md).
