package repository;

public interface ProductoRepository {
    boolean tieneStock(String idProducto, int cantidad);
}
