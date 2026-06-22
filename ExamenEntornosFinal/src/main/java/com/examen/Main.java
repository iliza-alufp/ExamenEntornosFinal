package com.examen;
public class Main {
    public static void main(String[] args) {
        iniciarPartidaSimulada();
    }

    public static void iniciarPartidaSimulada() {
        System.out.println("Inicializando MotorJuego...");
        MotorJuego motor = new MotorJuego();

        System.out.println("Arrancando partida simulada en consola.");
        int puntuacion = 0;
        int vidas = 3;
        java.util.Random rnd = new java.util.Random();

        for (int tick = 1; tick <= 10 && vidas > 0; tick++) {
            System.out.printf("Tick %d - Estado del juego: ", tick);
            int evento = rnd.nextInt(100);

            if (evento < 30) {
                System.out.println("Enemigo aparece. Disparo del jugador.");
                if (rnd.nextBoolean()) {
                    puntuacion += 100;
                    System.out.println("Enemigo destruido. +100 puntos.");
                } else {
                    System.out.println("Disparo fallido.");
                }
            } else if (evento < 50) {
                vidas--;
                System.out.println("Impacto recibido. Vida perdida.");
            } else if (evento < 80) {
                System.out.println("Movimiento del jugador.");
            } else {
                System.out.println("Partida en calma.");
            }

            System.out.printf("Puntuación: %d | Vidas: %d%n", puntuacion, vidas);

            try {
                Thread.sleep(400); // breve pausa para simular tiempo entre ticks
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Simulación interrumpida.");
                break;
            }
        }

        if (vidas <= 0) {
            System.out.println("Partida terminada: jugador eliminado.");
        } else {
            System.out.println("Partida finalizada: simulación completada.");
        }

        System.out.printf("Puntuación final: %d%n", puntuacion);
    }
}