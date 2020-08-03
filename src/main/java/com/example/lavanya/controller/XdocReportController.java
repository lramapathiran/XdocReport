package com.example.lavanya.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.lavanya.model.Person;
import com.example.lavanya.model.ProjectReport;
import com.example.lavanya.service.PersonService;

import fr.opensagres.xdocreport.core.XDocReportException;
import fr.opensagres.xdocreport.document.IXDocReport;
import fr.opensagres.xdocreport.document.registry.XDocReportRegistry;
import fr.opensagres.xdocreport.template.IContext;
import fr.opensagres.xdocreport.template.TemplateEngineKind;
import fr.opensagres.xdocreport.template.formatter.FieldsMetadata;

public class XdocReportController {
	
	@Autowired
	private PersonService personService;
	  
//	@PostMapping("/exportToWord")
//	public String exportMysqlDataToWord() {
//			
//			try {
//			      // 1) Load Docx file by filling Velocity template engine and cache it to the registry
//					InputStream in = XdocReportController.class.getResourceAsStream("PersonsListXdocReport.docx");
//					IXDocReport report = XDocReportRegistry.getRegistry().loadReport(in,TemplateEngineKind.Velocity);
//			      
//					// 2) Create fields metadata to manage lazy loop (#forech velocity)
//					// for table row.
//						// a) Create FieldsMetadata by setting Velocity as template engine
//					FieldsMetadata fieldsMetadata = report.createFieldsMetadata();
//						// b) Load fields metadata from Java Class
//					fieldsMetadata.load("project", ProjectReport.class);
//							// Here load is called with true because model is a list of Developer.
//					fieldsMetadata.load("persons", Person.class, true);
//
//					// 3) Create context Java model
//					IContext context = report.createContext();
//					ProjectReport project = new ProjectReport("Liste de Personnes Enregistrées");
//					context.put("project", project);
//					
//					// Register persons list
//					List<Person> list  = personService.getPersonsList();
//					context.put("persons", list);
//			      
//			      // 4) Generate report by merging Java model with the Docx
//			      OutputStream out = new FileOutputStream(new File("PersonsListXdocReport_out.docx"));
//			      report.process(context, out);
//
//			    } catch (IOException e) {
//			      e.printStackTrace();
//			    } catch (XDocReportException e) {
//			      e.printStackTrace();
//			    }
//			
//			
//			
//			return "redirect:/persons";
//		
//
//			
//		}

	  
	  
}
