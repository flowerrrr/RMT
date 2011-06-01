package de.flower.rmt.ui.player;

import de.flower.rmt.dao.ITestDao;
import de.flower.rmt.model.TestBE;
import de.flower.rmt.ui.common.panel.LogoutLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * @author oblume
 */
public class PlayerHomePage extends PlayerBasePage {

    @SpringBean
    private ITestDao dao;

    public PlayerHomePage() {

        TestBE testBE = new TestBE();
        testBE.setName("hello - " + System.currentTimeMillis());

        dao.save(testBE);

        add(new Label("testBE", Model.of(testBE.toString())));

        add(new LogoutLink("logoutLink", this.getClass()));

    }

}