package tech.pinto.catel.util.error;

import lombok.Getter;

@Getter
public class EntityNotExists extends CustomException {
    public String entityName;

    public EntityNotExists(String entityName) {
        this.entityName = entityName;
    }
}
