package de.ktbl.android.sharedlibrary.annotation.mock;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Mock {

    int lowerValueBound() default 1;

    int upperValueBound() default 10;

    Type type() default Type.STRING;

    String customValue() default "€";

    /**
     * Only List<> is currently supported
     *
     * @return
     */
    boolean isList() default false;

    int lowerListBound() default 1;

    int upperListBound() default 10;

    Class<?> mockType() default Object.class;

    enum Type {
        STRING,
        INT,
        DOUBLE,
        UNIT,
        CUSTOM,
        MOCK
    }
}
