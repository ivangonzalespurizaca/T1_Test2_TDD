package service;

import model.Orden;
import model.ItemOrden;
import repository.ClienteRepository;
import repository.ProductoRepository;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class OrdenService {
    private final ClienteRepository clienteRepo;
    private final ProductoRepository productoRepo;

    public OrdenService(ClienteRepository clienteRepo, ProductoRepository productoRepo) {
        this.clienteRepo = clienteRepo;
        this.productoRepo = productoRepo;
    }

    public String registrarOrden(Orden orden) {
        // Validar Formato de Código (OR-0001)
        if (orden.codigo() == null || !orden.codigo().matches("OR-\\d{4}")) {
            return "Orden cancelada: Formato de código inválido";
        }

        // Validar Fecha (Debe ser la fecha actual)
        if (orden.fecha() == null || !orden.fecha().equals(LocalDate.now())) {
            return "Orden cancelada: Fecha de orden debe ser la fecha actual";
        }

        // Validar Cliente (Existencia y Estado Activo)
        if (!clienteRepo.existe(orden.idCliente()) || !clienteRepo.estaActivo(orden.idCliente())) {
            return "Orden cancelada: Cliente no válido o inactivo";
        }

        double total = 0;
        Set<String> productosVistos = new HashSet<>();

        // Validar Lista de Productos
        if (orden.items() == null || orden.items().isEmpty()) {
            return "Orden cancelada: La lista de productos está vacía";
        }

        for (ItemOrden item : orden.items()) {
            // Regla: Cantidad por producto > 0
            if (item.cantidad() <= 0) {
                return "Orden cancelada: Cantidad de producto debe ser mayor a 0";
            }

            // Regla: No se permiten productos duplicados (usando un Set)
            if (!productosVistos.add(item.idProducto())) {
                return "Orden cancelada: Productos duplicados en la orden";
            }

            // Regla: Stock disponible (Si falla uno, se cancela toda la orden)
            if (!productoRepo.tieneStock(item.idProducto(), item.cantidad())) {
                return "Orden cancelada: Sin stock suficiente";
            }

            total += item.cantidad() * item.precio();
        }

        // Aplicar Descuento (10% si el total supera 500)
        if (total > 500) {
            total *= 0.90;
        }

        return "Orden registrada. Total: " + total;
    }
}