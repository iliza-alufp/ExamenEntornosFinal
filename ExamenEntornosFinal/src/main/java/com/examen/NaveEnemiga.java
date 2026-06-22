public class NaveEnemiga extends EntidadJuego {
    public NaveEnemiga() {
        this.ancho = 14;
        this.alto = 14;
        this.vida = 1;
    }

    @Override
    public void mover() {
        // Enemigo se desplaza hacia la izquierda
        this.x -= 2;
    }
}