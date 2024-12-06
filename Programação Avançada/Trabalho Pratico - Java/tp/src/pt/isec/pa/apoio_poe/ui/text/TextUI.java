package pt.isec.pa.apoio_poe.ui.text;

import pt.isec.pa.apoio_poe.model.data.Comando;
import pt.isec.pa.apoio_poe.model.data.TipoComando;
import pt.isec.pa.apoio_poe.model.data.alunos.Aluno;
import pt.isec.pa.apoio_poe.model.data.docente.Docente;
import pt.isec.pa.apoio_poe.model.data.propostas.Proposta;
import pt.isec.pa.apoio_poe.model.fsm.*;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class TextUI {
    private MaquinaEstados maquinaEstados;

    public TextUI() {
        maquinaEstados = new MaquinaEstados();
    }

    public void run() {
        System.out.println("O Programa começou");

        while (true) {
            if (maquinaEstados == null)
                return;

            switch (maquinaEstados.getEstadoAtual()) {
                case CONFIG -> uiConfiguracao();
                case GERIR_ALUNOS -> uiGestaoAlunos();
                case GERIR_DOC -> uiGestaoDocentes();
                case GERIR_PROP -> uiGestaoPropostas();
                case OP_CAND -> uiOpcoesCandidatura();
                case ATRIB_PROP -> uiAtribuicaoPropostas();
                case ATRIB_ORIENT -> uiAtribuicaoOrientadores();
                case CONSULTA -> uiConsultar();
            }
        }
    }

    private void uiConsultar() {
        String[] opcoes = {"Aluno atribuido", "Sem proposta", "Propostas disponiveis", "propostas atribuidas", "Lista docentes", "Guardar Ficheiro", "Fechar fase", "Recomeçar", "Sair"};
        Comando comando = null;
        switch (getEscolha(opcoes)) {
            case 1:
                uiAtribuido();
                break;
            case 2:
                uiSemProposta();
                break;
            case 3:
                uiPropostasDisponiveis();
                break;
            case 4:
                uiPropostasAtribuidas();
                break;
            case 5:
                uiListarAlunosDocentes();
                break;
            case 6:
                comando.getParams().add("C6");
                uiGuardaInfo(comando);
                break;
            case 7:
                maquinaEstados.fechaFase();
                break;
            case 8:
                maquinaEstados.recomecar();
                break;
            case 9:
                maquinaEstados = null;
                break;


        }
    }

    private void uiGuardaInfo(Comando comando) {
        System.out.println("Insira o nome do ficheiro");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        comando.getParams().add(input);
        maquinaEstados.gerir(comando);
        mostraRegisto();

    }

    private void uiPropostasAtribuidas() {
        for (Proposta proposta : maquinaEstados.getPropostasAtribuidas()) {
            System.out.println(proposta);
        }
        Comando cmd = new Comando(TipoComando.PROPOSTAS_ATR);
        maquinaEstados.gerir(cmd);
        mostraRegisto();

    }

    private void uiPropostasDisponiveis() {
        for (Proposta proposta : maquinaEstados.getPropostasDis()) {
            System.out.println(proposta);
        }
        Comando cmd = new Comando(TipoComando.PROPOSTAS_DIS);
        maquinaEstados.gerir(cmd);
        mostraRegisto();
    }

    private void uiSemProposta() {
        for (Aluno aluno : maquinaEstados.getAlunosNAtribuidos()) {
            System.out.println(aluno);
        }
        Comando cmd = new Comando(TipoComando.ALUNOS_NATR);
        maquinaEstados.gerir(cmd);
        mostraRegisto();
    }

    private void uiAtribuido() {
        for (Aluno aluno : maquinaEstados.getAlunosAtribuidos()) {
            System.out.println(aluno);
        }
        Comando cmd = new Comando(TipoComando.ALUNOS_ATR);
        maquinaEstados.gerir(cmd);
        mostraRegisto();
    }


    private void uiAtribuicaoOrientadores() {
        String[] opcoes = {"Associacao automatica (Docentes)", "Atribuicao do Docente", "Gestao da Atribuicao Manual", "Listar alunos", "Fase Anterior", "Fechar fase", "Sair"};
        switch (getEscolha(opcoes)) {
            case 1:
                uiAtribuirAutoA();
                break;
            case 2:
                uiAtribuirManualND();
                break;
            case 3:

                //uiConsultarDocentes(comando);
                break;
            case 4:

                uiListarAlunosDocentes();
                break;
            case 5:
                maquinaEstados.faseAnterior();
                ;
                break;
            case 6:
                maquinaEstados.fechaFase();
                break;
            case 7:
                maquinaEstados = null;
                break;


        }
    }

    public void uiAtribuirManualND() {
        String codI;
        String email;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Indique o codI da proposta : ");
        codI = scanner.nextLine();
        System.out.println("Indique o email : ");
        email = scanner.nextLine();

        maquinaEstados.atribuirDocenteManual(email, codI);
        Comando cmd = new Comando(TipoComando.ATRIBUI_DOCENTE_M);


        maquinaEstados.gerir(cmd);
        mostraRegisto();
    }

    public void uiListarAlunosDocentes() {
        String[] opcoes = {"Aluno proposta A D", "Aluno proposta A SD", "Proposta Atribuida"};
        switch (getEscolha(opcoes)) {
            case 1:
                for (Aluno aluno : maquinaEstados.getAlunosProDocAtr()) {
                    System.out.println(aluno);
                }

                mostraRegisto();
                break;
            case 2:
                for (Aluno aluno : maquinaEstados.getAlunosProDocNAtr()) {
                    System.out.println(aluno);
                }
                mostraRegisto();
                break;
            case 3:
                System.out.println("Media de propostas" + maquinaEstados.mediaProDocentes());
                System.out.println("Minimo de propostas" + maquinaEstados.minProDocentes());
                System.out.println("Maximo de propostas" + maquinaEstados.maxProDocentes());
                mostraRegisto();
                break;

        }
        Comando cmd = new Comando(TipoComando.LISTAR_PROP_D);
        maquinaEstados.gerir(cmd);
        mostraRegisto();

    }

    private void uiAtribuicaoPropostas() {
        String[] opcoes = {"Atribuicao automatica (Aluno atribuido)", "Atribuicao automatica (n atribuido)", "Atribuicao manual", "Listar alunos", "Proxima fase", "Fechar fase", "Fase anterior", "Sair"};
        //ArrayList<String> comando = new ArrayList<>();
        switch (getEscolha(opcoes)) {
            case 1:
                uiAtribuirAutoD();
                break;
            case 2:
                uiAtribuirAutoNA();
                break;
            case 3:
                uiAtribuirManual();
                break;
            case 4:
                uiListarAlunosFase3();
                break;
            case 5:
                maquinaEstados.proximaFase();
                break;
            case 6:
                maquinaEstados.fechaFase();
                break;
            case 7:
                maquinaEstados.faseAnterior();
                break;
            case 8:
                maquinaEstados = null;
                break;


        }
    }

    private void uiAtribuirAutoD() {
        Comando cmd = new Comando(TipoComando.ATRIBUI_DOCENTE_M);
        maquinaEstados.atribuiProjetos1();
        maquinaEstados.gerir(cmd);
        mostraRegisto();
    }

    /*public String atribuiManual(long nrAluno, String codI) {
        Proposta proposta = propostas.consulta(codI);
        if (proposta == null)
            return "Proposta nao encontrada";
        if (proposta.isAlunoAtribuido())
            return "Proposta ja foi atribuida";
        if (alunos.consultar(nrAluno) == null)
            return "Aluno nao encontrado";
        proposta.setNrAluno(nrAluno);
        return "Proposta atribuida com sucesso";
    }*/

    private void uiAtribuirAutoNA() {

        Comando cmd = new Comando(TipoComando.ATRIBUI_PROP_NA);
        maquinaEstados.atribuiPropostas();
        maquinaEstados.gerir(cmd);
        mostraRegisto();
    }

    private void uiAtribuirManual() {
        String codI;
        long nrAluno;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Indique o codI da candidatura : ");
        codI = scanner.nextLine();
        System.out.println("Indique o numero de aluno");
        nrAluno = scanner.nextLong();

        Comando cmd = new Comando(TipoComando.ATRIBUI_PROP_MANUAL);
        maquinaEstados.atribuiManual(nrAluno, codI);
        maquinaEstados.gerir(cmd);
        mostraRegisto();
    }

    private void uiAtribuirAutoA() {
        Comando cmd = new Comando(TipoComando.ATRIBUI_PROP_A);
        maquinaEstados.atribuiPropostas();
        maquinaEstados.gerir(cmd);
        mostraRegisto();
    }

    private void uiListarAlunosFase3() {
        String[] opcoes = {"Autoproposta associada", "Candidatura Registada", "Proposta Atribuida", "Proposta nao atribuida"};
        switch (getEscolha(opcoes)) {
            case 1:
                maquinaEstados.getAlunosAutopropostos1();
                mostraRegisto();
                break;
            case 2:
                maquinaEstados.getCandidaturasRegistadas1();
                break;
            case 3:
                maquinaEstados.getAlunosAtribuidos();
                mostraRegisto();
                break;
            case 4:
                maquinaEstados.getAlunosNAtribuidos();
                mostraRegisto();
                break;
        }
    }

    private void uiOpcoesCandidatura() {
        String[] opcoes = {"Adicionar Candidatura", "Listar Candidaturas", "Consultar Candidatura", "Listar alunos autopropostos", "Listar alunos Candidatura R",
                "Listar alunos Candidatura NR", "Proxima fase", "Fechar fase", "Fase anterior", "Sair"};
        //ArrayList<String> comando = new ArrayList<>();
        switch (getEscolha(opcoes)) {
            case 1:
                uiAdicionarCandidatura();
                break;
            case 2:
                uiListarCandidaturas();
                break;
            case 3:
                uiConsultarCandidatura();
                break;
            case 4:
                uiListarAlunosAutopropostos();
                break;
            case 5:
                uiListarAlunosCandidaturas();
                break;
            case 6:
                uiListarAlunosNCandidaturas();
            case 7:
                maquinaEstados.proximaFase();
                break;
            case 8:
                maquinaEstados.fechaFase();
                break;
            case 9:
                maquinaEstados.faseAnterior();
                break;
            case 10:
                maquinaEstados = null;
                break;


        }
    }

    private void uiListarAlunosNCandidaturas() {
        for (Aluno aluno : maquinaEstados.getAlunosNAtribuidos()) {
            System.out.println(aluno);
        }
        Comando cmd = new Comando(TipoComando.ALUNOS_NATR);
        maquinaEstados.gerir(cmd);
        mostraRegisto();
    }

    private void uiListarAlunosAutopropostos() {
        for (Aluno aluno : maquinaEstados.getAlunosAutopropostos1()) {
            System.out.println(aluno);
        }

        Comando cmd = new Comando(TipoComando.ALUNOS_AUTOPROPOSTOS);
        maquinaEstados.getAlunosAutopropostos1();
        maquinaEstados.gerir(cmd);
        mostraRegisto();
    }


    private void uiListarCandidaturas() {

        Comando cmd = new Comando(TipoComando.LISTAR_CAND);
        maquinaEstados.gerir(cmd);
        mostraRegisto();
    }

    private void uiAdicionarCandidatura() {
        System.out.println("Qual o codI da proposta: ");
        Scanner scanner = new Scanner(System.in);
        Comando cmd = new Comando(TipoComando.INSERIR_CAND);

        cmd.getParams().add(scanner.nextLine());

        maquinaEstados.gerir(cmd);
        mostraRegisto();
    }

    private void uiConsultarCandidatura() {
        String codI;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Indique o codI da candidatura : ");
        codI = scanner.nextLine();

        Comando cmd = new Comando(TipoComando.CONSULTAR_CAND);
        maquinaEstados.consultaCandidatura(codI);

        maquinaEstados.gerir(cmd);
        mostraRegisto();
    }

    private void uiListarAlunosCandidaturas() {
        for (Aluno aluno : maquinaEstados.getAlunosCandidatura()) {
            System.out.println(aluno);
        }

        Comando cmd = new Comando(TipoComando.ALUNOS_CAND);
        maquinaEstados.gerir(cmd);
        mostraRegisto();
    }

    private void uiConfiguracao() {
        String[] opcoes = {"Gestão de alunos", "Gestão de docentes", "Gestão de Propostas", "Proxima fase", "Fechar fase", "Sair"};
        switch (getEscolha(opcoes)) {
            case 1:
                maquinaEstados.gerirAlunos();
                break;
            case 2:
                maquinaEstados.gerirDoc();
                break;
            case 3:
                maquinaEstados.gerirProp();
                break;
            case 4:
                maquinaEstados.proximaFase();
                break;
            case 5:
                maquinaEstados.fechaFase();
                break;
            case 6:
                maquinaEstados = null;
                break;

        }
    }

    private void uiGestaoAlunos() {
        String[] opcoes = {"Inserir aluno", "Listar alunos", "Consultar aluno", "Inserir csv", "voltar"};
        //ArrayList<String> comando = new ArrayList<>();

        switch (getEscolha(opcoes)) {
            case 1:
                uiIserirAluno();
                break;
            case 2:
                uiListarAlunos();
                break;
            case 3:
                uiConsultarAluno();
                break;
            case 4:
                uiFicherioCsv();
                break;
            case 5:
                maquinaEstados.voltar();


        }
    }

    private void uiFicherioCsv() {
        System.out.println("Insira o nome do ficheiro:");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        Comando cmd = new Comando(TipoComando.LER_CSV_ALUNOS);
        cmd.getParams().add(input);
        maquinaEstados.gerir(cmd);
        mostraRegisto();

    }

    private void uiConsultarAluno() {
        String nrAluno;
        Scanner scanner = new Scanner(System.in);
        Aluno aluno;
        System.out.println("Inserir o numero de aluno: ");
        nrAluno = scanner.nextLine();
        Comando cmd = new Comando(TipoComando.CONSULTAR_ALUNO, nrAluno);
        maquinaEstados.gerir(cmd);
        mostraRegisto();
    }

    private void uiListarAlunos() {
        Comando cmd = new Comando(TipoComando.LISTAR_ALUNOS);
        maquinaEstados.gerir(cmd);
        mostraRegisto();
    }

    private void uiIserirAluno() {
        //Aluno(long nrE, String nome, String email, String siglaC, String siglaR, double classificacao, boolean aceder)
        String nome, email, siglaC, siglaR;
        String nrE;
        String classificacao;
        String aceder;
        Scanner scanner = new Scanner(System.in);

        System.out.print("Introduza o Nome: ");
        nome = scanner.nextLine();
        System.out.print("Introduza o Numero de Aluno: ");
        nrE = scanner.nextLine();
        System.out.print("Introduza o email: ");
        email = scanner.nextLine();
        System.out.print("Introduza a Sigla de Curso: ");
        siglaC = scanner.nextLine();
        System.out.print("Introduza a Sigla de Ramo: ");
        siglaR = scanner.nextLine();
        System.out.print("Introduza a Classificaçao: ");
        classificacao = scanner.nextLine();
        System.out.print("O aluno pode aceder a Estagio? (S/N): ");
        aceder = scanner.nextLine();
        String[] params = new String[7];
        params[0] = (nrE);
        params[1] = (nome);
        params[2] = (email);
        params[3] = (siglaC);
        params[4] = (siglaR);
        params[5] = (classificacao);
        params[6] = (aceder);

        Comando cmd = new Comando(TipoComando.INSERIR_ALUNO, params);
        maquinaEstados.gerir(cmd);
        mostraRegisto();
    }

    private void uiGestaoDocentes() {
        String[] opcoes = {"Inserir docente", "Listar docente", "Consultar docente", "Ficherio csv", "voltar"};
        //ArrayList<String> comando = new ArrayList<>();

        switch (getEscolha(opcoes)) {
            case 1:
                uiIserirDocente();
                break;
            case 2:
                uiListarDocentes();
                break;
            case 3:
                uiConsultarDocente();
                break;
            case 4:
                uiFicherioCsv();
                break;
            case 5:
                maquinaEstados.voltar();
                break;

        }
    }

    private void uiConsultarDocente() {
        String emailDocente;
        Scanner scanner = new Scanner(System.in);
        Docente docente;
        System.out.println("Inserir o email de docente: ");
        emailDocente = scanner.nextLine();
        Comando cmd = new Comando(TipoComando.CONSULTAR_DOC, emailDocente);
        maquinaEstados.gerir(cmd);
        mostraRegisto();

    }

    private void uiListarDocentes() {
        for (Docente docente : maquinaEstados.getDocentes()) {
            System.out.println(docente);
        }
        Comando cmd = new Comando(TipoComando.LISTAR_DOCS);
        maquinaEstados.gerir(cmd);
        mostraRegisto();
    }

    private void uiIserirDocente() {
        //Docente(long nrE, String nome, String email, String siglaC, String siglaR, double classificacao, boolean aceder)
        String nome, email;
        Scanner scanner = new Scanner(System.in);

        System.out.println("Introduza o Nome do Docente: ");
        nome = scanner.nextLine();
        System.out.println("Introduza o Email do Docente: ");
        email = scanner.nextLine();

        String [] params = new String[2];

        params[0] = nome;
        params[1] = email;

        Comando cmd = new Comando(TipoComando.INSERIR_DOC, params);
        maquinaEstados.gerir(cmd);
        mostraRegisto();
    }

    private void uiGestaoPropostas() {
        String[] opcoes = {"Inserir porposta", "Listar porposta", "Consultar porposta", "Ficheiro Csv", "voltar"};

        switch (getEscolha(opcoes)) {
            case 1:
                uiIserirPropostaE();
                break;
            case 2:
                uiListarPropostas();
                break;
            case 3:
                uiConsultarProposta();
                break;
            case 4:
                uiFicherioCsv();
                break;
            case 5:
                maquinaEstados.voltar();
                break;

        }
    }

    private void uiConsultarProposta() {
        String codI;
        Scanner scanner = new Scanner(System.in);
        Proposta proposta;
        System.out.println("Inserir o numero de proposta: ");
        codI = scanner.nextLine();


        proposta = maquinaEstados.consultaProposta(codI);

        if (proposta == null)
            System.out.println("Proposta nao encontrado");
        else System.out.println(proposta);

        Comando cmd = new Comando(TipoComando.CONSULTAR_PROP, codI);
        maquinaEstados.gerir(cmd);
        mostraRegisto();
    }

    private void uiListarPropostas() {
        Comando cmd = new Comando(TipoComando.LISTA_PROP, "");

        maquinaEstados.gerir(cmd);
        mostraRegisto();
    }

    private void uiIserirPropostaE() {
        //Proposta(long nrE, String nome, String email, String siglaC, String siglaR, double classificacao, boolean aceder)
        String codI, titulo,area, entidade;
        long aluno;
        Scanner scanner = new Scanner(System.in);

        System.out.println("Introduza o Codigo da Proposta");
        codI = scanner.nextLine();
        System.out.println("Introduza o titulo");
        titulo = scanner.nextLine();
        System.out.println("Introduza o area");
        area = scanner.nextLine();
        System.out.println("Introduza a entidade");
        entidade = scanner.nextLine();
        System.out.println("Introduza o numero do aluno (caso queira associar)");
        aluno = scanner.nextLong();

        String [] params = new String[5];
        params[0] = codI;
        params[1] = area;
        params[2] = titulo;
        params[3] = entidade;
        params[4] = String.valueOf(aluno);

        Comando cmd = new Comando(TipoComando.INSERIR_PROP_E, params);
        maquinaEstados.gerir(cmd);
        mostraRegisto();




    }

    /*private void uiEditaAluno() {
        Aluno x = consultar(nrAluno);
        if (x == null)
            return;

        Scanner scanner = new Scanner(System.in);
        int num;
        if (!verifica(x)) {
            num = 8;
        } else {
            System.out.println("Insira os dados que pertende alterar:\n 1- Numero de Estudante\n 2-Nome\n 3-email\n 4-Sigla do Curso\n 5-Sigla do Ramo\n 6-Classificaçao\n 7-Acede a estagio\n 0-Sair");
            num = scanner.nextInt();
        }
        Scanner scanner1 = new Scanner(System.in);
        switch (num) {
            case 1:
                System.out.println("Introduza um novo Numero de estudante: ");
                long nrEst = scanner1.nextLong();
                for (Aluno aluno : alunosM) {
                    if (aluno.getNrEstudante() == x.getNrEstudante())
                        System.out.println("O numero ja existe!!");
                    break;
                }
                x.setNrEstudante(nrEst);
                break;
            case 2:
                System.out.println("Introduza um novo Nome: ");
                String nome = scanner1.nextLine();
                x.setNome(nome);
                break;
            case 3:
                System.out.println("Introduza um novo Email: ");
                String email = scanner1.nextLine();
                for (Aluno aluno : alunosM) {
                    if (aluno.getEmail().equals(x.getEmail())) {
                        System.out.println("O email ja existe!!");
                        break;
                    }
                }
                x.setEmail(email);
                break;
            case 4:
                System.out.println("Introduza a nova sigla de curso: ");
                String siglaC = scanner1.nextLine();
                x.setSiglaC(siglaC);
                break;
            case 5:
                System.out.println("Introduza a nova sigla de ramo: ");
                String siglaR = scanner1.nextLine();
                x.setSiglaR(siglaR);
                break;
            case 6:
                System.out.println("Introduza uma nova classificação ");
                double classificacao = scanner1.nextInt();
                x.setClassificacao(classificacao);
                break;
            case 7:
                if (x.getAcederEstagio()) {
                    x.setAcederEstagio(false);
                } else x.setAcederEstagio(true);
                break;
            case 8:
                System.out.println("O Aluno nao esta inserido nos dados");
        }
    }*/

    private int getEscolha(String[] escolhas) {
        Scanner scanner = new Scanner(System.in);
        int escolha;

        while (true) {
            System.out.println("Select option:");
            for (int i = 0; i < escolhas.length; i++)
                System.out.println((i + 1) + " - " + escolhas[i]);

            System.out.print("> ");

            try {
                escolha = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input.");
                continue;
            }

            if (escolha >= 1 && escolha <= escolhas.length) break;

            System.out.println("Invalid input.");
        }
        return escolha;
    }

    private void mostraRegisto() {
        for (String string : maquinaEstados.getRegisto()) {
            System.out.println(string);
        }
    }

    public static void main(String[] args) {
        TextUI textUI = new TextUI();
        textUI.run();
    }
}
