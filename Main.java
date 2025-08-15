import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

class Libro {
    private String titulo;
    private String autor;
    private String codigo;
    private boolean disponible;

    public Libro(String titulo, String autor, String codigo) {
        this.titulo = titulo;
        this.autor = autor;
        this.codigo = codigo;
        this.disponible = true;
    }

    public void mostrarDatos() {
        System.out.println("Título: " + titulo);
        System.out.println("Autor: " + autor);
        System.out.println("Código: " + codigo);
        System.out.println("Disponible: " + (disponible ? "Sí" : "No"));
    }

    public void marcarPrestado() {
        disponible = false;
    }

    public void marcarDisponible() {
        disponible = true;
    }

    public String getCodigo() {
        return codigo;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public String getTitulo() {
        return titulo;
    }
}

class Usuario {
    private String nombre;
    private String idUsuario;
    private ArrayList<Libro> librosPrestados;

    public Usuario(String nombre, String idUsuario) {
        this.nombre = nombre;
        this.idUsuario = idUsuario;
        this.librosPrestados = new ArrayList<>();
    }

    public void mostrarDatos() {
        System.out.println("Nombre: " + nombre);
        System.out.println("ID: " + idUsuario);
        System.out.println("Libros prestados: " + librosPrestados.size());
    }

    public boolean agregarPrestamo(Libro libro) {
        if (librosPrestados.size() < 3) {
            librosPrestados.add(libro);
            return true;
        }
        return false;
    }

    public boolean devolverLibro(Libro libro) {
        return librosPrestados.remove(libro);
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public ArrayList<Libro> getLibrosPrestados() {
        return librosPrestados;
    }

    public String getNombre() {
        return nombre;
    }
}

class Prestamo {
    private Libro libro;
    private Usuario usuario;
    private Date fechaInicio;
    private Date fechaLimite;

    public Prestamo(Libro libro, Usuario usuario, Date fechaInicio, Date fechaLimite) {
        this.libro = libro;
        this.usuario = usuario;
        this.fechaInicio = fechaInicio;
        this.fechaLimite = fechaLimite;
    }

    public Libro getLibro() {
        return libro;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public Date getFechaLimite() {
        return fechaLimite;
    }
}

class Biblioteca {
    private ArrayList<Libro> libros;
    private ArrayList<Usuario> usuarios;
    private ArrayList<Prestamo> prestamos;

    public Biblioteca() {
        libros = new ArrayList<>();
        usuarios = new ArrayList<>();
        prestamos = new ArrayList<>();
    }

    public void registrarLibro(Libro libro) {
        libros.add(libro);
    }

    public void registrarUsuario(Usuario usuario) {
        usuarios.add(usuario);
    }

    public boolean prestarLibro(String codigoLibro, String idUsuario) {
        Libro libro = buscarLibro(codigoLibro);
        Usuario usuario = buscarUsuario(idUsuario);

        if (libro == null || usuario == null) {
            return false;
        }

        if (!libro.isDisponible()) {
            System.out.println("El libro no está disponible.");
            return false;
        }

        if (usuario.getLibrosPrestados().size() >= 3) {
            System.out.println("El usuario ya tiene el máximo de libros prestados.");
            return false;
        }

        libro.marcarPrestado();
        usuario.agregarPrestamo(libro);

        // Fecha actual como fecha de inicio
        Date fechaInicio = new Date();
        // Fecha límite: 7 días después
        Date fechaLimite = new Date(fechaInicio.getTime() + (7 * 24 * 60 * 60 * 1000));

        prestamos.add(new Prestamo(libro, usuario, fechaInicio, fechaLimite));
        return true;
    }

    public int devolverLibro(String codigoLibro, String idUsuario) {
        Libro libro = buscarLibro(codigoLibro);
        Usuario usuario = buscarUsuario(idUsuario);

        if (libro == null || usuario == null) {
            return -1; // Error
        }

        if (!usuario.devolverLibro(libro)) {
            return -1; // El usuario no tenía ese libro prestado
        }

        libro.marcarDisponible();

        // Buscar el préstamo correspondiente
        Prestamo prestamo = null;
        for (Prestamo p : prestamos) {
            if (p.getLibro().equals(libro) && p.getUsuario().equals(usuario)) {
                prestamo = p;
                break;
            }
        }

        if (prestamo == null) {
            return -1;
        }

        prestamos.remove(prestamo);

        // Calcular multa si hay retraso
        Date fechaActual = new Date();
        if (fechaActual.after(prestamo.getFechaLimite())) {
            long diferencia = fechaActual.getTime() - prestamo.getFechaLimite().getTime();
            int diasRetraso = (int) (diferencia / (1000 * 60 * 60 * 24));
            return diasRetraso * 500; // Multa de $500 por día
        }

        return 0; // Sin multa
    }

