package repository;

public interface ClienteRepository {
    boolean existe(String idCliente);
    boolean estaActivo(String idCliente);
}
