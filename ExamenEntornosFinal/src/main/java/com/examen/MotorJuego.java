package com.examen;

import java.util.ArrayList;
import java.util.List;

public class MotorJuego {
    private static MotorJuego instancia;
    private List<EntidadJuego> entidades = new ArrayList<>();
    private int tick = 0;
    private NaveJugador jugador;
    private int nextId = 100;

    public MotorJuego() {
        instancia = this;

        this.jugador = new NaveJugador();
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

    public static MotorJuego getInstancia() {
        return instancia;
    }

    public int generarId() {
        return nextId++;
    }

    public void addEntidad(EntidadJuego e) {
        if (e != null) {
            entidades.add(e);
            System.out.printf("Entidad registrada: %s(id=%d)%n",
                    e.getClass().getSimpleName(), e.id);
        }
    }

    public NaveJugador getJugador() {
        return jugador;
    }

    public List<EntidadJuego> getEntidades() {
        return entidades;
    }

    public void actualizar() {
        tick++;
        System.out.println("=== Tick " + tick + " - Actualizando entidades ===");

        List<EntidadJuego> aEliminar = new ArrayList<>();

        for (EntidadJuego e : new ArrayList<>(entidades)) {
            int oldX = e.x;
            int oldY = e.y;

            e.mover();
            System.out.printf("%s(id=%d): (%d,%d) -> (%d,%d)%n",
                    e.getClass().getSimpleName(), e.id, oldX, oldY, e.x, e.y);

            if (e instanceof NaveEnemiga) {
                if (e.x < 0) {
                    System.out.printf("NaveEnemiga(id=%d) ha salido del área y será eliminada.%n", e.id);
                    aEliminar.add(e);
                }
            } else if (e instanceof Proyectil) {
                for (EntidadJuego objetivo : entidades) {
                    if (objetivo != e && objetivo instanceof NaveEnemiga) {
                        if (e.colisionaCon(objetivo)) {
                            System.out.printf("Colisión detectada: Proyectil(id=%d) impacta NaveEnemiga(id=%d).%n",
                                    e.id, objetivo.id);
                            aEliminar.add(e);
                            aEliminar.add(objetivo);
                        }
                    }
                }
                if (e.x > 1000) {
                    aEliminar.add(e);
                }
            }
        }

        for (EntidadJuego rem : aEliminar) {
            if (entidades.remove(rem)) {
                System.out.printf("Entidad(id=%d) eliminada del motor.%n", rem.id);
            }
            if (rem == jugador) {
                jugador = null;
            }
        }

        System.out.println("=== Fin tick " + tick + " - Entidades restantes: " + entidades.size() + " ===");
    }
}
