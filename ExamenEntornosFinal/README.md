# ExamenEntornosFinal

Proyecto de ejemplo: motor de juego simple en Java (paquete `com.examen`). Proporciona entidades básicas (jugador, enemigos, defensas, proyectiles), un motor que actualiza la simulación y utilidades para entradas y puntuación. El objetivo es servir como ejercicio/plantilla para prácticas.

## Requisitos
- JDK 11+ instalado y configurado en PATH (javac/java).
- Maven (opcional para build/packaging) en PATH.
- Entorno Windows (comandos mostrados en ejemplos).

## Estructura principal
- src/main/java/com/examen/
  - Main.java
  - MotorJuego.java
  - EntidadJuego.java
  - NaveJugador.java
  - NaveEnemiga.java
  - Defensa.java
  - GestorEntradas.java
  - SistemaPuntuacion.java
  - Proyectil.java
- README.md (este archivo)

## Cómo compilar y ejecutar

Usando Maven (recomendado si existe pom.xml):

1. Compilar:
   - Desde la raíz del proyecto:
     - mvn clean package
2. Ejecutar desde clases compiladas:
   - mvn -q -Dexec.mainClass="com.examen.Main" org.codehaus.mojo:exec-maven-plugin:3.1.0:java
   (si no está configurado el plugin en pom.xml, usar la siguiente opción)
