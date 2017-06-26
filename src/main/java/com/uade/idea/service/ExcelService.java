package com.uade.idea.service;

import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uade.idea.domain.Person;
import com.uade.idea.domain.PersonUade;
import com.uade.idea.domain.Project;
import com.uade.idea.repository.ProjectRepository;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

@Service
@Transactional
public class ExcelService{
    private static final Logger LOGGER = Logger.getLogger(ExcelService.class);
   
    @Autowired
    private ProjectRepository projectRepository;
 
    @PersistenceContext
    private EntityManager entityManager;
    
    public Session getSession(){
    	return this.entityManager.unwrap(Session.class);
    }
    
    public WritableWorkbook createExcelOutputExcel(HttpServletResponse response) {
       String fileName = Calendar.getInstance().getTime().toString();
       WritableWorkbook writableWorkbook = null;
       try {
           response.setContentType("application/vnd.ms-excel");

           response.setHeader("Content-Disposition:", "attachment; filename=\"" + fileName + "\"");

           writableWorkbook = Workbook.createWorkbook(response.getOutputStream());
          
           WritableSheet excelOutputsheet = writableWorkbook.createSheet("Excel Output", 0);
           
           this.getSession().enableFilter("getProjectsOfActivePeriod");
           
           List<Project> projects = projectRepository.findAll();
           
           addExcelOutputHeader(excelOutputsheet);
           writeExcelOutputData(excelOutputsheet,projects);
           
           writableWorkbook.write();
           writableWorkbook.close();

       } catch (Exception e) {
           LOGGER.error("Error occured while creating Excel file", e);

       }

       return writableWorkbook;
    }

   
    private void addExcelOutputHeader(WritableSheet sheet) throws RowsExceededException, WriteException{
       // create header row
        sheet.addCell(new Label(0, 0, "Nombre de proyecto"));
        sheet.addCell(new Label(1, 0, "Fecha de presentacion"));
        sheet.addCell(new Label(2, 0, "Nombre"));
        sheet.addCell(new Label(3, 0, "Apellido"));
        sheet.addCell(new Label(4, 0, "DNI"));
        sheet.addCell(new Label(5, 0, "Edad"));
        sheet.addCell(new Label(6, 0, "Numero de telephono"));
        sheet.addCell(new Label(7, 0, "Email"));
        sheet.addCell(new Label(8, 0, "Legajo"));
        sheet.addCell(new Label(9, 0, "Carrera"));
        sheet.addCell(new Label(10, 0, "Avance"));
    }
   
    private void writeExcelOutputData(WritableSheet sheet,List<Project> projects) throws RowsExceededException, WriteException{
       int i = 1;
       for(Project project : projects){
    	   for(Person person : project.getTeam()){
	    	   sheet.addCell(new Label(0, i,project.getTitle()));
	    	   sheet.addCell(new Label(1, i,project.getCreatedDate().toString()));
	    	   sheet.addCell(new Label(2, i,person.getFirstName()));
	    	   sheet.addCell(new Label(3, i,person.getLastName()));
	    	   sheet.addCell(new Label(4, i,person.getDni()));
	    	   sheet.addCell(new Label(5, i,String.valueOf(person.getAge())));
	    	   sheet.addCell(new Label(6, i,person.getPhoneNumber()));
	    	   sheet.addCell(new Label(7, i,person.getEmail()));
	    	   if(person instanceof PersonUade){
	    		   sheet.addCell(new Label(8, i,((PersonUade)person).getFileNumber()));
	    		   sheet.addCell(new Label(8, i,((PersonUade)person).getCareer()));
	    		   sheet.addCell(new Label(8, i,((PersonUade)person).getStage().toString()));
	    	   }
    	   }
       }
    }
}
