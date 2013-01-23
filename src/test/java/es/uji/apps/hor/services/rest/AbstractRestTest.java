package es.uji.apps.hor.services.rest;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.util.Log4jConfigListener;

import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.client.filter.LoggingFilter;
import com.sun.jersey.spi.spring.container.servlet.SpringServlet;
import com.sun.jersey.test.framework.JerseyTest;
import com.sun.jersey.test.framework.WebAppDescriptor;

import es.uji.commons.rest.UIEntityJSONMessageBodyReader;
import es.uji.commons.rest.UIEntityJSONMessageBodyWriter;
import es.uji.commons.rest.UIEntityListJSONMessageBodyReader;
import es.uji.commons.sso.AuthFilter;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext-test.xml" })
@TransactionConfiguration(defaultRollback = false)
public abstract class AbstractRestTest extends JerseyTest
{
    protected WebResource resource;
    static String packageName = "es.uji.apps.hor.services.rest";

    static final protected SimpleDateFormat UIEntityDateFormat = new SimpleDateFormat(
            "\"yyyy-MM-dd'T'HH:mm:ss\"");

    public AbstractRestTest()
    {
        super(new WebAppDescriptor.Builder(packageName)
                .contextParam("contextConfigLocation", "classpath:applicationContext-test.xml")
                .contextParam("log4jConfigLocation", "src/main/webapp/WEB-INF/log4j.properties")
                .contextParam("webAppRootKey", packageName)
                .contextListenerClass(Log4jConfigListener.class)
                .contextListenerClass(ContextLoaderListener.class)
                .requestListenerClass(RequestContextListener.class)
                .servletClass(SpringServlet.class)
                .addFilter(AuthFilter.class, "/*", getAuthFilterConfig())
                .clientConfig(createClientConfig())
                .initParam("com.sun.jersey.config.property.packages",
                        "es.uji.commons.rest; " + packageName).build());

        this.resource = resource();

        this.client().addFilter(new LoggingFilter());
    }

    private static Map<String, String> getAuthFilterConfig() {
        Map<String, String> initAuthFilterConfig = new HashMap<String, String>();
        
        initAuthFilterConfig.put("domainCookie", "LSMSessionlocalhost");
        initAuthFilterConfig.put("defaultUsername", "vrubert");
        initAuthFilterConfig.put("defaultUserId", "831");

        return initAuthFilterConfig;
    }

    private static ClientConfig createClientConfig()
    {
        ClientConfig config = new DefaultClientConfig();
        config.getClasses().add(UIEntityJSONMessageBodyReader.class);
        config.getClasses().add(UIEntityJSONMessageBodyWriter.class);
        config.getClasses().add(UIEntityListJSONMessageBodyReader.class);
        config.getClasses().add(JsonProvider.class);

        return config;
    }

}