    public void mostrarLibrosDisponibles() {
        System.out.println("\nLIBROS DISPONIBLES:");
        for (Libro libro : libros) {
            if (libro.isDisponible()) {
                libro.mostrarDatos();
                System.out.println("-------------------");
            }
        }
    }

    public void mostrarUsuarios() {
        System.out.println("\nUSUARIOS REGISTRADOS:");
        for (Usuario usuario : usuarios) {
            usuario.mostrarDatos();
            System.out.println("-------------------");
        }
    }

    public void mostrarHistorialPrestamos() {
        System.out.println("\nHISTORIAL DE PRÉSTAMOS:");
        for (Prestamo prestamo : prestamos) {
            System.out.println("Libro: " + prestamo.getLibro().getTitulo());
            System.out.println("Usuario: " + prestamo.getUsuario().getNombre());
            System.out.println("Fecha préstamo: " + prestamo.getFechaInicio());
            System.out.println("Fecha límite: " + prestamo.getFechaLimite());
            System.out.println("-------------------");
        }
    }

    private Libro buscarLibro(String codigo) {
        for (Libro libro : libros) {
            if (libro.getCodigo().equals(codigo)) {
                return libro;
            }
        }
        return null;
    }

    private Usuario buscarUsuario(String idUsuario) {
        for (Usuario usuario : usuarios) {
            if (usuario.getIdUsuario().equals(idUsuario)) {
                return usuario;
            }
        }
        return null;
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Biblioteca biblioteca = new Biblioteca();

        biblioteca.registrarLibro(new Libro("Cien años de soledad", "Gabriel García Márquez", "LIB001"));
        biblioteca.registrarLibro(new Libro("El principito", "Antoine de Saint-Exupéry", "LIB002"));
        biblioteca.registrarLibro(new Libro("Harry Potter", "J.K. Rowling", "LIB003"));
        biblioteca.registrarUsuario(new Usuario("Ana Pérez", "001"));
        biblioteca.registrarUsuario(new Usuario("Carlos Gómez", "002"));
        

        int opcion;
        do {
            System.out.println("\nSISTEMA DE GESTIÓN DE BIBLIOTECA");
            System.out.println("1. Registrar usuario");
            System.out.println("2. Registrar libro");
            System.out.println("3. Mostrar libros disponibles");
            System.out.println("4. Prestar libro");
            System.out.println("5. Devolver libro");
            System.out.println("6. Mostrar usuarios");
            System.out.println("7. Mostrar historial de préstamos");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");

            opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer

            switch (opcion) {
                case 1:
                    System.out.print("Nombre del usuario: ");
                    String nombre = scanner.nextLine();
                    System.out.print("ID del usuario: ");
                    String idUsuario = scanner.nextLine();
                    biblioteca.registrarUsuario(new Usuario(nombre, idUsuario));
                    System.out.println("Usuario registrado con éxito.");
                    break;

                case 2:
                    System.out.print("Título del libro: ");
                    String titulo = scanner.nextLine();
                    System.out.print("Autor: ");
                    String autor = scanner.nextLine();
                    System.out.print("Código: ");
                    String codigo = scanner.nextLine();
                    biblioteca.registrarLibro(new Libro(titulo, autor, codigo));
                    System.out.println("Libro registrado con éxito.");
                    break;

                case 3:
                    biblioteca.mostrarLibrosDisponibles();
                    break;

                case 4:
                    System.out.print("Código del libro: ");
                    String codigoLibro = scanner.nextLine();
                    System.out.print("ID del usuario: ");
                    String idUser = scanner.nextLine();
                    if (biblioteca.prestarLibro(codigoLibro, idUser)) {
                        System.out.println("Préstamo realizado con éxito.");
                    } else {
                        System.out.println("No se pudo realizar el préstamo.");
                    }
                    break;

                case 5:
                    System.out.print("Código del libro a devolver: ");
                    String codigoDev = scanner.nextLine();
                    System.out.print("ID del usuario: ");
                    String idUserDev = scanner.nextLine();
                    int multa = biblioteca.devolverLibro(codigoDev, idUserDev);
                    if (multa == -1) {
                        System.out.println("Error al devolver el libro.");
                    } else if (multa > 0) {
                        System.out.println("Libro devuelto con éxito. Multa por retraso: $" + multa);
                    } else {
                        System.out.println("Libro devuelto con éxito. Sin multa.");
                    }
                    break;

                case 6:
                    biblioteca.mostrarUsuarios();
                    break;

                case 7:
                    biblioteca.mostrarHistorialPrestamos();
                    break;

                case 0:
                    System.out.println("Saliendo del sistema...");
                    break;

                default:
                    System.out.println("Opción no válida.");
            }
        } while (opcion != 0);

        scanner.close();
    }
}