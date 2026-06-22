import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MotorJuego {
    private List<EntidadJuego> entidades = new ArrayList<>();
    private int tick = 0;

    public MotorJuego() {
        // Inicializa algunas entidades de ejemplo (se usan referencias al tipo base
        // para poder acceder a los campos protected definidos en EntidadJuego)
        EntidadJuego jugador = new NaveJugador();
        jugador.x = 10;
        jugador.y = 50;
        jugador.id = 1;

        EntidadJuego enemigo1 = new NaveEnemiga();
        enemigo1.x = 100;
        enemigo1.y = 50;
        enemigo1.id = 2;

        EntidadJuego enemigo2 = new NaveEnemiga();
        enemigo2.x = 140;
        enemigo2.y = 60;
        enemigo2.id = 3;

        EntidadJuego proyectil = new Proyectil();
        proyectil.x = 12;
        proyectil.y = 50;
        proyectil.id = 4;

        EntidadJuego defensa = new Defensa();
        defensa.x = 200;
        defensa.y = 100;
        defensa.id = 5;

        entidades.add(jugador);
        entidades.add(enemigo1);
        entidades.add(enemigo2);
        entidades.add(proyectil);
        entidades.add(defensa);

        System.out.println("MotorJuego inicializado con " + entidades.size() + " entidades.");
    }

    /**
     * Recorre las entidades, actualiza sus posiciones según su tipo y emite
     * logs explicativos por consola. También detecta colisiones simples
     * proyectil-enemigo y elimina entidades que salen del área o resultan
     * impactadas.
     */
    public void actualizar() {
        tick++;
        System.out.println("=== Tick " + tick + " - Actualizando entidades ===");

        List<EntidadJuego> aEliminar = new ArrayList<>();
        Iterator<EntidadJuego> it = entidades.iterator();

        while (it.hasNext()) {
            EntidadJuego e = it.next();
            int oldX = e.x;
            int oldY = e.y;

            if (e instanceof NaveJugador) {
                // Movimiento del jugador: se mueve a la derecha
                e.x += 2;
                System.out.printf("NaveJugador(id=%d): (%d,%d) -> (%d,%d)%n", e.id, oldX, oldY, e.x, e.y);
            } else if (e instanceof NaveEnemiga) {
                // Enemigos se desplazan hacia la izquierda
                e.x -= 1;
                System.out.printf("NaveEnemiga(id=%d): (%d,%d) -> (%d,%d)%n", e.id, oldX, oldY, e.x, e.y);
                // eliminarlos si salen del área (ej. x < 0)
                if (e.x < 0) {
                    System.out.printf("NaveEnemiga(id=%d) ha salido del área y será eliminada.%n", e.id);
                    aEliminar.add(e);
                }
            } else if (e instanceof Proyectil) {
                // Proyectil avanza rápidamente hacia la derecha
                e.x += 3;
                System.out.printf("Proyectil(id=%d): (%d,%d) -> (%d,%d)%n", e.id, oldX, oldY, e.x, e.y);

                // Colisión simple: si está cerca de una nave enemiga, eliminar ambos
                for (EntidadJuego objetivo : entidades) {
                    if (objetivo != e && objetivo instanceof NaveEnemiga) {
                        if (Math.abs(objetivo.x - e.x) <= 2 && Math.abs(objetivo.y - e.y) <= 2) {
                            System.out.printf("Colisión detectada: Proyectil(id=%d) impacta NaveEnemiga(id=%d).%n", e.id, objetivo.id);
                            aEliminar.add(e);
                            aEliminar.add(objetivo);
                        }
                    }
                }
                // eliminar proyectil si sale mucho del área (ej. x > 1000)
                if (e.x > 1000) {
                    aEliminar.add(e);
                }
            } else if (e instanceof Defensa) {
                // Defensa está estática por defecto
                System.out.printf("Defensa(id=%d) permanece en (%d,%d).%n", e.id, e.x, e.y);
            } else {
                System.out.printf("Entidad desconocida(id=%d) en (%d,%d).%n", e.id, e.x, e.y);
            }
        }

        // Eliminar entidades marcadas (evitar ConcurrentModification)
        for (EntidadJuego rem : aEliminar) {
            if (entidades.remove(rem)) {
                System.out.printf("Entidad(id=%d) eliminada del motor.%n", rem.id);
            }
        }

        System.out.println("=== Fin tick " + tick + " - Entidades restantes: " + entidades.size() + " ===");
    }

    // Accesores útiles para pruebas o para que Main interactúe con el motor
    public List<EntidadJuego> getEntidades() {
        return entidades;
    }
}