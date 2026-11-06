    package com.eventmanagement.servlet;

    import com.eventmanagement.dao.EventDAO;
    import com.eventmanagement.model.Event;

    import javax.servlet.ServletException;
    import javax.servlet.annotation.WebServlet;
    import javax.servlet.http.HttpServlet;
    import javax.servlet.http.HttpServletRequest;
    import javax.servlet.http.HttpServletResponse;
    import java.io.IOException;
    import java.io.PrintWriter;
    import java.sql.Date;
    import java.time.LocalDate;
    import java.util.List;

    @WebServlet("/events")
    public class EventServlet extends HttpServlet {

        private EventDAO eventDAO = new EventDAO();

        // -------------------------------
        // GET → Return all events as JSON
        // -------------------------------
        @Override
        protected void doGet(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {

            response.setContentType("application/json");
            PrintWriter out = response.getWriter();

            List<Event> events = eventDAO.getAllEvents();

            StringBuilder json = new StringBuilder("[");
            for (int i = 0; i < events.size(); i++) {
                Event e = events.get(i);

                json.append("{")
                    .append("\"eventId\":").append(e.getEventId()).append(",")
                    .append("\"name\":\"").append(escape(e.getName())).append("\",")
                    .append("\"date\":\"").append(e.getDate()).append("\",")
                    .append("\"venue\":\"").append(escape(e.getVenue())).append("\",")
                    .append("\"description\":\"").append(escape(e.getDescription())).append("\"")
                    .append("}");

                if (i < events.size() - 1) json.append(",");
            }
            json.append("]");

            out.print(json.toString());
        }

        // -------------------------------
        // POST → Add/update/delete events
        // -------------------------------
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        String action = request.getParameter("action");

        if (action == null || action.trim().isEmpty()) {
            out.print("{\"status\":\"error\",\"message\":\"Action missing\"}");
            return;
        }

        switch (action) {
            case "add":
                addEvent(request, response);
                break;
            case "update":
                updateEvent(request, response);
                break;
            case "delete":
                deleteEvent(request, response);
                break;
            default:
                out.print("{\"status\":\"error\",\"message\":\"Invalid action\"}");
        }
    }


        // ------------------------------------
        // ADD EVENT
        // ------------------------------------
        private void addEvent(HttpServletRequest request, HttpServletResponse response)
                throws IOException {

            PrintWriter out = response.getWriter();

            String name = request.getParameter("name");
            String venue = request.getParameter("venue");
            String description = request.getParameter("description");
            String dateStr = request.getParameter("date");

            if (name == null || venue == null || dateStr == null) {
                out.print("{\"status\":\"error\",\"message\":\"Missing fields\"}");
                return;
            }

            Date eventDate;

            try {
                eventDate = Date.valueOf(dateStr);
            } catch (Exception e) {
                out.print("{\"status\":\"error\",\"message\":\"Invalid date\"}");
                return;
            }

            if (eventDate.toLocalDate().isBefore(LocalDate.now())) {
                out.print("{\"status\":\"error\",\"message\":\"Date cannot be in past\"}");
                return;
            }

            Event event = new Event();
            event.setName(name);
            event.setVenue(venue);
            event.setDescription(description);
            event.setDate(eventDate);

            boolean success = eventDAO.addEvent(event);

            if (success) out.print("{\"status\":\"success\"}");
            else out.print("{\"status\":\"error\",\"message\":\"DB error\"}");
        }


        // ------------------------------------
        // UPDATE EVENT
        // ------------------------------------
        private void updateEvent(HttpServletRequest request, HttpServletResponse response)
                throws IOException {

            PrintWriter out = response.getWriter();

            int eventId = Integer.parseInt(request.getParameter("eventId"));
            String name = request.getParameter("name");
            String venue = request.getParameter("venue");
            String description = request.getParameter("description");
            String dateStr = request.getParameter("date");

            Date eventDate;
            try {
                eventDate = Date.valueOf(dateStr);
            } catch (Exception e) {
                out.print("{\"status\":\"error\",\"message\":\"Invalid date\"}");
                return;
            }

            if (eventDate.toLocalDate().isBefore(LocalDate.now())) {
                out.print("{\"status\":\"error\",\"message\":\"Date cannot be past\"}");
                return;
            }

            Event event = new Event();
            event.setEventId(eventId);
            event.setName(name);
            event.setVenue(venue);
            event.setDescription(description);
            event.setDate(eventDate);

            boolean success = eventDAO.updateEvent(event);

            if (success) out.print("{\"status\":\"success\"}");
            else out.print("{\"status\":\"error\",\"message\":\"DB error\"}");
        }


        // ------------------------------------
        // DELETE EVENT
        // ------------------------------------
        private void deleteEvent(HttpServletRequest request, HttpServletResponse response)
                throws IOException {

            PrintWriter out = response.getWriter();

            int eventId = Integer.parseInt(request.getParameter("eventId"));
            boolean success = eventDAO.deleteEvent(eventId);

            if (success) out.print("{\"status\":\"success\"}");
            else out.print("{\"status\":\"error\",\"message\":\"DB error\"}");
        }


        // Utility: escape JSON special chars
        private String escape(String s) {
            if (s == null) return "";
            return s.replace("\"", "\\\"");
        }
    }
