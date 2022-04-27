package edu.school21.reflection.orm;

import java.lang.reflect.Field;
import java.net.URI;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class OrmUtils {
    private static String schema;

    public static Set<Class> findAllClasses(String packageName, String tmp) {
        FileSystem system;
        Path path;
        Set<Class> classes = null;
        schema = tmp;

        try {
            URI uri = OrmUtils.class.getClassLoader()
                    .getResource(packageName.replaceAll("[.]", "/")).toURI();

            if (uri.getScheme().equals("jar")) {
                system = FileSystems.newFileSystem(uri, Collections.emptyMap());
                path = system.getPath(packageName.replaceAll("[.]", "/"));
            } else {
                path = Paths.get(uri);
            }
            Stream<Path> walk = Files.walk(path, 1);
            classes = walk.filter(s -> s.toString().endsWith(".class")).map(p -> {
                try {
                    String s = p.toString();
                    s = s.substring(s.lastIndexOf('/') + 1, s.lastIndexOf('.'));
                    return Class.forName(packageName + "." + s);
                } catch (ClassNotFoundException e) {
                    System.out.println("ClassNotFoundException: " + e.getMessage());
                }
                return null;
            }).filter(Objects::nonNull).collect(Collectors.toSet());
            walk.close();
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
        return Optional.ofNullable(classes).orElse(new HashSet<>());
    }

    public static String createTableQuery(OrmEntity ormEntity, List<Field> fields) {
        OrmColumn annotation;
        StringBuilder stBuilder = new StringBuilder("create table " + schema
                + "." + ormEntity.table() + " (");

        if (!fields.stream().map(f -> f.getAnnotation(OrmColumnId.class))
                .filter(Objects::nonNull).collect(Collectors.toList()).isEmpty()) {
            stBuilder.append("id serial primary key, ");
        }

        for (Field field : fields) {
            if ((annotation = field.getAnnotation(OrmColumn.class)) != null) {
                String name = field.getType().getName();
                stBuilder.append(annotation.name());

                if (name.contains("String")) {
                    stBuilder.append(" varchar(" + annotation.length() + "), ");
                } else if (name.contains("Integer")) {
                    stBuilder.append(" integer, ");
                } else if (name.contains("Long")) {
                    stBuilder.append(" bigint, ");
                } else if (name.contains("Double")) {
                    stBuilder.append(" double precision, ");
                } else if (name.contains("Boolean")) {
                    stBuilder.append(" boolean, ");
                } else {
                    throw new RuntimeException("UNKNOWN TYPE: " + name);
                }
            }
        }

        if (stBuilder.lastIndexOf(",") == -1) {
            return stBuilder + ");";
        }
        return stBuilder.deleteCharAt(stBuilder.lastIndexOf(",")) + ");";
    }
}