3. Ejecutar con java directamente:
   - mvn -q -DskipTests package
   - java -cp target/classes;target/dependency/* com.examen.Main
   (en Windows usar `;` como separador de classpath)

Sin Maven (javac + java):

1. Compilar:
   - cd src/main/java
   - javac -d ../../classes com/examen/*.java
2. Ejecutar:
   - cd ../../classes
   - java com.examen.Main

Ejemplo rápido (Windows CMD usando clases compiladas manualmente):
- cd d:\DATA2\Examen\ExamenEntornosFinal\ExamenEntornosFinal\src\main\java
- javac -d d:\DATA2\Examen\ExamenEntornosFinal\ExamenEntornosFinal\target\classes com\examen\*.java
- cd d:\DATA2\Examen\ExamenEntornosFinal\ExamenEntornosFinal\target\classes
- java com.examen.Main

## Uso básico
- La clase `Main` inicializa `MotorJuego` y arranca una simulación por consola (`iniciarPartidaSimulada()`).
- `MotorJuego.actualizar()` realiza un tick de simulación: mueve entidades, detecta colisiones y aplica efectos (daño, destrucción, puntuación).
- `GestorEntradas` ofrece métodos para interactuar con la simulación (disparo del jugador, mover entidades, pausar/reanudar).
- `SistemaPuntuacion` mantiene y muestra la puntuación en consola.

## APIs públicas (resumen por clase)
El listado muestra los métodos y campos públicos más relevantes (firma aproximada). Todas las clases están en el paquete `com.examen`.

- com.examen.Main
  - public static void main(String[] args)
  - public static void iniciarPartidaSimulada()

- com.examen.MotorJuego
  - public MotorJuego()
  - public static MotorJuego getInstancia()
  - public int generarId()
  - public void addEntidad(EntidadJuego e)
  - public NaveJugador getJugador()
  - public java.util.List<EntidadJuego> getEntidades()
  - public void pausar()
  - public void reanudar()
  - public void actualizar()

- com.examen.EntidadJuego (abstracta)
  - protected int x, y, ancho, alto, vida, id
  - public abstract void mover()
  - public boolean colisionaCon(EntidadJuego other)
  - public void renderLog()

- com.examen.NaveJugador
  - public NaveJugador()
  - @Override public void mover()
  - public Proyectil disparar()    // crea un proyectil y lo registra en MotorJuego

- com.examen.NaveEnemiga
  - public NaveEnemiga()
  - @Override public void mover()  // comportamiento PATRULLAR / ATACAR en base a distancia al jugador

- com.examen.Defensa
  - public Defensa()
  - @Override public void mover()
  - (en algunas versiones puede existir) public void recibirImpacto(int dano)

- com.examen.Proyectil
  - public enum Origen { JUGADOR, ENEMIGO }
  - public Proyectil()
  - public Proyectil(Origen origen, int dano, int velocidad)
  - public Origen origen
  - public int dano
  - public int velocidad
  - @Override public void mover()  // avanza según origen y velocidad

- com.examen.GestorEntradas
  - public GestorEntradas()
  - public void pulsarBotonAccion()                       // hace que el jugador dispare
  - public void desplazarEntidad(String idEntidad, String direccion)
  - public void pausar()
  - public void reanudar()

- com.examen.SistemaPuntuacion
  - public SistemaPuntuacion()
  - public void sumar(int pts)
  - public void restar(int pts)
  - public int mostrarPuntuacion()

Notas:
- Las clases manejan logs por consola (System.out.printf) para informar sobre movimientos, colisiones, daños y puntuación.
- Identificadores de entidades (`id`) son enteros asignados por `MotorJuego.generarId()` si no se establecen manualmente.

## Diseño y comportamiento relevantes
- Colisiones: detección AABB (rectángulos) mediante `EntidadJuego.colisionaCon()`. `MotorJuego` itera pares y aplica efectos:
  - Proyectil JUGADOR impacta `NaveEnemiga` → reduce vida del enemigo, elimina proyectil y suma puntos si muere.
  - Proyectil ENEMIGO impacta `NaveJugador` → reduce vida del jugador.
  - Proyectil impacta `Defensa` → llama `recibirImpacto`.
  - Colisiones genéricas (jugador-enemigo) reducen vidas de ambos.
- `NaveEnemiga` alterna entre patrullar y atacar según distancia al jugador.
- `NaveJugador.disparar()` crea `Proyectil` posicionado delante de la nave y lo registra en `MotorJuego`.

## Recomendaciones para pruebas
- Ejecutar `Main` y observar logs por consola.
- Usar `GestorEntradas` para provocar disparos (`pulsarBotonAccion`) y mover entidades manualmente.
- Ajustar parámetros (velocidades, daño, rangos) directamente en las clases si se desea experimentar.

## Cómo contribuir
- Abrir un issue o pull request con cambios en el repositorio.
- Mantener coherencia de paquetes y tests si se añaden.

## Prompts usados

Genera el esqueleto Java de las 9 clases descritas (Main, MotorJuego, EntidadJuego, NaveJugador, NaveEnemiga, Defensa, GestorEntradas, SistemaPuntuacion, Proyectil) respetando la estructura del workspace.

Crea el archivo Main.java con un método main que inicialice MotorJuego y arranque una partida simulada por consola.

Implementa MotorJuego con un método actualizar() que recorra entidades, actualice posiciones y emita logs explicativos por consola.

Define EntidadJuego como clase abstracta con atributos x,y,ancho,alto,vida y métodos mover(), colisionaCon(EntidadJuego) y renderLog().

Implementa NaveJugador que herede de EntidadJuego e incluya método disparar() que cree un Proyectil de jugador y lo registre en MotorJuego.

Implementa NaveEnemiga que herede de EntidadJuego con comportamiento NPC básico: estados 'PATRULLAR' y 'ATACAR' y transición según distancia al jugador.

Crea la clase Defensa que actúe como barrera con puntos de vida y método recibirImpacto(int daño) que reduzca estado y emita log.

Implementa GestorEntradas con métodos públicos: pulsarBotonAccion(), desplazarEntidad(String idEntidad, String direccion) y pausar/reanudar.

Añade SistemaPuntuacion con métodos sumar(int puntos), restar(int puntos) y mostrarPuntuacion().

Crea Proyectil con origen (JUGADOR/ENEMIGO), daño, velocidad y lógica de movimiento en actualizar().

Implementa la detección de colisiones simple en MotorJuego: comparar rectángulos (x,y,w,h) y aplicar consecuencias (dañar, destruir, sumar puntos).

Arregla todos los errores que tenga los archivos te va a venir en dos partes dejalo funcional

En readme completa la documentacion, añade además con cómo compilar, ejecutar, y las APIs públicas de cada clase.



