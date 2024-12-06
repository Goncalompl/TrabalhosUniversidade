package pt.isec.pa.apoio_poe.ui.gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.Window;
import pt.isec.pa.apoio_poe.model.data.Comando;
import pt.isec.pa.apoio_poe.model.data.Gestor;
import pt.isec.pa.apoio_poe.model.data.TipoComando;
import pt.isec.pa.apoio_poe.model.fsm.MaquinaEstados;
import pt.isec.pa.apoio_poe.model.fsm.MaquinaEstadosObservable;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import static pt.isec.pa.apoio_poe.model.fsm.EstadoGestor.*;

public class RootPane extends MyBorderPane implements PropertyChangeListener {


    MaquinaEstadosObservable maquinaEstados;
    Gestor gestor;


    public RootPane(MaquinaEstadosObservable maquinaEstados) {
        this.maquinaEstados = maquinaEstados;
        maquinaEstados.addPropertyChangeListener(this);
        Button start = new MyButton("Começar");

        Label lblWc = new Label(("Bem-Vindo a Gestão de Projetos/Estágios do Isec!"));

        lblWc.setScaleX(2);
        lblWc.setScaleY(2);
        lblWc.setTextFill(Color.WHITE);
        lblWc.setFont(Font.font("Verdana", FontWeight.BOLD, 12));

        VBox toolBar = new VBox();
        toolBar.getChildren().addAll(start, lblWc);
        toolBar.setAlignment(Pos.BOTTOM_CENTER);
        changeBackgorund(toolBar, Color.DARKCYAN);
        toolBar.setPadding(new Insets(400));
        this.setTop(toolBar);
        toolBar.setSpacing(50);


        start.setOnAction(actionEvent -> maquinaEstados.voltar());

    }



