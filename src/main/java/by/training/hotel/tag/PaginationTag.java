package by.training.hotel.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class PaginationTag extends TagSupport {

    private static final String PREVIOUS = "<<";
    private static final String NEXT = ">>";

    private static final String LI_OPEN = "<li>";
    private static final String LI_CLOSE = "</li>";

    private static final String A_OPEN = "<a>";
    private static final String A_CLOSE = "</a>";


    private String urlPattern;

    private int currentPage;

    private int pagesCount;

    @Override
    public int doStartTag() throws JspException {
        try{
            JspWriter out = pageContext.getOut();
            if (currentPage > 1){
                out.write(prepareLink(PREVIOUS, currentPage - 1));
            }
            for (int page = 1; page <= pagesCount; page++){
                String link;
                if (page == currentPage){
                    link = prepareLink(Integer.toString(page));
                } else {
                    link = prepareLink(Integer.toString(page), page);
                }
                out.write(link);
            }
            if (currentPage < pagesCount){
                out.write(prepareLink(NEXT, currentPage + 1));
            }
        } catch (IOException e) {
            throw new JspException(e);
        }
        return SKIP_BODY;
    }

    private String prepareLink(String content, int page){
        StringBuilder link = new StringBuilder();
        link.append(LI_OPEN);
        link.append("<a href=\"");
        link.append(urlPattern);
        link.append("?page=");
        link.append(page);
        link.append("\">");
        link.append(content);
        link.append(A_CLOSE);
        link.append(LI_CLOSE);
        return link.toString();
    }

    private String prepareLink(String content){
        StringBuilder link = new StringBuilder();
        link.append(A_OPEN);
        link.append(content);
        link.append(A_CLOSE);
        return link.toString();
    }

    @Override
    public int doEndTag() throws JspException {
        return EVAL_PAGE;
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
}
