/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/7.0.47
 * Generated at: 2021-12-20 04:18:58 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.WEB_002dINF.pages;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class navbar_005fcms_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  private javax.el.ExpressionFactory _el_expressionfactory;
  private org.apache.tomcat.InstanceManager _jsp_instancemanager;

  public java.util.Map<java.lang.String,java.lang.Long> getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
    _jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
  }

  public void _jspDestroy() {
  }

  public void _jspService(final javax.servlet.http.HttpServletRequest request, final javax.servlet.http.HttpServletResponse response)
        throws java.io.IOException, javax.servlet.ServletException {

    final javax.servlet.jsp.PageContext pageContext;
    javax.servlet.http.HttpSession session = null;
    final javax.servlet.ServletContext application;
    final javax.servlet.ServletConfig config;
    javax.servlet.jsp.JspWriter out = null;
    final java.lang.Object page = this;
    javax.servlet.jsp.JspWriter _jspx_out = null;
    javax.servlet.jsp.PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html; charset=utf-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n");
      out.write("\r\n");
      out.write("<!DOCTYPE html>\r\n");
      out.write("<html>\r\n");
      out.write("<head>\r\n");
      out.write("<link rel=\"stylesheet\" href=\"https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css\" integrity=\"sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T\" crossorigin=\"anonymous\">\r\n");
      out.write("<script src=\"https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js\" integrity=\"sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM\" crossorigin=\"anonymous\"></script>\r\n");
      out.write("<script src=\"https://code.jquery.com/jquery-3.3.1.slim.min.js\" integrity=\"sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo\" crossorigin=\"anonymous\"></script>\r\n");
      out.write("<script src=\"https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js\" integrity=\"sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1\" crossorigin=\"anonymous\"></script>\r\n");
      out.write("\r\n");
      out.write("</head>\r\n");
      out.write("<body>\r\n");
      out.write("\t<nav class=\"navbar fixed-top navbar-expand-lg navbar-light bg-light justify-content-between\">\r\n");
      out.write("\t  \t<a class=\"navbar-brand\" href=\"home\">\r\n");
      out.write("\t\t\t<img src=\"../img/b-logo.jpg\" width=\"30\" height=\"30\" class=\"d-inline-block align-top\" alt=\"\">\r\n");
      out.write("    \t\tFaceBảo\r\n");
      out.write("\t\t</a>\r\n");
      out.write("\t  \t<button class=\"navbar-toggler\" type=\"button\" data-toggle=\"collapse\" data-target=\"#navbarSupportedContent\" aria-controls=\"navbarSupportedContent\" aria-expanded=\"false\" aria-label=\"Toggle navigation\">\r\n");
      out.write("\t    \t<span class=\"navbar-toggler-icon\"></span>\r\n");
      out.write("\t  \t</button>\r\n");
      out.write("\t  <div>\r\n");
      out.write("\t    <ul class=\"navbar-nav mr-auto\">\r\n");
      out.write("\t      <li class=\"nav-item active mr-4\">\r\n");
      out.write("\t        <a class=\"nav-link\" href=\"#\">\r\n");
      out.write("\t\t        <img alt=\"Avatar\" class=\"avatar \" src=\"../");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${sessionScope.adminInfo.getAvatarPath() }", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
      out.write("\" width=\"30\" height=\"30\">\r\n");
      out.write("\t\t        ");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${sessionScope.adminInfo.getName() }", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
      out.write("\r\n");
      out.write("\t        </a>\r\n");
      out.write("\t      </li>\r\n");
      out.write("\t      <li class=\"nav-item dropdown\">\r\n");
      out.write("\t        <a class=\"nav-link\" href=\"#\" id=\"navbarDropdown\" role=\"button\" data-toggle=\"dropdown\" aria-haspopup=\"true\" aria-expanded=\"false\">\r\n");
      out.write("\t        \t<img alt=\"\" src=\"../img/dropdown-icon.svg\" width=\"15\">\r\n");
      out.write("\t        </a>\r\n");
      out.write("\t        <div class=\"dropdown-menu dropdown-menu-right\" aria-labelledby=\"navbarDropdown\">\r\n");
      out.write("\t          \t<!-- <a class=\"dropdown-item\" href=\"#\" data-toggle=\"modal\" data-target=\"#changeAvatarModal\">Thay đổi ảnh đại diện</a> -->\r\n");
      out.write("\t          \t<div class=\"dropdown-divider\"></div>\r\n");
      out.write("\t          <a class=\"dropdown-item\" href=\"logout\">Đăng xuất</a>\r\n");
      out.write("\t        </div>\r\n");
      out.write("\t      </li>\r\n");
      out.write("\t    </ul>\r\n");
      out.write("\t  </div>\r\n");
      out.write("\t</nav>\r\n");
      out.write("\t\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("<!-- Modal -->\r\n");
      out.write("<div class=\"modal fade\" id=\"changeAvatarModal\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"changeAvatarModalLabel\" aria-hidden=\"true\">\r\n");
      out.write("  <div class=\"modal-dialog\" role=\"document\">\r\n");
      out.write("    <div class=\"modal-content\">\r\n");
      out.write("      <div class=\"modal-header\">\r\n");
      out.write("        <h5 class=\"modal-title text-dark\" id=\"changeAvatarModalLabel\">Modal title</h5>\r\n");
      out.write("        <button type=\"button\" class=\"close\" data-dismiss=\"modal\" aria-label=\"Close\">\r\n");
      out.write("          <span aria-hidden=\"true\">&times;</span>\r\n");
      out.write("        </button>\r\n");
      out.write("      </div>\r\n");
      out.write("      <div class=\"modal-body\">\r\n");
      out.write("      \t<form id=\"change-avatar-form\" action=\"/socialnetwork/changeAvatar\" method=\"post\" enctype=\"multipart/form-data\">\r\n");
      out.write("      \t\t<div class=\" d-flex justify-content-center\">\r\n");
      out.write("\t \t\t\t<img id=\"preview-avatar\" src=\"../img/default-avatar.png\" alt=\"your image\" width=\"300px\" height=\"300px\"/>\r\n");
      out.write("\t      \t</div>\r\n");
      out.write("\t        <input name=\"file\" accept=\"image/*\" class=\"btn\" type='file' id=\"input-avatar\" required=\"required\"/>\r\n");
      out.write("      \t</form>\r\n");
      out.write("      </div>\r\n");
      out.write("      <div class=\"modal-footer\">\r\n");
      out.write("        <button type=\"button\" class=\"btn btn-secondary\" data-dismiss=\"modal\">Thôi</button>\r\n");
      out.write("        <button form=\"change-avatar-form\" type=\"submit\" class=\"btn btn-primary\">Lưu</button>\r\n");
      out.write("      </div>\r\n");
      out.write("    </div>\r\n");
      out.write("  </div>\r\n");
      out.write("</div>\r\n");
      out.write("\r\n");
      out.write("<script\r\n");
      out.write("\tsrc=\"https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js\"></script>\r\n");
      out.write("<script\r\n");
      out.write("\tsrc=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js\"\r\n");
      out.write("\tintegrity=\"sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS\"\r\n");
      out.write("\tcrossorigin=\"anonymous\"></script>\r\n");
      out.write("<script src=\"../js/Navbar.js\"></script>\r\n");
      out.write("</body>\r\n");
      out.write("</html>");
    } catch (java.lang.Throwable t) {
      if (!(t instanceof javax.servlet.jsp.SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try { out.clearBuffer(); } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
