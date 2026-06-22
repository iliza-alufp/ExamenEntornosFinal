package com.examen;

public class NaveEnemiga extends EntidadJuego {

    private enum Estado { PATRULLAR, ATACAR }

    private Estado estado = Estado.PATRULLAR;
    private int patrullaDir = -1;

    public NaveEnemiga() {
        this.ancho = 14;
        this.alto = 14;
        this.vida = 1;
    }

    @Override
    public void mover() {
        MotorJuego mg = MotorJuego.getInstancia();
        NaveJugador jugador = mg != null ? mg.getJugador() : null;

        if (jugador != null) {
            int dx = jugador.x - this.x;
            int dy = jugador.y - this.y;
            double distancia = Math.hypot(dx, dy);

            if (distancia < 80) {
                estado = Estado.ATACAR;
            } else {
                estado = Estado.PATRULLAR;
            }
        } else {
            estado = Estado.PATRULLAR;
        }

        if (estado == Estado.PATRULLAR) {
            this.x += patrullaDir;
            if (this.x < 50) patrullaDir = 1;
            if (this.x > 300) patrullaDir = -1;
        } else {
            if (jugador != null) {
                if (jugador.x < this.x) this.x -= 2;
                else if (jugador.x > this.x) this.x += 1;

                if (jugador.y < this.y) this.y -= 1;
                else if (jugador.y > this.y) this.y += 1;
            }
        }
    }
}
