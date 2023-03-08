package com.jukes.model.jukes;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static io.swagger.v3.oas.annotations.media.Schema.AccessMode.READ_ONLY;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Schema(name = "Component model information")
public class Component {
    @Schema(accessMode = READ_ONLY, description = "Components name")
    private String name;
}