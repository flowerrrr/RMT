package de.flower.rmt.ui.page.event.player;

import com.mysema.query.types.Path;
import de.flower.rmt.model.db.entity.Lineup;
import de.flower.rmt.model.db.entity.event.Event;
import de.flower.rmt.service.ILineupManager;
import de.flower.rmt.test.AbstractRMTWicketMockitoTests;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.mockito.Mockito;
import org.testng.annotations.Test;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

/**
 * @author flowerrrr
 */
public class LineupPanelTest extends AbstractRMTWicketMockitoTests {

    @SpringBean
    private ILineupManager lineupManager;

    @Override
    protected boolean isMockitoVerboseLogging() {
        return true;
    }

    @Test
    public void testRenderNoLineup() {
        Event event = testData.newEvent();
        Lineup lineup = null;
        when(lineupManager.findOrCreateLineup(event)).thenReturn(lineup);

        wicketTester.startComponentInPage(new LineupPanel("panel", Model.of(event)));
        wicketTester.dumpComponentWithPage();

        verify(lineupManager, never()).findLineupItems(any(Event.class), Mockito.<Path>anyVararg());
    }

    @Test
    public void testRenderLineupNotPubslished() {
        Event event = testData.newEvent();
        Lineup lineup = testData.newLineup(event);
        when(lineupManager.findOrCreateLineup(event)).thenReturn(lineup);

        wicketTester.startComponentInPage(new LineupPanel("panel", Model.of(event)));
        wicketTester.dumpComponentWithPage();

        verify(lineupManager, never()).findLineupItems(any(Event.class), Mockito.<Path>anyVararg());
    }

    @Test
    public void testRenderLineupAvailable() {
        Event event = testData.newEvent();
        Lineup lineup = testData.newLineup(event);
        lineup.setPublished(true);
        when(lineupManager.findOrCreateLineup(event)).thenReturn(lineup);
        when(lineupManager.findLineupItems(eq(event), Mockito.<Path>anyVararg())).thenReturn(lineup.getItems());

        wicketTester.startComponentInPage(new LineupPanel("panel", Model.of(event)));
        wicketTester.dumpComponentWithPage();

        verify(lineupManager, times(1)).findLineupItems(any(Event.class), Mockito.<Path>anyVararg());
    }

}
