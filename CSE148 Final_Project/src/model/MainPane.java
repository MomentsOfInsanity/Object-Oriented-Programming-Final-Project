package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class MainPane extends BorderPane
{
	private static final Object Persons = null;
	private MenuBar menuBar = new MenuBar();
	private BorderPane bpViewBox = new BorderPane();

/////////////////////////////////////////////////////////////////////////////	
	public MainPane()
	{
		this.setPrefSize(650, 500);
		createMenuBar();
		this.setStyle("-fx-background-color: #500000;");
		bpViewBox.setPadding(new Insets(5,5,5,5));
	//	bpViewBox.setStyle("-fx-background-color: #0F1B4F");
		//500000
		//00BFFF
		//0F1B4F
		//FF8C00
		this.setTop(menuBar);
		this.setCenter(bpViewBox);
		
	}
////////////////////////////////////////////////////////////////////////////	
	private void createMenuBar()
	{
		Menu menuFile = new Menu("File");
		Menu menuInsert = new Menu("Insert");
		Menu menuSearch = new Menu("Search");
		Menu menuUpdate = new Menu("Update");
		Menu menuDelete = new Menu("Delete");

		MenuItem menuFile_IMPORT = new MenuItem("Import");
		MenuItem menuFile_EXPORT = new MenuItem("Export");
		MenuItem menuFile_SAVE = new MenuItem("Save");
		MenuItem menuFile_LOAD = new MenuItem("Load");
		MenuItem menuFile_EXIT = new MenuItem("Exit");
		
		menuFile_IMPORT.setOnAction(event ->{
			bpViewBox.setCenter(importFiles());
		});
		menuFile_EXPORT.setOnAction(event ->{
			bpViewBox.setCenter(exportFiles());
		});
		
		menuFile_SAVE.setOnAction(event ->{
			Demo.personbag.update();
			Demo.textbookbag.update();
			Demo.mastercoursebag.update();
			System.out.println("Saved");
		});
		
		menuFile_LOAD.setOnAction(event ->{
			Demo.personbag.load();
			Demo.textbookbag.load();
			Demo.mastercoursebag.load();
			System.out.println("Loaded");
		});
		
		menuFile_EXIT.setOnAction(e -> {
			Alert alert2 = new Alert(AlertType.WARNING);
			alert2.setTitle("Warning Dialog");
			alert2.setHeaderText("Warning!");
			alert2.setContentText("Do you wish to exit without saving? \n Hit OK to save.");
			alert2.showAndWait();
			Demo.personbag.update();
			Demo.textbookbag.update();
			Demo.mastercoursebag.update();
			Platform.exit();
			System.out.println("It has been saved");
		});
		
		
		menuFile.getItems().addAll(menuFile_IMPORT, menuFile_EXPORT, menuFile_SAVE, menuFile_LOAD, menuFile_EXIT);
		
		Menu menuInsert_ADD = new Menu("Add");
		MenuItem menuADD_Student = new MenuItem("Student");
		MenuItem menuADD_Faculty = new MenuItem("Faculty");
		MenuItem menuADD_Textbook = new MenuItem("Textbook");
		MenuItem menuADD_Course = new MenuItem("Course");
		
		
		menuADD_Student.setOnAction(event -> {
			bpViewBox.setCenter(nodeNewStudent());
		});
		
		menuADD_Faculty.setOnAction(event ->{
			bpViewBox.setCenter(nodeNewFaculty());
		});
		menuADD_Textbook.setOnAction(event ->{
			bpViewBox.setCenter(nodeNewTextbook());
		});
		menuADD_Course.setOnAction(event ->{
			bpViewBox.setCenter(nodeNewCourse());
		});
		
		
		menuInsert_ADD.getItems().addAll(menuADD_Student,menuADD_Faculty,menuADD_Textbook, menuADD_Course);
		
		
		MenuItem menuSearch_ID = new MenuItem("Search ID");
		MenuItem menuSearch_Course = new MenuItem("Search Course");
		MenuItem menuSearch_Textbook = new MenuItem("Search ISBN");
		
		menuSearch_ID.setOnAction(event ->{
			bpViewBox.setTop(searchID());
		});
		menuSearch_Course.setOnAction(event ->{
			bpViewBox.setTop(searchCourse());
		});
		menuSearch_Textbook.setOnAction(event -> {
			bpViewBox.setTop(searchTextbook());
		});
		
		menuSearch.getItems().addAll(menuSearch_ID, menuSearch_Course, menuSearch_Textbook);
		
		
		
		menuInsert.getItems().addAll(menuInsert_ADD);
		
		
		
		
		MenuItem menuUpdate_Student = new MenuItem("Student");
		MenuItem menuUpdate_Faculty = new MenuItem("Faculty");
		MenuItem menuUpdate_BookBag = new MenuItem("TextbookBag");
		MenuItem menuUpdate_CourseBag = new MenuItem("CourseBag");
		
		menuUpdate.getItems().addAll(menuUpdate_Student, menuUpdate_Faculty, menuUpdate_BookBag, menuUpdate_CourseBag);
		
		MenuItem menuDelete_Student = new MenuItem("Student");
		MenuItem menuDelete_Faculty = new MenuItem("Faculty");
		MenuItem menuDelete_Textbook = new MenuItem("Textbook");
		MenuItem menuDelete_Course = new MenuItem("Course");
		
		
		menuDelete_Student.setOnAction(event ->{
			bpViewBox.setTop(deletePerson());
		});
		menuDelete_Faculty.setOnAction(event -> {
			bpViewBox.setTop(deletePerson());
		});
		menuDelete_Textbook.setOnAction(event ->{
			bpViewBox.setTop(deleteTextbook());
		});
		menuDelete_Course.setOnAction(event ->{
			bpViewBox.setTop(deleteCourse());
		});
		
		
		menuDelete.getItems().addAll(menuDelete_Student,menuDelete_Faculty,menuDelete_Textbook, menuDelete_Course);
		
		
		menuBar.getMenus().addAll(menuFile, menuInsert, menuSearch, menuUpdate, menuDelete);
	}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private Node importFiles(){
		GridPane Pane = new GridPane();
		Pane.setVgap(80);
		Pane.setHgap(10);
		
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Resource File");
		
		Label lblimportStudent = new Label("Import Student File");
		lblimportStudent.setTextFill(Color.PURPLE);
		lblimportStudent.setFont(new Font ("ALGERIAN", 20));
		Label lblimportFaculty = new Label("Import Faculty File");
		lblimportFaculty.setTextFill(Color.PURPLE);
		lblimportFaculty.setFont(new Font ("ALGERIAN", 20));
		Label lblimportTextbook = new Label("Import Txtbook File");
		lblimportTextbook.setTextFill(Color.PURPLE);
		lblimportTextbook.setFont(new Font ("ALGERIAN", 20));
		Label lblimportCourse = new Label("Import Course File");
		lblimportCourse.setTextFill(Color.PURPLE);
		lblimportCourse.setFont(new Font ("ALGERIAN", 20));
		
		TextField tfimportStudent = new TextField();
		tfimportStudent.setDisable(true);
		TextField tfimportFaculty = new TextField();
		tfimportFaculty.setDisable(true);
		TextField tfimportTextbook = new TextField();
		tfimportTextbook.setDisable(true);
		TextField tfimportCourse = new TextField();
		tfimportCourse.setDisable(true);
		
		Button btnstudent = new Button("Select");
		btnstudent.setFont(new Font ("ALGERIAN", 16));
		btnstudent.setPrefWidth(120);
		btnstudent.setPrefHeight(30);
		Button btnfaculty = new Button("Select");
		btnfaculty.setFont(new Font ("ALGERIAN", 16));
		btnfaculty.setPrefWidth(120);
		btnfaculty.setPrefHeight(30);
		Button btntextbook = new Button("Select");;
		btntextbook.setFont(new Font ("ALGERIAN", 16));
		btntextbook.setPrefWidth(120);
		btntextbook.setPrefHeight(30);
		Button btncourse = new Button("Select");
		btncourse.setFont(new Font ("ALGERIAN", 16));
		btncourse.setPrefWidth(120);
		btncourse.setPrefHeight(30);
		
		Button btnstudent_Import = new Button("Import");
		btnstudent_Import.setFont(new Font ("ALGERIAN", 16));
		btnstudent_Import.setPrefWidth(120);
		btnstudent_Import.setPrefHeight(30);
		Button btnfaculty_Import = new Button("Import");
		btnfaculty_Import.setFont(new Font ("ALGERIAN", 16));
		btnfaculty_Import.setPrefWidth(120);
		btnfaculty_Import.setPrefHeight(30);
		Button btntextbook_Import = new Button("Import");
		btntextbook_Import.setFont(new Font ("ALGERIAN", 16));
		btntextbook_Import.setPrefWidth(120);
		btntextbook_Import.setPrefHeight(30);
		Button btncourse_Import = new Button("Import");
		btncourse_Import.setFont(new Font ("ALGERIAN", 16));
		btncourse_Import.setPrefWidth(120);
		btncourse_Import.setPrefHeight(30);
		
		btnstudent.setOnAction(event ->{
			File file = fileChooser.showOpenDialog(null);
			if(file != null){
				tfimportStudent.setText(file.getAbsolutePath());
			}
		});
		btnfaculty.setOnAction(event ->{
			File file = fileChooser.showOpenDialog(null);
			if(file != null){
				tfimportFaculty.setText(file.getAbsolutePath());
			}
		});
		btntextbook.setOnAction(event ->{
			File file = fileChooser.showOpenDialog(null);
			if(file != null){
				tfimportTextbook.setText(file.getAbsolutePath());
			}
		});
		btncourse.setOnAction(event ->{
			File file = fileChooser.showOpenDialog(null);
			if(file != null){
				tfimportCourse.setText(file.getAbsolutePath());
			}
		});
		
		
		btnstudent_Import.setOnAction(event ->{
			Demo.personbag.importStudentFile(tfimportStudent.getText());
		});
		btnfaculty_Import.setOnAction(event ->{
			Demo.personbag.importFacultyFile(tfimportFaculty.getText());
		});
		btntextbook_Import.setOnAction(event ->{
			Demo.textbookbag.importPersonBag(tfimportTextbook.getText());
		});
		btncourse_Import.setOnAction(event ->{
			Demo.mastercoursebag.importCourseBag(tfimportCourse.getText());
		});
		
		
		Pane.add(lblimportStudent, 0, 0);
		Pane.add(tfimportStudent, 1, 0);
		Pane.add(btnstudent, 2, 0);
		Pane.add(btnstudent_Import, 3, 0);
		
		Pane.add(lblimportFaculty, 0, 1);
		Pane.add(tfimportFaculty, 1, 1);
		Pane.add(btnfaculty, 2, 1);
		Pane.add(btnfaculty_Import, 3, 1);
		
		Pane.add(lblimportTextbook, 0, 2);
		Pane.add(tfimportTextbook, 1, 2);
		Pane.add(btntextbook, 2, 2);
		Pane.add(btntextbook_Import, 3, 2);
		
		Pane.add(lblimportCourse, 0, 3);
		Pane.add(tfimportCourse, 1, 3);
		Pane.add(btncourse, 2, 3);
		Pane.add(btncourse_Import, 3, 3);
		
		
		bpViewBox.setCenter(Pane);
		return Pane;
	}
////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	private Node exportFiles(){
		GridPane Pane = new GridPane();
		Pane.setVgap(80);
		Pane.setHgap(10);
		
		
		FileChooser fileChooser = new FileChooser();			
		fileChooser.setTitle("Open Resource File");
		
		Label lblexportStudent = new Label("Export Student File");
		lblexportStudent.setTextFill(Color.PURPLE);
		lblexportStudent.setFont(new Font ("ALGERIAN", 20));
		Label lblexportFaculty = new Label("Export Faculty File");
		lblexportFaculty.setTextFill(Color.PURPLE);
		lblexportFaculty.setFont(new Font ("ALGERIAN", 20));
		Label lblexportTextbook = new Label("Export Textbook File");
		lblexportTextbook.setTextFill(Color.PURPLE);
		lblexportTextbook.setFont(new Font ("ALGERIAN", 20));
		Label lblexportCourse = new Label("Export Course File");
		lblexportCourse.setTextFill(Color.PURPLE);
		lblexportCourse.setFont(new Font ("ALGERIAN", 20));
		
		TextField tfexportStudent = new TextField();
		tfexportStudent.setDisable(true);
		tfexportStudent.setPrefWidth(250);
		tfexportStudent.setPrefHeight(30);
		TextField tfexportFaculty = new TextField();
		tfexportFaculty.setDisable(true);
		tfexportFaculty.setPrefWidth(250);
		tfexportFaculty.setPrefHeight(30);
		TextField tfexportTextbook = new TextField();
		tfexportTextbook.setDisable(true);
		tfexportTextbook.setPrefWidth(250);
		tfexportTextbook.setPrefHeight(30);
		TextField tfexportCourse = new TextField();
		tfexportCourse.setDisable(true);
		tfexportCourse.setPrefWidth(250);
		tfexportCourse.setPrefHeight(30);
		
		Button btnstudent_export = new Button("Export");
		btnstudent_export.setFont(new Font ("ALGERIAN", 12));
		btnstudent_export.setPrefWidth(120);
		btnstudent_export.setPrefHeight(60);
		Button btnfaculty_export = new Button("Export");
		btnfaculty_export.setFont(new Font ("ALGERIAN", 12));
		btnfaculty_export.setPrefWidth(120);
		btnfaculty_export.setPrefHeight(60);
		Button btntextbook_export = new Button("Export");
		btntextbook_export.setFont(new Font ("ALGERIAN", 12));
		btntextbook_export.setPrefWidth(120);
		btntextbook_export.setPrefHeight(60);
		Button btncourse_export = new Button("Export");
		btncourse_export.setFont(new Font ("ALGERIAN", 12));
		btncourse_export.setPrefWidth(120);
		btncourse_export.setPrefHeight(60);
		
		btnstudent_export.setOnAction(event ->{
			File file = fileChooser.showSaveDialog(null);
			if(file != null){
				Demo.personbag.exportStudentFile(file.getAbsolutePath() + ".txt");
			}
		});
		btnfaculty_export.setOnAction(event ->{
			File file = fileChooser.showSaveDialog(null);
			if(file != null){
				//******
			}
		});
		btntextbook_export.setOnAction(event ->{
			File file = fileChooser.showSaveDialog(null);
			if(file != null){
				Demo.textbookbag.exportBookBag(file.getAbsolutePath() + ".txt");;
			}
		});
		btncourse_export.setOnAction(event ->{
			File file = fileChooser.showSaveDialog(null);
			if(file != null){
				Demo.mastercoursebag.exportCourseBag(file.getAbsolutePath() + ".txt");
			}
		});
		
		
		Pane.add(lblexportStudent, 0, 0);
		Pane.add(tfexportStudent, 1, 0);
		Pane.add(btnstudent_export, 2, 0);
		
		Pane.add(lblexportFaculty, 0, 1);
		Pane.add(tfexportFaculty, 1, 1);
		Pane.add(btnfaculty_export, 2, 1);
		
		Pane.add(lblexportTextbook, 0, 2);
		Pane.add(tfexportTextbook, 1, 2);
		Pane.add(btntextbook_export, 2, 2);
		
		Pane.add(lblexportCourse, 0, 3);
		Pane.add(tfexportCourse, 1, 3);
		Pane.add(btncourse_export, 2, 3);
		
		
		bpViewBox.setCenter(Pane);
		return Pane;
	}
