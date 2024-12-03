package apprubrica;

import java.util.*;

public class Menu {

    Scanner scanner = new Scanner(System.in); //oggetto per leggere dati di input
    Rubrica rubrica = new Rubrica(); //oggetto di tipo Rubrica, per avere accesso all'ArrayList contenenti oggetti di tipo Contatto

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
                scelta = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("\nErrore, input non valido");
                scanner.nextLine();
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
        if (rubrica.getRubrica().isEmpty() == true) {
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

        System.out.print("\nInserire nome > ");
        String nome = scanner.next();
        scanner.nextLine();
        nome = nome.substring(0, 1).toUpperCase() + nome.substring(1); // impostiamo in maiuscolo la prima lettere del nome
        System.out.print("Inserire cognome > ");
        String cognome = scanner.nextLine();
        cognome = cognome.substring(0, 1).toUpperCase() + cognome.substring(1);//impostiamo in maiuscolo la prima lettere del cognome

        //Richiamare il metodo isEsistente per verificare se il nome e cognome inseriti corrispondono a un contatto già esistente
        if (isEsistente(nome, cognome) == false) {
            String recapito;
            do {
                System.out.print("Inserire recapito telefonico > ");
                recapito = scanner.nextLine();
                if (recapito.length() != 10 || !recapito.matches("\\d{10}")) {
                    System.out.println("\nERRORE, il recapito inserito non è composto da 10 numeri!\n");
                    pause();
                }
            } while (recapito.length() != 10 || !recapito.matches("\\d{10}"));
            System.out.print("Inserire Email (Premere invio per non registrare)> ");
            String email = scanner.nextLine();

            //allocazione dell'oggetto contatto creato (contattoAdd)
            Contatto contattoAdd = new Contatto(nome, cognome, recapito, email);
            rubrica.aggiungiContatto(contattoAdd);

            System.out.println("\n---------- CONTATTO INSERITO ----------");
            printDati(contattoAdd);
            System.out.println("---------------------------------------");
        }

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

//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------    
    //SEZIONE: METODI PER LA RIMOZIONE DEI CONTATTI
    /**
     * Metodo per rimuovere un contatto dalla rubrica, (ArrayList).
     */
    public void rimuoviContatto() {
        int scelta = 0; //variabile per gestire le opzione del menù
        boolean esiste = false; // variabile utilizzata per verificare che il contatto che si vuole eliminare sia esistente
        do {
            cls();
            System.out.println("\n---- MENU' RIMUOVI CONTATTO ----\n");
            System.out.println("     1) Visualizza rubrica");
            System.out.println("     2) Rimuovi contatto");
            System.out.println("     3) MENU' principale");
            System.out.println("--------------------------------");
            try {
                System.out.print("\nInserire opzione > ");
                scelta = scanner.nextInt();
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
                        System.out.print("\nInserire il nome > ");
                        String nome = scanner.next();
                        scanner.nextLine();
                        System.out.print("Inserire il cognome > ");
                        String cognome = scanner.nextLine();
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
        cls();
        System.out.println("-- CERCA CONTATTO --");
        boolean isTrovato = false; //variabile per gestire la ricerca di un contatto; true = contatto trovato; false =  contatto non trovato
        if (rubrica.getRubrica().isEmpty()) {
            System.out.println("\n-- RUBRICA VUOTA --");
        } else {
            System.out.print("\nInserire nome da cercare> ");
            String nome = scanner.next();
            scanner.nextLine();
            System.out.print("Inserire cognome da cercare > ");
            String cognome = scanner.nextLine();
            for (int i = 0; i < rubrica.getRubrica().size(); i++) {
                if (nome.equalsIgnoreCase(rubrica.getRubrica().get(i).getNome()) == true
                        && cognome.equalsIgnoreCase(rubrica.getRubrica().get(i).getCognome()) == true) {
                    isTrovato = true;
                    System.out.println("\n----- CONTATTO TROVATO -----");
                    printDati(rubrica.getRubrica().get(i));
                    System.out.println("-----------------------------");
                    return new Contatto(rubrica.getRubrica().get(i).getNome(), rubrica.getRubrica().get(i).getCognome(),
                            rubrica.getRubrica().get(i).getRecapito(), rubrica.getRubrica().get(i).getEmail());
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
                scelta = scanner.nextInt();
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
                        if (cercaContatto() != null) {
                            for (int i = 0; i < rubrica.getRubrica().size(); i++) {
                                System.out.print("\nInserire nuovo nome (Premere invio per non modificare)> ");
                                String nomeM = scanner.nextLine();
                                System.out.print("Inserire nuovo cognome (Premere invio per non modificare)> ");
                                String cognomeM = scanner.nextLine();
                                //Richiamare il metodo isEsistente per verificare se il nome e cognome inseriti corrispondono a un contatto già esistente.
                                if (isEsistente(nomeM, cognomeM) == false) {
                                    String recapitoM;
                                    boolean isPossible = false; //variabile per gestire l'inserimento del recapito telefonico
                                    do {
                                        System.out.print("Inserire nuovo recapito telefonico (Premere invio per non modificare)> ");
                                        //contattoAdd.setRecapito(scanner.nextLine());
                                        recapitoM = scanner.nextLine();                        //controlliamo che il numero non contenga lettere
                                        if (recapitoM.isEmpty() || recapitoM.length() == 10 || recapitoM.matches("\\d{10}")) {
                                            isPossible = true;
                                        } else {
                                            System.out.println("\nRIPROVARE, Recapito telefonico non composto da 10 elementi\n");
                                            pause();
                                        }
                                    } while (isPossible == false);
                                    System.out.print("Inserire nuova Email (Premere invio per non modificare)> ");
                                    String emailM = scanner.nextLine();

                                    //controlli per non apportare modifiche ai campi del contatto
                                    if (nomeM != null && !nomeM.isEmpty()) {
                                        nomeM = nomeM.substring(0, 1).toUpperCase() + nomeM.substring(1);
                                        rubrica.getRubrica().get(i).setNome(nomeM);
                                    }
                                    if (cognomeM != null && !cognomeM.isEmpty()) {
                                        cognomeM = cognomeM.substring(0, 1).toUpperCase() + cognomeM.substring(1);
                                        rubrica.getRubrica().get(i).setCognome(cognomeM);
                                    }
                                    if (!recapitoM.isEmpty()) {
                                        rubrica.getRubrica().get(i).setRecapito(recapitoM);
                                    }
                                    // Per l'email, verifico che non sia vuota e contenga '@'
                                    if (emailM != null && !emailM.isEmpty() && emailM.contains("@")) {
                                        rubrica.getRubrica().get(i).setEmail(emailM);
                                    }
                                    System.out.println("\n----- CONTATTO MODIFICATO -----");
                                    printDati(rubrica.getRubrica().get(i));
                                    System.out.println("-------------------------------");
                                    System.out.println();
                                    pause();
                                }else{
                                    pause();
                                }
                                break;
                            }

                        }else{
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
