package boundary;

import controller.FuncionarioControl;
import entity.Funcionario;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;


public class FuncionarioBoundary implements StrategyBoundary {

	private TextField tfId = new TextField();
	private TextField tfNome = new TextField();
	private TextField tfCpf = new TextField();
	private TextField tfSenha = new TextField();
	private TextField tfContato = new TextField();
	private TextField tfEmail = new TextField();
	private ComboBox<String> cmbCargo = new ComboBox<>();
	private ComboBox<String> cmbTurno = new ComboBox<>();
	
	private Button btnAcervo = new Button("Acervo");
	private Button btnReserva = new Button("Reserva");
	private Button btnColaborador = new Button("Colaboradores");
	
	private Button btnAdicionar = new Button("Adicionar");
	private Button btnPesquisar = new Button("Pesquisar");
	private Button btnRemover = new Button("Remover");
	private Button btnAtualizar = new Button("Atualizar");
	private Button btnLimpar = new Button("Limpar");
	
	private FuncionarioControl control = new FuncionarioControl();
	
	private TableView<Funcionario> table = new TableView<>();
	
	private void criarTabela() {
		
		ObservableList<String> cargos = FXCollections.observableArrayList("Administrador", "Gerente", "Museólogo", "Segurança");
	    cmbCargo.setItems(cargos);
	    
	    ObservableList<String> turnos = FXCollections.observableArrayList("Matutino", "Vespertino", "Noturno");
	    cmbTurno.setItems(turnos);
		
		TableColumn<Funcionario, Long> col1 = new TableColumn<>("Id");
		col1.setCellValueFactory(new PropertyValueFactory<>("id"));
		
		TableColumn<Funcionario, String> col2 = new TableColumn<>("Nome");
		col2.setCellValueFactory(new PropertyValueFactory<>("nome"));
		
		TableColumn<Funcionario, String> col3 = new TableColumn<>("Cpf");
		col3.setCellValueFactory(new PropertyValueFactory<>("cpf"));

		TableColumn<Funcionario, String> col4 = new TableColumn<>("Contato");
		col4.setCellValueFactory(new PropertyValueFactory<>("contato"));
		
		TableColumn<Funcionario, String> col5 = new TableColumn<>("Email");
		col5.setCellValueFactory(new PropertyValueFactory<>("email"));
		
		TableColumn<Funcionario, String> col6 = new TableColumn<>("Cargo");
		col6.setCellValueFactory(new PropertyValueFactory<>("cargo"));
		
		TableColumn<Funcionario, String> col7 = new TableColumn<>("Turno");
		col7.setCellValueFactory(new PropertyValueFactory<>("turno"));
		
		table.getColumns().addAll(col1, col2, col3, col4, col5, col6, col7);
		
		table.setItems(control.getListaView());
		
		table
		.getSelectionModel()
		.selectedItemProperty()
		.addListener( (obs, antigo, novo) -> {
			if(novo != null) {
				control.setEntity(novo);
			}
		});
		
	}
	

	
	@Override
	public Pane render() {
		BorderPane borderPane = new BorderPane();
		GridPane gridPane = new GridPane();
		
		tfId.setEditable(false);
		tfId.setDisable(true);
		
		Bindings.bindBidirectional(tfId.textProperty(), control.id, new NumberStringConverter());
        Bindings.bindBidirectional(tfNome.textProperty(), control.nome);
        Bindings.bindBidirectional(tfCpf.textProperty(), control.cpf, new NumberStringConverter());
        Bindings.bindBidirectional(tfSenha.textProperty(), control.senha);
        Bindings.bindBidirectional(tfContato.textProperty(), control.contato, new NumberStringConverter());
        Bindings.bindBidirectional(tfEmail.textProperty(), control.email);
        Bindings.bindBidirectional(cmbCargo.valueProperty(), control.cargo);
        Bindings.bindBidirectional(cmbTurno.valueProperty(), control.turno);
        
        HBox buttonMenuLayout = new HBox();
        
        HBox.setHgrow(btnAcervo, Priority.ALWAYS);
        HBox.setHgrow(btnColaborador, Priority.ALWAYS);
        HBox.setHgrow(btnReserva, Priority.ALWAYS);
        
        btnAcervo.setMaxWidth(Double.MAX_VALUE);
        btnColaborador.setMaxWidth(Double.MAX_VALUE);
        btnReserva.setMaxWidth(Double.MAX_VALUE);
        
        buttonMenuLayout.getChildren().add(btnAcervo);
        buttonMenuLayout.getChildren().add(btnColaborador);
        buttonMenuLayout.getChildren().add(btnReserva);
        
        gridPane.add(btnAcervo, 0, 0);
        gridPane.add(btnReserva, 0, 1);
        gridPane.add(btnColaborador, 0, 2);
        
        btnAcervo.setOnAction( e -> {
			Stage stageAtual = (Stage) btnAcervo.getScene().getWindow();
            stageAtual.close();
			Stage stageAcervo = new Stage();
			Parent root = new ArteBoundary().render();
			Scene scene = new Scene(root, 900, 600);
			stageAcervo.setTitle("Museu - Acervo");
			stageAcervo.setScene(scene);
			stageAcervo.show(); 
		});

        btnColaborador.setOnAction( e -> {
        	Stage stageAtual = (Stage) btnColaborador.getScene().getWindow();
            stageAtual.close();
        	Stage stageExposicao = new Stage();
			Parent root = new ColaboradorBoundary().render();
			Scene scene = new Scene(root, 900, 600);
			stageExposicao.setTitle("Museu - Colaborador");
			stageExposicao.setScene(scene);
			stageExposicao.show(); 
		});
        
        btnReserva.setOnAction( e -> {
        	Stage stageAtual = (Stage) btnReserva.getScene().getWindow();
            stageAtual.close();
	      	Stage stageReserva = new Stage();
	      	Parent root = new ReservaBoundary().render();
	      	Scene scene = new Scene(root, 900, 600);
	        stageReserva.setTitle("Museu - Reserva");
	        stageReserva.setScene(scene);
	        stageReserva.show(); 
        });
        
        tfCpf.textProperty().addListener((ChangeListener<? super String>) new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, 
                String newValue) {
                if (!newValue.matches("\\d*")) {
                	tfCpf.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
        
        tfContato.textProperty().addListener((ChangeListener<? super String>) new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, 
                String newValue) {
                if (!newValue.matches("\\d*")) {
                	tfContato.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
        
        gridPane.add(new Label("Id"), 1, 0);
        gridPane.add(tfId, 2, 0);
        
        gridPane.add(new Label("Nome"), 1, 1);
        gridPane.add(tfNome, 2, 1);
        
        gridPane.add(new Label("Cpf"), 1, 2);
        gridPane.add(tfCpf, 2, 2);
        
        gridPane.add(new Label("Senha"), 1, 3);
        gridPane.add(tfSenha, 2, 3);
        
        gridPane.add(new Label("Contato"), 1, 4);
        gridPane.add(tfContato, 2, 4);
        
        gridPane.add(new Label("Email"), 1, 5);
        gridPane.add(tfEmail, 2, 5);
        
        gridPane.add(new Label("Cargo"), 1, 6);
        gridPane.add(cmbCargo, 2, 6);
        
        gridPane.add(new Label("Turno"), 1, 7);
        gridPane.add(cmbTurno, 2, 7);
        
        gridPane.setPadding(new Insets(10));
	    gridPane.setHgap(10);
	    gridPane.setVgap(10);
	    
	    HBox buttonOpcLayout = new HBox();
        
        HBox.setHgrow(btnAdicionar, Priority.ALWAYS);
        HBox.setHgrow(btnAtualizar, Priority.ALWAYS);
        HBox.setHgrow(btnPesquisar, Priority.ALWAYS);
        HBox.setHgrow(btnRemover, Priority.ALWAYS);
        HBox.setHgrow(btnLimpar, Priority.ALWAYS);
        
        btnAdicionar.setMaxWidth(Double.MAX_VALUE);
        btnAtualizar.setMaxWidth(Double.MAX_VALUE);
        btnPesquisar.setMaxWidth(Double.MAX_VALUE);
        btnRemover.setMaxWidth(Double.MAX_VALUE);
        btnLimpar.setMaxWidth(Double.MAX_VALUE);
        
        buttonOpcLayout.getChildren().add(btnAdicionar);
        buttonOpcLayout.getChildren().add(btnAtualizar);
        buttonOpcLayout.getChildren().add(btnPesquisar);
        buttonOpcLayout.getChildren().add(btnRemover);
        buttonOpcLayout.getChildren().add(btnLimpar);
	    
	    gridPane.add(btnAdicionar, 3, 0);
	    gridPane.add(btnAtualizar, 3, 1);
	    gridPane.add(btnPesquisar, 3, 2);
	    gridPane.add(btnRemover, 3, 3);
	    gridPane.add(btnLimpar, 3, 4);
	    
	    btnAdicionar.setOnAction( e -> {
			control.adicionar();
		});
	    
	    btnAtualizar.setOnAction( e -> {
			control.atualizar();
		});

		btnPesquisar.setOnAction( e -> {
			control.pesquisar();
		});

		btnRemover.setOnAction( e -> {
			control.remover();
		});
		
		btnLimpar.setOnAction( e-> {
			tfNome.setText("");
			tfCpf.setText("");;
			tfSenha.setText("");;
			tfContato.setText("");;
			tfEmail.setText("");;
			cmbCargo.setValue("");
			cmbTurno.setValue("");
		});
		
		borderPane.setTop(gridPane);
		borderPane.setCenter(table);
		this.criarTabela();
		return (borderPane);
		
	}
	
}