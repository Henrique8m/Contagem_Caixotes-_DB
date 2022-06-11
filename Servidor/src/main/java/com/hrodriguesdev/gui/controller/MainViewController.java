package com.hrodriguesdev.gui.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.hrodriguesdev.MainApp;
import com.hrodriguesdev.controller.Controller;
import com.hrodriguesdev.controller.PesagemController;
import com.hrodriguesdev.entities.Motorista;
import com.hrodriguesdev.entities.Pesagem;
import com.hrodriguesdev.gui.alert.Alerts;
import com.hrodriguesdev.relatorio.GeneratorPDF;
import com.hrodriguesdev.securit.DataSecurit;
import com.hrodriguesdev.serial.controller.SerialController;
import com.hrodriguesdev.utilitary.Format;
import com.hrodriguesdev.utilitary.NewView;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

//@Component
public class MainViewController implements Initializable{
	
	//@Autowired
	private Controller controller = new Controller();
	
	private Motorista motorista;
	private GeneratorPDF generator = new GeneratorPDF();	

	private Boolean descarregando = false ;	
	private Double totalPeso = 0d;
	
	private List<Pesagem> listPDF = new ArrayList<>();
	private Motorista motoristaPDF;
	@FXML
	private ImageView logoYgg, logo, save, seta, seta1, adcionar, home, buscar, buscar1, pdf, pdf1;
	//Tabela fila de motoristas

	@FXML
	private TableColumn<Motorista, String> nomeMotorista;
	@FXML
	private TableColumn<Motorista, String> placa;
	@FXML
	private TableColumn<Motorista, String> dataChegada;
	@FXML
	private TableColumn<Motorista, String> horaChegada;
	@FXML
	private TableView<Motorista> tableMotorista;
    public static ObservableList<Motorista> obsListTableMotorista = FXCollections.observableArrayList();
    
	@FXML
	private Text pesoDescarregando, caixotes;
	@FXML
	public Text comunicacao;
	
    //Tabela pesagem de carvão
    @FXML
	private TextField name, placa2;
	@FXML
	private TableColumn<Pesagem, Integer> numeroCaixote;
	@FXML
	private TableColumn<Pesagem, Double> peso;
	@FXML
	private TableColumn<Pesagem, String> dataHora;
	@FXML
	private TableColumn<Pesagem, String> responsavel;
    @FXML
	private TableView<Pesagem> tablePesoCarvao;
    public static ObservableList<Pesagem> obsListTableCarvao = FXCollections.observableArrayList();
    
    @FXML
    private void addNewMotorista(ActionEvent e) throws IOException {
    	NewView.getNewViewModal("Cadastro de Motorista", (Pane) NewView.loadFXML("addMotoristaView", MainApp.viewaddMotorista), LoadViewController.getStage());
    	
    }
    
	
	@FXML
	public void config(KeyEvent event) {

		if(event.getCode().toString() == "F5" ) {
			try {
				NewView.getNewViewModal("Cadastro de Motorista", 
						(Pane) NewView.loadFXML("configuration", new ConfigurationViewController()),
						LoadViewController.getStage());
			} catch (IOException e) {
				Alerts.showAlert("IOException", "", e.getMessage(), AlertType.ERROR);
				e.printStackTrace();
			}
	    	
//			if(event.getTarget().equals(data1)){
//				data1.setText(Format.replaceData(data1.getText()));
//				data1.end();	
//				
//			}else if(event.getTarget().equals(placa3)){
//				placa3.setText(Format.replacePlaca(placa3.getText()));
//				placa3.end();	
//			}else if(event.getTarget().equals(name1)){
//				String input = name1.getText().toUpperCase();
//				input = input.replaceAll("[^A-Z-' ']+", "");			
//				name1.setText(input);
//				name1.end();
//			}
		}
	
	}
	
    
    @FXML
    private void iniciarPesagem(ActionEvent e) throws IOException {
		String bug = "bug";
    	if(bug == "") {
    		System.out.println( "Henrique");
    	}
    	if(!descarregando) {    		
	    	if(!tableMotorista.getSelectionModel().isEmpty()) {
	    		descarregando=true;
	    		name.setText(tableMotorista.getSelectionModel().getSelectedItem().getName()); 
	    		placa2.setText(tableMotorista.getSelectionModel().getSelectedItem().getPlaca()); 
	    		motorista = new Motorista();
	    		motorista = tableMotorista.getSelectionModel().getSelectedItem();	    		
	    		obsListTableMotorista.remove(tableMotorista.getSelectionModel().getSelectedIndex());	    		
	    		tableMotorista.refresh();
	    		
	    	}else
	    		System.out.println("Nada Selecionado");
	    		    	
    	}else
    		System.out.println("Temos registro corrente");
    	
    }
    
