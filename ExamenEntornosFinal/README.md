# ExamenEntornosFinal — Shooter 2D de Consola

Simulación de un **juego de combate espacial 2D** ejecutado íntegramente en consola.  
El jugador controla una nave que se desplaza y dispara, mientras interactúa con enemigos, defensas y proyectiles.  
El objetivo es demostrar la arquitectura de un motor de juego minimalista, orientado a objetos y con detección de colisiones.

---

# Arquitectura del Software

El proyecto implementa un motor de juego básico compuesto por **9 clases principales**, cada una con una responsabilidad clara:

### MotorJuego
Núcleo del sistema. Gestiona:
- Lista de entidades
- Ticks de simulación
- Movimiento y actualización
- Colisiones
- Puntuación
- Pausa/Reanudación
- Generación de IDs

### EntidadJuego (abstracta)
Clase base para todas las entidades del juego.  
Define:
- Posición
- Tamaño
- Vida
- ID
- Método abstracto `mover()`
- Detección de colisiones AABB
- Método `renderLog()`

### NaveJugador
Entidad controlable por el jugador.  
Incluye:
- Movimiento simple
- Método `disparar()` que crea un proyectil y lo registra en MotorJuego

### NaveEnemiga
NPC con IA básica:
- Estado PATRULLAR
- Estado ATACAR (si está cerca del jugador)
- Movimiento horizontal o persecución

### Defensa
Entidad estática que actúa como barrera.  
Incluye:
- Vida
- Método `recibirImpacto(int daño)`

### Proyectil
Objeto móvil con:
- Origen (JUGADOR / ENEMIGO)
- Daño
- Velocidad
- Movimiento según origen

### GestorEntradas
Fachada para simular controles:
- Disparar
- Mover entidades por ID
- Pausar/Reanudar

### SistemaPuntuacion
Acumulador de puntos:
- sumar()
- restar()
- mostrarPuntuacion()

### Main
Punto de entrada.  
Ejecuta una simulación de partida por consola.

---

# Diagrama de Clases UML (Mermaid)

```mermaid
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
        + boolean colisionaCon(EntidadJuego)
        + void renderLog()
    }

    class NaveJugador {
        + NaveJugador()
        + void mover()
        + Proyectil disparar()
    }

    class NaveEnemiga {
        - enum Estado {PATRULLAR, ATACAR}
        - Estado estado
        - int patrullaDir
        + NaveEnemiga()
        + void mover()
    }

    class Defensa {
        + Defensa()
        + void mover()
        + void recibirImpacto(int)
    }

    class Proyectil {
        + enum Origen {JUGADOR, ENEMIGO}
        - Origen origen
        - int dano
        - int velocidad
        + Proyectil()
        + Proyectil(Origen,int,int)
        + void mover()
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
        + void addEntidad(EntidadJuego)
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
        + void desplazarEntidad(String,String)
        + void pausar()
        + void reanudar()
    }

    class SistemaPuntuacion {
        - int puntos
        + SistemaPuntuacion()
        + void sumar(int)
        + void restar(int)
        + int mostrarPuntuacion()
    }

    MotorJuego "1" o-- "0..*" EntidadJuego : gestiona >
    EntidadJuego <|-- NaveJugador
    EntidadJuego <|-- NaveEnemiga
    EntidadJuego <|-- Defensa
    EntidadJuego <|-- Proyectil
    MotorJuego o-- SistemaPuntuacion : usa >
    GestorEntradas ..> MotorJuego : invoca >
    NaveJugador ..> Proyectil : crea >
    actor Jugador

  rectangle Sistema {
    Jugador --> (CU-01 Iniciar Partida)
    Jugador --> (CU-02 Disparar)
    Jugador --> (CU-03 Pausar/Reanudar)
    Jugador --> (CU-04 Mover Jugador)
  }

Especificación de Casos de Uso
Caso de uso 1
Nombre: CU-01 Iniciar Partida
Objetivo: Inicializar MotorJuego, cargar entidades iniciales y arrancar la simulación por consola.
Actor Principal: Jugador
Precondiciones: No existe una partida en curso; JDK disponible y programa compilado.
Flujo Principal:

Ejecutar Main.main().

Main llama a iniciarPartidaSimulada().

MotorJuego se instancia y crea entidades iniciales.

Se ejecuta un bucle de ticks llamando a MotorJuego.actualizar().

Finaliza cuando se cumplen condiciones de parada.
Flujos Alternativos:

Si MotorJuego no puede inicializarse, mostrar error y terminar.

Si se pulsa pausar, la simulación se detiene hasta reanudar.
Postcondiciones: MotorJuego detenido; estado final mostrado; puntuación final disponible.
Reglas de Negocio: No se puede iniciar una nueva partida si ya existe MotorJuego.instancia.

Caso de uso 2
Nombre: CU-02 Disparar
Objetivo: El jugador dispara un proyectil que puede impactar enemigos o defensas.
Actor Principal: Jugador
Precondiciones: Partida en curso; existe MotorJuego y NaveJugador viva.
Flujo Principal:

El jugador invoca GestorEntradas.pulsarBotonAccion().

NaveJugador.disparar() crea un proyectil.

MotorJuego registra el proyectil.

En el siguiente tick, el proyectil se mueve y se detectan colisiones.

Si impacta, se aplican daños y se actualiza la puntuación.
Flujos Alternativos:

Si no hay jugador, no se dispara.

Si el juego está pausado, el proyectil se crea pero no se mueve hasta reanudar.
Postcondiciones: Proyectil registrado; posibles efectos aplicados.
Reglas de Negocio: Proyectiles del jugador solo dañan enemigos y defensas.


Cómo compilar y ejecutar
Con Maven
Código
mvn clean package
mvn -q -Dexec.mainClass="com.examen.Main" org.codehaus.mojo:exec-maven-plugin:3.1.0:java
Sin Maven (Windows CMD)
Código
cd src/main/java
javac -d ../../target/classes com/examen/*.java
cd ../../target/classes
java com.examen.Main
APIs públicas
com.examen.Main
public static void main(String[] args)

public static void iniciarPartidaSimulada()

com.examen.MotorJuego
public MotorJuego()

public static MotorJuego getInstancia()

public int generarId()

public void addEntidad(EntidadJuego e)

public NaveJugador getJugador()

public List<EntidadJuego> getEntidades()

public void pausar()

public void reanudar()

public void actualizar()

com.examen.EntidadJuego
public abstract void mover()

public boolean colisionaCon(EntidadJuego other)

public void renderLog()

com.examen.NaveJugador
public NaveJugador()

public void mover()

public Proyectil disparar()

com.examen.NaveEnemiga
public NaveEnemiga()

public void mover()

com.examen.Defensa
public Defensa()

public void mover()

public void recibirImpacto(int dano)

com.examen.Proyectil
public Proyectil()

public Proyectil(Origen origen, int dano, int velocidad)

public void mover()

com.examen.GestorEntradas
public GestorEntradas()

public void pulsarBotonAccion()

public void desplazarEntidad(String idEntidad, String direccion)

public void pausar()

public void reanudar()

com.examen.SistemaPuntuacion
public SistemaPuntuacion()

public void sumar(int pts)

public void restar(int pts)

public int mostrarPuntuacion()


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

Reflexión Crítica
El uso de IA acelera el desarrollo y reduce errores mecánicos, pero también puede:

Introducir inconsistencias entre clases

Generar código no solicitado

Omitir requisitos del enunciado

La supervisión humana sigue siendo esencial para garantizar coherencia, cumplimiento del examen y calidad del software.