# ExamenEntornosFinal — Shooter 2D de consola (simulación)

Temática elegida: simulador simple de combate espacial 2D en consola. El jugador controla una nave que patrulla y dispara, hay naves enemigas con IA básica, defensas/barreras estáticas y proyectiles. El objetivo es ilustrar arquitectura de un motor de juego mínimo y las interacciones entre entidades.

## Arquitectura del software

Justificación y descripción de clases principales:
- MotorJuego: núcleo de la simulación. Gestiona lista de entidades, ticks, actualización (mover, detectar colisiones, aplicar consecuencias) y puntuación. Centraliza creación de ids y referencia al jugador.
- EntidadJuego (abstracta): modelo base para todas las entidades; declara atributos espaciales y físicas, método abstracto mover(), detección AABB y renderLog().
- NaveJugador: entidad controlable; comportamiento simple de movimiento y método disparar() que genera y registra Proyectil en MotorJuego.
- NaveEnemiga: NPC con dos estados (PATRULLAR/ATACAR) y transición según distancia al jugador.
- Defensa: barrera con puntos de vida y método recibirImpacto(int daño).
- Proyectil: objeto móvil con origen (JUGADOR/ENEMIGO), daño y velocidad; lógica de movimiento según origen.
- GestorEntradas: API de control para la simulación (disparar, mover entidades por id, pausar/reanudar).
- SistemaPuntuacion: acumulador de puntos con operaciones sumar/restar/mostrar.
- Main: arranca la simulación de consola (iniciarPartidaSimulada()).

Diseño: MotorJuego orquesta y mantiene la lista de EntidadJuego. Entidades implementan mover() y renderLog(); MotorJuego detecta colisiones AABB y aplica reglas (daño, destrucción, puntuación). GestorEntradas es fachada para acciones externas (simular teclado/ratón).

## Diagrama de Clases UML (Mermaid)

classDiagram
    direction TB

    class EntidadJuego {
        - int x
        - int y
        - int ancho
        - int alto
        - int vida
        - int id
        + void mover()
        + boolean colisionaCon(EntidadJuego other)
        + void renderLog()
    }

    class NaveJugador {
        + NaveJugador()
        + void mover()
        + Proyectil disparar()
    }

    class NaveEnemiga {
        + NaveEnemiga()
        + void mover()
        - Estado estado
        - int patrullaDir
    }

    class Estado {
        <<enumeration>>
        PATRULLAR
        ATACAR
    }

    class Defensa {
        + Defensa()
        + void mover()
        + void recibirImpacto(int dano)
    }

    class Proyectil {
        + Proyectil()
        + Proyectil(Origen origen, int dano, int velocidad)
        + void mover()
        - Origen origen
        - int dano
        - int velocidad
    }

    class Origen {
        <<enumeration>>
        JUGADOR
        ENEMIGO
    }

    class MotorJuego {
        - static MotorJuego instancia
        - List~EntidadJuego~ entidades
        - int tick
        - NaveJugador jugador
        - int nextId
        - boolean pausado
        - SistemaPuntuacion puntuacion
        + MotorJuego()
        + static MotorJuego getInstancia()
        + int generarId()
        + void addEntidad(EntidadJuego e)
        + NaveJugador getJugador()
        + List~EntidadJuego~ getEntidades()
        + void pausar()
        + void reanudar()
        + void actualizar()
    }

    class GestorEntradas {
        - boolean pausado
        + GestorEntradas()
        + void pulsarBotonAccion()
        + void desplazarEntidad(String idEntidad, String direccion)
        + void pausar()
        + void reanudar()
    }

    class SistemaPuntuacion {
        - int puntos
        + SistemaPuntuacion()
        + void sumar(int pts)
        + void restar(int pts)
        + int mostrarPuntuacion()
    }

    MotorJuego "1" o-- "0..*" EntidadJuego : gestiona
    EntidadJuego <|-- NaveJugador
    EntidadJuego <|-- NaveEnemiga
    EntidadJuego <|-- Defensa

## Diagrama de Casos de Uso UML (Mermaid)

usecaseDiagram
    actor Jugador

    Jugador --> (Iniciar Partida)
    Jugador --> (Disparar)
    Jugador --> (Pausar/Reanudar)
    Jugador --> (Mover Jugador)

    rectangle Sistema {
        (Iniciar Partida)
        (Disparar)
        (Pausar/Reanudar)
        (Mover Jugador)
    }


## Especificación de Casos de Uso 

Caso de uso 1:
Nombre: CU-01 Iniciar Partida  
Objetivo: Inicializar MotorJuego, cargar entidades iniciales y arrancar la simulación por consola.  
Actor Principal: Jugador  
Precondiciones: No existe una partida en curso; JDK disponible y programa compilado.  
Flujo Principal: Paso 1: Ejecutar Main.main(). Paso 2: Main llama a iniciarPartidaSimulada(). Paso 3: MotorJuego se instancia y crea entidades iniciales (jugador, enemigos, defensas, proyectiles de ejemplo). Paso 4: Bucle de ticks ejecuta MotorJuego.actualizar() periódicamente mostrando logs por consola. Paso 5: Finaliza cuando se cumple condición de parada (ticks máximos o jugador destruido).  
Flujos Alternativos: Si MotorJuego no puede inicializarse, mostrar error y terminar; si se pulsa pausar, la simulación detiene ticks hasta reanudar.  
Postcondiciones: MotorJuego detenido; estado final de entidades mostrado; puntuación final disponible.  
Reglas de Negocio: No se puede iniciar una nueva partida si ya existe MotorJuego.instancia (evitar doble instanciación).

