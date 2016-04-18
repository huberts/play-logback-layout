# play-logback-layout
Projekt, który służy do nadpisania domyślnej klasy z pakietu Logback Classic i obsługuje autorską wersję układu logu HTML, opartego na Bootstrapie.

W celu skompilowania nowej wersji, należy:
* Pobrać projekt z GitHub
* Uruchomić <code>activator shell</code>
* Wykonać polecenie <code>package</code>

Wersji release można użyć dzięki wpisowi zależności bibliotecznej w <code>build.sbt</code> (zwróć uwagę, że numer wersji pojawia się aż trzy razy w poniższym kodzie):

<code>libraryDependencies += "com.github.huberts" % "play-logback-layout" % "1.0" from "https://github.com/huberts/play-logback-layout/releases/download/1.0/play-logback-layout_2.11-1.0.jar"</code>
