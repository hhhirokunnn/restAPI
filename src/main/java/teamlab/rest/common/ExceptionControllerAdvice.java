package teamlab.rest.common;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class ExceptionControllerAdvice {

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        throw new RuntimeException("controller advice: init binder");
    }
    
    @ExceptionHandler(Exception.class)
    public String exception(Exception e) {
        throw new RuntimeException(e.getMessage(),e);
    }
    
    @ModelAttribute
    public void modelAttribute(){
        throw new RuntimeException("controller advice:model Attribute");
    }
}