package boundary;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import controller.ColaboradorControl;
import entity.Colaborador;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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


public class ColaboradorBoundary extends CommandProducer implements StrategyBoundary {
	
	private TextField tfId = new TextField();
	private TextField tfNomeInstituicao = new TextField();
	private TextField tfCnpj = new TextField();
	private TextField tfValorDoado = new TextField();
	private DatePicker dpDataDoacao = new DatePicker();
	private TextField tfDescricao = new TextField();
	
	private Button btnAcervo = new Button("Acervo");
	private Button btnReserva = new Button("Reserva");
	private Button btnFuncionario = new Button("Funcionários");
	
	private Button btnAdicionar = new Button("Adicionar");
	private Button btnPesquisar = new Button("Pesquisar");
	private Button btnRemover = new Button("Remover");
	private Button btnAtualizar = new Button("Atualizar");
	private Button btnLimpar = new Button("Limpar");
	
	private ColaboradorControl control = new ColaboradorControl();
	
	private TableView<Colaborador> table = new TableView<>();
	
	private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	
	private void criarTabela() {
		
		TableColumn<Colaborador, Long> col1 = new TableColumn<>("Id");
		col1.setCellValueFactory(new PropertyValueFactory<>("id"));
		
		TableColumn<Colaborador, String> col2 = new TableColumn<>("Nome Instituição");
		col2.setCellValueFactory(new PropertyValueFactory<>("nomeInstituicao"));
		
		TableColumn<Colaborador, Long> col3 = new TableColumn<>("CNPJ");
		col3.setCellValueFactory(new PropertyValueFactory<>("cnpj"));
		
		TableColumn<Colaborador, Double> col4 = new TableColumn<>("Valor Doado (R$)");
		col4.setCellValueFactory(new PropertyValueFactory<>("valorDoado"));
		
		TableColumn<Colaborador, String> col5 = new TableColumn<>("Data Doacao");
		col5.setCellValueFactory( (exposicaoProp) -> {
			LocalDate localDate = exposicaoProp.getValue().getDataDoacao();
			String strDataIni = localDate.format(dtf);
			return new ReadOnlyStringWrapper(strDataIni);
		});
		
		TableColumn<Colaborador, String> col6 = new TableColumn<>("Descrição");
		col6.setCellValueFactory(new PropertyValueFactory<>("descricao"));
		
		table.getColumns().addAll(col1, col2, col3, col4, col5, col6);
		
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
		Bindings.bindBidirectional(tfNomeInstituicao.textProperty(), control.nomeInstituicao);
		Bindings.bindBidirectional(tfCnpj.textProperty(), control.cnpj, new NumberStringConverter());
		Bindings.bindBidirectional(tfValorDoado.textProperty(), control.valorDoado, new NumberStringConverter());
		Bindings.bindBidirectional(dpDataDoacao.valueProperty(), control.dataDoacao);
		Bindings.bindBidirectional(tfDescricao.textProperty(), control.descricao);
		
		HBox buttonMenuLayout = new HBox();
        
        HBox.setHgrow(btnAcervo, Priority.ALWAYS);
        HBox.setHgrow(btnReserva, Priority.ALWAYS);
        HBox.setHgrow(btnFuncionario, Priority.ALWAYS);
        
        btnAcervo.setMaxWidth(Double.MAX_VALUE);
        btnReserva.setMaxWidth(Double.MAX_VALUE);
        btnFuncionario.setMaxWidth(Double.MAX_VALUE);
        
        buttonMenuLayout.getChildren().add(btnAcervo);
        buttonMenuLayout.getChildren().add(btnReserva);
        buttonMenuLayout.getChildren().add(btnFuncionario);
        
        gridPane.add(btnAcervo, 0, 0);
        gridPane.add(btnReserva, 0, 1);
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
	    
	    tfCnpj.textProperty().addListener((ChangeListener<? super String>) new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, 
                String newValue) {
                if (!newValue.matches("\\d*")) {
                	tfCnpj.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
        
	    tfValorDoado.textProperty().addListener((ChangeListener<? super String>) new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, 
                String newValue) {
                if (!newValue.matches("\\d*")) {
                	tfValorDoado.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
		
	    gridPane.add(new Label("Id"), 1, 0);
	    gridPane.add(tfId, 2, 0);
		
	    gridPane.add(new Label("Nome Instituição"), 1, 1);
	    gridPane.add(tfNomeInstituicao, 2, 1);
		
	    gridPane.add(new Label("CNPJ"), 1, 2);
	    gridPane.add(tfCnpj, 2, 2);
		
	    gridPane.add(new Label("Valor Doado"), 1, 3);
	    gridPane.add(tfValorDoado, 2, 3);
		
	    gridPane.add(new Label("Data Doação"), 1, 4);
	    gridPane.add(dpDataDoacao, 2, 4);
		
	    gridPane.add(new Label("Descrição"), 1, 5);
	    gridPane.add(tfDescricao, 2, 5);
		
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
	    	tfNomeInstituicao.setText("");
			tfCnpj.setText("");
			tfValorDoado.setText("");
			dpDataDoacao.setValue(LocalDate.now());
			tfDescricao.setText("");
		});
		
	    borderPane.setTop(gridPane);
	    borderPane.setCenter(table);
	    this.criarTabela();
	    return (borderPane);
		
	}
	
}