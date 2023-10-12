package com.example.application.views.list;

import com.example.application.data.Contact;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "")
@PageTitle("Contacts | Vaadin CRM")
public class ListView extends VerticalLayout { // 1
    Grid<Contact> grid = new Grid<>(Contact.class); //2
    TextField filterText = new TextField();

    public ListView() {
        addClassName("list-view"); //10
        setSizeFull();
        configureGrid(); //3

        add(getToolbar(), grid); //4
    }

    private void configureGrid() {
        grid.addClassNames("contact-grid"); //10
        grid.setSizeFull();
        grid.setColumns("firstName", "lastName", "email"); //5
        grid.addColumn(contact -> contact.getStatus().getName()).setHeader("Status"); //6
        grid.addColumn(contact -> contact.getCompany().getName()).setHeader("Company");
        grid.getColumns().forEach(col -> col.setAutoWidth(true)); //7
    }

    private HorizontalLayout getToolbar() {
        filterText.setPlaceholder("Filter by name...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY); //8

        Button addContactButton = new Button("Add contact");

        var toolbar = new HorizontalLayout(filterText, addContactButton); //9
        toolbar.addClassName("toolbar"); //10
        return toolbar;
    }
}
/* 1 Представление расширяет VerticalLayout, который размещает все дочерние компоненты вертикально.
   2 Компонент Grid набирается с помощью Contact.
   3 Конфигурация сетки извлекается в отдельный метод, чтобы конструктор был легче читаемым.
   4 Добавьте панель инструментов и сетку в VerticalLayout.
   5 Определите, какие свойства Contact должна отображаться сеткой.
   6 Определите пользовательские столбцы для вложенных объектов.
   7 Настройте столбцы, чтобы они автоматически настраивались в соответствии с их содержимым.
   8 Настройте поле поиска так, чтобы зажечь события изменения значения только тогда, когда пользователь перестает печатать. Таким образом, вы избегаете ненужных вызовов базы данных, но слушатель все равно запускается, не покидая фокус пользователя из поля.
   9 Панель инструментов использует HorizontalLayout для размещения TextField и Button рядом друг с другом.
   10 Добавление некоторых имен классов в компоненты облегчает стилизацию приложения позже с помощью CSS.
*/

/*
* Метод setSizeFull() задает полный размер компонента в Vaadin,
* растягивая его на всю доступную область. В приведенном коде это реализовано путем установки ширины
* и высоты компонента на "100%" с помощью методов setWidth() и setHeight().
* Обычно этот метод вызывается для контейнеров (Container),
* таких, как VerticalLayout, HorizontalLayout, GridLayout и других,
* чтобы занять всю доступную область в родительском компоненте.
 */

/*
Установка autoWidth для столбца в значении true означает,
что ширина столбца будет автоматически настраиваться в зависимости от содержимого ячеек в этом столбце.
 */