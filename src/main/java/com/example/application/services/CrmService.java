package com.example.application.services;

import com.example.application.data.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service//1
public class CrmService {

    private final ContactRepository contactRepository;
    private final CompanyRepository companyRepository;
    private final StatusRepository statusRepository;

    public CrmService(ContactRepository contactRepository,
                      CompanyRepository companyRepository,
                      StatusRepository statusRepository) { //2
        this.contactRepository = contactRepository;
        this.companyRepository = companyRepository;
        this.statusRepository = statusRepository;
    }

    public List<Contact> findAllContacts(String stringFilter) {
        if (stringFilter == null || stringFilter.isEmpty()) { //3
            return contactRepository.findAll();
        } else {
            return contactRepository.search(stringFilter);
        }
    }

    public long countContacts() {
        return contactRepository.count();
    }

    public void deleteContact(Contact contact) {
        contactRepository.delete(contact);
    }

    public void saveContact(Contact contact) {
        if (contact == null) { //4
            System.err.println("Contact is null. Are you sure you have connected your form to the application?");
            return;
        }
        contactRepository.save(contact);
    }

    public List<Company> findAllCompanies() {
        return companyRepository.findAll();
    }

    public List<Status> findAllStatuses(){
        return statusRepository.findAll();
    }
}
/*
*  1 Аннотация @Service делает эту службу управляемой Spring, которую вы можете внедрить в свое представление.
   2 Используйте инъекцию конструктора Spring для автоматического подключения репозиториев базы данных.
   3 Проверьте, есть ли активный фильтр: верните либо все контакты, либо используйте репозиторий
   для фильтрации на основе строки.
   4 Классы услуг часто включают в себя проверку и другие бизнес-правила перед сохранением данных.
   Здесь вы проверяете, что не пытаетесь сохранить null объект.*/