////////////////////////////////////////////////////////////////////////////////////	
	private Node searchID(){
		HBox hbID = new HBox();
		hbID.setSpacing(5);
		
		Label lblID = new Label("Enter ID");
		lblID.setTextFill(Color.PURPLE);
		lblID.setFont(new Font ("ALGERIAN", 12));
		//lblID.setTextFill(Color.WHITE);
		
		TextField tfID = new TextField();
		
		Button buttonSearch = new Button("Search");
		buttonSearch.setOnAction(event -> {
			if (tfID.getText() != null){
				if (Demo.personbag.searchById(tfID.getText()) != null){
					if(Demo.personbag.searchById(tfID.getText()) instanceof Student){
						bpViewBox.setCenter(showStudent(null));
					}
					
				}
			}
			
        });
		
		hbID.getChildren().addAll(lblID, tfID , buttonSearch);
		
		hbID.setAlignment(Pos.BASELINE_CENTER);
		
		return hbID;
	}
//////////////////////////////////////////////////////////////////	
	private Node showStudent(Person S) {
		BorderPane node = new BorderPane();
		
		Label lblID = new Label("FirstName: " + S.getId());
		
		
		VBox VBox = new VBox();
		VBox.getChildren().add(lblID);
		
		node.setCenter(VBox);
		
		return node;
	}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private Node searchCourse(){
		HBox hbSearch = new HBox();
		hbSearch.setSpacing(5);
		
		Label lblCourseNumber = new Label("Enter Course Number");
		lblCourseNumber.setTextFill(Color.PURPLE);
		lblCourseNumber.setFont(new Font ("ALGERIAN", 12));
		
		TextField tfISBN= new TextField();
		
		Button buttonSearch = new Button("Search");
		buttonSearch.setOnAction(event -> {
			if ((tfISBN.getText()) != null){
				if(Demo.mastercoursebag.searchByCourseNumber(tfISBN.getText()) != null){
					bpViewBox.setCenter(showCourse(Demo.mastercoursebag.searchByCourseNumber(tfISBN.getText())));
				}
				else{
					tfISBN.setText("Invalid Course Number and/or Course not in database.");
				}
			}
        });
		
		hbSearch.getChildren().addAll(lblCourseNumber, tfISBN , buttonSearch);
		
		hbSearch.setAlignment(Pos.BASELINE_CENTER);
		
		return hbSearch;
	}
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	private Node searchTextbook(){
		HBox hbSearch = new HBox();
		hbSearch.setSpacing(5);
		
		Label lblISBN = new Label("Enter ISBN");
		lblISBN.setTextFill(Color.PURPLE);
		lblISBN.setFont(new Font ("ALGERIAN", 12));
		//lblID.setTextFill(Color.WHITE);
		
		TextField tfISBN= new TextField();
		
		Button buttonSearch = new Button("Search");
		buttonSearch.setOnAction(event -> {
			if ((tfISBN.getText()) != null){
				if(Demo.textbookbag.searchByBookISBN(tfISBN.getText()) != null){
					bpViewBox.setCenter(showTextbook(Demo.textbookbag.searchByBookISBN(tfISBN.getText())));
				}
				else{
					tfISBN.setText("Invalid ISBN and/or ISBN not in database.");
				}
			}
        });
		
		hbSearch.getChildren().addAll(lblISBN, tfISBN , buttonSearch);
		
		hbSearch.setAlignment(Pos.BASELINE_CENTER);
		
		return hbSearch;
	}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private Node showCourse(Course C){
		BorderPane node = new BorderPane();
		//public Course(String courseNumber, String courseTitle, String courseDescription, Double courseCredits,
		//ArrayList<String> textbooks) {
		
		Label lblCourseNumber = new Label("Course Number: " + C.getCourseNumber());
		Label lblCourseTitle = new Label("Course Title: " + C.getCourseTitle());
		Label lblCourseDecription = new Label("Description: " +  C.getCourseDescription());
		Label lblCredits = new Label("Credits: " + C.getCredits());
		Label lblTextbooks = new Label("Textbooks: " + C.getTextbook().toString().substring(1, C.getTextbook().toString().length()-2));
		
		
		VBox VBox = new VBox();
		VBox.getChildren().addAll(lblCourseNumber, lblCourseTitle, lblCourseDecription, lblCredits, lblTextbooks);
		
		node.setCenter(VBox);
		
		
		return node;
	}
	