    @FXML
    private void desfazer(ActionEvent e) throws IOException {
    	if(descarregando) {
    		descarregando=false;
        	name.setText("");    		
        	placa2.setText("");
    		obsListTableMotorista.add(motorista);	    
    		motorista=null;
	    	tableMotorista.refresh();	  
	    	
    	}else
    		System.out.println("Nada Descarregando");
    	
    }    
    
    @FXML
    private void salvarPesagem(ActionEvent e) throws IOException {
    	if(descarregando) {
    		ObservableList<Pesagem> obs = tablePesoCarvao.getItems();
    		if( !controller.updateMotorista(motorista.getId())) {
    			Alerts.showAlert("Error", "Erro ao atualizar o banco de dados!! ",
    					"Nao conseguil tirar o motorista da fila de pesagem.", AlertType.ERROR);
    		}
    		
    		if(obs.size()>0) {    		
    			for(Pesagem p: obs) {
    				if(!controller.editMotoristaDaPesagem(p.getId(), motorista.getId())) {
    	    			Alerts.showAlert("Error", "Erro ao atualizar o banco de dados!! ",
    	    					"Nao conseguil tirar a pesagem da fila.", AlertType.ERROR);
    					return;
    				}
    			}    	
	    		totalPeso = 0d;
	    		caixotes.setText("0");
	    		pesoDescarregando.setText("0");
		    	obsListTableCarvao.removeAll(obs);
		    	tablePesoCarvao.refresh();
		    	name.setText("");    		
		    	placa2.setText("");
		    	descarregando=false;
		    	
				listPDF = new ArrayList<>();
				listPDF.addAll(obs);
				
				
				if(!generator.newDocument(motorista, listPDF, 1) ) {
					Alerts.showAlert("Documento ", "",
							"Nao foi possiver criar o documento", AlertType.ERROR);
				}else {
					Alerts.showAlert("Documento ", "Relatorio criado com sucesso!! ",
							"Diretorio = " + MainApp.caminhoPDF , AlertType.INFORMATION);
				}
				 
			    	
	    			
    		}else {
    			Alerts.showAlert("Operacao", "A lista de pesagem esta nula!! ",
    					"Somente finaliza se tiver peso", AlertType.ERROR);
    		}
    	}else {
			Alerts.showAlert("Operacao", "Nao tem caminhao descarregando ",
					"", AlertType.ERROR);
    	}
    }    
 
	
	
	
	//------------------------------------- Página de Busca --------------------------------------------------------
	
	@FXML
	private TableColumn<Motorista, String> nomeMotorista1;
	@FXML
	private TableColumn<Motorista, String> placa1;
	@FXML
	private TableColumn<Motorista, String> dataChegada1;
	@FXML
	private TableColumn<Motorista, String> horaChegada1;
	@FXML
	private TableView<Motorista> tableMotoristaFind;
    public static ObservableList<Motorista> obsListTableViewMotoristaFind = FXCollections.observableArrayList();;
	
    
	@FXML
	private TableColumn<Pesagem, Integer> numeroCaixote1;
	@FXML
	private TableColumn<Pesagem, Double> peso1;
	@FXML
	private TableColumn<Pesagem, String> dataHora1;
	@FXML
	private TableColumn<Pesagem, String> responsavel1;
    @FXML
	private TableView<Pesagem> tablePesoCarvao1;
    public static ObservableList<Pesagem> obsListTableCarvao1 = FXCollections.observableArrayList();
    
    
    @FXML
    private TextField name1, data1, placa3;

    
	@FXML
    private void buscar(ActionEvent e) throws IOException {
    	Motorista obj = new Motorista();
    	if( !name1.getText().isEmpty() ) {
    		obj.setName(name1.getText());
    	}
    	if( !placa3.getText().isEmpty() ) {
    		obj.setPlaca(placa3.getText());
    	}
    	if( !data1.getText().isEmpty() ) {
    		obj.setData(data1.getText());
    	}    	    	
    	
    	ObservableList<Motorista> ojs = controller.findAll(obj);
    	if(ojs.size()>0 ) {
    		obsListTableViewMotoristaFind = ojs;    	
;
    	}else { 		
    		obsListTableViewMotoristaFind = controller.findPageable(); 		
    	}
    	 tableMotoristaFind.setItems(obsListTableViewMotoristaFind);
    }
    
	
	@FXML
	public void format(KeyEvent event) {
		if(event.getCode().toString() != "BACK_SPACE" || event.getCode().toString() != "LEFT" ) {
			if(event.getTarget().equals(data1)){
				data1.setText(Format.replaceData(data1.getText()));
				data1.end();	
				
			}else if(event.getTarget().equals(placa3)){
				placa3.setText(Format.replacePlaca(placa3.getText()));
				placa3.end();	
			}else if(event.getTarget().equals(name1)){
				String input = name1.getText().toUpperCase();
				input = input.replaceAll("[^A-Z-' ']+", "");			
				name1.setText(input);
				name1.end();
			}
		}
	
	}
	
