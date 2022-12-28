package com.andile.todolist.controller;

import com.andile.todolist.utils.TodoCategory;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.eclipsecollections.EclipseCollectionsModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.factory.Lists;

import javafx.fxml.FXML;
import org.eclipse.collections.impl.collector.Collectors2;

import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.stream.Collectors;

public class TodoListController {
    @FXML
    private TextField todoItem;
    @FXML
    private DatePicker todoDate;
    @FXML
    private TableView<ToDoItem> todoList;
    @FXML
    public ComboBox<TodoCategory> todoCategory;
    @FXML
    private void initialize(){
        this.todoList.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        MutableList<ToDoItem> items= Lists.mutable.empty();
        ObservableList<ToDoItem> list = FXCollections.observableList(items);
        this.todoList.setItems(list);
        ObservableList<TodoCategory> categories =
                FXCollections.observableList(Lists.mutable.with(TodoCategory.values()));
        this.todoCategory.setItems(categories);
    }
    @FXML
    protected void onAddButtonClick() {
        String text = this.todoItem.getText();
        TodoCategory category = this.todoCategory.getValue();
        LocalDate date = this.todoDate.getValue();
        if (text == null || category == null || date ==null){
            this.displayInvalidInputMessage();
        }else {
            this.createdAndAddTodoItem(text,category,date);
        }
    }

    private void displayInvalidInputMessage() {
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setHeaderText("Invalid input");
        errorAlert.setContentText("Text, category and date must all be specified!");
        errorAlert.showAndWait();
    }

    private void createdAndAddTodoItem(String text, TodoCategory category, LocalDate date) {
        ToDoItem item = new ToDoItem(text, category, date);
        this.todoList.getItems().add(item);
    }


    @FXML
    protected void onRemoveButtonClick()
    {
        int indexToRemove = this.todoList.getSelectionModel().getSelectedIndex();
        this.todoList.getItems().remove(indexToRemove);
    }
    public record ToDoItem(@JsonProperty String name,@JsonProperty TodoCategory category,@JsonProperty LocalDate date){
        @JsonIgnore
        public String getCategory() {
            return this.category.getEmoji();
        }
        @JsonIgnore
        public String getName() {
            return this.name;
        }
        @JsonIgnore
        public String getDate() {
            return this.date.toString();
        }
    }
    private MutableList<ToDoItem> readTodoListFromFile(){
        ObjectMapper mapper = this.getObjectMapper();
        MutableList<ToDoItem> list;
        try {
            list = mapper.readValue(
                    Paths.get("todolist.json").toFile(),
                    new TypeReference<MutableList<ToDoItem>>() {});
            return list;
        }catch (IOException e){
            System.out.println(e);
        }
        return Lists.mutable.empty();
    }

    public void writeTodoListToFile() {
       ObjectMapper mapper = this.getObjectMapper();
       MutableList<ToDoItem> list = this.todoList.getItems().stream()
               .collect(Collectors2.toList());
        try {
            mapper.writeValue(
                    Paths.get("todolist.json").toFile(),
                    list);
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    private ObjectMapper getObjectMapper() {
        ObjectMapper mapper = new ObjectMapper()
                .registerModule(new EclipseCollectionsModule())
                .registerModule(new JavaTimeModule());
        return mapper;
    }
}