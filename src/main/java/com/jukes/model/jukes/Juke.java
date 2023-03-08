package com.jukes.model.jukes;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import static io.swagger.v3.oas.annotations.media.Schema.AccessMode.READ_ONLY;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Schema(name = "Juke model information")
public class Juke {

    @Schema(accessMode = READ_ONLY, description = "Juke id")
    private String id;
    @Schema(accessMode = READ_ONLY, description = "Model of juke")
    private String model;
    @Schema(accessMode = READ_ONLY, description = "Components that Juke has")
    private List<Component> components;

}
