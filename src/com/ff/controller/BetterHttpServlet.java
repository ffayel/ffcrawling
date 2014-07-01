package com.ff.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ff.tool.MyStatics;

public abstract class BetterHttpServlet extends HttpServlet {

	private static final long serialVersionUID = -2692039853176285807L;

	/**Variable pour : String / contenant le titre a afficher dans la page*/
	public final static String PAGE_ATTR_TITLE = "pageTitle";
	public static final String ENCODING = "encoding";
	public final static DateFormat inputDate_1 =  new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);
	public final static DateFormat inputDate_2 =  new SimpleDateFormat("dd-MM-yyyy", Locale.FRANCE);
	public final static DateFormat inputDate_3 =  new SimpleDateFormat("dd.MM.yyyy", Locale.FRANCE);
	public final static DateFormat inputDate_4 =  new SimpleDateFormat("ddMMyyyy", Locale.FRANCE);
	public final static DateFormat inputDateWithTime =  new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.FRANCE);

	private static final Pattern _PROTOCOL_MATCHER = Pattern.compile(
			"^(dict|dns|file|ftp|gopher|https?|imap|ipp|ldap|mailto|news|nfs|nntp|" +
			"pop|rtsp|sip|sips|snmp|soap\\.beeps?|telnet|tftp|urn|" +
			"xmlrpc\\.beeps?)://.*", Pattern.CASE_INSENSITIVE);

	protected final void renderView(final ServletRequest request, final ServletResponse response, String viewName) throws ServletException, IOException {
		if (!viewName.matches(".*\\.(jspf|csv)?$")) viewName += ".jsp";
		final ServletContext ct = getServletContext();
		final RequestDispatcher rd = ct.getRequestDispatcher("/WEB-INF/view/" + viewName);
		rd.forward(request, response);
	}

	@SuppressWarnings("unchecked")
	public static List<String> getParamNames(final ServletRequest request, final String pattern) {
		final List<String> result = new ArrayList<String>();
		for(Enumeration<Object> e = request.getParameterNames(); e.hasMoreElements(); ){
			final String paramName = (String)e.nextElement();
			if (paramName.contains(pattern))
				result.add(paramName);
		}
		return result;
	}


	public static String getParam(final ServletRequest request, final String name, final String defaultValue) {
		final String result = request.getParameter(name);
		return null == result ? defaultValue : result.trim();
	}

	public static String getParam(final ServletRequest request, final String name) {
		return getParam(request, name, "");
	}

	public static boolean existParam(final ServletRequest request, final String name) {
		final String tmp = getParam(request, name, null);
		return (null != tmp);
	}

	public static boolean getParamAsBoolean(final ServletRequest request, final String name) {
		if (!hasParam(request, name))
			return false;
		final String s = getParam(request, name).toLowerCase();
		for (final String trueText : MyStatics._TRUE_TEXTS)
			if (s.equals(trueText))
				return true;
		return false;
	}

	public static Calendar getParamAsCalendar(final HttpServletRequest request, final String name) {
		String s = getParam(request, name);
		if (null == s || 0 == s.length()) return null;
		final Calendar result = new GregorianCalendar();
		try {  result.setTime(inputDate_1.parse(s));
		} catch (Exception e1) {
			try { result.setTime(inputDate_2.parse(s));
			} catch (Exception e2) {
				try { result.setTime(inputDate_3.parse(s));
				} catch (Exception e3) {
					try { result.setTime(inputDate_4.parse(s));
					} catch (Exception e4) { return null; }
				}
			}
		}
		return result;
	}

	public static Calendar getParamAsCalendarWithTime(final HttpServletRequest request, final String name) {
		String s = getParam(request, name);
		if (null == s || 0 == s.length()) return null;
		final Calendar result = new GregorianCalendar();
		try {  result.setTime(inputDateWithTime.parse(s));
		} catch (Exception e1) {
			return null;
		}
		return result;
	}

	public static double getParamAsDouble(final ServletRequest request, final String name, final double defaultValue) {
		String s = getParam(request, name);
		if (null == s || 0 == s.length())
			return defaultValue;
		try {
			try { s = s.replaceAll(",", "."); } catch (Exception e) { }
			return Double.parseDouble(s);
		} catch (NumberFormatException e) {
			return defaultValue;
		}
	}

	public static int getParamAsInt(final ServletRequest request,
			final String name, final int defaultValue)
	{
		final String s = getParam(request, name);
		if (null == s || 0 == s.length())
			return defaultValue;
		try {
			return Integer.parseInt(s);
		} catch (NumberFormatException e) {
			return defaultValue;
		}
	}


	public static List<String> getParamAsList(final ServletRequest request, final String name) {
		final List<String> result = new ArrayList<String>();
		final String[] mesData = request.getParameterValues(name);
		if (null != mesData) {
			for (final String uneData : mesData) {
				result.add(uneData);
			}
		}
		return result;
	}

	public static long getParamAsLong(final ServletRequest request, final String name, final long defaultValue) {
		final String s = getParam(request, name);
		if (null == s || 0 == s.length())
			return defaultValue;
		try {
			return Long.parseLong(s);
		} catch (NumberFormatException e) {
			return defaultValue;
		}
	}

	public static int getSessionParamAsInt(final ServletRequest request, final String name, final int defaultValue) {
		final String s = getSessionParamAsString(request, name, ""+defaultValue);
		if (null == s || 0 == s.length())
			return defaultValue;
		try {
			return Integer.parseInt(s);
		} catch (NumberFormatException e) {
			return defaultValue;
		}
	}


	public static Object getSessionParam(final ServletRequest request, final String name) {
		final HttpSession session = ((HttpServletRequest)request).getSession();
		final Object s = session.getAttribute(name);
		return s;
	}

	public static String getSessionParamAsString(final ServletRequest request, final String name, final String defaultValue) {
		final HttpSession session = ((HttpServletRequest)request).getSession();
		final Object res = session.getAttribute(name);
		if (null == res || res.toString().equals("")) return defaultValue;
		return res.toString();
	}

	public static int getTmpSessionParamAsInt(final ServletRequest request, final String name, final int defaultValue) {
		final String s = getTmpSessionParam(request, name, ""+defaultValue);
		if (null == s || 0 == s.length())
			return defaultValue;
		try {
			return Integer.parseInt(s);
		} catch (NumberFormatException e) {
			return defaultValue;
		}
	}

	public static double getTmpSessionParamAsDouble(final ServletRequest request, final String name, final double defaultValue) {
		String s = getTmpSessionParam(request, name, ""+defaultValue);
		if (null == s || 0 == s.length())
			return defaultValue;
		try {
			try { s = s.replaceAll(",", "."); } catch (Exception e) { }
			return Double.parseDouble(s);
		} catch (NumberFormatException e) {
			return defaultValue;
		}
	}

	public static boolean getTmpSessionParamAsBoolean(final ServletRequest request, final String name, final boolean defaultValue) {
		final String s = getTmpSessionParam(request, name, ""+defaultValue);
		if (null == s || 0 == s.length())
			return defaultValue;
		try {
			return Boolean.parseBoolean(s);
		} catch (NumberFormatException e) {
			return defaultValue;
		}
	}

	public static boolean existTmpParam(final ServletRequest request, final String name) {
		final HttpSession session = ((HttpServletRequest)request).getSession();
		final Object res = session.getAttribute(name);
		return (null != res && !"".equals(res.toString()));
	}

	public static void setTmpSessionParam(final ServletRequest request, final String name, final Object value) {
		if (null != value) {
			final HttpSession session = ((HttpServletRequest)request).getSession();
			session.setAttribute(name, value);
		}
	}

	public static void removeSessionParam(final ServletRequest request, final String name) {
		try {
			final HttpSession session = ((HttpServletRequest)request).getSession();
			session.removeAttribute(name);
		} catch (Exception e) { }
	}

	public static String getTmpSessionParam(final ServletRequest request, final String name, final String defaultValue) {
		final HttpSession session = ((HttpServletRequest)request).getSession();
		final Object res = session.getAttribute(name);
		if (null == res || res.toString().equals("")) return defaultValue;
		session.removeAttribute(name);
		return res.toString();
	}

	public static Object getTmpSessionParam(final ServletRequest request, final String name) {
		final HttpSession session = ((HttpServletRequest)request).getSession();
		final Object res = session.getAttribute(name);
		session.removeAttribute(name);
		return res;
	}

	public static boolean hasParam(final ServletRequest request, final String name) {
		final String s = getParam(request, name);
		return (null != s && 0 != s.length());
	}

	public static void redirect(final HttpServletRequest request, final HttpServletResponse response, String uri) throws IOException {
		if (!_PROTOCOL_MATCHER.matcher(uri).matches())
			uri = request.getContextPath() + uri;
		response.sendRedirect(response.encodeRedirectURL(uri));
	}

	public static void setSessionParam(final ServletRequest request, final String name, final Object value) {
		if (null != value) {
			final HttpSession session = ((HttpServletRequest)request).getSession();
			session.setAttribute(name, value);
		}
	}
}
