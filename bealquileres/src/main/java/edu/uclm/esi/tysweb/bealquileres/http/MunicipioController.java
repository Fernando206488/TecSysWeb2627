package edu.uclm.esi.tysweb.bealquileres.http;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.uclm.esi.tysweb.bealquileres.dto.MunicipioRequest;
import edu.uclm.esi.tysweb.bealquileres.services.MunicipioService;

@RestController
@RequestMapping("/api/municipios")
public class MunicipioController {

    private final MunicipioService municipioService;

    public MunicipioController(MunicipioService municipioService) {
        this.municipioService = municipioService;
    }

    @GetMapping("/getNumeroDeDirecciones")
    public Integer getNumeroDeCalles(@RequestParam String municipio) {
        return this.municipioService.getNumeroDeDirecciones(municipio);
    }

    @PostMapping("/load")
    public void load(@RequestParam Integer offset, @RequestParam  Integer limit, @RequestParam(required = false) Boolean progress, @RequestBody MunicipioRequest request) {
        municipioService.load(request.municipio(), offset, limit, progress);
    }
}