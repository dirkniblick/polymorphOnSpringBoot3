package com.example.hibernatepolymorph;

import com.example.hibernatepolymorph.entity.IntegerProperty;
import com.example.hibernatepolymorph.repository.IntegerPropertyRepository;
import com.example.hibernatepolymorph.entity.Property;
import com.example.hibernatepolymorph.entity.PropertyHolder;
import com.example.hibernatepolymorph.repository.PropertyHolderRepository;
import com.example.hibernatepolymorph.entity.PropertyRepository;
import com.example.hibernatepolymorph.repository.PropertyRepositoryRepository;
import com.example.hibernatepolymorph.entity.StringProperty;
import com.example.hibernatepolymorph.repository.StringPropertyRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class HibernatePolymorphApplicationTests {

    @Autowired
    IntegerPropertyRepository integerPropertyRepository;
    @Autowired
    StringPropertyRepository stringPropertyRepository;
    @Autowired
    private PropertyHolderRepository propertyHolderRepository;
    @Autowired
    private PropertyRepositoryRepository propertyRepositoryRepository;

    @Test
    @Order(1)
    void createPropertyHolder() {
        IntegerProperty ageProperty = new IntegerProperty();
        ageProperty.setId(1L);
        ageProperty.setName("age");
        ageProperty.setValue(23);

        integerPropertyRepository.saveAndFlush(ageProperty);
        System.out.printf("Created: %s%n", ageProperty);

        StringProperty nameProperty = new StringProperty();
        nameProperty.setId(1L);
        nameProperty.setName("name");
        nameProperty.setValue("John Doe");

        stringPropertyRepository.saveAndFlush(nameProperty);
        System.out.printf("Created: %s%n", nameProperty);

        PropertyHolder namePropertyHolder = new PropertyHolder();
        namePropertyHolder.setId(1L);
        namePropertyHolder.setProperty(nameProperty);

        propertyHolderRepository.saveAndFlush(namePropertyHolder);
        System.out.printf("Created: %s%n", namePropertyHolder);

        assertThat(namePropertyHolder.getId()).isNotNull();

        PropertyHolder propertyHolder = propertyHolderRepository.findById(1L).orElseThrow();
        System.out.printf("Retrieved: %s%n", propertyHolder);

        assertThat(propertyHolder.getProperty().getName()).isEqualTo("name");
        assertThat(propertyHolder.getProperty().getValue()).isEqualTo("John Doe");
    }

    @Test
    @Order(2)
    void createPropertyRepository() {
        IntegerProperty ageProperty = new IntegerProperty();
        ageProperty.setId(2L);
        ageProperty.setName("age");
        ageProperty.setValue(23);

        integerPropertyRepository.saveAndFlush(ageProperty);
        System.out.printf("Created: %s%n", ageProperty);

        StringProperty nameProperty = new StringProperty();
        nameProperty.setId(2L);
        nameProperty.setName("name");
        nameProperty.setValue("John Doe");

        stringPropertyRepository.saveAndFlush(nameProperty);
        System.out.printf("Created: %s%n", nameProperty);

        PropertyRepository propertyRepository = new PropertyRepository();
        propertyRepository.setId(1L);

        propertyRepository.getProperties().add(ageProperty);
        propertyRepository.getProperties().add(nameProperty);

        propertyRepositoryRepository.saveAndFlush(propertyRepository);
        System.out.printf("Created: %s%n", propertyRepository);

        assertThat(propertyRepository.getId()).isNotNull();

        PropertyRepository retrievedPropertyRepository = propertyRepositoryRepository.findById(1L).orElseThrow();
        System.out.printf("Retrieved: %s%n", retrievedPropertyRepository);

        assertThat(retrievedPropertyRepository.getProperties()).hasSize(2);

        for(Property<?> property : retrievedPropertyRepository.getProperties()) {
            assertThat(property.getValue()).isNotNull();
        }
    }
}
