package com.examen;

public class SistemaPuntuacion {

    private int puntos = 0;

    public void sumar(int pts) {
        if (pts <= 0) return;
        puntos += pts;
        System.out.printf("SistemaPuntuacion: +%d puntos (total=%d)%n", pts, puntos);
    }

    public void restar(int pts) {
        if (pts <= 0) return;
        puntos -= pts;
        if (puntos < 0) puntos = 0;
        System.out.printf("SistemaPuntuacion: -%d puntos (total=%d)%n", pts, puntos);
    }

    public int mostrarPuntuacion() {
        System.out.printf("SistemaPuntuacion: puntuación actual = %d%n", puntos);
        return puntos;
    }
}
