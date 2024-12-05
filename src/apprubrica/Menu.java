package apprubrica;

import java.util.*;
import java.util.regex.Pattern;

public class Menu {

    Scanner scanner = new Scanner(System.in); //oggetto per leggere dati di input
    private Rubrica rubrica = new Rubrica(); //oggetto di tipo Rubrica, per avere accesso all'ArrayList contenenti oggetti di tipo Contatto

//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    //SEZIONE: METODI PER LA GESTIONE DELL'INTERAZIONE E VISUALIZZAZIONE GRAFICA DEL MENU' PRINCIPALE 
    /**
     * Metodo per la visualizzazione grafica del menù principale.
     *
     */
    public void menuPrincipale() {
        cls();
        //menu che verrà visualizzato
        System.out.println("\n----- MENU' PRINCIPALE -----\n");
        System.out.println("   1) Visualizza rubrica");
        System.out.println("   2) Aggiungi contatto");
        System.out.println("   3) Rimuovi contatto");
        System.out.println("   4) Modifica contatto");
        System.out.println("   5) Cerca contatto");
        System.out.println("   6) Exit ");
        System.out.println("----------------------------\n");
    }

    /**
     * metodo per gestire l'interazione dell'utente con il menù principale.
     *
     */
    public void useMenu() {
        int scelta = 0; // variabile per prendere in input un'opzione  
        do {
            menuPrincipale();
            try {
                System.out.print("Inserire opzione > ");
                scelta = Integer.parseInt(scanner.nextLine());
            } catch (InputMismatchException e) {
                System.out.println("\nErrore, input non valido");
                pause();
                continue;
            }
            //controllo inserimento opzioni
            if (scelta < 1 || scelta > 6) {
                System.out.println("\nL'opzione inserita non è valida, RIPROVARE");
                pause();
            }
            switch (scelta) { //opzioni disponibili
                case 1:
                    cls();
                    printRubrica();
                    pause();
                    cls();
                    break;
                case 2:
                    aggiungiContatto();
                    pause();
                    cls();
                    break;
                case 3:
                    cls();
                    rimuoviContatto();
                    pause();
                    cls();
                    break;
                case 4:
                    cls();
                    modificaContatto();
                    pause();
                    cls();
                    break;
                case 5:
                    cercaContatto();
                    pause();
                    cls();
                    break;
            }
        } while (scelta != 6);

    }
//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    //SEZIONE: METODI PER LA VISUALIZZAZIONE DI RUBRICA E CONTATTI

    /**
     * metodo per la visualizzazione grafica della rubrica,(ArrayList), con
     * relativi contatti
     */
    public void printRubrica() {
        int i = 0;
        System.out.println("\n  ---------- RUBRICA ----------\n");
        //controllo se la rubrica è vuota
        if (rubrica.getRubrica().isEmpty()) {
            System.out.println("     Nessun contatto aggiunto!");
        } else {
            //si stampa a video i vari contatti
            for (Contatto contatto : rubrica.getRubrica()) {
                System.out.println("---------- CONTATTO " + " (" + (i + 1) + ") ----------");
                printDati(contatto);
                System.out.println("-----------------------------------");
                System.out.println();
                i++;
            }
        }

    }

    /**
     * metodo per la visualizzazione grafica dei dati (nome, cognome, recapito,
     * email) di un contatto
     */
    public void printDati(Contatto contatto) {
        System.out.println("Nome > " + contatto.getNome());
        System.out.println("Cognome > " + contatto.getCognome());
        System.out.println("Recapito > " + contatto.getRecapito());
        System.out.println("Email > " + contatto.getEmail());

    }
//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    //SEZIONE: METODI PER L'AGGIUNTA DEI CONTATTI

    /**
     * metodo per aggiungere un contatto, con relativi dati, alla rubrica,
     * (ArrayList).
     *
     */
    public void aggiungiContatto() {
        String nome, cognome, recapito, email;
        System.out.print("\nInserire nome > ");
        scanner.useDelimiter("\n"); //impostiamo il delimitatore di input
        nome = scanner.nextLine();
        nome = capitalize(nome);
        System.out.print("Inserire cognome > ");
        cognome = scanner.nextLine();
        cognome = capitalize(cognome);
        //Richiamare il metodo isEsistente per verificare se il nome e cognome inseriti corrispondono a un contatto già esistente
        if (isEsistente(nome, cognome) == false) {

            do {
                System.out.print("Inserire recapito telefonico > ");
                recapito = scanner.nextLine();
                if (recapito.length() != 10 || !recapito.matches("\\d{10}")) {
                    System.out.println("\nRIPROVARE, recapito telefonico non composto da 10 NUMERI\n");
                    pause();
                }
            } while (recapito.length() != 10 || !recapito.matches("\\d{10}"));
            System.out.print("Inserire Email (Premere invio per non registrare)> ");
            email = scanner.nextLine();
            if (isEmailValid(email) != true) {
                email = "";
            }
            //allocazione dell'oggetto contatto creato (contattoAdd)
            Contatto contattoAdd = new Contatto(nome, cognome, recapito, email);
            rubrica.aggiungiContatto(contattoAdd);

            System.out.println("\n---------- CONTATTO INSERITO ----------");
            printDati(contattoAdd);
            System.out.println("---------------------------------------");
        }
    }