    void changeBackgorund(Region region, Color color) {
        region.setBackground(new Background(new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY)));

    }


    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (maquinaEstados.getEstadoAtual()) {
            case CONFIG -> guiConfiguracao();
            case GERIR_ALUNOS -> guiGestaoAlunos();
            case GERIR_DOC -> guiGestaoDocentes();
            case GERIR_PROP -> guiGestaoPropostas();
            case OP_CAND -> guiOpcoesCandidatura();
            case ATRIB_PROP -> guiAtribuicaoPropostas();
            case ATRIB_ORIENT -> guiAtribuicaoOrientadores();
            case CONSULTA -> guiConsultar();
        }
    }


    private void guiConfiguracao() {
        this.getChildren().clear();
        Button btnA = new MyButton("Gestao de Alunos");
        Button btnD = new MyButton("Gestao de Docentes");
        Button btnP = new MyButton("Gestao de Propostas");
        Button btnFechar = new MyButton("Fechar fase");
        Button btnProx = new MyButton("Proxima fase");

        Label lblWc = new Label(("Bem-Vindo a Gestão de Projetos/Estágios do Isec!"));

        lblWc.setScaleX(2);
        lblWc.setScaleY(2);
        lblWc.setTextFill(Color.WHITE);
        lblWc.setFont(Font.font("Verdana", FontWeight.BOLD, 12));


        VBox toolBar = new VBox();
        toolBar.getChildren().addAll(lblWc, btnA, btnD, btnP, btnFechar, btnProx);
        toolBar.setAlignment(Pos.BOTTOM_CENTER);
        changeBackgorund(toolBar, Color.DARKCYAN);
        toolBar.setPadding(new Insets(200));
        this.setTop(toolBar);
        toolBar.setSpacing(50);


        btnA.setOnAction(actionEvent -> {
            maquinaEstados.gerirAlunos();
        });
        btnD.setOnAction(actionEvent -> {
            maquinaEstados.gerirDoc();
        });
        btnP.setOnAction(actionEvent -> {
            maquinaEstados.gerirProp();
        });
        btnFechar.setOnAction(actionEvent -> {
            maquinaEstados.fechaFase();
        });
        btnProx.setOnAction(actionEvent -> {
            maquinaEstados.proximaFase();
        });
    }

    private void guiGestaoAlunos() {
        this.getChildren().clear();

        Button inserirButton = new MyButton("Inserir Aluno");
        Button consultarButton = new MyButton("Consultar Aluno");
        Button editarButton = new MyButton("Editar Aluno");
        Button eliminarButton = new MyButton("Eliminar Aluno");
        Button csv = new MyButton("cvs");
        Button voltarButton = new MyButton("Voltar");
        Label label = new Label("Programação Avançada 2021/2022");


        VBox toolBar = new VBox();
        toolBar.getChildren().addAll(inserirButton, consultarButton, editarButton, eliminarButton, csv,voltarButton, label);
        toolBar.setAlignment(Pos.BOTTOM_CENTER);
        changeBackgorund(toolBar, Color.DARKCYAN);
        toolBar.setPadding(new Insets(210));
        this.setTop(toolBar);
        toolBar.setSpacing(50);






        inserirButton.setOnAction(actionEvent -> guiInserirAluno());
        consultarButton.setOnAction(actionEvent -> guiConsultarAluno());
        editarButton.setOnAction(actionEvent -> guiEditarAluno());
        eliminarButton.setOnAction(actionEvent -> guiEliminarAluno());
        csv.setOnAction(actionEvent -> guiCarregarCsv());
        voltarButton.setOnAction(actionEvent -> {
            maquinaEstados.voltar();
        });



    }

    private void guiGestaoDocentes() {
        this.getChildren().clear();

        Button inserirButton = new MyButton("Inserir Docente");
        Button consultarButton = new MyButton("Consultar Docente");
        Button editarButton = new MyButton("Editar Docente");
        Button eliminarButton = new MyButton("Eliminar Docente");
        Button csv = new MyButton("cvs");
        Button voltarButton = new MyButton("Voltar");


        VBox toolBar = new VBox();
        toolBar.getChildren().addAll(inserirButton, consultarButton, editarButton, eliminarButton, csv,voltarButton);
        toolBar.setAlignment(Pos.BOTTOM_CENTER);
        changeBackgorund(toolBar, Color.DARKCYAN);
        toolBar.setPadding(new Insets(200));
        this.setTop(toolBar);
        toolBar.setSpacing(50);

        inserirButton.setOnAction(actionEvent -> guiInserirDoc());
        consultarButton.setOnAction(actionEvent -> guiConsultarDoc());
        editarButton.setOnAction(actionEvent -> guiEditarDoc());
        eliminarButton.setOnAction(actionEvent -> guiEliminarDoc());
        csv.setOnAction(actionEvent -> guiCarregarCsvDoc());
        voltarButton.setOnAction(actionEvent -> {
            maquinaEstados.voltar();
        });

    }

    private void guiGestaoPropostas() {
        this.getChildren().clear();
        Button gerirEstagio = new MyButton("Gerir Estagio");
        Button gerirProjeto = new MyButton("Gerir Projeto");
        Button voltar = new MyButton("Voltar");

        VBox toolBar = new VBox();
        toolBar.getChildren().addAll(gerirEstagio,gerirProjeto,voltar);
        toolBar.setAlignment(Pos.BOTTOM_CENTER);
        changeBackgorund(toolBar, Color.DARKCYAN);
        toolBar.setPadding(new Insets(100));
        this.setTop(toolBar);
        toolBar.setSpacing(50);

        gerirEstagio.setOnAction(actionEvent -> guiGerirEstagio());
        gerirProjeto.setOnAction(actionEvent -> guiGerirProjeto());

        voltar.setOnAction(actionEvent -> {
            maquinaEstados.voltar();
        });

    }

    private void guiOpcoesCandidatura() {
        this.getChildren().clear();
        Button btnAdicionarC = new MyButton("Adicionar Candidatura");
        Button btnListarC = new MyButton("Listar Candidaturas");
        Button btnConsultarC = new MyButton("Consultar Candidatura");
        Button btnListarAAuto = new MyButton("Listar alunos autopropostos");
        Button btnListarCRegistada = new MyButton("Listar alunos Candidatura R");
        Button btnListarCNRegistada = new MyButton("Listar alunos Candidatura NR");
        Button btnProx = new MyButton("Proxima fase");
        Button btnFechar = new MyButton("Fechar fase");
        Button btnFAnterior = new MyButton("Fase anterior");
        Label resultado = new Label("");

        Label lblWc = new Label(("Bem-Vindo a Gestão de Projetos/Estágios do Isec!"));

        lblWc.setScaleX(2);
        lblWc.setScaleY(2);
        lblWc.setTextFill(Color.WHITE);
        lblWc.setFont(Font.font("Verdana", FontWeight.BOLD, 12));


        VBox toolBar = new VBox();
        toolBar.getChildren().addAll(lblWc, btnAdicionarC, btnListarC, btnConsultarC, btnListarAAuto, btnListarCRegistada, btnListarCNRegistada, btnProx, btnFechar, btnFAnterior);
        toolBar.setAlignment(Pos.BOTTOM_CENTER);
        changeBackgorund(toolBar, Color.DARKCYAN);
        toolBar.setPadding(new Insets(90));
        this.setTop(toolBar);
        toolBar.setSpacing(50);

        btnAdicionarC.setOnAction(actionEvent -> guiAdicionaCand());

        btnListarC.setOnAction(actionEvent -> {

            Comando cmd;
            StringBuilder registo = new StringBuilder();


            cmd = new Comando(TipoComando.LISTAR_CAND);
            maquinaEstados.gerir(cmd);
            for (String str : maquinaEstados.getRegisto())
                registo.append(str).append("\n");

            resultado.setText(registo.toString());



            GridPane gridpane = new GridPane();
            Button button = new Button();
            GridPane.setRowIndex(button, 0);
            GridPane.setColumnIndex(button, 1);
            Label label = new Label();
            GridPane.setConstraints(label, 2, 0);
            gridpane.getChildren().addAll(button, label,resultado);



            Stage listar = new Stage();
            Scene listarS = new Scene(gridpane,800,600);

            listar.setScene(listarS);
            listar.setTitle("Alunos");
            listar.show();
        });

        btnConsultarC.setOnAction(actionEvent -> guiConsultarC());

        btnListarAAuto.setOnAction(actionEvent -> {Comando cmd;
            StringBuilder registo = new StringBuilder();


            cmd = new Comando(TipoComando.ALUNOS_AUTOPROPOSTOS);
            maquinaEstados.gerir(cmd);
            for (String str : maquinaEstados.getRegisto())
                registo.append(str).append("\n");

            resultado.setText(registo.toString());



            GridPane gridpane = new GridPane();
            Button button = new Button();
            GridPane.setRowIndex(button, 0);
            GridPane.setColumnIndex(button, 1);
            Label label = new Label();
            GridPane.setConstraints(label, 2, 0);
            gridpane.getChildren().addAll(button, label,resultado);



            Stage listar = new Stage();
            Scene listarS = new Scene(gridpane,800,600);

            listar.setScene(listarS);
            listar.setTitle("Alunos");
            listar.show();});

        btnListarCRegistada.setOnAction(actionEvent -> {
            Comando cmd;
            StringBuilder registo = new StringBuilder();


            cmd = new Comando(TipoComando.ALUNOS_CAND);
            maquinaEstados.gerir(cmd);
            for (String str : maquinaEstados.getRegisto())
                registo.append(str).append("\n");

            resultado.setText(registo.toString());



            GridPane gridpane = new GridPane();
            Button button = new Button();
            GridPane.setRowIndex(button, 0);
            GridPane.setColumnIndex(button, 1);
            Label label = new Label();
            GridPane.setConstraints(label, 2, 0);
            gridpane.getChildren().addAll(button, label,resultado);



            Stage listar = new Stage();
            Scene listarS = new Scene(gridpane,800,600);

            listar.setScene(listarS);
            listar.setTitle("Alunos");
            listar.show();});

        btnListarCNRegistada.setOnAction(actionEvent -> {Comando cmd;
            StringBuilder registo = new StringBuilder();


            cmd = new Comando(TipoComando.ALUNOS_NATRIBUIDOS);
            maquinaEstados.gerir(cmd);
            for (String str : maquinaEstados.getRegisto())
                registo.append(str).append("\n");

            resultado.setText(registo.toString());



            GridPane gridpane = new GridPane();
            Button button = new Button();
            GridPane.setRowIndex(button, 0);
            GridPane.setColumnIndex(button, 1);
            Label label = new Label();
            GridPane.setConstraints(label, 2, 0);
            gridpane.getChildren().addAll(button, label,resultado);



            Stage listar = new Stage();
            Scene listarS = new Scene(gridpane,800,600);

            listar.setScene(listarS);
            listar.setTitle("Alunos");
            listar.show();});

        btnFechar.setOnAction(actionEvent -> {
            maquinaEstados.fechaFase();
        });
        btnProx.setOnAction(actionEvent -> {
            maquinaEstados.proximaFase();
        });
        btnFAnterior.setOnAction(actionEvent -> {
            maquinaEstados.faseAnterior();
        });
    }

    private void guiAtribuicaoPropostas() {
        this.getChildren().clear();
        Button btnAtribuiAutoA = new MyButton("Atribuição automatica (atribuido)");
        Button btnAtribuiAutoAN = new MyButton("Atribuição automatica (nao atribuido)");
        Button btnAtribuiManual = new MyButton("Atribuiçao Manual");
        Button btnListarAlunos = new MyButton("Listar alunos");
        Label resultado = new Label("");
        Button btnProx = new MyButton("Proxima fase");
        Button btnFechar = new MyButton("Fechar fase");
        Button btnFAnterior = new MyButton("Fase anterior");

        Label lblWc = new Label(("Bem-Vindo a Gestão de Projetos/Estágios do Isec!"));

        lblWc.setScaleX(2);
        lblWc.setScaleY(2);
        lblWc.setTextFill(Color.WHITE);
        lblWc.setFont(Font.font("Verdana", FontWeight.BOLD, 12));


        VBox toolBar = new VBox();
        toolBar.getChildren().addAll(lblWc, btnAtribuiAutoA, btnAtribuiAutoAN, btnAtribuiManual, btnListarAlunos, btnProx, btnFechar, btnFAnterior);
        toolBar.setAlignment(Pos.BOTTOM_CENTER);
        changeBackgorund(toolBar, Color.DARKCYAN);
        toolBar.setPadding(new Insets(90));
        this.setTop(toolBar);
        toolBar.setSpacing(50);

        btnAtribuiAutoAN.setOnAction(actionEvent -> { Comando cmd;
            StringBuilder registo = new StringBuilder();


            cmd = new Comando(TipoComando.ATRIBUI_PROP_NA);
            maquinaEstados.gerir(cmd);
            for (String str : maquinaEstados.getRegisto())
                registo.append(str).append("\n");

            resultado.setText(registo.toString());});

        btnAtribuiManual.setOnAction(actionEvent -> guiAtribuiçaoM());
        btnListarAlunos.setOnAction(actionEvent -> {
            Comando cmd;
            StringBuilder registo = new StringBuilder();


            cmd = new Comando(TipoComando.LISTAR_ALUNOS);
            maquinaEstados.gerir(cmd);
            for (String str : maquinaEstados.getRegisto())
                registo.append(str).append("\n");

            resultado.setText(registo.toString());





            GridPane gridpane = new GridPane();
            Button button = new Button();
            GridPane.setRowIndex(button, 0);
            GridPane.setColumnIndex(button, 1);
            Label label = new Label();
            GridPane.setConstraints(label, 2, 0);
            gridpane.getChildren().addAll(button, label, resultado);



            Stage listar = new Stage();
            Scene listarS = new Scene(gridpane,800,600);

            listar.setScene(listarS);
            listar.setTitle("Alunos");
            listar.show();






        });

        btnFechar.setOnAction(actionEvent -> {
            maquinaEstados.fechaFase();
        });
        btnProx.setOnAction(actionEvent -> {
            maquinaEstados.proximaFase();
        });
        btnFAnterior.setOnAction(actionEvent -> {
            maquinaEstados.faseAnterior();
        });
    }

    private void guiAtribuicaoOrientadores() {
        this.getChildren().clear();
        Button btnAssociacaoA = new MyButton("Atribui Projeto");
        Button btnAtribuiDocente = new MyButton("Atribuição do Docente Manual");
        Button btnListarAlunos = new MyButton("Listar Aluno e docentes");
        Button btnProx = new MyButton("Proxima fase");
        Button btnFechar = new MyButton("Fechar fase");
        Button btnFAnterior = new MyButton("Fase anterior");
        Label resultado = new Label();


        VBox toolBar = new VBox();
        toolBar.getChildren().addAll(btnAssociacaoA, btnAtribuiDocente, btnListarAlunos, btnProx, btnFechar, btnFAnterior);
        toolBar.setAlignment(Pos.BOTTOM_CENTER);
        changeBackgorund(toolBar, Color.DARKCYAN);
        toolBar.setPadding(new Insets(90));
        this.setTop(toolBar);
        toolBar.setSpacing(50);

        btnAssociacaoA.setOnAction(actionEvent -> {
            Comando cmd;
            StringBuilder registo = new StringBuilder();


            cmd = new Comando(TipoComando.ATRIBUI_PROJETO);
            maquinaEstados.gerir(cmd);
            for (String str : maquinaEstados.getRegisto())
                registo.append(str).append("\n");

            resultado.setText(registo.toString());});

        btnAtribuiDocente.setOnAction(actionEvent -> guiAtribuiDoc());

        btnListarAlunos.setOnAction(actionEvent -> {
            Comando cmd;
            StringBuilder registo = new StringBuilder();


            cmd = new Comando(TipoComando.LISTAR_PROP_D);
            maquinaEstados.gerir(cmd);
            for (String str : maquinaEstados.getRegisto())
                registo.append(str).append("\n");

            resultado.setText(registo.toString());





            GridPane gridpane = new GridPane();
            Button button = new Button();
            GridPane.setRowIndex(button, 0);
            GridPane.setColumnIndex(button, 1);
            Label label = new Label();
            GridPane.setConstraints(label, 2, 0);
            gridpane.getChildren().addAll(button, label, resultado);



            Stage listar = new Stage();
            Scene listarS = new Scene(gridpane,800,600);

            listar.setScene(listarS);
            listar.setTitle("Alunos");
            listar.show();






        });

        btnFechar.setOnAction(actionEvent -> {
            maquinaEstados.fechaFase();
        });
        btnProx.setOnAction(actionEvent -> {
            maquinaEstados.proximaFase();
        });
        btnFAnterior.setOnAction(actionEvent -> {
            maquinaEstados.faseAnterior();
        });
    }

    private void guiConsultar() {
        this.getChildren().clear();

        Button btnAlunoA = new MyButton("Aluno Atribuido");
        Button btnAlunoSP = new MyButton("Aluno Sem proposta");
        Button btnPropostasD = new MyButton("Propostas disponiveis");
        Button btnPropostasA = new MyButton("propostas atribuidas");
        Button btnListaDocentes = new MyButton("Lista docentes");
        Button btnGuardaFich = new MyButton("Guardar Ficheiro");
        Button btnRecomecar = new MyButton("Recomeçar");
        Label resultado = new Label();


        VBox toolBar = new VBox();
        toolBar.getChildren().addAll(btnAlunoA, btnAlunoSP, btnPropostasD, btnPropostasA, btnListaDocentes, btnGuardaFich, btnRecomecar);
        toolBar.setAlignment(Pos.BOTTOM_CENTER);
        changeBackgorund(toolBar, Color.DARKCYAN);
        toolBar.setPadding(new Insets(90));
        this.setTop(toolBar);
        toolBar.setSpacing(50);

        btnAlunoA.setOnAction(actionEvent -> {

            Comando cmd;
            StringBuilder registo = new StringBuilder();


            cmd = new Comando(TipoComando.ALUNOS_ATR);
            maquinaEstados.gerir(cmd);
            for (String str : maquinaEstados.getRegisto())
                registo.append(str).append("\n");

            resultado.setText(registo.toString());

            GridPane gridpane = new GridPane();
            Button button = new Button();
            GridPane.setRowIndex(button, 0);
            GridPane.setColumnIndex(button, 1);
            Label label = new Label();
            GridPane.setConstraints(label, 2, 0);
            gridpane.getChildren().addAll(button, label, resultado);



            Stage listar = new Stage();
            Scene listarS = new Scene(gridpane,800,600);

            listar.setScene(listarS);
            listar.setTitle("Alunos Atribuidos");
            listar.show();
        });

        btnAlunoSP.setOnAction(actionEvent -> {

            Comando cmd;
            StringBuilder registo = new StringBuilder();


            cmd = new Comando(TipoComando.ALUNOS_NATR);
            maquinaEstados.gerir(cmd);
            for (String str : maquinaEstados.getRegisto())
                registo.append(str).append("\n");

            resultado.setText(registo.toString());

            GridPane gridpane = new GridPane();
            Button button = new Button();
            GridPane.setRowIndex(button, 0);
            GridPane.setColumnIndex(button, 1);
            Label label = new Label();
            GridPane.setConstraints(label, 2, 0);
            gridpane.getChildren().addAll(button, label, resultado);



            Stage listar = new Stage();
            Scene listarS = new Scene(gridpane,800,600);

            listar.setScene(listarS);
            listar.setTitle("Alunos Atribuidos");
            listar.show();
        });

        btnPropostasD.setOnAction(actionEvent -> {Comando cmd;
            StringBuilder registo = new StringBuilder();


            cmd = new Comando(TipoComando.PROPOSTAS_DIS);
            maquinaEstados.gerir(cmd);
            for (String str : maquinaEstados.getRegisto())
                registo.append(str).append("\n");

            resultado.setText(registo.toString());

            GridPane gridpane = new GridPane();
            Button button = new Button();
            GridPane.setRowIndex(button, 0);
            GridPane.setColumnIndex(button, 1);
            Label label = new Label();
            GridPane.setConstraints(label, 2, 0);
            gridpane.getChildren().addAll(button, label, resultado);



            Stage listar = new Stage();
            Scene listarS = new Scene(gridpane,800,600);

            listar.setScene(listarS);
            listar.setTitle("Alunos Atribuidos");
            listar.show();});

        btnPropostasA.setOnAction(actionEvent -> {Comando cmd;
            StringBuilder registo = new StringBuilder();


            cmd = new Comando(TipoComando.PROPOSTAS_ATR);
            maquinaEstados.gerir(cmd);
            for (String str : maquinaEstados.getRegisto())
                registo.append(str).append("\n");

            resultado.setText(registo.toString());

            GridPane gridpane = new GridPane();
            Button button = new Button();
            GridPane.setRowIndex(button, 0);
            GridPane.setColumnIndex(button, 1);
            Label label = new Label();
            GridPane.setConstraints(label, 2, 0);
            gridpane.getChildren().addAll(button, label, resultado);



            Stage listar = new Stage();
            Scene listarS = new Scene(gridpane,800,600);

            listar.setScene(listarS);
            listar.setTitle("Alunos Atribuidos");
            listar.show();});

        btnListaDocentes.setOnAction(actionEvent -> {Comando cmd;
            StringBuilder registo = new StringBuilder();


            cmd = new Comando(TipoComando.LISTAR_PROPC);
            maquinaEstados.gerir(cmd);
            for (String str : maquinaEstados.getRegisto())
                registo.append(str).append("\n");

            resultado.setText(registo.toString());

            GridPane gridpane = new GridPane();
            Button button = new Button();
            GridPane.setRowIndex(button, 0);
            GridPane.setColumnIndex(button, 1);
            Label label = new Label();
            GridPane.setConstraints(label, 2, 0);
            gridpane.getChildren().addAll(button, label, resultado);



            Stage listar = new Stage();
            Scene listarS = new Scene(gridpane,800,600);

            listar.setScene(listarS);
            listar.setTitle("Alunos Atribuidos");
            listar.show();});

        btnGuardaFich.setOnAction(actionEvent -> guiGuardarFich());

        btnRecomecar.setOnAction(actionEvent -> guiConfiguracao());


    }

    public void guiCarregarCsv() {
        this.getChildren().clear();

        Label nome = new Label("Numero do ficheiro: ");
        TextField textNome = new TextField();
        Button submit = new MyButton("Consultar");
        Label resultado = new Label("");
        Button voltar = new MyButton("Voltar");

        VBox vBox = new VBox();
        vBox.getChildren().addAll(nome, textNome,submit, voltar,  resultado);
        this.setCenter(vBox);
        vBox.setAlignment(Pos.CENTER);
        vBox.setMinWidth(600);
        vBox.setSpacing(10);
        vBox.setPadding(new Insets(20));

        submit.setOnAction(actionEvent -> {
            String input = textNome.getText();
            Comando cmd;
            StringBuilder registo = new StringBuilder();


            cmd = new Comando(TipoComando.LER_CSV_ALUNOS, input);
            maquinaEstados.gerir(cmd);
            for (String str : maquinaEstados.getRegisto())
                registo.append(str).append("\n");

            resultado.setText(registo.toString());


        });



        voltar.setOnAction(actionEvent -> guiGestaoAlunos());

    }
    public void guiCarregarCsvDoc() {
        this.getChildren().clear();

        Label nome = new Label("Numero do ficheiro: ");
        TextField textNome = new TextField();
        Button submit = new MyButton("Consultar");
        Label resultado = new Label("");
        Button voltar = new MyButton("Voltar");

        VBox vBox = new VBox();
        vBox.getChildren().addAll(nome, textNome,submit, voltar,  resultado);
        this.setCenter(vBox);
        vBox.setAlignment(Pos.CENTER);
        vBox.setMinWidth(600);
        vBox.setSpacing(10);
        vBox.setPadding(new Insets(20));

        submit.setOnAction(actionEvent -> {
            String input = textNome.getText();
            Comando cmd;
            StringBuilder registo = new StringBuilder();


            cmd = new Comando(TipoComando.LER_CSV_DOCS, input);
            maquinaEstados.gerir(cmd);
            for (String str : maquinaEstados.getRegisto())
                registo.append(str).append("\n");

            resultado.setText(registo.toString());


        });



        voltar.setOnAction(actionEvent -> guiGestaoAlunos());

    }
    public void guiCarregarCsvEstagio() {
        this.getChildren().clear();

        Label nome = new Label("Numero do ficheiro: ");
        TextField textNome = new TextField();
        Button submit = new MyButton("Consultar");
        Label resultado = new Label("");
        Button voltar = new MyButton("Voltar");

        VBox vBox = new VBox();
        vBox.getChildren().addAll(nome, textNome,submit, voltar,  resultado);
        this.setCenter(vBox);
        vBox.setAlignment(Pos.CENTER);
        vBox.setMinWidth(600);
        vBox.setSpacing(10);
        vBox.setPadding(new Insets(20));

        submit.setOnAction(actionEvent -> {
            String input = textNome.getText();
            Comando cmd;
            StringBuilder registo = new StringBuilder();


            cmd = new Comando(TipoComando.LER_CSV_ESTAGIO, input);
            maquinaEstados.gerir(cmd);
            for (String str : maquinaEstados.getRegisto())
                registo.append(str).append("\n");

            resultado.setText(registo.toString());


        });



        voltar.setOnAction(actionEvent -> guiGestaoAlunos());

    }
    public void guiCarregarCsvProjeto() {
        this.getChildren().clear();

        Label nome = new Label("Numero do ficheiro: ");
        TextField textNome = new TextField();
        Button submit = new MyButton("Consultar");
        Label resultado = new Label("");
        Button voltar = new MyButton("Voltar");

        VBox vBox = new VBox();
        vBox.getChildren().addAll(nome, textNome,submit, voltar,  resultado);
        this.setCenter(vBox);
        vBox.setAlignment(Pos.CENTER);
        vBox.setMinWidth(600);
        vBox.setSpacing(10);
        vBox.setPadding(new Insets(20));

        submit.setOnAction(actionEvent -> {
            String input = textNome.getText();
            Comando cmd;
            StringBuilder registo = new StringBuilder();


            cmd = new Comando(TipoComando.LER_CSV_PROJETO, input);
            maquinaEstados.gerir(cmd);
            for (String str : maquinaEstados.getRegisto())
                registo.append(str).append("\n");

            resultado.setText(registo.toString());


        });



        voltar.setOnAction(actionEvent -> guiGestaoAlunos());

    }


    public void guiGuardarFich(){
        this.getChildren().clear();

        Label nome = new Label("Numero do ficheiro: ");
        TextField textNome = new TextField();
        Button submit = new MyButton("Consultar");
        Label resultado = new Label("");
        Button voltar = new MyButton("Voltar");

        VBox vBox = new VBox();
        vBox.getChildren().addAll(nome, textNome,submit, voltar,  resultado);
        this.setCenter(vBox);
        vBox.setAlignment(Pos.CENTER);
        vBox.setMinWidth(600);
        vBox.setSpacing(10);
        vBox.setPadding(new Insets(20));

        submit.setOnAction(actionEvent -> {
            String input = textNome.getText();
            Comando cmd;
            StringBuilder registo = new StringBuilder();


            cmd = new Comando(TipoComando.GUARDAR, input);
            maquinaEstados.gerir(cmd);
            for (String str : maquinaEstados.getRegisto())
                registo.append(str).append("\n");

            resultado.setText(registo.toString());


        });



        voltar.setOnAction(actionEvent -> guiGestaoAlunos());
    }

    public void guiInserirAluno() {

        this.getChildren().clear();

        Label nome = new Label("Nome");
        TextField textNome = new TextField();
        Label email = new Label("Email");
        TextField textEmail = new TextField();
        Label nrEstudante = new Label("Numero de Estudante");
        TextField textNrEstudante = new TextField();
        Label siglaC = new Label("Sigla de Curso");
        TextField textSiglaC = new TextField();
        Label siglaR = new Label("Sigla de Ramo");
        TextField textSiglaR = new TextField();
        Label classificacao = new Label("Classificação");
        TextField textClass = new TextField();
        Label aceder = new Label("Pode aceder a projeto/estágio ?");
        TextField textAceder = new TextField();


       // Text alunos = new Text(String.valueOf(gestor.getTamanhoAlunos()));

        Button submit = new MyButton("Submit");
        Button voltar = new MyButton("Voltar");



        VBox vBox = new VBox();
        vBox.getChildren().addAll(nome,textNome,email,textEmail,nrEstudante,textNrEstudante,siglaC,textSiglaC,siglaR,textSiglaR, classificacao,textClass, aceder,textAceder, submit, voltar );
        this.setCenter(vBox);
        vBox.setAlignment(Pos.CENTER);
        vBox.setMinWidth(600);
        vBox.setSpacing(10);
        vBox.setPadding(new Insets(20));

        //long nrE, String nome, String email, String siglaC, String siglaR, double classificacao, boolean aceder

        submit.setOnAction(actionEvent -> {
            String [] input = new String[7];
            input[0] = textNrEstudante.getText();
            input[1] = textNome.getText();
            input[2] = textEmail.getText();
            input[3] = textSiglaC.getText();
            input[4] = textSiglaR.getText();
            input[5] = textClass.getText();
            input[6] = textAceder.getText();
            Comando cmd;
            StringBuilder registo = new StringBuilder();
            cmd = new Comando(TipoComando.INSERIR_ALUNO, input);
            maquinaEstados.gerir(cmd);
            for (String str : maquinaEstados.getRegisto())
                registo.append(str).append("\n");


        });
        voltar.setOnAction(actionEvent -> guiGestaoAlunos());



    }
    public void guiConsultarAluno() {

        this.getChildren().clear();

        Label nome = new Label("Numero do aluno: ");
        TextField textNome = new TextField();
        Button submit = new MyButton("Consultar");
        Label resultado = new Label("");
        Button voltar = new MyButton("Voltar");

        VBox vBox = new VBox();
        vBox.getChildren().addAll(nome, textNome,submit, voltar,  resultado);
        this.setCenter(vBox);
        vBox.setAlignment(Pos.CENTER);
        vBox.setMinWidth(600);
        vBox.setSpacing(10);
        vBox.setPadding(new Insets(20));

        submit.setOnAction(actionEvent -> {
            String input = textNome.getText();
            Comando cmd;
            StringBuilder registo = new StringBuilder();


            cmd = new Comando(TipoComando.CONSULTAR_ALUNO, input);
            maquinaEstados.gerir(cmd);
            for (String str : maquinaEstados.getRegisto())
                registo.append(str).append("\n");

            resultado.setText(registo.toString());



            GridPane gridpane = new GridPane();
            Button button = new Button();
            GridPane.setRowIndex(button, 0);
            GridPane.setColumnIndex(button, 1);
            Label label = new Label();
            GridPane.setConstraints(label, 2, 0);
            gridpane.getChildren().addAll(button, label,resultado);



            Stage listar = new Stage();
            Scene listarS = new Scene(gridpane,800,600);

            listar.setScene(listarS);
            listar.setTitle("Alunos");
            listar.show();






        });



        voltar.setOnAction(actionEvent -> guiGestaoAlunos());


    }
    public void guiEditarAluno() {
        this.getChildren().clear();

        Label nrEstudante = new Label("Número de Estudante");
        TextField textNrEstudante = new TextField();

        Button submit = new Button("Submit");

        VBox vBox = new VBox();
        vBox.getChildren().addAll(nrEstudante,textNrEstudante, submit);
        this.setCenter(vBox);
        vBox.setAlignment(Pos.CENTER);
        vBox.setMinWidth(600);
        vBox.setSpacing(10);
        vBox.setPadding(new Insets(20));

        submit.setOnAction(actionEvent -> guiEditarAlunoE(textNrEstudante.getText()));


    }
    public void guiEditarAlunoE(String text) {
        this.getChildren().clear();

        Label nome = new Label("Nome");
        TextField textNome = new TextField();
        Label email = new Label("Email");
        TextField textEmail = new TextField();
        Label nrEstudante = new Label("Numero de Estudante");
        TextField textNrEstudante = new TextField();
        Label siglaC = new Label("Sigla de Curso");
        TextField textSiglaC = new TextField();
        Label siglaR = new Label("Sigla de Ramo");
        TextField textSiglaR = new TextField();
        Label classificacao = new Label("Classificação");
        TextField textClass = new TextField();
        Label aceder = new Label("Pode aceder a projeto/estágio ?");
        TextField textAceder = new TextField();

        Button submit = new MyButton("Submit");
        Button voltar = new MyButton("Voltar");



        VBox vBox = new VBox();
        vBox.getChildren().addAll(nome,textNome,email,textEmail,nrEstudante,textNrEstudante,siglaC,textSiglaC,siglaR,textSiglaR, classificacao,textClass, aceder,textAceder, submit, voltar);
        this.setCenter(vBox);
        vBox.setAlignment(Pos.CENTER);
        vBox.setMinWidth(600);
        vBox.setSpacing(10);
        vBox.setPadding(new Insets(20));

        //long nrE, String nome, String email, String siglaC, String siglaR, double classificacao, boolean aceder

        submit.setOnAction(actionEvent -> {
            String [] input = new String[8];
            input[0] = text;
            input[1] = textNrEstudante.getText();
            input[2] = textNome.getText();
            input[3] = textEmail.getText();
            input[4] = textSiglaC.getText();
            input[5] = textSiglaR.getText();
            input[6] = textClass.getText();
            input[7] = textAceder.getText();
            Comando cmd;
            StringBuilder registo = new StringBuilder();
            cmd = new Comando(TipoComando.EDITAR_ALUNO, input);
            maquinaEstados.gerir(cmd);
            for (String str : maquinaEstados.getRegisto())
                registo.append(str).append("\n");


        });
        voltar.setOnAction(actionEvent -> guiGestaoAlunos());


    }
    public void guiEliminarAluno() {

        this.getChildren().clear();
        Label nome = new Label("Numero do Aluno: ");
        TextField textNome = new TextField();
        Button submit = new MyButton("Eliminar");
        Button voltar = new MyButton("Voltar");

        VBox vBox = new VBox();
        vBox.getChildren().addAll(nome,textNome, submit, voltar);
        this.setCenter(vBox);
        vBox.setAlignment(Pos.CENTER);
        vBox.setMinWidth(600);
        vBox.setSpacing(10);
        vBox.setPadding(new Insets(20));

        submit.setOnAction(actionEvent -> {
            String input = textNome.getText();
            Comando cmd;
            StringBuilder registo = new StringBuilder();


            cmd = new Comando(TipoComando.ELIMINA_ALUNO, input);
            maquinaEstados.gerir(cmd);
            for (String str : maquinaEstados.getRegisto())
                registo.append(str).append("\n");

        });


        voltar.setOnAction(actionEvent -> guiGestaoAlunos());
    }



    public void guiGerirEstagio() {
        this.getChildren().clear();

        Button inserirButton = new MyButton("Inserir Estagio");
        Button consultarButton = new MyButton("Consultar Estagio");
        Button editarButton = new MyButton("Editar Estagio");
        Button eliminarButton = new MyButton("Eliminar Estagio");
        Button csv = new MyButton("cvs");
        Button voltarButton = new MyButton("Voltar");
        Label label = new Label("Programação Avançada 2021/2022");

        VBox toolBar = new VBox();
        toolBar.getChildren().addAll(inserirButton, consultarButton, editarButton, eliminarButton, csv,voltarButton, label);
        toolBar.setAlignment(Pos.BOTTOM_CENTER);
        changeBackgorund(toolBar, Color.DARKCYAN);
        toolBar.setPadding(new Insets(100));
        this.setTop(toolBar);
        toolBar.setSpacing(50);

        inserirButton.setOnAction(actionEvent -> guiInserirEstagio());
        consultarButton.setOnAction(actionEvent -> guiConsultarEstagio());
        editarButton.setOnAction(actionEvent -> guiEditarEstagio());
        eliminarButton.setOnAction(actionEvent -> guiEliminarEstagio());
        csv.setOnAction(actionEvent -> guiCarregarCsvEstagio());
        voltarButton.setOnAction(actionEvent -> maquinaEstados.voltar());

    }

    public void guiInserirEstagio(){
        this.getChildren().clear();

        Label nome = new Label("Codigo de Identificaçao");
        TextField textNome = new TextField();
        Label email = new Label("Titulo");
        TextField textEmail = new TextField();
        Label nrEstudante = new Label("Area");
        TextField textNrEstudante = new TextField();
        Label siglaC = new Label("entidade");
        TextField textSiglaC = new TextField();
        Label siglaR = new Label("Numero do Aluno (opcional)");
        TextField textSiglaR = new TextField();


        Button submit = new MyButton("Submit");
        Button voltar = new MyButton("Voltar");



        VBox vBox = new VBox();
        vBox.getChildren().addAll(nome, textNome,email,textEmail,nrEstudante,textNrEstudante,siglaC,textSiglaC,siglaR,textSiglaR,submit, voltar);
        this.setCenter(vBox);
        vBox.setAlignment(Pos.CENTER);
        vBox.setMinWidth(600);
        vBox.setSpacing(10);
        vBox.setPadding(new Insets(20));

        //long nrE, String nome, String email, String siglaC, String siglaR, double classificacao, boolean aceder

        submit.setOnAction(actionEvent -> {
            String [] input = new String[5];
            input[0] = textNome.getText();
            input[1] = textEmail.getText();
            input[2] = textNrEstudante.getText();
            input[3] = textSiglaC.getText();
            input[4] = textSiglaR.getText();

            Comando cmd;
            StringBuilder registo = new StringBuilder();
            cmd = new Comando(TipoComando.INSERIR_PROP_E, input);
            maquinaEstados.gerir(cmd);
            for (String str : maquinaEstados.getRegisto())
                registo.append(str).append("\n");


        });
        voltar.setOnAction(actionEvent -> guiGerirEstagio());

    }
    public void guiConsultarEstagio(){
        this.getChildren().clear();

        Label nome = new Label("Codigo de Idenificaçao do Estagio");
        TextField textNome = new TextField();
        Button submit = new MyButton("Consultar");
        Label resultado = new Label("");
        Button voltar = new MyButton("Voltar");

        VBox vBox = new VBox();
        vBox.getChildren().addAll(nome, textNome,submit, voltar, resultado);
        this.setCenter(vBox);
        vBox.setAlignment(Pos.CENTER);
        vBox.setMinWidth(600);
        vBox.setSpacing(10);
        vBox.setPadding(new Insets(20));

        submit.setOnAction(actionEvent -> {
            String input = textNome.getText();
            Comando cmd;
            StringBuilder registo = new StringBuilder();


            cmd = new Comando(TipoComando.CONSULTAR_PROP, input);
            maquinaEstados.gerir(cmd);
            for (String str : maquinaEstados.getRegisto())
                registo.append(str).append("\n");

            resultado.setText(registo.toString());



            GridPane gridpane = new GridPane();
            Button button = new Button();
            GridPane.setRowIndex(button, 0);
            GridPane.setColumnIndex(button, 1);
            Label label = new Label();
            GridPane.setConstraints(label, 2, 0);
            gridpane.getChildren().addAll(button, label,resultado);



            Stage listar = new Stage();
            Scene listarS = new Scene(gridpane,800,600);

            listar.setScene(listarS);
            listar.setTitle("Alunos");
            listar.show();







        });



        voltar.setOnAction(actionEvent -> guiGerirEstagio());



    }
    public void guiEditarEstagio(){}
    public void guiEliminarEstagio(){
        this.getChildren().clear();
        Label nome = new Label("Codigo de Identificaçao ");
        TextField textNome = new TextField();
        Button submit = new MyButton("Eliminar");
        Button voltar = new MyButton("Voltar");

        VBox vBox = new VBox();
        vBox.getChildren().addAll(nome,textNome, submit, voltar);
        this.setCenter(vBox);
        vBox.setAlignment(Pos.CENTER);
        vBox.setMinWidth(600);
        vBox.setSpacing(10);
        vBox.setPadding(new Insets(20));

        submit.setOnAction(actionEvent -> {
            String input = textNome.getText();
            Comando cmd;
            StringBuilder registo = new StringBuilder();


            cmd = new Comando(TipoComando.ELIMINA_PROP, input);
            maquinaEstados.gerir(cmd);
            for (String str : maquinaEstados.getRegisto())
                registo.append(str).append("\n");

        });


        voltar.setOnAction(actionEvent -> guiGerirEstagio());
    }

    public void guiGerirProjeto() {

        this.getChildren().clear();
        Button inserirButton = new MyButton("Inserir Projeto");
        Button consultarButton = new MyButton("Consultar Projeto");
        Button editarButton = new MyButton("Editar Projeto");
        Button eliminarButton = new MyButton("Eliminar Projeto");
        Button csv = new MyButton("cvs");
        Button voltarButton = new MyButton("Voltar");
        Label label = new Label("Programação Avançada 2021/2022");

        VBox toolBar = new VBox();
        toolBar.getChildren().addAll(inserirButton, consultarButton, editarButton, eliminarButton, csv,voltarButton, label);
        toolBar.setAlignment(Pos.BOTTOM_CENTER);
        changeBackgorund(toolBar, Color.DARKCYAN);
        toolBar.setPadding(new Insets(100));
        this.setTop(toolBar);
        toolBar.setSpacing(50);


        inserirButton.setOnAction(actionEvent -> guiInserirProjeto());
        consultarButton.setOnAction(actionEvent -> guiConsultarProjeto());
        editarButton.setOnAction(actionEvent -> guiEditarProjeto());
        eliminarButton.setOnAction(actionEvent -> guiEliminarProjeto());
        csv.setOnAction(actionEvent -> guiCarregarCsvProjeto());
        voltarButton.setOnAction(actionEvent -> maquinaEstados.voltar());

    }

    public void guiInserirProjeto() {
        this.getChildren().clear();

        Label codI = new Label("Codigo de identificação");
        TextField textCodI = new TextField();
        Label titulo = new Label("Titulo");
        TextField textTitulo = new TextField();
        Label Ramo = new Label("Ramo");
        TextField textRamo = new TextField();
        Label emailPro = new Label("Email Associado");
        TextField textEmailPro = new TextField();
        Label nrEstudante = new Label("Número Estudante");
        TextField textEstudante = new TextField();

        Button submit = new MyButton("Submit");
        Button voltar = new MyButton("Voltar");

        VBox vBox = new VBox();
        vBox.getChildren().addAll(codI,textCodI,titulo,textTitulo,Ramo,textRamo,emailPro,textEmailPro,nrEstudante,textEstudante, submit, voltar);
        this.setCenter(vBox);
        vBox.setAlignment(Pos.CENTER);
        vBox.setMinWidth(600);
        vBox.setSpacing(10);
        vBox.setPadding(new Insets(20));

        //long nrE, String nome, String email, String siglaC, String siglaR, double classificacao, boolean aceder

        submit.setOnAction(actionEvent -> {
            String [] input = new String[7];
            input[0] = textCodI.getText();
            input[1] = textTitulo.getText();
            input[2] = textRamo.getText();
            input[3] = textEmailPro.getText();
            input[4] = textEstudante.getText();
            Comando cmd;
            StringBuilder registo = new StringBuilder();
            cmd = new Comando(TipoComando.INSERIR_PROP_P, input);
            maquinaEstados.gerir(cmd);
            for (String str : maquinaEstados.getRegisto())
                registo.append(str).append("\n");


        });
        voltar.setOnAction(actionEvent -> guiGestaoAlunos());



    }
    public void guiConsultarProjeto() {
        this.getChildren().clear();

        Label nome = new Label("Codigo de Idenificaçao do Projeto");
        TextField textNome = new TextField();
        Button submit = new MyButton("Consultar");
        Label resultado = new Label("");
        Button voltar = new MyButton("Voltar");

        VBox vBox = new VBox();
        vBox.getChildren().addAll(nome, textNome,submit, voltar,  resultado);
        this.setCenter(vBox);
        vBox.setAlignment(Pos.CENTER);
        vBox.setMinWidth(600);
        vBox.setSpacing(10);
        vBox.setPadding(new Insets(20));

        submit.setOnAction(actionEvent -> {
            String input = textNome.getText();
            Comando cmd;
            StringBuilder registo = new StringBuilder();


            cmd = new Comando(TipoComando.CONSULTAR_PROP_P, input);
            maquinaEstados.gerir(cmd);
            for (String str : maquinaEstados.getRegisto())
                registo.append(str).append("\n");

            resultado.setText(registo.toString());



            GridPane gridpane = new GridPane();
            Button button = new Button();
            GridPane.setRowIndex(button, 0);
            GridPane.setColumnIndex(button, 1);
            Label label = new Label();
            GridPane.setConstraints(label, 2, 0);
            gridpane.getChildren().addAll(button, label,resultado);



            Stage listar = new Stage();
            Scene listarS = new Scene(gridpane,800,600);

            listar.setScene(listarS);
            listar.setTitle("Projeto");
            listar.show();






        });



        voltar.setOnAction(actionEvent -> guiGerirEstagio());

    }
    public void guiEditarProjeto() {}
    public void guiEliminarProjeto() {
        this.getChildren().clear();
        Label nome = new Label("Codigo de Identificaçao ");
        TextField textNome = new TextField();
        Button submit = new MyButton("Eliminar");
        Button voltar = new MyButton("Voltar");

        VBox vBox = new VBox();
        vBox.getChildren().addAll(nome,textNome, submit, voltar);
        this.setCenter(vBox);
        vBox.setAlignment(Pos.CENTER);
        vBox.setMinWidth(600);
        vBox.setSpacing(10);
        vBox.setPadding(new Insets(20));

        submit.setOnAction(actionEvent -> {
            String input = textNome.getText();
            Comando cmd;
            StringBuilder registo = new StringBuilder();


            cmd = new Comando(TipoComando.ELIMINA_PROP, input);
            maquinaEstados.gerir(cmd);
            for (String str : maquinaEstados.getRegisto())
                registo.append(str).append("\n");

        });


        voltar.setOnAction(actionEvent -> guiGerirEstagio());
    }



    public void guiInserirDoc(){
        this.getChildren().clear();

        Label nome = new Label("Nome do Docente");
        TextField textNome = new TextField();
        Label email = new Label("Email");
        TextField textEmail = new TextField();
        Button submit = new MyButton("Submit");
        Button voltar = new MyButton("Voltar");



        VBox vBox = new VBox();
        vBox.getChildren().addAll(nome,textNome,email,textEmail,submit,voltar);
        this.setCenter(vBox);
        vBox.setAlignment(Pos.CENTER);
        vBox.setMinWidth(600);
        vBox.setSpacing(10);
        vBox.setPadding(new Insets(20));

        submit.setOnAction(actionEvent -> {
            String [] input = new String[2];
        input[0] = textNome.getText();
        input[1] = textEmail.getText();

        Comando cmd;
        StringBuilder registo = new StringBuilder();
        cmd = new Comando(TipoComando.INSERIR_DOC, input);
        maquinaEstados.gerir(cmd);
        for (String str : maquinaEstados.getRegisto())
            registo.append(str).append("\n");
        });
        voltar.setOnAction(actionEvent -> guiGestaoDocentes());


    }
    public void guiConsultarDoc(){
        this.getChildren().clear();

        Label nome = new Label("Email do Docente: ");
        TextField textNome = new TextField();
        Button submit = new MyButton("Consutar");
        Label resultado = new Label("");
        Button voltar = new MyButton("Voltar");

        VBox vBox = new VBox();
        vBox.getChildren().addAll(nome, textNome,submit, voltar, resultado);
        this.setCenter(vBox);
        vBox.setAlignment(Pos.CENTER);
        vBox.setMinWidth(600);
        vBox.setSpacing(10);
        vBox.setPadding(new Insets(20));

        submit.setOnAction(actionEvent -> {
            String input = textNome.getText();
            Comando cmd;
            StringBuilder registo = new StringBuilder();


            cmd = new Comando(TipoComando.CONSULTAR_DOC, input);
            maquinaEstados.gerir(cmd);
            for (String str : maquinaEstados.getRegisto())
                registo.append(str).append("\n");

            resultado.setText(registo.toString());

            GridPane gridpane = new GridPane();
            Button button = new Button();
            GridPane.setRowIndex(button, 0);
            GridPane.setColumnIndex(button, 1);
            Label label = new Label();
            GridPane.setConstraints(label, 2, 0);
            gridpane.getChildren().addAll(button, label,resultado);



            Stage listar = new Stage();
            Scene listarS = new Scene(gridpane,800,600);

            listar.setScene(listarS);
            listar.setTitle("Docentes");
            listar.show();
        });



        voltar.setOnAction(actionEvent -> guiGestaoDocentes());
    }
    public void guiEditarDoc(){
        this.getChildren().clear();

        Label nrEstudante = new Label("Email do docente");
        TextField textNrEstudante = new TextField();

        Button submit = new Button("Submit");

        VBox vBox = new VBox();
        vBox.getChildren().addAll(nrEstudante,textNrEstudante, submit);
        this.setCenter(vBox);
        vBox.setAlignment(Pos.CENTER);
        vBox.setMinWidth(600);
        vBox.setSpacing(10);
        vBox.setPadding(new Insets(20));

        submit.setOnAction(actionEvent -> guiEditarAlunoEE(textNrEstudante.getText()));

    }

    public void guiEditarAlunoEE(String email){
        this.getChildren().clear();

        Label nome = new Label("Nome");
        TextField textNome = new TextField();
        Label email1 = new Label("Email");
        TextField textEmail = new TextField();


        Button submit = new MyButton("Submit");
        Button voltar = new MyButton("Voltar");



        VBox vBox = new VBox();
        vBox.getChildren().addAll(nome,textNome,email1,textEmail, submit, voltar);
        this.setCenter(vBox);
        vBox.setAlignment(Pos.CENTER);
        vBox.setMinWidth(600);
        vBox.setSpacing(10);
        vBox.setPadding(new Insets(20));

        //long nrE, String nome, String email, String siglaC, String siglaR, double classificacao, boolean aceder

        submit.setOnAction(actionEvent -> {
            String [] input = new String[3];
            input[0] = email;
            input[1] = textEmail.getText();
            input[2] = textNome.getText();

            Comando cmd;
            StringBuilder registo = new StringBuilder();
            cmd = new Comando(TipoComando.EDITAR_DOC, input);
            maquinaEstados.gerir(cmd);
            for (String str : maquinaEstados.getRegisto())
                registo.append(str).append("\n");


        });
        voltar.setOnAction(actionEvent -> guiGestaoAlunos());


    }
    public void guiEliminarDoc(){
        this.getChildren().clear();

        Label nome = new Label("Email do Docente ");
        TextField textNome = new TextField();
        Button submit = new MyButton("Eliminar");
        Button voltar = new MyButton("Voltar");

        VBox vBox = new VBox();
        vBox.getChildren().addAll(nome,textNome, submit, voltar);
        this.setCenter(vBox);
        vBox.setAlignment(Pos.CENTER);
        vBox.setMinWidth(600);
        vBox.setSpacing(10);
        vBox.setPadding(new Insets(20));

        submit.setOnAction(actionEvent -> {
            String input = textNome.getText();
            Comando cmd;
            StringBuilder registo = new StringBuilder();


            cmd = new Comando(TipoComando.ELIMINA_DOC, input);
            maquinaEstados.gerir(cmd);
            for (String str : maquinaEstados.getRegisto())
                registo.append(str).append("\n");

        });

        voltar.setOnAction(actionEvent -> guiGestaoDocentes());
    }

    public void guiAdicionaCand(){
        this.getChildren().clear();

        Label nome = new Label("Codigo da Proposta a adicionar");
        TextField textNome = new TextField();

        Button submit = new MyButton("Submit");
        Button voltar = new MyButton("Voltar");



        VBox vBox = new VBox();
        vBox.getChildren().addAll(nome,textNome,submit,voltar);
        this.setCenter(vBox);
        vBox.setAlignment(Pos.CENTER);
        vBox.setMinWidth(600);
        vBox.setSpacing(10);
        vBox.setPadding(new Insets(20));

        submit.setOnAction(actionEvent -> {
            String input;
            input = textNome.getText();


            Comando cmd;
            StringBuilder registo = new StringBuilder();
            cmd = new Comando(TipoComando.INSERIR_CAND, input);
            maquinaEstados.gerir(cmd);
            for (String str : maquinaEstados.getRegisto())
                registo.append(str).append("\n");
        });
        voltar.setOnAction(actionEvent -> guiOpcoesCandidatura());

    }
    public void guiConsultarC() {
        this.getChildren().clear();

        Label nome = new Label("Codigo de Identificaçao da Candidatira ");
        TextField textNome = new TextField();
        Button submit = new MyButton("Consultar");
        Label resultado = new Label("");
        Button voltar = new MyButton("Voltar");


        VBox vBox = new VBox();
        vBox.getChildren().addAll(nome, textNome,submit, voltar,  resultado);
        this.setCenter(vBox);
        vBox.setAlignment(Pos.CENTER);
        vBox.setMinWidth(600);
        vBox.setSpacing(10);
        vBox.setPadding(new Insets(20));

        submit.setOnAction(actionEvent -> {
            String input = textNome.getText();
            Comando cmd;
            StringBuilder registo = new StringBuilder();


            cmd = new Comando(TipoComando.CONSULTAR_CAND, input);
            maquinaEstados.gerir(cmd);
            for (String str : maquinaEstados.getRegisto())
                registo.append(str).append("\n");

            resultado.setText(registo.toString());



            GridPane gridpane = new GridPane();
            Button button = new Button();
            GridPane.setRowIndex(button, 0);
            GridPane.setColumnIndex(button, 1);
            Label label = new Label();
            GridPane.setConstraints(label, 2, 0);
            gridpane.getChildren().addAll(button, label,resultado);



            Stage listar = new Stage();
            Scene listarS = new Scene(gridpane,800,600);

            listar.setScene(listarS);
            listar.setTitle("Candidatura");
            listar.show();






        });



        voltar.setOnAction(actionEvent -> guiOpcoesCandidatura());


    }

    public void guiAtribuiçaoM(){
        this.getChildren().clear();
        Label proposta = new Label("Insira uma Proposta Disponível");
        TextField textProposta = new TextField();

        Label nrEstudante = new Label("Insira o Numero de Estudante");
        TextField textNrEstudante = new TextField();

        Button submit = new MyButton("Submit");
        Button voltar = new MyButton("Voltar");

        VBox vBox = new VBox();
        vBox.getChildren().addAll(nrEstudante,textNrEstudante,proposta,textProposta,submit);
        this.setCenter(vBox);
        vBox.setAlignment(Pos.CENTER);
        vBox.setMinWidth(600);
        vBox.setSpacing(10);
        vBox.setPadding(new Insets(20));

        submit.setOnAction(actionEvent -> {
            String [] input = new String[2];
            input[0] = textNrEstudante.getText();
            input[1] = textProposta.getText();
            Comando cmd;
            StringBuilder registo = new StringBuilder();


            cmd = new Comando(TipoComando.ATRIBUI_MANUAL, input);
            maquinaEstados.gerir(cmd);
            for (String str : maquinaEstados.getRegisto())
                registo.append(str).append("\n");

        });

        voltar.setOnAction(actionEvent -> guiAtribuicaoPropostas());
    }

    public void guiAtribuiDoc(){
        this.getChildren().clear();
        Label emailD = new Label("Insira o email do Docente");
        TextField textProposta = new TextField();

        Label codI = new Label("Insira o Codigo da proposta");
        TextField textNrEstudante = new TextField();

        Button submit = new MyButton("Submit");
        Button voltar = new MyButton("Voltar");

        VBox vBox = new VBox();
        vBox.getChildren().addAll(emailD,textProposta,codI,textNrEstudante,submit);
        this.setCenter(vBox);
        vBox.setAlignment(Pos.CENTER);
        vBox.setMinWidth(600);
        vBox.setSpacing(10);
        vBox.setPadding(new Insets(20));

        submit.setOnAction(actionEvent -> {
            String [] input = new String[2];
            input[0] = textProposta.getText();
            input[1] = textNrEstudante.getText();
            Comando cmd;
            StringBuilder registo = new StringBuilder();


            cmd = new Comando(TipoComando.ATRIBUI_DOCENTE_M, input);
            maquinaEstados.gerir(cmd);
            for (String str : maquinaEstados.getRegisto())
                registo.append(str).append("\n");

        });

        voltar.setOnAction(actionEvent -> guiAtribuicaoOrientadores());

    }


}
