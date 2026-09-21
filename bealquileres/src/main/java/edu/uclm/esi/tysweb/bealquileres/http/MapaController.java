package edu.uclm.esi.tysweb.bealquileres.http;

import edu.uclm.esi.tysweb.bealquileres.dto.VehiculoDto;
import java.util.List;

import edu.uclm.esi.tysweb.bealquileres.services.MapaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/mapa")
@CrossOrigin("*") 

public class MapaController {

    @Autowired 
    private MapaService service;
    
    //Se hace para darle un nombre publico de cara al uso desde los clientes
    @GetMapping("/getVehiculos/{city}")
    public List<VehiculoDto> getVehiculos(@PathVariable String city) {
        return (List<VehiculoDto>) (List<?>) this.service.getVehiculos(city);
    }
}
