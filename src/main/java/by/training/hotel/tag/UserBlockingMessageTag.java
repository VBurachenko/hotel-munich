package by.training.hotel.tag;

import by.training.hotel.controller.command.ParameterName;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class UserBlockingMessageTag extends TagSupport {

    private static final String ACCOUNT_BLOCKED_BECAUSE = "Your account was blocked, because ";

    private String textOfReason;

    private Integer reasonCode;

    @Override
    public int doStartTag() throws JspException {

        JspWriter out = pageContext.getOut();

        Integer code = (Integer) pageContext.getSession().getAttribute(ParameterName.BLOCKING);

        try {
            if (code != null && code.equals(reasonCode)){
                out.write(getBlockingMessage(textOfReason));
            }
        } catch (IOException e){
            throw new JspException(e);
        }
        return SKIP_BODY;
    }

    private String getBlockingMessage(String textMessageOfReason){
        StringBuilder message = new StringBuilder();
        message.append(StandardTagName.P_OPEN);
        message.append(ACCOUNT_BLOCKED_BECAUSE);
        message.append(StandardTagName.P_CLOSE);
        message.append(StandardTagName.P_OPEN);
        message.append(textMessageOfReason);
        message.append(StandardTagName.P_CLOSE);
        return message.toString();
    }

    @Override
    public int doEndTag() throws JspException {
        return EVAL_PAGE;
    }

    public void setTextOfReason(String textOfReason) {
        this.textOfReason = textOfReason;
    }

    public void setReasonCode(Integer reasonCode) {
        this.reasonCode = reasonCode;
    }
}
