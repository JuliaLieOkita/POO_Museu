package boundary;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import controller.ReservaControl;
import entity.Reserva;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyStringWrapper;
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
import javafx.scene.control.DatePicker;
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


public class ReservaBoundary extends CommandProducer implements StrategyBoundary {
	
	private TextField tfId = new TextField();
	private TextField tfNome = new TextField();
	private TextField tfRg = new TextField();
	private TextField tfContato = new TextField();
	private TextField tfQtdPessoas = new TextField();
	private DatePicker dpData = new DatePicker();
	private ComboBox<String> cmbInicio = new ComboBox<>();
	
	private Button btnAcervo = new Button("Acervo");
	private Button btnColaborador = new Button("Colaboradores");
	private Button btnFuncionario = new Button("Funcionários");
	
	private Button btnAdicionar = new Button("Adicionar");
	private Button btnPesquisar = new Button("Pesquisar");
	private Button btnRemover = new Button("Remover");
	private Button btnAtualizar = new Button("Atualizar");
	private Button btnLimpar = new Button("Limpar");
	
	private ReservaControl control = new ReservaControl();
	
	private TableView<Reserva> table = new TableView<>();
	
	private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	   	
	
	private void criarTabela() {
		
		ObservableList<String> horarios = FXCollections.observableArrayList("10:00", "12:00", "14:00", "16:00");
	    cmbInicio.setItems(horarios);
		
		TableColumn<Reserva, Long> col1 = new TableColumn<>("Id");
		col1.setCellValueFactory(new PropertyValueFactory<>("id"));
		
		TableColumn<Reserva, String> col2 = new TableColumn<>("Nome");
		col2.setCellValueFactory(new PropertyValueFactory<>("nome"));
		
		TableColumn<Reserva, Long> col3 = new TableColumn<>("RG");
		col3.setCellValueFactory(new PropertyValueFactory<>("rg"));
		
		TableColumn<Reserva, Long> col4 = new TableColumn<>("Contato");
		col4.setCellValueFactory(new PropertyValueFactory<>("contato"));
		
		TableColumn<Reserva, Integer> col5 = new TableColumn<>("Quantidade Pessoas");
		col5.setCellValueFactory(new PropertyValueFactory<>("qtdPessoas"));
		
		TableColumn<Reserva, String> col6 = new TableColumn<>("Data");
		col6.setCellValueFactory( (reservaProp) -> {
			LocalDate localDate = reservaProp.getValue().getData();
			String strData = localDate.format(dateFormatter);
			return new ReadOnlyStringWrapper(strData);
		});
		
		TableColumn<Reserva, String> col7 = new TableColumn<>("Hora Entrada");
		col7.setCellValueFactory(new PropertyValueFactory<>("horaInicio"));
		
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
        Bindings.bindBidirectional(tfRg.textProperty(), control.rg, new NumberStringConverter());
        Bindings.bindBidirectional(tfContato.textProperty(), control.contato, new NumberStringConverter());
        Bindings.bindBidirectional(tfQtdPessoas.textProperty(), control.qtdPessoas, new NumberStringConverter());
        Bindings.bindBidirectional(dpData.valueProperty(), control.data);
        Bindings.bindBidirectional(cmbInicio.valueProperty(), control.horaInicio);
        
        HBox buttonMenuLayout = new HBox();
        
        HBox.setHgrow(btnAcervo, Priority.ALWAYS);
        HBox.setHgrow(btnColaborador, Priority.ALWAYS);
        HBox.setHgrow(btnFuncionario, Priority.ALWAYS);
        
        btnAcervo.setMaxWidth(Double.MAX_VALUE);
        btnColaborador.setMaxWidth(Double.MAX_VALUE);
        btnFuncionario.setMaxWidth(Double.MAX_VALUE);
        
        buttonMenuLayout.getChildren().add(btnAcervo);
        buttonMenuLayout.getChildren().add(btnColaborador);
        buttonMenuLayout.getChildren().add(btnFuncionario);
        
        gridPane.add(btnAcervo, 0, 0);
        gridPane.add(btnColaborador, 0, 2);
        gridPane.add(btnFuncionario, 0, 3);
        
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
        
        btnFuncionario.setOnAction( e -> {
	    	Stage stageAtual = (Stage) btnFuncionario.getScene().getWindow();
	        stageAtual.close();
	        Stage stageFuncionario = new Stage();
		    Parent root = new FuncionarioBoundary().render();
		    Scene scene = new Scene(root, 900, 600);
		    stageFuncionario.setTitle("Museu - Funcionário");
		    stageFuncionario.setScene(scene);
		    stageFuncionario.show();
        });
        
        tfRg.textProperty().addListener((ChangeListener<? super String>) new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, 
                String newValue) {
                if (!newValue.matches("\\d*")) {
                	tfRg.setText(newValue.replaceAll("[^\\d]", ""));
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
        
        tfQtdPessoas.textProperty().addListener((ChangeListener<? super String>) new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, 
                String newValue) {
                if (!newValue.matches("\\d*")) {
                	tfQtdPessoas.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
                
        
        gridPane.add(new Label("Id"), 1, 0);
		gridPane.add(tfId, 2, 0);
		
		gridPane.add(new Label("Nome"), 1, 1);
		gridPane.add(tfNome, 2, 1);
		
		gridPane.add(new Label("RG"), 1, 2);
		gridPane.add(tfRg, 2, 2);
		
		gridPane.add(new Label("Contato"), 1, 3);
		gridPane.add(tfContato, 2, 3);
		
		gridPane.add(new Label("Quantidade Pessoas"), 1, 4);
		gridPane.add(tfQtdPessoas, 2, 4);
		
		gridPane.add(new Label("Data"), 1,5);
		gridPane.add(dpData, 2, 5);
		
		gridPane.add(new Label("Hora Entrada"), 1, 6);
		gridPane.add(cmbInicio, 2, 6);
		
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
			tfRg.setText("");
			tfContato.setText("");
			tfQtdPessoas.setText("");
			dpData.setValue(LocalDate.now());
			cmbInicio.setValue("");
		});
		
		
		
		borderPane.setTop(gridPane);
		borderPane.setCenter(table);
		this.criarTabela();
		return (borderPane);

	}
	
}