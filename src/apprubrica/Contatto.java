package apprubrica;

public class Contatto {

    private String nome;
    private String cognome;
    private String recapito;
    private String email;

    public Contatto(String nome, String cognome, String recapito, String email) {
        this.nome = nome;
        this.cognome = cognome;
        this.recapito = recapito;
        if (!email.isEmpty() && email.contains("@")) {
            this.email = email;
        } else {
            this.email = "non registrata";
        }

    }
      
    public String getNome() {
        return nome;
    }

    public String getCognome() {
        return cognome;
    }

    public String getRecapito() {
        return recapito;
    }

    public String getEmail() {
        return email;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public void setRecapito(String recapito) {
        this.recapito = recapito;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
