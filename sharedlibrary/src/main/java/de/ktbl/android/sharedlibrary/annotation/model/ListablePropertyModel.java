package de.ktbl.android.sharedlibrary.annotation.model;

import android.util.Log;

import androidx.annotation.Nullable;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class ListablePropertyModel {
    private static final String tag = ListablePropertyModel.class.getSimpleName();

    private String description;
    private String formatString;
    private boolean isEditable;
    private String propertyName;
    private Class<?> propertyType;
    private String unit;
    private String value;

    /**
     * @param description  provided description of this property. This description will be
     *                     displayed to the user.
     * @param formatString provided formatstring to verify user input against.
     * @param propertyType type of the extracted property.
     * @param unit         provided unit of this property. Will be displyed to the user.
     * @param value        value at extraction time of the property as {@link String}, so it's
     *                     displayable to the user.
     * @param isEditable   if {@code true} the user will be able to modify the property's content
     *                     and it will be written back in to the field of extraction.
     * @param propertyName name of the propery within the class it was extracted from.
     */
    public ListablePropertyModel(String description, @Nullable String formatString,
                                 Class<?> propertyType, @Nullable String unit, String value,
                                 boolean isEditable, String propertyName) {
        this.isEditable = isEditable;
        this.setDescription(description);
        this.setFormatString(formatString);
        this.setPropertyType(propertyType);
        this.setUnit(unit);
        this.setValue(value);
        this.setPropertyName(propertyName);

    }

    ListablePropertyModel() {
        // Since setters are only accessible on package level this
        // Constructor needs also only be accessible on package level
    }


    public Object asPropertyType() {
        Object prop = null;
        try {
            Constructor<?> constructor = this.propertyType.getConstructor(String.class);
            prop = constructor.newInstance(this.value);
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            Log.e(tag, String.format("Could not transform %s to object of type %s. Maybe there's " +
                                     "no string constructor?", this.value,
                                     this.propertyType.getSimpleName()), e);
        }
        return prop;
    }

    public String getDescription() {
        return description;
    }

    public String getPropertyName() {
        return this.propertyName;
    }

    public String getFormatString() {
        return formatString;
    }

    void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public boolean isEditable() {
        return isEditable;
    }

    void setEditable(boolean editable) {
        isEditable = editable;
    }

    public Class<?> getPropertyType() {
        return propertyType;
    }

    void setDescription(String description) {
        this.description = description;
    }

    public String getUnit() {
        return unit;
    }

    void setFormatString(String formatString) {
        this.formatString = formatString;
    }

    public String getValue() {
        return value;
    }

    void setPropertyType(Class<?> propertyType) {
        this.propertyType = propertyType;
    }

    void setUnit(String unit) {
        this.unit = unit;
    }

    void setValue(String value) {
        this.value = value;
    }
}
