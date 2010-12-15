package controllers;

import java.io.StringWriter;

import jj.play.org.eclipse.mylyn.wikitext.core.parser.MarkupParser;
import jj.play.org.eclipse.mylyn.wikitext.core.parser.builder.HtmlDocumentBuilder;
import jj.play.org.eclipse.mylyn.wikitext.core.parser.builder.HtmlDocumentBuilder.Stylesheet;
import jj.play.org.eclipse.mylyn.wikitext.textile.core.TextileLanguage;
import play.mvc.Controller;

public class Wikitext extends Controller {

    public static void renderPreview(String wiki) {
        StringWriter writer = new StringWriter();
        HtmlDocumentBuilder builder = new HtmlDocumentBuilder(writer);
        // avoid the <html> and <body> tags
        // builder.setEmitAsDocument(false);
        Stylesheet css = new Stylesheet("/public/stylesheets/main.css");
        builder.addCssStylesheet(css);
        css = new Stylesheet("/public/stylesheets/wiki.css");
        builder.addCssStylesheet(css);

        MarkupParser parser = new MarkupParser(new TextileLanguage());
        parser.setBuilder(builder);
        parser.parse(wiki);

        String htmlContent = writer.toString();
        renderText(htmlContent);
    }

    public static String render(String wiki) {
        StringWriter writer = new StringWriter();
        HtmlDocumentBuilder builder = new HtmlDocumentBuilder(writer);
        // avoid the <html> and <body> tags
        builder.setEmitAsDocument(false);

        MarkupParser parser = new MarkupParser(new TextileLanguage());
        parser.setBuilder(builder);
        parser.parse(wiki);

        return writer.toString();
    }
}
