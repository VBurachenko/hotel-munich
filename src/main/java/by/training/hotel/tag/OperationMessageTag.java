package by.training.hotel.tag;

import by.training.hotel.controller.command.ParameterName;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class OperationMessageTag extends TagSupport {

    private String textMessage;

    private int messageCode;

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        try {
            Integer code = (Integer) pageContext.getRequest().getAttribute(ParameterName.OPERATION_MESSAGE);
            if (code == null){
                code = (Integer) pageContext.getSession().getAttribute(ParameterName.OPERATION_MESSAGE);
            }
            if (code != null && code.equals(messageCode)){
                out.write(prepareMessageTag());
            }
        } catch (IOException e) {
            throw new JspException(e);
        }
        return SKIP_BODY;
    }

    private String prepareMessageTag(){
        StringBuilder messageTag = new StringBuilder();
        messageTag.append(StandardTagName.P_OPEN);
        messageTag.append(textMessage);
        messageTag.append(StandardTagName.P_CLOSE);
        return messageTag.toString();
    }

    @Override
    public int doEndTag() throws JspException {
        return EVAL_PAGE;
    }

    public void setTextMessage(String textMessage) {
        this.textMessage = textMessage;
    }

    public void setMessageCode(int messageCode) {
        this.messageCode = messageCode;
    }
}
