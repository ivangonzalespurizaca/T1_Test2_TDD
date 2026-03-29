package model;

import java.time.LocalDate;
import java.util.List;

public record Orden(String codigo, String idCliente, List<ItemOrden> items, LocalDate fecha) {}