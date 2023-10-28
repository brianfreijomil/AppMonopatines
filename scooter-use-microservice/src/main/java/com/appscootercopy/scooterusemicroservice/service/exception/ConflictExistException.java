package com.appscootercopy.scooterusemicroservice.service.exception;

public class ConflictExistException extends RuntimeException {

    private String message;
    public ConflictExistException(String entity, String attribute, Long id) {
        this.message = String.format("There is already a %s entity with %s %s.", entity, attribute, id);
    }

    public ConflictExistException(String entity, String attribute, Long id, String attribute2, Long id2) {
        this.message = String.format("There is already a %s entity with %s %s, and %s %s.", entity, attribute, id, attribute2, id2);
    }

    public ConflictExistException(String entity, String attribute, Double x, String attribute2, Double y) {
        this.message = String.format("There is already a %s entity with %s %s, and %s %s.", entity, attribute, x, attribute2, y);
    }

    public ConflictExistException(String entity, String attribute, String licensePLate) {
        this.message = String.format("There is already a %s entity with %s %s.", entity, attribute, licensePLate);
    }

    /*
    public ConflictExistException(String entity, String entity2, String attribute, String attribute2, Long id, Long id2){
        this.message = String.format("There is already a %s entity with %s, %s and there is already a %s entity with %s, %s", entity, attribute, id, entity2, attribute2, id2);
    }*/

    public String getMessage() {
        return message;
    }

}
