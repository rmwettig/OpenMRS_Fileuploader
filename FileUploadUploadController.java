package org.openmrs.module.fileupload.web.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openmrs.api.context.Context;
import org.openmrs.module.fileupload.ConversionConstants;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


@Controller
@RequestMapping("/module/fileupload/upload")
public class FileUploadUploadController 
{
	private List<String> getTargetDirs()
	{
		List<String> tdirs = new ArrayList<String>();
		String dirs = Context.getAdministrationService().getGlobalProperty("fileupload.rootDirectory");
		for (String d : dirs.split(";"))
			tdirs.add(d);
		
		return tdirs;		
	}
	@RequestMapping(method=RequestMethod.GET)
	public void showUploadForm(ModelMap model)
	{
		model.addAttribute("user", Context.getAuthenticatedUser());
		model.addAttribute("result", "");
		model.addAttribute("targetDirs",getTargetDirs());
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public void uploadFile(@RequestParam(required=true, value="targetDir")String targetDir,
			@RequestParam(required=true, value="file") MultipartFile inputFile,
			ModelMap model)
	{
		//get the uploaded file name like it appears on the client's pc
		String orgName = inputFile.getOriginalFilename();
		String allowedTypes = Context.getAdministrationService().getGlobalProperty("fileupload.allowedFileTypes"); //must be with real prefix addressed
		String disallowedTypes = Context.getAdministrationService().getGlobalProperty("fileupload.disallowedFileTypes");
		
		//extract the extension and check whether it is a forbidden one
		String extension = orgName.substring(orgName.lastIndexOf(".")+1);//orgName.split(".")[1].toLowerCase();
		
		//model.addAttribute("result", orgName+"_"+extension+"_"+allowedTypes+"_"+disallowedTypes);//debug
		
		if(!disallowedTypes.contains(extension))
		{
			//accept the file type when there are no explicit extensions specified or if the input extension is included in the specified list
			if(allowedTypes.isEmpty() || allowedTypes.contains(extension))
			{
				//check whether the file size in bytes is smaller than the specified size
				long fileSize = inputFile.getSize();
				String allowedFileSizeValue = Context.getAdministrationService().getGlobalProperty("fileupload.maxFileSizeValue");
				String allowedFileSizeUnit = Context.getAdministrationService().getGlobalProperty("fileupload.maxFileSizeUnit");
				long allowedBytes = convertToBytes(allowedFileSizeValue, allowedFileSizeUnit);
				
				if(fileSize < allowedBytes)
				{
					//upload to specified directory
					try 
					{
						inputFile.transferTo(new File(targetDir+"/"+orgName));
						model.addAttribute("result", "Upload was successful.");
					}
					catch (IllegalStateException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) 
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				else
				{
					model.addAttribute("result", "Upload has failed. File is too big.");
				}
			}
			else
			{
				model.addAttribute("result", "Upload has failed. File type is not on the whitelist.");
			}
		}
		else
		{
			model.addAttribute("result", "Upload has failed. File type is on the blacklist.");
		}
		model.addAttribute("targetDirs",getTargetDirs());
	}
	
	/**
	 * converts a numerical string along with its unit to bytes
	 * @param sizeString represents the size, e.g. 20
	 * @param sizeUnit specifies the unit of the size string, e.g. M (for megabytes)
	 * @return converted size in bytes
	 */
	private long convertToBytes(String sizeString, String sizeUnit)
	{
		long conv = Long.parseLong(sizeString);
		
		if(sizeUnit.equalsIgnoreCase("M"))
			conv = conv * ConversionConstants.megabyteFactor;
		else if(sizeUnit.equalsIgnoreCase("G"))
			conv = conv * ConversionConstants.gigabyteFactor;
		else if(sizeUnit.equalsIgnoreCase("K"))
			conv = conv * ConversionConstants.kilobyteFactor;		
		
		return conv;
	}
}
