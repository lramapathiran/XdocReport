package com.example.lavanya.controller;


import com.example.lavanya.model.Person;
import com.example.lavanya.model.PersonForm;
import com.example.lavanya.model.PersonFormDelete;
import com.example.lavanya.model.ProjectReport;
import com.example.lavanya.model.ProjectWithLogo;
import com.example.lavanya.service.PersonFormDeleteService;
import com.example.lavanya.service.PersonFormService;
import com.example.lavanya.service.PersonService;

import fr.opensagres.xdocreport.core.XDocReportException;
import fr.opensagres.xdocreport.document.IXDocReport;
import fr.opensagres.xdocreport.document.registry.XDocReportRegistry;
import fr.opensagres.xdocreport.template.IContext;
import fr.opensagres.xdocreport.template.TemplateEngineKind;
import fr.opensagres.xdocreport.template.formatter.FieldsMetadata;

import java.beans.PropertyEditorSupport;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.apache.poi.xwpf.converter.pdf.PdfConverter;
import org.apache.poi.xwpf.converter.pdf.PdfOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

//import org.o7planning.thymeleaf.docxandvelocity.model.Project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller // This means that this class is a Controller
public class MainController {
		
	@Autowired
	private PersonService personService;
	
	@Autowired
	private PersonFormService personFormService;
	
	@Autowired
	private PersonFormDeleteService personFormDeleteService;
	
	
	// Injectez (inject) via application.properties.
	@Value("${welcome.message}")
	private String message;

	@Value("${error.message}")
	private String errorMessage;
	
	
	@RequestMapping(value = { "/home", "/index" }, method = RequestMethod.GET)
	public String index(Model model) {

		model.addAttribute("message", message);

		return "index";
	}

	@RequestMapping(value = { "/persons" }, method = RequestMethod.GET)
	public String personList(Model model) {
		

		List<Person> list  = personService.getPersonsList();
		
//		list ajouté dans le modèle si on veut afficher les personnes sans la liste de commandes!
//		Dans html on affiche que la liste des personnes, on boucle sur la liste et non un map
		

		model.addAttribute("list", list);
			    
		return "personList";
	}

