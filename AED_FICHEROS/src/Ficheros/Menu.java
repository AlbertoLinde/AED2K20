package Ficheros;

import java.io.IOException;
import java.util.Scanner;

/**
 * @author Alberto Linde GitHub: https://github.com/AlbertoLinde
 */
public class Menu {

    Scanner readFromKeyboard = new Scanner(System.in);

    // Objeto 
    ManejoFicheros manageFiles = new ManejoFicheros();
    
    public void optionMenu() throws IOException {

        System.out.println("===== MENU =====");
        System.out.println("1.- CREAR FICHERO.");
        System.out.println("2.- ELIMINAR FICHERO.");
        System.out.println("3.- CREAR DIRECTORIO.");
        System.out.println("4.- ELIMINAR DIRECTORIO.");
        System.out.println("5.- LISTADO FICHEROS.");
        System.out.println("6.- ¡SALIR!");
        System.out.print("Selecciona la opción que desees: ");

        // Variable que guardará la opción del usuario.
        int userNumber = 0;
        boolean bucle = true;

        // Bucle que me impedirá la entrada de String/Float/Double, solo aceptará enteros.
        // Estará entrando en el bucle hasta que uno entre.
        do {

            if (!readFromKeyboard.hasNextInt() || !readFromKeyboard.hasNext()) {

                System.out.print("Introduce un valor correcto (1-6 y no letras): ");
                readFromKeyboard.nextLine();
            } else {
                userNumber = readFromKeyboard.nextInt();
                // Si lo que metemos por teclado es mayor o igual a 6 y mayor o 
                // igual a 1 cambiamos el estado del bucle y saldremos de el.
                if (userNumber <= 6 && userNumber >= 1) {
                    bucle = false;
                } else {
                    System.out.print("Introduce un valor correcto (1-6 y no letras): ");
                    readFromKeyboard.nextLine();
                }

            }
        } while (bucle);

        // Menu para seleccionar las opciones.
        switch (userNumber) {

            case 1:
                System.out.print("Introduce el nombre de tu fichero. Recuerda de añadir la extension: ");
                manageFiles.createFile(askForName());
                optionMenu();
            case 2:
                System.out.print("Introduce la ruta del fichero o directorio que deseas eliminar.\nEJEMPLO: C:/Users/Profesor/prueba.txt: ");
                manageFiles.deleteFile(askForName());
                optionMenu();
            case 3:
                System.out.print("Introduce la ruta donde quieres crear el directorio seguida del nombre del mismo."
                        + "\nEJEMPLO: C:/Users/Profesor/CarpetaTest: ");
                manageFiles.createDirectory(askForName());
                optionMenu();
            case 4:
                System.out.print("Introduce la ruta del fichero o directorio que deseas eliminar: ");
                manageFiles.deleteDirectory(askForName());
                optionMenu();
            case 5:
                System.out.print("Introduce la ruta del directorio que deseas ver los ficheros: ");
                manageFiles.listFiles((askForName()));
                optionMenu();
            case 6:
                System.out.println("¡Nos vemos!");
                System.exit(0);

        }

    }

    public String askForName() {
        String entryName = readFromKeyboard.next();
        return entryName;
    }

}
