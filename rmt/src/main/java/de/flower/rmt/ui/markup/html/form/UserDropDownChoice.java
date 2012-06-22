package de.flower.rmt.ui.markup.html.form;

import de.flower.rmt.model.db.entity.User;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.IModel;

import java.util.List;

/**
 * @author flowerrrr
 */
public class UserDropDownChoice extends DropDownChoice<User> {

    public UserDropDownChoice(String id, IModel<User> model, IModel<List<User>> choices) {
        super(id, model, choices);
        setChoiceRenderer(new IChoiceRenderer<User>() {
            @Override
            public Object getDisplayValue(User user) {
                return user.getFullname();
            }

            @Override
            public String getIdValue(User user, int index) {
                return user.getId().toString();
            }
        });
        // setNullValid(true);
    }

}