Caso de uso 2:
Nombre: CU-02 Disparar  
Objetivo: El jugador dispara un proyectil que puede impactar enemigos o defensas.  
Actor Principal: Jugador  
Precondiciones: Partida en curso; existe instancia de MotorJuego y NaveJugador viva.  
Flujo Principal: Paso 1: El jugador invoca GestorEntradas.pulsarBotonAccion() o el Main/automatización llama a NaveJugador.disparar(). Paso 2: NaveJugador.disparar() crea Proyectil con origen JUGADOR, asigna posición y id y registra en MotorJuego.addEntidad(). Paso 3: En el siguiente tick, MotorJuego.actualizar() mueve Proyectil y comprueba colisiones. Paso 4: Si colisiona con NaveEnemiga o Defensa, aplicar daño y eliminar entidades según reglas. Paso 5: Actualizar SistemaPuntuacion si el enemigo muere.  
Flujos Alternativos: Si no hay jugador (muerto), pulsarBotonAccion() informa y no crea proyectil. Si MotorJuego está pausado, la creación puede ocurrir pero movimiento/colisión se retrasan hasta reanudar.  
Postcondiciones: Proyectil registrado; posibles efectos aplicados (daño, destrucción, puntos).  
Reglas de Negocio: Proyectiles de jugador solo dañan naves enemigas y defensas; un proyectil desaparece al impactar.

## Cómo compilar y ejecutar

Requisitos: JDK 11+ y opcionalmente Maven.

Con Maven (desde la raíz del proyecto):
- Compilar: mvn clean package
- Ejecutar: mvn -q -Dexec.mainClass="com.examen.Main" org.codehaus.mojo:exec-maven-plugin:3.1.0:java

Sin Maven (javac + java) — Windows CMD:
- cd d:\DATA2\Examen\ExamenEntornosFinal\ExamenEntornosFinal\src\main\java
- javac -d d:\DATA2\Examen\ExamenEntornosFinal\ExamenEntornosFinal\target\classes com\examen\*.java
- cd d:\DATA2\Examen\ExamenEntornosFinal\ExamenEntornosFinal\target\classes
- java com.examen.Main

Nota: si usas un IDE (IntelliJ/VSCode) importa el proyecto Maven o marca src/main/java como source root y ejecuta com.examen.Main.

## APIs públicas

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
  - public void mover()
  - public Proyectil disparar()

- com.examen.NaveEnemiga
  - public NaveEnemiga()
  - public void mover()

- com.examen.Defensa
  - public Defensa()
  - public void mover()
  - public void recibirImpacto(int dano)

- com.examen.Proyectil
  - public enum Origen { JUGADOR, ENEMIGO }
  - public Proyectil()
  - public Proyectil(Origen origen, int dano, int velocidad)
  - public void mover()
  - public Origen origen
  - public int dano
  - public int velocidad

- com.examen.GestorEntradas
  - public GestorEntradas()
  - public void pulsarBotonAccion()
  - public void desplazarEntidad(String idEntidad, String direccion)
  - public void pausar()
  - public void reanudar()

- com.examen.SistemaPuntuacion
  - public SistemaPuntuacion()
  - public void sumar(int pts)
  - public void restar(int pts)
  - public int mostrarPuntuacion()

# Bitácora del Uso de Inteligencia Artificial

Prompts usados:

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

Añade al readme  un Diagrama de Clases UML: Generado obligatoriamente usando código Mermaid o PlantUML e incrustado en el Markdown. Debe reflejar atributos privados, métodos públicos y relaciones (Asociación, Herencia, etc.).


Control de Errores de la IA
Durante el desarrollo, la IA cometió errores como:

Crear clases sin package com.examen;

No incluir métodos pausar() y reanudar() en MotorJuego aunque GestorEntradas los llamaba

Generar colisiones que eliminaban al jugador sin control

Estos errores fueron corregidos manualmente y mediante nuevas instrucciones precisas.

# Reflexión Crítica
El uso de IA acelera el desarrollo y reduce errores mecánicos, pero también puede:

Introducir inconsistencias entre clases

Generar código no solicitado

Omitir requisitos del enunciado

La supervisión humana sigue siendo esencial para garantizar coherencia, cumplimiento del examen y calidad del software.

## Notas finales y recomendaciones
- Los logs por consola (System.out.printf) están diseñados para facilitar la observación del comportamiento sin interfaz gráfica.
- Para probar interacciones rápidas, ejecutar Main y usar GestorEntradas desde un pequeño programa de pruebas o ampliando Main para llamar a sus métodos en momentos concretos.
- Ajustes fáciles: valores de velocidad, daño, rangos de detección de IA y vida de defensas se encuentran en las clases respectivas.




