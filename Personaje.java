public class Personaje {
    String nombre;
    String raza;
    Integer ki;
    Integer ataque;
    String planeta;
    static final Integer KI_MAXIMO = 1000;

    public Personaje(String nombre, String raza, Integer ki, Integer ataque, String planeta) {
        this.nombre = nombre;
        this.raza = raza;
        this.ki = ki;
        this.ataque = ataque;
        this.planeta = planeta;
    }

    public void mostrarPersonaje() {
        System.out.println("El nombre es: " + nombre);
        System.out.println("La raza es: " + raza);
        System.out.println("El ki es: " + ki);
        System.out.println("El ataque es: " + ataque);
        System.out.println("El planeta es: " + planeta);
    }

    public void atacar(Personaje enemigo) {
        System.out.println("\n03" + this.nombre + " ataca a " + enemigo.nombre + "!");
        enemigo.ki -= this.ataque;
        if (enemigo.ki < 0) {
            enemigo.ki = 0;
        }
        System.out.println(enemigo.nombre + " ha perdido " + this.ataque + " puntos de ki!");
    }

    public void curar(Integer cantidadKi) {
        int kiAntes = this.ki;
        this.ki += cantidadKi;

        if (this.ki > KI_MAXIMO) {
            this.ki = KI_MAXIMO;
        }

        int kiRecuperado = this.ki - kiAntes;
        System.out.println("\n" + this.nombre + " se ha curado, recuperando " + kiRecuperado + " puntos de ki!");
        System.out.println("Ki actual: " + this.ki + "/" + KI_MAXIMO);
    }

    public static void main(String[] args) {
        Personaje goku = new Personaje("Gokú", "Saiyajin", 1000, 1000, "Vegito");
        Personaje vegetta = new Personaje("Vegetta", "Saiyajin", 1000, 999, "Vegito");

        goku.mostrarPersonaje();
        System.out.println();
        vegetta.mostrarPersonaje();

        goku.atacar(vegetta);
        System.out.println("\n=== Despúes del ataque ===");
        goku.mostrarPersonaje();
        System.out.println();
        vegetta.mostrarPersonaje();

        vegetta.curar(500);
        System.out.println("\n=== Después de curarse ===");
        vegetta.mostrarPersonaje();
    }
}