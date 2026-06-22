package com.examen;
public abstract class EntidadJuego {
    protected int x;
    protected int y;
    protected int ancho;
    protected int alto;
    protected int vida;
    protected int id;

    public EntidadJuego() {
        this.ancho = 10;
        this.alto = 10;
        this.vida = 1;
    }

    /**
     * Actualiza la posición/estado de la entidad en cada tick.
     * Implementado por las subclases según su comportamiento.
     */
    public abstract void mover();

    /**
     * Comprobación AABB simple de colisión entre dos entidades.
     */
    public boolean colisionaCon(EntidadJuego other) {
        if (other == null) return false;
        return this.x < other.x + other.ancho &&
               this.x + this.ancho > other.x &&
               this.y < other.y + other.alto &&
               this.y + this.alto > other.y;
    }

    /**
     * Emite un log descriptivo de la entidad por consola.
     * Puede sobrescribirse en subclases para más detalle.
     */
    public void renderLog() {
        System.out.printf("%s(id=%d) pos=(%d,%d) tam=(%dx%d) vida=%d%n",
                this.getClass().getSimpleName(), id, x, y, ancho, alto, vida);
    }
}