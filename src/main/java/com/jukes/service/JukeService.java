package com.jukes.service;

import com.jukes.exception.SettingsException;
import com.jukes.model.jukes.Component;
import com.jukes.model.jukes.Juke;
import com.jukes.model.settings.Setting;
import com.jukes.model.settings.Settings;
import com.jukes.remote.RemoteJukeService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.nonNull;

/*
 * JukeBoxService
 * filters jukes by given parameters from an incoming request
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class JukeService {

    private final RemoteJukeService remoteJukeService;

    /**
     * This method calls settings and jukes to load and then filters them
     *
     * @param settingId - use settingId to choose needed jukes by internal settings
     * @param model     - need to filter jukes by model (optional)
     * @param offset    - need to set skip option in result stream (optional)
     * @param limit     - need to set take (amount of Jukes) in result stream (optional)
     * @return Flux<Juke>.
     */
    @SneakyThrows
    public Flux<Juke> getJukes(String settingId, String model, Integer offset, Integer limit) {
        Flux<Juke> jukeFlux = remoteJukeService.loadJukes();
        Mono<Settings> settingsMono = remoteJukeService.loadSettings();

        int intLimt = limit != null ? limit : Integer.MAX_VALUE;
        int intSkipp = offset != null ? offset : 0;

        return settingsMono
                .flatMapMany(settings -> jukeFlux
                        .filter(juke -> juke.getComponents().stream()
                                .map(Component::getName).toList()
                                .containsAll(getRequires(settings, settingId)))
                        .onErrorResume(Mono::error)
                )
                .filter(jb -> !nonNull(model) || model.equals(jb.getModel()))
                .skip(intSkipp)
                .take(intLimt);
    }

    /**
     * This method finds needed list of setings in Settings object
     *
     * @param settings  - need to filter jukes by model
     * @param settingId - need to set skip option in result stream
     * @return List of strings.
     * @throws SettingsException in case the setting not fount  by settingsId
     */
    public List<String> getRequires(Settings settings, String settingId) {
        Optional<Setting> elementOptional = settings.getSettings().stream()
                .filter(s -> s.getId().equals(settingId))
                .findFirst();
        List<String> requires;
        if (elementOptional.isPresent()) {
            requires = new ArrayList<>(elementOptional.get().getRequires());
        } else {
            throw new SettingsException("Setings not found by Id" + settingId);
        }
        return requires;
    }

}