	@RequestMapping(value = { "/addPerson" }, method = RequestMethod.GET)
	public String showAddPersonPage(Model model) {
		
		PersonForm personForm = new PersonForm();
		model.addAttribute("personForm", personForm);

		return "addPerson";
	}

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
	  binder.registerCustomEditor(LocalDate.class, new PropertyEditorSupport() {
	    @Override
	    public void setAsText(String text) throws IllegalArgumentException{
	      setValue(LocalDate.parse(text, DateTimeFormatter.ofPattern("yyyy-MM-dd")));
	    }

	    @Override
	    public String getAsText() throws IllegalArgumentException {
	      if (getValue() == null) {
	    	  return "";
	      }
	      return DateTimeFormatter.ofPattern("yyyy-MM-dd").format((LocalDate) getValue());
	    }
	  });
	}
	
	@RequestMapping(value = { "/savePerson" }, method = RequestMethod.POST)
	public String addNewPerson(@Valid @ModelAttribute("personForm") PersonForm personForm, Model model) { //
		
			String firstName = personForm.getFirstName();
			String lastName = personForm.getLastName();
			LocalDate birthDate = personForm.getBirthDate();
				
			
			if (firstName == null || firstName.length() < 1 //
					|| lastName == null || lastName.length() < 1 || birthDate == null) {
				model.addAttribute("errorMessage", errorMessage);
				return "addPerson";
			}
			
			
			personFormService.save(personForm);
		
			return "redirect:/persons";

			
	}

	@RequestMapping(value = { "/deletePerson" }, method = RequestMethod.POST)
	public String deletePerson(Model model,  
			@ModelAttribute("personFormDelete") PersonFormDelete personFormDelete) {
		
		personFormDeleteService.deletePerson(personFormDelete);
		
		return "redirect:/persons";
	}
	
	   
	   @RequestMapping(value = { "/updatePerson/{id}" }, method = RequestMethod.GET)
		public String showUpdatePersonPage(@PathVariable("id") int id, Person person, Model model) {
		   	
		   person = personService.getPersonById(id);
		   model.addAttribute("person", person);

			return "updatePerson";
		}

		@RequestMapping(value = { "/updatePerson/{id}" }, method = RequestMethod.POST)
		public String updatePerson(@PathVariable("id") int id, @Valid Person person, 
				  BindingResult result, Model model) { 
			
				String firstName = person.getFirstName();
				String lastName = person.getLastName();
				LocalDate birthDate = person.getBirthDate();
				
				if (firstName == null || firstName.length() < 1 //
						|| lastName == null || lastName.length() < 1 || birthDate == null) {
					model.addAttribute("errorMessage", errorMessage);
					return "updatePerson";
				}
				
				
				personService.save(person);
			
				return "redirect:/persons";

				
		}
		
		@PostMapping("/exportToWordToPDF")
		public String exportMysqlDataToPDF(HttpServletResponse response, String index) throws IOException, XDocReportException {
				
			try {
//			Conversion from server to docx
				// 1) Load Docx file by filling Velocity template engine and cache it to the registry
				InputStream in = MainController.class.getResourceAsStream("PersonsListXdocReport.docx");
				IXDocReport report = XDocReportRegistry.getRegistry().loadReport(in,TemplateEngineKind.Velocity);
				      
				// 2) Create fields metadata to manage image and lazy loop (#foreach velocity)
				// for table row.
				// a) Create FieldsMetadata by setting Velocity as template engine
				FieldsMetadata fieldsMetadata = report.createFieldsMetadata();
				// b) Load fields metadata from Java Class
//				fieldsMetadata.load("project", ProjectReport.class);
				// Here load is called with true because model is a list of Developer.
				fieldsMetadata.load("persons", Person.class, true);
				 // NEW API which use @FieldMetadata
	            fieldsMetadata.load( "project", ProjectWithLogo.class);
				
//				Add dynamic image
//				fieldsMetadata.addFieldAsImage("logo");

				// 3) Create context Java model
				IContext context = report.createContext();
//				ProjectReport project = new ProjectReport("Liste de Personnes Enregistrées");
				ProjectReport projectLogo = new ProjectWithLogo("XdocReport");
//				context.put("project", project);
				context.put("project", projectLogo);
				
				// Image from File
	            fieldsMetadata.addFieldAsImage( "logoFile", "project.logoFile" );
						
				// Register persons list
				List<Person> list  = personService.getPersonsList();
				
				context.put("persons", list);
				
				
//				HttpHeaders headers = new HttpHeaders();
				
//			    InputStream imageStream = servletContext.getResourceAsStream("/images/innso_logo.jpg");
//			    byte[] media = IOUtils.toByteArray(imageStream);
//			    
//			    IImageProvider logo = new ByteArrayImageProvider(media);
//			    context.put("logo", logo);
			    
			    
//			    headers.setCacheControl(CacheControl.noCache().getHeaderValue());
//			    
//			    ResponseEntity<byte[]> responseEntity = new ResponseEntity<>(media, headers, HttpStatus.OK);
//				
				
				
				
				
				
				
				
				
//				IImageProvider logo = new ClassPathImageProvider(
//						MainController.class, "logo.png");
//				context.put("logo", logo);
				      
				// 4) Generate report by merging Java model with the Docx
				OutputStream out = new FileOutputStream(new File("PersonsListXdocReport_out.docx"));
				report.process(context, out);

				   
//			Conversion from docx to PDF
				// 1) Load DOCX into XWPFDocument
				InputStream inDocx= new FileInputStream(new File("PersonsListXdocReport_out.docx"));
				XWPFDocument document = new XWPFDocument(inDocx);

				// 2) Prepare Pdf options
				PdfOptions options = PdfOptions.create();

				// 3a) Convert XWPFDocument to Pdf and save it locally
//				OutputStream outPDF = new FileOutputStream(new File("PersonsListXdocReport_out.pdf"));
//				PdfConverter.getInstance().convert(document, outPDF, options);
				
//				3b)or Convert XWPFDocument to Pdf and download file from server to client
				response.setContentType("application/octet-stream");    // set content attributes for the response
		        response.setHeader("Content-Disposition", "attachment; filename=\"PersonsListXdocReport_out.pdf\"");
		        OutputStream outputStream = response.getOutputStream();             // get output stream of the response   
		        PdfConverter.getInstance().convert(document, outputStream, options);
		        outputStream.flush();

			} catch (IOException e) {
				e.printStackTrace();
			} catch (XDocReportException e) {
				e.printStackTrace();
			}
//				
				return "redirect:/persons";
			

				
		}
		
		
			
}



	


