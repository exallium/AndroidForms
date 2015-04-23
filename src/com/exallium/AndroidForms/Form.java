package com.exallium.AndroidForms;

import android.os.Bundle;

public abstract class Form<E, EH extends Form.EntityHolder<E>> {

    private final EntityHolder<E> entityHolder;

    public static abstract class EntityHolder<E> {

        private E entity;

        public EntityHolder() {}

        public EntityHolder(E entity) {
            this.entity = entity;
        }

        protected abstract void onSave(E entity);
        protected abstract E onCreate();

        private void save() {
            getEntity();
            onSave(entity);
        }

        private E getEntity() {
            if (entity == null)
                entity = onCreate();
            return entity;
        }

    }

    public Form() {
        entityHolder = createEntityHolder(null);
    }

    public Form(E entity) {
        entityHolder = createEntityHolder(entity);
    }

    /**
     * See forceSave(bundle)
     */
    public void forceSave() {
        forceSave(null);
    }

    /**
     * Save without validation
     * @param bundle bundle of extra data for entity
     */
    public void forceSave(Bundle bundle) {
        populateEntity(entityHolder.getEntity());
        populateEntityFromBundle(entityHolder.getEntity());
        entityHolder.save();
    }

    /**
     * See save(bundle)
     */
    public boolean save() {
        return save(null);
    }

    /**
     * Save entity if valid.
     * @param bundle Extra data for entity
     * @return Whether or not a save was attempted
     */
    public boolean save(Bundle bundle) {
        boolean isValid = isValid();
        if (isValid) forceSave(bundle);
        return isValid;
    }

    /**
     * Checks whether the form fields are valid
     * @return true if valid, false otherwise
     */
    public abstract boolean isValid();

    /**
     * Creates a new EntityHolder for a specific entity
     * @param entity The entity or NULL
     * @return the new EntityHolder
     */
    protected abstract EH createEntityHolder(E entity);

    /**
     * Move data from field to entity
     * @param entity the entity to populate
     */
    protected abstract void populateEntity(E entity);

    /** Move data from bundle to entity
     * @param entity the entity to populate
     */
    protected abstract void populateEntityFromBundle(E entity);

    /**
     * Populate form fields with entity data
     * @param entity The entity to read data from
     */
    protected abstract void populateFields(E entity);

}
