public class Proyectil extends EntidadJuego {
    public Proyectil() {
        this.ancho = 2;
        this.alto = 2;
        this.vida = 1;
    }

    @Override
    public void mover() {
        // Movimiento por defecto: avanza hacia la derecha
        this.x += 3;
    }
}