public class NaveEnemiga extends EntidadJuego {
    private enum Estado { PATRULLAR, ATACAR }
    private Estado estado = Estado.PATRULLAR;
    private int patrullaDir = -1; // -1 izquierda, +1 derecha

    public NaveEnemiga() {
        this.ancho = 14;
        this.alto = 14;
        this.vida = 1;
    }

    @Override
    public void mover() {
        // Busca jugador y decide estado
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

        // Comportamientos por estado
        if (estado == Estado.PATRULLAR) {
            // Movimiento de patrulla horizontal sencillo
            this.x += patrullaDir * 1;
            // cambia de dirección si alcanza ciertos límites
            if (this.x < 50) patrullaDir = 1;
            if (this.x > 300) patrullaDir = -1;
        } else {
            // ATACAR: moverse hacia la posición del jugador
            if (jugador != null) {
                if (jugador.x < this.x) this.x -= 2;
                else if (jugador.x > this.x) this.x += 1;
                if (jugador.y < this.y) this.y -= 1;
                else if (jugador.y > this.y) this.y += 1;
            } else {
                // sin jugador, patrullar
                this.x += patrullaDir * 1;
            }
        }
    }
}