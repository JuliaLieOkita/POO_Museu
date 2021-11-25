package boundary;
	
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import controller.ArteControl;
import entity.Arte;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.geometry.Insets;
import javafx.util.converter.NumberStringConverter;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
import javafx.stage.Stage;


public class ArteBoundary implements StrategyBoundary {
	
	private TextField tfId = new TextField();
	private TextField tfNomeObra = new TextField();
	private TextField tfNomeArtista = new TextField();
	private DatePicker dpDataCriacao = new DatePicker();
	private TextField tfDescricao = new TextField();
	
	private Button btnReserva = new Button("Reserva");
	private Button btnColaborador = new Button("Colaboradores");
	private Button btnFuncionario = new Button("Funcionários");
	
	private Button btnAdicionar = new Button("Adicionar");
	private Button btnPesquisar = new Button("Pesquisar");
	private Button btnRemover = new Button("Remover");
	private Button btnAtualizar = new Button("Atualizar");
	private Button btnLimpar = new Button("Limpar");
	
	private ArteControl control = new ArteControl();
	
	private TableView<Arte> table = new TableView<>();
	
	private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	
	private void criarTabela() {
		
		TableColumn<Arte, Long> col1 = new TableColumn<>("Id");
		col1.setCellValueFactory(new PropertyValueFactory<>("id"));
		
		TableColumn<Arte, String> col2 = new TableColumn<>("Nome da Obra");
		col2.setCellValueFactory(new PropertyValueFactory<>("nomeObra"));
		
		TableColumn<Arte, String> col3 = new TableColumn<>("Nome do Artista");
		col3.setCellValueFactory(new PropertyValueFactory<>("nomeArtista"));
		
		TableColumn<Arte, String> col4 = new TableColumn<>("Data Criação da Obra");
		col4.setCellValueFactory( (arteProp) -> {
			LocalDate localDate = arteProp.getValue().getDataCriacao();
			String strData = localDate.format(this.dtf);
			return new ReadOnlyStringWrapper(strData);
		});
		
		TableColumn<Arte, String> col5 = new TableColumn<>("Descrição");
		col5.setCellValueFactory(new PropertyValueFactory<>("descricao"));
		
		table.getColumns().addAll(col1, col2, col3, col4, col5);
		
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
        Bindings.bindBidirectional(tfNomeObra.textProperty(), control.nomeObra);
        Bindings.bindBidirectional(tfNomeArtista.textProperty(), control.nomeArtista);
        Bindings.bindBidirectional(dpDataCriacao.valueProperty(), control.dataCriacao);
        Bindings.bindBidirectional(tfDescricao.textProperty(), control.descricao);
        
        HBox buttonMenuLayout = new HBox();
        
        HBox.setHgrow(btnColaborador, Priority.ALWAYS);
        HBox.setHgrow(btnReserva, Priority.ALWAYS);
        HBox.setHgrow(btnFuncionario, Priority.ALWAYS);
        
        btnColaborador.setMaxWidth(Double.MAX_VALUE);
        btnReserva.setMaxWidth(Double.MAX_VALUE);
        btnFuncionario.setMaxWidth(Double.MAX_VALUE);
        
        buttonMenuLayout.getChildren().add(btnColaborador);
        buttonMenuLayout.getChildren().add(btnReserva);
        buttonMenuLayout.getChildren().add(btnFuncionario);
        
        gridPane.add(btnReserva, 0, 1);
        gridPane.add(btnColaborador, 0, 2);
        gridPane.add(btnFuncionario, 0, 3);
        
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
        
        gridPane.add(new Label("Id"), 1, 0);
        gridPane.add(tfId, 2, 0);
        
        gridPane.add(new Label("Nome da Obra"), 1, 1);
        gridPane.add(tfNomeObra, 2, 1);
        
        gridPane.add(new Label("Nome do Artista"), 1, 2);
        gridPane.add(tfNomeArtista, 2, 2);
        
        gridPane.add(new Label("Data de Criação"), 1, 3);
        gridPane.add(dpDataCriacao, 2, 3);
        
        gridPane.add(new Label("Descrição"), 1, 4);
        gridPane.add(tfDescricao, 2, 4);
        
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
			tfNomeObra.setText("");
			tfNomeArtista.setText("");
			dpDataCriacao.setValue(LocalDate.now());
			tfDescricao.setText("");
		});
		
		borderPane.setTop(gridPane);
		borderPane.setCenter(table);
		this.criarTabela();
		return (borderPane);
		
	}
	
}