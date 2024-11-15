package info.stepanoff.trsis.lab1.servlets;

import info.stepanoff.trsis.lab1.model.DataModel;
import info.stepanoff.trsis.lab1.model.Employee;
import info.stepanoff.trsis.lab1.model.Room;
import info.stepanoff.trsis.lab1.model.SecData;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "SecServletUser", urlPatterns = {"/sec/user"})
public class SecServletUser extends HttpServlet {
    protected String htmlPath = "src/main/resources/user.html";
    protected String srcHtml = SecData.readHTML(htmlPath);


    // Data processing
    protected String checkAccess(HttpServletRequest request) {
        String[] htmlCodeSep = srcHtml.split("<!-- status_check_access -->");

        int employeeID, roomID;
        try {
            employeeID = Integer.parseInt(request.getParameter("checkEmployeeID"));
            roomID = Integer.parseInt(request.getParameter("checkRoomID"));
        }
        catch (NumberFormatException e) {
            return htmlCodeSep[0] + "Введен некорректный ID!" + htmlCodeSep[1];
        }

        if (!SecData.employees.containsKey(employeeID))
            return htmlCodeSep[0] + "Сотрудник с указанным ID не найден!" + htmlCodeSep[1];
        if (!SecData.rooms.containsKey(roomID))
            return htmlCodeSep[0] + "Помещение с указанным ID не найдено!" + htmlCodeSep[1];
        if (!SecData.access.containsKey(employeeID))
            return htmlCodeSep[0] + "У сотрудника " + employeeID + " нет доступа к помещению " + roomID + htmlCodeSep[1];

        if (SecData.access.get(employeeID).contains(roomID))
            return htmlCodeSep[0] + "У сотрудника " + employeeID + " есть доступ к помещению " + roomID + htmlCodeSep[1];
        return htmlCodeSep[0] + "У сотрудника " + employeeID + " нет доступа к помещению " + roomID + htmlCodeSep[1];
    }

    protected String genTable(String htmlCode, int table_type) {
        switch (table_type) {
            case 0 -> {
                if (SecData.employees.isEmpty())
                    return htmlCode;
            }
            case 1 -> {
                if (SecData.rooms.isEmpty())
                    return htmlCode;
            }
            default -> {
                return htmlCode;
            }
        }

        String separator;
        switch (table_type) {
            case 0 -> separator = "<!-- end_employee_table -->";
            case 1 -> separator = "<!-- end_rooms_table -->";
            default -> {
                return htmlCode;
            }
        }

        StringBuilder tableBody = new StringBuilder();
        String[] htmlCodeSep = htmlCode.split(separator);
        switch (table_type) {
            case 0 -> {
                for (Employee emp : SecData.employees.values()) {
                    tableBody.append("<tr>");
                    tableBody.append("<td>").append(emp.ID).append("</td>");
                    tableBody.append("<td>").append(emp.name).append("</td>");
                    tableBody.append("<td>").append(emp.department).append("</td>");
                    tableBody.append("<td>").append(emp.position).append("</td>");
                    tableBody.append("</tr>\n");
                }
            }
            case 1 -> {
                for (Room room : SecData.rooms.values()) {
                    tableBody.append("<tr>");
                    tableBody.append("<td>").append(room.ID).append("</td>");
                    tableBody.append("<td>").append(room.name).append("</td>");
                    tableBody.append("<td>").append(room.department).append("</td>");
                    tableBody.append("<td>").append(room.capacity).append("</td>");
                    tableBody.append("</tr>\n");
                }
            }
            default -> {
                return htmlCode;
            }
        }
        tableBody.append(separator).append('\n');

        return htmlCodeSep[0] + tableBody.toString() + htmlCodeSep[1];
    }


    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            String htmlCode = srcHtml;
            String htmlCode0 = genTable(htmlCode, 0);
            String htmlCode1 = genTable(htmlCode0, 1);
            out.println(htmlCode1);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            String submit = request.getParameter("user_submit");
            String htmlCode;
            if (submit != null) {
                htmlCode = switch (submit) {
                    case "check_access" -> checkAccess(request);
                    default -> srcHtml;
                };
            }
            else {
                htmlCode = srcHtml;
            }
            String htmlCode0 = genTable(htmlCode, 0);
            String htmlCode1 = genTable(htmlCode0, 1);
            out.println(htmlCode1);
        }
    }
}
