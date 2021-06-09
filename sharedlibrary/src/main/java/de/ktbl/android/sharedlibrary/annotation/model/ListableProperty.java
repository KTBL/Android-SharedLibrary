package de.ktbl.android.sharedlibrary.annotation.model;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ListableProperty {
    /**
     * The {@code defaultValueProvider} is used if a value was modified by a user
     * and needs to be reset to the default value.
     * This parameter must be filled if {@link #editable()} is set to {@code true}.
     *
     * @see #editable()
     * @see DefaultValueProvider
     */
    Class<? extends DefaultValueProvider<?>> defaultValueProvider() default
            DefaultValueProviderDefault.class;

    /**
     * As alternative to {@link #descriptionResourceID()} a field of <b>this</b>
     * class can be used. To do so the variable name can be given to this parameter.
     * If bot, {@link #descriptionResourceID()} and {@link #descriptionFieldName()},
     * are given {@link #descriptionResourceID()} is prefered.
     *
     * @see #descriptionResourceID()
     */
    String descriptionFieldName() default "";

    /**
     * Used to link a {@code R.string.what_ever_string}-id as description for the
     * annotated field. Alternatively {@link #descriptionFieldName()} can be used.
     *
     * @see #descriptionFieldName()
     */
    int descriptionResourceID() default Integer.MIN_VALUE;

    /**
     * If the annotated field should be user modifiable this must be set to
     * {@code true}. To provide a rest functionality a {@link #defaultValueProvider()}
     * must be provided.
     *
     * @see #defaultValueProvider()
     */
    boolean editable() default false;

    /**
     * As alternative to {@link #unitResourceID()} a field of <b>this</b> class
     * can be used. To do so the variable name can be given to this parameter.
     * If both, {@link #unitResourceID()} and {@link #unitResourceFieldName()}, are
     * given {@link #unitResourceID()} is prefered.
     *
     * @see #unitResourceID()
     */
    String unitResourceFieldName() default "";

    /**
     * Used to link a {@code R.string.what_ever-string]-id as a unit for the annotated
     * field. Alternatively {@link #unitResourceFieldName()} can be used.
     *
     * @see #unitResourceFieldName
     */
    int unitResourceID() default Integer.MIN_VALUE;

    /**
     * Formatstring which will be used to verify user input against, if this
     * field can be edited by the user.
     *
     * @see #editable()
     */
    String valueFormatString() default "";
}

class DefaultValueProviderDefault implements DefaultValueProvider<Object> {

    @Override
    public Object getDefaultValue(Long id) {
        return null;
    }
}