    /**
     * Capitalizza (trasforma) la prima lettera di ogni parola in una stringa,
     * trasformando il resto delle lettere in minuscolo. Le parole sono separate
     * da spazi.
     *
     * @param string la stringa di string da capitalizzare
     * @return una nuova stringa con la prima lettera di ogni parola in
     * maiuscolo e le altre lettere in minuscolo. Se l'string è null o vuoto,
     * restituisce l'string originale.
     */
    public String capitalize(String string) {
        if (string == null || string.isEmpty()) {
            return string; //se la stringa passata come parametro è nulla o vuota ritornerà string
        }

        String[] parole = string.toLowerCase().split(" ");  // Divide in parole la stringa passata come parametro e converte in minuscolo
        StringBuilder capitalized = new StringBuilder();

        for (String parola : parole) {
            if (!parola.isEmpty()) {
                capitalized.append(Character.toUpperCase(parola.charAt(0))) // Prima lettera maiuscola
                        .append(parola.substring(1)) // Aggiunge il resto della parola
                        .append(" ");  // Aggiunge uno spazio dopo ogni parola
            }
        }
        return capitalized.toString().trim();  // .trim rimuove lo spazio finale in eccesso
    }

    /**
     * metodo per controllare che un contatto sia già esistente nella rubrica,
     * (ArrayList).
     *
     */
    public boolean isEsistente(String nome, String cognome) {
        boolean isEsistente = false; //Variabile per gestire l'inserimento di nome e cognome di un contatto esistente
        for (int i = 0; i < rubrica.getRubrica().size(); i++) {
            if (nome.equalsIgnoreCase(rubrica.getRubrica().get(i).getNome())
                    && cognome.equalsIgnoreCase(rubrica.getRubrica().get(i).getCognome())) {
                System.out.println("\nCONTATTO GIA' ESISTENTE");
                isEsistente = true;
                break; //se il contatto è già esistente interrompiamo il ciclo di controllo
            }
        }
        return isEsistente;
    }