	@FXML
	public void click(MouseEvent event) {
		long motoristaid;
		ObservableList<Pesagem> obs;
		if(!tableMotoristaFind.getSelectionModel().isEmpty()) {
			motoristaid = tableMotoristaFind.getSelectionModel().getSelectedItem().getId();
			motoristaPDF = controller.getMotoristaById(motoristaid);
			obs = controller.getPesagemByMotoristaId(motoristaid);
			if(obs != null) {
				listPDF = new ArrayList<>();
				obsListTableCarvao1 = obs;
				listPDF.addAll(obs);
			}
				
			tablePesoCarvao1.setItems(obsListTableCarvao1);
		
		}
	}	
	
	@FXML
    private void gerarPDF(ActionEvent e) throws IOException {
		if(motoristaPDF != null) {
			if(!generator.newDocument(motoristaPDF, listPDF, 1) ) {
				Alerts.showAlert("Documento ", "Não foi possiver criar o documento ",
						"", AlertType.ERROR);
			}else {
				Alerts.showAlert("Documento ", "Relatorio criado com sucesso!! ",
						"Diretorio = " + MainApp.caminhoPDF , AlertType.INFORMATION);
			}
		}    	
    	
    }
	
	@FXML
    private void gerarPDFDia(ActionEvent e) throws IOException {
		long motoristaid;
		if( obsListTableViewMotoristaFind.size() > 0 ) {
			ObservableList<Pesagem> pesagem;
			for(Motorista motorista: obsListTableViewMotoristaFind) {				
				motoristaid =motorista.getId();
				motoristaPDF = controller.getMotoristaById(motoristaid);
				pesagem = controller.getPesagemByMotoristaId(motoristaid);
				if(pesagem != null) {
					listPDF = new ArrayList<>();
					listPDF.addAll(pesagem);
				}
				if(!generator.newDocument(motoristaPDF, listPDF, 2) ) {
					Alerts.showAlert("Documento ", "Nao foi possiver criar o documento ",
							"", AlertType.ERROR);
				}else {
					Alerts.showAlert("Documento ", "Relatorio criado com sucesso!! ",
							"Diretorio = " + MainApp.caminhoPDF , AlertType.INFORMATION);
				}				
				
			}
		}
   }
	
	
//----------------------------------------------------------------------------------------------------------------------
	
//	private List<String> avaliablePorts;
//	private String defautPort = "COM4";
	private Date date;
	
	private SerialController serialController = new SerialController();
	private PesagemController pesagemController = new PesagemController();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		serialController = MainApp.serialController;
		strartTable();
		pesagemController.start();
		
		Image image = new Image(MainApp.class.getResource("gui/resources/Yggdrasilicon.jpg").toString() );
		logoYgg.setImage(image);
		image = new Image(MainApp.class.getResource("gui/resources/metalNobre.jpg").toString() );
		logo.setImage(image);
		image = new Image(MainApp.class.getResource("gui/resources/icons-salvar-arquivo.png").toString() );
		save.setImage(image);
		image = new Image(MainApp.class.getResource("gui/resources/icons-fechar-painel.png").toString() );
		seta.setImage(image);
		seta1.setImage(image);
		image = new Image(MainApp.class.getResource("gui/resources/icons-adicionar.png").toString() );
		adcionar.setImage(image);
		image = new Image(MainApp.class.getResource("gui/resources/icons-bandeira-de-chegada.png").toString() );
		home.setImage(image);
		image = new Image(MainApp.class.getResource("gui/resources/icons-pesquisar.png").toString() );
		buscar.setImage(image);
		buscar1.setImage(image);
		image = new Image(MainApp.class.getResource("gui/resources/icons-pdf.png").toString() );
		pdf.setImage(image);
		pdf1.setImage(image);
		 
		 
		
		//Start serial
		/*
		avaliablePorts = new ArrayList<>();
		@SuppressWarnings("unchecked")
		Enumeration<CommPortIdentifier> enume = CommPortIdentifier.getPortIdentifiers();		
		while(enume.hasMoreElements()) {
			avaliablePorts.add(enume.nextElement().getName());		
		}
		//DependencyInjection.setAvaliablePortsNames(avaliablePorts);
		
		for(String e : avaliablePorts)
			if(e.equals(defautPort))
				serialProperties.setPorta("COM4");
			else if(e.equals(lastPort))
			serialProperties.setPorta(lastPort);
		*/
		//serialProperties.setPorta("COM4");
		
