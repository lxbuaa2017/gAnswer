package application;
import java.io.IOException;
import java.util.Collections;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fgmt.RelationFragment;
import fgmt.TypeFragment;
import lcn.EntityFragmentFields;
import log.QueryLogger;

import org.eclipse.jetty.client.util.StringContentProvider;
import org.json.*;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import qa.Globals;
import qa.extract.EntityRecognitionCh;

public class GreloadHandler  extends AbstractHandler{

    public static String errorHandle(String status){
        JSONObject exobj = new JSONObject();
        try {
            exobj.put("status", status);
        } catch (Exception ignored) {
        }
        return exobj.toString();
    }

    public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        try{
            response.setContentType("text/html;charset=utf-8");
            request.setCharacterEncoding("utf-8");
            response.setStatus(HttpServletResponse.SC_OK);
            JSONObject infoobj = new JSONObject();
            String[] names = request.getReader().readLine().split(",");
            for(String name:names){
                EntityRecognitionCh.entMap.put(name, Collections.singletonList(name));
            }
            try
            {
                EntityFragmentFields.clear();
                RelationFragment.clear();
                TypeFragment.clear();
                EntityFragmentFields.load();
                RelationFragment.load();
                TypeFragment.load();
            }
            catch (Exception e1) {
                System.out.println("EntityIDs and RelationFragment and TypeFragment loading error!");
                e1.printStackTrace();
            }

            baseRequest.setHandled(true);
            response.getWriter().println(infoobj.toString());
        }
        catch(Exception e){
            if(e instanceof IOException){
                try {
                    baseRequest.setHandled(true);
                    response.getWriter().println(errorHandle("500"));
                } catch (Exception e1) {
                }
            }
            else if(e instanceof JSONException){
                try {
                    baseRequest.setHandled(true);
                    response.getWriter().println(errorHandle("500"));
                } catch (Exception e1) {
                }
            }
            else if(e instanceof ServletException){
                try {
                    baseRequest.setHandled(true);
                    response.getWriter().println(errorHandle("500"));
                } catch (Exception e1) {
                }
            }
            else {
                try {
                    baseRequest.setHandled(true);
                    response.getWriter().println(errorHandle("500"));
                } catch (Exception e1) {
                }
            }
        }
    }

}
