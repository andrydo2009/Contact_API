package com.example.application.views.list;

import com.example.application.data.Company;
import com.example.application.data.Contact;
import com.example.application.data.Status;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.shared.Registration;

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
    BeanValidationBinder<Contact> binder = new BeanValidationBinder<>(Contact.class);//8

    public void setContact(Contact contact) { //10
        binder.setBean(contact);
    }

    public ContactForm(List<Company> companies, List<Status> statuses) {
        addClassName("contact-form"); //3
        binder.bindInstanceFields(this);//9

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

        save.addClickListener(event -> validateAndSave()); //13 Кнопка сохранения вызывает метод validateAndSave().
        delete.addClickListener(event -> fireEvent(new DeleteEvent(this, binder.getBean()))); //14 Кнопка удаления запускает событие удаления и передает активный контакт.
        close.addClickListener(event -> fireEvent(new CloseEvent(this))); //15 Кнопка отмены закрывает событие закрытия.

        binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid())); //16 Проверяет форму каждый раз, когда она меняется. Если он недействителен, он отключает кнопку сохранения, чтобы избежать недействительных сообщений.

        return new HorizontalLayout(save, delete, close); //7

    }

    private void validateAndSave() {
        if (binder.isValid()) {
            fireEvent(new SaveEvent(this, binder.getBean())); //17 Заработойте событие сохранения, чтобы родительский компонент мог справиться с действием.
        }
    }

    public static abstract class ContactFormEvent extends ComponentEvent<ContactForm> {
        private Contact contact;

        protected ContactFormEvent(ContactForm source, Contact contact) { //11
            super(source, false);
            this.contact = contact;
        }

        public Contact getContact() {
            return contact;
        }
    }

    public static class SaveEvent extends ContactFormEvent {
        SaveEvent(ContactForm source, Contact contact) {
            super(source, contact);
        }
    }

    public static class DeleteEvent extends ContactFormEvent {
        DeleteEvent(ContactForm source, Contact contact) {
            super(source, contact);
        }

    }

    public static class CloseEvent extends ContactFormEvent {
        CloseEvent(ContactForm source) {
            super(source, null);
        }
    }

    public Registration addDeleteListener(ComponentEventListener<DeleteEvent> listener) { //12
        return addListener(DeleteEvent.class, listener);
    }

    public Registration addSaveListener(ComponentEventListener<SaveEvent> listener) {
        return addListener(SaveEvent.class, listener);
    }

    public Registration addCloseListener(ComponentEventListener<CloseEvent> listener) {
        return addListener(CloseEvent.class, listener);
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
8 BeanValidationBinderэто Binder, который знает об аннотациях проверки бобов.
  Передавая его в Contact.class, вы определяете тип объекта, к которому вы привязываетесь.
9 bindInstanceFields() сопоставляет поля в Contact и ContactForm на основе их имен.
10 Вызывает binder.setBean(), чтобы связать значения из контакта с полями пользовательского интерфейса.
Метод также добавляет слушателей изменения значения для обновления изменений в пользовательском
интерфейсе обратно в объект домена.
11 ContactFormEvent это общий суперкласс для всех событий. Он содержит contact, который был отредактирован или удален.
12 Методы add*Listener(), которые передают типизированный тип события в шину событий Vaadin
  для регистрации пользовательских типов событий.
  Выберите импорт com.vaadin для регистрации, если IntelliJ спросит.
*/
/*
FormLayout - это контейнерный компонент в Vaadin, который используется для организации элементов формы,
таких как метки и поля для ввода, в виде сетки.
Он предоставляет удобные возможности для компоновки элементов ввода и соответствующих меток в формате лэйбл-поле.
*/