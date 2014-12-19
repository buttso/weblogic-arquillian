/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demo.buttso.web;

import buttso.demo.ejb.QuoteMaker;
import buttso.demo.model.Quote;
import buttso.demo.web.QuoteServlet;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import org.apache.commons.io.IOUtils;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.exporter.ZipExporter;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 *
 * @author sbutton
 */
@RunWith(Arquillian.class)
public class QuoteMakerTest {

    private static final Logger LOG = Logger.getLogger(QuoteMakerTest.class.getName());
    private static boolean SAVE_WAR = false;

    @Inject
    private QuoteMaker quoteMaker;

    @Deployment(testable = true)
    public static WebArchive createDeployment() {
        WebArchive war = ShrinkWrap.create(WebArchive.class)
                .addClass(QuoteMaker.class)
                .addClass(QuoteServlet.class)
                .addClass(Quote.class)
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");

        LOG.fine(war.toString(true));
        
        // Also see this in arquillian.xml to save the actual deployment
        // <property name="deploymentExportPath">target/</property>
        if (SAVE_WAR) {
            war.as(ZipExporter.class).exportTo(new File("/tmp/" + war.getName()), true);
        }

        return war;
    }

    @Test
    @RunAsClient
    public void checkQuoteIsPresent(@ArquillianResource URL contextPath) {
        String newContext = contextPath.toExternalForm() + "/QuoteServlet";
        LOG.info(newContext);

        InputStream in = null;

        try {
            in = new URL(newContext).openStream();
            for (String line : IOUtils.readLines(in)) {
                if (line.contains("quote")) {
                    System.out.println(line);
                    Assert.assertTrue("Quote found", line.contains("quote"));
                    break;
                }
            }
        } catch (Exception e) {
            Assert.assertFalse("Quote not found", true);
        } finally {
            IOUtils.closeQuietly(in);
        }

    }

    @Test
    public void testQuoteMakerQuote() {
        Assert.assertTrue(quoteMaker != null);
        Quote quote = quoteMaker.quote();
        LOG.log(Level.INFO, "\nQuote: {0}", quote);
        Assert.assertTrue("Quote is null", quote.getQuote() != null);
        Assert.assertTrue("Author is null", quote.getAuthor() != null);
    }

    @Test
    public void testQuoteMakerQuoteModel() {
        Assert.assertTrue(quoteMaker != null);
        Quote quote = quoteMaker.quote();
        LOG.log(Level.INFO, "\nQuote: {0}\nAuthor: {1}", 
                new Object[] { 
                    quote.getQuote(), 
                    quote.getAuthor()
                });
        Assert.assertTrue("QuoteModel is null", quote != null);
        Assert.assertTrue("Quote is not null", quote.getQuote() != null);
        Assert.assertTrue("Quote is not null", quote.getAuthor()!= null);
    }
    

}
