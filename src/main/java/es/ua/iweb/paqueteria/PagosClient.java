package es.ua.iweb.paqueteria;

import es.ua.iweb.paqueteria.dto.NuevoPagoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(name = "GreenSys", url = "https://sandbox.green-sys.es")
public interface PagosClient {
    @PostMapping("/sales")
    ResponseEntity<Map<String, String>> crearPago(@RequestBody NuevoPagoDTO request);
}
