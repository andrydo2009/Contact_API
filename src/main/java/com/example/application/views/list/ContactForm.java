package com.example.application.views.list;

import com.example.application.data.Company;
import com.example.application.data.Status;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;

import java.util.List;

public class ContactForm extends FormLayout { //1
    TextField firstName = new TextField("First name"); //2
    TextField lastName = new TextField("Last name");
    EmailField email = new EmailField("Email");
    ComboBox<Status> status = new ComboBox<>("Status");
    ComboBox<Company> company = new ComboBox<>("Company");

    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button close = new Button("Cancel");

    public ContactForm(List<Company> companies, List<Status> statuses) {
        addClassName("contact-form"); //3

        company.setItems(companies);
        company.setItemLabelGenerator(Company::getName);
        status.setItems(statuses);
        status.setItemLabelGenerator(Status::getName);

        add(firstName, //4
                lastName,
                email,
                company,
                status,
                createButtonsLayout());
    }

    private HorizontalLayout createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY); //5
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER); //6
        close.addClickShortcut(Key.ESCAPE);

        return new HorizontalLayout(save, delete, close); //7
    }
}
/*
1 ContactFormрасширяет FormLayout: адаптивный макет, который показывает поля формы в одном или двух столбцах,
в зависимости от ширины видового поля.
2 Создает все компоненты пользовательского интерфейса в виде полей в компоненте.
3 Дает компоненту имя класса CSS, чтобы вы могли стилизовать его позже.
4 Добавляет все компоненты пользовательского интерфейса в макет.
Кнопки требуют немного дополнительной конфигурации. Создайте и вызовите новый метод createButtonsLayout().
5 Делает кнопки визуально отличными друг от друга с помощью встроенных вариантов темы.
6 Определяет сочетания клавиш: Enter для сохранения и Escape для закрытия редактора.
7 Возвращает HorizontalLayout, содержащий кнопки для размещения их рядом друг с другом.
*/
/*
FormLayout - это контейнерный компонент в Vaadin, который используется для организации элементов формы,
таких как метки и поля для ввода, в виде сетки.
Он предоставляет удобные возможности для компоновки элементов ввода и соответствующих меток в формате лэйбл-поле.
*/