////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	private Node nodeNewStudent(){
		ScrollPane Pane = new ScrollPane();
		Pane.setFitToWidth(true);
		VBox VBox = new VBox();
		
		TextField tfFirstName = new TextField();
		tfFirstName.setPrefWidth(120);
		tfFirstName.setPrefHeight(30);
		TextField tfLastName = new TextField();
		tfLastName.setPrefWidth(120);
		tfLastName.setPrefHeight(30);
		Label lblFirstName = new Label("First:");
		lblFirstName.setTextFill(Color.PURPLE);
		lblFirstName.setFont(new Font ("ALGERIAN", 28));
		Label lblLastName = new Label("Last:");
		lblLastName.setTextFill(Color.PURPLE);
		lblLastName.setFont(new Font ("ALGERIAN", 28));
		
		Label lblNewStudent = new Label("New Student");
		lblNewStudent.setTextFill(Color.PURPLE);
		lblNewStudent.setFont(new Font ("ALGERIAN", 30));
		
		HBox hbName = new HBox();
		hbName.setSpacing(30);
		hbName.setPadding(new Insets(0,10,0,10));
		
		////////////////////////////////////
		Label lbladdCourse = new Label("Add Course taking:");
		ArrayList<String> c = new ArrayList<String>();
		
		Label lblCoursesTaking = new Label();
		HBox hbCoursesTaking = new HBox();
		hbCoursesTaking.setSpacing(20);
		hbCoursesTaking.setAlignment(Pos.CENTER);	
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////		
		
		
		//////////////////////////////////Creates Address GridPane
		GridPane gpAddress = new GridPane();
		
		Label lblADDRESS = new Label("Address");
		lblADDRESS.setTextFill(Color.PURPLE);
		lblADDRESS.setFont(new Font ("ALGERIAN", 28));
		
		Label lblStreetNumber = new Label("Street Number:");
		lblStreetNumber.setTextFill(Color.PURPLE);
		lblStreetNumber.setFont(new Font ("ALGERIAN", 28));
		TextField tfStreetNumber = new TextField();
		tfStreetNumber.setPrefHeight(30);
	
		Label lblStreet = new Label("Street:");
		lblStreet.setTextFill(Color.PURPLE);
		lblStreet.setFont(new Font ("ALGERIAN", 28));
		TextField tfStreet = new TextField();
		tfStreet.setPrefHeight(30);

		Label lblTown = new Label("Town:");
		lblTown.setTextFill(Color.PURPLE);
		lblTown.setFont(new Font ("ALGERIAN", 28));
		TextField tfTown = new TextField();
		tfTown.setPrefHeight(30);
		
		Label lblState = new Label("State:");
		lblState.setTextFill(Color.PURPLE);
		lblState.setFont(new Font ("ALGERIAN", 28));
		TextField tfState = new TextField();
		tfState.setPrefHeight(30);
		
		Label lblZip = new Label("Zip:");
		lblZip.setTextFill(Color.PURPLE);
		lblZip.setFont(new Font ("ALGERIAN", 28));
		TextField tfZip = new TextField();
		tfZip.setPrefHeight(30);
		
		gpAddress.add(lblADDRESS, 0, 0);
		gpAddress.add(lblStreetNumber, 0, 0);
		gpAddress.add(tfStreetNumber, 1, 0);
		gpAddress.add(lblStreet, 0, 1);
		gpAddress.add(tfStreet, 1, 1);
		gpAddress.add(lblTown, 0, 2);
		gpAddress.add(tfTown, 1, 2);
		gpAddress.add(lblState, 0, 3);
		gpAddress.add(tfState, 1, 3);
		gpAddress.add(lblZip, 0, 4);
		gpAddress.add(tfZip, 1, 4);
		
		gpAddress.setVgap(5);
		gpAddress.setHgap(50);
		gpAddress.setPadding(new Insets(5,5,5,5));
		
		HBox hbAddress = new HBox();
		hbAddress.getChildren().add(gpAddress);
		hbAddress.setAlignment(Pos.CENTER);
		
		Address address = new Address(tfStreetNumber.getText(), tfStreet.getText(), tfTown.getText(), tfState.getText(), tfZip.getText());
		
		///////////////////////////////////Address GridPane
		
		
		Button btnNext = new Button("Next");
		btnNext.setFont(new Font ("ALGERIAN", 30));
		btnNext.setOnAction(event ->{
			GridPane gridPane = new GridPane();
			gridPane.setPadding(new Insets(5));
			gridPane.setHgap(10);
			gridPane.setVgap(10);
			
			
			ColumnConstraints column1 = new ColumnConstraints(150, 150, Double.MAX_VALUE);
			ColumnConstraints column2 = new ColumnConstraints(35);
			ColumnConstraints column3 = new ColumnConstraints(150, 150, Double.MAX_VALUE);
			ColumnConstraints column4 = new ColumnConstraints(35);
			ColumnConstraints column5 = new ColumnConstraints(150, 150, Double.MAX_VALUE);
			
			column1.setHgrow(Priority.ALWAYS);
			column3.setHgrow(Priority.ALWAYS);
			column5.setHgrow(Priority.ALWAYS);
			
			gridPane.getColumnConstraints().addAll(column1, column2, column3, column4, column5);
			
			Label coursesToTakeLbl = new Label("Courses to Take");
			GridPane.setHalignment(coursesToTakeLbl,  HPos.CENTER);
			coursesToTakeLbl.setTextFill(Color.PURPLE);
			coursesToTakeLbl.setFont(new Font ("ALGERIAN", 16));
			gridPane.add(coursesToTakeLbl, 0, 0);
			
			Label coursesTakingLbl = new Label("Courses Taking");
			GridPane.setHalignment(coursesTakingLbl,  HPos.CENTER);
			coursesTakingLbl.setTextFill(Color.PURPLE);
			coursesTakingLbl.setFont(new Font ("ALGERIAN", 16));
			gridPane.add(coursesTakingLbl, 2, 0);
			
			Label coursesTakenLbl = new Label("Courses Taken");
			GridPane.setHalignment(coursesTakenLbl,  HPos.CENTER);
			coursesTakenLbl.setTextFill(Color.PURPLE);
			coursesTakenLbl.setFont(new Font ("ALGERIAN", 16));
			gridPane.add(coursesTakenLbl, 4, 0);
			
			String[] courses = {"CSE110", "CSE118", "CSE148", "CSE218", "CSE222", "CSE248"};
			
			ObservableList<String> coursesToTakeList = FXCollections.observableArrayList(Arrays.asList(courses));
			ListView<String> coursesToTakeListView = new ListView<>(coursesToTakeList);
			gridPane.add(coursesToTakeListView, 0, 1);
			
			ObservableList<String> coursesTakingList = FXCollections.observableArrayList(Arrays.asList());
			ListView<String> coursesTakingListView = new ListView<>(coursesTakingList);
			gridPane.add(coursesTakingListView, 2, 1);
			
			ObservableList<String> coursesTakenList = FXCollections.observableArrayList(Arrays.asList());
			ListView<String> coursesTakenListView = new ListView<>(coursesTakenList);
			gridPane.add(coursesTakenListView, 4, 1);
			
			Button sendRightButton = new Button("->");
			Button sendLeftButton = new Button("<-");
			
			Button sendRightButton1 = new Button("->");
			Button sendLeftButton1 = new Button("<-");
			
			sendRightButton.setOnAction(e -> {
				String potential = coursesToTakeListView.getSelectionModel().getSelectedItem();
				if(potential != null) {
					coursesToTakeListView.getSelectionModel().clearSelection();
					coursesToTakeList.remove(potential);
					coursesTakingList.add(potential);
				}
			});
			
			sendLeftButton.setOnAction(e -> {
				String potential = coursesTakingListView.getSelectionModel().getSelectedItem();
				if(potential != null) {
					coursesTakingListView.getSelectionModel().clearSelection();
					coursesTakingList.remove(potential);
					coursesToTakeList.add(potential);
				}
			});
			
			sendRightButton1.setOnAction(e -> {
				String potential = coursesTakingListView.getSelectionModel().getSelectedItem();
				if(potential != null) {
					coursesTakingListView.getSelectionModel().clearSelection();
					coursesTakingList.remove(potential);
					coursesTakenList.add(potential);
				}
			});
			
			sendLeftButton1.setOnAction(e -> {
				String potential = coursesTakenListView.getSelectionModel().getSelectedItem();
				if(potential != null) {
					coursesTakenListView.getSelectionModel().clearSelection();
					coursesTakenList.remove(potential);
					coursesTakingList.add(potential);
				}
			});
			
			Button btnSave = new Button("Save");
			btnSave.setFont(new Font ("ALGERIAN", 15));
			btnSave.setOnAction(e ->{
				Student s = new Student(tfFirstName.getText(), tfLastName.getText());
				//s.setAddress(address);
				s.setCoursesTaking(c);
				
				Demo.personbag.insert(s);
				System.out.println("Inserted Student: " + s.getLastName() + " " + s.getId());
			});
			VBox vbox = new VBox(5);
			VBox vbox2 = new VBox(5);
			VBox vbox3 = new VBox(5);
			vbox.getChildren().addAll(sendRightButton, sendLeftButton);
			vbox2.getChildren().addAll(sendRightButton1, sendLeftButton1);
			vbox3.getChildren().addAll(btnSave);
			
			gridPane.add(vbox, 1, 1);
			gridPane.add(vbox2, 3, 1);
			gridPane.add(vbox3, 4, 3);
			bpViewBox.setCenter(gridPane);
			BorderPane.setAlignment(btnSave, Pos.BOTTOM_RIGHT);
			GridPane.setVgrow(bpViewBox, Priority.ALWAYS);
			
			
		});
		
		hbName.getChildren().addAll(lblFirstName, tfFirstName, lblLastName, tfLastName);
		
		
		VBox.getChildren().addAll(lblNewStudent,hbName, hbCoursesTaking, lblCoursesTaking, /*hbCoursesTook, lblCoursesTook, hbCourseNeed
				, lblCoursesNeed*/ lblADDRESS, hbAddress, btnNext);
		VBox.setAlignment(Pos.CENTER);
		VBox.setSpacing(15);
		
		BorderPane.setAlignment(lblNewStudent, Pos.CENTER);
		BorderPane.setAlignment(lblADDRESS, Pos.CENTER);
		BorderPane.setAlignment(gpAddress, Pos.CENTER);
		
		Pane.setStyle("-fx-background-color:transparent;");
		Pane.setContent(VBox);
		return Pane;
	}
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	private Node nodeNewFaculty(){
		ScrollPane Pane = new ScrollPane();
		Pane.setFitToWidth(true);
		VBox VBox = new VBox();

		TextField tfFirstName = new TextField();
		tfFirstName.setPrefWidth(120);
		tfFirstName.setPrefHeight(30);
		TextField tfLastName = new TextField();
		tfLastName.setPrefWidth(120);
		tfLastName.setPrefHeight(30);
		Label lblFirstName = new Label("F.Name:");
		lblFirstName.setTextFill(Color.PURPLE);
		lblFirstName.setFont(new Font ("ALGERIAN", 28));
		Label lblLastName = new Label("L.Name:");
		lblLastName.setTextFill(Color.PURPLE);
		lblLastName.setFont(new Font ("ALGERIAN", 28));

		Label lblNewFaculty = new Label("New Faculty");
		lblNewFaculty.setTextFill(Color.PURPLE);
		lblNewFaculty.setFont(new Font ("ALGERIAN", 30));

		HBox hbName = new HBox();
		hbName.setSpacing(30);
		hbName.setPadding(new Insets(0,10,0,10));

		////////////////////////////////////
		Label lbladdCourse = new Label("Add Course teaching:");
		lbladdCourse.setTextFill(Color.PURPLE);
		lbladdCourse.setFont(new Font ("ALGERIAN", 18));
	//////////////LOOK HERE//////////////	
		final ComboBox<String> cbCourses = new ComboBox<String>(Demo.mastercoursebag.getCourses());
		ArrayList<String> c = new ArrayList<String>();

		Label lblCoursesTaking = new Label();

		Button addCourseTaking = new Button("Add");
		addCourseTaking.setFont(new Font ("ALGERIAN", 22));
		addCourseTaking.setOnAction(event ->{
			c.add(cbCourses.getValue());
			if(c.size() == 1){
				lblCoursesTaking.setText(cbCourses.getValue());
			}
			else{
				lblCoursesTaking.setText(lblCoursesTaking.getText() + ", " + cbCourses.getValue() );
			}	
		});
		//cbCourses.setPrefWidth(40);

		HBox hbCoursesTaking = new HBox();
		hbCoursesTaking.setSpacing(5);
		hbCoursesTaking.setAlignment(Pos.CENTER);
		hbCoursesTaking.getChildren().addAll(lbladdCourse, cbCourses, addCourseTaking);
		
		// cbCourses middle after bladd course
		//////////////////////////////////////

///////////////////////////////////////////////////////////////////////////////////////////////////////////

		//////////////////////////////////Creates Address GridPane
		GridPane gpAddress = new GridPane();

		Label lblADDRESS = new Label("Address");
		lblADDRESS.setTextFill(Color.PURPLE);
		lblADDRESS.setFont(new Font ("ALGERIAN", 22));
		

		Label lblStreetNumber = new Label("Street Number:");
		lblStreetNumber.setTextFill(Color.PURPLE);
		lblStreetNumber.setFont(new Font ("ALGERIAN", 22));	
		TextField tfStreetNumber = new TextField();


		Label lblStreet = new Label("Street:");
		lblStreet.setTextFill(Color.PURPLE);
		lblStreet.setFont(new Font ("ALGERIAN", 22));	
		TextField tfStreet = new TextField();


		Label lblTown = new Label("Town:");
		lblTown.setTextFill(Color.PURPLE);
		lblTown.setFont(new Font ("ALGERIAN", 22));	
		TextField tfTown = new TextField();

		Label lblState = new Label("State:");
		lblState.setTextFill(Color.PURPLE);
		lblState.setFont(new Font ("ALGERIAN", 22));	
		TextField tfState = new TextField();

		Label lblZip = new Label("Zip:");
		lblZip.setTextFill(Color.PURPLE);
		lblZip.setFont(new Font ("ALGERIAN", 22));	
		TextField tfZip = new TextField();


		gpAddress.add(lblStreetNumber, 0, 0);
		gpAddress.add(tfStreetNumber, 1, 0);
		gpAddress.add(lblStreet, 0, 1);
		gpAddress.add(tfStreet, 1, 1);
		gpAddress.add(lblTown, 0, 2);
		gpAddress.add(tfTown, 1, 2);
		gpAddress.add(lblState, 0, 3);
		gpAddress.add(tfState, 1, 3);
		gpAddress.add(lblZip, 0, 4);
		gpAddress.add(tfZip, 1, 4);

		gpAddress.setVgap(30);
		gpAddress.setHgap(10);
		gpAddress.setPadding(new Insets(5,5,5,5));

		HBox hbAddress = new HBox();
		hbAddress.getChildren().add(gpAddress);
		hbAddress.setAlignment(Pos.CENTER);

		Address address = new Address(tfStreetNumber.getText(), tfStreet.getText(), tfTown.getText(), tfState.getText(), tfZip.getText());

		///////////////////////////////////Address GridPane


		Button btnSave = new Button("Save");
		btnSave.setFont(new Font ("ALGERIAN", 22));
		
		btnSave.setOnAction(event ->{
			Faculty f = new Faculty(tfFirstName.getText(), tfLastName.getText());
			f.setAddress(address);
			f.setCoursesTeaching(c);
			Demo.personbag.insert(f);
			System.out.println("Inserted Faculty: " + f.getLastName() + " " + f.getId());


		});



		hbName.getChildren().addAll(lblFirstName, tfFirstName, lblLastName, tfLastName);


		VBox.getChildren().addAll(lblNewFaculty,hbName, hbCoursesTaking, lblCoursesTaking, lblADDRESS, hbAddress, btnSave);
		VBox.setAlignment(Pos.CENTER);
		VBox.setSpacing(5);

		BorderPane.setAlignment(lblNewFaculty, Pos.CENTER);
		BorderPane.setAlignment(lblADDRESS, Pos.CENTER);
		BorderPane.setAlignment(gpAddress, Pos.CENTER);

		Pane.setStyle("-fx-background-color:transparent;");
		Pane.setContent(VBox);
		return Pane;
	}
	
	private Node nodeNewTextbook(){
		BorderPane Pane = new BorderPane();
		
		VBox VBox = new VBox();
		//public Textbook(String bookISBN, String bookTitle, ArrayList<String> bookAuthors, double bookPrice) {
		
		Label lblbookISBN = new Label("ISBN:     ");
		lblbookISBN.setTextFill(Color.PURPLE);
		lblbookISBN.setFont(new Font ("ALGERIAN", 22));
		TextField tfbookISBN = new TextField();
		HBox hbbookISBN = new HBox();
		hbbookISBN.setSpacing(10);
		hbbookISBN.getChildren().addAll(lblbookISBN, tfbookISBN);
		hbbookISBN.setAlignment(Pos.CENTER);
		
		Label lblbooktitle = new Label("Title:     ");
		lblbooktitle.setTextFill(Color.PURPLE);
		lblbooktitle.setFont(new Font ("ALGERIAN", 22));
		TextField tfbooktitle = new TextField();
		HBox hbbooktitle = new HBox();
		hbbooktitle.setSpacing(10);
		hbbooktitle.getChildren().addAll(lblbooktitle, tfbooktitle);
		hbbooktitle.setAlignment(Pos.CENTER);
		
		Label lblauthor = new Label("Authors:");
		lblauthor.setTextFill(Color.PURPLE);
		lblauthor.setFont(new Font ("ALGERIAN", 22));
		TextField tfauthor = new TextField();
		HBox hbauthor = new HBox();
		hbauthor.setSpacing(10);
		hbauthor.getChildren().addAll(lblauthor, tfauthor);
		hbauthor.setAlignment(Pos.CENTER);
		
		Label lblprice = new Label("Price:    ");
		lblprice.setTextFill(Color.PURPLE);
		lblprice.setFont(new Font ("ALGERIAN", 22));
		TextField tfprice = new TextField();
		HBox hbprice = new HBox();
		hbprice.setSpacing(10);
		hbprice.getChildren().addAll(lblprice, tfprice);
		hbprice.setAlignment(Pos.CENTER);
		
		Button btnSave = new Button("Save");
		btnSave.setFont(new Font ("ALGERIAN", 22));
		
		ArrayList<String> s = new ArrayList<String>();
	
		VBox.setSpacing(75);
		VBox.getChildren().addAll(hbbookISBN,hbbooktitle,hbauthor,hbprice);
		
		
		
		btnSave.setOnAction(event ->{
			String[] authors = tfauthor.getText().split(",");
			for(String h: authors){
				s.add(h);
			}
			try {
				Double.parseDouble(tfprice.getText());
				Textbook t = new Textbook(tfbookISBN.getText(), tfbooktitle.getText(), s, Double.parseDouble(tfprice.getText()));
				System.out.println("Inserted new Textbook: " + t.getBookISBN() + ": by " + t.getBookAuthors().toString());
				Demo.textbookbag.add(t);
			} catch (NumberFormatException e) {
				System.out.println("Price format incorrect: " + e);
			}
			
		});
		
		Pane.setCenter(VBox);
		
		BorderPane.setAlignment(btnSave, Pos.BASELINE_RIGHT);
		Pane.setBottom(btnSave);
		
		return Pane;
		
		
	}

	private Node nodeNewCourse(){
		BorderPane Pane = new BorderPane();
		VBox vbox = new VBox();
		GridPane GP = new GridPane();
		GP.setHgap(5);
		GP.setVgap(10);
		
		
		Label lblcourseNumber = new Label("Course Number:");
		lblcourseNumber.setTextFill(Color.PURPLE);
		lblcourseNumber.setFont(new Font ("ALGERIAN", 22));
		TextField tfcourseNumber = new TextField();
		Label lblcourseTitle = new Label("Course Title:");
		lblcourseTitle.setTextFill(Color.PURPLE);
		lblcourseTitle.setFont(new Font ("ALGERIAN", 22));
		TextField tfcourseTitle= new TextField();
		Label lblDescription = new Label("Course Description:");
		lblDescription.setTextFill(Color.PURPLE);
		lblDescription.setFont(new Font ("ALGERIAN", 22));
		TextArea txtarea = new TextArea();
		txtarea.setFont(new Font ("Segoe Script", 18));
		Label lblcredits = new Label("Credits:");
		lblcredits.setTextFill(Color.PURPLE);
		lblcredits.setFont(new Font ("ALGERIAN", 22));
		TextField tfcredits = new TextField();
		Label lbltextbook = new Label("TextBooks:");
		lbltextbook.setTextFill(Color.PURPLE);
		lbltextbook.setFont(new Font ("ALGERIAN", 22));
		TextField tftextbook = new TextField();
		
		GP.add(lblcourseNumber, 0, 0);
		GP.add(tfcourseNumber, 1, 0);
		GP.add(lblcourseTitle, 0, 1);
		GP.add(tfcourseTitle, 1, 1);
		GP.add(lblcredits, 0, 2);
		GP.add(tfcredits, 1, 2);
		GP.add(lbltextbook, 0, 3);
		GP.add(tftextbook, 1, 3);
		GP.add(lblDescription, 0, 4);
		
		
		Button btnSave = new Button("Save");
		btnSave.setFont(new Font ("ALGERIAN", 15));
		
		btnSave.setOnAction(event ->{
			ArrayList<String> T = new ArrayList<String>();
			String[] textbooks = tftextbook.getText().split(",");

			try {
				if(Double.parseDouble(tfcredits.getText()) > 4.0 || Double.parseDouble(tfcredits.getText()) < 0.0){
					tfcredits.setText("Number Invalid");
				}
				else{
					for(String h: textbooks){
						T.add(h);
					}
					Course c = new Course(tfcourseNumber.getText(), tfcourseTitle.getText(), Double.parseDouble(tfcredits.getText()), txtarea.getText(), T);
					System.out.println("Inserted Course: \t" + c.getCourseNumber());
					Demo.mastercoursebag.insert(c);
					
				}
			} catch (NumberFormatException e) {
				System.out.println("Credits invalid number: ");
			} 
			
			
			
			
		});
		
		vbox.getChildren().addAll(GP,txtarea);
		
		Pane.setCenter(vbox);
		Pane.setBottom(btnSave);
		BorderPane.setAlignment(btnSave, Pos.BOTTOM_RIGHT);
		return Pane;
	}
	
	private Node deletePerson(){
		HBox hbID = new HBox();
		hbID.setSpacing(10);
		
		Label lblID = new Label("Enter ID");
		lblID.setTextFill(Color.PURPLE);
		lblID.setFont(new Font ("ALGERIAN", 15));
		//lblID.setTextFill(Color.WHITE);
		
		TextField tfID = new TextField();
		
		Button buttonSearch = new Button("Delete");
		buttonSearch.setOnAction(event -> {
			if (tfID.getText() != null){
				if (Demo.personbag.searchById(tfID.getText()) != null){
					Demo.personbag.delete(Demo.personbag.selectPerson(tfID.getText()));
				}else{
					System.out.println("Person not in Personbag.");
				}
			}
			
        });
		
		hbID.getChildren().addAll(lblID, tfID , buttonSearch);
		
		hbID.setAlignment(Pos.BASELINE_CENTER);
		
		return hbID;
	}
		
	private Node deleteCourse(){
		HBox hbID = new HBox();
		hbID.setSpacing(5);
		
		Label lblID = new Label("Enter Course Number");
		lblID.setTextFill(Color.PURPLE);
		lblID.setFont(new Font ("ALGERIAN", 15));
		//lblID.setTextFill(Color.WHITE);
		
		TextField tfID = new TextField();
		
		Button buttonSearch = new Button("Delete");
		buttonSearch.setOnAction(event -> {
			if (tfID.getText() != null){
				if (Demo.mastercoursebag.searchByCourseNumber(tfID.getText()) != null){
					Demo.mastercoursebag.delete(Demo.mastercoursebag.searchByCourseNumber(tfID.getText()));
				}else{
					System.out.println("Course not in Coursebag.");
				}
			}
			
        });
		
		hbID.getChildren().addAll(lblID, tfID , buttonSearch);
		
		hbID.setAlignment(Pos.BASELINE_CENTER);
		
		return hbID;
	}

	private Node deleteTextbook(){
		HBox hbID = new HBox();
		hbID.setSpacing(5);
		
		Label lblID = new Label("Enter Textbook ISBN");
		lblID.setTextFill(Color.PURPLE);
		lblID.setFont(new Font ("ALGERIAN", 15));
		//lblID.setTextFill(Color.WHITE);
		
		TextField tfID = new TextField();
		
		Button buttonSearch = new Button("Delete");
		buttonSearch.setOnAction(event -> {
			if (tfID.getText() != null){
				if (Demo.textbookbag.searchByBookISBN(tfID.getText()) != null){
					Demo.textbookbag.delete(Demo.textbookbag.searchByBookISBN(tfID.getText()));
				}else{
					System.out.println("Textbook not in BookBag.");
				}
			}
			
        });
		
		hbID.getChildren().addAll(lblID, tfID , buttonSearch);
		
		hbID.setAlignment(Pos.BASELINE_CENTER);
		
		return hbID;
	}
	
	
	private Node showTextbook(Textbook T){
		BorderPane node = new BorderPane();
		node.setPadding(new Insets(5,5,5,5));
		//Textbook(String bookISBN, String bookTitle, ArrayList<String> bookAuthors, double bookPrice)
		Label lblCourseNumber = new Label("ISBN: " + "T.getBookISBN()");
		Label lblTitle = new Label("Title: " + "T.getBookTitle()");
		Label lblAuthors = new Label("Authors: " + "T.getBookAuthors().toString().substring(1,T.getBookAuthors().toString().length()-2)");
		Label lblPrice = new Label("Price: " + "T.getBookPrice()");
		
		VBox VBox = new VBox();
		VBox.getChildren().addAll(lblCourseNumber,lblTitle, lblAuthors, lblPrice);
		
		
		node.setCenter(VBox);
		
		
		return node;
		
	}
	
}
