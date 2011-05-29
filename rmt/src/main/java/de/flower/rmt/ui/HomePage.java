package de.flower.rmt.ui;

import de.flower.rmt.dao.ITestDao;
import de.flower.rmt.model.TestBE;
import org.apache.wicket.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * @author oblume
 */
@AuthorizeInstantiation("ROLE_USER")
public class HomePage extends BasePage {

    @SpringBean
    private ITestDao dao;

    public HomePage() {

        TestBE testBE = new TestBE();
        testBE.setName("hello - " + System.currentTimeMillis());

        dao.save(testBE);

        add(new Label("testBE", Model.of(testBE.toString())));

    }

}