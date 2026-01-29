package com.example.saudejafichapacienteservice.infrastructure.input.api.controllers.fichapaciente;

import com.example.saudejafichapacienteservice.controllers.FichaPacienteController;
import com.example.saudejafichapacienteservice.datasources.FichaPacienteDataSource;
import com.example.saudejafichapacienteservice.infrastructure.exceptions.TipoTokenException;
import dtos.requests.AtualizarFichaPacienteRequest;
import dtos.requests.CriarFichaPacienteRequest;
import dtos.responses.FichaPacienteResponse;
import enums.TipoTokenEnum;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@Slf4j
@RestController
@RequestMapping(path = "/api/v1/fichas-paciente")
@Tag(name = "Fichas Paciente API V1", description = "Versão 1 do controlador referente a fichas de paciente")
@Profile("api")
public class FichaPacienteControllerV1 {

    private final FichaPacienteController fichaPacienteController;

    public FichaPacienteControllerV1(FichaPacienteDataSource fichaPacienteDataSource) {
        this.fichaPacienteController = new FichaPacienteController(fichaPacienteDataSource);
    }

    @Operation(summary = "Cria uma ficha de paciente",
            description = "Endpoint restrito a usuários MEDICO ou ENFERMEIRO",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "201",
                    description = "Ficha de paciente criada com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = FichaPacienteResponse.class))),
            @ApiResponse(responseCode = "400",
                    description = "Valores inválidos para os atributos da ficha de paciente a ser criada",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "401",
                    description = "Credenciais de acesso inválidas",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "403",
                    description = "Usuário autenticado não é 'MEDICO' ou 'ENFERMEIRO'",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class))),
    })
    @PostMapping
    public ResponseEntity<FichaPacienteResponse> criarFichaPaciente(@AuthenticationPrincipal Jwt jwt,
                                                        @RequestBody @Valid CriarFichaPacienteRequest criarFichaPacienteRequest) {
        log.info("Médico ou enfermeiro {} criando ficha para paciente {}", jwt.getSubject(), criarFichaPacienteRequest.paciente());
        FichaPacienteResponse fichaPacienteResponse = fichaPacienteController.criarFichaPaciente(criarFichaPacienteRequest);
        log.info("Médico ou enfermeiro {} criou ficha para paciente {}", jwt.getSubject(), criarFichaPacienteRequest.paciente());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(fichaPacienteResponse);
    }

    @Operation(summary = "Recupera a ficha de um paciente",
            description = "Endpoint restrito a usuários MEDICO ou ENFERMEIRO",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    description = "Ficha de paciente recuperada com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = FichaPacienteResponse.class))),
            @ApiResponse(responseCode = "401",
                    description = "Credenciais de acesso inválidas",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "403",
                    description = "Usuário autenticado não é 'MEDICO' ou 'ENFERMEIRO'",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "404",
                    description = "Ficha de paciente não encontrada",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class))),
    })
    @GetMapping("/{pacienteId}")
    public ResponseEntity<FichaPacienteResponse> getFichaPaciente(@AuthenticationPrincipal Jwt jwt,
                                                                            @PathVariable("pacienteId") Long pacienteId) {
        log.info("Médico ou enfermeiro {} recuperando ficha do paciente {}", jwt.getSubject(), pacienteId);
        FichaPacienteResponse fichaPacienteResponse = fichaPacienteController.getFichaPaciente(pacienteId);
        log.info("Médico ou enfermeiro {} recuperou ficha do paciente {}", jwt.getSubject(), pacienteId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(fichaPacienteResponse);
    }

    @Operation(summary = "Recupera sua própria ficha de paciente",
            description = "Endpoint restrito a usuários PACIENTE",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    description = "Própria ficha de paciente recuperada com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = FichaPacienteResponse.class))),
            @ApiResponse(responseCode = "401",
                    description = "Credenciais de acesso inválidas",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "403",
                    description = "Usuário autenticado não é 'PACIENTE'",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "404",
                    description = "Ficha de paciente não encontrada",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class))),
    })
    @GetMapping
    public ResponseEntity<FichaPacienteResponse> getPropriaFichaPaciente(@AuthenticationPrincipal Jwt jwt) {
        log.info("Paciente {} recuperando sua própria ficha", jwt.getSubject());
        FichaPacienteResponse fichaPacienteResponse = fichaPacienteController.getFichaPaciente(Long.valueOf(jwt.getSubject()));
        log.info("Paciente {} recuperou sua própria ficha", jwt.getSubject());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(fichaPacienteResponse);
    }

    @Operation(summary = "Atualiza ficha de paciente",
            description = "Endpoint restrito a usuários MEDICO ou ENFERMEIRO",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    description = "Ficha de paciente atualizada com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = FichaPacienteResponse.class))),
            @ApiResponse(responseCode = "400",
                    description = "Valores inválidos para os atributos da ficha de paciente a ser atualizada",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "401",
                    description = "Credenciais de acesso inválidas",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "403",
                    description = "Usuário autenticado não é 'MEDICO' ou 'ENFERMEIRO'",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "404",
                    description = "Ficha de paciente não encontrada",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class))),
    })
    @PutMapping
    public ResponseEntity<FichaPacienteResponse> atualizarFichaPaciente(@AuthenticationPrincipal Jwt jwt,
                                                                   @RequestBody @Valid AtualizarFichaPacienteRequest atualizarFichaPacienteRequest) {
        log.info("Médico ou enfermeiro {} atualizando ficha do paciente {}", jwt.getSubject(), atualizarFichaPacienteRequest.paciente());
        FichaPacienteResponse fichaPacienteResponse = fichaPacienteController.atualizarFichaPaciente(atualizarFichaPacienteRequest);
        log.info("Médico ou enfermeiro {} atualizou ficha do paciente {}", jwt.getSubject(), atualizarFichaPacienteRequest.paciente());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(fichaPacienteResponse);
    }

    @Operation(summary = "Apaga ficha de paciente",
            description = "Endpoint restrito a serviços",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "204",
                    description = "Ficha de paciente apagada com sucesso"),
            @ApiResponse(responseCode = "401",
                    description = "Credenciais de acesso inválidas",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "403",
                    description = "Token autenticado não é de serviço",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "404",
                    description = "Ficha de paciente não encontrada",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class))),
    })
    @DeleteMapping("/{pacienteId}")
    public ResponseEntity<Void> apagarFichaPaciente(@AuthenticationPrincipal Jwt jwt, @PathVariable("pacienteId") Long pacienteId) {
        if (!Objects.equals(TipoTokenEnum.valueOf((String) jwt.getClaims().get("tipo_token")), TipoTokenEnum.SERVICO))
            throw new TipoTokenException();
        log.info("Serviço {} apagando ficha do paciente {}", jwt.getSubject(), pacienteId);
        fichaPacienteController.apagarFichaPaciente(pacienteId);
        log.info("Serviço {} apagou ficha do paciente {}", jwt.getSubject(), pacienteId);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
