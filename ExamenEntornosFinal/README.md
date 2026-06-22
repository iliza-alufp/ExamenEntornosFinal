# ExamenEntornosFinal

Este proyecto es un juego desarrollado en Java. A continuación se describen los componentes principales del proyecto:

## Estructura del Proyecto

- **src/main/java/com/examen/**: Contiene las clases principales del juego.
  - **Main.java**: Clase principal que contiene el método `main`, punto de entrada de la aplicación.
  - **MotorJuego.java**: Clase que gestiona la lógica del juego.
  - **EntidadJuego.java**: Clase abstracta que sirve como base para otras entidades del juego.
  - **NaveJugador.java**: Clase que representa la nave del jugador.
  - **NaveEnemiga.java**: Clase que representa las naves enemigas.
  - **Defensa.java**: Clase que representa las defensas en el juego.
  - **GestorEntradas.java**: Clase que gestiona las entradas del usuario.
  - **SistemaPuntuacion.java**: Clase que gestiona el sistema de puntuación del juego.
  - **Proyectil.java**: Clase que representa los proyectiles disparados en el juego.

## Dependencias

El proyecto utiliza Maven para la gestión de dependencias y construcción. Asegúrate de tener configurado Maven en tu entorno de desarrollo.

## Instrucciones de Uso

1. Clona el repositorio.
2. Navega a la carpeta del proyecto.
3. Ejecuta `mvn clean install` para construir el proyecto.
4. Ejecuta la clase `Main` para iniciar el juego.

## Contribuciones

Las contribuciones son bienvenidas. Si deseas contribuir, por favor abre un issue o un pull request.



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

arregla todos los errores que tenga los archivos te va a venir en dos partes dejalo funcional

Genera documentación breve (README) con cómo compilar, ejecutar, y las APIs públicas de cada clase.