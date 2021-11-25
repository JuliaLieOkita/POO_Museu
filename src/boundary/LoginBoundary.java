package boundary;

import controller.FuncionarioControl;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

public class LoginBoundary extends Application {
	
	private TextField tfEmail = new TextField();
	private TextField tfSenha = new TextField();
	private Button btnEntrar = new Button("Entrar");
	
	private FuncionarioControl funcionarioControl = new FuncionarioControl();
		
	private GridPane gridPane = new GridPane();
    private BorderPane panePrincipal = new BorderPane();
    
	@Override
	public void start(Stage stage) throws Exception {

		Scene scn = new Scene(panePrincipal, 250, 170);
		
		Bindings.bindBidirectional(tfEmail.textProperty(), funcionarioControl.email);
        Bindings.bindBidirectional(tfSenha.textProperty(), funcionarioControl.senha);
        
        gridPane.add(new Label("Login"), 2, 0);
        
        gridPane.add(new Label("Email"), 1, 1);
        gridPane.add(tfEmail, 2, 1);
        
        gridPane.add(new Label("Senha"), 1, 2);
        gridPane.add(tfSenha, 2, 2);
        
        gridPane.add(btnEntrar, 2, 3);
        
        btnEntrar.setOnAction( e -> {
        	if(funcionarioControl.verificarAcesso(tfEmail.getText(), tfSenha.getText()).equals("Adm")) {
        		Stage stageAtual = (Stage) btnEntrar.getScene().getWindow();
                stageAtual.close();
    			Stage stageAcervo = new Stage();
    			Parent root = new ArteBoundary().render();
    			Scene scene = new Scene(root, 900, 600);
    			stageAcervo.setTitle("Museu - Acervo");
    			stageAcervo.setScene(scene);
    			stageAcervo.show();
        	} else if (funcionarioControl.verificarAcesso(tfEmail.getText(), tfSenha.getText()).equals("Incorreto")) {
        		Alert alert = new Alert(Alert.AlertType.ERROR, "Usuário ou senha incorreta", ButtonType.OK);
        		alert.show();
        	} else {
        		Alert alert = new Alert(Alert.AlertType.ERROR, "Você não é um Administrador", ButtonType.OK);
        		alert.show();
        	}
        });
        
        gridPane.setPadding(new Insets(10));
	    gridPane.setHgap(10);
	    gridPane.setVgap(10);

        panePrincipal.setCenter(gridPane);
        stage.setScene(scn);
        stage.setTitle("Museu - Login");
        stage.show();
		
	}
	
	
	public static void main(String[] args) {
		Application.launch(LoginBoundary.class, args);
	}

}
