package com.servicio.reservas.usuarios.infraestructure.exceptions;

public class BusinessException extends RuntimeException {
  public BusinessException(String message) {
    super(message);
  }
}
