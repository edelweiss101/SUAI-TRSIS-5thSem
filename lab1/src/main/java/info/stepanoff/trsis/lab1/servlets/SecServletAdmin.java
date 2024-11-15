package info.stepanoff.trsis.lab1.servlets;

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
import java.util.*;

@WebServlet(name = "SecServletAdmin", urlPatterns = {"/sec/admin"})
public class SecServletAdmin extends HttpServlet {
    protected String htmlPath = "src/main/resources/admin.html";
    protected String srcHtml = SecData.readHTML(htmlPath);

    // Data processing
    protected String addEmployee(HttpServletRequest request) {
        String[] htmlCodeSep = srcHtml.split("<!-- status_add_employee -->");

        Employee newEmployee = new Employee();
        try {
            newEmployee.ID = Integer.parseInt(request.getParameter("employeeID"));
            newEmployee.name = request.getParameter("name");
            newEmployee.department = request.getParameter("department");
            newEmployee.position = request.getParameter("position");
        }
        catch (NumberFormatException e) {
            return htmlCodeSep[0] + "Введен некорректный ID!" + htmlCodeSep[1];
        }

        SecData.employees.put(newEmployee.ID, newEmployee);
        return htmlCodeSep[0] + "Сотрудник успешно добавлен." + htmlCodeSep[1];
    }

    protected String addRoom(HttpServletRequest request) {
        String[] htmlCodeSep = srcHtml.split("<!-- status_add_room -->");

        Room newRoom = new Room();
        try {
            newRoom.ID = Integer.parseInt(request.getParameter("roomID"));
            newRoom.capacity = Integer.parseInt(request.getParameter("roomCapacity"));
            newRoom.name = request.getParameter("roomName");
            newRoom.department = request.getParameter("roomDepartment");
        }
        catch (NumberFormatException e) {
            return htmlCodeSep[0] + "Введен некорректный ID или вместительность!" + htmlCodeSep[1];
        }

        SecData.rooms.put(newRoom.ID, newRoom);
        return htmlCodeSep[0] + "Помещение успешно добавлено." + htmlCodeSep[1];
    }

    protected String delEmployee(HttpServletRequest request) {
        String[] htmlCodeSep = srcHtml.split("<!-- status_del_employee -->");

        int remEmployeeID;
        try {
            remEmployeeID = Integer.parseInt(request.getParameter("deleteEmployeeID"));
        }
        catch (NumberFormatException e) {
            return htmlCodeSep[0] + "Введен некорректный ID!" + htmlCodeSep[1];
        }

        if (SecData.employees.remove(remEmployeeID) == null)
            return htmlCodeSep[0] + "Сотрудник с указанным ID не найден!" + htmlCodeSep[1];

        SecData.access.remove(remEmployeeID);
        return htmlCodeSep[0] + "Сотрудник успешно удален." + htmlCodeSep[1];
    }

    protected String delRoom(HttpServletRequest request) {
        String[] htmlCodeSep = srcHtml.split("<!-- status_del_room -->");

        int remRoomID;
        try {
            remRoomID = Integer.parseInt(request.getParameter("deleteRoomID"));
        }
        catch (NumberFormatException e) {
            return htmlCodeSep[0] + "Введен некорректный ID!" + htmlCodeSep[1];
        }

        if (SecData.rooms.remove(remRoomID) == null)
            return htmlCodeSep[0] + "Помещение с указанным ID не найдено!" + htmlCodeSep[1];

        for (HashSet<Integer> roomIDs : SecData.access.values())
            roomIDs.remove(remRoomID);
        return htmlCodeSep[0] + "Помещение успешно удалено." + htmlCodeSep[1];
    }

    protected String grantAccess(HttpServletRequest request) {
        String[] htmlCodeSep = srcHtml.split("<!-- status_grant_access -->");

        int employeeID, roomID;
        try {
            employeeID = Integer.parseInt(request.getParameter("accessEmployeeID"));
            roomID = Integer.parseInt(request.getParameter("accessRoomID"));
        }
        catch (NumberFormatException e) {
            return htmlCodeSep[0] + "Введен некорректный ID!" + htmlCodeSep[1];
        }

        if (!SecData.employees.containsKey(employeeID))
            return htmlCodeSep[0] + "Сотрудник с указанным ID не найден!" + htmlCodeSep[1];
        if (!SecData.rooms.containsKey(roomID))
            return htmlCodeSep[0] + "Помещение с указанным ID не найдено!" + htmlCodeSep[1];

        if (!SecData.access.containsKey(employeeID))
            SecData.access.put(employeeID, new HashSet<Integer>());
        if (SecData.access.get(employeeID).add(roomID))
            return htmlCodeSep[0] + "Сотруднику " + employeeID + " успешно выдан доступ к помещению " + roomID + htmlCodeSep[1];
        return htmlCodeSep[0] + "Сотруднику " + employeeID + " уже был выдан доступ к помещению " + roomID + htmlCodeSep[1];
    }

    protected String revokeAccess(HttpServletRequest request) {
        String[] htmlCodeSep = srcHtml.split("<!-- status_revoke_access -->");

        int employeeID, roomID;
        try {
            employeeID = Integer.parseInt(request.getParameter("revokeEmployeeID"));
            roomID = Integer.parseInt(request.getParameter("revokeRoomID"));
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

        if (SecData.access.get(employeeID).remove(roomID))
            return htmlCodeSep[0] + "У сотрудника " + employeeID + " успешно отозван доступ к помещению " + roomID + htmlCodeSep[1];
        return htmlCodeSep[0] + "У сотрудника " + employeeID + " нет доступа к помещению " + roomID + htmlCodeSep[1];
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
            out.println(srcHtml);
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
            String submit = request.getParameter("admin_submit");
            String htmlCode;
            if (submit != null) {
                htmlCode = switch (submit) {
                    case "add_employee" -> addEmployee(request);
                    case "add_room" -> addRoom(request);
                    case "del_employee" -> delEmployee(request);
                    case "del_room" -> delRoom(request);
                    case "grant_access" -> grantAccess(request);
                    case "revoke_access" -> revokeAccess(request);
                    default -> srcHtml;
                };
            }
            else {
                htmlCode = srcHtml;
            }
            out.println(htmlCode);
        }
    }
}
