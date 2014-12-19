/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buttso.demo.model;

/**
 *
 * @author sbutton
 */
public class Quote {
    
    private String quote;
    private String author;

    public Quote(String quote, String author) {
        this.quote = quote;
        this.author = author;
    }
    
    

    public String getQuote() {
        return quote;
    }

    public String getAuthor() {
        return author;
    }
    
    
    
}
