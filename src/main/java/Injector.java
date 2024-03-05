import interfaces.AutoInjectable;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Properties;

public class Injector {

    /**
     * Injects dependencies annotated with {@link AutoInjectable} into the fields of the given object.
     *
     * @param <T> the type of the object to inject dependencies into
     * @param objectToInject the object into which dependencies will be injected
     * @return the object with dependencies injected
     */
    public <T> T inject(T objectToInject) {
        Properties properties = loadProperties("src/main/properties/config.properties");
        Class<?> classToInject = objectToInject.getClass();
        Field[] fields = classToInject.getDeclaredFields();

        for (Field field : fields) {
            if (field.isAnnotationPresent(AutoInjectable.class)) {
                Class<?> fieldType = field.getType();

                String interfaceName = properties.getProperty(fieldType.getName());
                
                try {
                    Object implementation = Class.forName(interfaceName).newInstance();
                    field.setAccessible(true);
                    field.set(objectToInject, implementation);
                } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        return objectToInject;
    }

    private Properties loadProperties(String fileName) {
        Properties properties = new Properties();
        try (FileInputStream fileInputStream = new FileInputStream(fileName)) {
            properties.load(fileInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }
}