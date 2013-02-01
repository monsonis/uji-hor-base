package es.uji.apps.hor.matchers;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import es.uji.commons.rest.UIEntity;

public class UIEntityHasTitle extends TypeSafeMatcher<UIEntity>
{

    @Override
    public void describeTo(Description description)
    {
        description.appendText("having a title");

    }

    @Override
    protected boolean matchesSafely(UIEntity entity)
    {
        String entity_title = entity.get("title").replace("\"", "");

        return entity_title != null && entity_title.length() > 0;

    }

    @Factory
    public static <T> Matcher<UIEntity> hasTitle()
    {
        return new UIEntityHasTitle();
    }

}
