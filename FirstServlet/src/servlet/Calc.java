package servlet;

import calc.CalcOperations;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

@WebServlet(name = "Calc", urlPatterns = {"/Calc"})
public class Calc extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        if(request.getParameter("operation") != null &&
                request.getParameter("one") != null &&
                request.getParameter("two") != null) {
            //Input is valid, create session
            HttpSession session = request.getSession(true);
            List list;
            if (session.getAttribute("operationHistory") != null) {
                list = (List) session.getAttribute("operationHistory");
            } else {
                list = new LinkedList();
            }
            String operation;
            Double result;
            Double one = Double.valueOf(request.getParameter("one"));
            Double two = Double.valueOf(request.getParameter("two"));
            switch (request.getParameter("operation")) {
                case "add": result = CalcOperations.add(one, two);
                    operation = "+";
                    break;
                case "subtract": result = CalcOperations.subtract(one, two);
                    operation = "-";
                    break;
                case "multiply": result = CalcOperations.multiply(one, two);
                    operation = "*";
                    break;
                case "divide": result = CalcOperations.divide(one, two);
                    operation = "/";
                    break;
                default: result = Double.valueOf(0);
                    operation = "?";
                    break;
            }
            String calcResult = "<p>" + one + operation + two +" = " + result + "</p>";
            try {
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Calc</title>");
                out.println("</head>");
                out.println("<body>");
                out.println("<h1>Session id: " + request.getSession().getId() + "</h1>");
                out.println(calcResult);
                out.println("<h2>History: </h2>");
                for(int cnt = 0; cnt < list.size(); cnt ++) out.println(list.get(cnt));
//                out.println(list.toString());
                out.println("</body>");
                out.println("</html>");
                //Add operation to a history
                list.add(calcResult);
                session.setAttribute("operationHistory", list);

                //display history for all the sessions
                while(HttpSession session: getServletConfig().getS)
            } finally {
                out.close();
            }
        } else {
            try {
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Calc</title>");
                out.println("</head>");
                out.println("<body>");
                out.println("<p>At least one of the parameters is invalid or doesn't exist</p>");
                out.println("</body>");
                out.println("</html>");
            } finally {
                out.close();
            }
        }
    }
}
