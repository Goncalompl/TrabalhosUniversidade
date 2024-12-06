package pt.isec.pa.apoio_poe.model.data.docente;

public class Docente {
    private String nome;
    private String email;


    public Docente(String nome, String email) {
        this.nome = nome;
        this.email = email;

    }

    @Override
    public String toString() {
        String frase = "Nome: " + nome + "\nEmail: " + email;
        return frase;
    }

    public String getEmail() {
        return email;
    }

    public String getNome() {
        return nome;
    }


    public void setEmail(String email) {
        this.email = email;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}