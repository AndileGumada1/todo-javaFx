module com.andile.todolist {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;

    opens com.andile.todolist to javafx.fxml;
    exports com.andile.todolist;
    exports com.andile.todolist.controller;
    opens com.andile.todolist.controller to javafx.fxml;
    exports com.andile.todolist.utils;
    opens com.andile.todolist.utils to javafx.fxml;

    requires org.eclipse.collections.api;
    requires org.eclipse.collections.impl;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.datatype.jsr310;
    requires jackson.datatype.eclipse.collections;
}