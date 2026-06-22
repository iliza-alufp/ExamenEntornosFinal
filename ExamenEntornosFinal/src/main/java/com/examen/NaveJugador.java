package com.examen;

public class NaveJugador extends EntidadJuego {

    public NaveJugador() {
        this.ancho = 16;
        this.alto = 16;
        this.vida = 3;
    }

    @Override
    public void mover() {
        this.x += 2;
    }

    public Proyectil disparar() {
        Proyectil p = new Proyectil();
        p.x = this.x + this.ancho;
        p.y = this.y + this.alto / 2;

        MotorJuego mg = MotorJuego.getInstancia();
        p.id = mg != null ? mg.generarId() : (int)(System.currentTimeMillis() % Integer.MAX_VALUE);

        System.out.printf("NaveJugador(id=%d) dispara Proyectil(id=%d) en (%d,%d).%n",
                this.id, p.id, p.x, p.y);

        if (mg != null) {
            mg.addEntidad(p);
        }

        return p;
    }
}
