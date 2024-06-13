package uni.isw.controllers;

import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uni.isw.model.OrdenVenta;
import uni.isw.services.OrdenVentaService;

@RestController
@RequestMapping(path = "api/v1/orden_venta")
public class OrdenVentaController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private OrdenVentaService ordenVentaService;

    @RequestMapping(value = "/listar", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<OrdenVenta>> getOrdenesVenta() {
        List<OrdenVenta> listaOrdenesVenta = null;
        try {
            listaOrdenesVenta = ordenVentaService.getOrdenesVenta();
        } catch (Exception e) {
            logger.error("Error inesperado", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(listaOrdenesVenta, HttpStatus.OK);
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OrdenVenta> searchOrdenVenta(@RequestBody Optional<OrdenVenta> ordenVenta) {
        try {
            ordenVenta = ordenVentaService.getOrdenVenta(ordenVenta.get().getId_orden_venta());
        } catch (Exception e) {
            logger.error("Error inesperado", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(ordenVenta.get(), HttpStatus.OK);
    }

    @RequestMapping(value = "/insert", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OrdenVenta> insertOrdenVenta(@RequestBody OrdenVenta ordenVenta) {
        try {
            ordenVentaService.saveOrUpdateOrdenVenta(ordenVenta);
        } catch (Exception e) {
            logger.error("Error inesperado", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(ordenVenta, HttpStatus.OK);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OrdenVenta> updateOrdenVenta(@RequestBody OrdenVenta ordenVenta) {
        try {
            ordenVentaService.saveOrUpdateOrdenVenta(ordenVenta);
        } catch (Exception e) {
            logger.error("Error inesperado", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(ordenVenta, HttpStatus.OK);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OrdenVenta> deleteOrdenVenta(@RequestBody Optional<OrdenVenta> ordenVenta) {
        try {
            ordenVenta = ordenVentaService.getOrdenVenta(ordenVenta.get().getId_orden_venta());
            if (ordenVenta.isPresent())
                ordenVentaService.deleteOrdenVenta(ordenVenta.get().getId_orden_venta());
        } catch (Exception e) {
            logger.error("Error inesperado", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(ordenVenta.get(), HttpStatus.OK);
    }

    @RequestMapping(value = "/actualizarEstado/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OrdenVenta> actualizarEstado(@PathVariable Long id) {
        try {
            Optional<OrdenVenta> ordenVentaOpt = ordenVentaService.getOrdenVenta(id);
            if (ordenVentaOpt.isPresent()) {
                OrdenVenta ordenVenta = ordenVentaOpt.get();
                ordenVenta.setEstado(true);
                ordenVentaService.saveOrUpdateOrdenVenta(ordenVenta);
                return new ResponseEntity<>(ordenVenta, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error("Error inesperado", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
