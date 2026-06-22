package com.examen;

public class GestorEntradas {
    private boolean pausado = false;

    public GestorEntradas() {
    }

    public void pulsarBotonAccion() {
        MotorJuego mg = MotorJuego.getInstancia();
        if (mg == null) {
            System.out.println("GestorEntradas: MotorJuego no inicializado.");
            return;
        }
        if (mg.getJugador() != null) {
            System.out.println("GestorEntradas: botón acción pulsado -> jugador dispara.");
            mg.getJugador().disparar();
        } else {
            System.out.println("GestorEntradas: no hay jugador para disparar.");
        }
    }

    /**
     * Desplaza una entidad por id (cadena) en la dirección indicada.
     * idEntidad puede ser "jugador" o el id numérico en texto.
     * direcciones válidas: "ARRIBA","ABAJO","IZQUIERDA","DERECHA"
     */
    public void desplazarEntidad(String idEntidad, String direccion) {
        MotorJuego mg = MotorJuego.getInstancia();
        if (mg == null) {
            System.out.println("GestorEntradas: MotorJuego no inicializado.");
            return;
        }

        EntidadJuego objetivo = null;
        if ("jugador".equalsIgnoreCase(idEntidad) || "player".equalsIgnoreCase(idEntidad)) {
            objetivo = mg.getJugador();
        } else {
            try {
                int id = Integer.parseInt(idEntidad);
                for (EntidadJuego e : mg.getEntidades()) {
                    if (e.id == id) { objetivo = e; break; }
                }
            } catch (NumberFormatException ex) {
                System.out.println("GestorEntradas: id inválido: " + idEntidad);
                return;
            }
        }

        if (objetivo == null) {
            System.out.println("GestorEntradas: entidad no encontrada: " + idEntidad);
            return;
        }

        switch (direccion.toUpperCase()) {
            case "ARRIBA": objetivo.y -= 5; break;
            case "ABAJO": objetivo.y += 5; break;
            case "IZQUIERDA": objetivo.x -= 5; break;
            case "DERECHA": objetivo.x += 5; break;
            default:
                System.out.println("GestorEntradas: dirección desconocida: " + direccion);
                return;
        }
        System.out.printf("GestorEntradas: Entidad(id=%d) movida a (%d,%d)%n", objetivo.id, objetivo.x, objetivo.y);
    }

    public void pausar() {
        MotorJuego mg = MotorJuego.getInstancia();
        if (mg == null) { System.out.println("GestorEntradas: MotorJuego no inicializado."); return; }
        mg.pausar();
        pausado = true;
        System.out.println("GestorEntradas: juego pausado.");
    }

    public void reanudar() {
        MotorJuego mg = MotorJuego.getInstancia();
        if (mg == null) { System.out.println("GestorEntradas: MotorJuego no inicializado."); return; }
        mg.reanudar();
        pausado = false;
        System.out.println("GestorEntradas: juego reanudado.");
    }
}