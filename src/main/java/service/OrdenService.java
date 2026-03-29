package service;

import model.Orden;
import model.ItemOrden;
import repository.ClienteRepository;
import repository.ProductoRepository;
import java.time.LocalDate;

public class OrdenService {
    private final ClienteRepository clienteRepo;
    private final ProductoRepository productoRepo;

    public OrdenService(ClienteRepository clienteRepo, ProductoRepository productoRepo) {
        this.clienteRepo = clienteRepo;
        this.productoRepo = productoRepo;
    }

    public String registrarOrden(Orden orden) {
        // Validar Cliente (Debe existir y estar activo)
        // Usamos las llamadas que configuraste en el when
        if (!clienteRepo.existe(orden.idCliente()) || !clienteRepo.estaActivo(orden.idCliente())) {
            return "Orden cancelada: Cliente no válido o inactivo";
        }

        double total = 0;

        // Validar Stock de la lista de productos
        for (ItemOrden item : orden.items()) {
            if (!productoRepo.tieneStock(item.idProducto(), item.cantidad())) {
                // Mensaje exacto que espera tu test 'testErrorStock'
                return "Orden cancelada: Sin stock suficiente";
            }
            total += item.cantidad() * item.precio();
        }

        // 3. Aplicar descuento del 10% si el total supera 500
        if (total > 500) {
            total = total * 0.90;
        }

        // 4. Retornar éxito (Mensaje exacto para 'testRegistroExitoso')
        return "Orden registrada. Total: " + total;
    }
}