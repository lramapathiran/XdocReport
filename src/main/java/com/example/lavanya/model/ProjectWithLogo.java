package com.example.lavanya.model;

import java.io.File;
import java.io.InputStream;

import com.example.lavanya.controller.MainController;

import fr.opensagres.xdocreport.template.annotations.FieldMetadata;
import fr.opensagres.xdocreport.template.annotations.ImageMetadata;
import fr.opensagres.xdocreport.template.formatter.NullImageBehaviour;

public class ProjectWithLogo extends ProjectReport {

	public ProjectWithLogo( String name )

    {

        super( name );

    }



    // as docx cannot support bookmark name, with dot, we force the name of this field to "logo" and we use "logo" as
    // bookmark name which wraps the "template image".
      



    @FieldMetadata( images = { @ImageMetadata( name = "logoFile" ) } )
    public File getLogoFile()
    {

        return new File( "src/main/resources/static/images/logo.jpg" );

    }



    
	
}
