public class Defensa extends EntidadJuego {
    public Defensa() {
        this.ancho = 20;
        this.alto = 20;
        this.vida = 5;
    }

    @Override
    public void mover() {
        // Defensa estática por defecto; no se desplaza.
    }
}