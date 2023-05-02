package com.example.hibernatepolymorph;

import com.example.hibernatepolymorph.entity.IntegerProperty;
import com.example.hibernatepolymorph.entity.IntegerPropertyRepository;
import com.example.hibernatepolymorph.entity.Property;
import com.example.hibernatepolymorph.entity.PropertyHolder;
import com.example.hibernatepolymorph.entity.PropertyHolderRepository;
import com.example.hibernatepolymorph.entity.PropertyRepository;
import com.example.hibernatepolymorph.entity.PropertyRepositoryRepository;
import com.example.hibernatepolymorph.entity.StringProperty;
import com.example.hibernatepolymorph.entity.StringPropertyRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class HibernatePolymorphApplicationTests {

    @Autowired
    IntegerPropertyRepository integerPropertyRepository;
    @Autowired
    StringPropertyRepository stringPropertyRepository;
    @Autowired
    private PropertyHolderRepository propertyHolderRepository;
    @Autowired
    private PropertyRepositoryRepository propertyRepositoryRepository;

    IntegerProperty ageProperty;
    StringProperty nameProperty;

    @Test
    void createPropertyHolder() {
        ageProperty = new IntegerProperty();
        ageProperty.setId(1L);
        ageProperty.setName("age");
        ageProperty.setValue(23);

        integerPropertyRepository.save(ageProperty);
        System.out.printf("Created: %s%n", ageProperty);

        nameProperty = new StringProperty();
        nameProperty.setId(1L);
        nameProperty.setName("name");
        nameProperty.setValue("John Doe");

        stringPropertyRepository.save(nameProperty);
        System.out.printf("Created: %s%n", nameProperty);

        PropertyHolder namePropertyHolder = new PropertyHolder();
        namePropertyHolder.setId(1L);
        namePropertyHolder.setProperty(nameProperty);

        propertyHolderRepository.save(namePropertyHolder);
        System.out.printf("Created: %s%n", namePropertyHolder);

        assertThat(namePropertyHolder.getId()).isNotNull();

        PropertyHolder propertyHolder = propertyHolderRepository.getReferenceById(1L);
        System.out.printf("Retrieved: %s%n", propertyHolder);

        assertThat(propertyHolder.getProperty().getName()).isEqualTo("name");
        assertThat(propertyHolder.getProperty().getValue()).isEqualTo("John Doe");
    }

    @Test
    void createPropertyRepository() {
        ageProperty = new IntegerProperty();
        ageProperty.setId(1L);
        ageProperty.setName("age");
        ageProperty.setValue(23);

        integerPropertyRepository.save(ageProperty);
        System.out.printf("Created: %s%n", ageProperty);

        nameProperty = new StringProperty();
        nameProperty.setId(1L);
        nameProperty.setName("name");
        nameProperty.setValue("John Doe");

        stringPropertyRepository.save(nameProperty);
        System.out.printf("Created: %s%n", nameProperty);

        PropertyRepository propertyRepository = new PropertyRepository();
        propertyRepository.setId(1L);

        propertyRepository.getProperties().add(ageProperty);
        propertyRepository.getProperties().add(nameProperty);

        propertyRepositoryRepository.save(propertyRepository);
        System.out.printf("Created: %s%n", propertyRepository);

        assertThat(propertyRepository.getId()).isNotNull();

        PropertyRepository retrievedPropertyRepository = propertyRepositoryRepository.getReferenceById(1L);
        System.out.printf("Retrieved: %s%n", retrievedPropertyRepository);

        assertThat(retrievedPropertyRepository.getProperties()).hasSize(2);

        for(Property<?> property : retrievedPropertyRepository.getProperties()) {
            assertThat(property.getValue()).isNotNull();
        }
    }
}
