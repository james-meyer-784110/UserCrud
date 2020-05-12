package product.crud.usercrud.exceptions;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@NoArgsConstructor
public class FieldPropertyException extends WebAppException {

    private String field;
    private String property;
    private String value;

    @JsonIgnore
    public FieldPropertyException field(@NonNull String f){
        this.field = f;
        return this;
    }

    @JsonIgnore
    public FieldPropertyException property(@NonNull String p){
        this.property = p;
        return this;
    }

    @JsonIgnore
    public FieldPropertyException value(@NonNull String v){
        this.value = v;
        return this;
    }

    public String getMessage(){
        return String.format(
                "Value \"%s\" violates property \"%s\" for field \"%s\""
                , value
                , property
                , field
        );
    }
}
