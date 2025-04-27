import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class ListaDeTarefas extends Application {

    private List<Tarefa> tarefas = new ArrayList<>();
    private ListView<String> listaTarefas = new ListView<>();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        // Layout Principal
        BorderPane layoutPrincipal = new BorderPane();

        // Campo para nova tarefa
        TextField campoTarefa = new TextField();
        campoTarefa.setPromptText("Digite sua tarefa...");
        Button botaoAdicionar = new Button("Adicionar Tarefa");

        // Adiciona tarefas à lista
        botaoAdicionar.setOnAction(e -> {
            String texto = campoTarefa.getText();
            if (!texto.isEmpty()) {
                Tarefa novaTarefa = new Tarefa(texto);
                tarefas.add(novaTarefa);
                atualizarLista();
                campoTarefa.clear();
            } else {
                mostrarAlerta("Campo vazio!", "Por favor, insira uma tarefa.");
            }
        });

        // Botão para marcar como concluído
        Button botaoConcluir = new Button("Marcar como Concluído");
        botaoConcluir.setOnAction(e -> {
            int selecionada = listaTarefas.getSelectionModel().getSelectedIndex();
            if (selecionada >= 0) {
                Tarefa tarefa = tarefas.get(selecionada);
                tarefa.setConcluida(true);
                atualizarLista();
            } else {
                mostrarAlerta("Nenhuma tarefa selecionada!", "Por favor, selecione uma tarefa.");
            }
        });

        // Botão para remover tarefa
        Button botaoRemover = new Button("Remover Tarefa");
        botaoRemover.setOnAction(e -> {
            int selecionada = listaTarefas.getSelectionModel().getSelectedIndex();
            if (selecionada >= 0) {
                tarefas.remove(selecionada);
                atualizarLista();
            } else {
                mostrarAlerta("Nenhuma tarefa selecionada!", "Por favor, selecione uma tarefa.");
            }
        });

        // Layout para botões
        HBox layoutBotoes = new HBox(10, botaoAdicionar, botaoConcluir, botaoRemover);

        // Layout central
        VBox layoutCentral = new VBox(10, campoTarefa, listaTarefas);
        layoutCentral.setSpacing(10);

        // Configuração do layout principal
        layoutPrincipal.setTop(layoutCentral);
        layoutPrincipal.setBottom(layoutBotoes);

        // Cena principal
        Scene cena = new Scene(layoutPrincipal, 400, 300);
        stage.setTitle("Lista de Tarefas");
        stage.setScene(cena);
        stage.show();
    }

    private void atualizarLista() {
        listaTarefas.getItems().clear();
        for (Tarefa tarefa : tarefas) {
            String status = tarefa.isConcluida() ? "[Concluída]" : "[Pendente]";
            listaTarefas.getItems().add(status + " " + tarefa.getDescricao());
        }
    }

    private void mostrarAlerta(String titulo, String mensagem) {
        Alert alerta = new Alert(Alert.AlertType.WARNING);
        alerta.setTitle(titulo);
        alerta.setContentText(mensagem);
        alerta.show();
    }

    // Classe interna para representar uma tarefa
    public static class Tarefa {
        private String descricao;
        private boolean concluida;

        public Tarefa(String descricao) {
            this.descricao = descricao;
            this.concluida = false;
        }

        public String getDescricao() {
            return descricao;
        }

        public boolean isConcluida() {
            return concluida;
        }

        public void setConcluida(boolean concluida) {
            this.concluida = concluida;
        }
    }
}
