public class NaveJugador extends EntidadJuego {
    public NaveJugador() {
        this.ancho = 16;
        this.alto = 16;
        this.vida = 3;
    }

    @Override
    public void mover() {
        // Movimiento simple del jugador: hacia la derecha
        this.x += 2;
    }

    /**
     * Crea un proyectil salido de la nave jugador y lo registra en el MotorJuego.
     */
    public Proyectil disparar() {
        Proyectil p = new Proyectil();
        p.x = this.x + this.ancho; // sale por la proa
        p.y = this.y + this.alto / 2;
        p.id = MotorJuego.getInstancia() != null ? MotorJuego.getInstancia().generarId() : (int)(System.currentTimeMillis() % Integer.MAX_VALUE);
        System.out.printf("NaveJugador(id=%d) dispara Proyectil(id=%d) en (%d,%d).%n", this.id, p.id, p.x, p.y);

        MotorJuego mg = MotorJuego.getInstancia();
        if (mg != null) {
            mg.addEntidad(p);
        }
        return p;
    }
}