    /**
     * Verifica se una stringa rappresenta un indirizzo email valido secondo un
     * modello definito da una espressione regolare.
     * <p>
     * Il metodo utilizza un'espressione regolare per convalidare l'email,
     * controllando che il formato dell'indirizzo email rispetti le convenzioni
     * standard. La validazione include il controllo della parte locale (prima
     * del simbolo '@') e la parte del dominio (dopo il simbolo '@'), ma non
     * esegue una verifica approfondita sul dominio o sul TLD.
     * </p>
     *
     * <p>
     * Le regole seguite dall'espressione regolare includono:</p>
     * <ul>
     * <li>Il nome locale può contenere lettere, numeri, e caratteri speciali
     * come '_', '!', '#', '%', '&', e altri.</li>
     * <li>Il dominio deve essere composto da lettere, numeri e trattini.</li>
     * <li>Il dominio può contenere più segmenti separati da punti.</li>
     * </ul>
     *
     * @param email L'indirizzo email da verificare.
     * @return {@code true} se l'indirizzo email è valido, {@code false}
     * altrimenti.
     */
    public static boolean isEmailValid(String email) {
        String regex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+(?:.[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+)*@[a-zA-Z0-9-]+(?:.[a-zA-Z0-9-]+)*$";
        Pattern p = Pattern.compile(regex);
        return p.matcher(email).matches();
    }

//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------    
    //SEZIONE: METODI PER LA RIMOZIONE DEI CONTATTI
    /**
     * Metodo per rimuovere un contatto dalla rubrica, (ArrayList).
     */
    public void rimuoviContatto() {
        String nome,cognome;
        int scelta = 0; //variabile per gestire le opzione del menù
        boolean esiste = false; // variabile utilizzata per verificare che il contatto che si vuole eliminare sia esistente
        do {
            cls();
            System.out.println("\n---- MENU' RIMUOVI CONTATTO ----\n");
            System.out.println("     1) Visualizza rubrica");
            System.out.println("     2) Rimuovi contatto");
            System.out.println("     3) MENU' principale");
            System.out.println("-----------------------------------");
            try {
                System.out.print("\nInserire opzione > ");
                scelta = Integer.parseInt(scanner.nextLine());
            } catch (InputMismatchException e) {
                System.out.println("\nErrore, input non valido");
                scanner.nextLine();
                pause();
                continue;
            }
            //controllo inserimento opzioni
            if (scelta < 1 || scelta > 3) {
                System.out.println("\nRIPROVARE, opzione inserita non valida");
                pause();
            }
            switch (scelta) {
                case 1:
                    cls();
                    printRubrica();
                    pause();
                    break;
                case 2:
                    if (rubrica.getRubrica().isEmpty()) {
                        System.out.println("\n-- RUBRICA VUOTA --");
                        pause();
                    } else {
                        System.out.print("\nInserire nome > ");
                        scanner.useDelimiter("\n"); //impostiamo il delimitatore di input
                        nome = scanner.nextLine();
                        System.out.print("Inserire cognome > ");
                        cognome = scanner.nextLine();
                        for (int i = 0; i < rubrica.getRubrica().size(); i++) {
                            if (nome.equalsIgnoreCase(rubrica.getRubrica().get(i).getNome()) == true
                                    && cognome.equalsIgnoreCase(rubrica.getRubrica().get(i).getCognome())) {
                                esiste = true;
                                System.out.println("\n----- CONTATTO RIMOSSO -----");
                                printDati(rubrica.getRubrica().get(i)); //metodo per stampare i dati del contatto rimosso
                                System.out.println("-------------------------");
                                rubrica.removeContatto(i);
                            }

                        }
                        if (esiste == false) {
                            System.out.println("\nCONTATTO NON ESISTENTE");
                        }
                        pause();
                    }
            }
        } while (scelta != 3);

    }
//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    //SEZIONE: METODI PER LA RICERCA DEI CONTATTI

    /**
     * Metodo per cercare attraverso l'inserimento del nome e cognome, un
     * contatto nella rubrica,(ArrayList).
     *
     * @return ritorna l'oggetto di tipo Contatto se la ricerca nella rubrica ha
     * esito positivo, altrimenti ritorna il valore NULL
     */
    public Contatto cercaContatto() {
        String nome, cognome;
        cls();
        System.out.println("-- CERCA CONTATTO --");
        boolean isTrovato = false; //variabile per gestire la ricerca di un contatto; true = contatto trovato; false =  contatto non trovato
        if (rubrica.getRubrica().isEmpty()) {
            System.out.println("\n-- RUBRICA VUOTA --");
        } else {
            System.out.print("\nInserire nome > ");
            scanner.useDelimiter("\n"); //impostiamo il delimitatore di input
            nome = scanner.nextLine();
            nome = capitalize(nome);
            System.out.print("Inserire cognome > ");
            cognome = scanner.nextLine();
            cognome = capitalize(cognome);
            for (int i = 0; i < rubrica.getRubrica().size(); i++) {
                if (nome.equalsIgnoreCase(rubrica.getRubrica().get(i).getNome())
                    && cognome.equalsIgnoreCase(rubrica.getRubrica().get(i).getCognome())) {
                    isTrovato = true;
                    System.out.println("\n----- CONTATTO TROVATO -----");
                    printDati(rubrica.getRubrica().get(i));
                    System.out.println("-----------------------------");
                    return rubrica.getRubrica().get(i);
                }
            }
            if (isTrovato == false) {
                System.out.println("\n-- CONTATTO NON ESISTENTE --");
            }
        }
        return null;
    }
//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    //SEZIONE: METODI PER LA MODIFICA DEI CONTATTI

    /*MIGLIORAMENTI DA FARE AL METODO modificaContatto():
      IMPLEMENTARE LA POSSIBILITA' DI ELIMINARE L'EMAIL
      RIDURRE IL NUMERO DI ISTRUZIONI DEL METODO PER UNA MAGGIORE EFFICACIA */
    /**
     * metodo per apportare modifiche ai dati (nome, cognome, recapito, email)
     * di un contatto registrato nella rubrica,(ArrayList).
     */
    public void modificaContatto() {
        String nome, cognome, recapito, email;
        int scelta = 0;
        do {
            cls();
            System.out.println("\n---- MENU' MODIFICA CONTATTO ----\n");
            System.out.println("     1) Visualizza rubrica");
            System.out.println("     2) Modifica contatto");
            System.out.println("     3) MENU' principale");
            System.out.println("---------------------------------");
            try {
                System.out.print("\nInserire opzione > ");
                scelta = Integer.parseInt(scanner.nextLine());
            } catch (InputMismatchException e) {
                System.out.println("\nErrore, input non valido");
                scanner.nextLine();
                pause();
                continue;
            }
            //controllo inserimento opzioni
            if (scelta < 1 || scelta > 3) {
                System.out.println("\nRIPROVARE, opzione inserita non valida");
                pause();
            }
            switch (scelta) {
                case 1:
                    cls();
                    printRubrica();
                    pause();
                    break;
                case 2:
                    cls();
                    System.out.println("-- MODIFICA CONTATTO --");
                    if (rubrica.getRubrica().isEmpty() == true) {
                        System.out.println("\n  -- RUBRICA VUOTA --");
                        pause();
                    } else {
                        Contatto contattoDaModificare = cercaContatto();
                        
                        if (contattoDaModificare != null) {      
                            System.out.print("\nInserire nuovo nome (Premere INVIO per non modificare) > ");
                            scanner.useDelimiter("\n"); //impostiamo il delimitatore di input
                            nome = scanner.nextLine();
                            nome = capitalize(nome);
                            System.out.print("Inserire cognome (Premere INVIO per non modificare) > ");
                            cognome = scanner.nextLine();
                            cognome = capitalize(cognome);
                            
                            //Richiamare il metodo isEsistente per verificare se il nome e cognome inseriti corrispondono a un contatto già esistente.
                            if (isEsistente(nome, cognome) == false) {
                                String recapitoM;
                                boolean isPossible = false; //variabile per gestire l'inserimento del recapito telefonico
                                do {
                                    System.out.print("Inserire nuovo recapito telefonico (Premere INVIO per non modificare) > ");
                                    //contattoAdd.setRecapito(scanner.nextLine());
                                    recapitoM = scanner.nextLine();                        //controlliamo che il numero non contenga lettere
                                    if (recapitoM.isEmpty() || recapitoM.length() == 10 || recapitoM.matches("\\d{10}")) {
                                        isPossible = true;
                                    } else {
                                        System.out.println("\nRIPROVARE, recapito telefonico non composto da 10 NUMERI\n");
                                        pause();
                                    }
                                } while (isPossible == false);
                                System.out.print("Inserire nuova Email (Premere INVIO per non modificare) > ");
                                String emailM = scanner.nextLine();
                                    if (nome != null && !nome.isEmpty()) {

                                        contattoDaModificare.setNome(nome);
                                    }
                                    if (cognome != null && !cognome.isEmpty()) {

                                        contattoDaModificare.setCognome(cognome);
                                    }
                                    if (!recapitoM.isEmpty()) {
                                        contattoDaModificare.setRecapito(recapitoM);
                                    }
                                    // Per l'email, si verifica che non sia vuota e che rispetti lo standard definito dall'espressione regolare
                                    if (!emailM.isEmpty() && emailM.contains("@") && isEmailValid(emailM) == true) {
                                        contattoDaModificare.setEmail(emailM);
                                    }
                                    System.out.println("\n----- CONTATTO MODIFICATO -----");
                                    printDati(contattoDaModificare);
                                    System.out.println("-------------------------------");
                                    System.out.println();
                                    pause();
                                   
                                

                            } else {
                                pause();
                            }

                        } else {
                            pause();
                        }
                    }
            }
        } while (scelta != 3);

    }
//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    //SEZIONE: METODI PER MIGLIORARE LA VISUALIZZAZIONE ED ESECUZIONE DEL CODICE DA TERMINALE

    /**
     * metodo per mettere in pausa l'esecuzione del codice.
     *
     */
    public void pause() {
        Scanner input = new Scanner(System.in);
        System.out.print("\nPremere invio per continuare...> \n");
        input.nextLine();
    }

    /**
     * metodo rimuovere il contenuto della finestra del terminale (funziona solo
     * da terminale).
     *
     */
    public static void cls() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

/*for (int i = 0; i < rubrica.getRubrica().size(); i++) {
                System.out.print((i + 1) + ") " + rubrica.getRubrica().get(i).getNome() + " ");
                System.out.print(rubrica.getRubrica().get(i).getCognome() + " > ");
                System.out.print(rubrica.getRubrica().get(i).getRecapito() + " ");
                System.out.println();
            }
 */

 /*if (nomeM.isEmpty() || nomeM == null) {
                            rubrica.getRubrica().get(i).setNome(rubrica.getRubrica().get(i).getNome());
                        } else {
                            rubrica.getRubrica().get(i).setNome(nomeM);
                        }
                        if (cognomeM.isEmpty() || cognomeM == null) {
                            rubrica.getRubrica().get(i).setCognome(rubrica.getRubrica().get(i).getCognome());
                        } else {
                            rubrica.getRubrica().get(i).setCognome(cognomeM);
                        }
                        if (recapitoM.isEmpty() || recapitoM == null) {
                            rubrica.getRubrica().get(i).setRecapito(rubrica.getRubrica().get(i).getRecapito());
                        } else {
                            rubrica.getRubrica().get(i).setRecapito(recapitoM);
                        }
                        if (!emailM.isEmpty() && emailM.contains("@")) {
                            rubrica.getRubrica().get(i).setEmail(emailM);
                        } else {
                            rubrica.getRubrica().get(i).setEmail(rubrica.getRubrica().get(i).getEmail());
                        }
 */
