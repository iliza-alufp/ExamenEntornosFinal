public class Proyectil extends EntidadJuego {
    public enum Origen { JUGADOR, ENEMIGO }

    public Origen origen = Origen.JUGADOR;
    public int dano = 1;
    public int velocidad = 3;

    public Proyectil() {
        this.ancho = 2;
        this.alto = 2;
        this.vida = 1;
    }

    public Proyectil(Origen origen, int dano, int velocidad) {
        this();
        this.origen = origen;
        this.dano = dano;
        this.velocidad = velocidad;
    }

    @Override
    public void mover() {
        // Movimiento según el origen: jugador -> derecha, enemigo -> izquierda
        if (this.origen == Origen.JUGADOR) {
            this.x += velocidad;
        } else {
            this.x -= velocidad;
        }
    }
}