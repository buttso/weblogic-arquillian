/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buttso.demo.ejb;

import buttso.demo.model.Quote;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;

/**
 *
 * @author sbutton
 */
@Stateless
public class QuoteMaker {
    
    private static final Logger LOG = Logger.getLogger(QuoteMaker.class.getName());

    static final String[][] QUOTES = {
        { "Imagination is more important than knowledge.", "Albert Einstein" },
        { "If music be the food of love, play on.", "Shakespeare" },
        { "The way to get started is to quit talking and begin doing.", "Walt Disney" },
        { "When you come to a fork in the road, take it.", "Yogi Berra" },
        { "Always do right. This will gratify some people and astonish the rest.", "Mark Twain" },
        { "If you have nothing to say, say nothing.", "Mark Twain" },
        { "Don't tell people how to do things, tell them what to do and let them surprise you with their results.", "George S. Patton" },
        { "Fuck you, I won't do what you tell me.", "Rage Against the Machine" }
    };

    final Random rnd = new Random(System.currentTimeMillis());
    static int counter[] = new int[QUOTES.length];

    private int next() {
        return rnd.nextInt(QUOTES.length);
    }

    private String[] nextQuote() {
        int idx = next();
        counter[idx] += 1;
        return new String[]{QUOTES[idx][0], QUOTES[idx][1]};
    }

    /**
     * 
     * @return - A quote
     */
    public String quote_() {
        String[] q = nextQuote();
        LOG.log(Level.INFO, "Quote: {0}", new Object[] { q.toString() } );
        return String.format("%s %s", q[0], q[1]);
    }
    
    public Quote quote() {
        String[] q = nextQuote();
        LOG.log(Level.INFO, "Quote: {0}", new Object[] { q.toString() } );
        return new Quote(q[0], q[1]);
    }

    /**
     * 
     * @return - A quote and an author
     */
    public String[] quoteAndAuthor() {
        String quote[] = nextQuote();
        LOG.log(Level.INFO, "\nQuote: {0}\nAuthor: {1}", new Object[] { quote[0], quote[1] } );
        return quote;
    }

    /**
     * Test
     * @param args 
     */
    public static void main(String[] args) {
        QuoteMaker qm = new QuoteMaker();
        int i = 0;
        while (i++ < 100) {
            String[] quote = qm.quoteAndAuthor();
            System.out.printf("'%s' %S\n", quote[0], quote[1]);
        }

        for (int x = 0; x < QUOTES.length; x++) {
            System.out.printf("%s:%s\n", x, counter[x]);
        }

    }

}
