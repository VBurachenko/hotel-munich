package by.training.hotel.controller.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class PaginationTag extends TagSupport {

    private final static String PAGE_PARAMETER = "?page=";

    private String previous;

    private String next;

    private String urlPattern;

    private int currentPage;

    private int pagesCount;

    @Override
    public int doStartTag() throws JspException {
        try{
            JspWriter out = pageContext.getOut();
            if (currentPage > 1){
                out.write(prepareLink(previous, currentPage - 1));
            }
            for (int page = 1; page <= pagesCount; page++){
                String link;
                if (page == currentPage){
                    link = prepareActiveLink(Integer.toString(page), page);
                } else {
                    link = prepareLink(Integer.toString(page), page);
                }
                out.write(link);
            }
            if (currentPage < pagesCount){
                out.write(prepareLink(next, currentPage + 1));
            }
        } catch (IOException e) {
            throw new JspException(e);
        }
        return SKIP_BODY;
    }

    protected String prepareLink(String content, int page){
        StringBuilder link = new StringBuilder();
        link.append(StandardTagName.A_HREF_OPEN);
        link.append(urlPattern);
        link.append(PAGE_PARAMETER);
        link.append(page);
        link.append("\">");
        link.append(content);
        link.append(StandardTagName.A_CLOSE);
        return link.toString();
    }

    protected String prepareActiveLink(String content, int page){
        StringBuilder link = new StringBuilder();
        link.append(StandardTagName.A_HREF_OPEN);
        link.append(urlPattern);
        link.append(PAGE_PARAMETER);
        link.append(page);
        link.append("\" class = \"active\">");
        link.append(content);
        link.append(StandardTagName.A_CLOSE);
        return link.toString();
    }

    @Override
    public int doEndTag() throws JspException {
        return EVAL_PAGE;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public void setUrlPattern(String urlPattern) {
        this.urlPattern = urlPattern;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public void setPagesCount(int pagesCount) {
        this.pagesCount = pagesCount;
    }

    int getCurrentPage() {
        return currentPage;
    }

    int getPagesCount() {
        return pagesCount;
    }

    public String getPrevious() {
        return previous;
    }

    public String getNext() {
        return next;
    }
}
