package de.fewobacher.model;

import de.fewobacher.constant.ErrorLevel;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RequestCodeModel
{
    @NotNull
    Integer imageSize;

    @NotNull
    String data;

    @NotNull
    ErrorLevel errorLevel;
}
