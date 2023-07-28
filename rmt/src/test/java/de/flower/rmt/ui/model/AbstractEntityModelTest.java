package de.flower.rmt.ui.model;

import de.flower.common.model.db.entity.IEntity;
import org.apache.wicket.model.Model;
import org.testng.annotations.Test;

import java.io.Serializable;

import static org.testng.Assert.*;


public class AbstractEntityModelTest {
    
    @Test 
    public void testEmptyConstructor() {
        TestModel model = new TestModel();
        TestEntity object = model.getObject();
        assertNull(object.getId());
        assertTrue(model.newInstance);
    }

    @Test
    public void testEntityConstructor() {
        TestEntity entity = new TestEntity(1L);
        TestModel model = new TestModel(entity);
        TestEntity object = model.getObject();
        assertEquals(object, entity);
        assertFalse(model.load);

        // now detach model -> entity instance will be newly loaded
        model.detach();
        object = model.getObject();
        assertTrue(model.load);
        assertNotEquals(object, entity);
        assertEquals(object.getId(), entity.getId());
    }
    
    @Test
    public void testTransientEntityConstructor() {
        TestEntity entity = new TestEntity(null);
        TestModel model = new TestModel(entity);
        TestEntity object = model.getObject();
        assertEquals(object, entity);
        assertFalse(model.newInstance);

        // now detach model -> entity instance will be newly loaded
        model.detach();
        object = model.getObject();
        assertNotEquals(object, entity);
        assertNull(object.getId());
        assertTrue(model.newInstance);
    }

    @Test
    public void testWrappedModel() {
        TestEntity entity = new TestEntity(1L);
        TestModel model = new TestModel(Model.of(entity));
        TestEntity object = model.getObject();
        assertEquals(object, entity);
        assertFalse(model.load);

        // now detach model -> call to load
        model.detach();
        object = model.getObject();
        assertTrue(model.load);
        assertNotEquals(object, entity);
        assertEquals(object.getId(), entity.getId());
    }

    @Test
    public void testWrappedTransientModel() {
        TestEntity entity = new TestEntity(null);
        TestModel model = new TestModel(Model.of(entity));
        TestEntity object = model.getObject();
        assertEquals(object, entity);
        assertFalse(model.newInstance);

        // now detach model -> call to load
        model.detach();
        object = model.getObject();
        assertNotEquals(object, entity);
        assertNull(object.getId());
        assertTrue(model.newInstance);
    }

    private static class TestModel extends AbstractEntityModel<TestEntity> {

        public boolean load;

        public boolean newInstance;

        public TestModel(final TestEntity entity) {
            super(entity);
        }

        public TestModel() {
            super();
        }

        public TestModel(final Model<TestEntity> model) {
            super(model);
        }

        @Override
        protected void init() {
            ; // empty implementation, no need for Injector to work in this test.
        }

        @Override
        protected TestEntity load(final Long id) {
            load = true;
            return new TestEntity(id);
        }

        @Override
        protected TestEntity newInstance() {
            newInstance = true;
            return new TestEntity(null);
        }

    }

    private static class TestEntity implements IEntity, Serializable {

        Long id;

        public TestEntity(final Long id) {
            setId(id);
        }

        @Override
        public boolean isNew() {
            return id == null;
        }

        public Long getId() {
            return id;
        }

        public void setId(final Long id) {
            this.id = id;
        }
    }
    

}
