package service;

import model.ItemOrden;
import model.Orden;
import repository.ClienteRepository;
import repository.ProductoRepository;

public class OrdenService {
    private final ClienteRepository clienteRepo;
    private final ProductoRepository productoRepo;

    public OrdenService(ClienteRepository clienteRepo, ProductoRepository productoRepo) {
        this.clienteRepo = clienteRepo;
        this.productoRepo = productoRepo;
    }

    public String registrarOrden(Orden orden) {
        return null;
    }
}