package hcl.policing.digitalpolicingplatform.mpermissionslibrary;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * Created by HCL Technologies on 11/05/19.
 * custom anotation for AfterPermissionDenied
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface AfterPermissionDenied {
    int value();
}