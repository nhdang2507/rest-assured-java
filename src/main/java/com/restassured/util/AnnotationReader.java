package com.restassured.util;
import com.restassured.Category;
import org.testng.ITestResult;

import java.lang.reflect.Method;

public class AnnotationReader {
    public static String getTestMethodCategory(ITestResult iTestResult) {
        String categoryName = null;
        Method method = iTestResult.getMethod().getConstructorOrMethod().getMethod();
        Category category = method.getAnnotation(Category.class);
        if (category != null) categoryName = category.value();
        return categoryName;
    }
}
