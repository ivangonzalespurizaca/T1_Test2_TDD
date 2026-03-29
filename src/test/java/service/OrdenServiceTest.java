package service;

import model.ItemOrden;
import model.Orden;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import repository.ClienteRepository;
import repository.ProductoRepository;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class OrdenServiceTest {

    @Mock
    private ClienteRepository clienteRepo;
    @Mock private ProductoRepository productoRepo;
    @InjectMocks
    private OrdenService ordenService;

    @Test
    @DisplayName("Should return success with discount when total > 500")
    void testRegistroExitoso() {
        when(clienteRepo.existe("C01")).thenReturn(true);
        when(clienteRepo.estaActivo("C01")).thenReturn(true);
        when(productoRepo.tieneStock("P01", 2)).thenReturn(true);

        Orden orden = new Orden("OR-0001", "C01", List.of(new ItemOrden("P01", 2, 300.0)), LocalDate.now());

        assertEquals("Orden registrada. Total: 540.0", ordenService.registrarOrden(orden));
    }

    @Test
    @DisplayName("Should return error when product is out of stock")
    void testErrorStock() {
        when(clienteRepo.existe("C01")).thenReturn(true);
        when(clienteRepo.estaActivo("C01")).thenReturn(true);
        when(productoRepo.tieneStock("P02", 1)).thenReturn(false);

        Orden orden = new Orden("OR-0001", "C01", List.of(new ItemOrden("P02", 1, 100.0)), LocalDate.now());

        assertEquals("Orden cancelada: Sin stock suficiente", ordenService.registrarOrden(orden));
    }
}