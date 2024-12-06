package pt.isec.pa.apoio_poe.model.data.propostas;

public class Proposta {
    private String codI;
    private String titulo;
    private long nrAluno;
    private String emailDoc;


    public Proposta(String codI, String titulo) {
        this.codI = codI;
        this.titulo = titulo;
        this.nrAluno = 0;
        this.emailDoc = null;
    }

    public Proposta(String codI, String titulo, long nrAluno) {
        this.codI = codI;
        this.titulo = titulo;
        this.nrAluno = nrAluno;
        this.emailDoc = null;
    }

    public void setNrAluno(long nrAluno) {
        this.nrAluno = nrAluno;
    }

    public void setEmailDoc(String emailDoc) {
        this.emailDoc = emailDoc;
    }

    public String getCodI() {
        return this.codI;
    }
    public String getTitulo() {
        return this.titulo;
    }

    public long getNrAluno() {
        return nrAluno;
    }
    public boolean isAlunoAtribuido() {return nrAluno != 0;}

    public String getEmailDoc() {
        return emailDoc;
    }
    public boolean isDocenteAtribuido() {
        return emailDoc != null;
    }


}

