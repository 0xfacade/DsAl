# Datenstrukturen und Algorithmen SoSe 2016, RWTH Aachen
Ziel ist es, gemeinsam möglichst viele der Algorithmen aus der Vorlesung *Datenstrukturen und Algorithmen* zu implementieren, und,
falls mehrere Algorithmen / Datenstrukturen für den selben Zweck vorgestellt wurden, diese auch quantitativ zu vergleichen, um
ein echtes Gefühl für die Wichtigkeit der Wahl einer guten Datenstruktur / eines guten Algorithmus zu bekommen.
Die Besonderheiten einer Datenstruktur / Algorithmus sollten kurz in einem Kommentar erklärt werden und die Komplexität
der relevanten Operationen vermerkt werden.

## Mitmachen
Jeder kann mitmachen! Bitte keine Angst haben.

Weil viele noch keine Erfahrung mit dem Git Workflow, Unit Testing etc. gemacht haben, möchte ich hier
kurz erklären, wie man mit den jeweiligen Tools arbeitet und auf weiterführendes Material verlinken. Die anderen,
die sich hierdurch genervt fühlen, mögen mir vergeben ;)

### Arbeiten mit der Code-Base
Wenn du den Code auf deinem Computer ausprobieren willst, dann kannst du ihn hier herunterladen: 
https://github.com/0xfacade/DsAl/archive/master.zip

Wenn du Veränderungen zu diesem Projekt hinzufügen willst, musst du einen **Pull Request** an dieses Repository stellen.
Dazu benötigst du die Git-Software auf deinem Computer sowie einen Account bei GitHub. Falls du noch nicht weißt, was das ist:
es lohnt sich auf jeden Fall zu lernen, was das ist, denn alles, was heute irgendetwas mit Code zu tun hat, findet
zumindest mit Git statt, und sehr viele OpenSource-Projekte benutzen GitHub. Leider kann ich keine gesamte Erklärung schreiben,
wie man Git und GitHub benutzt, aber hier sind ein paar Stichworte:
- du benötigst einen GitHub Account
- forke das Repository, sodass du eine Kopie davon in deinem eigenen GitHub Account hast
- `git checkout`e deinen Fork auf deinem Computer
- mach deine Veränderungen am Code, speichere den Code
- `git add`: Wähle die zu comittenden Veränderungen aus
- `git commit`: lege einen Commit mit deinen Veränderungen ab
- `git push origin master`: lade deine Veränderungen zu deinem GitHub Respository hoch
- erzeuge einen Pull Request auf dieses Repository, indem du auf die GitHub-Seite deines Forks gehst
- warte auf Feedback auf deinen Pull Request

Hier sind einige Links, die erklären, was die obigen Begriffe bedeuten:
- https://git-scm.com/doc (offizielle Git Doku)
- https://guides.github.com/ (GitHub Guides)

### Projekt öffnen
Dieses Projekt benutzt Java als Programmiersprache und die Eclipse-IDE (http://www.eclipse.org/home/index.php).
Diese solltest du aus der Vorlesung Programmierung kennen - importiere in Eclipse einfach den Ordner, der diesen Code enthält,
als bereits existierendes Projekt.

### Beispiel-Implementierung
In der Vorlesung haben wir z.B. zwei verschiedene Arten verlinkter Listen kennen gelernt: einfach und doppelt verlinkte Listen.
Im Package (Ordner) `com.fbehrens.dsal.lists` finden sich zwei Java-Klassen: `SimpleLinkedList` und `DoublyLinkedList`.
Diese implementieren beide das Interface `AbstractList`, welches sich ebenfalls in diesem Package befindet und sind
jeweils einfache bzw. doppelt verlinkte Listen.

Im Unterpackage `test` finden sich JUnit4-Tests, die sicher stellen sollen, dass die beiden Listen auch das tun, was sie tun
sollen.

Im Unterpackage `competition` finden sich zwei Klassen `ListAsStack` und `ListAppendingAndPrepending`, die
die Performance der beiden Listen testen sollen. `ListAsStack` verwendet jeweils einmal eine einfach verlinkte
Liste und eine doppelt verlinkte Liste, um einen Stack zufällig zu füllen und zu leeren. Hier sollte sich zeigen,
dass beide Listen quasi gleichschnell sind.
`ListAppendingAndPrepending` führt zufällig `append()` und `prepend()` Operationen auf den Listen aus - hier sollte
sich zeigen, dass die doppelt verlinkte Liste viel schneller ist als die einfach verlinkte Liste.

Die beiden zuletzt genannten Klassen aus dem `competition` Unterpackage werden vom `com.fbehrens.dsal.RuntimeComparator` benutzt,
um die Laufzeiten der zwei verschiednen Strukturen zu vergleichen. Im `RuntimeComparator` findet sich auch die `main()`-Methode.
