package de.ktbl.android.sharedlibrary.annotation.model;

import android.content.Context;
import android.util.Log;
import android.util.Pair;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import de.ktbl.android.sharedlibrary.R;

import static de.ktbl.android.sharedlibrary.util.IfDebug.ifDebugElse;


public class ListablePropertyExtractor {

    private static final String tag = ListablePropertyExtractor.class.getSimpleName();

    private ListablePropertyExtractor() {
        // nö, ich seh gar nicht ein irgendwas zu tun!
    }

    public static <T> List<ListablePropertyModel> extractProperties(T instance, Class<T> clazz,
                                                                    Context context) {
        List<ListablePropertyModel> extractedModels = new ArrayList<>();
        List<Pair<Field, ListableProperty>> extractedAnnotation = extractAnnotatedFields(clazz);
        for (Pair<Field, ListableProperty> pair : extractedAnnotation) {
            Field field = pair.first;
            field.setAccessible(true);
            ListableProperty annotation = pair.second;
            ListablePropertyModel model = buildModel(clazz, instance, field, annotation, context);
            extractedModels.add(model);
        }

        return extractedModels;
    }

    private static <T> ListablePropertyModel buildModel(Class<T> clazz, T instance, Field field,
                                                        ListableProperty annotation,
                                                        Context context) {
        ListablePropertyModel model = new ListablePropertyModel();
        model.setPropertyName(field.getName());
        model.setPropertyType(field.getType());
        model.setFormatString(annotation.valueFormatString());
        model.setEditable(annotation.editable());
        model.setUnit(getUnit(clazz, annotation, instance, context));
        model.setDescription(getDescription(clazz, annotation, instance, context));
        model.setValue(getValueAsString(field, instance, context));
        return model;
    }

    private static <T> List<Pair<Field, ListableProperty>> extractAnnotatedFields(Class<T> clazz) {
        List<Pair<Field, ListableProperty>> annotatedFields = new ArrayList<>();
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            ListableProperty property = field.getAnnotation(ListableProperty.class);
            if (property != null) {
                Pair<Field, ListableProperty> p = Pair.create(field, property);
                annotatedFields.add(p);
            }
        }
        return annotatedFields;
    }

    private static <T> String getDescription(Class<T> clazz, ListableProperty annotation,
                                             T instance, Context context) {
        if (annotation.descriptionResourceID() != Integer.MIN_VALUE) {
            return context.getResources().getString(annotation.descriptionResourceID());
        } else {
            return getStringFieldValue(clazz, instance, annotation.descriptionFieldName());
        }
    }

    private static <T> String getStringFieldValue(Class<T> clazz, T instance, String fieldname) {
        String value = ifDebugElse("Field Not Found Or No Access", " - ");

        try {
            // This case happens for example if there is no unit and therefore the fieldname is
            // equal to ""
            if (fieldname.trim().length() == 0) {
                return "";
            }
            Field field = clazz.getDeclaredField(fieldname);
            field.setAccessible(true);
            if (field.getType().equals(String.class)) {
                value = (String) field.get(instance);
            } else {
                value = ifDebugElse("Field Not A String", " UNK ");
            }

        } catch (IllegalAccessException | NoSuchFieldException e) {
            Log.e(tag, String.format("Could not access field %s of class %s", fieldname,
                                     clazz.getSimpleName()), e);
        }
        return value;
    }

    private static <T> String getUnit(Class<T> clazz, ListableProperty annotation, T instance,
                                      Context context) {
        if (annotation.unitResourceID() != Integer.MIN_VALUE) {
            return context.getResources().getString(annotation.unitResourceID());
        } else {
            return getStringFieldValue(clazz, instance, annotation.unitResourceFieldName());
        }
    }

    private static <T> String getValueAsString(Field field, T instance, Context context) {
        String value = ifDebugElse("Field Not Accessible", " - ");
        try {
            Object objValue = field.get(instance);
            Class<?> fieldType = field.getType();
            if (Number.class.isAssignableFrom(fieldType)) {
                Number number = 0f;
                if (objValue != null) {
                    number = (Number) objValue;
                }
                // TODO: Formatstring must be properly implemented
                value = String.format("%.2f", number);
            } else if (Boolean.class.isAssignableFrom(fieldType)) {
                if (objValue == null || (Boolean) objValue) {
                    value = context.getResources().getString(R.string.yes);
                } else {
                    value = context.getResources().getString(R.string.no);
                }
            } else if (String.class.isAssignableFrom(fieldType)) {
                if (objValue == null) {
                    value = "";
                } else {
                    value = (String) objValue;
                }
            } else {
                value = ifDebugElse("Unkown Field Type", " UNK ");
            }
        } catch (IllegalAccessException e) {
            Log.e(tag, String.format("Could not access field %s.", field.getName()), e);
        }
        return value;
    }

}