    	try {
    		serialController.read();
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    	
		if(!DataSecurit.validateData()) {
			Alerts.showAlert("Securit", "Error, validação da licença ",
					"Erro ao validar a licença, entre em contato com o adim", AlertType.ERROR);
		}
	}	 	
	
	
	public void strartTable() {	
		//Table fila de Motorista descarregando
		obsListTableMotorista = controller.getByFila(true);		
		tableMotorista.setEditable(false);	 
		nomeMotorista.setSortType(TableColumn.SortType.DESCENDING);
		nomeMotorista.setCellValueFactory(new PropertyValueFactory<Motorista, String>("name"));
		placa.setCellValueFactory(new PropertyValueFactory<Motorista, String>("placa"));
		dataChegada.setCellValueFactory( new PropertyValueFactory<Motorista, String>("data"));
		horaChegada.setCellValueFactory(new PropertyValueFactory<Motorista, String>("hora"));
		tableMotorista.setItems(obsListTableMotorista);
		
		//Table pesagem corrente
		obsListTableCarvao = controller.getByDescarregando(true);
		//Alimentar as informações de Peso corrente, e numero de Caixotes
		for(Pesagem p: obsListTableCarvao) {
			totalPeso += p.getPeso();
		}
		caixotes.setText(Integer.toString( obsListTableCarvao.size() ) );
		pesoDescarregando.setText( Double.toString(totalPeso) );
		caixotes.setText( Integer.toString(obsListTableCarvao.size()) );
		tablePesoCarvao.setEditable(false);
	 	numeroCaixote.setSortType(TableColumn.SortType.DESCENDING);
		numeroCaixote.setCellValueFactory(new PropertyValueFactory<Pesagem, Integer>("numeroCaixote"));
		peso.setCellValueFactory(new PropertyValueFactory<Pesagem, Double>("peso"));
		dataHora.setCellValueFactory( new PropertyValueFactory<Pesagem, String>("dataHora"));
		responsavel.setCellValueFactory(new PropertyValueFactory<Pesagem, String>("responsavel"));
		tablePesoCarvao.setItems(obsListTableCarvao); 		
		
		//Table find Motorista
		tableMotoristaFind.setEditable(false);		 
		nomeMotorista1.setSortType(TableColumn.SortType.DESCENDING);
		nomeMotorista1.setCellValueFactory(new PropertyValueFactory<Motorista, String>("name"));
		placa1.setCellValueFactory(new PropertyValueFactory<Motorista, String>("placa"));
		dataChegada1.setCellValueFactory(new PropertyValueFactory<Motorista, String>("data"));
		horaChegada1.setCellValueFactory(new PropertyValueFactory<Motorista, String>("hora"));
		tableMotoristaFind.setItems(obsListTableViewMotoristaFind);		
		
		//Table de pesagem relacionado ao Motorista
		tablePesoCarvao1.setEditable(false);		 
		numeroCaixote1.setSortType(TableColumn.SortType.DESCENDING);
		numeroCaixote1.setCellValueFactory(new PropertyValueFactory<Pesagem, Integer>("numeroCaixote"));
		peso1.setCellValueFactory(new PropertyValueFactory<Pesagem, Double>("peso"));
		dataHora1.setCellValueFactory( new PropertyValueFactory<Pesagem, String>("dataHora"));
		responsavel1.setCellValueFactory( new PropertyValueFactory<Pesagem, String>("responsavel"));		
		tablePesoCarvao1.setItems(obsListTableCarvao1);
		
	}

	public void addPesagem(Double valueStabilized) { 
		date = new Date(System.currentTimeMillis());
		totalPeso += valueStabilized;
		pesoDescarregando.setText( Double.toString(totalPeso) );
		Pesagem pesagem = new Pesagem(obsListTableCarvao.size() + 1 ,valueStabilized, Format.formatData.format(date), Format.formataTimeString.format(date), "", true );

		caixotes.setText( Integer.toString(obsListTableCarvao.size() + 1 ) );
		Long id = controller.addPesagem(pesagem);
		if( id == null ) {
			Alerts.showAlert("Pesagem ", "Erro na criação de um novo peso ",
					"", AlertType.ERROR);
			return;
		}else pesagem.setId(id);		
		obsListTableCarvao.add(pesagem);
	}
	/*
	@FXML
    private void insert(ActionEvent e) throws IOException {
		addPesagem(35.0);
    }
*/
}