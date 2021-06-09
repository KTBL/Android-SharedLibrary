package de.ktbl.android.sharedlibrary.annotation.mock;


import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.thedeanda.lorem.LoremIpsum;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import timber.log.Timber;

public class MockCreator {
    private static final String tag = MockCreator.class.getSimpleName();

    private MockCreator() {
        // no. I'm just here for cleaning
    }

    private static final LoremIpsum ipsum = LoremIpsum.getInstance();
    private static final Random random = new Random();

    public static <T> List<T> createMockList(@NonNull Class<T> clazz, int lowerListBound, int
            upperListBound) {
        int listSize = MockCreator.getRandomInt(lowerListBound, upperListBound);
        List<T> list = new ArrayList<>(listSize);
        for (int i = 0; i < listSize; i++) {
            list.add(MockCreator.mock(clazz));
        }
        return list;
    }

    public static <T> T mock(Class<T> clazz) {
        T obj;
        try {
            obj = clazz.newInstance();
            for (Field field : clazz.getDeclaredFields()) {
                field.setAccessible(true);
                Mock mock = field.getAnnotation(Mock.class);
                if (mock != null) {
                    Object mockValue;
                    if (mock.isList()) {
                        mockValue = MockCreator.createMockedList(mock);
                    } else {
                        mockValue = MockCreator.getMockValue(mock);
                    }
                    MockCreator.setValue(field, obj, mockValue);
                }
            }
        } catch (InstantiationException |
                IllegalAccessException |
                InvocationTargetException |
                NoSuchMethodException e) {
            Timber.e(e, "Could not mock class %s", clazz.getSimpleName());
            return null;
        }
        return obj;
    }

    private static List<?> createMockedList(@NonNull Mock mock) {
        int listSize = MockCreator.getRandomInt(mock.lowerListBound(), mock.upperListBound());
        List<Object> list = new ArrayList<>(listSize);
        for (int i = 0; i < listSize; i++) {
            list.add(MockCreator.getMockValue(mock));
        }
        return list;
    }

    private static int getRandomInt(int lowerBound, int upperBound) {
        int value = random.nextInt(upperBound - lowerBound);
        value += lowerBound;
        return value;
    }

    private static Object getMockValue(@NonNull Mock mock) {
        Object mockValue = null;
        switch (mock.type()) {
            case STRING: {
                mockValue = ipsum.getWords(mock.lowerValueBound(), mock.upperValueBound());
            }
            break;
            case INT: {
                mockValue = MockCreator.getRandomInt(mock.lowerValueBound(), mock
                        .upperValueBound());
            }
            break;
            case DOUBLE: {
                double value = random.nextDouble();
                value += MockCreator.getRandomInt(mock.lowerValueBound(), mock.upperValueBound());
                mockValue = value;
            }
            break;
            case UNIT:
            case CUSTOM:
                mockValue = mock.customValue();
                break;
            case MOCK:
                mockValue = MockCreator.mock(mock.mockType());
        }
        return mockValue;
    }

    private static void setValue(Field field, Object obj, Object value)
            throws
            IllegalAccessException,
            NoSuchMethodException,
            InvocationTargetException,
            InstantiationException {
        field.setAccessible(true);
        if (MutableLiveData.class.isAssignableFrom(field.getType())) {
            Object ld = field.getType()
                             .newInstance();
            // Depending on wether we're on the main-Thread (MainLooper) or not, we have to use
            // different functions to set the MutableLivaData value.
            // setValue - if the setter is running on the main-Thread
            // postValue - if the setter is running on some other thread than the main-Thread
            if (Looper.myLooper() == Looper.getMainLooper()) {
                MutableLiveData.class.getDeclaredMethod("setValue", Object.class)
                                     .invoke(ld, value);
            } else {
                MutableLiveData.class.getDeclaredMethod("postValue", Object.class)
                                     .invoke(ld, value);
            }
            value = ld;
        }
        field.set(obj, value);

    }

    public static String getMockedString(int lowerBound, int upperBound) {
        return MockCreator.ipsum.getWords(lowerBound, upperBound);
    }

}
