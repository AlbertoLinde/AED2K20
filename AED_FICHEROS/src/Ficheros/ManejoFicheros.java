package Ficheros;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 * @author Alberto Linde GitHub: https://github.com/AlbertoLinde
 */
public class ManejoFicheros {

    Scanner readFromKeyboard = new Scanner(System.in);

    // Metodo individual para la creación de ficheros a través de consola.
    public void createFile(String fileName) throws IOException {

        // Lo he hecho un poco más largo para utilizar más tecnicas se podría haber 
        // simplificado. 
        String pathUser;
        boolean directoryExist = true;

        // Creo un bucle en el cual entraremos para pedir la ruta en la que dejaremos finalmente
        // nuestro archivo. Como se decia en el enunciado que el nombre lo recibiriamos por parametro
        // lo he hecho de esa forma.
        System.out.print("Introduce la ruta donde se creará el fichero: ");
        do {
            // Se entra en el bucle para la comprobacion de que el fichero vaya ser
            // metido en una carpeta que realmente exista y así evitar capturar esa excepcion.
            pathUser = readFromKeyboard.next().toLowerCase();
            Path pathDirectory = Paths.get(pathUser);
            if (Files.exists(pathDirectory)) {
                directoryExist = false;
            } else {
                System.out.print("¡AVISO! -> La ruta o directorio introducido no existe. Prueba de nuevo: ");
            }
        } while (directoryExist);

        // Se sale del bucle y se crea un nuevo path que contiene tanto la ruta como el nombre introducido 
        // por el usuario.
        Path path = Paths.get(pathUser + File.separator + fileName);

        try {
            // Se intenta crear, he capturado que no exista el mismo fichero en destino aún pudiendo poner mas clausulas.
            // si se crea todo bien enviamos e imprimimos el metodo de sucess que es el mensaje de confirmación.
            Files.createFile(path);
            System.out.println(sucessMesagge(path));
        } catch (FileAlreadyExistsException e) {
            System.out.println("¡WARNING! -> Actualmente ya existe un fichero en la ruta seleccionada con ese nombre.");
        }

    }

    public void deleteFile(String fileToDelete) throws IOException {

        // Se podia haber extraido de otro metodo uno que comprobara la existencia de los ficheros.
        // pero para este ejercicio lo dejo de esta manera pudiendo mejorarlo en los siguientes.
        // Me he limitado a poner solo una excepcion pudiendo creando otras reglas, comprobando
        // que tengo permiso para eliminar etc... 
        try {
            Path pathToDelete = Paths.get(fileToDelete);
            if (Files.deleteIfExists(pathToDelete)) {
                System.out.println(deleteMessage(pathToDelete));
            }
        } catch (NoSuchFileException e) {
            System.out.println("¡WARNING! -> Fichero no encontrado.");
            System.out.println("COD ERROR: " + e);
        }

    }

    public void createDirectory(String directorytToCreate) throws IOException {

        // Leyendo documentación me he encontrado que podemos usar de la clase 
        // Files la opcion createDirectory que creara el directorio en la ubicacion que le demos
        // o tambien podemos utilizar createDirectories, que nos creara todo el arbol de directorio
        // que introduzcamos.
        Path pathCreateDirectory = Paths.get(directorytToCreate);
        try {
            // Compruebo que no exista para crearlo.
            if (!Files.exists(pathCreateDirectory)) {
                Files.createDirectories(pathCreateDirectory);
                System.out.println(sucessMesagge(pathCreateDirectory));
            }
        } catch (FileAlreadyExistsException e) {
            System.out.println("¡WARNING! -> El directorio que intentas crear ya existe.");
        }

        // Ahora dejo la otra opcion que seria crear todo el arbol de directorios. Simplemente
        // hay que cambiar una sentencia en nuestro codigo.
        /*
        Path pathCreateMultiple = Paths.get(directorytToCreate);
        try {
            if (!Files.exists(pathCreateMultiple)) {
                Files.Directories(pathCreateMultiple);
                sucessMesagge(pathCreateMultiple);
            }
        } catch (FileAlreadyExistsException e) {
            System.out.println("¡WARNING! -> El directorio que intentas crear ya existe.");
        }
         */
    }

    public void deleteDirectory(String directoryToDelete) {

        // Segun la documentacion para poder utilizar el delete que nos entrega la clase Files el directorio
        // debe estar vacio. Como seria recurrente lo mejor en este punto seria al encontrarnos con un directorio
        // con elementos preguntar al usuario que si quiere eliminarlos, llamariamos entonces al metodo creado
        // mas arriba de eliminar los ficheros y en caso de encontrar un directorio llamar recurrentemente a este mismo.
        // Lo dejo asi de primeras, en caso de querer esta modificacion puedo crearla proximamente.
        Path directory = Paths.get(directoryToDelete);

        try {
            // Como es el metodo de eliminar directorio lo compruebo que exista y sea directorio para eliminarlo.
            if (Files.isDirectory(directory) && Files.exists(directory)) {
                Files.delete(directory);
                System.out.println(deleteMessage(directory));
            }
        } catch (DirectoryNotEmptyException e) {
            System.out.println("¡AVISO! -> El directorio que intentas eliminar contiene elementos en su interior. Eliminalos e intentalo de nuevo.");
        } catch (IOException e) {
            System.out.println("¡WARNING!->  Hemos tenido un error.\nCOD Error: " + e);
        }

    }

    // El metodo de listado podría hacerse recursivo, por ejemplo que dentro de la carpeta
    // si tuvieramos mas carpetas listar las mismas. Como en el ejercicio solo se pide una 
    // lo he hecho de esta manera pero puedo modificarlo en el futuro.
    public void listFiles(String directoryToList) {

        File directory = new File(directoryToList);
        if (directory.exists()) {
            String[] filesOnDirectory = directory.list();

            System.out.println("===== LISTADO ======");
            System.out.println("En el directorio proporcionado hay un total de: " + filesOnDirectory.length);
            for (String file : filesOnDirectory) {
                System.out.println("-" + file);
            }
        } else {
            System.out.println("¡AVISO! -> La ruta que estás introduciendo no existe. Compruebalo e intentalo de nuevo.");
        }

    }

    public String sucessMesagge(Path pathCreated) {
        return "¡COMPLETADO! Creado con éxito.\nCreado en: " + pathCreated;
    }

    public String deleteMessage(Path pathCreated) {
        return "¡COMPLETADO! Eliminado con éxito.\nEliminado en: " + pathCreated;
    }

}
