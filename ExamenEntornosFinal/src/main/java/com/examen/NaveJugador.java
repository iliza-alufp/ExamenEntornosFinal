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
}