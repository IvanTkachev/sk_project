package com.ivantk.skproj.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.JspFragment;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.io.StringWriter;

public class InfoTag extends SimpleTagSupport{
    private int headerSize = 1;

    public void setHeaderSize(int headerSize) {
        this.headerSize = headerSize;
    }

    public void doTag() throws JspException {
        try {
            if (headerSize < 1) {
                headerSize = 1;
            } else if (headerSize > 3) {
                headerSize = 3;
            }
            PageContext pageContext = (PageContext) getJspContext();
            JspWriter out = pageContext.getOut();

            StringWriter sw = new StringWriter();
            sw.append("<h" + headerSize + ">");

            JspFragment body = getJspBody();
            body.invoke(sw);

            sw.append("</h" + headerSize + ">");
            out.println(sw.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
