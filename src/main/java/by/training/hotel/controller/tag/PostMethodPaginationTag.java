package by.training.hotel.controller.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import java.io.IOException;

public class PostMethodPaginationTag extends PaginationTag {

    private final static String CHANGE_PAGE_FUNCTION = "changePage(\'";

    private String formId;

    @Override
    public int doStartTag() throws JspException {
        try{
            JspWriter out = pageContext.getOut();
            if (getCurrentPage() > 1){
                out.write(prepareLink(getPrevious(), getCurrentPage() - 1));
            }
            for (int page = 1; page <= getPagesCount(); page++){
                String link;
                if (page == getCurrentPage()){
                    link = prepareActiveLink(Integer.toString(page), page);
                } else {
                    link = prepareLink(Integer.toString(page), page);
                }
                out.write(link);
            }
            if (getCurrentPage() < getPagesCount()){
                out.write(prepareLink(getNext(), getCurrentPage() + 1));
            }
        } catch (IOException e) {
            throw new JspException(e);
        }
        return SKIP_BODY;
    }

    @Override
    protected String prepareLink(String content, int page) {
        StringBuilder link = new StringBuilder();
        link.append(StandardTagName.A_HREF_OPEN);
        link.append(StandardTagName.JAVA_SCRIPT);
        link.append(CHANGE_PAGE_FUNCTION);
        link.append(formId);
        link.append("\', ");
        link.append(page);
        link.append(")\">");
        link.append(content);
        link.append(StandardTagName.A_CLOSE);
        return link.toString();
    }

    @Override
    protected String prepareActiveLink(String content, int page) {
        StringBuilder link = new StringBuilder();
        link.append(StandardTagName.A_HREF_OPEN);
        link.append(StandardTagName.JAVA_SCRIPT);
        link.append(CHANGE_PAGE_FUNCTION);
        link.append(formId);
        link.append("\', ");
        link.append(page);
        link.append(")\" class=\"active\">");
        link.append(content);
        link.append(StandardTagName.A_CLOSE);
        return link.toString();
    }

    @Override
    public int doEndTag() throws JspException {
        return EVAL_PAGE;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }
}
