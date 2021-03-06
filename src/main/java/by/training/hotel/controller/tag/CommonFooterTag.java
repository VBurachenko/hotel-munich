package by.training.hotel.controller.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public final class CommonFooterTag extends TagSupport {

    private final static String CONFIG_FILE_PATH = "page_footer_info_config";

    private final static StringBuilder FOOTER_INFO_LINE = new StringBuilder();

    static {
        ConfigFooterInfo.init(CONFIG_FILE_PATH);
        FOOTER_INFO_LINE.append(StandardTagName.FOOTER_OPEN);
        FOOTER_INFO_LINE.append("<div class=\"footer-info\">");

        FOOTER_INFO_LINE.append(StandardTagName.P_OPEN);
        FOOTER_INFO_LINE.append(ConfigFooterInfo.TELEPHONE_SIGN);
        FOOTER_INFO_LINE.append(":\u0020");
        FOOTER_INFO_LINE.append(ConfigFooterInfo.CONTACT_TELEPHONE);
        FOOTER_INFO_LINE.append(StandardTagName.P_CLOSE);

        FOOTER_INFO_LINE.append(StandardTagName.P_OPEN);
        FOOTER_INFO_LINE.append(ConfigFooterInfo.EMAIL_SIGN);
        FOOTER_INFO_LINE.append(":\u0020");
        FOOTER_INFO_LINE.append(ConfigFooterInfo.CONTACT_EMAIL);
        FOOTER_INFO_LINE.append(StandardTagName.P_CLOSE);

        FOOTER_INFO_LINE.append(StandardTagName.P_OPEN);
        FOOTER_INFO_LINE.append(ConfigFooterInfo.COMPANY_NAME);
        FOOTER_INFO_LINE.append(" ");
        FOOTER_INFO_LINE.append(ConfigFooterInfo.COMPANY_TYPE);
        FOOTER_INFO_LINE.append(StandardTagName.P_CLOSE);

        FOOTER_INFO_LINE.append(StandardTagName.P_OPEN);
        FOOTER_INFO_LINE.append(ConfigFooterInfo.AUTHOR_COPYRIGHT);
        FOOTER_INFO_LINE.append(StandardTagName.P_CLOSE);
        FOOTER_INFO_LINE.append("</div>");
        FOOTER_INFO_LINE.append(StandardTagName.FOOTER_CLOSE);
    }

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        try {
            out.write(FOOTER_INFO_LINE.toString());
        } catch (IOException e) {
            throw new JspException(e);
        }
        return SKIP_BODY;
    }

    @Override
    public int doEndTag() throws JspException {
        return EVAL_PAGE;
    }
}
