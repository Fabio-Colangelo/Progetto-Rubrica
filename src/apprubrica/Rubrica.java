package apprubrica;

import java.util.*;

public class Rubrica {

    private ArrayList<Contatto> rubrica = new ArrayList<>();
    
    //AGGIUNGERE COMMENTI DI DOCUMENTAZIONE
    
    //metodo utilizzato per aggiungere un contatto all' interno della rubrica
    public  void aggiungiContatto(Contatto contattoAdd) {
        rubrica.add(contattoAdd);
    }
    
    //metodo utilizzato per rimuovere un contatto all' interno della rubrica
    public void removeContatto(int index){
        rubrica.remove(index);
    }
    
    //metodo utilizzato per la stampa della rubrica
    public ArrayList<Contatto> getRubrica() {
        return rubrica;
    }
}
     