package com.jukes.controller;

import com.jukes.model.jukes.Juke;
import com.jukes.service.JukeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@Tag(name = "Jukes controller", description = "Jukes controller needs to get filtered jukes by parameter")
@RestController
@RequestMapping("/api/jukes")
@RequiredArgsConstructor
public class JukesController {

    private final JukeService cardsService;

    @Operation(description = "Load filtered Jukes", parameters = {
            @Parameter(name = "settingId", in = ParameterIn.QUERY, required = true,
                    description = "Setting Id needed the app to find a setting list and filter by them"),
            @Parameter(name = "model", in = ParameterIn.QUERY,
                    description = "Model needed to filter jukes by name"),
            @Parameter(name = "offset", in = ParameterIn.QUERY,
                    description = "Offset sets the first position to return from the results of the query"),
            @Parameter(name = "limit", in = ParameterIn.QUERY,
                    description = "Limit specifies the number of resources that a response contains")
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully loaded"),
            @ApiResponse(responseCode = "404", description = "Not found - The setting was not found")
    })
    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public ResponseEntity<Flux<Juke>> loadJukes(@RequestParam(value = "settingId") String settingId,
                                                @RequestParam(value = "model", required = false) String model,
                                                @RequestParam(value = "offset", required = false) Integer offset,
                                                @RequestParam(value = "limit", required = false) Integer limit) {
        return ResponseEntity.ok(cardsService.getJukes(settingId, model, offset, limit));
    }
}
